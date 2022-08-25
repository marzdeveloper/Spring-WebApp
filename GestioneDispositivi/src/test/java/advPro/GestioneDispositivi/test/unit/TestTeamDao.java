package advPro.GestioneDispositivi.test.unit;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import advPro.GestioneDispositivi.Test.DataServiceConfigTest;
import advPro.GestioneDispositivi.Utils.Utils;
import advPro.GestioneDispositivi.Model.Entities.Device;
import advPro.GestioneDispositivi.Model.Entities.Job;
import advPro.GestioneDispositivi.Model.Entities.Team;
import advPro.GestioneDispositivi.Model.Dao.DeviceDao;
import advPro.GestioneDispositivi.Model.Dao.JobDao;
import advPro.GestioneDispositivi.Model.Dao.TeamDao;

@TestMethodOrder(OrderAnnotation.class)
public class TestTeamDao {

	@BeforeEach
	void setUp() throws Exception {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			Session s  = sf.openSession();
			
			s.beginTransaction();
			s.createQuery("Delete From Workstation").executeUpdate();
			s.createQuery("Delete From Positioning p").executeUpdate();
			s.createQuery("Delete From Job j").executeUpdate();
			s.createQuery("Delete From Team t").executeUpdate();
			s.createQuery("Delete From Location l").executeUpdate();
			s.createQuery("Delete From Employee e").executeUpdate();
			s.createQuery("Delete From Device d").executeUpdate();
			s.createQuery("Delete From Sender s").executeUpdate();
			s.getTransaction().commit();
		} catch (Exception e) {
			//System.out.println(e.getMessage());
			assertTrue(false);
		}
	}
	
	@Test
	@Order(1)
	void testBeginCommitTransaction() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			TeamDao teamDao = ctx.getBean("teamDao", TeamDao.class);
			
			Session s  = sf.openSession();
			
			assertTrue(s.isOpen());
			
			s.beginTransaction();
			
			teamDao.setSession(s);
			
			assertEquals(s, teamDao.getSession());
			
			s.getTransaction().commit();
			
			assertFalse(s.getTransaction().isActive());
			
			s.close();
			ctx.close();
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	@Order(2)
	void testNoTeamAtBeginning() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			TeamDao teamDao = ctx.getBean("teamDao", TeamDao.class);
			
			Session s  = sf.openSession();
			teamDao.setSession(s);
			
			s.beginTransaction();
			try {
				List<Team> allTeam = teamDao.findAll();
				s.getTransaction().commit();
				assertEquals(allTeam.size(), 0);
			}
			catch (Exception e) {
				s.getTransaction().rollback();
				assertTrue(false);
			}
			finally {
				s.getTransaction().rollback();
				s.close();
				ctx.close();
			}
		}
	}

	@Test
	@Order(3)
	void testCreateTeamEmptyFields() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			TeamDao teamDao = ctx.getBean("teamDao", TeamDao.class);
						
			Session s  = sf.openSession();
			teamDao.setSession(s);
			
			s.beginTransaction();
			try {
				Team team = teamDao.create(null, null, null);
				teamDao.update(team);
				s.getTransaction().commit();
				assertTrue(true);
			} catch (Exception e) {
				s.getTransaction().rollback();
				assertTrue(false);
			}
			finally {
				s.getTransaction().rollback();
				s.close();
				ctx.close();
			}
		}
	}

	@Test
	@Order(4)
	void testCreateTeamDuplicate() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			TeamDao teamDao = ctx.getBean("teamDao", TeamDao.class);
			
			Session s  = sf.openSession();
			teamDao.setSession(s);
			
			s.beginTransaction();
			try {
				Team team1 = teamDao.create("Team", null, null);
				Team team2 = teamDao.create(team1.getName(), team1.getType(), team1.getDescription());
				
				teamDao.update(team1);
				teamDao.update(team2);
				
				s.getTransaction().commit();
				assertTrue(true);
			} catch (Exception e) {
				s.getTransaction().rollback();
				assertTrue(false);
			}
			finally {
				s.getTransaction().rollback();
				s.close();
				ctx.close();
			}
		}	
	}
	
	@Test
	@Order(5)
	void testCreateAndDeleteTeam() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			TeamDao teamDao = ctx.getBean("teamDao", TeamDao.class);
			
			Session s  = sf.openSession();
			teamDao.setSession(s);
			
			s.beginTransaction();
			try {
				Team team = teamDao.create("Team", null, null);
				Team upd = teamDao.update(team);
			
				teamDao.delete(upd);

				List<Team> allTeam = teamDao.findAll();
				s.getTransaction().commit();
				assertEquals(allTeam.size(), 0);
			} 
			catch (Exception e) {
				s.getTransaction().rollback();
				assertTrue(false);
			}
			finally {
				s.getTransaction().rollback();
				s.close();
				ctx.close();
			}
		}
	}
	
	@Test
	@Order(6)
	void testGetJobs(){
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			JobDao jobDao = ctx.getBean("jobDao",JobDao.class);
			TeamDao teamDao = ctx.getBean("teamDao", TeamDao.class);
			
			Session s  = sf.openSession();
			jobDao.setSession(s);
			deviceDao.setSession(s);
			teamDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Device dev = deviceDao.create("nuovo", "brand", "model", "reason", "document", Utils.date(dateFormat.format(date)), null , null);
				deviceDao.update(dev);
				
				Team team = teamDao.create("Team1", "tipo1", "descrizione");
				teamDao.update(team);
				
				Job job = jobDao.create("descrizione", Utils.date("2019-12-02"),  null, dev, team, null);
				jobDao.update(job);
				
				s.refresh(team);
				s.getTransaction().commit();
				assertTrue(team.getJobs().contains(job));
			}
			catch(Exception e) {
				s.getTransaction().rollback();
				assertTrue(false);
			}
			finally {
				s.getTransaction().rollback();
				s.close();
				ctx.close();
			}
		}
	}
	
	@Test
	@Order(7)
	void testCreateAndFindTeam() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			TeamDao teamDao = ctx.getBean("teamDao", TeamDao.class);
			
			Session s  = sf.openSession();
			teamDao.setSession(s);

			s.beginTransaction();
			try {
				Team team = teamDao.create("Team1", null, null);
				Team upd = teamDao.update(team);
				
				Team find = teamDao.findById(upd.getId());
				s.getTransaction().commit();
				assertNotEquals(find, null);
			} catch (Exception e) {
				s.getTransaction().rollback();
				fail("Exception not expected: " + e.getMessage());
			}
			finally {
				s.getTransaction().rollback();
				s.close();
				ctx.close();
			}
		}
	}
	
	@Test
	@Order(8)
	void testCreateAndUpdateTeam() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			TeamDao teamDao = ctx.getBean("teamDao", TeamDao.class);
			
			Session s  = sf.openSession();
			teamDao.setSession(s);
			
			s.beginTransaction();
			try {
				Team team = teamDao.create("Team1", "Type1", "Description1");
				Team upd = teamDao.update(team);
				
				if (teamDao.findAll().size() != 1) {
					assertTrue(false);
				} else {
					Team find = teamDao.findById(upd.getId());
					find.setName("Team2");
					find.setType("Type2");
					find.setDescription("Description2");
					
					Team upd2 = teamDao.update(find);
					Team find2 = teamDao.findById(upd2.getId());
					
					s.getTransaction().commit();
					assertEquals(find2.getName(), "Team2");
					assertEquals(find2.getType(), "Type2");
					assertEquals(find2.getDescription(), "Description2");
				}
			} catch (Exception e) {
				s.getTransaction().rollback();
				fail("Exception not expected: " + e.getMessage());
			}
			finally {
				s.getTransaction().rollback();
				s.close();
				ctx.close();
			}
		}
		catch(Exception e) {
			fail("Unexpected error: " + e);
		}
	}
}
