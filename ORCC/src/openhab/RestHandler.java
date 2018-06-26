/**
 * 
 */
package openhab;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.launchdarkly.eventsource.EventSource;

import application.FakeInterceptor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

/**
 * @author Sven Rehfuﬂ
 *
 */
public class RestHandler {
	
	/**
	 * The retrofit object used for JSON coded answers. 
	 */
	Retrofit jsonRetrofit;
	
	/**
	 * The retrofit object used for plain text answers.
	 */
	Retrofit textRetrofit;
	
	/**
	 * Service used for JSON coded answers.
	 */
	private final OpenhabJsonService openhabJsonService;
	
	/**
	 * Service used for plain text answers.
	 */
	private final OpenhabTextService openhabTextService;
	
	/**
	 * The event handler
	 */
	private OpenhabEventHandler openhabEventHandler;
	
	/**
	 * The Openhab event source object.
	 */
	EventSource eventSource;
	
	/**
	 * Definition of all REST services returning JSON data. 
	 * @author Sven Rehfuﬂ
	 */
	protected interface OpenhabJsonService {
		@GET("items") Call<List<RestItem>> getAllItems();
	}
	
	/**
	 * Definition of all REST services returning plain text data. 
	 * @author Sven Rehfuﬂ
	 */
	protected interface OpenhabTextService {
		@GET("uuid") Call<String> getUuid();
	}
	
	
	public RestHandler(String serverUrl) {
		
		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new FakeInterceptor()).build();
		
		Retrofit jsonRetrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(serverUrl).client(client).build();
		Retrofit textRetrofit = new Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).baseUrl(serverUrl).client(client).build();

		openhabJsonService = jsonRetrofit.create(OpenhabJsonService.class);
		openhabTextService = textRetrofit.create(OpenhabTextService.class);

		EventSource.Builder builder = new EventSource.Builder(openhabEventHandler, URI.create(serverUrl+"events"));
		eventSource = builder.build();
		eventSource.setReconnectionTimeMs(3000);
	}

	
	public boolean canConnect() {
		boolean retVal = false;
		
		Call<String> call = openhabTextService.getUuid();
		
		try {
			Response<String> callResponse = call.execute();
			if (callResponse.isSuccessful()) {
				retVal = true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		return retVal;
	}
	
	/**
	 * Get all available Openhab items
	 * @return The list of Openhab items
	 * @throws Exception
	 */
	public ArrayList<OpenhabItem> getAllItems() throws Exception {
		Call<List<RestItem>> allItemsCall = openhabJsonService.getAllItems();
		
		Response<List<RestItem>> callResponse;
			callResponse = allItemsCall.execute();
			if (callResponse.isSuccessful() == false) {
				throw new Exception("Unknow error, but the response was unseccessful.");
			}
			
			ArrayList<OpenhabItem> openhabItemList = new ArrayList<OpenhabItem>();
			
			for (RestItem item : callResponse.body()) {
				OpenhabItem itemToAdd = new OpenhabItem(item);
				openhabItemList.add(itemToAdd);
			}
			
			return openhabItemList;
	}
	
	public void subscribeToSSE() {
		eventSource.start();
	}
	
}
