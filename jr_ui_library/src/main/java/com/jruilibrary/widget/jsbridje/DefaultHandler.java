package com.jruilibrary.widget.jsbridje;

public class DefaultHandler implements BridgeHandler{

	String TAG = "DefaultHandler";
	
	@Override
	public void handler(String data, CallBackFunction function) {
		if(function != null){
			function.onCallBack("DefaultHandler response data");
		}
	}

	@Override
	public void urlHandler(String url, CallBackFunction function) {

	}

}
