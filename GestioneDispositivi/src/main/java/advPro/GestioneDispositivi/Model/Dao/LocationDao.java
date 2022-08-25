package advPro.GestioneDispositivi.Model.Dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import advPro.GestioneDispositivi.Model.Entities.Location;
import advPro.GestioneDispositivi.Model.Entities.Positioning;
import advPro.GestioneDispositivi.Model.Entities.Workstation;

public interface LocationDao {
	
	public Session getSession();
	public void setSession(Session session);
	
	List<Location> findAll();
	
	Location findById(int id);

	Location create(String name, String description);
	
	Location update(Location location);
	
	void delete(Location location);
	
	Set<Positioning> getPositionings(Location location);
	
	Set<Workstation> getWorkstations(Location location);

}
