package eu.immontilla.currencyfair.markettradeprocessor.inject;

import io.dropwizard.hibernate.HibernateBundle;

import javax.inject.Inject;
import javax.inject.Provider;

import org.hibernate.SessionFactory;

import eu.immontilla.currencyfair.markettradeprocessor.configuration.AppConfiguration;

public class SessionFactoryProvider implements Provider<SessionFactory> {

    private final HibernateBundle<AppConfiguration> hibernateBundle;

    @Inject
    public SessionFactoryProvider(HibernateBundle<AppConfiguration> hibernateBundle) {
        this.hibernateBundle = hibernateBundle;
    }

    @Override
    public SessionFactory get() {
        return hibernateBundle.getSessionFactory();
    }
}
