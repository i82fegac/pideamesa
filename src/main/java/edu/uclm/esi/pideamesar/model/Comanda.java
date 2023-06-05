package edu.uclm.esi.pideamesar.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Comanda {
	@Id @Column(length = 36)
	private String id;
	
	@Transient
	private List<PlatoPedido> platos;

	public Comanda() {
		this.id = UUID.randomUUID().toString();
		this.platos = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PlatoPedido add(String idPlato, double cantidad) {
		PlatoPedido result = null;
		for (PlatoPedido platoPedido : this.platos) {
			if (platoPedido.getId().equals(idPlato)) {
				result = platoPedido;
				break;
			}
		}
		if (result==null) {
			result = new PlatoPedido();
			result.setId(idPlato);
			this.platos.add(result);
		}
		
		result.incrementarCantidad(cantidad);
		return result;
	}
	
	
}
