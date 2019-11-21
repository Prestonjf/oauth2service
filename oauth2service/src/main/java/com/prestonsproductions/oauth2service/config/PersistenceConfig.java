/*
* This code is confidential proprietary trade secret of MiHIN. Copyright MiHIN 2019.
*/
package com.prestonsproductions.oauth2service.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.typesafe.config.Config;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.prestonsproductions.oauth2service.repository")
public class PersistenceConfig {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
 
	   @Bean
	   public LocalContainerEntityManagerFactoryBean entityManagerFactory(Config properties) {
	      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	      em.setDataSource(dataSource(properties));
	      em.setPackagesToScan("com.prestonsproductions.oauth2service");
	 
	      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	      em.setJpaVendorAdapter(vendorAdapter);
	      em.setJpaProperties(additionalProperties());
	      return em;
	   }
	 
	   @Bean
	   public DataSource dataSource(Config properties) {
	      ComboPooledDataSource cpds = new ComboPooledDataSource();
	      try {
			cpds.setDriverClass(properties.getString("com.prestonsproductions.oauth2service.DATABASE_DRIVER"));
			cpds.setJdbcUrl(properties.getString("com.prestonsproductions.oauth2service.DATABASE_URL"));
			cpds.setUser(properties.getString("com.prestonsproductions.oauth2service.DATABASE_USERNAME"));
			cpds.setPassword(properties.getString("com.prestonsproductions.oauth2service.DATABASE_PASSWORD"));
			
			cpds.setMinPoolSize(1);
			cpds.setInitialPoolSize(1);
			cpds.setMaxPoolSize(20);
			cpds.setMaxIdleTime(1800);
			cpds.setIdleConnectionTestPeriod(1900);
			cpds.setMaxStatements(50);
		} catch (PropertyVetoException e1) {
			logger.error("", e1);
		}
	      return cpds;
	   }
	 
	   @Bean
	   public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
	      JpaTransactionManager transactionManager = new JpaTransactionManager();
	      transactionManager.setEntityManagerFactory(emf);
	 
	      return transactionManager;
	   }
	 
	   @Bean
	   public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
	      return new PersistenceExceptionTranslationPostProcessor();
	   }
	 
	   Properties additionalProperties() {
	      Properties props = new Properties();
	      props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
	      props.setProperty("hibernate.show_sql", "false");
	      props.setProperty("hibernate.id.new_generator_mappings", "false");
	      return props;
	   }
}
