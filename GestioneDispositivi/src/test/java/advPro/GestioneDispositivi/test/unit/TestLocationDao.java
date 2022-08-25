package advPro.GestioneDispositivi.test.unit;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import advPro.GestioneDispositivi.Test.DataServiceConfigTest;
import advPro.GestioneDispositivi.Utils.Utils;
import advPro.GestioneDispositivi.Model.Entities.Device;
import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Entities.Location;
import advPro.GestioneDispositivi.Model.Entities.Positioning;
import advPro.GestioneDispositivi.Model.Entities.Workstation;
import advPro.GestioneDispositivi.Model.Dao.DeviceDao;
import advPro.GestioneDispositivi.Model.Dao.EmployeeDao;
import advPro.GestioneDispositivi.Model.Dao.LocationDao;
import advPro.GestioneDispositivi.Model.Dao.PositioningDao;
import advPro.GestioneDispositivi.Model.Dao.WorkstationDao;

@TestMethodOrder(OrderAnnotation.class)
public class TestLocationDao {

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
			LocationDao locationDao = ctx.getBean("locationDao", LocationDao.class);
			
			Session s  = sf.openSession();
			
			assertTrue(s.isOpen());
			
			s.beginTransaction();
			
			locationDao.setSession(s);
			
			assertEquals(s, locationDao.getSession());
			
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
	void testNoLocationAtBeginning() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			LocationDao locationDao = ctx.getBean("locationDao", LocationDao.class);
			
			Session s  = sf.openSession();
			locationDao.setSession(s);
			
			s.beginTransaction();
			try {
				List<Location> allLocation = locationDao.findAll();
				s.getTransaction().commit();
				assertEquals(allLocation.size(), 0);
			}
			catch (Exception e) {
				s.getTransaction().rollback();
				assertTrue(false);
			}
			finally{
				s.close();
				ctx.close();
			}
		}
	}

	@Test
	@Order(3)
	void testCreateLocationEmptyFields() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			LocationDao locationDao = ctx.getBean("locationDao", LocationDao.class);
						
			Session s  = sf.openSession();
			locationDao.setSession(s);
			
			s.beginTransaction();
			try {
				Location location = locationDao.create(null, null);
				locationDao.update(location);
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
	void testCreateLocationDuplicate() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			LocationDao locationDao = ctx.getBean("locationDao", LocationDao.class);
			
			Session s  = sf.openSession();
			locationDao.setSession(s);
			
			s.beginTransaction();
			try {
				Location location1 = locationDao.create("Name1", "Description1");
				Location location2 = locationDao.create(location1.getName(), location1.getDescription());
				
				locationDao.update(location1);
				locationDao.update(location2);
				
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
	void testCreateAndDeleteLocation() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			LocationDao locationDao = ctx.getBean("locationDao", LocationDao.class);
			
			Session s  = sf.openSession();
			locationDao.setSession(s);
			
			s.beginTransaction();
			try {
				Location location = locationDao.create("Nome", "Descrizione");
				Location upd = locationDao.update(location);
				
				locationDao.delete(upd);
				
				List<Location> allLocation = locationDao.findAll();
				
				s.getTransaction().commit();
				assertEquals(allLocation.size(), 0);
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
	void testGetPositionings() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PositioningDao positioningDao = ctx.getBean("positioningDao", PositioningDao.class);
			LocationDao locationDao = ctx.getBean("locationDao", LocationDao.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);

			Session s  = sf.openSession();
			positioningDao.setSession(s);
			locationDao.setSession(s);
			deviceDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Location location1 = locationDao.create("Location1", null);
				Location location2 = locationDao.create("Location2", null);
				locationDao.update(location1);
				locationDao.update(location2);
				
				Device dev = deviceDao.create("sn", "brand", "model", null, null, Utils.date(dateFormat.format(date)), null, null);
				deviceDao.update(dev);
				
				Positioning positioning = positioningDao.create(Utils.date(dateFormat.format(date)), dev, location1, null);
				positioningDao.update(positioning);
				
				s.refresh(location1);
				s.refresh(location2);
				s.getTransaction().commit();
				assertTrue(location1.getPositionings().contains(positioning));
				assertTrue(location2.getPositionings().isEmpty());
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
	void testGetWorkstations() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			WorkstationDao workstationDao = ctx.getBean("workstationDao", WorkstationDao.class);
			EmployeeDao employeeDao = ctx.getBean("employeeDao", EmployeeDao.class);
			LocationDao locationDao = ctx.getBean("locationDao", LocationDao.class);

			Session s  = sf.openSession();
			workstationDao.setSession(s);
			employeeDao.setSession(s);
			locationDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Employee em = employeeDao.create("Sandro", "Azzurri");
				employeeDao.update(em);
				
				Location location = locationDao.create("location", null);
				Location WithoutWorkstation = locationDao.create("location1", null);
				locationDao.update(location);
				locationDao.update(WithoutWorkstation);
				
				Workstation workstation = workstationDao.create(Utils.date(dateFormat.format(date)), null, em, location);
				workstationDao.update(workstation);
				
				s.refresh(location);
				s.refresh(WithoutWorkstation);
				s.getTransaction().commit();
				assertTrue(location.getWorkstations().contains(workstation));
				assertTrue(WithoutWorkstation.getWorkstations().isEmpty());
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
	void testCreateAndFindLocation() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			LocationDao locationDao = ctx.getBean("locationDao", LocationDao.class);
			
			Session s  = sf.openSession();
			locationDao.setSession(s);
			
			s.beginTransaction();
			try {
				Location location = locationDao.create("location", "descrizione");
				Location upd = locationDao.update(location);
				
				Location find = locationDao.findById(upd.getId());
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
	@Order(9)
	void testCreateAndUpdateLocation() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			LocationDao locationDao = ctx.getBean("locationDao", LocationDao.class);
			
			Session s  = sf.openSession();
			locationDao.setSession(s);
			
			s.beginTransaction();
			try {
				Location location = locationDao.create("location", "descrizione");
				Location upd = locationDao.update(location);
				int id = upd.getId();
				
				if (locationDao.findAll().size() != 1) {
					assertTrue(false);
				} else {
					Location find = locationDao.findById(id);
					find.setName("location2");
					find.setDescription("descrizione2");
					Location upd2 = locationDao.update(find);
					
					int id2 = upd2.getId();
					Location find2 = locationDao.findById(id2);
					
					s.getTransaction().commit();
					assertEquals(find2.getName(), "location2");
					assertEquals(find2.getDescription(), "descrizione2");
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
