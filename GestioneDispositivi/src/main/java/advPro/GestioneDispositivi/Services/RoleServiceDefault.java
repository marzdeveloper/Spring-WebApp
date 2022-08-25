package advPro.GestioneDispositivi.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Dao.RoleDao;
import advPro.GestioneDispositivi.Model.Entities.Role;

@Transactional
@Service("roleService")
public class RoleServiceDefault implements RoleService {

	private RoleDao roleRepository;
	
	@Override
	public List<Role> findAll() {
		return this.roleRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Role findRoleByName(String name) {
		return this.roleRepository.findRoleByName(name);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Role findRoleById(long id) {
		return this.roleRepository.findRoleById(id);
	}
	
	@Override
	public Role create(String name) {
		return this.roleRepository.create(name);
	}

	@Override
	public Role update(Role role) {
		return this.roleRepository.update(role);
	}

	@Override
	public void delete(String name) {
		Role role = findRoleByName(name);
		this.roleRepository.delete(role);
	}
	
	@Autowired
	public void setSenderRepository(RoleDao roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public void delete(Role role) {
		this.roleRepository.delete(role);
	}

}
