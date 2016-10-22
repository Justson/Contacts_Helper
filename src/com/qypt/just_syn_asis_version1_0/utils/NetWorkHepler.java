package com.qypt.just_syn_asis_version1_0.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * 
 * @author Administrator justson cenxiaozhong 
 *
 */
public class NetWorkHepler {

	/**
	 * 判断网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean netWorkIsReady(Context context)
	{
		ConnectivityManager mConnectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netWorkInfo=mConnectivityManager.getAllNetworkInfo();
		if(netWorkInfo==null)
			return false;
		for(int i=0;i<netWorkInfo.length;i++)
		{
			if(netWorkInfo[i].getState()==NetworkInfo.State.CONNECTED)
			{
				return true;
			}
		}
		return false;
	}
	
	
}
