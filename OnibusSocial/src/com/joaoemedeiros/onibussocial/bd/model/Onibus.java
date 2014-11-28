/*
 * @author João Eduardo Medeiros
 * 
 */
package com.joaoemedeiros.onibussocial.bd.model;

/**
 * The Class Onibus.
 */
public class Onibus {
	
	/** Id do onibus. */
	private int id;
	
	/** The linha. */
	private String linha;
	
	/** The itinerario. */
	private String itinerario;
	
	/** The nome empresa. */
	private String nomeEmpresa;
	
	/** The cidade empresa. */
	private String cidadeEmpresa;
	
	/** The estado empresa. */
	private String estadoEmpresa;
	
	/**
	 * Instantiates a new onibus.
	 */
	public Onibus() {

	}
	 
	/**
	 * Instantiates a new onibus.
	 *
	 * @param id the id
	 * @param linha the linha
	 * @param itinerario the itinerario
	 * @param nomeEmpresa the nome empresa
	 * @param cidadeEmpresa the cidade empresa
	 * @param estadoEmpresa the estado empresa
	 */
	public Onibus(int id, String linha, String itinerario, String nomeEmpresa, String cidadeEmpresa, String estadoEmpresa) {
		this.id = id;
		this.linha = linha;
		this.itinerario = itinerario;
		this.nomeEmpresa = nomeEmpresa;
		this.cidadeEmpresa = cidadeEmpresa;
		this.estadoEmpresa = estadoEmpresa;
	}
	
	/**
	 * Instantiates a new onibus.
	 *
	 * @param linha the linha
	 * @param itinerario the itinerario
	 * @param nomeEmpresa the nome empresa
	 * @param cidadeEmpresa the cidade empresa
	 * @param estadoEmpresa the estado empresa
	 */
	public Onibus(String linha, String itinerario, String nomeEmpresa, String cidadeEmpresa, String estadoEmpresa) {
		this.linha = linha;
		this.itinerario = itinerario;
		this.nomeEmpresa = nomeEmpresa;
		this.cidadeEmpresa = cidadeEmpresa;
		this.estadoEmpresa = estadoEmpresa;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the linha.
	 *
	 * @return the linha
	 */
	public String getLinha() {
		return linha;
	}

	/**
	 * Sets the linha.
	 *
	 * @param linha the new linha
	 */
	public void setLinha(String linha) {
		this.linha = linha;
	}

	/**
	 * Gets the itinerario.
	 *
	 * @return the itinerario
	 */
	public String getItinerario() {
		return itinerario;
	}

	/**
	 * Sets the itinerario.
	 *
	 * @param itinerario the new itinerario
	 */
	public void setItinerario(String itinerario) {
		this.itinerario = itinerario;
	}

	/**
	 * Gets the nome empresa.
	 *
	 * @return the nome empresa
	 */
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	/**
	 * Sets the nome empresa.
	 *
	 * @param nomeEmpresa the new nome empresa
	 */
	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	/**
	 * Gets the cidade empresa.
	 *
	 * @return the cidade empresa
	 */
	public String getCidadeEmpresa() {
		return cidadeEmpresa;
	}

	/**
	 * Sets the cidade empresa.
	 *
	 * @param cidadeEmpresa the new cidade empresa
	 */
	public void setCidadeEmpresa(String cidadeEmpresa) {
		this.cidadeEmpresa = cidadeEmpresa;
	}

	/**
	 * Gets the estado empresa.
	 *
	 * @return the estado empresa
	 */
	public String getEstadoEmpresa() {
		return estadoEmpresa;
	}

	/**
	 * Sets the estado empresa.
	 *
	 * @param estadoEmpresa the new estado empresa
	 */
	public void setEstadoEmpresa(String estadoEmpresa) {
		this.estadoEmpresa = estadoEmpresa;
	}
	
}
