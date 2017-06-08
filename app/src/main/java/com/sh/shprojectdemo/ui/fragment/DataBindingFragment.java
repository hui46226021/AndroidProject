package com.sh.shprojectdemo.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh.shprojectdemo.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DataBindingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DataBindingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataBindingFragment extends Fragment {




    public DataBindingFragment() {

    }


    public static DataBindingFragment newInstance(int i) {
        DataBindingFragment fragment = new DataBindingFragment();

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_binding, container, false);
    }



}
