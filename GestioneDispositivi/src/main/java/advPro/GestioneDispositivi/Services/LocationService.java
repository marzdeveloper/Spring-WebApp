package advPro.GestioneDispositivi.Services;

import java.util.List;
import java.util.Set;

import advPro.GestioneDispositivi.Model.Entities.Location;
import advPro.GestioneDispositivi.Model.Entities.Positioning;
import advPro.GestioneDispositivi.Model.Entities.Workstation;

public interface LocationService {
	List<Location> findAll();
	
	Location findById(int id);
	
	Location create(String name, String description);
	
	Location update(Location location);

	void delete(Location location);
	
	void delete(int id);
	
	Set<Positioning> getPositionings(Location location);
	
	Set<Workstation> getWorkstations(Location location);
	
}
