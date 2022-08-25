package advPro.GestioneDispositivi.Services;

import java.util.List;
import java.util.Set;

import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Job;
import advPro.GestioneDispositivi.Model.Entities.Team;

public interface TeamService {
	List<Team> findAll();
	
	List<Team> findAllWithJob();
	
	Team findById(int id);
	
	Team create(String name, String type, String description);
	
	Team update(Team team);

	void delete(Team team);
	
	void delete(int id);
	
	Set<Job> getJobs(Team team);
	
	Set<Employee> getEmployees(Team team);
	
}
