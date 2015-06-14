package eu.immontilla.currencyfair.markettradeprocessor.model;

import java.util.UUID;

/**
 * An abstract base class for model classes.
 * @author immontilla
 *
 */
public abstract class BaseModel {
    public UUID randomUUID() {
        return UUID.randomUUID();
    }

}
