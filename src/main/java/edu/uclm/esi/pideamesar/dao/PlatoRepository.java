package edu.uclm.esi.pideamesar.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.uclm.esi.pideamesar.model.Bar;
import edu.uclm.esi.pideamesar.model.Plato;

@Repository
public interface PlatoRepository extends JpaRepository <Plato, String> {

	List<Plato> findByBar(Bar bar);
	
	@Query(value = "select * from Plato where bar_id= :barId order by nombre asc", nativeQuery = true)
	List<Plato> getPlatosDeBar(@Param("barId") String barId);


}
