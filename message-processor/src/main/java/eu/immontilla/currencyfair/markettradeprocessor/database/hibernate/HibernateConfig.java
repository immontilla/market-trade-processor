package eu.immontilla.currencyfair.markettradeprocessor.database.hibernate;

import io.dropwizard.db.DataSourceFactory;

import org.hibernate.cfg.Configuration;

import eu.immontilla.currencyfair.markettradeprocessor.configuration.AppConfiguration;

public class HibernateConfig {
    private static DataSourceFactory dataSourceFactory;

    private static Configuration dbConfig = new Configuration();

    public static Configuration getConfig(AppConfiguration config) {
        HibernateConfig.dataSourceFactory = config.getDataSourceFactory();
        dbConfig.setProperty("hibernate.connection.url", HibernateConfig.dataSourceFactory.getUrl());
        dbConfig.setProperty("hibernate.connection.driver_class", HibernateConfig.dataSourceFactory.getDriverClass());
        dbConfig.setProperty("hibernate.current_session_context_class", "thread");
        dbConfig.setProperty("hibernate.show_sql", "false");
        dbConfig.setProperty("hibernate.hbm2ddl.auto", "update");
        return dbConfig;

    }
}
