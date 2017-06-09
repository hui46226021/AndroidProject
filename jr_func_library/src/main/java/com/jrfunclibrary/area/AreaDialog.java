package com.jrfunclibrary.area;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
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
import android.widget.Toast;


import com.jereibaselibrary.netowrk.HttpAsynTask;
import com.jereibaselibrary.netowrk.HttpUtils;
import com.jereibaselibrary.netowrk.listen.HandleResponse;
import com.jereibaselibrary.netowrk.listen.RequestCall;
import com.jereibaselibrary.tools.JRDataResult;
import com.jruilibrary.widget.MyProgressDialog;
import com.sh.zsh.jrfunclibrary.R;

import java.util.ArrayList;
import java.util.List;




/**
 * Created by zhush on 17-6-9 下午3:10
 * Email:405086805@qq.com
 * Ps:地区选择框
 */
public class AreaDialog {
	/**
	 * 得到自定义的progressDialog
	 * @param context
	 * @param msg
	 * @return
	 */

	public  Dialog dialog;
	List<CommArea> list = new ArrayList<>();
	Context context;
	AreaAdapter commAdapter;
	SelectedCall selectedCall;
	public AreaDialog(Context context, SelectedCall selectedCall) {
		this.context = context;
		this.selectedCall=selectedCall;
		createDialog();
	}
	public static interface SelectedCall{
		public void selectedArea(CommArea prductModel);
	}

	/**
	 * 创建Dailog
	 * @return
	 */
	public  Dialog createDialog() {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialog_right, null);// 得到加载view

		ListView listView = (ListView) v.findViewById(R.id.listview);
		//listview适配器
		commAdapter = new AreaAdapter();
		listView.setAdapter(commAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

				if(list.get(i).getLeaf()){

					selectedCall.selectedArea(list.get(i));
					dismiss();
				}else {
					queryArea(list.get(i).getAreaId());
				}

			}
		});
		dialog = new Dialog(context, R.style.rightDialogStyle);
		dialog.setContentView(v, new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
				RadioGroup.LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.right_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();

		wl.gravity = Gravity.RIGHT;
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = (int)(( (Activity)context).getWindowManager().getDefaultDisplay()
				.getWidth() * 0.8);
		WindowManager m = ((Activity)context).getWindowManager();
		wl.height = ViewGroup.LayoutParams.MATCH_PARENT;

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
		queryArea(0l);
		dialog.show();
	}


	 class AreaAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int i) {
			return list.get(i).getAreaName();
		}

		@Override
		public long getItemId(int i) {
			return 0;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
			ViewHolder holder;
			if (view == null) {
				view = LayoutInflater.from(context).inflate(R.layout.item_comm_layout, viewGroup, false);
				holder = new ViewHolder();
				holder.text = (TextView) view.findViewById(R.id.text);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.text.setText((String) getItem(i));
			return view;
		}


		 class ViewHolder {
			TextView text;
		}
	}

	RequestCall<List<CommArea>>request = new RequestCall<List<CommArea>>() {
		@Override
		public void success(List<CommArea> dataResult) {
			list.clear();
			list.addAll(dataResult);
			commAdapter.notifyDataSetChanged();
			MyProgressDialog.dismiss();
		}

		@Override
		public void failed(String message, int errorCode) {
			dismiss();
			MyProgressDialog.dismiss();
			Toast.makeText(context,message,Toast.LENGTH_LONG).show();
		}
	};

	public void queryArea(Long keyid){
		MyProgressDialog.show(context,"正在查询",false);
		HttpAsynTask httpAsynTask = new HttpAsynTask("comm/pAreaList.action");

		httpAsynTask.putParam("id",keyid);
		httpAsynTask.putParam("page", 1);
		httpAsynTask.putParam("rows", 100);
		httpAsynTask.putParam("sort", "area_id");
		httpAsynTask.putParam("order", "desc");
		httpAsynTask.setHttpRequestCall(request);
		httpAsynTask.setHandleResponse(new HandleResponse() {
			@Override
			public JRDataResult putResponse(JRDataResult dataControlResult, HttpUtils client) {

				List<CommArea>  list = client.getList(CommArea.class,"rows");
				dataControlResult.setResultObject(list);
				return dataControlResult;
			}
		});
		httpAsynTask.execute();
	}
}
