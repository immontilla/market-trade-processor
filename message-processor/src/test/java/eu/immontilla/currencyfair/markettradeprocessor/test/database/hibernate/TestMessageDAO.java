package eu.immontilla.currencyfair.markettradeprocessor.test.database.hibernate;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.immontilla.currencyfair.markettradeprocessor.database.GenericDAO;
import eu.immontilla.currencyfair.markettradeprocessor.database.hibernate.MessageHibernateDAO;
import eu.immontilla.currencyfair.markettradeprocessor.model.Message;
import eu.immontilla.currencyfair.markettradeprocessor.test.utils.ModelFactory;
import eu.immontilla.currencyfair.markettradeprocessor.test.utils.database.TestDAO;

public class TestMessageDAO {
    private static GenericDAO<Message> dao;
    private static SessionFactory factory;
    private static ModelFactory<Message> messageFactory;
    
    private void setValues(Message message) {
        message.setCurrencyFrom("EUR");
        message.setCurrencyTo("USD");
        message.setAmountSell(new BigDecimal("8"));
        message.setAmountBuy(new BigDecimal("8"));
        message.setOriginatingCountry("IE");
    }

    @BeforeClass
    public static void setupClass() {
        Configuration configuration = TestDAO.getConfig();
        configuration.addAnnotatedClass(Message.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        factory = configuration.buildSessionFactory(builder.build());
        messageFactory = new ModelFactory<Message>(Message.class);
        dao = new MessageHibernateDAO(factory);
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
    public void testCreate() throws IllegalAccessException, InstantiationException {
        setValues(messageFactory.getObject());
        dao.create(messageFactory.getObject());
    }

    @Test
    public void testUpdate() throws IllegalAccessException, InstantiationException {
        assertEquals(dao.count(), 0L);
        setValues(messageFactory.getObject());
        Message message = dao.create(messageFactory.getObject());
        String updatedCurrencyFrom = "GBP";
        message.setCurrencyFrom(updatedCurrencyFrom);
        dao.update(message);
        assertEquals(updatedCurrencyFrom, message.getCurrencyFrom());
    }

    @Test
    public void testDelete() throws IllegalAccessException, InstantiationException {
        Message message = dao.create(messageFactory.getObject());
        dao.delete(message);
    }

}
