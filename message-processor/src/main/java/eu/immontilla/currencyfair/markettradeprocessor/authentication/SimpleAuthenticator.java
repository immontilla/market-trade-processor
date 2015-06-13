package eu.immontilla.currencyfair.markettradeprocessor.authentication;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import eu.immontilla.currencyfair.markettradeprocessor.facade.BaseFacade;
import eu.immontilla.currencyfair.markettradeprocessor.model.User;

public class SimpleAuthenticator implements Authenticator<BasicCredentials, User> {

    private final BaseFacade<User> facade;

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleAuthenticator.class);

    public SimpleAuthenticator(BaseFacade<User> facade) {
        this.facade = facade;
    }

    private Optional<User> getUserByCredentials(String token, String username) {

        LOGGER.info("Checking credentials for admin user {}", username);
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("username", username);

        Optional<Map<String, Object>> p = Optional.of(params);
        List<User> userList = facade.findByParams(p);

        LOGGER.info("token {}", token);
        if (userList.size() == 1) {
            return Optional.of(userList.get(0));
        }
        else {
            return Optional.absent();
        }

    }

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        return getUserByCredentials(credentials.getPassword(), credentials.getUsername());
    }
}
