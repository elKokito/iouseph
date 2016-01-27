package iouseph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.Consts;
import org.json.JSONArray;
import org.json.JSONObject;

public class SoundcloudClient {

	private final String host = "http://api.soundcloud.com/";
	private String client_id = "0b26692829174e89b8c12870cbdc77aa";
	private String client_secret = "c792cfd55e331d931f074b8d8a7f351a";
	private String token ="";
	
	public void retreive_token() throws ClientProtocolException, IOException {
	
		String url = "https://api.soundcloud.com/oauth2/token";
		
		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("client_id", client_id));
		body_args.add(new BasicNameValuePair("client_secret", client_secret));
		body_args.add(new BasicNameValuePair("grant_type", "password"));
		body_args.add(new BasicNameValuePair("username", "is_the_freeze@hotmail.com"));
		body_args.add(new BasicNameValuePair("password", "1234abcd"));
		body_args.add(new BasicNameValuePair("scope", "non-expiring"));
		
		JSONObject res = post(url, body_args);
		token = res.getString("access_token");
 
	}
	
	public void get_personnal_info() throws ClientProtocolException, IOException {

		String url = host + "me?oauth_token=" + token;
		JSONObject res = get(url);
		System.out.println(res.toString());
	}
	
	public void get_user_info(String user_id) throws UnsupportedOperationException, IOException {
		String url = host + "users/" + user_id + "?client_id=" + client_id;
		JSONObject res = get(url);
		System.out.println(res.toString());
	}
	
	public void resolve(String soundcloud_url) throws UnsupportedOperationException, IOException {
		String url = host + "resolve?url=" + soundcloud_url + "&client_id=" + client_id;
		JSONObject res = get(url);
		System.out.println(res.toString());
	}
	
	public void get_tracks() throws UnsupportedOperationException, IOException {
		String url = host + "tracks?client_id=" + client_id;
		JSONArray res = get_array(url);
		System.out.println(res.toString());
	}
	
	public void search(String query) throws UnsupportedOperationException, IOException {
		String url = host + "tracks?q=" + query + "&client_id=" + client_id;
		JSONArray res = get_array(url);
		System.out.println(res.toString());
	}
	
	private JSONObject get(String url) throws UnsupportedOperationException, IOException {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		System.out.println("sending GET: " + url);
		HttpGet get = new HttpGet(url);

		CloseableHttpResponse httpresponse = httpclient.execute(get);

		JSONObject res_json = null;
		if(httpresponse.getStatusLine().getStatusCode() == 200) {
			res_json = read_response(httpresponse.getEntity().getContent());
			//System.out.println(res_json.toString());
		}
		else {
			System.out.println("error");
			System.out.println(httpresponse.getStatusLine().getReasonPhrase());
		}
		return res_json;
	}
	
	private JSONArray get_array(String url) throws UnsupportedOperationException, IOException {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		System.out.println("sending GET: " + url);
		HttpGet get = new HttpGet(url);

		CloseableHttpResponse httpresponse = httpclient.execute(get);

		JSONArray res_json = null;
		if(httpresponse.getStatusLine().getStatusCode() == 200) {
			res_json = read_response_array(httpresponse.getEntity().getContent());
			//System.out.println(res_json.toString());
		}
		else {
			System.out.println("error");
			System.out.println(httpresponse.getStatusLine().getReasonPhrase());
		}
		return res_json;
	}

	private JSONObject post(String url, List<NameValuePair> body_args) throws UnsupportedOperationException, IOException {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(body_args, Consts.UTF_8));
		
		CloseableHttpResponse httpresponse = httpclient.execute(post);

		JSONObject res_json = null;
		if(httpresponse.getStatusLine().getStatusCode() == 200) {
			res_json = read_response(httpresponse.getEntity().getContent());
			token = res_json.getString("access_token");
			System.out.println(token);
		}
		else {
			System.out.println("error");
			System.out.println(httpresponse.getStatusLine().getReasonPhrase());
		}
		
		return res_json;
	}
	
	private JSONObject read_response(InputStream r) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(r)); 
 
        String inputLine;
        StringBuffer response = new StringBuffer();
 
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        System.out.println(response);
        reader.close();
        JSONObject res_json = new JSONObject(response.toString());
        return res_json;
	}
	
	private JSONArray read_response_array(InputStream r) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(r)); 
		 
        String inputLine;
        StringBuffer response = new StringBuffer();
 
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        System.out.println(response);
        reader.close();
        JSONArray res_json = new JSONArray(response.toString());
        return res_json;
	
	}
}
