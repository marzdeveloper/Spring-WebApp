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
import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Location;
import advPro.GestioneDispositivi.Model.Entities.Positioning;
import advPro.GestioneDispositivi.Model.Dao.DeviceDao;
import advPro.GestioneDispositivi.Model.Dao.EmployeeDao;
import advPro.GestioneDispositivi.Model.Dao.PositioningDao;
import advPro.GestioneDispositivi.Model.Dao.LocationDao;

@TestMethodOrder(OrderAnnotation.class)
public class TestPositioningDao {

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
			PositioningDao positioningDao = ctx.getBean("positioningDao", PositioningDao.class);
			
			Session s  = sf.openSession();
			
			assertTrue(s.isOpen());
			
			s.beginTransaction();
			
			positioningDao.setSession(s);
			
			assertEquals(s, positioningDao.getSession());
			
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
	void testNoPositioningAtBeginning() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PositioningDao positioningDao = ctx.getBean("positioningDao", PositioningDao.class);
			
			Session s  = sf.openSession();
			positioningDao.setSession(s);
			
			s.beginTransaction();
			try {
				List<Positioning> allPositioning = positioningDao.findAll();
				s.getTransaction().commit();
				assertEquals(allPositioning.size(), 0);
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
	void testCreatePositioningEmptyFields() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PositioningDao positioningDao = ctx.getBean("positioningDao", PositioningDao.class);
						
			Session s  = sf.openSession();
			positioningDao.setSession(s);
			
			s.beginTransaction();
			try {
				Positioning positioning = positioningDao.create(null, null, null, null);
				positioningDao.update(positioning);
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
	void testCreatePositioningDuplicate() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PositioningDao positioningDao = ctx.getBean("positioningDao", PositioningDao.class);
			DeviceDao deviceDao= ctx.getBean("deviceDao", DeviceDao.class);
			LocationDao locationDao= ctx.getBean("locationDao", LocationDao.class);
			EmployeeDao employeeDao= ctx.getBean("employeeDao", EmployeeDao.class);

			Session s  = sf.openSession();
			positioningDao.setSession(s);
			deviceDao.setSession(s);
			locationDao.setSession(s);
			employeeDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Device device = deviceDao.create("serial", "brand", "model", "reason", "document", Utils.date(dateFormat.format(date)), null, null);
				deviceDao.update(device);
				
				Employee employee = employeeDao.create("Mario", "Rossi");
				employeeDao.update(employee);
				
				Location location = locationDao.create("location", null);
				locationDao.update(location);
				
				Positioning positioning1= positioningDao.create(Utils.date(dateFormat.format(date)), device, location, employee);
				Positioning positioning2 = positioningDao.create(positioning1.getDatePositioning(), positioning1.getDevice(), positioning1.getLocation(), positioning1.getEmployee());
				positioningDao.update(positioning1);
				positioningDao.update(positioning2);
				
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
	void testCreateAndDeletePositioning() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PositioningDao positioningDao = ctx.getBean("positioningDao", PositioningDao.class);
			DeviceDao deviceDao= ctx.getBean("deviceDao", DeviceDao.class);
			LocationDao locationDao= ctx.getBean("locationDao", LocationDao.class);
			EmployeeDao employeeDao= ctx.getBean("employeeDao", EmployeeDao.class);
			
			Session s  = sf.openSession();
			positioningDao.setSession(s);
			deviceDao.setSession(s);
			locationDao.setSession(s);
			employeeDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Device device = deviceDao.create("serial", "brand", "model", "reason", "document", Utils.date(dateFormat.format(date)), null, null);
				deviceDao.update(device);
				
				Employee employee = employeeDao.create("Mario", "Rossi");
				employeeDao.update(employee);
				
				Location location = locationDao.create("location", null);
				locationDao.update(location);
				
				Positioning positioning= positioningDao.create(Utils.date(dateFormat.format(date)), device, location, employee);
				positioningDao.update(positioning);
				positioningDao.delete(positioning);

				List<Positioning> allPositioning = positioningDao.findAll();
				
				s.getTransaction().commit();
				assertEquals(allPositioning.size(), 0);
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
	@Order(6)
	void testFindAllByDeviceId() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PositioningDao positioningDao = ctx.getBean("positioningDao", PositioningDao.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			
			Session s  = sf.openSession();
			positioningDao.setSession(s);
			deviceDao.setSession(s);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Device dev1 = deviceDao.create("dev1", "brand1", "model1", "reason", "document", Utils.date(dateFormat.format(date)), null , null);
				Device dev2 = deviceDao.create("dev2", "brand2", "model2", "reason", "document", Utils.date(dateFormat.format(date)), null , null);
				deviceDao.update(dev1);
				deviceDao.update(dev2);
				
				Positioning positiong1 = positioningDao.create(Utils.date(dateFormat.format(date)), dev1, null, null);
				Positioning positiong2 = positioningDao.create(Utils.date(dateFormat.format(date)), dev2, null, null);
				positioningDao.update(positiong1);
				positioningDao.update(positiong2);

				s.refresh(dev1);
				s.refresh(dev2);
				
				List <Positioning> posdev1 = positioningDao.findAllByDeviceId(dev1.getId());
				List <Positioning> posdev2 = positioningDao.findAllByDeviceId(dev2.getId());
				
				
				s.getTransaction().commit();
				assertTrue(posdev1.contains(positiong1));
				assertTrue(posdev2.contains(positiong2));
				assertFalse(posdev1.contains(positiong2));
				assertFalse(posdev2.contains(positiong1));
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
	@Order(7)
	void testCreateAndFindPositioning() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			PositioningDao positioningDao = ctx.getBean("positioningDao", PositioningDao.class);
			
			Session s  = sf.openSession();
			deviceDao.setSession(s);
			positioningDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Device dev = deviceDao.create("nuovo", "brand", "model", "reason", "document", Utils.date(dateFormat.format(date)), null , null);
				deviceDao.update(dev);
				
				Positioning positioning = positioningDao.create(Utils.date(dateFormat.format(date)), dev, null, null);
				Positioning upd = positioningDao.update(positioning);
				
				Positioning find = positioningDao.findById(upd.getId());
				
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
	@Order(8)
	void testCreateAndUpdatePositioning() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			PositioningDao positioningDao = ctx.getBean("positioningDao", PositioningDao.class);

			Session s  = sf.openSession();
			deviceDao.setSession(s);
			positioningDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Device dev1 = deviceDao.create("dev1", "brand1", "model1", "reason1", "document1", Utils.date(dateFormat.format(date)), null, null);
				Device dev2 = deviceDao.create("dev2", "brand2", "model2", "reason2", "document2", Utils.date(dateFormat.format(date)), null, null);
				deviceDao.update(dev1);
				deviceDao.update(dev2);
				
				Positioning positioning = positioningDao.create(Utils.date(dateFormat.format(date)), dev1, null, null);
				Positioning upd = positioningDao.update(positioning);
				
				if (positioningDao.findAll().size() != 1) {
					assertTrue(false);
				} else {
					Positioning find = positioningDao.findById(upd.getId());
					find.setDatePositioning(Utils.date("2019-08-05"));
					find.setDevice(dev2);
					
					Positioning upd2 = positioningDao.update(find);
					Positioning find2 = positioningDao.findById(upd2.getId());
					
					s.getTransaction().commit();
					assertEquals(find2.getDatePositioning(), Utils.date("2019-08-05"));
					assertEquals(find2.getDevice(), dev2);
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
