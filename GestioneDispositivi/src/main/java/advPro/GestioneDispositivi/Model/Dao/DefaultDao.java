package advPro.GestioneDispositivi.Model.Dao;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class DefaultDao {
	
	private SessionFactory sessionFactory;
	private Session session;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	public Session getSession() {
		// 1. in case a shared session exists, return it (e.g. data generation script)
		Session session = this.session;
		if (session == null) {
			// 2. otherwise generate a new session using the factory (e.g. Spring MVC)
			session = this.sessionFactory.getCurrentSession();
		}
		return session;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}

}
