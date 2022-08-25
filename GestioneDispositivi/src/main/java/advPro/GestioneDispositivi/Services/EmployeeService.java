package advPro.GestioneDispositivi.Services;

import java.util.List;

import advPro.GestioneDispositivi.Model.Entities.Employee;

public interface EmployeeService {
	List<Employee> findAll();
	
	Employee findById(int id);
	
	Employee create(String name, String surname);
	
	Employee update(Employee employee);
	
	boolean delete(int id);

	boolean delete(Employee employee);
	
	List<Employee> findAllByTeamId(int team);
	
	List<Employee> findAllByWorkstationId(int work);
	
}
