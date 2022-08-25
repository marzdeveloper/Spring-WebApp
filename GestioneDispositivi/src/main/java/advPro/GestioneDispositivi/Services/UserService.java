package advPro.GestioneDispositivi.Services;

import java.util.List;

import advPro.GestioneDispositivi.Model.Entities.Role;
import advPro.GestioneDispositivi.Model.Entities.User;

public interface UserService {
	List<User> findAll();
	
	User findByUsername(String username);
	
	User create(String username, String password, Role role);
	
	User update(User user);
	
	boolean delete(String username);

	boolean delete(User user);
	
	String encryptPassword(String password);
}
