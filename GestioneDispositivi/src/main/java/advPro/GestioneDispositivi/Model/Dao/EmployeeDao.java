package advPro.GestioneDispositivi.Model.Dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Job;
import advPro.GestioneDispositivi.Model.Entities.Positioning;
import advPro.GestioneDispositivi.Model.Entities.Workstation;

public interface EmployeeDao {
	
	public Session getSession();
	public void setSession(Session session);
	
	List<Employee> findAll();
	
	Employee findById(int id);

	Employee create(String name, String surname);
	
	Employee update(Employee employee);
	
	boolean delete(Employee employee);
	
	Set<Workstation> getWorkstations(Employee employee);
	
	Set<Positioning> getPositionings(Employee employee);
	
	List<Employee> findAllWithJob();
	
	Set<Job> getJobs(Employee employee);
	
	List<Employee> findAllByTeamId(int team);
	
	List<Employee> findAllByWorkstationId(int work);
}
