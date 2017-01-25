package com.sh.shprojectdemo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jruilibarary.widget.RoundCornerImageView;
import com.jruilibarary.widget.letterlist.LetterBaseListAdapter;
import com.jruilibarary.widget.letterlist.LetterModle;
import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.model.City;
import com.xinlan.dragindicator.DragIndicatorView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhush on 2017/1/20.
 * E-mail zhush@jerei.com
 * PS
 */
public class LetterAdapter extends LetterBaseListAdapter {


    private Context context;

    public LetterAdapter(List<LetterModle> dataList, Context context,Selected selected) {
        super(dataList);
        this.context = context;
        this.selected = selected;
    }

    /**
     *   要显示的文字
     * @param t
     * @return
     */
    @Override
    public String getItemString(LetterModle t) {
        City city = (City) t;
        return city.getName();
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     * 显示字母行的布局
     */
    @Override
    public View getLetterView(int position, View convertView, ViewGroup parent) {
        //这里是字母的item界面设置.
        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.item_letter_list_view, null);
            convertView = new TextView(context);
            convertView.setBackgroundColor(context.getResources().getColor(R.color.ui_background));
        }

//        ((TextView)convertView.findViewById(R.id.text)).setText(list.get(position).getFirstLetter());
        ((TextView) convertView).setText("   "+list.get(position).getFirstLetter());
        return convertView;
    }

    /**
     * 显示文字行的布局
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getContainerView(int position, View convertView, ViewGroup parent) {
        LetterAdapter.ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.item_letter_list_view, null);

            holder = new LetterAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (LetterAdapter.ViewHolder) convertView.getTag();
        }
       final City city = (City) getItem(position);

        holder.text.setText(city.getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected.selectde(city);
            }
        });
        return convertView;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    static class ViewHolder {

        @InjectView(R.id.text)
        TextView text;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
    Selected selected;
   public static interface Selected{
        public void selectde(City city);
    }
}
