package com.joaoemedeiros.onibussocial;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joaoemedeiros.onibussocial.bd.model.Localizacao;
import com.joaoemedeiros.onibussocial.bd.model.Onibus;
import com.joaoemedeiros.onibussocial.mysql.Connector;
import com.joaoemedeiros.onibussocial.mysql.ConnectorImpl;
import com.joaoemedeiros.onibussocial.mysql.OnibusDAO;

public class MapFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private GoogleMap googleMap;
    private Spinner spinner;
    private Connector connect;
    private OnibusDAO onibusDAO;
    private List<Onibus> onibus;
	private int onibus_selecionado;
    Thread threadMapa;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MapFragment newInstance(int sectionNumber) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onDestroy() {
    	threadMapa.interrupt();
    	super.onDestroy();
    }

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        spinner = (Spinner) rootView.findViewById(R.id.spinner1);
        
        connect = new ConnectorImpl();
        onibusDAO = OnibusDAO.getDAO();

        onibusDAO.populate(getActivity());
        
        /**
         * Thread para popular marcadores 
         */
        threadMapa = new Thread(new Runnable() {
			@Override
			public void run() {
				while(!Thread.currentThread().isInterrupted()) {
					
					//Para matar a thread quando o usuário fechar o app
					if(getActivity() == null)
						break;
					getActivity().runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							limparMapa();
						}
					});
					List<Localizacao> list = connect.getHTTPLocalizacao(onibus_selecionado);
					for(Localizacao loc : list) {
						String[] parts = loc.getLocalizacao().split("&");
						final double latitude = Double.parseDouble(parts[0]);
						final double longitude = Double.parseDouble(parts[1]);
						if(getActivity() == null)
							break;
						getActivity().runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								addMarcador(latitude, longitude, "44", "Santa Maria");
							}
						});
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

        carregarSpinner();
        initilizeMap();
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        carregarMarcadores();
        return rootView;
    }
    
    private void carregarSpinner() {
    	
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

	private void carregarMarcadores() {
		if(connect == null)
			connect = new ConnectorImpl();
		threadMapa.start();
	}
	
	public void limparMapa() {
		googleMap.clear();
	}
	
	public void addMarcador(double latitude, double longitude, String linha, String empresa) {
		// create marker
		MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude))
												  .title(linha)
												  .snippet(empresa)
												  .icon(BitmapDescriptorFactory.fromResource(R.drawable.onbius));
		googleMap.addMarker(marker);
	}

	private void initilizeMap() {
    	if (googleMap == null) {
            googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
	}

}
