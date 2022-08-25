package advPro.GestioneDispositivi.Model.Dao;

import java.util.List;

import org.hibernate.Session;

import advPro.GestioneDispositivi.Model.Entities.Role;

public interface RoleDao {
	Session getSession();
	
	public void setSession(Session session);

	Role create(String name);
	
	Role update(Role role);
	
	void delete(Role role);
	
	List<Role> findAll();
	
	Role findRoleByName(String name);
	
	Role findRoleById(long id);
}
