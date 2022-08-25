package advPro.GestioneDispositivi.Model.Dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import advPro.GestioneDispositivi.Model.Entities.Device;
import advPro.GestioneDispositivi.Model.Entities.Job;
import advPro.GestioneDispositivi.Model.Entities.Positioning;
import advPro.GestioneDispositivi.Model.Entities.Sender;

public interface DeviceDao {
	public Session getSession();
	public void setSession(Session session);
	
	List<Device> findAll();
	
	Device findById(int id);
	
	Device create(String serialNumber, String brand, String model, String reason, String document, Date checkIn, Date checkOut, Sender sender);
	
	Device update(Device device);
	
	void delete(Device device);
	
	Set<Positioning> getPositionings(Device device);
	
	Set<Job> getJobs(Device device);
	
	List<Device> findAllWithJob();
	
	List<Device> findAllBySenderId(int sender);
}
