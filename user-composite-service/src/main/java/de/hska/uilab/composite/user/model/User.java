package de.hska.uilab.composite.user.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class User implements Serializable {

	private static final long serialVersionUID = 1151209418558405156L;

	public Long id;
	public String firstname;
	public String lastname;
	public String username;
	public String password;
	public Long roleID;
	public Role role;
}
