package com.jruilibarary.widget.jsbridje;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.ProgressBar;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("SetJavaScriptEnabled")
public class BridgeWebView extends WebView implements WebViewJavascriptBridge {

	private final String TAG = "BridgeWebView";

	public static final String toLoadJs = "WebViewJavascriptBridge.js";
	Map<String, CallBackFunction> responseCallbacks = new HashMap<String, CallBackFunction>();
	Map<String, BridgeHandler> messageHandlers = new HashMap<String, BridgeHandler>();
	BridgeHandler defaultHandler = new DefaultHandler();

	private List<Message> startupMessage = new ArrayList<Message>();

	public List<Message> getStartupMessage() {
		return startupMessage;
	}

	public void setStartupMessage(List<Message> startupMessage) {
		this.startupMessage = startupMessage;
	}

	private long uniqueId = 0;

	public BridgeWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();

	}

	public BridgeWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();

	}

	public BridgeWebView(Context context) {
		super(context);
		init();

	}
	ProgressBar progressbar;
	public void addProgressBar(Context context){
		progressbar = new ProgressBar(context, null,
				android.R.attr.progressBarStyleHorizontal);
		progressbar.setLayoutParams(new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.FILL_PARENT,
				10, 0, 0));
		addView(progressbar);


	}

	/**
	 * 
	 * @param handler
	 *            default handler,handle messages send by js without assigned handler name,
     *            if js message has handler name, it will be handled by named handlers registered by native
	 */
	public void setDefaultHandler(BridgeHandler handler) {
       this.defaultHandler = handler;
	}

    private void init() {
		this.setVerticalScrollBarEnabled(false);
		this.setHorizontalScrollBarEnabled(false);
		this.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
		this.setWebViewClient(generateBridgeWebViewClient());
		//设置 进度条
		addProgressBar(getContext());
		//屏蔽alert 设置进度条进度
		this.setWebChromeClient(new JrWebChromeClient(getContext(), progressbar));
		//设置 web缓存
		setCache();
	}

    protected BridgeWebViewClient generateBridgeWebViewClient() {
        return new BridgeWebViewClient(this);
    }

	void handlerReturnData(String url) {
		String functionName = BridgeUtil.getFunctionFromReturnUrl(url);
		CallBackFunction f = responseCallbacks.get(functionName);
		String data = BridgeUtil.getDataFromReturnUrl(url);
		if (f != null) {
			f.onCallBack(data);
			responseCallbacks.remove(functionName);
			return;
		}
	}

	@Override
	public void send(String data) {
		send(data, null);
	}

	@Override
	public void send(String data, CallBackFunction responseCallback) {
		doSend(null, data, responseCallback);
	}

	private void doSend(String handlerName, String data, CallBackFunction responseCallback) {
		Message m = new Message();
		if (!TextUtils.isEmpty(data)) {
			m.setData(data);
		}
		if (responseCallback != null) {
			String callbackStr = String.format(BridgeUtil.CALLBACK_ID_FORMAT, ++uniqueId + (BridgeUtil.UNDERLINE_STR + SystemClock.currentThreadTimeMillis()));
			responseCallbacks.put(callbackStr, responseCallback);
			m.setCallbackId(callbackStr);
		}
		if (!TextUtils.isEmpty(handlerName)) {
			m.setHandlerName(handlerName);
		}
		queueMessage(m);
	}

	private void queueMessage(Message m) {
		if (startupMessage != null) {
			startupMessage.add(m);
		} else {
			dispatchMessage(m);
		}
	}

	void dispatchMessage(Message m) {
        String messageJson = m.toJson();
        //escape special characters for json string
        messageJson = messageJson.replaceAll("(\\\\)([^utrn])", "\\\\\\\\$1$2");
        messageJson = messageJson.replaceAll("(?<=[^\\\\])(\")", "\\\\\"");
        String javascriptCommand = String.format(BridgeUtil.JS_HANDLE_MESSAGE_FROM_JAVA, messageJson);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            this.loadUrl(javascriptCommand);
        }
    }

	void flushMessageQueue() {
		if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
			loadUrl(BridgeUtil.JS_FETCH_QUEUE_FROM_JAVA, new CallBackFunction() {

				@Override
				public void onCallBack(String data) {
					// deserializeMessage
					List<Message> list = null;
					try {
						list = Message.toArrayList(data);
					} catch (Exception e) {
                        e.printStackTrace();
						return;
					}
					if (list == null || list.size() == 0) {
						return;
					}
					for (int i = 0; i < list.size(); i++) {
						Message m = list.get(i);
						String responseId = m.getResponseId();
						// 是否是response
						if (!TextUtils.isEmpty(responseId)) {
							CallBackFunction function = responseCallbacks.get(responseId);
							String responseData = m.getResponseData();
							function.onCallBack(responseData);
							responseCallbacks.remove(responseId);
						} else {
							CallBackFunction responseFunction = null;
							// if had callbackId
							final String callbackId = m.getCallbackId();
							if (!TextUtils.isEmpty(callbackId)) {
								responseFunction = new CallBackFunction() {
									@Override
									public void onCallBack(String data) {
										Message responseMsg = new Message();
										responseMsg.setResponseId(callbackId);
										responseMsg.setResponseData(data);
										queueMessage(responseMsg);
									}
								};
							} else {
								responseFunction = new CallBackFunction() {
									@Override
									public void onCallBack(String data) {
										// do nothing
									}
								};
							}
							BridgeHandler handler;
							if (!TextUtils.isEmpty(m.getHandlerName())) {
								handler = messageHandlers.get(m.getHandlerName());
							} else {
								handler = defaultHandler;
							}
							if (handler != null){
								handler.handler(m.getData(), responseFunction);
							}
						}
					}
				}
			});
		}
	}

	public void notifyUrl(String url){
		BridgeHandler handler;
		handler = defaultHandler;
		if (handler != null){
			handler.urlHandler(url, null);
		}
	}


	public void loadUrl(String jsUrl, CallBackFunction returnCallback) {
		this.loadUrl(jsUrl);
		responseCallbacks.put(BridgeUtil.parseFunctionName(jsUrl), returnCallback);
	}

	/**
	 * register handler,so that javascript can call it
	 * 
	 * @param handlerName
	 * @param handler
	 */
	public void registerHandler(String handlerName, BridgeHandler handler) {
		if (handler != null) {
			messageHandlers.put(handlerName, handler);
		}
	}

	/**
	 * call javascript registered handler
	 *
     * @param handlerName
	 * @param data
	 * @param callBack
	 */
	public void callHandler(String handlerName, String data, CallBackFunction callBack) {
        doSend(handlerName, data, callBack);
	}

	public void setCache(){
		//缓存
		this.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
		if (!isNetworkAvailable(getContext())) {
			getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		} else {
			getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
			clearWebViewCache();
		}
		String cacheDirPath = getContext().getFilesDir().getAbsolutePath() + "/sevalo_cache";
		//������ݿ⻺��·��
		getSettings().setDatabasePath(cacheDirPath);
		//����  Application Caches ����Ŀ¼
		getSettings().setAppCachePath(cacheDirPath);
		//���� Application Caches ����
		getSettings().setAppCacheEnabled(true);
		// ���� DOM storage API ����
		getSettings().setDomStorageEnabled(true);
		//���� database storage API ����
		getSettings().setDatabaseEnabled(true);
		getSettings().setAllowFileAccess(true);
	}

	/**
	 * 判断网络状态  （为了不 过多依赖 所以这里没有用到 jrbaselibray里的 工具类 又单独写了个方法）
	 * @param context
	 * @return
     */
	public  boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
		} else {
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * �清除WebView缓存
	 */
	public void clearWebViewCache() {
		// 清理Webview缓存数据库
		try {
			getContext().deleteDatabase("webview.db");
			getContext().deleteDatabase("webviewCache.db");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// WebView 缓存文件
		File appCacheDir = new File(getContext().getApplicationContext().getFilesDir()
				.getAbsolutePath()
				+ "/sevalo_cache");
		File webviewCacheDir = new File(getContext().getApplicationContext()
				.getFilesDir().getAbsolutePath()
				+ "/webviewCache");
		// 删除webview 缓存目录
		if (webviewCacheDir.exists()) {
			deleteFile(webviewCacheDir);
		}
		// 删除webview 缓存 缓存目录
		if (appCacheDir.exists()) {
			deleteFile(appCacheDir);
		}
	}

	/**
	 * 递归删除 文件/文件夹
	 *
	 * @param file
	 */
	public void deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
			file.delete();
		} else {
			Log.e("jrerror", "delete file no exists " + file.getAbsolutePath());
		}
	}
}
