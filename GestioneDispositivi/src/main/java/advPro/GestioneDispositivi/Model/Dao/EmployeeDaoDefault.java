package advPro.GestioneDispositivi.Model.Dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Job;
import advPro.GestioneDispositivi.Model.Entities.Positioning;
import advPro.GestioneDispositivi.Model.Entities.Workstation;

@Transactional
@Repository("employeeDao")
public class EmployeeDaoDefault extends DefaultDao implements EmployeeDao {
	
	@Override
	@Transactional(readOnly = true)
	public List<Employee> findAll() {
		return getSession().
				createQuery("from Employee e", Employee.class).
				getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Employee findById(int id) {
		return getSession().find(Employee.class, id);
	}
	
	@Override
	@Transactional
	public Employee update(Employee employee) {
		Employee merged = (Employee)this.getSession().merge(employee);
		return merged;
	}
	
	@Override
	@Transactional
	public boolean delete(Employee employee) {
		Session s = this.getSession();
		try {
			s.createNativeQuery("DELETE FROM membership WHERE Employee_id = ?").setParameter(1, employee.getId()).executeUpdate();
			s.createNativeQuery("DELETE FROM job WHERE Team_id IN (SELECT t1.id FROM team t1 WHERE t1.Id NOT IN (SELECT DISTINCT t.id FROM team t, membership m WHERE t.Id = m.Team_id))").executeUpdate();
			s.createNativeQuery("DELETE FROM team WHERE Id NOT IN (SELECT DISTINCT t.id FROM team t, membership m WHERE t.Id = m.Team_id)").executeUpdate();
			s.delete(employee);
			return true;
		} catch (Exception e) {
			//System.out.print("Error: " + e.getMessage());
			return false;
		}
	}

	@Override
	@Transactional
	public Employee create(String name, String surname) {
		Employee employee = new Employee();
		employee.setName(name);
		employee.setSurname(surname);
		this.getSession().save(employee);
		return employee;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Set<Workstation> getWorkstations(Employee employee) {
		Query q = this.getSession().createQuery("from Workstation w JOIN FETCH w.employee WHERE w.employee = :employee", Workstation.class);
		return new HashSet<Workstation>(q.setParameter("employee", employee).getResultList());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Set<Positioning> getPositionings(Employee employee) {
		Query q = this.getSession().createQuery("from Positioning p JOIN FETCH p.employee WHERE p.employee = :employee", Positioning.class);
		return new HashSet<Positioning>(q.setParameter("employee", employee).getResultList());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Employee> findAllWithJob() {
		return getSession().createQuery("SELECT distinct e from Employee e, Job j WHERE j.employee = e").getResultList(); 
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Set<Job> getJobs(Employee employee) {
		Query q = this.getSession().createQuery("from Job j JOIN FETCH j.employee WHERE j.employee = :employee", Job.class);
		return new HashSet<Job>(q.setParameter("employee", employee).getResultList());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Employee> findAllByTeamId(int team) {
		Query q = this.getSession().createQuery("from Employee e JOIN FETCH e.teams et WHERE et.id = :team", Employee.class);
		return q.setParameter("team", team).getResultList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Employee> findAllByWorkstationId(int work) {
		Query q = this.getSession().createQuery("from Employee e JOIN FETCH e.workstations ew WHERE ew.id = :workstation", Employee.class);
		return q.setParameter("workstation", work).getResultList();
	}
	
}
