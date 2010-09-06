package org.ftm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import org.ftm.api.Contribution;
import org.ftm.api.Contributor;
import org.ftm.api.Politician;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Sep 3, 2010
 */
final class JSonParsingTest {
    public static void main(String[] args) throws Exception {
        final Reader reader = new FileReader(new File("resources/contributions.json"));

        final Collection<Contribution> objects = loadContribution(reader);
        System.out.println(objects);
    }

    private static Collection<Contribution> loadContribution(Reader reader) {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Contribution.class, new ContributionDeserializer());
        final Gson gson = gsonBuilder.create();
        final Type collectionType = new TypeToken<Collection<Contribution>>() {
        }.getType();
        final Collection<Contribution> objects = gson.fromJson(reader, collectionType);
        return objects;
    }

    private static final class ContributionDeserializer implements JsonDeserializer<Contribution> {
        public Contribution deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject object = jsonElement.getAsJsonObject();
            final String contributorName = object.get("contributor_name").getAsString();
            final String recipient = object.get("recipient_name").getAsString();
            final float amount = object.get("amount").getAsFloat();
            return new Contribution(new Contributor(contributorName), amount, new Politician(recipient));
        }
    }
}
