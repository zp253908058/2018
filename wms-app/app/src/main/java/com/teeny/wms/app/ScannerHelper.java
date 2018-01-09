package com.teeny.wms.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ScannerHelper
 * @since 2017/8/28
 */

public class ScannerHelper {

    public interface ResultHandler {
        void handleResult(String msg);
    }

    private static volatile ScannerHelper mInstance;

    public static ScannerHelper getInstance() {
        if (mInstance == null) {
            synchronized (ScannerHelper.class) {
                if (mInstance == null) {
                    mInstance = new ScannerHelper();
                }
            }
        }
        return mInstance;
    }

    private static final String SCANNER_CTRL_FILE = "/sys/devices/platform/scan_se955/se955_state";
    private static final String SCANNER_CTRL_OPEN = "4";
    private static final String SCANNER_CTRL_CLOSE = "5";

    private static final String SCANNER_ACTION_FILTER = "com.android.server.scannerservice.broadcast";
    private static final String SCANNER_RESULT_DATA = "scannerdata";

    private static final String SCANNER_ACTION_START = "android.intent.action.SCANNER_BUTTON_DOWN";
    private static final String SCANNER_ACTION_CANCEL = "android.intent.action.SCANNER_BUTTON_UP";

    private final Map<Class, BroadcastReceiver> mReceiverHolder;

    private ScannerHelper() {
        mReceiverHolder = new HashMap<>();
    }

    private void setScannerAvailable(boolean available) {
        if (available) {
            writeToScannerCtrlFile(SCANNER_CTRL_OPEN);
        } else {
            writeToScannerCtrlFile(SCANNER_CTRL_CLOSE);
        }
    }

    private void writeToScannerCtrlFile(String data) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(SCANNER_CTRL_FILE));
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public boolean isScannerAvailable() {
        try {
            File file = new File(SCANNER_CTRL_FILE);

            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
                BufferedReader br = new BufferedReader(isr);
                String result = "";
                String lineTxt;
                while ((lineTxt = br.readLine()) != null) {
                    result = result.concat(lineTxt);
                }
                br.close();
                return result.trim().equals(SCANNER_CTRL_OPEN);
            } else {
                return false;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void openScanner(Context context, ResultHandler handler) {
        setScannerAvailable(true);
        IntentFilter intentFilter = new IntentFilter(SCANNER_ACTION_FILTER);
        ScannerBroadcastReceiver receiver = new ScannerBroadcastReceiver(handler);
        context.registerReceiver(receiver, intentFilter);
        mReceiverHolder.put(context.getClass(), receiver);
    }

    public void unregisterReceiver(Context context) {
        BroadcastReceiver receiver = mReceiverHolder.get(context.getClass());
        context.unregisterReceiver(receiver);
        mReceiverHolder.remove(context.getClass());
    }

    public void startScan(Context context) {
        Intent scannerIntent = new Intent(SCANNER_ACTION_START);
        context.sendBroadcast(scannerIntent);
    }

    public void stopScan(Context context) {
        Intent scannerIntent = new Intent(SCANNER_ACTION_CANCEL);
        context.sendBroadcast(scannerIntent);
    }

    public void closeScanner() {
        setScannerAvailable(false);
    }

    private static class ScannerBroadcastReceiver extends BroadcastReceiver {

        private ResultHandler mResultHandler;

        public ScannerBroadcastReceiver(ResultHandler resultHandler) {
            mResultHandler = resultHandler;
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            switch (action) {
                case SCANNER_ACTION_FILTER:
                    String message = intent.getStringExtra(SCANNER_RESULT_DATA);
                    mResultHandler.handleResult(message);
                    break;
            }
        }
    }
}
