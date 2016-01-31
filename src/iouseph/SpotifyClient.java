package iouseph;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class SpotifyClient implements Iapi {

	private String host = "https://accounts.spotify.com";
	private String client_id = "ccb24bc509974a72babd14e92902f816";
	private String client_secret = "9e85225cb1324ee1bb7fa32be121a96c";
	private String redirect_uri = "http://localhost:8888/callback";
	private String access_token;

	private Map<String, String> lastItemSearchedInfo = new HashMap<String, String>();

	public void retreive_token() throws Exception {
		String url = host + "/authorize/?";
		String scope = "playlist-read-private playlist-read-collaborative playlist-modify-public "
				+ "playlist-modify-private streaming user-follow-modify user-follow-read user-library-read "
				+ "user-library-modify user-read-private user-read-birthdate user-read-email";
		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("client_id", client_id));
		body_args.add(new BasicNameValuePair("response_type", "code"));
		body_args.add(new BasicNameValuePair("redirect_uri", redirect_uri));
		body_args.add(new BasicNameValuePair("scope", scope));
		String paramString = URLEncodedUtils.format(body_args, "utf-8");

		url += paramString;
		// redirect
		System.out.println(url);
		java.awt.Desktop.getDesktop().browse(new URI(url));
		String code_retrieved = NetworkWrapper.runServerToListen(8888);
		url = host + "/api/token";
		// recuperation de la partie qui nous importe
		// format du code GET
		// exemple
		// :/callback?code=AQA1Bw3lyz_oJU3oWBaRPIgQUkCiWFnLLecNrbROeQwJWKl92l9p0XdVqRvXguxjiYcceaceoF_oMNTqMdV6O6SQwnOFq4qXFdPfEEI-jcjk9tkYwNJMwNO8-j_ufyS543p_LZGK7ix7UMlz55_8A-S6q1Phrso3eUa5QLpEcWR3zba8VJf34F0UJp5G0ntCrw18MJTKECU5nS0JxWtkj0yT2PWthr54jgF9BdNj6SAit20M7-x3Qg
		// HTTP/1.1
		String[] parts = code_retrieved.split("=");
		parts = parts[1].split(" ");
		code_retrieved = parts[0];

		body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("grant_type", "authorization_code"));
		body_args.add(new BasicNameValuePair("redirect_uri", redirect_uri));
		body_args.add(new BasicNameValuePair("code", code_retrieved));
		// client_id:secret_id en base 64
		String encodedBytes = Base64.encode((client_id + ":" + client_secret).getBytes());

		JSONObject res_json = NetworkWrapper.post(url, body_args, "Authorization", "Basic " + encodedBytes);
		access_token = res_json.getString("access_token");
	}

	public void get_personnal_info() {
		String url = "https://api.spotify.com/v1/me";
		JSONObject res_json = NetworkWrapper.get(url, "Authorization", "Bearer " + access_token);
		System.out.println(res_json.toString());
	}

	public Set<String> getListOfTracks(String item) {
		String url = "https://api.spotify.com/v1/search?";
		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("q", item));
		body_args.add(new BasicNameValuePair("type", "track"));
		String paramString = URLEncodedUtils.format(body_args, "utf-8");

		url += paramString;

		System.out.println(url);
		JSONObject res_json = NetworkWrapper.get(url);
		// format
		res_json = res_json.getJSONObject("tracks");
		JSONArray jsonobjectsArray = (JSONArray) res_json.get("items");
		// returning the tracks found
		String MyKey, external_urls;
		lastItemSearchedInfo.clear();
		for (int i = 0; i < jsonobjectsArray.length(); i++) {
			// cle est titre-artiste
			String title = jsonobjectsArray.getJSONObject(i).getString("name");
			String artist = jsonobjectsArray.getJSONObject(i).getJSONArray("artists").getJSONObject(0)
					.getString("name");
			external_urls = jsonobjectsArray.getJSONObject(i).getJSONObject("external_urls").getString("spotify");
			MyKey = title + " - " + artist;
			lastItemSearchedInfo.put(MyKey, external_urls);
		}
		return lastItemSearchedInfo.keySet();
	}

	public JSONObject getListOfArtist(String item) {
		String url = "https://api.spotify.com/v1/search?";
		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("q", item));
		body_args.add(new BasicNameValuePair("type", "artist"));
		String paramString = URLEncodedUtils.format(body_args, "utf-8");

		url += paramString;

		System.out.println(url);
		JSONObject res_json = NetworkWrapper.get(url);
		System.out.println(res_json);

		return res_json;
	}

	public Set<String> getListOfAlbum(String item) {
		String url = "https://api.spotify.com/v1/search?";
		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("q", item));
		body_args.add(new BasicNameValuePair("type", "album"));
		String paramString = URLEncodedUtils.format(body_args, "utf-8");

		url += paramString;

		System.out.println(url);
		JSONObject res_json = NetworkWrapper.get(url);

		// format
		res_json = res_json.getJSONObject("albums");
		JSONArray jsonobjectsArray = (JSONArray) res_json.get("items");
		// returning the tracks found
		String MyKey, albumType, external_urls;
		lastItemSearchedInfo.clear();
		for (int i = 0; i < jsonobjectsArray.length(); i++) {
			// cle est titre-artiste
			MyKey = jsonobjectsArray.getJSONObject(i).getString("name");
			albumType = jsonobjectsArray.getJSONObject(i).getString("album_type");
			external_urls = jsonobjectsArray.getJSONObject(i).getJSONObject("external_urls").getString("spotify");
			MyKey += " - " + albumType;
			lastItemSearchedInfo.put(MyKey, external_urls);
		}
		return lastItemSearchedInfo.keySet();
	}

	public void get_user_info(String user_id) {
		String url = "https://api.spotify.com/v1/users/" + user_id;
		JSONObject res_json = NetworkWrapper.get(url);
		System.out.println(res_json);
	}

	@Override
	public void get_search(String search) {
		// TODO Auto-generated method stub

	}

	@Override
	public void get_album(String album_id) {
		// TODO Auto-generated method stub
	}

	public void get_tracks() {
		String url = "https://api.spotify.com/v1/me/tracks";
		JSONObject res_json = NetworkWrapper.get(url, "Authorization", "Bearer " + access_token);
		System.out.println("List of tracks");
		System.out.println(res_json);
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
		String url = "https://api.spotify.com/v1/tracks/" + track_id;

		JSONObject res_json = NetworkWrapper.get(url, "Authorization", "Bearer " + access_token);
		System.out.println("track");
		System.out.println(res_json.toString());
	}

}
