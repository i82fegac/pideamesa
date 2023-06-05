package edu.uclm.esi.pideamesar.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Plato {
	@Id
	private String id;
	@ManyToOne
	private Bar bar;
	private String nombre;
	private double pvp;
	
	
	public Plato() {
		this.id = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	/*public Bar getRestaurante() {
		return bar;
	}
	public void setRestaurante(Bar bar) {
		this.bar = bar;
	}*/
	
	public void setBar(Bar bar) {
		this.bar = bar;
	}
	
	public Bar getBar() {
		return bar;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getPvp() {
		return pvp;
	}
	public void setPvp(double pvp) {
		this.pvp = pvp;
	}
	
	
}
