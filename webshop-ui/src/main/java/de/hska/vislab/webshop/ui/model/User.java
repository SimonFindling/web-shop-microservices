package de.hska.vislab.webshop.ui.model;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
	
	
	private Long id;
	@NotBlank(message = "error.username.required")
	private String username;
	private String lastname;
	private String firstname;
	@NotBlank(message = "error.password.required")
	private String password;
	@JsonIgnore
	private String passwordWdh;
	private Role role;

	public User() { }
	
	public User(Long id, String username, String lastname, String firstname, String password, Role role) {
		super();
		this.id = id;
		this.username = username;
		this.lastname = lastname;
		this.firstname = firstname;
		this.password = password;
		this.role = role;
	}

	public User(Long id, String username, String lastname, String firstname, String password, String passwordWdh,
			Role role) {
		super();
		this.id = id;
		this.username = username;
		this.lastname = lastname;
		this.firstname = firstname;
		this.password = password;
		this.passwordWdh = passwordWdh;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordWdh() {
		return passwordWdh;
	}

	public void setPasswordWdh(String passwordWdh) {
		this.passwordWdh = passwordWdh;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", lastname=" + lastname + ", firstname=" + firstname
				+ ", password=" + password + ", passwordWdh=" + passwordWdh + ", role=" + role + "]";
	}


}
