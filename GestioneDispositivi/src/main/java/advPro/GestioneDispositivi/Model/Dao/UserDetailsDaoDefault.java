package advPro.GestioneDispositivi.Model.Dao;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Entities.Role;
import advPro.GestioneDispositivi.Model.Entities.User;

@Transactional
@Repository("userDetailsDao")
public class UserDetailsDaoDefault extends DefaultDao implements UserDetailsDao {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User findUserByUsername(String username) {
		return this.getSession().get(User.class, username);
	}

	@Override
	public User create(String username, String password, boolean isEnabled, Role role) {
		User u = new User();
		u.setUsername(username);
		u.setPassword(encryptPassword(password));
		u.setEnabled(isEnabled);
		u.addRole(role);
		
		this.getSession().save(u);
		return u;
	}

	@Override
	public User update(User user) {
		return (User)this.getSession().merge(user);
	}

	@Override
	public boolean delete(User user) {
		boolean delete = true;
		Set<Role> set = user.getRoles();		
		for (Role r : set) {
		    if (r.isAdmin()) {
		    	delete = false;
		    }
		}
		
		if (delete) {			
			this.getSession().delete(user);
		}
		return delete;
	}

	@Override
	public String encryptPassword(String password) {
		return this.passwordEncoder.encode(password);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return getSession().
			createQuery("from User u", User.class).
			getResultList();
	}
}
