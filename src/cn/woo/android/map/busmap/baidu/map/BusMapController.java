package cn.woo.android.map.busmap.baidu.map;

import android.app.Activity;
import android.widget.Toast;
import cn.woo.android.map.busmap.MapApplication;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class BusMapController {
	private MapView mapView = null;
	private MapController mapController = null;
	
	private Activity activity = null;
	
	private static BusMapController ins = null;
	public static BusMapController getInstence() {
		if (ins == null) {
			ins = new BusMapController();
		}
		return ins;
	}
	
	private BusMapController() {
		
	}
	
	private MKMapViewListener mapListener = new MKMapViewListener() {
		@Override
		public void onMapMoveFinish() {
		}

		@Override
		public void onClickMapPoi(MapPoi mapPoiInfo) {
			String title = "";
			if (mapPoiInfo != null) {
				title = mapPoiInfo.strText;
				Toast.makeText(
						BusMapController.this.activity, 
						title, 
						Toast.LENGTH_SHORT
				).show();
				BusMapController.this.mapController.animateTo(mapPoiInfo.geoPt);
			}
		}
	};
	
	public MapView getMapView() {
		return mapView;
	}

	public void setMapView(MapView mapView) {
		this.mapView = mapView;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public void initMap() {
		this.initMapManager();
		this.initMapView();
		this.initMapController();
	}
	
	public void setLocation(double latitude, double longitude) {
		GeoPoint point = new GeoPoint((int) (latitude * 1E6), (int) (longitude * 1E6));
		this.mapController.setCenter(point); //设置地图中心点
		this.mapController.setZoom(12); //设置地图zoom级别
	}
	
	private void initMapManager() {
		MapApplication app = (MapApplication) this.activity.getApplication();
		if (app.getMbMapManager() == null) {
			app.setMbMapManager(new BMapManager(this.activity));
			app.getMbMapManager().init(MapApplication.strKey, new MapApplication.MyGeneralListener());
		}
	}
	
	private void initMapView() {
		this.mapView.setBuiltInZoomControls(true); //设置启用内置的缩放控件
		this.mapView.setDoubleClickZooming(true);
		this.mapView.setOnTouchListener(null);
		
		this.mapView.regMapViewListener(MapApplication.getInstance().getMbMapManager(), this.mapListener);
	}
	
	private void initMapController() {
		this.mapController = this.mapView.getController();
		this.mapController.enableClick(true);
	}
	
	public void onDestroy() {
		this.mapView.destroy();
		if (MapApplication.getInstance().getMbMapManager() != null) {
			MapApplication.getInstance().getMbMapManager().destroy();
			MapApplication.getInstance().setMbMapManager(null);
		}
	}

	public void onPause() {
		this.mapView.onPause();
		if (MapApplication.getInstance().getMbMapManager() != null) {
			MapApplication.getInstance().getMbMapManager().stop();
		}
	}
	
	public void onResume() {
		this.mapView.onResume();
		if (MapApplication.getInstance().getMbMapManager() != null) {
			MapApplication.getInstance().getMbMapManager().start();
		}
	}
}
