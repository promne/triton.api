package triton.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import triton.domain.Fleet;
import triton.domain.FleetOrder;
import triton.domain.GameConfig;
import triton.domain.Player;
import triton.domain.Star;
import triton.domain.TechnologyType;
import triton.domain.UniverseReport;
import triton.json.BooleanNumDeserializer;
import triton.json.FleetOrderDeserializer;
import triton.json.FleetReportWrapper;
import triton.json.TechnologyTypeDeserializer;
import triton.json.UniverseReportWrapper;
import triton.mechanic.Economy;

/**
 * Interface allowing to interact with game server.
 * 
 * 
 * @author georgeh
 *
 */
public class GameClientImpl implements GameClient {
	
	private static final Logger LOG = Logger.getLogger(GameClientImpl.class);

	private final String gameId;
	
	/**
	 * If possible put command in batch instead sending one by one.
	 * It's better for debugging to turn this feature off.
	 */
	private final boolean preferBatchOrders; 

	private final CloseableHttpClient httpClient;
	private final HttpClientContext httpContext;

	private GameConfig gameConfig;
	
	private ConcurrentLinkedQueue<String> batchOrders = new ConcurrentLinkedQueue<>();

	public GameClientImpl(String authKey, String gameId, boolean preferBatchOrders) {
		super();
		this.gameId = gameId;
		this.preferBatchOrders = preferBatchOrders;
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		// now, init http client
		String proxyHost = System.getProperty("http.proxyHost");
		Integer proxyPort = null;
		if (System.getProperty("http.proxyPort")!=null) {
			proxyPort = Integer.valueOf(System.getProperty("http.proxyPort"));			
		}
		
		HttpHost proxy = null;
		if (proxyHost != null && proxyPort != null ) {
			proxy = new HttpHost(proxyHost, proxyPort);
		}
		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH).setProxy(proxy).build();
		httpClientBuilder.setDefaultRequestConfig(globalConfig);
		
		String proxyUser = System.getProperty("http.proxyUser");
		String proxyPassword = System.getProperty("http.proxyPassword");
		if (proxyUser != null && proxyPassword != null) {
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(new AuthScope(proxyHost, proxyPort), new UsernamePasswordCredentials(proxyUser, proxyPassword));
			httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
		}

		CookieStore cookieStore = new BasicCookieStore();
		httpContext = HttpClientContext.create();
		httpContext.setCookieStore(cookieStore);


		BasicClientCookie cookie = new BasicClientCookie("auth", authKey);
		cookie.setDomain("np.ironhelmet.com");
		cookieStore.addCookie(cookie);
		httpClientBuilder.setDefaultCookieStore(cookieStore);

		
		httpClient = httpClientBuilder.build();
	}

	/**
	 * Custom gson builder
	 * 
	 * @return
	 */
	private GsonBuilder getGsonBuilder() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(FleetOrder.class, new FleetOrderDeserializer());
		gsonBuilder.registerTypeAdapter(TechnologyType.class, new TechnologyTypeDeserializer());
		gsonBuilder.registerTypeAdapter(Boolean.class, new BooleanNumDeserializer());
		return gsonBuilder;
	}
	
	/**
	 * Common post entity values
	 * 
	 * @return
	 */
	private List<NameValuePair> getCommonRequestData() {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("version", ""));
		nvps.add(new BasicNameValuePair("game_number", gameId));
		return nvps;
	}

	/**
	 * Sending post with order, returns response as String
	 * 
	 * @param order
	 * @return
	 * @throws ClientOrderException
	 */
	private String sendOrder(String order) throws ClientOrderException {
		List<NameValuePair> nvps = getCommonRequestData();
		nvps.add(new BasicNameValuePair("order", order));
		nvps.add(new BasicNameValuePair("type", "order"));

		LOG.debug("Sending order > " + order);
		String result = sendPostRequest(nvps, "https://np.ironhelmet.com/trequest/order");
		LOG.debug("Received response > " + result);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see triton.api.GameClient#sendBatchOrders()
	 */
	@Override
	public void sendBatchOrders() throws ClientOrderException {
		// upgrade_industry,26,33/upgrade_economy,26,16
		
		StringBuilder batchOrderBuilder = new StringBuilder();
		String order = batchOrders.poll();
		if (order!=null) {
			batchOrderBuilder.append(order);
		}
		while (batchOrders.peek()!=null) {
			batchOrderBuilder.append('/');
			batchOrderBuilder.append(batchOrders.poll());
		}
		String batchOrder = batchOrderBuilder.toString();
		
		if (batchOrder.isEmpty()) {
			return;
		}
		
		List<NameValuePair> nvps = getCommonRequestData();
		nvps.add(new BasicNameValuePair("order", batchOrder));
		nvps.add(new BasicNameValuePair("type", "batched_orders"));

		LOG.debug("Sending batch order > " + batchOrder);
		String result = sendPostRequest(nvps, "https://np.ironhelmet.com/trequest/batched_orders");
		LOG.debug("Received batch response > " + result);
	}

	private String sendPostRequest(List<NameValuePair> nvps, String url) throws ClientOrderException {
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		} catch (UnsupportedEncodingException e) {
			throw new ClientOrderException(e);
		}
		
		String result = getHttpResponse(httpPost);
		if (result.contains("order:error")) {
			throw new ClientOrderException(result);
		}
		return result;
	}

	private String getHttpResponse(HttpUriRequest request) throws ClientOrderException {
		try (CloseableHttpResponse response = httpClient.execute(request, httpContext)) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder responseStringBuilder = new StringBuilder();
			String line = "";
			while ((line = rd.readLine()) != null) {
				responseStringBuilder.append(line);
			}
			return responseStringBuilder.toString();
		} catch (IOException e) {
			throw new ClientOrderException(e);
		}		
	}

	/* (non-Javadoc)
	 * @see triton.api.GameClient#getIntelReport()
	 */
	@Override
	public void getIntelReport() throws ClientOrderException {
		List<NameValuePair> nvps = getCommonRequestData();
		nvps.add(new BasicNameValuePair("type", "intel_data"));

		LOG.debug("Sending intel request > " + nvps);
		String result = sendPostRequest(nvps, "https://np.ironhelmet.com/trequest/intel_data");
		LOG.debug("Received intel response > " + result);
	}

	/* (non-Javadoc)
	 * @see triton.api.GameClient#getUniverseReport()
	 */
	@Override
	public UniverseReport getUniverseReport() throws ClientOrderException {
		String response = null;
		response = sendOrder("full_universe_report");
		Gson gson = getGsonBuilder().create();
		UniverseReportWrapper universeReportWrapper = gson.fromJson(response, UniverseReportWrapper.class);
		return universeReportWrapper.getUniverseReport();
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	public void close() throws IOException {
		if (httpClient != null) {
			httpClient.close();
		}
	}

	/* (non-Javadoc)
	 * @see triton.api.GameClient#setFleetOrders(triton.domain.Fleet)
	 */
	@Override
	public void setFleetOrders(Fleet fleet) throws ClientOrderException {
		if (fleet.getOrders().isEmpty()) {
			clearFleetOrders(fleet);
			return;
		}
		
		StringBuilder order = new StringBuilder();
		order.append("add_fleet_orders,").append(fleet.getId()).append(',');
		
		StringBuilder efoBuilder = new StringBuilder();
		StringBuilder pathBuilder = new StringBuilder();
		StringBuilder commandBuilder = new StringBuilder();
		StringBuilder shipsBuilder = new StringBuilder();
		
		Iterator<FleetOrder> iterator = fleet.getOrders().iterator();
		while (iterator.hasNext()) {
			FleetOrder fleetOrder = iterator.next();
			
			efoBuilder.append("0"); //wtf is this value?
			pathBuilder.append(fleetOrder.getStarId());
			commandBuilder.append(fleetOrder.getAction().getId());
			shipsBuilder.append(fleetOrder.getShipsCount());
			if (iterator.hasNext()) {
				efoBuilder.append("_");
				pathBuilder.append("_");
				commandBuilder.append("_");
				shipsBuilder.append("_");				
			}
		}
		
		order.append(efoBuilder).append(',').append(pathBuilder).append(',').append(commandBuilder).append(',').append(shipsBuilder);
		order.append(',').append(fleet.isLooping());
		
		if (preferBatchOrders) {
			batchOrders.add(order.toString());
		} else {
			sendOrder(order.toString());
		}
	}

	/* (non-Javadoc)
	 * @see triton.api.GameClient#clearFleetOrders(triton.domain.Fleet)
	 */
	@Override
	public void clearFleetOrders(Fleet fleet) throws ClientOrderException {
		String order = "clear_fleet_orders," + fleet.getId();
		if (preferBatchOrders) {
			batchOrders.add(order);
		} else {
			sendOrder(order);
		}
	}

	/* (non-Javadoc)
	 * @see triton.api.GameClient#setFleetLoop(triton.domain.Fleet, boolean)
	 */
	@Override
	public void setFleetLoop(Fleet fleet, boolean loop) throws ClientOrderException {
		String order = "loop_fleet_orders," + fleet.getId() + "," + (loop ? "1" : "0");
		if (preferBatchOrders) {
			batchOrders.add(order);
		} else {
			sendOrder(order);
		}
	}

	/* (non-Javadoc)
	 * @see triton.api.GameClient#shipTransfer(triton.domain.Fleet, int)
	 */
	@Override
	public void shipTransfer(Fleet fleet, int shipCount) throws ClientOrderException {
		StringBuilder order = new StringBuilder();
		order.append("ship_transfer,").append(fleet.getId()).append(',').append(shipCount);
		if (preferBatchOrders) {
			batchOrders.add(order.toString());
		} else {
			sendOrder(order.toString());
		}
	}

	/* (non-Javadoc)
	 * @see triton.api.GameClient#submitTurn()
	 */
	@Override
	public UniverseReport submitTurn() throws ClientOrderException {
		sendBatchOrders();
		
		String order = "force_ready";
		String orderResult = sendOrder(order);
		
		// returns universe
		Gson gson = getGsonBuilder().create();
		UniverseReportWrapper universeReport = gson.fromJson(orderResult, UniverseReportWrapper.class);
		return universeReport.getUniverseReport();
	}

	/* (non-Javadoc)
	 * @see triton.api.GameClient#buyFleet(triton.domain.Star, int)
	 */
	@Override
	public Fleet buyFleet(Star star, int shipCount) throws ClientOrderException {
		StringBuilder order = new StringBuilder();
		order.append("new_fleet,").append(star.getId()).append(',').append(shipCount);
		String orderResult = sendOrder(order.toString());
		// returns fleet in report
		FleetReportWrapper fleetReportWrapper = getGsonBuilder().create().fromJson(orderResult, FleetReportWrapper.class);
		return fleetReportWrapper.getFleet();
	}

	/* (non-Javadoc)
	 * @see triton.api.GameClient#buyEconomy(triton.domain.Player, triton.domain.Star)
	 */
	@Override
	public void buyEconomy(Player player, Star star) {
		// upgrade_economy,25,14
		StringBuilder order = new StringBuilder();
		int money = Economy.getPriceForStarEconomy(gameConfig, star, player.getTech().getTerraforming().getLevel(), star.getEconomyCount()+1);
		order.append("upgrade_economy,").append(star.getId()).append(',').append(money);
		batchOrders.add(order.toString());
	}

	/* (non-Javadoc)
	 * @see triton.api.GameClient#buyIndustry(triton.domain.Player, triton.domain.Star)
	 */
	@Override
	public void buyIndustry(Player player, Star star) {
		// upgrade_industry,25,28
		StringBuilder order = new StringBuilder();
		int money = Economy.getPriceForStarIndustry(gameConfig, star, player.getTech().getTerraforming().getLevel(), star.getIndustryCount()+1);
		order.append("upgrade_industry,").append(star.getId()).append(',').append(money);
		batchOrders.add(order.toString());
	}

	/* (non-Javadoc)
	 * @see triton.api.GameClient#buyScience(triton.domain.Player, triton.domain.Star)
	 */
	@Override
	public void buyScience(Player player, Star star) {
		// upgrade_science,25,28
		StringBuilder order = new StringBuilder();
		int money = Economy.getPriceForStarScience(gameConfig, star, player.getTech().getTerraforming().getLevel(), star.getScienceCount()+1);
		order.append("upgrade_science,").append(star.getId()).append(',').append(money);
		batchOrders.add(order.toString());
	}

	/* (non-Javadoc)
	 * @see triton.api.GameClient#setResearching(triton.domain.TechnologyType)
	 */
	@Override
	public void setResearching(TechnologyType technology) throws ClientOrderException {
		String order = "change_research,"+technology.getStoreValue();
		if (preferBatchOrders) {
			batchOrders.add(order);
		} else {
			sendOrder(order);
		}
	}
	
	/* (non-Javadoc)
	 * @see triton.api.GameClient#setResearchingNext(triton.domain.TechnologyType)
	 */
	@Override
	public void setResearchingNext(TechnologyType technology) throws ClientOrderException {
		String order = "change_research_next,"+technology.getStoreValue();
		if (preferBatchOrders) {
			batchOrders.add(order);
		} else {
			sendOrder(order);
		}
	}
	
	/* (non-Javadoc)
	 * @see triton.api.GameClient#getGameConfig()
	 */
	@Override
	public GameConfig getGameConfig() throws ClientOrderException {
		if (gameConfig==null) {
			HttpGet httpGet = new HttpGet("https://np.ironhelmet.com/game/" + gameId);
			String result = getHttpResponse(httpGet);
			
			String startingText = "NeptunesPride.gameConfig ="; 
			
			int startIndex = result.indexOf(startingText);
			int endIndex = result.indexOf('}', startIndex);
			
			String gameConfigJson = result.substring(startIndex+startingText.length(), endIndex+1);
			LOG.debug("Retrieved game settings > " + gameConfigJson);
			gameConfig = getGsonBuilder().create().fromJson(gameConfigJson, GameConfig.class);
		}
		return gameConfig;
	}

}
