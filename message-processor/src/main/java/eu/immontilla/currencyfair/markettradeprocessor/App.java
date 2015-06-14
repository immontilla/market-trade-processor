package eu.immontilla.currencyfair.markettradeprocessor;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.fasterxml.jackson.module.mrbean.MrBeanModule;

import eu.immontilla.currencyfair.markettradeprocessor.authentication.SimpleAuthenticator;
import eu.immontilla.currencyfair.markettradeprocessor.command.CreateMessagesCommand;
import eu.immontilla.currencyfair.markettradeprocessor.configuration.AppConfiguration;
import eu.immontilla.currencyfair.markettradeprocessor.database.GenericDAO;
import eu.immontilla.currencyfair.markettradeprocessor.database.hibernate.MessageHibernateDAO;
import eu.immontilla.currencyfair.markettradeprocessor.database.hibernate.UserHibernateDAO;
import eu.immontilla.currencyfair.markettradeprocessor.facade.BaseFacade;
import eu.immontilla.currencyfair.markettradeprocessor.facade.MessageFacade;
import eu.immontilla.currencyfair.markettradeprocessor.facade.UserFacade;
import eu.immontilla.currencyfair.markettradeprocessor.model.Message;
import eu.immontilla.currencyfair.markettradeprocessor.model.User;
import eu.immontilla.currencyfair.markettradeprocessor.resources.MessageResource;

/**
 * Application configuration
 * @author immontilla
 *
 */
public class App extends Application<AppConfiguration> {

    private final HibernateBundle<AppConfiguration> hibernate;

    public App() {
        this.hibernate = new HibernateBundle<AppConfiguration>(Message.class, User.class) {
            @Override
            public DataSourceFactory getDataSourceFactory(AppConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        bootstrap.getObjectMapper().registerModule(new MrBeanModule());
        bootstrap.addBundle(this.hibernate);
        bootstrap.addCommand(new CreateMessagesCommand());
    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) throws Exception {
        final GenericDAO<User> userDao = new UserHibernateDAO(this.hibernate.getSessionFactory());
        final BaseFacade<User> userFacade = new UserFacade(userDao);
        final GenericDAO<Message> dao = new MessageHibernateDAO(this.hibernate.getSessionFactory());
        final BaseFacade<Message> messageFacade = new MessageFacade(dao);
        Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
        environment.jersey().register(new MessageResource(messageFacade, configuration));        
        environment.jersey().register(AuthFactory.binder(new BasicAuthFactory<>(new SimpleAuthenticator(userFacade), "SECURITY REALM", User.class)));
    }

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }
}
