package com.carolsum.jingle.ui.space;

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
public class SpaceFragment extends Fragment {

    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_space, container, false);
    }

}
