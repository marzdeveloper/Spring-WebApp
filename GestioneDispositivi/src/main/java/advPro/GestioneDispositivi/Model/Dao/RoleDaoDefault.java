package advPro.GestioneDispositivi.Model.Dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Entities.Role;

@Transactional
@Repository("roleDao")
public class RoleDaoDefault extends DefaultDao implements RoleDao {

	@Override
	public Role create(String name) {
		Role r = new Role();
		r.setName(name);
		this.getSession().save(r);
		
		return r;
	}

	@Override
	public Role update(Role role) {
		return (Role)this.getSession().merge(role);
	}

	@Override
	public void delete(Role role) {
		this.getSession().delete(role);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> findAll() {
		return getSession().
				createQuery("from Role r", Role.class).
				getResultList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Role findRoleByName(String name) {
		Query q = this.getSession().createQuery("from Role r WHERE r.name = :name", Role.class);
		return (Role) q.setParameter("name", name).getSingleResult();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Role findRoleById(long id) {
		return getSession().find(Role.class, id);
	}
}
