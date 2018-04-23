package com.teeny.wms.page.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.SeekBar;

import com.teeny.wms.R;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.datasouce.local.SharedPreferencesManager;
import com.teeny.wms.widget.KeyValueTextView;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see RefreshTimeActivity
 * @since 2018/4/21
 */
public class RefreshTimeActivity extends ToolbarActivity implements SeekBar.OnSeekBarChangeListener {

    public static final String REFRESH_TIME = "refresh_time";

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RefreshTimeActivity.class);
        context.startActivity(intent);
    }

    private KeyValueTextView mKeyValueTextView;
    private String mFormat;
    private int mSeconds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_time_layout);
        initView();
    }

    private void initView() {
        mFormat = getResources().getString(R.string.text_refresh_time_format);
        SeekBar seekBar = findViewById(R.id.refresh_time_seek_bar);
        mKeyValueTextView = findViewById(R.id.refresh_time_tip);
        seekBar.setOnSeekBarChangeListener(this);
        mSeconds = SharedPreferencesManager.getInstance().getRefreshTimePreferences().getInt(RefreshTimeActivity.REFRESH_TIME, 600);
        if (mSeconds == 0) {
            mSeconds = 600;
        }
        int progress = mSeconds / 60 - 1;
        seekBar.setProgress(progress);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int minute = progress + 1;
        mSeconds = minute * 60;
        mKeyValueTextView.setValue(String.format(mFormat, minute));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void onDestroy() {
        SharedPreferencesManager.getInstance().getRefreshTimePreferences().putInt(RefreshTimeActivity.REFRESH_TIME, mSeconds);
        getEventBus().post(new RefreshTimeFlag(mSeconds));
        super.onDestroy();
    }

    public static final class RefreshTimeFlag {
        private int intervalSecond;

        RefreshTimeFlag(int intervalSecond) {
            this.intervalSecond = intervalSecond;
        }

        public int getIntervalSecond() {
            return intervalSecond;
        }

        public void setIntervalSecond(int intervalSecond) {
            this.intervalSecond = intervalSecond;
        }
    }
}
