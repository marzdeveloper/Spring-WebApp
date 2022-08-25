package advPro.GestioneDispositivi.Services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Dao.LocationDao;
import advPro.GestioneDispositivi.Model.Entities.Location;
import advPro.GestioneDispositivi.Model.Entities.Positioning;
import advPro.GestioneDispositivi.Model.Entities.Workstation;

@Transactional
@Service("locationService")
public class LocationServiceDefault implements LocationService {

	private LocationDao locationRepository;
	
	@Override
	public List<Location> findAll() {
		return this.locationRepository.findAll();
	}
	

	@Transactional(readOnly=true)
	@Override
	public Location findById(int id) {
		return this.locationRepository.findById(id);
	}
	
	@Override
	public Location create(String name, String description){
		return this.locationRepository.create(name, description);
	}

	@Override
	public Location update(Location location) {
		return this.locationRepository.update(location);
	}

	@Override
	public void delete(int id) {
		Location location = findById(id);
		this.locationRepository.delete(location);
	}
	
	@Autowired
	public void setLocationRepository(LocationDao locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	public void delete(Location location) {
		this.locationRepository.delete(location);
	}

	@Override
	public Set<Positioning> getPositionings(Location location) {
		return this.locationRepository.getPositionings(location);
	}
	
	@Override
	public Set<Workstation> getWorkstations(Location location){
		return this.locationRepository.getWorkstations(location);
	}
	
}
