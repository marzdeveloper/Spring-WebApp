package advPro.GestioneDispositivi.Model.Dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Entities.Device;
import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Location;
import advPro.GestioneDispositivi.Model.Entities.Positioning;

@Transactional
@Repository("positioningDao")
public class PositioningDaoDefault extends DefaultDao implements PositioningDao {
	
	@Override
	@Transactional(readOnly = true)
	public List<Positioning> findAll() {
		return getSession().
			createQuery("from Positioning p", Positioning.class).
			getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Positioning findById(int id) {
		return getSession().find(Positioning.class, id);
	}

	@Override
	@Transactional
	public void delete(Positioning positioning) {
		this.getSession().delete(positioning);
	}

	@Override
	@Transactional
	public Positioning create(Date datePositioning, Device device, Location location, Employee employee) {
		Positioning positioning = new Positioning();
		positioning.setDatePositioning(datePositioning);
		positioning.setDevice(device);
		positioning.setLocation(location);
		positioning.setEmployee(employee);
		this.getSession().save(positioning);
		return positioning;
	}

	@Override
	@Transactional
	public Positioning update(Positioning positioning) {
		return (Positioning)this.getSession().merge(positioning);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Positioning> findAllByDeviceId(int device) {
		Query q = this.getSession().createQuery("from Positioning p JOIN FETCH p.device WHERE p.device.id = :device", Positioning.class);
		return q.setParameter("device", device).getResultList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Positioning> findAllByEmployeeId(int emp) {
		Query q = this.getSession().createQuery("from Positioning p JOIN FETCH p.employee WHERE p.employee.id = :employee", Positioning.class);
		return q.setParameter("employee", emp).getResultList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Positioning findLastByEmployeeId(int emp) {
		Query q = this.getSession().createQuery("from Positioning p JOIN FETCH p.employee WHERE p.employee.id = :employee ORDER BY p.datePositioning DESC", Positioning.class);
		q.setParameter("employee", emp);
		List<Positioning> lista = q.getResultList();
		if (lista.size() > 0) {
			return lista.get(0);			
		} else {
			return null;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Positioning> findAllByLocationId(int location) {
		Query q = this.getSession().createQuery("from Positioning p JOIN FETCH p.location WHERE p.location.id = :location", Positioning.class);
		return q.setParameter("location", location).getResultList();
	}
}
