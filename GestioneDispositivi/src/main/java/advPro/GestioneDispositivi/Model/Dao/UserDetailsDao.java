package advPro.GestioneDispositivi.Model.Dao;

import java.util.List;

import org.hibernate.Session;

import advPro.GestioneDispositivi.Model.Entities.Role;
import advPro.GestioneDispositivi.Model.Entities.User;


public interface UserDetailsDao {
	Session getSession();
	
	public void setSession(Session session);
	
	User findUserByUsername(String username);
	
	User create(String username, String password, boolean isEnabled, Role role);
	
	User update(User user);
	
	boolean delete(User user);

	public String encryptPassword(String password);
	
	List<User> findAll();
}
