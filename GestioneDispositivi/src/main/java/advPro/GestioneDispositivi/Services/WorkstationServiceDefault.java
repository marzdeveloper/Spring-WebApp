package advPro.GestioneDispositivi.Services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Dao.WorkstationDao;
import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Location;
import advPro.GestioneDispositivi.Model.Entities.Workstation;

@Transactional
@Service("workstationService")
public class WorkstationServiceDefault implements WorkstationService {

	private WorkstationDao workstationRepository;
	
	@Override
	public List<Workstation> findAll() {
		return this.workstationRepository.findAll();
	}

	@Transactional(readOnly=true)
	@Override
	public Workstation findById(int id) {
		return this.workstationRepository.findById(id);
	}
	
	@Override
	public Workstation create(Date start, Date end, Employee employee, Location location) {
		return this.workstationRepository.create(start, end, employee, location);
	}

	@Override
	public Workstation update(Workstation workstation) {
		return this.workstationRepository.update(workstation);
	}

	@Override
	public void delete(int id) {
		Workstation workstation = findById(id);
		this.workstationRepository.delete(workstation);
	}
	
	@Autowired
	public void setWorkstationRepository(WorkstationDao workstationRepository) {
		this.workstationRepository = workstationRepository;
	}

	@Override
	public void delete(Workstation workstation) {
		this.workstationRepository.delete(workstation);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Workstation> findAllByEmployeeId(int emp) {
		return this.workstationRepository.findAllByEmployeeId(emp);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Workstation findByEmployeeId(int emp) {
		return this.workstationRepository.findByEmployeeId(emp);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Workstation> findAllByLocationId(int location) {
		return this.workstationRepository.findAllByLocationId(location);
	}
}
