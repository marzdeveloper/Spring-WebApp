package advPro.GestioneDispositivi.test.unit;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import advPro.GestioneDispositivi.Model.Entities.Device;
import advPro.GestioneDispositivi.Model.Entities.Sender;
import advPro.GestioneDispositivi.Test.DataServiceConfigTest;
import advPro.GestioneDispositivi.Utils.Utils;
import advPro.GestioneDispositivi.Model.Dao.DeviceDao;
import advPro.GestioneDispositivi.Model.Dao.SenderDao;

@TestMethodOrder(OrderAnnotation.class)
public class TestSenderDao {
	
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
			SenderDao senderDao = ctx.getBean("senderDao", SenderDao.class);
			
			Session s  = sf.openSession();
			
			assertTrue(s.isOpen());
			
			s.beginTransaction();
			
			senderDao.setSession(s);
			
			assertEquals(s, senderDao.getSession());
			
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
	void testNoSenderAtBeginning() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			SenderDao senderDao = ctx.getBean("senderDao", SenderDao.class);
			
			Session s  = sf.openSession();
			senderDao.setSession(s);
			
			s.beginTransaction();
			try {
				List<Sender> allSender = senderDao.findAll();
				s.getTransaction().commit();
				assertEquals(allSender.size(), 0);
			}
			catch (Exception e) {
				s.getTransaction().rollback();
				assertTrue(false);
			}
			finally {
				s.close();
				ctx.close();
			}
		}
	}
	
	@Test
	@Order(3)
	void testCreateSenderEmptyFields() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			SenderDao senderDao = ctx.getBean("senderDao", SenderDao.class);
			
			Session s  = sf.openSession();
			senderDao.setSession(s);
			
			s.beginTransaction();
			
			try {
				Sender sender = senderDao.create(null);
				senderDao.update(sender);
				
				s.getTransaction().commit();
				assertTrue(true);
			} catch (Exception e) {
				s.getTransaction().rollback();
				assertTrue(false);
			}
			finally {
				s.close();
				ctx.close();
			}
		}
	}
	
	@Test
	@Order(4)
	void testCreateSenderDuplicate() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			SenderDao senderDao = ctx.getBean("senderDao", SenderDao.class);
			
			Session s  = sf.openSession();
			senderDao.setSession(s);
			
			s.beginTransaction();
			
			try {
				Sender sender1 = senderDao.create("Test-Sender1");
				Sender sender2 = senderDao.create(sender1.getName());
				
				senderDao.update(sender1);
				senderDao.update(sender2);
				
				s.getTransaction().commit();
				assertTrue(true);
			} catch (Exception e) {
				s.getTransaction().rollback();
				assertTrue(false);
			}
			finally {
				s.close();
				ctx.close();
			}
		}
	}
	
	@Test
	@Order(5)
	void testCreateSender() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			SenderDao senderDao = ctx.getBean("senderDao", SenderDao.class);
			
			Session s  = sf.openSession();
			senderDao.setSession(s);
			
			s.beginTransaction();
			try {
				Sender sender = senderDao.create("LENOVO");
				senderDao.update(sender);
				s.getTransaction().commit();
				assertTrue(true);
			} catch (Exception e) {
				s.getTransaction().rollback();
				assertTrue(false);
			}
			finally {
				s.close();
				ctx.close();
			}
		}
	}
	
	@Test
	@Order(6)
	void testCreateAndDeleteSender() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			SenderDao senderDao = ctx.getBean("senderDao", SenderDao.class);
			
			Session s  = sf.openSession();
			senderDao.setSession(s);
			
			s.beginTransaction();
			try {
				Sender sender = senderDao.create("Test-CreateDelete");
				Sender upd = senderDao.update(sender);
				
				senderDao.delete(upd);

				List<Sender> allSender = senderDao.findAll();
				
				s.getTransaction().commit();
				assertEquals(allSender.size(), 0);
			} 
			catch (Exception e) {
				s.getTransaction().rollback();
				assertTrue(false);
			}
			finally {
				s.close();
				ctx.close();
			}
		}
	}
	
	@Test
	@Order(7)
	void testGetDevices() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			SenderDao senderDao = ctx.getBean("senderDao", SenderDao.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			
			Session s= sf.openSession();
			senderDao.setSession(s);
			deviceDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Sender sender = senderDao.create("Sender");
				senderDao.update(sender);
				
				Device dev1 = deviceDao.create("nuovo1", "brand1", "model1", "reason", "document", Utils.date(dateFormat.format(date)), Utils.date(dateFormat.format(date)) , sender);
				Device dev2 = deviceDao.create("nuovo2", "brand2", "model2", "reason", "document", Utils.date(dateFormat.format(date)), Utils.date(dateFormat.format(date)) , sender);
				Device dev3 = deviceDao.create("nuovo3", "brand3", "model3", "reason", "document", Utils.date(dateFormat.format(date)), null , null);
				deviceDao.update(dev1);
				deviceDao.update(dev2);
				deviceDao.update(dev3);
				
				s.refresh(sender);
				
				Set<Device> SenderDev = sender.getDevices();
				
				s.getTransaction().commit();
				assertTrue(SenderDev.contains(dev1));
				assertTrue(SenderDev.contains(dev2));
				assertFalse(SenderDev.contains(dev3));
			}
			catch(Exception e) {
				s.getTransaction().rollback();
				assertTrue(false);
			}
			finally {
				s.close();
				ctx.close();
			}
		}
	}
	
	@Test
	@Order(8)
	void testCreateAndFindSender() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			SenderDao senderDao = ctx.getBean("senderDao", SenderDao.class);
			
			Session s  = sf.openSession();
			senderDao.setSession(s);
			
			s.beginTransaction();
			try {
				Sender sender = senderDao.create("Sender");
				Sender upd = senderDao.update(sender);
				
				Sender find = senderDao.findById(upd.getId());
				s.getTransaction().commit();
				assertNotEquals(find, null);
			}catch (Exception e) {
				s.getTransaction().rollback();
				fail("Exception not expected: " + e.getMessage());
			}
			finally {
				s.close();
				ctx.close();
			}
		}
	}
	
	@Test
	@Order(9)
	void testCreateAndUpdateSender() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			SenderDao senderDao = ctx.getBean("senderDao", SenderDao.class);
			
			Session s  = sf.openSession();
			senderDao.setSession(s);
			
			s.beginTransaction();
			try {
				Sender sender = senderDao.create("Test-update");
				Sender upd = senderDao.update(sender);
				
				if (senderDao.findAll().size() != 1) {
					assertTrue(false);
				} else {
					Sender find = senderDao.findById(upd.getId());
					find.setName("Sender2");

					Sender upd2 = senderDao.update(find);
					Sender find2 = senderDao.findById(upd2.getId());
					
					s.getTransaction().commit();
					assertEquals(find2.getName(), "Sender2");
				}
			} catch (Exception e) {
				s.getTransaction().rollback();
				assertTrue(false);
			}
			finally {
				s.close();
				ctx.close();
			}
		}
		catch(Exception e) {
			fail("Unexpected error: " + e);
		}
	}
}
