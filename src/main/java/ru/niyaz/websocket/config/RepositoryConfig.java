package ru.niyaz.websocket.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by user on 23.04.2016.
 */

@Configuration
@EnableTransactionManagement
@ComponentScan("ru.niyaz.websocket.repository")
public class RepositoryConfig {

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);
        builder.scanPackages("ru.niyaz.websocket.entity").addProperties(getHibernateProperties());
        return builder.buildSessionFactory();
    }

    private Properties getHibernateProperties() {
        Properties prop = new Properties();
        prop.put("hibernate.format_sql", "true");
        prop.put("hibernate.show_sql", "false");
        prop.put("hibernate.hbm2ddl.auto", "validate");
        prop.put("hibernate.connection.release_mode", "after_transaction");
        prop.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");
        return prop;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        DataSource dataSource = dsLookup.getDataSource("jdbc/MESSAGES_DB");
        return dataSource;
    }

    //Create a transaction manager
    @Bean(name = "transactionManager")
    public HibernateTransactionManager txManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

}
