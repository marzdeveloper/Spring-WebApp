package advPro.GestioneDispositivi.Model.Dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Entities.Location;
import advPro.GestioneDispositivi.Model.Entities.Positioning;
import advPro.GestioneDispositivi.Model.Entities.Workstation;

@Transactional
@Repository("locationDao")
public class LocationDaoDefault extends DefaultDao implements LocationDao {
	
	@Override
	@Transactional(readOnly = true)
	public List<Location> findAll() {
		return getSession().
				createQuery("from Location l", Location.class).
				getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Location findById(int id) {
		return getSession().find(Location.class, id);
	}
	
	@Override
	@Transactional
	public Location update(Location location) {
		Location merged = (Location)this.getSession().merge(location);
		return merged;
	}
	
	@Override
	public void delete(Location location) {
		this.getSession().delete(location);
	}

	@Override
	@Transactional
	public Location create(String name, String description) {
		Location location = new Location();
		location.setName(name);
		location.setDescription(description);
		this.getSession().save(location);
		return location;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Set<Positioning> getPositionings(Location location) {
		Query q = this.getSession().createQuery("from Positioning p JOIN FETCH p.location WHERE p.location = :location", Positioning.class);
		return new HashSet<Positioning>(q.setParameter("location", location).getResultList());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Set<Workstation> getWorkstations(Location location) {
		Query q = this.getSession().createQuery("from Workstation w JOIN FETCH w.location WHERE w.location = :location", Workstation.class);
		return new HashSet<Workstation>(q.setParameter("location", location).getResultList());
	}
}
