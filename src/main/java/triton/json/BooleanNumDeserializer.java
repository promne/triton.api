package triton.json;

import java.lang.reflect.Type;
import java.util.IllformedLocaleException;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

public class BooleanNumDeserializer implements JsonDeserializer<Boolean> {

	@Override
	public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonPrimitive primitive = (JsonPrimitive) json;
		if (primitive.isBoolean()) {
			return primitive.getAsBoolean();
		}
		if (primitive.isNumber()) {
			return primitive.getAsInt() == 1;
		}
		throw new IllformedLocaleException(String.format("For input \"%s\"", json.getAsString()));
	}

}
