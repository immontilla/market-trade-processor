package eu.immontilla.currencyfair.markettradeprocessor.model;

import java.util.UUID;

public abstract class BaseModel {
    public UUID randomUUID() {
        return UUID.randomUUID();
    }

}
