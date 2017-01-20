package com.sh.shprojectdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jereibaselibrary.image.JRSetImage;
import com.jruilibarary.widget.RoundCornerImageView;
import com.sh.shprojectdemo.model.News;
import com.sh.shprojectdemo.R;
import com.xinlan.dragindicator.DragIndicatorView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhush on 2017/1/20.
 * E-mail zhush@jerei.com
 * PS
 */
public class TestAdapter extends BaseAdapter {
    private List<News> listData;
    private Context context;

    public TestAdapter(List<News> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
      final   ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_conversation, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        News news = (News) getItem(i);
        JRSetImage.setLocalBitmap(context,R.mipmap.avatar,holder.avatar);
        holder.count.setVisibility(View.VISIBLE);
        holder.count.setText(news.getId()+"");
        holder.name.setText(news.getTitle());
        holder.lastMessage.setText(news.getContent());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(buttonCall!=null){
                    buttonCall.delete(i);
                }
            }
        });

        holder.read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(buttonCall!=null){
                    buttonCall.read(holder.count,i);
                }
            }
        });
        return view;
    }


    static class ViewHolder {
        @InjectView(R.id.avatar)
        RoundCornerImageView avatar;
        @InjectView(R.id.count)
        DragIndicatorView count;
        @InjectView(R.id.name)
        TextView name;
        @InjectView(R.id.last_message)
        TextView lastMessage;
        @InjectView(R.id.read)
        TextView read;
        @InjectView(R.id.delete)
        TextView delete;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    private ButtonCall buttonCall;

    public void setButtonCall(ButtonCall buttonCall) {
        this.buttonCall = buttonCall;
    }

    public static interface ButtonCall{
        public void delete(int i);
        public void read(DragIndicatorView count,int i);
    }
}
