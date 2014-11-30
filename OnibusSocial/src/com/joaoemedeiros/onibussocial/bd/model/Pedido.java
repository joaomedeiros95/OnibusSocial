package com.joaoemedeiros.onibussocial.bd.model;

import java.util.Date;

public class Pedido {
	
	private int id;
	private String nome;
	private String email;
	private String cidade;
	private String estado;
	private Date data_cadastro;
	
	public Pedido(String nome, String email, String cidade, String estado) {
		super();
		this.nome = nome;
		this.email = email;
		this.cidade = cidade;
		this.estado = estado;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getData_cadastro() {
		return data_cadastro;
	}
	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}
	
}
