package cn.woo.android.map.busmap.activity;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import cn.woo.android.map.busmap.R;
import cn.woo.android.map.busmap.baidu.location.BusMapLocationManager;
import cn.woo.android.map.busmap.baidu.map.BusMapController;

import com.baidu.mapapi.map.MapView;

public class BusMapMainActivity extends Activity {
	private BusMapController mapController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bus_map_main);
		
		BusMapLocationManager lManager = new BusMapLocationManager((LocationManager) this.getSystemService(Context.LOCATION_SERVICE));
		Location location = lManager.getLastKnownLocation();
		double longitude = location.getLongitude(); //经度 
		double latitude = location.getLatitude(); //纬度  
		
		this.mapController = BusMapController.getInstence();
		this.mapController.setMapView((MapView) this.findViewById(R.id.bmapsView));
		this.mapController.setActivity(this);
		this.mapController.initMap();
		this.mapController.setLocation(latitude, longitude);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.bus_map_main, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		this.mapController.onDestroy();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		this.mapController.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		this.mapController.onResume();
		super.onResume();
	}

}
