package com.joaoemedeiros.onibussocial.mysql;

import java.util.List;

import com.joaoemedeiros.onibussocial.bd.model.Localizacao;
import com.joaoemedeiros.onibussocial.exceptions.UnconnectedException;

/**
 * The Interface Connector.
 */
public interface Connector {
	
	
	
	/**
	 * Sets the location tracker.
	 *
	 * @param context the context
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param id_onibus the id_onibus
	 * @throws UnconnectedException 
	 */
	void setLocationTracker(double latitude, double longitude, int id_onibus, int id) throws UnconnectedException;
	
	/**
	 * Pega as localiza��es do banco.
	 * @param id_onibus
	 * @return
	 * @throws UnconnectedException 
	 */
	List<Localizacao> getHTTPLocalizacao(int id_onibus) throws UnconnectedException;
	
	/**
	 * Pega um ID único para ser usado pelo usuário ao publicar informações.
	 * @return
	 * @throws UnconnectedException 
	 */
	public int getUniqueID() throws UnconnectedException;
	
}
