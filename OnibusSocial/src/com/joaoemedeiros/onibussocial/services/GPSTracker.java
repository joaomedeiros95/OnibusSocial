package com.joaoemedeiros.onibussocial.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.joaoemedeiros.onibussocial.R;
import com.joaoemedeiros.onibussocial.exceptions.UnconnectedException;
import com.joaoemedeiros.onibussocial.mysql.ConnectorImpl;

public class GPSTracker extends Service {
	
	private static final long MIN_TIME_MILLIS = 2000;
	private static final long MIN_DIST_METERS = 10;
	private static final float MIN_ACURRACY_METERS = 35;
	private static int onibus = 0;
	private static int id = 0;
	
	public static final String LOCATION_RECEIVED = "fused.location.received";
	
	private LocationManager lm;
	private LocationListener locationListener;
	private NotificationManager nNM;
	private int lastStatus = 0;

	public void startLoggerService() {
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new MyLocationListener();
		
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_MILLIS, MIN_DIST_METERS, locationListener);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_MILLIS, MIN_DIST_METERS, locationListener);
	}
	
	public void shutdownLoggerService() {
		lm.removeUpdates(locationListener);
	}
	
	public class MyLocationListener implements LocationListener {
		
		private ConnectorImpl connector = new ConnectorImpl();
		
		@Override
		public void onLocationChanged(Location location) {
			if(location != null) {
				boolean pontoEnviado = false;
				if(location.hasAccuracy() && location.getAccuracy() <= MIN_ACURRACY_METERS) {
					pontoEnviado = true;
					try {
						connector.setLocationTracker(location.getLatitude(), location.getLongitude(), onibus, id);
					} catch (UnconnectedException e) {
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "OnibusSocial: Verifique sua conexão com Internet", Toast.LENGTH_LONG).show();
					}
				}
				if(pontoEnviado) {
					Log.d("Location Received", "lat :" + location.getLatitude() + ",lng :" + location.getLongitude());
				} else {
					Log.d("Location Not Received", "localização não recebida");
				}
			}
		}
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			String showStatus = null;
			if (status == LocationProvider.AVAILABLE)
				showStatus = "Available";
			if (status == LocationProvider.TEMPORARILY_UNAVAILABLE)
				showStatus = "Temporarily Unavailable";
			if (status == LocationProvider.OUT_OF_SERVICE)
				showStatus = "Out of Service";
			if (status != lastStatus) {
				Log.d("Novo Status", showStatus);
			}
			lastStatus = status;
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			Log.d("Provider", "Disabled");
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			Log.d("Provider", "Enabled");
		}
		
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		nNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		startLoggerService();
		showNotification();
	}

	@SuppressWarnings("deprecation")
	private void showNotification() {
		CharSequence text = "OnibusSocial está coletando seus dados de GPS.";
		Notification notificacao = new Notification.Builder(this).setSmallIcon(R.drawable.onbius)
				                                                 .setContentTitle("GPSTracker")
				                                                 .setContentText(text)
				                                                 .getNotification();
		notificacao.flags |= Notification.FLAG_NO_CLEAR;
				
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, GPSTracker.class)
																, 0);
		notificacao.setLatestEventInfo(this, "GPSTracker", text, contentIntent);
		nNM.notify("OnibusSocial está coletando seus dados de GPS.", 1, notificacao);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onDestroy() {
		Toast.makeText(getApplicationContext(), "Serviço Finalizado!", Toast.LENGTH_LONG).show();
		shutdownLoggerService();
		nNM.cancel("OnibusSocial está coletando seus dados de GPS.", 1);
	};
	
	public static void setOnibus(int onibus) {
		GPSTracker.onibus = onibus;
	}

	public static void setId(int id) {
		GPSTracker.id = id;
	}
}
	
