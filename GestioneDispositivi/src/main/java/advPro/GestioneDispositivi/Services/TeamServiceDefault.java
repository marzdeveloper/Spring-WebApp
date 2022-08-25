package advPro.GestioneDispositivi.Services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Dao.TeamDao;
import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Job;
import advPro.GestioneDispositivi.Model.Entities.Team;

@Transactional
@Service("teamService")
public class TeamServiceDefault implements TeamService {

	private TeamDao teamRepository;
	
	@Override
	public List<Team> findAll() {
		return this.teamRepository.findAll();
	}
	
	@Override
	public List<Team> findAllWithJob() {
		return this.teamRepository.findAllWithJob();
	}

	@Transactional(readOnly=true)
	@Override
	public Team findById(int id) {
		return this.teamRepository.findById(id);
	}
	
	@Override
	public Team create(String name, String type, String description){
		return this.teamRepository.create(name, type, description);
	}

	@Override
	public Team update(Team team) {
		return this.teamRepository.update(team);
	}

	@Override
	public void delete(int id) {
		Team team = findById(id);
		this.teamRepository.delete(team);
	}
	
	@Autowired
	public void setTeamRepository(TeamDao teamRepository) {
		this.teamRepository = teamRepository;
	}

	@Override
	public void delete(Team team) {
		this.teamRepository.delete(team);
	}

	@Override
	public Set<Job> getJobs(Team team) {
		return this.teamRepository.getJobs(team);
	}
	
	@Override
	public Set<Employee> getEmployees(Team team){
		return this.teamRepository.getEmployees(team);
	}
	
}
