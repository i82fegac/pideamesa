package edu.uclm.esi.pideamesar.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.uclm.esi.pideamesar.model.Bar;

@Repository
public interface BarRepository extends JpaRepository <Bar, String> {

	List<Bar> findByLatitudAndLongitud(Double latitud, Double longitud);

	List<Bar> findAllByOrderByNombre();

	Bar findByNombre(String nombre);

}
