package edu.uclm.esi.pideamesar.model;

import java.util.Base64;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(indexes = {
	@Index(unique = true, columnList = "email"),
	@Index(unique = true, columnList = "name"),
})
public class User {
	@Id
	private String id;
	@NotNull 
	private String email;
	@NotNull
	private String name;
	@NotNull
	private String pwd;
	private String picture;
	private Long confirmationDate;
	
	public User() {
		this.id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String userName) {
		this.name = userName;
	}

	@JsonIgnore
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		byte[] b64 = Base64.getEncoder().encode(picture);
		this.picture = new String(b64);	
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public Long getConfirmationDate() {
		return confirmationDate;
	}
	
	public void setConfirmationDate(Long confirmationDate) {
		this.confirmationDate = confirmationDate;
	}
}
