package advPro.GestioneDispositivi.Model.Dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import advPro.GestioneDispositivi.Model.Entities.Device;
import advPro.GestioneDispositivi.Model.Entities.Sender;

public interface SenderDao {
	
	public Session getSession();
	public void setSession(Session session);
	
	List<Sender> findAll();
	
	Sender findById(int id);

	Sender create(String name);
	
	Sender update(Sender sender);
	
	void delete(Sender sender);
	
	Set<Device> getDevices(Sender sender);
}
