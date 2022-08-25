package advPro.GestioneDispositivi.Services;

import java.util.Date;
import java.util.List;

import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Location;
import advPro.GestioneDispositivi.Model.Entities.Workstation;

public interface WorkstationService {
	List<Workstation> findAll();
	
	Workstation findById(int id);
	
	Workstation create(Date start, Date end, Employee employee, Location location);
	
	Workstation update(Workstation workstation);

	void delete(Workstation workstation);
	
	void delete(int id);
	
	List<Workstation> findAllByEmployeeId(int emp);
	
	Workstation findByEmployeeId(int emp);
	
	List<Workstation> findAllByLocationId(int location);
}
