package advPro.GestioneDispositivi.test.unit;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import advPro.GestioneDispositivi.Model.Dao.DeviceDao;
import advPro.GestioneDispositivi.Model.Dao.JobDao;
import advPro.GestioneDispositivi.Model.Dao.PositioningDao;
import advPro.GestioneDispositivi.Model.Entities.Device;
import advPro.GestioneDispositivi.Model.Entities.Job;
import advPro.GestioneDispositivi.Model.Entities.Positioning;
import advPro.GestioneDispositivi.Model.Entities.Sender;
import advPro.GestioneDispositivi.Model.Dao.SenderDao;
import advPro.GestioneDispositivi.Test.DataServiceConfigTest;
import advPro.GestioneDispositivi.Utils.Utils;

@TestMethodOrder(OrderAnnotation.class)
public class TestDeviceDao {

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
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			
			Session s  = sf.openSession();
			
			assertTrue(s.isOpen());
			
			s.beginTransaction();
			
			deviceDao.setSession(s);
			
			assertEquals(s, deviceDao.getSession());
			
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
	void testBeginWithoutSpecifyingSession() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			Sender sender = ctx.getBean("senderDao", SenderDao.class).create("sender");
			
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			
			Date date = new Date();
			
			Device dev1 = deviceDao.create("serial1", "brand1", "model1", "reason1", "document1", date, date , null);
			
			sender.addDevice(dev1);
			
			deviceDao.update(dev1);
			
			Session s1 = deviceDao.getSession();
			assertFalse(s1.isOpen());
			
			ctx.close();
		} 
		catch (Exception e) {
			fail("Error unexpected: " + e);
		}
	}
	
	@Test
	@Order(3)
	void testNoDevicesAtBeginning() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			
			Session s  = sf.openSession();
			deviceDao.setSession(s);
			
			s.beginTransaction();
			try {
				List<Device> allDevice = deviceDao.findAll();
				s.getTransaction().commit();
				assertEquals(allDevice.size(), 0);
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
	@Order(4)
	void testCreateDeviceEmptyFields() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			
			Session s  = sf.openSession();
			deviceDao.setSession(s);
			
			s.beginTransaction();
			try {
				Device device = deviceDao.create(null, null, null, null, null, null, null, null);
				deviceDao.update(device);
				s.getTransaction().commit();
				assertTrue(true);
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
	@Order(5)
	void testCreateDeviceDuplicate() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			
			Session s  = sf.openSession();
			deviceDao.setSession(s);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Device device1 = deviceDao.create("serial1", "brand1", "model1", "reason1", "document1", Utils.date(dateFormat.format(date)), null, null);
				Device device2 = deviceDao.create(device1.getSerialNumber(), device1.getBrand(), device1.getModel(), device1.getReason(), device1.getDocument(), device1.getCheckIn(), device1.getCheckOut(), device1.getSender());
				deviceDao.update(device1);
				deviceDao.update(device2);
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
	void testCreateAndDeleteDevice() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			
			Session s  = sf.openSession();
			deviceDao.setSession(s);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Device dev = deviceDao.create("nuovo", "brand", "model", "reason", "document", Utils.date(dateFormat.format(date)), null, null);
				deviceDao.update(dev);
				
				deviceDao.delete(dev);
				
				List<Device> allDevice = deviceDao.findAll();
				s.getTransaction().commit();
				assertEquals(allDevice.size(), 0);
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
	void testGetPositionings(){
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			PositioningDao positioningDao = ctx.getBean(PositioningDao.class);
			
			Session s  = sf.openSession();
			deviceDao.setSession(s);
			positioningDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Device dev = deviceDao.create("nuovo", "brand", "model", "reason", "document", Utils.date(dateFormat.format(date)), null, null);
				deviceDao.update(dev);
				
				Positioning pos = positioningDao.create(Utils.date(dateFormat.format(date)), dev, null, null);
				positioningDao.update(pos);
				
				s.refresh(dev);
				s.getTransaction().commit();
				assertTrue(dev.getPositionings().contains(pos));
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
	void testGetJobs(){
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			JobDao jobDao = ctx.getBean("jobDao",JobDao.class);
			
			Session s  = sf.openSession();
			jobDao.setSession(s);
			deviceDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Device dev = deviceDao.create("nuovo", "brand", "model", "reason", "document", Utils.date(dateFormat.format(date)), null, null);
				deviceDao.update(dev);
				
				Job job = jobDao.create("descrizione", Utils.date("2019-12-02"),  null, dev, null, null);
				jobDao.update(job);
				
				s.refresh(dev);
				s.getTransaction().commit();
				assertTrue(dev.getJobs().contains(job));
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
	@Order(9)
	void testFindAllWithJob(){
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			JobDao jobDao = ctx.getBean("jobDao",JobDao.class);
			
			Session s  = sf.openSession();
			jobDao.setSession(s);
			deviceDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Device dev1 = deviceDao.create("nuovo1", "brand1", "model1", "reason1", "document1", Utils.date(dateFormat.format(date)), null, null);
				Device dev2 = deviceDao.create("nuovo2", "brand2", "model2", "reason2", "document2", Utils.date(dateFormat.format(date)), null, null);
				Device dev3 = deviceDao.create("nuovo3", "brand3", "model3", "reason3", "document3", Utils.date(dateFormat.format(date)), null, null);
				deviceDao.update(dev1);
				deviceDao.update(dev2);
				deviceDao.update(dev3);
				
				Job job1 = jobDao.create("descrizione1", Utils.date("2019-12-02"),  null, dev1, null, null);
				Job job2 = jobDao.create("descrizione2", Utils.date("2019-12-08"),  null, dev2, null, null);
				jobDao.update(job1);
				jobDao.update(job2);

				s.refresh(dev1);
				s.refresh(dev2);
				s.refresh(dev3);
				s.getTransaction().commit();
				assertTrue(deviceDao.findAllWithJob().contains(dev1));
				assertTrue(deviceDao.findAllWithJob().contains(dev2));
				assertFalse(deviceDao.findAllWithJob().contains(dev3));
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
	@Order(10)
	void testCreateAndFindDevice() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			
			Session s  = sf.openSession();
			deviceDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Device dev = deviceDao.create("nuovo", "brand", "model", "reason", "document", Utils.date(dateFormat.format(date)), null, null);
				Device upd = deviceDao.update(dev);
				
				Device find = deviceDao.findById(upd.getId());
				s.getTransaction().commit();
				assertNotEquals(find, null);
			} catch (Exception e) {
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
	@Order(11)
	void testCreateAndUpdateDevice() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			
			Session s  = sf.openSession();
			deviceDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Device dev = deviceDao.create("nuovo", "brand", "model", "reason", "document", Utils.date(dateFormat.format(date)), null, null);
				Device upd = deviceDao.update(dev);
				
				if (deviceDao.findAll().size() != 1) {
					assertTrue(false);
				} else {
					Device find = deviceDao.findById(upd.getId());
					find.setSerialNumber("dev2");
					find.setBrand("brand2");
					find.setModel("model2");
					find.setReason("reason2");
					find.setDocument("document2");
					find.setCheckIn(Utils.date("2020-01-02"));
					
					Device upd2 = deviceDao.update(find);
					Device find2 = deviceDao.findById(upd2.getId());
					s.getTransaction().commit();
					assertEquals(find2.getSerialNumber(), "dev2");
					assertEquals(find2.getBrand(), "brand2");
					assertEquals(find2.getModel(), "model2");
					assertEquals(find2.getReason(), "reason2");
					assertEquals(find2.getDocument(), "document2");
					assertEquals(find2.getCheckIn(), Utils.date("2020-01-02"));
				}
			} catch (Exception e) {
				s.getTransaction().rollback();
				fail("Exception not expected: " + e.getMessage());
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
