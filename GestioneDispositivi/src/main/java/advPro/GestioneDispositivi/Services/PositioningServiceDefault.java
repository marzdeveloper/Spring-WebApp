package advPro.GestioneDispositivi.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Dao.PositioningDao;
import advPro.GestioneDispositivi.Model.Entities.Positioning;

@Transactional
@Service("positioningService")
public class PositioningServiceDefault implements PositioningService {

	private PositioningDao positioningRepository;
	
	@Override
	public List<Positioning> findAll() {
		return this.positioningRepository.findAll();
	}

	@Transactional(readOnly=true)
	@Override
	public Positioning findById(int id) {
		return this.positioningRepository.findById(id);
	}
	
	@Override
	public Positioning create(String name) {
		return this.create(name);
	}

	@Override
	public Positioning update(Positioning pos) {
		return this.positioningRepository.update(pos);
	}

	@Override
	public void delete(int id) {
		Positioning emp = findById(id);
		this.positioningRepository.delete(emp);
	}
	
	@Autowired
	public void setPositioningRepository(PositioningDao jobRepository) {
		this.positioningRepository = jobRepository;
	}

	@Override
	public void delete(Positioning job) {
		this.positioningRepository.delete(job);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Positioning> findAllByDeviceId(int device) {
		return this.positioningRepository.findAllByDeviceId(device);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Positioning> findAllByEmployeeId(int emp) {
		return this.positioningRepository.findAllByEmployeeId(emp);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Positioning findLastByEmployeeId(int emp) {
		return this.positioningRepository.findLastByEmployeeId(emp);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Positioning> findAllByLocationId(int location) {
		return this.positioningRepository.findAllByLocationId(location);
	}
}
