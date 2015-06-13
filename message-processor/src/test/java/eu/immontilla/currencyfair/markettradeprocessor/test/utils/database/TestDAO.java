package eu.immontilla.currencyfair.markettradeprocessor.test.utils.database;

import org.hibernate.cfg.Configuration;

public class TestDAO {
	private static Configuration config = new Configuration();

	public static Configuration getConfig() {
		config.setProperty("hibernate.connection.url", "jdbc:h2:./currencyfairtest");
		config.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
		config.setProperty("hibernate.current_session_context_class", "thread");
		config.setProperty("hibernate.show_sql", "false");
		config.setProperty("hibernate.hbm2ddl.auto", "create");
		return config;
	}

}
