package advPro.GestioneDispositivi.Model.Dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Entities.Sender;
import advPro.GestioneDispositivi.Model.Entities.Device;

@Transactional
@Repository("senderDao")
public class SenderDaoDefault extends DefaultDao implements SenderDao {
	
	@Override
	@Transactional(readOnly = true)
	public List<Sender> findAll() {
		return getSession().
				createQuery("from Sender s", Sender.class).
				getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Sender findById(int id) {
		return getSession().find(Sender.class, id);
	}

	@Override
	@Transactional
	public Sender create(String name) {
		Sender sender = new Sender();
		sender.setName(name);
		this.getSession().save(sender);
		return sender;
	}
	
	@Override
	@Transactional
	public Sender update(Sender sender) {
		Sender merged = (Sender)this.getSession().merge(sender);
		return merged;
	}
		
	@Override
	@Transactional
	public void delete(Sender sender) {
		this.getSession().createQuery("update Device d set d.sender = null WHERE d.sender = :sender").setParameter("sender", sender).executeUpdate();
		this.getSession().delete(sender);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Set<Device> getDevices(Sender sender) {
		Query q = this.getSession().createQuery("from Device d JOIN FETCH d.sender WHERE d.sender = :sender", Device.class);
		return new HashSet<Device>(q.setParameter("sender", sender).getResultList());
	}
}
