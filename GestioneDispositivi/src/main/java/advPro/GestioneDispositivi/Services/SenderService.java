package advPro.GestioneDispositivi.Services;

import java.util.List;

import advPro.GestioneDispositivi.Model.Entities.Sender;

public interface SenderService {
	List<Sender> findAll();
	
	Sender findById(int id);
	
	Sender create(String name);
	
	Sender update(Sender sender);
	
	void delete(int id);

	void delete(Sender sender);
}
