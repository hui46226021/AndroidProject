package com.jrfunclibrary.base.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
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
import com.jrfunclibrary.area.CommArea;
import com.jruilibrary.widget.MyProgressDialog;
import com.jruilibrary.widget.RefreshListView;
import com.jruilibrary.widget.UISearchView;
import com.sh.zsh.jrfunclibrary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by zhush on 17-6-9 下午3:10
 * Email:405086805@qq.com
 * Ps: * Ps:  继承次类的自定义控件
 *      重写getView 和 responseHanle 方法 可实现 分页选中器 （基类）
 *
 * 公有函数
 * putParam 设置参数
 * setUrl  设置查询路径
 */
public abstract class BaseRightDialog <T>implements RefreshListView.RefreshListViewListener{



	public Context context;//上下文
	public  String url;//获取数据的url
	public UISearchView uiSearchView;//搜索控件
	public HashMap<String,Object> paramMap =new HashMap<>();//参数集合
	public List<T> dataList = new ArrayList<T>();//数据集合
	public RefreshListView listView;//下拉刷新上拉加载组件


	public  Dialog dialog;
	public SelectedCall selectedCall;


	/**
	 * 适配器
	 */
	BaseAdapter	baseAdapter = new BaseAdapter() {
		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {
			return dataList.get(0);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
			return BaseRightDialog.this.getView(i,view,viewGroup);
		}

	};

	/**
	 * 构造方法
	 * @param context
	 * @param selectedCall  选中回调
	 */
	public BaseRightDialog(Context context, SelectedCall selectedCall) {

		this.context = context;
		this.selectedCall = selectedCall;
		createDialog();
	}

	public static interface SelectedCall<T>{
		public void selected(T dataResult);
	}

    /**
     * 创建Dailog
	 * @return
     */
	public  Dialog createDialog() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.comm_dialog_right, null);// 得到加载view
		uiSearchView = (UISearchView) v.findViewById(R.id.search);
		listView = (RefreshListView) v.findViewById(R.id.listview);
		listView.setAdapter(baseAdapter);
		listView.setRefreshListViewListener(this);
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
		dialog.show();
	}



	@Override
	public void onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onRefresh(final boolean isRefresh, int page) {
		HttpAsynTask httpAsynTask = new HttpAsynTask(url);
		httpAsynTask.putParam("page", page);
		httpAsynTask.putParam("keyWord", uiSearchView.getContent());
		httpAsynTask.putParam("rows", 20);
		httpAsynTask.putParam("order", "desc");
		httpAsynTask.setHttpRequestCall(new RequestCall<List<T>>() {
			@Override
			public void success(List<T> dataResult) {
				if(isRefresh){
					dataList.clear();
				}
				dataList.addAll(dataResult);
				listView.setRefreshing(false);

			}

			@Override
			public void failed(String message, int errorCode) {
				dismiss();
				listView.setRefreshing(false);
				Toast.makeText(context,message,Toast.LENGTH_LONG).show();
			}
		});
		httpAsynTask.setHandleResponse(new HandleResponse() {
			@Override
			public JRDataResult putResponse(JRDataResult dataControlResult, HttpUtils client) {
				return responseHanle(dataControlResult,client);
			}
		});
		httpAsynTask.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		selectedCall.selected(dataList.get(position));
	}

	/**
	 * BaseAdapter 的 getView 方法
	 * @param i
	 * @param view
	 * @param viewGroup
	 * @return
	 */
	protected abstract View getView(int i, View view, ViewGroup viewGroup);

	/**
	 * 处理 网络获取到的数据
	 * @param dataControlResult
	 * @param client
	 * @return
	 */
	protected abstract JRDataResult responseHanle(JRDataResult dataControlResult, HttpUtils client);

	/**
	 * 设置其他参数
	 */
	public void putParam(String key,Object object){
		paramMap.put(key,object);
	}

	/**
	 * 设置url
	 */
	public void setUrl(String url){
		this.url =url;
	}
}
