package com.joaoemedeiros.onibussocial.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;

public class GPSTracker extends Service implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener,
		com.google.android.gms.location.LocationListener {
	
	private static final LocationRequest REQUEST = LocationRequest.create()
			.setInterval(0).setFastestInterval(0)
			.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
	public static final String LOCATION_RECEIVED = "fused.location.received";
	private Long now;
	private LocationClient mLocationClient;
	private final Object locking = new Object();
	private Runnable onFusedLocationProviderTimeout;
	private Handler handler = new Handler();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		now = Long.valueOf(System.currentTimeMillis());
		mLocationClient = new LocationClient(this, this, this);
		mLocationClient.connect();
		return START_STICKY;
	}

	@Override
	public void onConnected(Bundle bundle) {
		Log.d("Location",
				"Fused Location Provider got connected successfully");
		mLocationClient.requestLocationUpdates(REQUEST, this);
		onFusedLocationProviderTimeout = new Runnable() {
			public void run() {
				Log.d("Location", "location Timeout");
				Location lastbestStaleLocation = getLastBestStaleLocation();
				sendLocationUsingBroadCast(lastbestStaleLocation);
				if (lastbestStaleLocation != null)
					Log.d("Location",
							"Last best location returned ["
									+ lastbestStaleLocation.getLatitude()
									+ ","
									+ lastbestStaleLocation.getLongitude()
									+ "] in "
									+ (Long.valueOf(System.currentTimeMillis()) - now)
									+ " ms");
				if (mLocationClient.isConnected())
					mLocationClient.disconnect();
			}
		};
		handler.postDelayed(onFusedLocationProviderTimeout, 20000);// 20 sec
	}

	private void sendLocationUsingBroadCast(Location location) {
		Intent locationBroadcast = new Intent(
				GPSTracker.LOCATION_RECEIVED);
		locationBroadcast.putExtra("LOCATION", location);
		locationBroadcast.putExtra("TIME",
				Long.valueOf(System.currentTimeMillis() - now) + " ms");
		LocalBroadcastManager.getInstance(this)
				.sendBroadcast(locationBroadcast);
		if(System.currentTimeMillis() - now >= 3600000) {
			stopSelf();
		}
	}

	@Override
	public void onDisconnected() {
		Log.d("Location",
				"Fused Location Provider got disconnected successfully");
		stopSelf();
	}

	@Override
	public void onLocationChanged(Location location) {
		synchronized (locking) {
			Log.d("Location", "Location received successfully ["
					+ location.getLatitude() + "," + location.getLongitude()
					+ "] in "
					+ (Long.valueOf(System.currentTimeMillis() - now)) + " ms");
			handler.removeCallbacks(onFusedLocationProviderTimeout);
//			if (mLocationClient.isConnected())
//				mLocationClient.removeLocationUpdates(this);
			sendLocationUsingBroadCast(location);
//			if (mLocationClient.isConnected())
//				mLocationClient.disconnect();
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Log.d("Location",
				"Error connecting to Fused Location Provider");
	}
	
	public Location getLastBestStaleLocation() {
		Location bestResult = null;
		LocationManager locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location lastFusedLocation = mLocationClient.getLastLocation();
		Location gpsLocation = locMgr
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		Location networkLocation = locMgr
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (gpsLocation != null && networkLocation != null) {
			if (gpsLocation.getTime() > networkLocation.getTime())
				bestResult = gpsLocation;
		} else if (gpsLocation != null) {
			bestResult = gpsLocation;
		} else if (networkLocation != null) {
			bestResult = networkLocation;
		}
		// take Fused Location in to consideration while checking for last stale
		// location
		if (bestResult != null && lastFusedLocation != null) {
			if (bestResult.getTime() < lastFusedLocation.getTime())
				bestResult = lastFusedLocation;
		}
		return bestResult;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onDestroy() {
		//TODO: Remover antes de mandar para Google Play
		Toast.makeText(getApplicationContext(), "Serviço Finalizado!", Toast.LENGTH_LONG).show();
		handler.removeCallbacks(onFusedLocationProviderTimeout);
		mLocationClient.disconnect();
	};
}