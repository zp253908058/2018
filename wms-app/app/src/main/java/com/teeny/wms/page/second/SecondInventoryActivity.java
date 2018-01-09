package com.teeny.wms.page.second;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import com.teeny.wms.R;
import com.teeny.wms.base.BaseFragmentPagerAdapter;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.SecondInventoryService;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.SecondInventoryGoodsEntity;
import com.teeny.wms.page.common.activity.InventoryActivity;
import com.teeny.wms.page.common.adapter.SimpleAdapter;
import com.teeny.wms.page.common.fragment.InventoryHeaderFragment;
import com.teeny.wms.page.second.adapter.SecondInventoryGoodsAdapter;
import com.teeny.wms.page.second.fragment.SecondInventoryFragment;
import com.teeny.wms.page.second.fragment.SecondInventoryHeaderFragment;
import com.teeny.wms.page.second.helper.SecondInventoryHelper;
import com.teeny.wms.page.warehouse.WarehouseFirstAddActivity;
import com.teeny.wms.page.warehouse.fragment.WarehouseInventoryFragment;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;
import com.teeny.wms.widget.KeyValueTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description: 复盘
 *
 * @author zp
 * @version 1.0
 * @see SecondInventoryActivity
 * @since 2017/7/16
 */

public class SecondInventoryActivity extends InventoryActivity {
    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SecondInventoryActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void startAdd(Context context, int key, String locationCode) {
        SecondInventoryAddActivity.startActivity(context, key, locationCode);
    }

    @Override
    protected InventoryHeaderFragment createHeaderFragment() {
        return SecondInventoryHeaderFragment.newInstance();
    }

    @Override
    protected Fragment createFragment(int position) {
        return SecondInventoryFragment.newInstance(position);
    }
}
