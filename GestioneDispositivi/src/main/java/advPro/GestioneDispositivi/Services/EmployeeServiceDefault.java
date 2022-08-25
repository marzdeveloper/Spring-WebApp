package advPro.GestioneDispositivi.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Dao.EmployeeDao;
import advPro.GestioneDispositivi.Model.Entities.Employee;

@Transactional
@Service("employeeService")
public class EmployeeServiceDefault implements EmployeeService {

	private EmployeeDao employeeRepository;
	
	@Override
	public List<Employee> findAll() {
		return this.employeeRepository.findAll();
	}

	@Transactional(readOnly=true)
	@Override
	public Employee findById(int id) {
		return this.employeeRepository.findById(id);
	}
	
	@Override
	public Employee create(String name, String surname) {
		return this.employeeRepository.create(name, surname);
	}

	@Override
	public Employee update(Employee employee) {
		return this.employeeRepository.update(employee);
	}

	@Override
	public boolean delete(int id) {
		Employee emp = findById(id);
		return this.employeeRepository.delete(emp);
	}
	
	@Autowired
	public void setEmployeeRepository(EmployeeDao employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public boolean delete(Employee employee) {
		return this.employeeRepository.delete(employee);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Employee> findAllByTeamId(int team) {
		return this.employeeRepository.findAllByTeamId(team);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Employee> findAllByWorkstationId(int work) {
		return this.employeeRepository.findAllByWorkstationId(work);
	}
	
}
