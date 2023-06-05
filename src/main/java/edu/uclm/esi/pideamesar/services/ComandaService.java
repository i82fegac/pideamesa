package edu.uclm.esi.pideamesar.services;

import org.springframework.stereotype.Service;

import edu.uclm.esi.pideamesar.model.Comanda;
import edu.uclm.esi.pideamesar.model.PlatoPedido;

@Service
public class ComandaService {

	public PlatoPedido add(Comanda comanda, String idPlato, double cantidad) {
		return comanda.add(idPlato, cantidad);
	}

}
