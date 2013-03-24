package cn.woo.android.map.busmap.baidu.location;

import cn.woo.android.map.busmap.baidu.map.BusMapController;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class BusMapLocationManager {
	
	private LocationManager locationManager = null;
	
	public BusMapLocationManager(LocationManager locationManager) {
		this.locationManager = locationManager;
		this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);  
	}
	
	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) { 
			// 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
			// log it when the location changes
			if (location != null) {
				Log.i("SuperMap", "Location changed : Lat: " + location.getLatitude() + " Lng: " + location.getLongitude());
				BusMapController.getInstence().setLocation(location.getLatitude(), location.getLongitude());
			}
		}

		public void onProviderDisabled(String provider) {
			// Provider被disable时触发此函数，比如GPS被关闭
		}

		public void onProviderEnabled(String provider) {
			// Provider被enable时触发此函数，比如GPS被打开
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// Provider的转态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
		}
	};
	
	public Location getLastKnownLocation() {
		return this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
}
