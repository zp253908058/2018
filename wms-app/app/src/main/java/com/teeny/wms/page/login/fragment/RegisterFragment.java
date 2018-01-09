package com.teeny.wms.page.login.fragment;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teeny.wms.R;
import com.teeny.wms.base.BaseFragment;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see RegisterFragment
 * @since 2017/7/15
 */

public class RegisterFragment extends BaseFragment {
    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    protected View onCreateHolderView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_register_layout, container, false);
    }
}
