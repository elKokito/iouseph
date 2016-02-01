package modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class DeezerClient implements Iapi {

	private final String host = "http://api.deezer.com/";
	private String client_id = "171795";
	private String client_secret = "a460f3efd5e3e0c98af00730d882b5f0";
	private String token = "ny80x01z2456a7bff4829f7h9TJ0R3D56a7bff482a3dtzk9wDL";

	public void retreive_token() {

		String url = "https://api.deezer.com/oauth2/token";

		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("client_id", client_id));
		body_args.add(new BasicNameValuePair("client_secret", client_secret));
		body_args.add(new BasicNameValuePair("grant_type", "password"));
		body_args.add(new BasicNameValuePair("username", "eymen.zalila@me.com"));
		body_args.add(new BasicNameValuePair("password", "a5z4&988"));
		body_args.add(new BasicNameValuePair("scope", "non-expiring"));

		NetworkWrapper.get(
				"https://connect.deezer.com/oauth/auth.php?app_id=171795&redirect_uri=http://localhost:8080/callback.html&perms=basic_access,email,offline_access,manage_library,listening_history");
		JSONObject res = NetworkWrapper.post(url, body_args);
		// token = res.getString("access_token");

	}

	public void get_personnal_info() {

		String url = host + "/infos";// me?oauth_token=" + token;
		JSONObject res = null;
		res = NetworkWrapper.get(url);
		// System.out.println(res.toString());
		Iterator<String> i = res.keys();
		while (i.hasNext()) {
			String s = i.next();
			System.out.println(s + " : " + res.get(s));
		}
	}

	public void get_search(String search) {

		String url = host + "/search?q=" + search;// +
													// "&index=0&limit=5";//me?oauth_token="
													// + token;
		JSONObject res = null;
		res = NetworkWrapper.get(url);
		// System.out.println(res.toString());
		Iterator<String> i = res.keys();
		while (i.hasNext()) {
			String s = i.next();
			System.out.println(s + " : " + res.get(s));
		}
	}

	public void get_user_info(String user_id) {
		String url = host + "user/" + user_id;// + "/playlists";// +
												// "?client_id=" + client_id;
		JSONObject res = NetworkWrapper.get(url);
		// System.out.println(res.toString());
		Iterator<String> i = res.keys();
		while (i.hasNext()) {
			String s = i.next();
			System.out.println(s + " : " + res.get(s));
		}
	}

	@Override
	public void get_album(String album_id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void get_artist(String artist_id) {
		// TODO Auto-generated method stub

	}

	public void get_chart() {
		// TODO Auto-generated method stub

	}

	public void get_comment(String comment_id) {
		// TODO Auto-generated method stub

	}

	public void get_editorials() {
		// TODO Auto-generated method stub

	}

	public void get_editorial(String editorial_id) {
		// TODO Auto-generated method stub

	}

	public void get_genres() {
		// TODO Auto-generated method stub

	}

	public void get_genre(String genre_id) {
		// TODO Auto-generated method stub

	}

	public void get_options() {
		// TODO Auto-generated method stub

	}

	public void get_playlist(String playlist_id) {
		// TODO Auto-generated method stub

	}

	public void get_podcast(String podcast_id) {
		// TODO Auto-generated method stub

	}

	public void get_radios() {
		// TODO Auto-generated method stub

	}

	public void get_radio(String radio_id) {
		// TODO Auto-generated method stub

	}

	public void get_track(String track_id) {
		// TODO Auto-generated method stub

	}

}
