package com.teeny.wms.page.login;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerViewTouchListener;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.dao.ServerConfigEntityDao;
import com.teeny.wms.datasouce.local.DatabaseManager;
import com.teeny.wms.datasouce.local.cache.ServerConfigManager;
import com.teeny.wms.model.ServerConfigEntity;
import com.teeny.wms.page.login.adapter.ServerConfigAdapter;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ServerConfigActivity
 * @since 2017/7/14
 */

public class ServerConfigActivity extends ToolbarActivity implements RecyclerViewTouchListener.OnItemClickListener, RecyclerViewTouchListener.OnItemLongClickListener, DialogInterface.OnClickListener {

    private static final int REQUEST_CODE = 0x01;
    private static final int INVALID_POSITION = -1;

    public static void startActivityForResult(Activity context, int requestCode) {
        Intent intent = new Intent(context, ServerConfigActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    private ServerConfigAdapter mAdapter;
    private ServerConfigManager mServerConfigManager;
    private AlertDialog mDeleteDialog;
    private int mDeletePosition = INVALID_POSITION;
    private ServerConfigEntityDao mServerConfigEntityDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_recycler_view);

        initView();
        obtainData();
        registerEventBus();
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new ServerConfigAdapter(null);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        RecyclerViewTouchListener listener = new RecyclerViewTouchListener(this, this);
        listener.setOnItemLongClickListener(this);
        recyclerView.addOnItemTouchListener(listener);

        mServerConfigManager = ServerConfigManager.getInstance();
        mDeleteDialog = DialogFactory.createAlertDialog(this, getString(R.string.prompt_delete_confirm), this);
        mDeleteDialog.setOnDismissListener(dialog -> mDeletePosition = INVALID_POSITION);
    }

    private void obtainData() {
        DatabaseManager manager = DatabaseManager.getInstance();
        mServerConfigEntityDao = manager.getDaoSession().getServerConfigEntityDao();
        List<ServerConfigEntity> data = mServerConfigEntityDao.loadAll();
        if (Validator.isEmpty(data)) {
            Toaster.showToast("未添加过服务器配置!");
        }
        mAdapter.setItems(data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onServerAdded(ServerConfigEntity entity) {
        mAdapter.insert(entity, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE:
                setResult(RESULT_OK);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        unregisterEventBus();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add:
                SystemConfigCreateActivity.startActivity(this, REQUEST_CODE);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        mServerConfigManager.set(mAdapter.getItem(position)).save();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        mDeletePosition = position;
        mDeleteDialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mDeletePosition != INVALID_POSITION) {
            ServerConfigEntity entity = mAdapter.getItem(mDeletePosition);
            mAdapter.remove(mDeletePosition);
            mServerConfigEntityDao.delete(entity);
        }
    }
}
