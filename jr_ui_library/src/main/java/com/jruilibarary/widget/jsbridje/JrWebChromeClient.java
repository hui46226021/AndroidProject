package com.jruilibarary.widget.jsbridje;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Bitmap;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;

/**
 * http://618119.com/archives/2010/12/20/199.html
 */

//****************************************************************************
public class JrWebChromeClient extends WebChromeClient {
	private ProgressBar progressbar; 
    private Dialog mDialog;
	public JrWebChromeClient(ProgressBar progressbar){
		this.progressbar = progressbar;
		
	}
	public JrWebChromeClient(Context ctx, ProgressBar progressbar){
		this.progressbar = progressbar;
	}
	@Override
	public void onCloseWindow(WebView window) {
		super.onCloseWindow(window);
	}

	@Override
	public boolean onCreateWindow(WebView view, boolean dialog,
			boolean userGesture, Message resultMsg) {
		return super.onCreateWindow(view, dialog, userGesture, resultMsg);
	}
	
    //扩充缓存的容量    
	@Override
	public void onReachedMaxAppCacheSize(long spaceNeeded, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
		quotaUpdater.updateQuota(spaceNeeded * 2);
	}    

	/**
	 * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////”
	 */
	public boolean onJsAlert(WebView view, String url, String message,
			JsResult result) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
				
		builder.setTitle("温馨提示")
				.setMessage(message)
				.setPositiveButton("确定", null);
				
		// 不需要绑定按键事件
		// 屏蔽keycode等于84之类的按键
		builder.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,KeyEvent event) {
				Log.v("onJsAlert", "keyCode==" + keyCode + "event="+ event);
				return true;
			}
		});
		// 禁止响应按back键的事件
		builder.setCancelable(false);
		AlertDialog dialog = builder.create();
		dialog.show();
		result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
		return true;
		// return super.onJsAlert(view, url, message, result);
	}

	public boolean onJsBeforeUnload(WebView view, String url,
			String message, JsResult result) {
		return super.onJsBeforeUnload(view, url, message, result);
	}

	/**
	 * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
	 */
	public boolean onJsConfirm(WebView view, String url, String message,
			final JsResult result) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
		builder.setTitle("温馨提示")
				.setMessage(message)
				.setPositiveButton("确定",new OnClickListener() {
							public void onClick(DialogInterface dialog,int which) {
								result.confirm();
							}
						})
				.setNeutralButton("取消", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						result.cancel();
					}
				});
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				result.cancel();
			}
		});

		// 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
		builder.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,KeyEvent event) {
				Log.v("onJsConfirm", "keyCode==" + keyCode + "event="+ event);
				return true;
			}
		});
		// 禁止响应按back键的事件
		// builder.setCancelable(false);
		AlertDialog dialog = builder.create();
		dialog.show();
		return true;
		// return super.onJsConfirm(view, url, message, result);
	}

	/**
	 * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////”
	 * window.prompt('请输入您的域名地址', '618119.com');
	 */
	public boolean onJsPrompt(WebView view, String url, String message,
			String defaultValue, final JsPromptResult result) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
				
		builder.setTitle("温馨提示").setMessage(message);
				
		final EditText et = new EditText(view.getContext());
		et.setSingleLine();
		et.setText(defaultValue);
		builder.setView(et)
				.setPositiveButton("确定", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						result.confirm(et.getText().toString());
					}
		
				})
				.setNeutralButton("取消", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						result.cancel();
					}
				});

		// 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
		builder.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,KeyEvent event) {
				Log.v("onJsPrompt", "keyCode==" + keyCode + "event="+ event);
				return true;
			}
		});

		// 禁止响应按back键的事件
		// builder.setCancelable(false);
		AlertDialog dialog = builder.create();
		dialog.show();
		return true;
		// return super.onJsPrompt(view, url, message, defaultValue,
		// result);
	}

	@Override
	public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress == 100) {
        	if(mDialog!=null&&mDialog.isShowing()){
                mDialog.dismiss();
                mDialog = null;
        	}
            progressbar.setVisibility(View.GONE);
        } else {
            if (progressbar.getVisibility() == View.GONE)
                progressbar.setVisibility(View.VISIBLE);
            progressbar.setProgress(newProgress);
//            if(newProgress<=0){
//                showRoundProcessDialog(ctx, R.layout.jrm_loading_process_dialog_icon);
//            }
        }
        super.onProgressChanged(view, newProgress);
	}

	@Override
	public void onReceivedIcon(WebView view, Bitmap icon) {
		super.onReceivedIcon(view, icon);
	}

	@Override
	public void onReceivedTitle(WebView view, String title) {
		super.onReceivedTitle(view, title);
	}

	@Override
	public void onRequestFocus(WebView view) {
		super.onRequestFocus(view);
	}

    public void showRoundProcessDialog(Context mContext, int layout)
    {
    	if(mDialog==null){
            mDialog = new AlertDialog.Builder(mContext).create();
    	}
    	if(!mDialog.isShowing()){
            mDialog.show();
            // 注意此处要放在show之后 否则会报异常
            mDialog.setContentView(layout);
    	}
    }
}

