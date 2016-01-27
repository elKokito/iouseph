package iouseph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.crypto.dsig.XMLObject;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.XML;
import org.xml.sax.Parser;


public class Deezer implements Iapi{

	private final String host = "http://api.deezer.com/";
	private String client_id = "171795";
	private String client_secret = "a460f3efd5e3e0c98af00730d882b5f0";
	private String token ="ny80x01z2456a7bff4829f7h9TJ0R3D56a7bff482a3dtzk9wDL";
	
	public void retreive_token() {
	
		String url = "https://api.deezer.com/oauth2/token";
		
		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("client_id", client_id));
		body_args.add(new BasicNameValuePair("client_secret", client_secret));
		body_args.add(new BasicNameValuePair("grant_type", "password"));
		body_args.add(new BasicNameValuePair("username", "eymen.zalila@me.com"));
		body_args.add(new BasicNameValuePair("password", "a5z4&988"));
		body_args.add(new BasicNameValuePair("scope", "non-expiring"));
		
		//get("http://api.deezer.com/search/artist/?q=eminem&index=0&limit=2&output=json");
		get("https://connect.deezer.com/oauth/auth.php?app_id=171795&redirect_uri=http://localhost:8080/callback.html&perms=basic_access,email,offline_access,manage_library,listening_history");
		JSONObject res = post(url, body_args);
		//token = res.getString("access_token");
 
	}
	
	public void get_personnal_info() {

		String url = host + "/infos";//me?oauth_token=" + token;
		JSONObject res = get(url);
		//System.out.println(res.toString());
		Iterator<String> i = res.keys();
		while(i.hasNext()){
			String s = i.next();
			System.out.println(s + " : " + res.get(s) );
		}
	}
	
	public void get_search(String search) {

		String url = host + "/search?q=" + search ;//+ "&index=0&limit=5";//me?oauth_token=" + token;
		JSONObject res = get(url);
		//System.out.println(res.toString());
		Iterator<String> i = res.keys();
		while(i.hasNext()){
			String s = i.next();
			System.out.println(s + " : " + res.get(s) );
		}
	}
	
	public void get_user_info(String user_id)  {
		String url = host + "user/" + user_id ;//+ "/playlists";// + "?client_id=" + client_id;
		JSONObject res = get(url);
		//System.out.println(res.toString());
		Iterator<String> i = res.keys();
		while(i.hasNext()){
			String s = i.next();
			System.out.println(s + " : " + res.get(s) );
		}
	}
	
	private JSONObject get(String url) {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);

		CloseableHttpResponse httpresponse;
		JSONObject res_json = null;
		try {
			httpresponse = httpclient.execute(get);	

		if(httpresponse.getStatusLine().getStatusCode() == 200) {
			res_json = read_response(httpresponse.getEntity().getContent());
			//System.out.println(res_json.toString());
		}
		else {
			System.out.println("error");
			System.out.println(httpresponse.getStatusLine().getReasonPhrase());
		}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res_json;
	}
	
	private JSONObject post(String url, List<NameValuePair> body_args) {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(body_args, Consts.UTF_8));
		
		CloseableHttpResponse httpresponse;
		JSONObject res_json = null;
		try {
			httpresponse = httpclient.execute(post);
		if(httpresponse.getStatusLine().getStatusCode() == 200) {
			res_json = read_response(httpresponse.getEntity().getContent());
			token = res_json.getString("access_token");
			System.out.println(token);
		}
		else {
			System.out.println("error");
			System.out.println(httpresponse.getStatusLine().getReasonPhrase());
		}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res_json;
	}
	
	private JSONObject read_response(InputStream r){
		BufferedReader reader = new BufferedReader(new InputStreamReader(r)); 
 
        String inputLine;
        StringBuffer response = new StringBuffer();
 
        try {
			while ((inputLine = reader.readLine()) != null) {
			    response.append(inputLine);
			}
        reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        //System.out.println(response.toString());
        JSONObject res_json = new JSONObject(response.toString());
        /*Iterator<String> i = res_json.keys();
		while(i.hasNext()){
			String s = i.next();
			System.out.println(s + " : " + res_json.get(s) );
		}*/
        return res_json;
	}

	@Override
	public void get_album(String album_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_artist(String artist_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_chart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_comment(String comment_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_editorials() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_editorial(String editorial_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_genres() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_genre(String genre_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_options() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_playlist(String playlist_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_podcast(String podcast_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_radios() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_radio(String radio_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_track(String track_id) {
		// TODO Auto-generated method stub
		
	}
	

	
}
