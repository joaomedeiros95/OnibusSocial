package com.joaoemedeiros.onibussocial;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.joaoemedeiros.onibussocial.bd.model.Onibus;
import com.joaoemedeiros.onibussocial.exceptions.UnconnectedException;
import com.joaoemedeiros.onibussocial.mysql.ConnectorImpl;
import com.joaoemedeiros.onibussocial.mysql.OnibusDAO;
import com.joaoemedeiros.onibussocial.services.GPSTracker;

/**
 * A placeholder fragment containing a simple view.
 */
public class FollowFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static final String PREFS_NAME = "OnibusSocialPrefs";
    private Spinner spinner;
    private Button button;
    private ConnectorImpl connector;
    private OnibusDAO onibusDAO;
    private List<Onibus> onibus;
	private int onibus_selecionado = 0;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FollowFragment newInstance(int sectionNumber) {
        FollowFragment fragment = new FollowFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FollowFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_follow, container, false);
        spinner = (Spinner) rootView.findViewById(R.id.spinner1);
        connector = new ConnectorImpl();
        onibusDAO = OnibusDAO.getDAO();
        carregarSpinner();
        
        
        button = (Button) rootView.findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isMyServiceRunning(GPSTracker.class)) {
					if(onibus_selecionado > 0) {
						LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
						if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
							SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
							if(settings.getInt("uniqueID", 0) == 0) {
								SharedPreferences.Editor editor = settings.edit();
								try {
									editor.putInt("uniqueID", connector.getUniqueID());
								} catch (UnconnectedException e) {
									e.printStackTrace();
									Toast.makeText(getActivity(), "OnibusSocial: Verifique sua conexão com Internet", Toast.LENGTH_LONG).show();
									MainActivity.isConnected();
								}
								editor.commit();
							}
							final int uniqueID = settings.getInt("uniqueID", 0);
							GPSTracker.setId(uniqueID);
							GPSTracker.setOnibus(onibus_selecionado);
							getActivity().startService(new Intent(getActivity(), GPSTracker.class));
							button.setText("Parar");
						} else {
							AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
							builder.setMessage("Você precisa estar com o GPS ativado para realizar essa função, deseja habilitá-lo agora?")
							       .setCancelable(false)
							       .setTitle("GPS Desativado")
							       .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
										}
							       })
							       .setNegativeButton("Não", null);
							builder.create().show();
						}
					} else {
						Toast.makeText(getActivity(), "Selecione um ônibus primeiro!", Toast.LENGTH_LONG).show();
					}
				} else {
					getActivity().stopService(new Intent(getActivity(), GPSTracker.class));
					button.setText("Iniciar");
				}
			}
		});
        
        return rootView;
    }
    
    /**
     * Checa se um servi�o est� rodando no Android.
     * @param serviceClass
     * @return
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    
    private void carregarSpinner() {
    	if(connector == null) {
    		connector = new ConnectorImpl();
    	}
    	List<String> list = new ArrayList<String>();
    	onibus = onibusDAO.getAllOnibus(getActivity());
    	
    	list.add("Selecione um Ônibus");
    	for(Onibus bus : onibus) {
    		list.add(bus.getLinha() + "/" + bus.getNomeEmpresa());
    	}
    	
    	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
    	
    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(dataAdapter);
    	 
    	spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(arg2 > 0) {
					Onibus bus = onibus.get(arg2 - 1);
					onibus_selecionado = bus.getId(); 
				} else {
					onibus_selecionado = 0;
				}
			}
	
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
					
			}
    		 
    	});
	}
}
