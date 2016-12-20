package de.hska.vislab.usercore.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.hska.vislab.usercore.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	
	@Query("FROM Customer u WHERE LOWER(p.name) LIKE LOWER(?1)")
	Iterable<User> getUserByName(String name);
	
}
