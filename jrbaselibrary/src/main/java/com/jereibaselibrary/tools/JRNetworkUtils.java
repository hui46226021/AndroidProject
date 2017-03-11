package com.jereibaselibrary.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.List;

/**
 * Created by zhush on 2017/1/13
 * E-mail 405086805@qq.com
 * PS
 *
 * isNetworkAvailable 判断是否有网络
 * isWifiEnabled  判断是否是wifi
 * is3rd     判断是否是3G
 * openWifi    强制开启wifi
 * closeWifi   强制关闭wifi
 * queryWifiList 获取所搜到得wifi列表
 * queryWifiConfiguration  获取已保存得wifi列表
 * getSecurity  获取wifi加密方式  用于判断是否弹出密码框
 * getScanResultSecurity  获取扫描到wifi的加密方式
 * connectWifi   链接到制定wifi
 */

public class JRNetworkUtils {
	/**
	 * 判断是否有网络
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null && info.isConnected())
			{
				// 当前网络是连接的
				if (info.getState() == NetworkInfo.State.CONNECTED)
				{
					// 当前所连接的网络可用
					return true;
				}
			}
		}
		return false;

	}
	/**
	 * 判断wifi启用
	 * @param context
	 * @return
	 */
	public static boolean isWifiEnabled(Context context) {
		ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return ((mgrConn.getActiveNetworkInfo() != null && mgrConn
				.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
				.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
	}
	/**
	 * 判断是否是3G
	 * @param context
	 * @return
	 */
	public static boolean is3rd(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkINfo = cm.getActiveNetworkInfo();
		if (networkINfo != null
				&& networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}
	/**
	 * 判断是不是wifi
	 * @param context
	 * @return
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkINfo = cm.getActiveNetworkInfo();
		if (networkINfo != null&& networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * 强制开启wifi
	 * @param context
	 * @return
	 */
	public static void openWifi(Context context) {
		/**
		 * 获取WIFI服务
		 */
		WifiManager wifiManager=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(true);
	}


	/**
	 * 强制关闭wifi
	 * @param context
	 * @return
	 */
	public static void closeWifi(Context context) {
		/**
		 * 获取WIFI服务
		 */
		WifiManager wifiManager=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(true);
	}

	public static WifiInfo queryWifiInfo(Context context){
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiinof=wifiManager.getConnectionInfo();
		return wifiinof;
	}

	/**
	 * 获取到 搜索到得wifi列表
	 * @param context
	 * @return
     */
	public static List<ScanResult> queryWifiList(Context context){
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifiManager.startScan();
		List<ScanResult>	listb = wifiManager.getScanResults();

			//得到配置好的网络连接
		List<WifiConfiguration> 	mWifiConfiguration = wifiManager.getConfiguredNetworks();
		//数组初始化要注意
		String[] listk=new String[listb.size()];
		if(listb!=null){

			for( int i=0;i<listb.size();i++){
				ScanResult scanResult = listb.get(i);
				listk[i]=scanResult.SSID;

				Log.i("wifi",listk[i]);
			}
		}

		if(mWifiConfiguration!=null){

			for( int i=0;i<mWifiConfiguration.size();i++){
				WifiConfiguration wifiConfiguration = mWifiConfiguration.get(i);


				Log.i("wifi",wifiConfiguration.SSID);
			}
		}
		return listb;
	}

	/**
	 * 获取到 已经保存得wifi列表
	 * @param context
	 * @return
	 */
	public static List<WifiConfiguration>  queryWifiConfiguration(Context context){
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

		//得到配置好的网络连接
		List<WifiConfiguration> 	mWifiConfiguration = wifiManager.getConfiguredNetworks();


		if(mWifiConfiguration!=null){

			for( int i=0;i<mWifiConfiguration.size();i++){
				WifiConfiguration wifiConfiguration = mWifiConfiguration.get(i);


				Log.i("wifi",wifiConfiguration.SSID);
			}
		}
		return mWifiConfiguration;
	}

	enum WifiEncType {
		WEP, WPA, OPEN
	}

	/**
	 * 获取 已保存的wifi的加密方式
	 * @param config
	 * @return
     */
	public static WifiEncType getSecurity(WifiConfiguration config) {
		if (config.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.WPA_PSK)) {
			return WifiEncType.WPA;
		}
		if (config.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.WPA_EAP) || config.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.IEEE8021X)) {
			return WifiEncType.WPA;
		}
		return (config.wepKeys[0] != null) ? WifiEncType.WEP : WifiEncType.OPEN;
	}

	/**
	 * 获取 扫描到wifi的 加密方式
	 * @param scanResult
	 * @return
     */
	public static WifiEncType getScanResultSecurity(ScanResult scanResult) {

		if (scanResult.capabilities.toLowerCase().indexOf("wep")!=-1) {
			return WifiEncType.WEP;
		}
		if (scanResult.capabilities.toLowerCase().indexOf("wpa")!=-1) {
			return WifiEncType.WEP;
		}
		return  WifiEncType.OPEN;
	}


	/**
	 * 自定链接到哪个wifi
	 * @param context
	 * @param targetSsid
	 * @param targetPsd
     * @param enc
     */
	public void connectWifi(Context context,String targetSsid, String targetPsd, WifiEncType enc) {
		// 1、注意热点和密码均包含引号，此处需要需要转义引号
		String ssid = "\"" + targetSsid + "\"";
		String psd = "\"" + targetPsd + "\"";

		//2、配置wifi信息
		WifiConfiguration conf = new WifiConfiguration();
		conf.SSID = ssid;
		switch (enc) {
			case WEP:
				// 加密类型为WEP
				conf.wepKeys[0] = psd;
				conf.wepTxKeyIndex = 0;
				conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
				conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
				break;
			case WPA:
				// 加密类型为WPA
				conf.preSharedKey = psd;
				break;
			case OPEN:
				//开放网络
				conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		}

		//3、链接wifi
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifiManager.addNetwork(conf);
		List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
		for (WifiConfiguration i : list) {
			if (i.SSID != null && i.SSID.equals(ssid)) {
				wifiManager.disconnect();
				wifiManager.enableNetwork(i.networkId, true);
				wifiManager.reconnect();
				break;
			}
		}
	}
}
