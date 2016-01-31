package iouseph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.corba.se.impl.orbutil.RepositoryIdUtility;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class SpotifyClient implements Iapi{

	private String host = "https://accounts.spotify.com";
	private String AuthPath = "/authorize/?";
	private String TokenPath = "/api/token";
	private String client_id = "ccb24bc509974a72babd14e92902f816";
	private String client_secret = "9e85225cb1324ee1bb7fa32be121a96c";
	private String redirect_uri = "http://localhost:8888/callback";
	private String access_token ;
	private String token_type ;
	private String refresh_token;
	private String code_retrieved;
	
	private Map<String, String> lastItemSearchedInfo=new HashMap<String, String>();

	public void retreive_token() throws Exception {
		String url = host + AuthPath;
		String scope="playlist-read-private playlist-read-collaborative playlist-modify-public " +
		"playlist-modify-private streaming user-follow-modify user-follow-read user-library-read " +
		"user-library-modify user-read-private user-read-birthdate user-read-email";
		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("client_id", client_id));
		body_args.add(new BasicNameValuePair("response_type", "code"));
		body_args.add(new BasicNameValuePair("redirect_uri", redirect_uri));
		body_args.add(new BasicNameValuePair("scope", "playlist-read-private user-read-private user-read-email "));
		String paramString = URLEncodedUtils.format(body_args, "utf-8");

		url+=paramString;
		// redirect
		System.out.println(url);
		java.awt.Desktop.getDesktop().browse(new URI(url));
		code_retrieved= NetworkWrapper.runServerToListen(8888);
		requestRefrechAndAccessToken();
	}

	public JSONObject getProfile() throws ClientProtocolException, IOException {
		String url = "https://api.spotify.com/v1/me";
		JSONObject res_json = NetworkWrapper.get(url, "Authorization", "Bearer " + access_token);
		return res_json;
	}

	private void requestRefrechAndAccessToken() throws Exception
	{
		String url = host + TokenPath ;
		// recuperation de la partie qui nous importe 
		// format du code GET /callback?code=AQA1Bw3lyz_oJU3oWBaRPIgQUkCiWFnLLecNrbROeQwJWKl92l9p0XdVqRvXguxjiYcceaceoF_oMNTqMdV6O6SQwnOFq4qXFdPfEEI-jcjk9tkYwNJMwNO8-j_ufyS543p_LZGK7ix7UMlz55_8A-S6q1Phrso3eUa5QLpEcWR3zba8VJf34F0UJp5G0ntCrw18MJTKECU5nS0JxWtkj0yT2PWthr54jgF9BdNj6SAit20M7-x3Qg HTTP/1.1
		String[] parts = code_retrieved.split("=");
		parts= parts[1].split(" ");
		code_retrieved=parts[0];
		
		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("grant_type", "authorization_code"));
		body_args.add(new BasicNameValuePair("redirect_uri",redirect_uri));
		body_args.add(new BasicNameValuePair("code",code_retrieved));
		// client_id:secret_id en base 64
		String encodedBytes = Base64.encode((client_id+":"+client_secret).getBytes());
		
		JSONObject res_json=NetworkWrapper.post(url, body_args, "Authorization", "Basic "+encodedBytes);
		access_token= res_json.getString("access_token");
		refresh_token=res_json.getString("refresh_token");
	}
	

	public Set<String> getListOfTracks(String item)
	{
		String url = "https://api.spotify.com/v1/search?";
		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("q", item));
		body_args.add(new BasicNameValuePair("type", "track"));
		String paramString = URLEncodedUtils.format(body_args, "utf-8");

		url+=paramString;

		System.out.println(url);
		JSONObject res_json = NetworkWrapper.get(url);
		// format 
		res_json = res_json.getJSONObject("tracks");
		JSONArray jsonobjectsArray = (JSONArray) res_json.get("items");
		// returning the tracks found
		String MyKey,external_urls;
		lastItemSearchedInfo.clear();
		for(int i=0;i<jsonobjectsArray.length();i++)
		{
			// cle est titre-artiste
			String title = jsonobjectsArray.getJSONObject(i).getString("name");
			String artist = jsonobjectsArray.getJSONObject(i).getJSONArray("artists").getJSONObject(0).getString("name");
			 external_urls= jsonobjectsArray.getJSONObject(i).getJSONObject("external_urls").getString("spotify");
			 MyKey = title + " - " + artist;
			lastItemSearchedInfo.put(MyKey, external_urls);
		}
		return lastItemSearchedInfo.keySet();
	}
	public JSONObject getListOfArtist(String item)
	{
		String url = "https://api.spotify.com/v1/search?";
		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("q", item));
		body_args.add(new BasicNameValuePair("type", "artist"));
		String paramString = URLEncodedUtils.format(body_args, "utf-8");

		url+=paramString;

		System.out.println(url);
		JSONObject res_json = NetworkWrapper.get(url);
		System.out.println(res_json);
		
		return res_json;
	}
	public Set<String> getListOfAlbum(String item)
	{
		String url = "https://api.spotify.com/v1/search?";
		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("q", item));
		body_args.add(new BasicNameValuePair("type", "album"));
		String paramString = URLEncodedUtils.format(body_args, "utf-8");

		url+=paramString;

		System.out.println(url);
		JSONObject res_json = NetworkWrapper.get(url);

		// format 
		res_json = res_json.getJSONObject("albums");
		JSONArray jsonobjectsArray = (JSONArray) res_json.get("items");
		// returning the tracks found
		String MyKey, albumType,external_urls;
		lastItemSearchedInfo.clear();
		for(int i=0;i<jsonobjectsArray.length();i++)
		{
			// cle est titre-artiste
			MyKey = jsonobjectsArray.getJSONObject(i).getString("name");
			albumType=jsonobjectsArray.getJSONObject(i).getString("album_type");
			external_urls= jsonobjectsArray.getJSONObject(i).getJSONObject("external_urls").getString("spotify");
			MyKey+=" - " + albumType;
			lastItemSearchedInfo.put(MyKey, external_urls);
		}
		return lastItemSearchedInfo.keySet();
	}

	@Override
	public void get_search(String search) {
		// TODO Auto-generated method stub
		
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
	public void get_genres() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_genre(String genre_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_playlist(String playlist_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_track(String track_id) {
		// TODO Auto-generated method stub
		
	}
	
}
