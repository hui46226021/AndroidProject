package com.sh.shprojectdemo.ui.fragment;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.sh.shprojectdemo.DataBindingBinding;
import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.common.cache.TemporaryCache;
import com.sh.shprojectdemo.model.User;


public class DataBindingFragment extends Fragment {


    int page;


    public DataBindingFragment() {
        // Required empty public constructor
    }

    public void setPage(int page) {
        this.page = page;
    }

    public static DataBindingFragment newInstance(int i) {
        DataBindingFragment fragment = new DataBindingFragment();

        fragment.setPage(i);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        DataBindingBinding dataBindingBinding=DataBindingUtil.inflate(inflater,
                R.layout.fragment_im, container, false);
        User user = TemporaryCache.getUserSession();
        dataBindingBinding.setUser(user);
        return dataBindingBinding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
