package eu.immontilla.currencyfair.markettradeprocessor.test.database.hibernate;

import static org.junit.Assert.assertEquals;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.immontilla.currencyfair.markettradeprocessor.database.GenericDAO;
import eu.immontilla.currencyfair.markettradeprocessor.database.hibernate.UserHibernateDAO;
import eu.immontilla.currencyfair.markettradeprocessor.model.User;
import eu.immontilla.currencyfair.markettradeprocessor.test.utils.ModelFactory;
import eu.immontilla.currencyfair.markettradeprocessor.test.utils.database.TestDAO;

public class TestUserDAO  {
    private static GenericDAO<User> dao;
    private static SessionFactory factory;
    private static ModelFactory<User> userFactory;

	@BeforeClass
	public static void setupClass() {
		Configuration configuration = TestDAO.getConfig();
		configuration.addAnnotatedClass(User.class);
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		factory = configuration.buildSessionFactory(builder.build());
        userFactory = new ModelFactory<User>(User.class);
        dao = new UserHibernateDAO(factory);
    }

    @Before
    public void beforeTest() {
        factory.getCurrentSession().beginTransaction();
    }

    @After
    public void afterTest() {    	
        factory.getCurrentSession().getTransaction().rollback();
    }

    @Test
    public void testCreate() throws IllegalAccessException,
        InstantiationException  {
        assertEquals(dao.count(), 0L);
        dao.create(userFactory.getObject());
        assertEquals(dao.count(), 1L);
    }

    @Test
    public void testUpdate() throws IllegalAccessException,
        InstantiationException {
    	assertEquals(dao.count(), 0L);
        User user = dao.create(userFactory.getObject());
        String updatedUsername = "updatedusername";
        user.setUsername(updatedUsername);
        dao.update(user);
        assertEquals(updatedUsername, user.getUsername());
    }

    @Test
    public void testDelete() throws IllegalAccessException,
        InstantiationException  {
        User user = dao.create(userFactory.getObject());
        dao.delete(user);
    }

}
