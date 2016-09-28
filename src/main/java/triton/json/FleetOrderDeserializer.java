package triton.json;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import triton.domain.FleetAction;
import triton.domain.FleetOrder;

public class FleetOrderDeserializer implements JsonDeserializer<FleetOrder> {

	@Override
	public FleetOrder deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Gson gson = new Gson();
		int[] tmp  = gson.fromJson(json, int[].class);
		FleetOrder fleetOrder = new FleetOrder();
		
		//TODO: whats in tmp[0] ?
		fleetOrder.setStarId(tmp[1]);
		fleetOrder.setAction(FleetAction.getFleetAction(tmp[2]));
		fleetOrder.setShipsCount(tmp[3]);
		
		return fleetOrder;
	}

}
