package advPro.GestioneDispositivi.Model.Dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import advPro.GestioneDispositivi.Model.Entities.Device;
import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Location;
import advPro.GestioneDispositivi.Model.Entities.Positioning;

public interface PositioningDao {
	
	public Session getSession();
	public void setSession(Session session);
	
	List<Positioning> findAll();
	
	Positioning findById(int id);

	Positioning create(Date datePositioning, Device device, Location location, Employee employee);
	
	Positioning update(Positioning positioning);
	
	void delete(Positioning positioning);
	
	List<Positioning> findAllByDeviceId(int device);
	
	List<Positioning> findAllByEmployeeId(int emp);
	
	Positioning findLastByEmployeeId(int emp);
	
	List<Positioning> findAllByLocationId(int location);
}
