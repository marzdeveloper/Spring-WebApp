package advPro.GestioneDispositivi.test.unit;

import static org.junit.jupiter.api.Assertions.*;

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
import advPro.GestioneDispositivi.Model.Entities.User;
import advPro.GestioneDispositivi.Model.Entities.Role;
import advPro.GestioneDispositivi.Model.Dao.UserDetailsDao;
import advPro.GestioneDispositivi.Model.Dao.RoleDao;

@TestMethodOrder(OrderAnnotation.class)
public class TestUserDao {
	
	@BeforeEach
	void setUp() throws Exception {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			Session s  = sf.openSession();
			
			s.beginTransaction();
			s.createQuery("Delete From User u").executeUpdate();
			s.createQuery("Delete From Role r").executeUpdate();
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
			UserDetailsDao userDetailsDao = ctx.getBean("userDetailsDao", UserDetailsDao.class);
			
			Session s  = sf.openSession();
			
			assertTrue(s.isOpen());
			
			s.beginTransaction();
			
			userDetailsDao.setSession(s);
			
			assertEquals(s, userDetailsDao.getSession());
			
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
	void testNoUserAtBeginning() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			UserDetailsDao userDetailsDao = ctx.getBean("userDetailsDao", UserDetailsDao.class);
			
			Session s  = sf.openSession();
			userDetailsDao.setSession(s);
			
			s.beginTransaction();
			try {
				List<User> allUsers = userDetailsDao.findAll();
				s.getTransaction().commit();
				assertEquals(allUsers.size(), 0);
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
	void testCreateUserEmptyFields() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			UserDetailsDao userDetailsDao = ctx.getBean("userDetailsDao", UserDetailsDao.class);
			RoleDao roleDao = ctx.getBean("roleDao", RoleDao.class);
						
			Session s  = sf.openSession();
			userDetailsDao.setSession(s);
			roleDao.setSession(s);
			
			s.beginTransaction();
			try {
				Role role = roleDao.create("USER");
				roleDao.update(role);
				
				User user = userDetailsDao.create(null, null, false, role);
				userDetailsDao.update(user);
				s.getTransaction().commit();
				assertTrue(true);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				s.getTransaction().rollback();
				assertTrue(false);
			}
			finally {
				s.close();
				ctx.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	@Order(4)
	void testCreateUserDuplicate() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			UserDetailsDao userDetailsDao = ctx.getBean("userDetailsDao", UserDetailsDao.class);
			RoleDao roleDao = ctx.getBean("roleDao", RoleDao.class);
			
			Session s  = sf.openSession();
			userDetailsDao.setSession(s);
			roleDao.setSession(s);
			
			s.beginTransaction();
			try {
				Role role = roleDao.create("USER");
				roleDao.update(role);
				
				User user1 = userDetailsDao.create("username1", "password", true, role);
				User user2 = userDetailsDao.create(user1.getUsername(), "password", true, role);
				userDetailsDao.update(user1);
				userDetailsDao.update(user2);
				
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
	void testCreateAndDeleteUser() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			UserDetailsDao userDetailsDao = ctx.getBean("userDetailsDao", UserDetailsDao.class);
			RoleDao roleDao = ctx.getBean("roleDao", RoleDao.class);
			
			Session s  = sf.openSession();
			userDetailsDao.setSession(s);
			roleDao.setSession(s);
			
			s.beginTransaction();
			try {
				Role role = roleDao.create("USER");
				roleDao.update(role);
				
				User user = userDetailsDao.create("username1", "password", true, role);
				userDetailsDao.update(user);
				
				userDetailsDao.delete(user);
				
				List<User> allUser = userDetailsDao.findAll();
				
				s.getTransaction().commit();
				assertEquals(allUser.size(), 0);
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
	void testGetRoles() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			UserDetailsDao userDetailsDao = ctx.getBean("userDetailsDao", UserDetailsDao.class);
			RoleDao roleDao = ctx.getBean("roleDao", RoleDao.class);
			
			Session s  = sf.openSession();
			userDetailsDao.setSession(s);
			roleDao.setSession(s);
			
			s.beginTransaction();
			try {
				Role role = roleDao.create("USER");
				roleDao.update(role);
				
				User user = userDetailsDao.create("username1", "password", true, role);
				User upd = userDetailsDao.update(user);
				s.getTransaction().commit();
				
				User find = userDetailsDao.findUserByUsername(upd.getUsername());
				assertTrue(find.getRoles().contains(role));
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
	void testCreateAndFindUser() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			UserDetailsDao userDetailsDao = ctx.getBean("userDetailsDao", UserDetailsDao.class);
			RoleDao roleDao = ctx.getBean("roleDao", RoleDao.class);
			
			Session s  = sf.openSession();
			userDetailsDao.setSession(s);
			roleDao.setSession(s);
			
			s.beginTransaction();
			try {
				Role role = roleDao.create("USER");
				roleDao.update(role);
				
				User user = userDetailsDao.create("username1", "password", true, role);
				User upd = userDetailsDao.update(user);
				
				User find = userDetailsDao.findUserByUsername(upd.getUsername());
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
	void testCreateAndUpdateUser() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			UserDetailsDao userDetailsDao = ctx.getBean("userDetailsDao", UserDetailsDao.class);
			RoleDao roleDao = ctx.getBean("roleDao", RoleDao.class);
			
			Session s  = sf.openSession();
			userDetailsDao.setSession(s);
			roleDao.setSession(s);
			
			s.beginTransaction();
			try {
				Role role = roleDao.create("USER");
				roleDao.update(role);
				
				User user = userDetailsDao.create("username1", "password", true, role);
				User upd = userDetailsDao.update(user);
				s.getTransaction().commit();
				
				if (userDetailsDao.findAll().size() != 1) {
					assertTrue(false);
				} else {
					s.beginTransaction();
					User find = userDetailsDao.findUserByUsername(upd.getUsername());
					find.setEnabled(false);
					find.setPassword(userDetailsDao.encryptPassword("gatto"));
					User upd2 = userDetailsDao.update(find);
					s.getTransaction().commit();

					User find2 = userDetailsDao.findUserByUsername(upd2.getUsername());
					assertEquals(find2.isEnabled(), false);
					assertEquals(find2.getPassword(), upd.getPassword());
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
	
	@Test
	@Order(9)
	void testCreateUserAndChangeUsername() {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigTest.class)) {
			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			UserDetailsDao userDetailsDao = ctx.getBean("userDetailsDao", UserDetailsDao.class);
			RoleDao roleDao = ctx.getBean("roleDao", RoleDao.class);
			
			Session s  = sf.openSession();
			userDetailsDao.setSession(s);
			roleDao.setSession(s);
			
			s.beginTransaction();
			try {
				Role role = roleDao.create("USER");
				roleDao.update(role);
				
				User user = userDetailsDao.create("username1", "password", true, role);
				User upd = userDetailsDao.update(user);
				s.getTransaction().commit();
				
				if (userDetailsDao.findAll().size() != 1) {
					assertTrue(false);
				} else {
					s.beginTransaction();
					User find = userDetailsDao.findUserByUsername(upd.getUsername());
					find.setUsername("username2");
					find.setEnabled(false);
					find.setPassword(userDetailsDao.encryptPassword("gatto"));
					User upd2 = userDetailsDao.update(find);
					s.getTransaction().commit();

					User find2 = userDetailsDao.findUserByUsername(upd2.getUsername());
					assertEquals(find2.getUsername(), "username2");
					assertEquals(find2.isEnabled(), false);
					assertEquals(find2.getPassword(), userDetailsDao.encryptPassword("gatto"));
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
