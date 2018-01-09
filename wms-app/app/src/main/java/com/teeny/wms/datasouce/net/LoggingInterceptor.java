package com.teeny.wms.datasouce.net;

import android.support.annotation.NonNull;
import android.util.Log;

import com.teeny.wms.util.log.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see LoggingInterceptor
 * @since 2017/8/1
 */

public class LoggingInterceptor implements Interceptor {

    private static final String BR = "\n";

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);
    private static final String TAG = LoggingInterceptor.class.getSimpleName();

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Log.e(TAG, "intercept: " + request.url());
        final int id = ID_GENERATOR.incrementAndGet();
        String message;
        message = "[current request count: " + id + " ]" + BR;
        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        message += "Protocol: " + protocol + BR;
        message += "Method: " + request.method() + BR;
        message += "Url: " + request.url() + BR;

        message += "request header: " + BR;

        if (hasRequestBody) {
            // Request body headers are only present when installed as a network interceptor. Force
            // them to be included (when available) so there values are known.
            if (requestBody.contentType() != null) {
                message += ("Content-Type: " + requestBody.contentType() + BR);
            }
            if (requestBody.contentLength() != -1) {
                message += ("Content-Length: " + requestBody.contentLength() + BR);
            }
        }

        Headers requestHeaders = request.headers();
        StringBuilder requestHeader = new StringBuilder();
        for (int i = 0, count = requestHeaders.size(); i < count; i++) {
            String name = requestHeaders.name(i);
            // Skip headers from the request body as they are explicitly logged above.
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                requestHeader.append(name).append(": ").append(requestHeaders.value(i)).append(BR);
            }
        }

        message += requestHeader.toString();

        if (!hasRequestBody) {
            message += ("request body: null" + BR);
        } else if (bodyEncoded(request.headers())) {
            message += ("request body: encoded body omitted" + BR);
        } else {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            if (isPlaintext(buffer)) {
                String bufferString = null;
                if (charset != null) {
                    bufferString = buffer.readString(charset);
                }
                message += ("request body: " + bufferString + BR);
                message += ("content length: " + requestBody.contentLength() + "-byte body" + BR);
            } else {
                message += (" (binary " + requestBody.contentLength() + "-byte body omitted)" + BR);
            }
        }

        message += "----------------------------------------------------------------------------------" + BR;

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            message += "response body: null";
            message += "code: " + response.code() + BR;
            message += "message: " + response.message() + BR;
        } else {
            long contentLength = responseBody.contentLength();
            String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
            message += "code: " + response.code() + BR;
            message += "message: " + response.message() + BR;
            message += "tookMs: " + tookMs + "ms" + (", bodySize: " + bodySize + " body") + BR;

            Headers responseHeaders = response.headers();
            StringBuilder responseHeader = new StringBuilder();
            for (int i = 0, count = responseHeaders.size(); i < count; i++) {
                responseHeader.append(responseHeaders.name(i)).append(": ").append(responseHeaders.value(i)).append(BR);
            }

            message += responseHeader.toString();

            if (!HttpHeaders.hasBody(response)) {
                message += ("response body: null" + BR);
            } else if (bodyEncoded(response.headers())) {
                message += ("response body: encoded body omitted" + BR);
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                message += "response body: " + BR;
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8);
                    } catch (UnsupportedCharsetException e) {
                        message += ("Couldn't decode the response body; charset is likely malformed.");
                        Logger.e(message);
                        return response;
                    }
                }

                if (contentLength != 0) {
                    if (charset != null) {
                        message += (buffer.clone().readString(charset));
                    }
                }

                message += ("(" + buffer.size() + "-byte body)");
            }
        }
        Logger.e(message);
        return response;
    }

    private static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private static boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}

