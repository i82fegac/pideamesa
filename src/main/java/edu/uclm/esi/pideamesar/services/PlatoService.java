package edu.uclm.esi.pideamesar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.pideamesar.dao.PlatoRepository;
import edu.uclm.esi.pideamesar.http.Manager;
import edu.uclm.esi.pideamesar.model.Bar;
import edu.uclm.esi.pideamesar.model.Plato;

@Service
public class PlatoService {
	
	@Autowired
	private PlatoRepository platoDAO;

	public List<Plato> getPlatosDeBar(String barId) {
		Optional<Bar> optBar = Manager.get().getBarRepository().findById(barId);
		if (optBar.isPresent()) {
			Bar bar = optBar.get();
			return this.platoDAO.getPlatosDeBar(bar.getId());
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante sin carta cargada");
		}
	}
}
