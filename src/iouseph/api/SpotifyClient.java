package iouseph.api;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import iouseph.model.Playlist;
import iouseph.model.Track;
import iouseph.model.User;

public class SpotifyClient implements Iapi {

	private String host = "https://accounts.spotify.com";
	private String client_id = "ccb24bc509974a72babd14e92902f816";
	private String client_secret = "9e85225cb1324ee1bb7fa32be121a96c";
	private String redirect_uri = "http://localhost:8888/callback";
	private String access_token;

	private IParser parser;

	public SpotifyClient() {
		this.parser = new SpotifyParser();
	}

	/**
	 * @throws Exception
	 */
	public String GetAuthorizationUrl() {
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
		try {
			java.awt.Desktop.getDesktop().browse(new URI(url));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//runServer(8888);
		Class[] parameterTypes = new Class[1];
        parameterTypes[0] = String.class;
        Method method1;
		try {
			method1 = SpotifyClient.class.getMethod("retreive_token", parameterTypes);
			NetworkWrapper.runServerToListen(8888,this,method1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return url;
	}

	public void retreive_token(String code_retrieved) {
		String url = host + "/api/token";
		// recuperation de la partie qui nous importe
		// format du code GET
		// exemple
		// :/callback?code=AQA1Bw3lyz_oJU3oWBaRPIgQUkCiWFnLLecNrbROeQwJWKl92l9p0XdVqRvXguxjiYcceaceoF_oMNTqMdV6O6SQwnOFq4qXFdPfEEI-jcjk9tkYwNJMwNO8-j_ufyS543p_LZGK7ix7UMlz55_8A-S6q1Phrso3eUa5QLpEcWR3zba8VJf34F0UJp5G0ntCrw18MJTKECU5nS0JxWtkj0yT2PWthr54jgF9BdNj6SAit20M7-x3Qg
		// HTTP/1.1
		String[] parts = code_retrieved.split("=");
		parts = parts[1].split(" ");
		code_retrieved = parts[0];
		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("grant_type", "authorization_code"));
		body_args.add(new BasicNameValuePair("redirect_uri", redirect_uri));
		body_args.add(new BasicNameValuePair("code", code_retrieved));
		// client_id:secret_id en base 64

		String encodedBytes = Base64.getEncoder().encodeToString((client_id + ":" + client_secret).getBytes());

		JSONObject res_json = NetworkWrapper.post(url, body_args, "Authorization", "Basic " + encodedBytes);
		access_token = res_json.getString("access_token");
		//stopServer();

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see modele.Iapi#get_personnal_info()
	 */
	@Override
	public User get_personnal_info() {
		String url = "https://api.spotify.com/v1/me";
		return this.parser.userParse(NetworkWrapper.get(url, "Authorization", "Bearer " + access_token));
	}

	/**
	 * @param item
	 * @return
	 */
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

		/**
	 * @param user_id
	 */
	public User get_user_info(String user_id) {
		String url = "https://api.spotify.com/v1/users/" + NetworkWrapper.encode(user_id);
		return this.parser.userParse(NetworkWrapper.get(url));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see modele.Iapi#get_search(java.lang.String)
	 */
	@Override
	public List<Track> get_search(String search) {
		String url = "https://api.spotify.com/v1/search?";
		List<NameValuePair> body_args = new ArrayList<NameValuePair>();
		body_args.add(new BasicNameValuePair("q", search));
		body_args.add(new BasicNameValuePair("type", "track"));
		String paramString = URLEncodedUtils.format(body_args, "utf-8");

		url += paramString;

		System.out.println(url);
		JSONObject res_json = NetworkWrapper.get(url);
		System.out.println(res_json);
		List<Track> myTrackList = ((SpotifyParser) this.parser).tracksSearchedParse(NetworkWrapper.get(url));
		return myTrackList;
	}

	/**
	 * @return
	 *
	 */
	@Override
	public List<Track> get_tracks() {
		String url = "https://api.spotify.com/v1/me/tracks";
		List<Track> myTrackList = this.parser
				.tracksParse(NetworkWrapper.get(url, "Authorization", "Bearer " + access_token));
		return myTrackList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see modele.Iapi#get_playlist(java.lang.String)
	 */
	@Override
	public List<Track> get_playlist(String playlist_id) {

		String url = "https://api.spotify.com/v1/me/playlists";
		System.out.println(NetworkWrapper.get(url, "Authorization", "Bearer " + access_token));
		List<Playlist> myPlaylists = this.parser.playlistsParse(NetworkWrapper.get(url, "Authorization", "Bearer " + access_token));
		for(int i=0;i<myPlaylists.size();i++)
		{
			//myPlaylists.get(i).initiliasePlayList("Authorization", "Bearer " + access_token);
		}

		//return myPlaylists;
		return myPlaylists.get(0).getTracks();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see modele.Iapi#get_track(java.lang.String)
	 */
	@Override
	public Track get_track(String track_id) {
		String url = "https://api.spotify.com/v1/tracks/" + NetworkWrapper.encode(track_id);
		return this.parser.trackParse(NetworkWrapper.get(url, "Authorization", "Bearer " + access_token));
	}

	@Override
	public List<Playlist> get_playlists(String search) {
		String url = "https://api.spotify.com/v1/me/playlists";
		System.out.println(NetworkWrapper.get(url, "Authorization", "Bearer " + access_token));
		List<Playlist> myPlaylists = this.parser.playlistsParse(NetworkWrapper.get(url, "Authorization", "Bearer " + access_token));
		for(int i=0;i<myPlaylists.size();i++)
		{
			//myPlaylists.get(i).initiliasePlayList("Authorization", "Bearer " + access_token);
		}

		return myPlaylists;
	}

	@Override
	public String retreive_token() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean set_playlists(List<Playlist> playlists) {
		// TODO Auto-generated method stub
		return true;
	}

	}
