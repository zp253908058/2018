package com.teeny.wms.util.log;

import android.util.Log;

import com.teeny.wms.util.Validator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see LoggerPrinter
 * @since 2017/5/9
 */

class LoggerPrinter implements Printer {

    private static final String LOG_TAG = "Logger";

    private static final int DEBUG = 3;
    private static final int ERROR = 6;
    private static final int ASSERT = 7;
    private static final int INFO = 4;
    private static final int VERBOSE = 2;
    private static final int WARN = 5;

    /**
     * Android's max limit for a print entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset
     * is UTF-8
     */
    private static final int CHUNK_SIZE = 4000;

    /**
     * It is used for json pretty print
     */
    private static final int JSON_INDENT = 2;

    /**
     * The minimum stack trace index, starts at this class after two native calls.
     */
    private static final int MIN_STACK_OFFSET = 3;

    /**
     * Drawing toolbox
     */
    private static final char TOP_LEFT_CORNER = '╔';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char MIDDLE_CORNER = '╟';
    private static final char HORIZONTAL_DOUBLE_LINE = '║';
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    private static final String COLON = " : ";

    /**
     * tag is used for the Log, the name is a little different
     * in order to differentiate the logs easily with the filter
     */
    private String mTag;

    /**
     * Localize single tag and method count for each thread
     */
    private final ThreadLocal<String> mLocalTag = new ThreadLocal<>();
    private final ThreadLocal<Integer> mLocalMethodCount = new ThreadLocal<>();

    private final Settings mSettings = Settings.getInstance();

    public LoggerPrinter() {
        init(LOG_TAG);
    }

    @Override
    public Settings init(String tag) {
        if (Validator.isEmpty(tag)) {
            throw new IllegalStateException("tag may not be null or empty");
        }
        this.mTag = tag;
        return mSettings;
    }

    @Override
    public void resetSettings() {
        mSettings.reset();
    }

    @Override
    public Printer t(String tag, int methodCount) {
        if (tag != null) {
            mLocalTag.set(tag);
        }
        mLocalMethodCount.set(methodCount);
        return this;
    }

    @Override
    public void d(String message, Object... args) {
        print(DEBUG, null, message, args);
    }

    @Override
    public void d(Object object) {
        String message;
        if (object == null) {
            message = "object == null";
        } else if (object.getClass().isArray()) {
            message = Arrays.deepToString((Object[]) object);
        } else {
            message = object.toString();
        }
        print(DEBUG, null, message);
    }

    @Override
    public void e(String message, Object... args) {
        e(null, message, args);
    }

    @Override
    public void e(Throwable throwable, String message, Object... args) {
        print(ERROR, throwable, message, args);
    }

    @Override
    public void w(String message, Object... args) {
        print(WARN, null, message, args);
    }

    @Override
    public void i(String message, Object... args) {
        print(INFO, null, message, args);
    }

    @Override
    public void v(String message, Object... args) {
        print(VERBOSE, null, message, args);
    }

    @Override
    public void wtf(String message, Object... args) {
        print(ASSERT, null, message, args);
    }

    @Override
    public void json(String json) {
        if (Validator.isEmpty(json)) {
            d("Empty/Null json content");
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                d(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                d(message);
                return;
            }
            e("Invalid Json");
        } catch (JSONException e) {
            e(e, "Invalid Json");
        }
    }

    @Override
    public void xml(String xml) {
        if (Validator.isEmpty(xml)) {
            d("Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            d(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            e("Invalid xml");
        }
    }

    @Override
    public void print(int priority, String tag, String message, Throwable throwable) {
        if (!mSettings.isLogAvailable()) {
            return;
        }
        if (throwable != null && message != null) {
            message += " : " + Log.getStackTraceString(throwable);
        }
        if (throwable != null && message == null) {
            message = Log.getStackTraceString(throwable);
        }
        if (message == null) {
            message = "No message/exception is set";
        }
        int methodCount = getMethodCount();
        if (Validator.isEmpty(message)) {
            message = "Empty/NULL print message";
        }

        printTopBorder(priority, tag);
        printHeaderContent(priority, tag, methodCount);

        //get bytes of message with system's default charset (which is UTF-8 for Android)
        byte[] bytes = message.getBytes();
        int length = bytes.length;
        if (length <= CHUNK_SIZE) {
            if (methodCount > 0) {
                printDivider(priority, tag);
            }
            printContent(priority, tag, message);
            printBottomBorder(priority, tag);
            return;
        }
        if (methodCount > 0) {
            printDivider(priority, tag);
        }
        for (int i = 0; i < length; i += CHUNK_SIZE) {
            int count = Math.min(length - i, CHUNK_SIZE);
            //create a new String with system's default charset (which is UTF-8 for Android)
            printContent(priority, tag, new String(bytes, i, count));
        }
        printBottomBorder(priority, tag);
    }

    /**
     * This method is synchronized in order to avoid messy of logs' order.
     */
    private synchronized void print(int priority, Throwable throwable, String msg, Object... args) {
        if (!mSettings.isLogAvailable()) {
            return;
        }
        String tag = getTag();
        String message = createMessage(msg, args);
        print(priority, tag, message, throwable);
    }

    private void printTopBorder(int logType, String tag) {
        printChunk(logType, tag, TOP_BORDER);
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private void printHeaderContent(int logType, String tag, int methodCount) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (mSettings.isShowThreadInfo()) {
            printChunk(logType, tag, HORIZONTAL_DOUBLE_LINE + " Thread: " + Thread.currentThread().getName());
            printDivider(logType, tag);
        }
        String level = "";

        int stackOffset = getStackOffset(trace) + mSettings.getMethodOffset();

        //corresponding method count with the current stack may exceeds the stack trace. Trims the count
        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            StringBuilder builder = new StringBuilder();
            builder.append("║ ")
                    .append(level)
                    .append(getSimpleClassName(trace[stackIndex].getClassName()))
                    .append(".")
                    .append(trace[stackIndex].getMethodName())
                    .append(" ")
                    .append(" (")
                    .append(trace[stackIndex].getFileName())
                    .append(":")
                    .append(trace[stackIndex].getLineNumber())
                    .append(")");
            level += "   ";
            printChunk(logType, tag, builder.toString());
        }
    }

    private void printBottomBorder(int logType, String tag) {
        printChunk(logType, tag, BOTTOM_BORDER);
    }

    private void printDivider(int logType, String tag) {
        printChunk(logType, tag, MIDDLE_BORDER);
    }

    private void printContent(int logType, String tag, String chunk) {
        String[] lines = chunk.split(System.getProperty("line.separator"));
        for (String line : lines) {
            printChunk(logType, tag, HORIZONTAL_DOUBLE_LINE + " " + line);
        }
    }

    private void printChunk(int logType, String tag, String chunk) {
        String finalTag = formatTag(tag);
        LogAdapter adapter = mSettings.getLogAdapter();
        switch (logType) {
            case ERROR:
                adapter.e(finalTag, chunk);
                break;
            case INFO:
                adapter.i(finalTag, chunk);
                break;
            case VERBOSE:
                adapter.v(finalTag, chunk);
                break;
            case WARN:
                adapter.w(finalTag, chunk);
                break;
            case ASSERT:
                adapter.wtf(finalTag, chunk);
                break;
            case DEBUG:
                // Fall through, print debug by default
            default:
                adapter.d(finalTag, chunk);
                break;
        }
    }

    private String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    private String formatTag(String tag) {
        if (!Validator.isEmpty(tag) && !tag.equals(this.mTag)) {
            return this.mTag + "-" + tag;
        }
        return this.mTag;
    }

    /**
     * @return the appropriate tag based on local or global
     */
    private String getTag() {
        String tag = mLocalTag.get();
        if (tag != null) {
            mLocalTag.remove();
            return tag;
        }
        return this.mTag;
    }

    private String createMessage(String message, Object... args) {
        return args == null || args.length == 0 ? message : String.format(message, args);
    }

    private int getMethodCount() {
        Integer count = mLocalMethodCount.get();
        int result = mSettings.getMethodCount();
        if (count != null) {
            mLocalMethodCount.remove();
            result = count;
        }
        if (result < 0) {
            throw new IllegalStateException("methodCount cannot be negative");
        }
        return result;
    }

    /**
     * Determines the starting index of the stack trace, after method calls made by this class.
     *
     * @param trace the stack trace
     * @return the stack offset
     */
    private int getStackOffset(StackTraceElement[] trace) {
        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(LoggerPrinter.class.getName()) && !name.equals(Logger.class.getName())) {
                return --i;
            }
        }
        return -1;
    }
}
