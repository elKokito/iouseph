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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.Consts;
import org.json.JSONObject;

public class SoundcloudClient {

	public final String host = "https://soundcloud.com/connect";
	public String client_id = "0b26692829174e89b8c12870cbdc77aa";
	public String client_secret = "c792cfd55e331d931f074b8d8a7f351a";
	public String token ="";
	
	public void get_token() throws ClientProtocolException, IOException {
	
		String url = "https://api.soundcloud.com/oauth2/token";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		
		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("client_id", client_id));
		body_args.add(new BasicNameValuePair("client_secret", client_secret));
		body_args.add(new BasicNameValuePair("grant_type", "password"));
		body_args.add(new BasicNameValuePair("username", "is_the_freeze@hotmail.com"));
		body_args.add(new BasicNameValuePair("password", "1234abcd"));
		body_args.add(new BasicNameValuePair("scope", "non-expiring"));
		
		
		post.setEntity(new UrlEncodedFormEntity(body_args, Consts.UTF_8));
		
		CloseableHttpResponse res = httpclient.execute(post);
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("res : " + res.getStatusLine());

		BufferedReader reader = new BufferedReader(new InputStreamReader(
                res.getEntity().getContent()));
		
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while((inputLine = reader.readLine()) != null) {
			response.append(inputLine);
		}
		reader.close();
		
		JSONObject obj = new JSONObject(response.toString());
		token = obj.getString("access_token");
		res.close();
 
	}
}
