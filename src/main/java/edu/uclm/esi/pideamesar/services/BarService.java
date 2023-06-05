package edu.uclm.esi.pideamesar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uclm.esi.pideamesar.dao.BarRepository;
import edu.uclm.esi.pideamesar.model.Bar;

@Service
public class BarService {
	
	@Autowired
	private BarRepository barDAO;

	public List<Bar> getBares(Double latitud, Double longitud, Integer radio) {
		return this.barDAO.findByLatitudAndLongitud(latitud, longitud);
	}

	public List<Bar> getBares() {
		return this.barDAO.findAllByOrderByNombre();
	}

	public Bar findById(String barId) {
		return this.barDAO.findById(barId).get();
	}

}
