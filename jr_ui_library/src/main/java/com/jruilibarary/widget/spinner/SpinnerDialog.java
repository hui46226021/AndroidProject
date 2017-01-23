package com.jruilibarary.widget.spinner;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.sh.zsh.jr_ui_library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhush on 2016/9/29.
 * E-mail zhush@jerei.com
 */
public class SpinnerDialog {
    /**
     * 得到自定义的progressDialog
     * @param context
     * @param msg
     * @return
     */


    public  Dialog dialog;
    ListView listview1;

    SpinnerAdapter spinnerAdapter1;


    SelectedCall selectedCall;

    public SpinnerDialog(SelectedCall selectedCall) {
        this.selectedCall = selectedCall;
    }

    List<SpinnerModel> lsit = new ArrayList<SpinnerModel>() ;



    public void notifyDataSetChanged(){
        spinnerAdapter1.notifyDataSetChanged();
    }

    public  Dialog createLoadingDialog(Context context, final List<SpinnerModel> list1) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_jr_spinner, null);// 得到加载view

         listview1 = (ListView) v.findViewById(R.id.listview1);
        spinnerAdapter1 = new SpinnerAdapter(context,list1);
        listview1.setAdapter(spinnerAdapter1);
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dismiss();
                selectedCall.selectedCall(list1.get(i));
            }
        });

        TextView general_cancel = (TextView) v.findViewById(R.id.general_cancel);
        general_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



        dialog = new Dialog(context, R.style.spnner_dialog);

        dialog.setContentView(v, new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y =( (Activity)context).getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        WindowManager m = ((Activity)context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用

        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围消失
        dialog.setCanceledOnTouchOutside(true);

        return dialog;

    }


    public  void dismiss(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    public void show(){
        dialog.show();
    }
    static class SpinnerAdapter extends BaseAdapter {

         private List<SpinnerModel> listData;
         private Context context;

         public SpinnerAdapter(Context context, List<SpinnerModel> listData) {
             this.context = context;
             this.listData = listData;
         }

         @Override
         public int getCount() {
             return listData.size();
         }

         @Override
         public Object getItem(int position) {
             return listData.get(position);
         }

         @Override
         public long getItemId(int position) {
             return 0;
         }

         @Override
         public View getView(int position, View convertView, ViewGroup parent) {
             ViewHolder holder;
             if (convertView == null) {
                 convertView = LayoutInflater.from(context).inflate(R.layout.item_notice_layout, parent, false);
                 holder = new ViewHolder();
                 holder.tital = (TextView) convertView.findViewById(R.id.tital);

                 convertView.setTag(holder);
             } else {
                 holder = (ViewHolder) convertView.getTag();
             }
             SpinnerModel map = (SpinnerModel) getItem(position);
             holder.tital.setText(map.getKey());
             return convertView;
         }

         class ViewHolder {

             TextView tital;

         }
     }

    /**
     * 弹出下拉框选中监听
     */
    public static interface SelectedCall{
        public void selectedCall(SpinnerModel spinnerModel);
    }
}
