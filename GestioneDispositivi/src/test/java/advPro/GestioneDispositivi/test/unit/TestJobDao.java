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
import advPro.GestioneDispositivi.Model.Entities.Team;
import advPro.GestioneDispositivi.Model.Dao.DeviceDao;
import advPro.GestioneDispositivi.Model.Dao.EmployeeDao;
import advPro.GestioneDispositivi.Model.Dao.JobDao;
import advPro.GestioneDispositivi.Model.Dao.TeamDao;

@TestMethodOrder(OrderAnnotation.class)
public class TestJobDao {

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
			JobDao jobDao = ctx.getBean("jobDao", JobDao.class);
			
			Session s  = sf.openSession();
			
			assertTrue(s.isOpen());
			
			s.beginTransaction();
			
			jobDao.setSession(s);
			
			assertEquals(s, jobDao.getSession());
			
			s.getTransaction().commit();
			
			assertFalse(s.getTransaction().isActive());
			
			s.close();
			ctx.close();
		}
	}
	
	@Test
	@Order(2)
	void testNoJobAtBeginning() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			JobDao jobDao = ctx.getBean("jobDao", JobDao.class);
			
			Session s  = sf.openSession();
			jobDao.setSession(s);
			
			s.beginTransaction();
			try {
				List<Job> allJob = jobDao.findAll();
				s.getTransaction().commit();
				assertEquals(allJob.size(), 0);
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
	void testCreateJobEmptyFields() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			JobDao jobDao = ctx.getBean("jobDao", JobDao.class);
						
			Session s  = sf.openSession();
			jobDao.setSession(s);
			
			s.beginTransaction();
			try {
				Job job = jobDao.create(null, null, null, null, null, null);
				jobDao.update(job);
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
	void testCreateJobDuplicate() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			JobDao jobDao = ctx.getBean("jobDao", JobDao.class);
			DeviceDao deviceDao= ctx.getBean("deviceDao", DeviceDao.class);
			TeamDao teamDao= ctx.getBean("teamDao", TeamDao.class);
			EmployeeDao employeeDao= ctx.getBean("employeeDao", EmployeeDao.class);

			Session s  = sf.openSession();
			jobDao.setSession(s);
			deviceDao.setSession(s);
			teamDao.setSession(s);
			employeeDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Device device = deviceDao.create("serial", "brand", "model", "reason", "document", Utils.date(dateFormat.format(date)), null, null);
				deviceDao.update(device);
				
				Team team = teamDao.create("team", null, null);
				teamDao.update(team);
				
				Employee employee = employeeDao.create("Mario", "Rossi");
				employeeDao.update(employee);
				
				Job job1 = jobDao.create("job1", Utils.date(dateFormat.format(date)), null, device, team, employee);
				Job job2 = jobDao.create(job1.getDescription(), job1.getStart(), job1.getEnd(), job1.getDevice(), job1.getTeam(), job1.getEmployee());
				jobDao.update(job1);
				jobDao.update(job2);
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
	void testCreateandDeleteJob() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			JobDao jobDao = ctx.getBean("jobDao", JobDao.class);
			DeviceDao deviceDao= ctx.getBean("deviceDao", DeviceDao.class);
			TeamDao teamDao= ctx.getBean("teamDao", TeamDao.class);
			EmployeeDao employeeDao= ctx.getBean("employeeDao", EmployeeDao.class);
			
			Session s  = sf.openSession();
			jobDao.setSession(s);
			deviceDao.setSession(s);
			teamDao.setSession(s);
			employeeDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Device device = deviceDao.create("serial", "brand", "model", "reason", "document", Utils.date(dateFormat.format(date)), null, null);
				deviceDao.update(device);
				
				Team team = teamDao.create("team", null, null);
				teamDao.update(team);
				
				Employee employee = employeeDao.create("Mario", "Rossi");
				employeeDao.update(employee);
				
				Job job = jobDao.create("job", Utils.date(dateFormat.format(date)), null, device, team, employee);
				jobDao.update(job);
				
				jobDao.delete(job);
				List<Job> allJob = jobDao.findAll();
				
				s.getTransaction().commit();
				assertEquals(allJob.size(), 0);
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
	void testfindAllByDeviceId() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			JobDao jobDao = ctx.getBean("jobDao",JobDao.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			
			Session s  = sf.openSession();
			jobDao.setSession(s);
			deviceDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Device dev1 = deviceDao.create("dev1", "brand1", "model1", "reason", "document", Utils.date(dateFormat.format(date)), null, null);
				Device dev2 = deviceDao.create("dev2", "brand2", "model2", "reason", "document", Utils.date(dateFormat.format(date)), null, null);
				deviceDao.update(dev1);
				deviceDao.update(dev2);
				
				Job job1 = jobDao.create("lavoro1", Utils.date("2019-12-02"),  null, dev1, null, null);
				Job job2 = jobDao.create("lavoro2", Utils.date("2019-12-08"),  null, dev2, null, null);
				Job job3 = jobDao.create("lavoro3", Utils.date("2017-04-28"),  null, dev1, null, null);
				jobDao.update(job1);
				jobDao.update(job2);
				jobDao.update(job3);

				s.refresh(dev1);
				s.refresh(dev2);
				
				List<Job> jobdev1 = jobDao.findAllByDeviceId(dev1.getId());
				List<Job> jobdev2 = jobDao.findAllByDeviceId(dev2.getId());

				s.getTransaction().commit();
				assertTrue(jobdev1.contains(job1));
				assertTrue(jobdev1.contains(job3));
				assertTrue(jobdev2.contains(job2));
				assertFalse(jobdev1.contains(job2));
				assertFalse(jobdev2.contains(job1));
				assertFalse(jobdev2.contains(job3));
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
	void testCreateAndFindJob() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			JobDao jobDao = ctx.getBean("jobDao",JobDao.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);

			Session s  = sf.openSession();
			jobDao.setSession(s);
			deviceDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();			
			try {
				Device dev = deviceDao.create("nuovo", "brand", "model", "reason", "document", Utils.date(dateFormat.format(date)), null, null);
				deviceDao.update(dev);
				
				Job job = jobDao.create("job", Utils.date(dateFormat.format(date)), null, dev, null, null);
				Job upd = jobDao.update(job);
				
				Job find = jobDao.findById(upd.getId());
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
	void testCreateAndUpdateJob() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			DeviceDao deviceDao = ctx.getBean("deviceDao", DeviceDao.class);
			JobDao jobDao = ctx.getBean("jobDao",JobDao.class);
			
			Session s  = sf.openSession();
			deviceDao.setSession(s);
			jobDao.setSession(s);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			s.beginTransaction();
			try {
				Device dev1 = deviceDao.create("dev1", "brand1", "model1", "reason1", "document1", Utils.date(dateFormat.format(date)), null , null);
				Device dev2 = deviceDao.create("dev2", "brand2", "model2", "reason2", "document2", Utils.date(dateFormat.format(date)), null , null);
				deviceDao.update(dev1);
				deviceDao.update(dev2);
				
				Job job = jobDao.create("job", Utils.date(dateFormat.format(date)), null, dev1, null, null);
				Job upd = jobDao.update(job);
				
				if (jobDao.findAll().size() != 1) {
					assertTrue(false);
				} else {
					Job find = jobDao.findById(upd.getId());
					find.setDescription("job2");
					find.setStart(Utils.date("2020-01-09"));
					find.setEnd(date);
					find.setDevice(dev2);
					Job upd2 = jobDao.update(find);
					
					Job find2 = jobDao.findById(upd2.getId());
					s.getTransaction().commit();
					assertTrue(find2.getDescription().equals("job2"));
					assertTrue(find2.getStart().equals(Utils.date("2020-01-09")));
					assertTrue(find2.getEnd().equals(date));
					assertTrue(find2.getDevice().equals(dev2));
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
