package com.ecommerce.model;

import java.util.Date;

public class Orden {
	private Integer id;
	private String numero;
	private Date fechaCreacion;
	private Date fechaRecibida;
	
	private double total;
	
	public Orden() {
		// TODO Auto-generated constructor stub
	}

	public Orden(Integer id, String numero, Date fechaCreacion, Date fechaRecibida, double total) {
		super();
		this.id = id;
		this.numero = numero;
		this.fechaCreacion = fechaCreacion;
		this.fechaRecibida = fechaRecibida;
		this.total = total;
	}

	@Override
	public String toString() {
		return "Orden [id=" + id + ", numero=" + numero + ", fechaCreacion=" + fechaCreacion + ", fechaRecibida="
				+ fechaRecibida + ", total=" + total + "]";
	}
	
	
	

}
