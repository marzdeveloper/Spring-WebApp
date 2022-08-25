package advPro.GestioneDispositivi.Services;

import java.util.Date;
import java.util.List;

import advPro.GestioneDispositivi.Model.Entities.Device;
import advPro.GestioneDispositivi.Model.Entities.Job;

public interface JobService {
	List<Job> findAll();
	
	Job findById(int id);
	
	Job create(String des, Date start, Device dev);
	
	Job update(Job job);
	
	void delete(int id);

	void delete(Job job);
	
	List<Job> findAllByDeviceId(int device);
	
	List<Job> findAllByEmployeeId(int emp);
	
	List<Job> findAllByTeamId(int team);
}
