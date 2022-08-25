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
import advPro.GestioneDispositivi.Model.Entities.Location;
import advPro.GestioneDispositivi.Model.Dao.LocationDao;
import advPro.GestioneDispositivi.Model.Entities.Employee;
import advPro.GestioneDispositivi.Model.Dao.EmployeeDao;
import advPro.GestioneDispositivi.Model.Entities.Workstation;
import advPro.GestioneDispositivi.Model.Dao.WorkstationDao;

@TestMethodOrder(OrderAnnotation.class)
public class TestWorkstationDao {

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
			WorkstationDao workstationDao = ctx.getBean("workstationDao", WorkstationDao.class);
			
			Session s  = sf.openSession();
			
			assertTrue(s.isOpen());
			
			s.beginTransaction();
			
			workstationDao.setSession(s);
			
			assertEquals(s, workstationDao.getSession());
			
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
	void testNoWorkstationAtBeginning() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			WorkstationDao workstationDao = ctx.getBean("workstationDao", WorkstationDao.class);
			
			Session s  = sf.openSession();
			workstationDao.setSession(s);
			
			s.beginTransaction();
			try {
				List<Workstation> allWorkstation = workstationDao.findAll();
				s.getTransaction().commit();
				assertEquals(allWorkstation.size(), 0);
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
	void testCreateWorkstationEmptyFields() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			WorkstationDao workstationDao = ctx.getBean("workstationDao", WorkstationDao.class);
						
			Session s  = sf.openSession();
			workstationDao.setSession(s);
			
			s.beginTransaction();
			try {
				Workstation workstation = workstationDao.create(null, null, null, null);
				workstationDao.update(workstation);
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
	void testCreateWorkstationDuplicate() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			EmployeeDao employeeDao = ctx.getBean("employeeDao", EmployeeDao.class);
			LocationDao locationDao = ctx.getBean("locationDao", LocationDao.class);
			WorkstationDao workstationDao = ctx.getBean("workstationDao", WorkstationDao.class);
			
			Session s  = sf.openSession();
			workstationDao.setSession(s);
			locationDao.setSession(s);
			employeeDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Location location1 = locationDao.create("Location1", null);
				Employee employee1 = employeeDao.create("Mario", "Rossi");
				
				Workstation workstation1 = workstationDao.create(Utils.date(dateFormat.format(date)), Utils.date(dateFormat.format(date)), employee1, location1);
				Workstation workstation2 = workstationDao.create(workstation1.getStart(), workstation1.getEnd(), workstation1.getEmployee(), workstation1.getLocation());

				workstationDao.update(workstation1);
				workstationDao.update(workstation2);
				
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
	void testCreateAndDeleteWorkstation() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			LocationDao locationDao = ctx.getBean("locationDao", LocationDao.class);
			WorkstationDao workstationDao = ctx.getBean("workstationDao", WorkstationDao.class);
			EmployeeDao employeeDao = ctx.getBean("employeeDao", EmployeeDao.class);
			
			Session s  = sf.openSession();
			locationDao.setSession(s);
			workstationDao.setSession(s);
			employeeDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Location location = locationDao.create("Nome", "Descrizione");
				Employee employee = employeeDao.create("Mario", "Rossi");
				Workstation workstation = workstationDao.create(Utils.date(dateFormat.format(date)), null, employee, location);
				
				workstationDao.delete(workstation);
				
				List<Workstation> allWorkstation= workstationDao.findAll();
				s.getTransaction().commit();
				assertEquals(allWorkstation.size(), 0);
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
	void testCreateAndFindWorkstation() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			EmployeeDao employeeDao = ctx.getBean("employeeDao", EmployeeDao.class);					
			LocationDao locationDao = ctx.getBean("locationDao", LocationDao.class);
			WorkstationDao workstationDao = ctx.getBean("workstationDao", WorkstationDao.class);
			
			Session s  = sf.openSession();
			employeeDao.setSession(s);
			locationDao.setSession(s);
			workstationDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Location location = locationDao.create("location", "descrizione");
				Employee em = employeeDao.create("Giulio", "Marsi");
				Workstation workstation = workstationDao.create(Utils.date(dateFormat.format(date)), null, em, location);
				Workstation upd = workstationDao.update(workstation);
				
				Workstation prova = workstationDao.findById(upd.getId());
				s.getTransaction().commit();
				assertNotEquals(prova, null);
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
	@Order(7)
	void testCreateAndUpdateWorkstation() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			EmployeeDao employeeDao = ctx.getBean("employeeDao", EmployeeDao.class);					
			LocationDao locationDao = ctx.getBean("locationDao", LocationDao.class);
			WorkstationDao workstationDao = ctx.getBean("workstationDao", WorkstationDao.class);
			
			Session s  = sf.openSession();
			employeeDao.setSession(s);
			locationDao.setSession(s);
			workstationDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Location location1 = locationDao.create("location1", "descrizione1");
				Location location2 = locationDao.create("location2", "descrizione2");

				Employee em1 = employeeDao.create("Giulio", "Marsi");
				Employee em2 = employeeDao.create("Laura", "Bravi");

				Workstation workstation = workstationDao.create(Utils.date(dateFormat.format(date)), null, em1, location1);
				Workstation upd = workstationDao.update(workstation);
				int id = upd.getId();
				if (workstationDao.findAll().size() != 1) {
					assertTrue(false);
				} else {
					Workstation find = workstationDao.findById(id);
					find.setStart(Utils.date("2019-04-05"));
					find.setEnd(Utils.date("2020-09-21"));
					find.setEmployee(em2);
					find.setLocation(location2);

					Workstation upd2 = workstationDao.update(find);
					int id2 = upd2.getId();
					Workstation find2 = workstationDao.findById(id2);
					
					s.getTransaction().commit();
					assertEquals(find2.getStart(), Utils.date("2019-04-05"));
					assertEquals(find2.getEnd(), Utils.date("2020-09-21"));
					assertEquals(find2.getEmployee(), em2);
					assertEquals(find2.getLocation(), location2);
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

