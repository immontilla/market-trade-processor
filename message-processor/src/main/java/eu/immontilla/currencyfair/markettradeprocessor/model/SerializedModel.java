package eu.immontilla.currencyfair.markettradeprocessor.model;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

/**
 * JsonSerialize configuration
 * @author immontilla
 *
 * @param <T>
 */
@JsonSerialize(using = SerializedModel.Serializer.class)
public class SerializedModel<T extends BaseModel> {

    private final String name;
    private final T model;
    private final Map<String, T> obj;

    @SuppressWarnings("rawtypes")
    static class Serializer extends StdSerializer<SerializedModel> {

        private static final long serialVersionUID = 1L;

        public Serializer() {
            super(SerializedModel.class, false);
        }

        @Override
        public void serialize(SerializedModel value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

            jgen.writeStartObject();
            jgen.writeFieldName(value.name);
            jgen.writeObject(value.model);
            jgen.writeEndObject();
        }

    }

    @JsonCreator
    public SerializedModel(Map<String, T> args) {
        String key = Iterables.getFirst(args.keySet(), null);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
        this.name = key;
        this.model = args.get(key);
        this.obj = args;
    }

    public SerializedModel(String name, T model) {
        this.name = name;
        this.model = model;
        this.obj = ImmutableMap.of(name, model);
    }

    @JsonValue
    public Map<String, T> getValue() {
        return obj;
    }

    public String getName() {
        return name;
    }

    public T getModel() {
        return model;
    }
}
