package modele;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


public class DeezerClient implements Iapi {

	private final String host = "https://api.deezer.com/";
	private String app_id = "171795";
	private String secret = "a460f3efd5e3e0c98af00730d882b5f0";
	private String redirect_uri = "http://localhost:9999/callback";
	@SuppressWarnings("unused")
	private String access_token = "";

	public void retreive_token() throws Exception {

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

	}

	public JSONObject get_personnal_info() {

		String url = host + "/infos";// me?oauth_token=" + token;
		JSONObject res = null;
		res = NetworkWrapper.get(url);
		Iterator<String> i = res.keys();
		while (i.hasNext()) {
			String s = i.next();
			System.out.println(s + " : " + res.get(s));
		}

		return res;
	}


	/**
	 * @see modele.Iapi#get_search(java.lang.String)
	 */
	public JSONObject get_search(String search) {

		String url = host + "/search?q=" + search;// +
													// "&index=0&limit=5";//me?oauth_token="
													// + token;
		JSONObject res = null;
		res = NetworkWrapper.get(url);
		return res;
	}

	/**
	 * retourne les informations de l'utilisateur connecte
	 *
	 * @param user_id	l'id du user dans deezer
	 * @return un JSONObject contenant les informations de l'utilisateur
	 */
	public JSONObject get_user_info(String user_id) {
		String url = host + "user/" + user_id;// + "/playlists";// +
												// "?client_id=" + client_id;
		JSONObject res = NetworkWrapper.get(url);
		Iterator<String> i = res.keys();
		while (i.hasNext()) {
			String s = i.next();
			System.out.println(s + " : " + res.get(s));
		}

		return res;
	}



	/**
	 *	retourne une liste de playlists
	 *
	 * @param search	le String a rechercher dana deezer
	 * @return un JSONObject contenant une liste de playlists
	 * @see modele.Iapi#get_playlists(java.lang.String)
	 */
	public JSONObject get_playlists(String search) {
		String url = host + "/search/playlist?q=" + search;
		JSONObject res = null;
		res = NetworkWrapper.get(url);
		return res;
	}

	/*
	 * retourne la liste des tracks de la playlist
	 *
	 * @see modele.Iapi#get_playlist(java.lang.String)
	 */
	@Override
	public JSONObject get_playlist(String playlist_id) {
		String url = host + "/playlist/" + playlist_id + "/tracks";
		JSONObject res = null;
		res = NetworkWrapper.get(url);
		return res;
	}

	//TODO ces methodes seront implementees dans les prochaines versions

	@Override
	public JSONObject get_album(String album_id) {
		// TODO Auto-generated method stub
		JSONObject res = null;

		return res;

	}

	@Override
	public JSONObject get_artist(String artist_id) {
		// TODO Auto-generated method stub
		JSONObject res = null;

		return res;

	}



	public JSONObject get_genre(String genre_id) {
		// TODO Auto-generated method stub
		JSONObject res = null;

		return res;

	}

	public JSONObject get_track(String track_id) {
		// TODO Auto-generated method stub
		JSONObject res = null;

		return res;

	}

	public JSONObject get_genres() {
		// TODO Auto-generated method stub
		JSONObject res = null;

		return res;

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

	public void get_podcast(String podcast_id) {
		// TODO Auto-generated method stub

	}

	public void get_radios() {
		// TODO Auto-generated method stub

	}

	public void get_radio(String radio_id) {
		// TODO Auto-generated method stub

	}
	public void get_options() {
		// TODO Auto-generated method stub

	}


}
