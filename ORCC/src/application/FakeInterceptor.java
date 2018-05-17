/**
 * 
 */
package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author reh4512
 *
 */
public class FakeInterceptor implements Interceptor { 
 
 
     private final static String TAG = FakeInterceptor.class.getSimpleName(); 
 
     @Override 
     public Response intercept(Chain chain) throws IOException { 
         Response response = null; 
             // Get Request URI. 
             final URI uri = chain.request().url().uri(); 
 
             String filename = uri.getPath();
             filename = filename.substring(filename.lastIndexOf('/') + 1).split("\\?")[0];
             filename = "./test/" + filename.toLowerCase() + ".json";
             
             try {
                 Thread.sleep(2500);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }

             InputStream is = new FileInputStream(new File(filename));
             int size = is.available();
             byte[] buffer = new byte[size];
             is.read(buffer);
             is.close();
             String responseString = new String(buffer);

 
             response = new Response.Builder() 
                     .code(200) 
                     .message(responseString) 
                     .request(chain.request()) 
                     .protocol(Protocol.HTTP_1_0) 
                     .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes())) 
                     .addHeader("content-type", "application/json") 
                     .build(); 
 
 
         return response; 
     } 
 } 
