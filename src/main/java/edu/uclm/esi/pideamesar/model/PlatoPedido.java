package edu.uclm.esi.pideamesar.model;

public class PlatoPedido {

	private String id;
	private String nombre;
	private double cantidad;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}
	
	public double getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void incrementarCantidad(double cantidad) {
		this.cantidad = this.cantidad + cantidad;
		if (this.cantidad<0)
			this.cantidad = 0;
	}

}
