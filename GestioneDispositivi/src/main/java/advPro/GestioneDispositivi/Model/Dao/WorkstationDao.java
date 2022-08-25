package advPro.GestioneDispositivi.Model.Dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Location;
import advPro.GestioneDispositivi.Model.Entities.Workstation;

public interface WorkstationDao {
	
	public Session getSession();
	public void setSession(Session session);
	
	List<Workstation> findAll();
	
	Workstation findById(int id);

	Workstation create(Date start, Date end, Employee employee, Location location);
	
	Workstation update(Workstation workstation);
	
	void delete(Workstation workstation);
	
	List<Workstation> findAllByEmployeeId(int emp);
	
	Workstation findByEmployeeId(int emp);
	
	List<Workstation> findAllByLocationId(int location);
}
