package com.ngnt.rest;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ngnt.models.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RestClient {
	
	public String address;
	
	private ExecutorService executor 
    = Executors.newSingleThreadExecutor();

    public RestClient(String address) {
		super();
		this.address = address;
	}

	// one instance, reuse
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final String token = "&key=AIzaSyASwthI1dzFN5q0tD7wqAvAnNus0idvgWU";
    private final String url = "https://maps.googleapis.com/maps/api/geocode/json?address=";

    public void close() throws IOException {
        httpClient.close();
    }

    public Future<Position> sendGet() throws Exception {
    	
    	return executor.submit(() -> {
    		HttpGet request = new HttpGet(url + address + token);
            

    		/*
    		 * // add request headers request.addHeader("custom-key", "mkyong");
    		 * request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
    		 */

            try (CloseableHttpResponse response = httpClient.execute(request)) {

                // Get HttpResponse Status
                System.out.println(response.getStatusLine().toString());

                HttpEntity entity = response.getEntity();
                Header headers = entity.getContentType();
                //System.out.println(headers);

                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    JSONObject json = new JSONObject(result);
                    JSONArray tokenList = json.getJSONArray("results");
                    JSONObject oj = tokenList.getJSONObject(0);
                    JSONObject geometry = oj.getJSONObject("geometry");
                    JSONObject location = geometry.getJSONObject("location");
                    
    				
    				  double lat = location.getDouble("lat");
    				  double lng = location.getDouble("lng");
    				  
    				  Position position = new Position(lat, lng);
    				  return position;
    				  
                }

            }
            return new Position(0, 0);
        });

    	
        
    }

    public void sendPost() throws Exception {

        HttpPost post = new HttpPost("https://httpbin.org/post");

        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("username", "abc"));
        urlParameters.add(new BasicNameValuePair("password", "123"));
        urlParameters.add(new BasicNameValuePair("custom", "secret"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        }

    }
}

