package advPro.GestioneDispositivi.Model.Dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Location;
import advPro.GestioneDispositivi.Model.Entities.Workstation;

@Transactional
@Repository("workstationDao")
public class WorkstationDaoDefault extends DefaultDao implements WorkstationDao {
	
	@Override
	@Transactional(readOnly = true)
	public List<Workstation> findAll() {
		return getSession().
			createQuery("from Workstation w", Workstation.class).
			getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Workstation findById(int id) {
		return getSession().find(Workstation.class, id);
	}

	@Override
	@Transactional
	public void delete(Workstation workstation) {
		this.getSession().delete(workstation);
	}

	@Override
	@Transactional
	public Workstation create(Date start, Date end, Employee employee, Location location) {
		Workstation workstation = new Workstation();
		workstation.setStart(start);
		workstation.setEnd(end);
		workstation.setEmployee(employee);
		workstation.setLocation(location);
		this.getSession().save(workstation);
		return workstation;
	}

	@Override
	@Transactional
	public Workstation update(Workstation workstation) {
		return (Workstation)this.getSession().merge(workstation);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Workstation> findAllByEmployeeId(int emp) {
		Query q = this.getSession().createQuery("from Workstation w JOIN FETCH w.employee WHERE w.employee.id = :employee", Workstation.class);
		return q.setParameter("employee", emp).getResultList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Workstation findByEmployeeId(int emp) {
		try {
			Query q = this.getSession().createQuery("from Workstation w JOIN FETCH w.employee WHERE w.employee.id = :employee and w.end is null", Workstation.class);
			return (Workstation) q.setParameter("employee", emp).getSingleResult();			
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Workstation> findAllByLocationId(int location) {
		Query q = this.getSession().createQuery("from Workstation w JOIN FETCH w.location WHERE w.location.id = :location", Workstation.class);
		return q.setParameter("location", location).getResultList();
	}
}
