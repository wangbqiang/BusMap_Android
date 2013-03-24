package cn.woo.android.map.busmap;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

public class MapApplication extends Application {

	private static MapApplication mInstance = null;
	
	public boolean mbKeyRight = true;
	
	private BMapManager mbMapManager = null;
	
	public static final String strKey = "4F7D7ACB99CF0526AA72F266DB7F772A8AEFA595";//
	
	public static MapApplication getInstance() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		this.initEngineManager(this);
	}
	
	@Override
	// 建议在您app的退出之前调用mapadpi的destroy()函数，避免重复初始化带来的时间消耗
	public void onTerminate() {
		if (this.mbMapManager != null) {
			this.mbMapManager.destroy();
			this.mbMapManager = null;
		}
		super.onTerminate();
	}

	public void initEngineManager(Context context) {
		if (this.mbMapManager == null) {
			this.mbMapManager = new BMapManager(context);
			
		}

		if (!this.mbMapManager.init(strKey, new MyGeneralListener())) {
			Toast.makeText(
					MapApplication.getInstance().getApplicationContext(),
					"BMapManager  初始化错误!", 
					Toast.LENGTH_LONG
			).show();
		} else {
//			Toast.makeText(
//					MapApplication.getInstance().getApplicationContext(),
//					"BMapManager!", 
//					Toast.LENGTH_LONG
//			).show();
			this.mbMapManager.start();
		}
	}
	
	public BMapManager getMbMapManager() {
		return mbMapManager;
	}

	public void setMbMapManager(BMapManager mbMapManager) {
		this.mbMapManager = mbMapManager;
	}



	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	public static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(
						MapApplication.getInstance().getApplicationContext(),
						"您的网络出错啦！", 
						Toast.LENGTH_LONG
				).show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(
						MapApplication.getInstance().getApplicationContext(),
						"输入正确的检索条件！", 
						Toast.LENGTH_LONG
				).show();
			}
		}
	
		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Toast.makeText(
						MapApplication.getInstance().getApplicationContext(),
						"请在DemoApplication.java文件输入正确的授权Key！",
						Toast.LENGTH_LONG
				).show();
				MapApplication.getInstance().mbKeyRight = false;
				System.out.println("请在DemoApplication.java文件输入正确的授权Key！");
			}
		}
	}
}
