package advPro.GestioneDispositivi.Services;

import java.util.List;

import advPro.GestioneDispositivi.Model.Entities.Role;

public interface RoleService {
	List<Role> findAll();
	
	Role findRoleByName(String name);
	
	Role findRoleById(long id);
	
	Role create(String name);
	
	Role update(Role role);
	
	void delete(String name);

	void delete(Role role);
}
