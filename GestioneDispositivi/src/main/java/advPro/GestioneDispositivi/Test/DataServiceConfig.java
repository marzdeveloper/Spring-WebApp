package advPro.GestioneDispositivi.Test;

import java.io.IOException;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan(basePackages = { "advPro.GestioneDispositivi.Model" })
@EnableTransactionManagement
public class DataServiceConfig {
	
	private static Logger logger = LoggerFactory.getLogger(DataServiceConfig.class);
			
	@Bean
	public DataSource dataSource() {
		try {DriverManagerDataSource ds = new DriverManagerDataSource();
			ds.setDriverClassName(com.mysql.cj.jdbc.Driver.class.getName());
		    ds.setUrl("jdbc:mysql://localhost:3307/GestioneDispositivi?createDatabaseIfNotExist=true");
		    ds.setUsername("root");
		    //ds.setPassword("p@ssw0rd");
		    return ds;
		} catch (Exception e) {
			logger.error("DataSource bean cannot be created!", e);
		    return null;
		}
	}
		   
	private Properties hibernateProperties() {
		Properties hibernateProp = new Properties();
		hibernateProp.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect"); 
		hibernateProp.put("hibernate.format_sql", true);
		hibernateProp.put("hibernate.use_sql_comments", true);
		hibernateProp.put("hibernate.show_sql", true);
		hibernateProp.put("hibernate.max_fetch_depth", 3);
		hibernateProp.put("hibernate.jdbc.batch_size", 10);
		hibernateProp.put("hibernate.jdbc.fetch_size", 50);
		hibernateProp.put("javax.persistence.schema-generation.database.action", "drop-and-create"); // importante, altrimenti si aspetta il DB gia` "strutturato"
		return hibernateProp;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
		   
	@Bean 
	public SessionFactory sessionFactory() throws IOException {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean = new LocalSessionFactoryBean();
	    sessionFactoryBean.setDataSource(dataSource());
	    sessionFactoryBean.setPackagesToScan("advPro.GestioneDispositivi.Model");
	    sessionFactoryBean.setHibernateProperties(hibernateProperties());
	    sessionFactoryBean.afterPropertiesSet();
	    return sessionFactoryBean.getObject();
	}
	
	@Bean 
	public PlatformTransactionManager transactionManager() throws IOException {
		return new HibernateTransactionManager(sessionFactory());
	}
	
}