package com.sh.shprojectdemo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sh.shprojectdemo.R;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class IMFragment extends Fragment {


    int page;
    @InjectView(R.id.text)
    TextView text;

    public IMFragment() {
        // Required empty public constructor
    }

    public void setPage(int page) {
        this.page = page;
    }

    public static IMFragment newInstance(int i) {
        IMFragment fragment = new IMFragment();

        fragment.setPage(i);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_im, container, false);
        ButterKnife.inject(this, view);

        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText("第"+page+"页");
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
