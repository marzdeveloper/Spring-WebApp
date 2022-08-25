package advPro.GestioneDispositivi.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Dao.UserDetailsDao;
import advPro.GestioneDispositivi.Model.Entities.Role;
import advPro.GestioneDispositivi.Model.Entities.User;

@Transactional
@Service("userService")
public class UserServiceDefault implements UserService {

	private UserDetailsDao userRepository;
	
	@Override
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	@Transactional(readOnly=true)
	@Override
	public User findByUsername(String username) {
		return this.userRepository.findUserByUsername(username);
	}
	
	@Override
	public User create(String name, String password, Role role) {
		return this.userRepository.create(name, password, true, role);
	}

	@Override
	public User update(User user) {
		return this.userRepository.update(user);
	}

	@Override
	public boolean delete(String username) {
		User user = findByUsername(username);
		return this.userRepository.delete(user);
	}
	
	@Autowired
	public void setSenderRepository(UserDetailsDao userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public boolean delete(User user) {
		return this.userRepository.delete(user);
	}

	@Override
	public String encryptPassword(String password) {
		return this.userRepository.encryptPassword(password);
	}
}
