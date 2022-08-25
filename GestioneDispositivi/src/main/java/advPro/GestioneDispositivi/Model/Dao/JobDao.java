package advPro.GestioneDispositivi.Model.Dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import advPro.GestioneDispositivi.Model.Entities.Device;
import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Job;
import advPro.GestioneDispositivi.Model.Entities.Team;

public interface JobDao {
	
	public Session getSession();
	public void setSession(Session session);
	
	List<Job> findAll();
	
	Job findById(int id);

	Job create(String description, Date start, Device dev);
	
	Job create(String description, Date start, Date end, Device device, Team team, Employee employee);
	
	Job update(Job job);
	
	void delete(Job job);
	
	List<Job> findAllByDeviceId(int device);
	
	List<Job> findAllByEmployeeId(int emp);
	
	List<Job> findAllByTeamId(int team);
}
