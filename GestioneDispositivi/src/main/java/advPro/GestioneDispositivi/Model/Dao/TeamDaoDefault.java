package advPro.GestioneDispositivi.Model.Dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Job;
import advPro.GestioneDispositivi.Model.Entities.Team;

@Transactional
@Repository("teamDao")
public class TeamDaoDefault extends DefaultDao implements TeamDao {
	
	@Override
	@Transactional(readOnly = true)
	public List<Team> findAll() {
		return getSession().
				createQuery("from Team t", Team.class).
				getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Team> findAllWithJob() {
		return getSession().createQuery("SELECT distinct t from Team t, Job j WHERE j.team = t").getResultList(); 
	}

	@Override
	@Transactional(readOnly = true)
	public Team findById(int id) {
		return getSession().find(Team.class, id);
	}
	
	@Override
	@Transactional
	public Team update(Team team) {
		Team merged = (Team)this.getSession().merge(team);
		return merged;
	}
	
	@Override
	@Transactional
	public void delete(Team team) {
		this.getSession().delete(team);
	}

	@Override
	@Transactional
	public Team create(String name, String type, String description) {
		Team team = new Team();
		team.setName(name);
		team.setType(type);
		team.setDescription(description);
		this.getSession().save(team);
		return team;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Set<Job> getJobs(Team team) {
		Query q1 = this.getSession().createQuery("from Job j JOIN FETCH j.team WHERE j.team = :team", Job.class);
		return new HashSet<Job>(q1.setParameter("team", team).getResultList());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Set<Employee> getEmployees(Team team) {
		Query q2 = this.getSession().createQuery("from membership m JOIN FETCH m.team WHERE m.team = :team", Employee.class);
		return new HashSet<Employee>(q2.setParameter("team", team).getResultList());
	}
	
}
