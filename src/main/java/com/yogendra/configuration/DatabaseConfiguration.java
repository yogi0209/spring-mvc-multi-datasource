package com.yogendra.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class DatabaseConfiguration {

	@Autowired
	private Environment env;

	@Bean("datasource1")
	public DataSource dataSource1() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("app1.db.driverClassName"));
		dataSource.setUrl(env.getProperty("app1.db.URL"));
		dataSource.setUsername(env.getProperty("app1.db.username"));
		dataSource.setPassword(env.getProperty("app1.db.password"));
		return dataSource;
	}
	
	@Bean("datasource2")
	public DataSource dataSource2() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("app2.db.driverClassName"));
		dataSource.setUrl(env.getProperty("app2.db.URL"));
		dataSource.setUsername(env.getProperty("app2.db.username"));
		dataSource.setPassword(env.getProperty("app2.db.password"));
		return dataSource;
	}

	@Bean("jdbc1")
	public JdbcTemplate jdbcTemplate1() {
		return new JdbcTemplate(dataSource1());
	}
	
	@Bean("jdbc2")
	public JdbcTemplate jdbcTemplate2() {
		return new JdbcTemplate(dataSource2());
	}

	@Bean("session1")
	public LocalSessionFactoryBean sessionFactory1() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource1());
		sessionFactory.setPackagesToScan(new String[] { "com.yogendra" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}
	
	@Bean("session2")
	public LocalSessionFactoryBean sessionFactory2() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource2());
		sessionFactory.setPackagesToScan(new String[] { "com.yogendra" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean("tx1")
	public HibernateTransactionManager transactionManager1() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory1().getObject());
		return transactionManager;
	}

	@Bean("tx2")
	public HibernateTransactionManager transactionManager2() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory2().getObject());
		return transactionManager;
	}
	
	@SuppressWarnings("serial")
	private Properties hibernateProperties() {
		return new Properties() {
			{
				setProperty("hibernate.hbm2ddl.auto", "none");
				setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
				setProperty("hibernate.show_sql", "true");
			}
		};
	}

}
