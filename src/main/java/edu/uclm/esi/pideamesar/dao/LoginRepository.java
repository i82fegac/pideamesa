package edu.uclm.esi.pideamesar.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.uclm.esi.pideamesar.model.Login;

public interface LoginRepository extends JpaRepository <Login, String> {

}
