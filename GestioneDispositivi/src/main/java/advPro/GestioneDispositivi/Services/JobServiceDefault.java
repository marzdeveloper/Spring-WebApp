package advPro.GestioneDispositivi.Services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Dao.JobDao;
import advPro.GestioneDispositivi.Model.Entities.Device;
import advPro.GestioneDispositivi.Model.Entities.Job;

@Transactional
@Service("jobService")
public class JobServiceDefault implements JobService {

	private JobDao jobRepository;
	
	@Override
	public List<Job> findAll() {
		return this.jobRepository.findAll();
	}

	@Transactional(readOnly=true)
	@Override
	public Job findById(int id) {
		return this.jobRepository.findById(id);
	}
	
	@Override
	public Job create(String des, Date start, Device dev) {
		return this.jobRepository.create(des, start, dev);
	}

	@Override
	public Job update(Job job) {
		return this.jobRepository.update(job);
	}
	
	@Override
	public void delete(int id) {
		Job emp = findById(id);
		this.jobRepository.delete(emp);
	}
	
	@Autowired
	public void setJobRepository(JobDao jobRepository) {
		this.jobRepository = jobRepository;
	}

	@Override
	public void delete(Job job) {
		this.jobRepository.delete(job);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Job> findAllByDeviceId(int device) {
		return this.jobRepository.findAllByDeviceId(device);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Job> findAllByEmployeeId(int emp) {
		return this.jobRepository.findAllByEmployeeId(emp);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Job> findAllByTeamId(int team) {
		return this.jobRepository.findAllByTeamId(team);
	}
}
