package advPro.GestioneDispositivi.Model.Dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Entities.Device;
import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Job;
import advPro.GestioneDispositivi.Model.Entities.Team;

@Transactional
@Repository("jobDao")
public class JobDaoDefault extends DefaultDao implements JobDao {
	
	@Override
	@Transactional(readOnly = true)
	public List<Job> findAll() {
		return getSession().
			createQuery("from Job j", Job.class).
			getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Job findById(int id) {
		return getSession().find(Job.class, id);
	}

	@Override
	@Transactional
	public void delete(Job job) {
		this.getSession().delete(job);
	}

	@Override
	@Transactional
	public Job create(String description, Date start, Device dev) {
		Job job = new Job();
		job.setDescription(description);
		job.setDevice(dev);
		job.setStart(start);
		this.getSession().save(job);
		return job;
	}
	
	@Override
	@Transactional
	public Job create(String description, Date start, Date end, Device device, Team team, Employee employee) {
		Job job = new Job();
		job.setDescription(description);
		job.setStart(start);
		job.setEnd(end);
		job.setDevice(device);
		job.setTeam(team);
		job.setEmployee(employee);
		this.getSession().save(job);
		return job;
	}
	
	@Override
	@Transactional
	public Job update(Job job) {
		return (Job)this.getSession().merge(job);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Job> findAllByDeviceId(int device) {
		Query q = this.getSession().createQuery("from Job j JOIN FETCH j.device WHERE j.device.id = :device", Job.class);
		return q.setParameter("device", device).getResultList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Job> findAllByEmployeeId(int emp) {
		Query q = this.getSession().createQuery("from Job j JOIN FETCH j.employee WHERE j.employee.id = :employee and j.end is null", Job.class);
		return q.setParameter("employee", emp).getResultList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Job> findAllByTeamId(int team) {
		Query q = this.getSession().createQuery("from Job j JOIN FETCH j.team WHERE j.team.id = :team", Job.class);
		return q.setParameter("team", team).getResultList();
	}
}
