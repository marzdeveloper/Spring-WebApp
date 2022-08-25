package advPro.GestioneDispositivi.Model.Dao;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Entities.Device;
import advPro.GestioneDispositivi.Model.Entities.Job;
import advPro.GestioneDispositivi.Model.Entities.Positioning;
import advPro.GestioneDispositivi.Model.Entities.Sender;

@Transactional
@Repository("deviceDao")
public class DeviceDaoDefault extends DefaultDao implements DeviceDao {
	
	@Override
	@Transactional(readOnly = true)
	public List<Device> findAll() {
		return getSession().
			createQuery("from Device d", Device.class).
			getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Device findById(int id) {
		return getSession().find(Device.class, id);
	}

	@Override
	@Transactional
	public void delete(Device device) {
		this.getSession().delete(device);	
	}

	@Override
	@Transactional
	public Device create(String serialNumber, String brand, String model, String reason, String document, Date checkIn, Date checkOut, Sender sender){
		Device device = new Device();
		device.setSerialNumber(serialNumber);
		device.setBrand(brand);
		device.setModel(model);
		device.setReason(reason);
		device.setDocument(document);
		device.setCheckIn(checkIn);
		device.setCheckOut(checkOut);
		device.setSender(sender);
		this.getSession().save(device);
		return device;
	}

	@Override
	@Transactional
	public Device update(Device device) {
		return (Device)this.getSession().merge(device);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Set<Positioning> getPositionings(Device device) {
		Query q = this.getSession().createQuery("from Positioning p JOIN FETCH p.device WHERE p.device = :device", Positioning.class);
		return new HashSet<Positioning>(q.setParameter("device", device).getResultList());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Set<Job> getJobs(Device device) {
		Query q = this.getSession().createQuery("from Job j JOIN FETCH j.device WHERE j.device = :device", Job.class);
		return new HashSet<Job>(q.setParameter("device", device).getResultList());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Device> findAllWithJob() {
		return getSession().createQuery("SELECT distinct d from Device d, Job j WHERE j.device = d").getResultList(); 
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Device> findAllBySenderId(int sender) {
		Query q = this.getSession().createQuery("SELECT distinct d from Device d JOIN FETCH d.sender WHERE d.sender.id = :sender", Device.class);
		return q.setParameter("sender", sender).getResultList();
	}
}
