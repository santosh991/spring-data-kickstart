package com.acme;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author TODO
 */
@Configuration
@EnableJpaRepositories
public class ApplicationConfig {

	/**
	 * TODO: Change to the database type you're going to use.
	 */
	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder.setType(EmbeddedDatabaseType.HSQL).build();
	}

	/**
	 * TODO: Adapt {@link JpaVendorAdapter} in case you use anything else than Hibernate. TODO: Adapt
	 * {@link JpaVendorAdapter} in case you use a different database. TODO: Adapt package to scan if necessary.
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.HSQL);
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(getClass().getPackage().getName());
		factory.setDataSource(dataSource());

		return factory;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return txManager;
	}
}
