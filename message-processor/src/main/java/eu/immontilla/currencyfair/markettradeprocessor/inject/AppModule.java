package eu.immontilla.currencyfair.markettradeprocessor.inject;

import io.dropwizard.hibernate.HibernateBundle;

import org.hibernate.SessionFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import eu.immontilla.currencyfair.markettradeprocessor.configuration.AppConfiguration;
import eu.immontilla.currencyfair.markettradeprocessor.database.GenericDAO;
import eu.immontilla.currencyfair.markettradeprocessor.database.hibernate.MessageHibernateDAO;
import eu.immontilla.currencyfair.markettradeprocessor.database.hibernate.UserHibernateDAO;
import eu.immontilla.currencyfair.markettradeprocessor.facade.BaseFacade;
import eu.immontilla.currencyfair.markettradeprocessor.facade.MessageFacade;
import eu.immontilla.currencyfair.markettradeprocessor.facade.UserFacade;
import eu.immontilla.currencyfair.markettradeprocessor.model.Message;
import eu.immontilla.currencyfair.markettradeprocessor.model.User;

public class AppModule extends AbstractModule {

    private final HibernateBundle<AppConfiguration> hibernateBundle;

    public AppModule(HibernateBundle<AppConfiguration> hibernateBundle) {
        this.hibernateBundle = hibernateBundle;
    }

    @Override
    protected void configure() {
        bind(SessionFactory.class).toProvider(SessionFactoryProvider.class);
        bind(new TypeLiteral<GenericDAO<Message>>() {
        }).to(MessageHibernateDAO.class);
        bind(new TypeLiteral<GenericDAO<User>>() {
        }).to(UserHibernateDAO.class);
        bind(new TypeLiteral<BaseFacade<User>>() {
        }).to(UserFacade.class);
        bind(new TypeLiteral<BaseFacade<Message>>() {
        }).to(MessageFacade.class);
    }

    @Provides
    @Singleton
    private HibernateBundle<AppConfiguration> provideBundle() {
        return hibernateBundle;
    }
}
