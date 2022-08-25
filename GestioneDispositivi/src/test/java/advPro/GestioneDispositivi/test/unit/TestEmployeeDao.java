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
import advPro.GestioneDispositivi.Model.Entities.Job;
import advPro.GestioneDispositivi.Model.Entities.Location;
import advPro.GestioneDispositivi.Model.Entities.Positioning;
import advPro.GestioneDispositivi.Model.Entities.Workstation;
import advPro.GestioneDispositivi.Model.Dao.DeviceDao;
import advPro.GestioneDispositivi.Model.Dao.EmployeeDao;
import advPro.GestioneDispositivi.Model.Dao.JobDao;
import advPro.GestioneDispositivi.Model.Dao.LocationDao;
import advPro.GestioneDispositivi.Model.Dao.PositioningDao;
import advPro.GestioneDispositivi.Model.Dao.WorkstationDao;

@TestMethodOrder(OrderAnnotation.class)
public class TestEmployeeDao {
	
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
			EmployeeDao employeeDao = ctx.getBean("employeeDao", EmployeeDao.class);
			
			Session s  = sf.openSession();
			
			assertTrue(s.isOpen());
			
			s.beginTransaction();
			
			employeeDao.setSession(s);
			
			assertEquals(s, employeeDao.getSession());
			
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
	void testNoEmployeesAtBeginning() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			EmployeeDao employeeDao = ctx.getBean("employeeDao", EmployeeDao.class);
			
			Session s  = sf.openSession();
			employeeDao.setSession(s);
			
			s.beginTransaction();
			try {
				List<Employee> allEmployee = employeeDao.findAll();
				s.getTransaction().commit();
				assertEquals(allEmployee.size(), 0);
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
	void testCreateEmployeeEmptyFields() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			EmployeeDao employeeDao = ctx.getBean("employeeDao", EmployeeDao.class);
			
			Session s  = sf.openSession();
			employeeDao.setSession(s);
			
			s.beginTransaction();
			try {
				employeeDao.create(null, null);
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
	void testCreateEmployeeDuplicate() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			EmployeeDao employeeDao = ctx.getBean("employeeDao",EmployeeDao.class);
			
			Session s  = sf.openSession();
			employeeDao.setSession(s);
			
			s.beginTransaction();
			try {
				Employee em1 = employeeDao.create("Mario", "Rossi");
				Employee em2 = employeeDao.create(em1.getName(), em1.getSurname());
				employeeDao.update(em1);
				employeeDao.update(em2);
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
	void testCreateandDeleteEmployee() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			EmployeeDao employeeDao = ctx.getBean("employeeDao",EmployeeDao.class);
			
			Session s  = sf.openSession();
			employeeDao.setSession(s);
			
			s.beginTransaction();
			try {
				Employee em = employeeDao.create("nome", "cognome");
				employeeDao.update(em);
				
				employeeDao.delete(em);
				
				List<Employee> allEmployee = employeeDao.findAll();
				s.getTransaction().commit();
				assertEquals(allEmployee.size(), 0);
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
				Employee em = employeeDao.create("Mario", "Bianchi");
				employeeDao.update(em);
				
				Location location = locationDao.create("location", null);
				locationDao.update(location);
				
				Workstation workstation = workstationDao.create(Utils.date(dateFormat.format(date)), null, em, location);
				workstationDao.update(workstation);
				
				s.refresh(em);
				s.getTransaction().commit();
				assertTrue(em.getWorkstations().contains(workstation));
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
	void testGetPositionings() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			PositioningDao positioningDao = ctx.getBean("positioningDao", PositioningDao.class);
			EmployeeDao employeeDao = ctx.getBean("employeeDao", EmployeeDao.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);

			Session s  = sf.openSession();
			positioningDao.setSession(s);
			employeeDao.setSession(s);
			deviceDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Employee em1 = employeeDao.create("Mario", "Bianchi");
				Employee em2 = employeeDao.create("Mario", "Verdi");
				employeeDao.update(em1);
				employeeDao.update(em2);
				
				Device dev = deviceDao.create("sn", "brand", "model", null, null, Utils.date(dateFormat.format(date)), null, null);
				deviceDao.update(dev);
				
				Positioning positioning = positioningDao.create(Utils.date(dateFormat.format(date)), dev, null, em1);
				positioningDao.update(positioning);
				
				s.refresh(em1);
				s.refresh(em2);
				s.getTransaction().commit();
				assertTrue(em1.getPositionings().contains(positioning));
				assertTrue(em2.getPositionings().isEmpty());
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
	void testFindAllWithJob(){
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			EmployeeDao employeeDao = ctx.getBean("employeeDao", EmployeeDao.class);
			JobDao jobDao = ctx.getBean("jobDao",JobDao.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);

			Session s  = sf.openSession();
			jobDao.setSession(s);
			employeeDao.setSession(s);
			deviceDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Employee em1 = employeeDao.create("Mario", "Bianchi");
				Employee em2 = employeeDao.create("Carlo", "Pasquali");
				Employee em3 = employeeDao.create("Mario", "Rossi");
				employeeDao.update(em1);
				employeeDao.update(em2);
				employeeDao.update(em3);
				
				Device dev1 = deviceDao.create("nuovo", "brand", "model", "reason", "document", Utils.date(dateFormat.format(date)), null , null);
				Device dev2 = deviceDao.create("nuovo", "brand", "model", "reason", "document", Utils.date(dateFormat.format(date)), null , null);
				deviceDao.update(dev1);
				deviceDao.update(dev2);
				
				Job job1 = jobDao.create("descrizione", Utils.date("2019-12-02"),  null, dev1, null, em1);
				Job job2 = jobDao.create("descrizione", Utils.date("2019-12-08"),  null, dev2, null, em2);
				jobDao.update(job1);
				jobDao.update(job2);

				s.refresh(em1);
				s.refresh(em2);
				s.refresh(em3);
				s.getTransaction().commit();
				assertTrue(employeeDao.findAllWithJob().contains(em1));
				assertTrue(employeeDao.findAllWithJob().contains(em2));
				assertFalse(employeeDao.findAllWithJob().contains(em3));
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
	void testGetJobs(){
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			EmployeeDao employeeDao = ctx.getBean("employeeDao", EmployeeDao.class);
			JobDao jobDao = ctx.getBean("jobDao",JobDao.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);

			Session s  = sf.openSession();
			jobDao.setSession(s);
			employeeDao.setSession(s);
			deviceDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Employee em1 = employeeDao.create("Mario", "Bianchi");
				Employee em2 = employeeDao.create("Carlo", "Pasquali");
				employeeDao.update(em1);
				employeeDao.update(em2);
				
				Device dev1 = deviceDao.create("nuovo", "brand", "model", "reason", "document", Utils.date(dateFormat.format(date)), null , null);
				Device dev2 = deviceDao.create("nuovo", "brand", "model", "reason", "document", Utils.date(dateFormat.format(date)), null , null);
				deviceDao.update(dev1);
				deviceDao.update(dev2);
				
				Job job1 = jobDao.create("lavoro1", Utils.date("2019-12-02"),  null, dev1, null, em1);
				Job job2 = jobDao.create("lavoro2", Utils.date("2019-12-08"),  null, dev2, null, em2);
				Job job3 = jobDao.create("lavoro3", Utils.date("2017-04-28"),  null, dev1, null, em2);
				jobDao.update(job1);
				jobDao.update(job2);
				jobDao.update(job3);
				
				s.refresh(em1);
				s.refresh(em2);
				s.getTransaction().commit();
				assertTrue(employeeDao.getJobs(em1).contains(job1));
				assertTrue(employeeDao.getJobs(em2).contains(job2));
				assertTrue(employeeDao.getJobs(em2).contains(job3));
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
	void testCreateAndFindEmployee() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			EmployeeDao employeeDao = ctx.getBean("employeeDao", EmployeeDao.class);
			
			Session s  = sf.openSession();
			employeeDao.setSession(s);
			
			s.beginTransaction();
			try {
				Employee em = employeeDao.create("Impiegato", "Prova");
				Employee upd = employeeDao.update(em);
				Employee find = employeeDao.findById(upd.getId());
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
	@Order(10)
	void testCreateAndUpdateEmployee() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			EmployeeDao employeeDao = ctx.getBean("employeeDao", EmployeeDao.class);
			
			Session s  = sf.openSession();
			employeeDao.setSession(s);
			
			s.beginTransaction();
			try {
				Employee em = employeeDao.create("Impiegato", "Prova");
				Employee upd = employeeDao.update(em);
				
				if (employeeDao.findAll().size() != 1) {
					assertTrue(false);
				} else {
					Employee find = employeeDao.findById(upd.getId());
					find.setName("Fabrizio");
					find.setSurname("Biondi");
					
					Employee upd2 = employeeDao.update(find);
					Employee find2 = employeeDao.findById(upd2.getId());
					s.getTransaction().commit();
					assertEquals(find2.getName(), "Fabrizio");
					assertEquals(find2.getSurname(), "Biondi");
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
