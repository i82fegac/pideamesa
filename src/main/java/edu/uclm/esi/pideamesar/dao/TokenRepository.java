package edu.uclm.esi.pideamesar.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.uclm.esi.pideamesar.model.Token;

public interface TokenRepository extends JpaRepository<Token, String> {

}
