package advPro.GestioneDispositivi.Services;

import java.util.List;

import advPro.GestioneDispositivi.Model.Entities.Positioning;;

public interface PositioningService {
	List<Positioning> findAll();
	
	Positioning findById(int id);
	
	Positioning create(String name);
	
	Positioning update(Positioning pos);
	
	void delete(int id);

	void delete(Positioning pos);
	
	List<Positioning> findAllByDeviceId(int device);
	
	List<Positioning> findAllByEmployeeId(int emp);
	
	Positioning findLastByEmployeeId(int emp);
	
	List<Positioning> findAllByLocationId(int location);
}
