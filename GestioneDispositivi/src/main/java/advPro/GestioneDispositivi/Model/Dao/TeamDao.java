package advPro.GestioneDispositivi.Model.Dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Job;
import advPro.GestioneDispositivi.Model.Entities.Team;

public interface TeamDao {
	
	public Session getSession();
	public void setSession(Session session);
	
	List<Team> findAll();

	List<Team> findAllWithJob();
	
	Team findById(int id);

	Team create(String name, String type, String description);
	
	Team update(Team team);
	
	void delete(Team team);
	
	Set<Job> getJobs(Team team);
	
	Set<Employee> getEmployees(Team team);
	
}
