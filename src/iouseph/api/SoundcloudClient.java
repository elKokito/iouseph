package iouseph.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import iouseph.model.Playlist;
import iouseph.model.Track;
import iouseph.model.User;


/** client utilisant l'API public de Soundcloud [https://developers.soundcloud.com/docs/api/reference]
 * et respectant l'interface Iapi
 *
 * @author Marcial Lopez-Ferrada
 *
 */
public class SoundcloudClient implements Iapi {

	/**
	 * String qui contient l'url de l'api de Soundcloud
	 */
	private final String host = "http://api.soundcloud.com/";
	/**
	 * String de l'id de l'application enregister chez soundcloud
	 */
	private String client_id = "0b26692829174e89b8c12870cbdc77aa";
	/**
	 * String du token permanent lorsqu'une application est enregistrer chez soundcloud
	 */
	private String client_secret = "c792cfd55e331d931f074b8d8a7f351a";
	/**
	 * String du token ressu pour la periode d'utilisation de l'application par un user
	 */
	private String token ="";

	/** methode pour s'authentifier aupres de soundcloud et obtenir un token de session
	 * @param username 	String username de l'utilisateur
	 * @param password	String password de l'utilisateur
	 */

	private IParser parser;

	public SoundcloudClient() {
		this.parser = new SoundCloudParser();
	}

	@Override
	public String retreive_token(){
		return token;}
	public void retreive_token(String username, String password) {

		String url = "https://api.soundcloud.com/oauth2/token";

		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("client_id", client_id));
		body_args.add(new BasicNameValuePair("client_secret", client_secret));
		body_args.add(new BasicNameValuePair("grant_type", "password"));
		body_args.add(new BasicNameValuePair("username", username));
		body_args.add(new BasicNameValuePair("password", password));
		body_args.add(new BasicNameValuePair("scope", "non-expiring"));

		JSONObject res = NetworkWrapper.post(url, body_args);
		token = res.getString("access_token");

	}

	/**
	 * methode permettant de recuperer l'information personnel de l'utilisateur
	 */
	@Override
	public User get_personnal_info() {
		String url = host + "me?oauth_token=" + token;
		return this.parser.userParse(NetworkWrapper.get(url));
	}

	/** methode permettant de recuperer l'information d'un membre de soundcloud
	 * @param user_id	id du membre rechercher
	 */
	public JSONObject get_user_info(String user_id) {
		String url = host + "users/" + user_id + "?client_id=" + client_id;
		JSONObject res = NetworkWrapper.get(url);
		System.out.println(res.toString());

		return res;
	}

	public JSONObject  resolve(String soundcloud_url) {
		String url = host + "resolve?url=" + soundcloud_url + "&client_id=" + client_id;
		JSONObject res = NetworkWrapper.get(url);
		System.out.println(res.toString());

		return res;
	}

	@Override
	public List<Track> get_tracks() {
		String url = host + "tracks?client_id=" + client_id;
		//return this.parser.tracksParse(get_array(url)); //TODO si possible changer en JSONObject
		return null;
	}

	@Override
	public List<Track> get_search(String query) {
		// TODO fix return
		String url = host + "tracks?q=" + query + "&client_id=" + client_id;
		JSONArray res = NetworkWrapper.get_array(url);
		System.out.println(res.toString());
		return null;

	}

	@Override
	public Track get_track(String song_id) {
		String url = host + "tracks/" + song_id + "?client_id=" + client_id;
		return this.parser.trackParse(NetworkWrapper.get(url));
	}



	@Override
	public List<Track> get_playlist(String playlist_id) {
		String url = null;// TODO Auto-generated method stub
		return this.parser.playlistIdParse(NetworkWrapper.get(url));

	}

	@Override
	public List<Playlist> get_playlists(String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean set_playlists(List<Playlist> playlists) {
		// TODO Auto-generated method stub
		return true;
	}
/* prochaines versions
	public JSONObject get_album(String album_id) {
		// TODO Auto-generated method stub
		JSONObject res = null;

		return res;

	}
	public JSONObject get_artist(String artist_id) {
		// TODO Auto-generated method stub
		JSONObject res = null;

		return res;

	}
	public JSONObject get_genres() {
		// TODO Auto-generated method stub
		JSONObject res = null;

		return res;

	}
	public JSONObject  get_genre(String genre_id) {
		// TODO Auto-generated method stub
		JSONObject res = null;

		return res;

	}*/
}
