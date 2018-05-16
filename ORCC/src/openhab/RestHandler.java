/**
 * 
 */
package openhab;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

/**
 * @author reh4512
 *
 */
public class RestHandler {
	
	private URL serverUrl;
	
	Retrofit jsonRetrofit;
	Retrofit textRetrofit;
	
	private final OpenhabJsonService openhabJsonService;
	private final OpenhabTextService openhabTextService;
	
	
	protected interface OpenhabJsonService {
		@GET("/items") Call<List<RestItem>> getAllItems();
	}
	
	protected interface OpenhabTextService {
		@GET("/uuid") Call<String> getUuid();
	}
	
	
	public RestHandler(URL serverUrl) {
		this.serverUrl = serverUrl;
		Retrofit jsonRetrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(serverUrl.toString()).build();
		Retrofit textRetrofit = new Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).baseUrl(serverUrl.toString()).build();

		openhabJsonService = jsonRetrofit.create(OpenhabJsonService.class);
		openhabTextService = textRetrofit.create(OpenhabTextService.class);
	}

	
	public boolean canConnect() {
		boolean retVal = false;
		
		Call<String> call = openhabTextService.getUuid();
		
		try {
			if (call.execute().isSuccessful()) {
				retVal = true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		return retVal;
	}
	
	public void getAllItems() throws Exception {
		
		
	}
	
	
	

}
