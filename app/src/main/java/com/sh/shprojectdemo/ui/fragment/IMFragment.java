package com.sh.shprojectdemo.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh.shprojectdemo.R;


public class IMFragment extends Fragment {




    public IMFragment() {
        // Required empty public constructor
    }


    public static IMFragment newInstance() {
        IMFragment fragment = new IMFragment();


        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_im, container, false);
    }


}
