package com.joaoemedeiros.onibussocial.mysql;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.joaoemedeiros.onibussocial.bd.model.Localizacao;
import com.joaoemedeiros.onibussocial.internet.HttpConnectionOnibusBD;
import com.joaoemedeiros.onibussocial.internet.JSONConstantes;

/**
 * The Class ConnectorImpl.
 */
public class ConnectorImpl implements Connector {
	
	public int getUniqueID() {
		HttpConnectionOnibusBD httpcobd = new HttpConnectionOnibusBD();
		return httpcobd.getUniqueId();
	}
	
	public List<Localizacao> getHTTPLocalizacao(int id_onibus) {
		HttpConnectionOnibusBD httpcobd = new HttpConnectionOnibusBD();
		String resultadoJSON = httpcobd.getLocalizacao(id_onibus);
		JSONArray localizacao = new JSONArray();
		List<Localizacao> localizacaoRetorno = new ArrayList<Localizacao>();
		
		if(resultadoJSON != null) {
			JSONObject jsonObj;
			try {
				jsonObj = new JSONObject(resultadoJSON);
				localizacao = jsonObj.getJSONArray(JSONConstantes.TAG_PRODUCT);
				
				for (int i = 0; i < localizacao.length(); i++) {
					JSONObject c = localizacao.getJSONObject(i);
					
					Localizacao loc = new Localizacao();
					loc.setId(c.getInt(JSONConstantes.TAG_ID));
					loc.setLocalizacao(c.getString(JSONConstantes.TAG_LOCALIZACAO));
					loc.setUltima_atualizacao(c.getString(JSONConstantes.TAG_ULTIMA_ATUALIZACAO));
					loc.setId_onibus(c.getInt(JSONConstantes.TAG_ONIBUS));
					
					localizacaoRetorno.add(loc);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return localizacaoRetorno;
	}
	
	/* (non-Javadoc)
	 * @see com.joaoemedeiros.onibussocial.mysql.Connector#setLocationTracker(android.content.Context, double, double, int)
	 */
	public void setLocationTracker(double latitude, double longitude, int id_onibus, int id) {
		Log.d("Saving Location", "Salvando localização atual" + latitude + ", " + longitude);
		HttpConnectionOnibusBD httpcpl = new HttpConnectionOnibusBD();
		httpcpl.postLocalizacao(id, id_onibus, latitude, longitude);
	}
	
}
