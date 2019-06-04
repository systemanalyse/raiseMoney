package com.carolsum.jingle.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carolsum.jingle.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpaceFragment extends BaseFragment {

    private TabLayout tabLayout;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected View initView() {
        return View.inflate(getActivity(), R.layout.fragment_space, null);
    }

}
