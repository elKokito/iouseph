package iouseph.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import iouseph.model.Playlist;
import iouseph.model.Track;
import iouseph.model.User;


public class DeezerClient implements Iapi{

	private final String host = "https://api.deezer.com/";
	private String app_id = "171795";
	private String secret = "a460f3efd5e3e0c98af00730d882b5f0";
	private String redirect_uri = "http://localhost:9999/callback";
	private String access_token = "";

	private IParser parser;

	public DeezerClient() {
		this.parser = new DeezerParser();
	}

	public String retreive_token() throws Exception {

		/*
		 * https://connect.deezer.com/oauth/auth.php?app_id=YOUR_APP_ID&redirect_uri
		 * =YOUR_REDIRECT_URI&perms=YOUR_PERMS
		 * http://redirect_uri?error_reason=user_denied
		 * http://redirect_uri?code=A_CODE_GENERATED_BY_DEEZER
		 * https://connect.deezer.com/oauth/access_token.php
		 * https://connect.deezer
		 * .com/oauth/access_token.php?app_id=YOU_APP_ID&secret
		 * =YOU_APP_SECRET&code=THE_CODE_FROM_ABOVE
		 */
		String url = "https://connect.deezer.com/oauth/auth.php?";
		String perms = "basic_access,email,offline_access,manage_library,listening_history";

		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("app_id", app_id));
		body_args.add(new BasicNameValuePair("redirect_uri", redirect_uri));
		body_args.add(new BasicNameValuePair("perms", perms));
		String paramString = URLEncodedUtils.format(body_args, "utf-8");

		//url += "app_id=" + app_id + "&redirect_uri=" + redirect_uri + "&perms=" + perms;
		url += paramString;
		System.out.println(url);
		java.awt.Desktop.getDesktop().browse(new URI(url));
		String code_retrieved = NetworkWrapper.runServerToListen(9999);
		System.out.println(code_retrieved);
		url = "https://connect.deezer.com/oauth/access_token.php?";
		String[] parts = code_retrieved.split("=");
		parts = parts[1].split(" ");
		code_retrieved = parts[0];

		body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("app_id", app_id));
		body_args.add(new BasicNameValuePair("secret", secret));
		body_args.add(new BasicNameValuePair("code", code_retrieved));
		paramString = URLEncodedUtils.format(body_args, "utf-8");

		//url += "app_id=" + app_id + "&secret=" + secret + "&code="+ code_retrieved;
		url += paramString;
		JSONObject res_json = NetworkWrapper.post(url, body_args);
		access_token = res_json.getString("access_token");

		return access_token;
	}

	public User get_personnal_info() {
		String url = host + "/infos";// me?oauth_token=" + token;
		return this.parser.userParse(NetworkWrapper.get(url));
	}


	/**
	 * @see iouseph.api.model.Iapi#get_search(java.lang.String)
	 */
	public List<Track> get_search(String search) {
		String url = host + "/search?q=" + search;// +
													// "&index=0&limit=5";//me?oauth_token="
													// + token;
		return this.parser.tracksParse(NetworkWrapper.get(url));
	}

	/**
	 * retourne les informations de l'utilisateur connecte
	 *
	 * @param user_id	l'id du user dans deezer
	 * @return un JSONObject contenant les informations de l'utilisateur
	 */
	public User get_user_info(String user_id) {
		String url = host + "user/" + user_id;// + "/playlists";// +
												// "?client_id=" + client_id;
		return this.parser.userParse(NetworkWrapper.get(url));
	}

	/**
	 *	retourne une liste de playlists
	 *
	 * @param search	le String a rechercher dana deezer
	 * @return une List<Playlist> contenant une liste de playlists
	 * @see iouseph.api.model.Iapi#get_playlists(java.lang.String)
	 */
	public List<Playlist> get_playlists(String search) {
		String url = host + "/search/playlist?q=" + search;
		return this.parser.playlistsParse(NetworkWrapper.get(url));
	}

	/*
	 * retourne la liste des tracks de la playlist
	 *
	 * @see iouseph.api.model.Iapi#get_playlist(java.lang.String)
	 */
	@Override
	public List<Track> get_playlist(String playlist_id) {
		String url = host + "/playlist/" + playlist_id + "/tracks";
		return this.parser.playlistIdParse(NetworkWrapper.get(url));
	}

	public Track get_track(String track_id) {
		String url = host + "track/" + track_id;
		return this.parser.trackParse(NetworkWrapper.get(url));
	}

	@Override
	public List<Track> get_tracks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean set_playlists(List<Playlist> playlists) {
		// TODO Auto-generated method stub
		return true;
	}

	//TODO ces methodes seront implementees dans les prochaines versions
	/*
	public Album get_album(String album_id) {
	}

	public Artist get_artist(String artist_id) {
	}

	public Genre get_genre(String genre_id) {
	}

	public List<Genre> get_genres() {
	}

	public void get_chart() {
	}

	public Comment get_comment(String comment_id) {
	}

	public List<Editorial> get_editorials() {
	}

	public Editorial get_editorial(String editorial_id) {
	}

	public Podcast get_podcast(String podcast_id) {
	}

	public List<Radio> get_radios() {
	}

	public Radio get_radio(String radio_id) {
	}
	public List<Option> get_options() {
	}
	*/

}
