package advPro.GestioneDispositivi.Test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import advPro.GestioneDispositivi.Test.DataServiceConfig;
import advPro.GestioneDispositivi.Model.Dao.*;
import advPro.GestioneDispositivi.Model.Entities.*;
import advPro.GestioneDispositivi.Utils.Utils;


public class LoadData {
	
	public static void main( String[] args ) {
    	
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfig.class)) {

			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			SenderDao senderDao = ctx.getBean(SenderDao.class);
			DeviceDao deviceDao = ctx.getBean(DeviceDao.class);
			JobDao jobDao = ctx.getBean(JobDao.class);
			TeamDao teamDao = ctx.getBean(TeamDao.class);
			EmployeeDao employeeDao = ctx.getBean(EmployeeDao.class);
			WorkstationDao workstationDao = ctx.getBean(WorkstationDao.class);
		    LocationDao locationDao = ctx.getBean(LocationDao.class);
			PositioningDao positioningDao = ctx.getBean(PositioningDao.class);
			RoleDao roleDao = ctx.getBean(RoleDao.class);
			UserDetailsDao userDao = ctx.getBean(UserDetailsDao.class);
			
			//N.B. RICORDARSI DI ABILITARE LE ASSERTIONS DA ECLIPSE
			
			try (Session session = sf.openSession()) {
				
				senderDao.setSession(session);
				deviceDao.setSession(session);
				jobDao.setSession(session);
				teamDao.setSession(session);
				employeeDao.setSession(session);
				workstationDao.setSession(session);
				locationDao.setSession(session);
				positioningDao.setSession(session);
				
				session.beginTransaction();
				
				//setto i sender per i device
				Sender sender1 = senderDao.create("Dell");
				Sender sender2 = senderDao.create("Samsung");
				Sender sender3 = senderDao.create("Huawei");
				Sender sender4 = senderDao.create("Apple");
				
				//creo dei device
				Device device1 = deviceDao.create("itv54jxs924", "Samsung", "Galaxy S20", "Signature tests", "itv54jxs110919.txt", Utils.date("2019-12-01"), null, sender2);
				Device device2 = deviceDao.create("ihv98jxs124", "Samsung", "A10", "Certification", "itv54jxs130919.txt", Utils.date("2019-12-01"), null, sender2);
				Device device3 = deviceDao.create("ith569ght342", "Huawei", "P30", "Application layout tests", "itv54jxs211019.txt", Utils.date("2019-12-01"), null, sender3);
				

				assert sender1.getDevices().size()==0;
				assert sender2.getDevices().size()==0;
				assert sender3.getDevices().size()==0;
				assert sender4.getDevices().size()==0;
				
				//refresh per ricaricare le collezioni dei senders
				
				session.refresh(sender1);
				session.refresh(sender2);
				session.refresh(sender3);


				assert sender1.getDevices().size()==0;
				assert sender2.getDevices().size()==2;
				assert sender3.getDevices().size()==1;
				assert sender4.getDevices().size()==0;
				
				Device device4 = deviceDao.create("a747yte9594", "Apple", "IPhone10", "Signature tests", "itv54jxs120119.txt", Utils.date("2019-12-01"), null, sender4);
				device4.setSender(sender4);
				senderDao.update(sender4);
				
				session.refresh(sender4);
				assert sender4.getDevices().size()==1;
						
				
				session.getTransaction().commit();
				
				session.beginTransaction();
				
				//creo degli impiegati
				Employee employee1 = employeeDao.create("Mario", "Rossi");
				Employee employee2 = employeeDao.create("Giuseppe", "Verdi");
				Employee employee3 = employeeDao.create("Valentina", "Forti");
				Employee employee4 = employeeDao.create("Giovanna", "Guida");
				
				
				//assegno device1 a employee1 senza location
				Job job1 = jobDao.create("Layout check", Utils.date("2019-12-09"), null, device1, null, employee1);
				Positioning positioning1 = positioningDao.create( Utils.date("2019-12-09"), device1, null, employee1);
				
				session.refresh(employee1);
				session.refresh(device1);
				assert employee1.getJobs().contains(job1);
				assert employee1.getPositionings().contains(positioning1);
				assert device1.getJobs().contains(job1);
				assert device1.getPositionings().contains(positioning1);

				//assegno device2 a team1 con una location
				Team team1 = teamDao.create("Web Developers", "Development", "Team for web applications");
				Location location1 = locationDao.create("Desk101", "Desk in room3 corner top right");
				Job job2 = jobDao.create("Signature tests", Utils.date("2020-01-23"), null, device2, team1, null);
				Positioning positioning2 = positioningDao.create(Utils.date("2019-12-02"), device2, location1, null);

				session.refresh(team1);
				session.refresh(location1);
				session.refresh(device2);
				assert job2.getTeam().equals(team1);
				assert team1.getJobs().contains(job2);
				assert positioning2.getLocation().equals(location1);
				assert location1.getPositionings().contains(positioning2);
				assert device2.getJobs().contains(job2);
				assert device2.getPositionings().contains(positioning2);
				
				session.getTransaction().commit();
				
				session.beginTransaction();
				//si può anche creare un team senza location, se ogni dispositivo assegnato al team è usato esclusivasmente da un solo employee del team
			
				//assegno device3 a employee4 con una location
				Location location2 = locationDao.create("Locker303", "Locker in room 1 second from left");
				Job job3 = jobDao.create("Singature tests", Utils.date("2020-01-25"), null, device3, null, employee4);
				Positioning positioning3 = positioningDao.create(Utils.date("2019-01-25"), device3, location2, employee4);
				
				session.refresh(device3);
				session.refresh(location2);
				session.refresh(employee4);
				assert device3.getJobs().contains(job3);
				assert device3.getPositionings().contains(positioning3);
				assert employee4.getJobs().contains(job3);
				assert employee4.getPositionings().contains(positioning3);
				assert location2.getPositionings().contains(positioning3);
				
				//assegno device4 a una location senza employee
				Location location3 = locationDao.create("Desk102", "Desk in room3 second left from corner top right");
				Positioning positioning4 = positioningDao.create(Utils.date("2019-12-22"), device4, location3, null);
				
				session.refresh(device4);
				session.refresh(location3);
				assert device4.getPositionings().contains(positioning4);
				assert location3.getPositionings().contains(positioning4);
				assert positioning4.getLocation().equals(location3);
			
				//definisco quali employee fanno parte del team1
				assert employee2.getTeams().size()==0;
				assert employee3.getTeams().size()==0;
				assert team1.getEmployees().size()==0;
				
				team1.addEmployee(employee2);
				team1.addEmployee(employee3);
				teamDao.update(team1);
				
				assert employee2.getTeams().contains(team1);
				assert employee3.getTeams().contains(team1);
				assert team1.getEmployees().contains(employee2);
				assert team1.getEmployees().contains(employee3);
				
				//assegno le location agli employee
				Workstation workstation1 = workstationDao.create(Utils.date("2020-02-02"), null, employee2, location1);
				Workstation workstation2 = workstationDao.create(Utils.date("2020-02-08"), null, employee3, location1);
				Workstation workstation3 = workstationDao.create(Utils.date("2020-02-10"), null, employee4, location2);
				
				session.refresh(employee2);
				session.refresh(employee3);
				session.refresh(employee4);
				session.refresh(location1);
				session.refresh(location2);
				assert employee2.getWorkstations().contains(workstation1);
				assert employee3.getWorkstations().contains(workstation2);
				assert employee4.getWorkstations().contains(workstation3);
				assert location1.getWorkstations().contains(workstation1);
				assert location1.getWorkstations().contains(workstation2);
				assert location2.getWorkstations().contains(workstation3);

				session.getTransaction().commit();
				
				session.beginTransaction();

				//phase 2 : navigate data in the database
				
				List<Device> allDevices = deviceDao.findAll();
				
				System.out.println("Number of devices: " + allDevices.size());
				for (Device d : allDevices) {
					System.out.println(" - " + d.getBrand() + " : " + d.getCheckIn() + " : " + d.getCheckOut() + " : " + d.getDocument()+ 
							" : " + d.getModel() + " : " + d.getReason()+ " : " + d.getSerialNumber()+ " : " + d.getSender());
					
					Set<Job> jobs = deviceDao.getJobs(d);
					System.out.println("Number of job: " + jobs.size());
					for (Job j : jobs) {
						System.out.println("  - " + j.getDescription());					
					}
				}
				
				List<Employee> allEmployees = employeeDao.findAll();
				System.out.println("Number of employees: " + allEmployees.size());
				for (Employee em : allEmployees) {
					System.out.println(" - " + em.getName()+ " : " + em.getSurname() + " : "+ em.getPositionings() + " : "+ em.getWorkstations());
					Set<Team> teams = em.getTeams();
					
					if (teams == null) {
						teams= new HashSet<Team>();
					}
					
					System.out.println("Number of teams: " + teams.size());
					for (Team tm : teams) {
						System.out.println("  - " + tm.getDescription() + " - " + tm.getName() + " - " + tm.getType());
					}
				}
				
				session.getTransaction().commit();
				
				session.beginTransaction();
				//creo dei roles
				Role r1 = roleDao.create("USER");
				Role r2 = roleDao.create("ADMIN");
				
				session.getTransaction().commit();
				
				session.beginTransaction();
				//creo degli users
				User u1 = userDao.create("user1", "password", true, r1);				
				User u2 = userDao.create("user2", "password", true, r2);
				
				userDao.update(u1);
				userDao.update(u2);
				session.getTransaction().commit();
			}
		}
		 catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
	}

}
