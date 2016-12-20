package de.hska.vislab.rolecore.dao;

import org.springframework.data.repository.CrudRepository;

import de.hska.vislab.rolecore.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	public Role getRoleByLevel(int level);
	public Role getRoleByType(String type);
}
