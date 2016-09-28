package triton.json;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import triton.domain.TechnologyType;

public class TechnologyTypeDeserializer implements JsonDeserializer<TechnologyType> {

	@Override
	public TechnologyType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Gson gson = new Gson();
		String value  = gson.fromJson(json, String.class);
		return TechnologyType.getTechnologyType(value);
	}

}
