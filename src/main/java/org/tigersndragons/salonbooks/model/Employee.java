package org.tigersndragons.salonbooks.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(schema="SALONBOOKS",name="EMPLOYEE")
@AttributeOverride(name="id", column=@Column(name="EMPLOYEE_ID"))
public class Employee extends SalonObject {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
	private String name;

	@Column(name="USERNAME",unique=true)
	@NaturalId
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name="PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="EMPLOYEE_NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
//	@Override
//	public Long getId(){
//		return id;
//	}
}
