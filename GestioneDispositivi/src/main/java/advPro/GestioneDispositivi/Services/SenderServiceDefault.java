package advPro.GestioneDispositivi.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Dao.SenderDao;
import advPro.GestioneDispositivi.Model.Entities.Sender;

@Transactional
@Service("senderService")
public class SenderServiceDefault implements SenderService {

	private SenderDao senderRepository;
	
	@Override
	public List<Sender> findAll() {
		return this.senderRepository.findAll();
	}

	@Transactional(readOnly=true)
	@Override
	public Sender findById(int id) {
		return this.senderRepository.findById(id);
	}
	
	@Override
	public Sender create(String name) {
		return this.senderRepository.create(name);
	}

	@Override
	public Sender update(Sender sender) {
		return this.senderRepository.update(sender);
	}

	@Override
	public void delete(int id) {
		Sender emp = findById(id);
		this.senderRepository.delete(emp);
	}
	
	@Autowired
	public void setSenderRepository(SenderDao senderRepository) {
		this.senderRepository = senderRepository;
	}

	@Override
	public void delete(Sender sender) {
		this.senderRepository.delete(sender);
	}

}
