package eu.immontilla.currencyfair.markettradeprocessor.command;

import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;

import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import eu.immontilla.currencyfair.markettradeprocessor.command.util.RandomValue;
import eu.immontilla.currencyfair.markettradeprocessor.configuration.AppConfiguration;
import eu.immontilla.currencyfair.markettradeprocessor.database.GenericDAO;
import eu.immontilla.currencyfair.markettradeprocessor.database.hibernate.HibernateConfig;
import eu.immontilla.currencyfair.markettradeprocessor.database.hibernate.MessageHibernateDAO;
import eu.immontilla.currencyfair.markettradeprocessor.model.Message;

/**
 * Command line tool to generate fake random messages
 * @author immontilla
 *
 */

public class CreateMessagesCommand extends ConfiguredCommand<AppConfiguration> {

    public CreateMessagesCommand() {
        super("create_messages", "Create trade messages");
    }
    
    @Override
    public void configure(Subparser subparser) {
        super.configure(subparser);
    }
    
    @Override
    protected void run(Bootstrap<AppConfiguration> bootstrap, Namespace namespace, AppConfiguration configuration) {
        Configuration dbConfig = null;
        SessionFactory factory = null;
        try {
            dbConfig = HibernateConfig.getConfig(configuration);
            dbConfig.addAnnotatedClass(Message.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(dbConfig.getProperties());
            factory = dbConfig.buildSessionFactory(builder.build());
            final GenericDAO<Message> dao = new MessageHibernateDAO(factory);
            
            System.out.print("\n Creating messages: \n");
            
            System.out.print("\n How many messages do you want to generate?: \n");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            int totalMessages = 100;
            String readLine = "";
            try {
                readLine = in.readLine();
                totalMessages = Integer.valueOf(readLine);
                if (totalMessages<=0) {
                    System.out.print("\n Invalid value: " + readLine  + ". We're going to set 100 instead. \n");
                    totalMessages = 15;
                }
            } catch (NumberFormatException e) {
                System.out.print("\n Invalid value: " + readLine  + ". We're going to set 100 instead. \n");
            }
            
            System.out.print("\n Placed in how many days before the current date?: \n");
            in = new BufferedReader(new InputStreamReader(System.in));
            int totalDays = 15;            
            try {
                readLine = in.readLine();
                totalDays = Integer.valueOf(readLine);
                if (totalDays<=0) {
                    System.out.print("\n Invalid value: " + readLine  + ". We're going to set 15 instead. \n");
                    totalDays = 15;
                }
            } catch (NumberFormatException e) {
                System.out.print("\n Invalid value: " + readLine  + ". We're going to set 15 instead. \n");
            }            
                                  
            Message obj = null;  
            Message createdMessage = null;
            StringWriter writer = null;
            for (int i=0;i<totalMessages;i++) {
                factory.getCurrentSession().beginTransaction();
                obj = new Message();
                obj.setUserId(Long.valueOf(RandomValue.get(1000000,1000020)));
                obj.setCurrencyFrom(RandomValue.getCurrency(null));
                obj.setCurrencyTo(RandomValue.getCurrency(obj.getCurrencyFrom()));
                obj.setAmountSell(RandomValue.get(1000.00, 2000.00));
                obj.setAmountBuy(RandomValue.get(1000.00, 2000.00));
                obj.setRate(RandomValue.getPercentage(0.50d,0.70d));                
                obj.setTimePlaced(RandomValue.getDate(totalDays));
                obj.setOriginatingCountry(RandomValue.getCountry());
                createdMessage = dao.create(obj);
                factory.getCurrentSession().getTransaction().commit();
                writer = new StringWriter();
                bootstrap.getObjectMapper().writeValue(writer, createdMessage);
                System.out.print(writer.toString());
                System.out.print("\n");
            }                                 
            System.exit(0);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            factory.getCurrentSession().getTransaction().rollback();
            System.exit(1);
        }
    }

}
