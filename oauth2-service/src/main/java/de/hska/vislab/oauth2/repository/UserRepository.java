package de.hska.vislab.oauth2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.hska.vislab.oauth2.domain.User;


@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
