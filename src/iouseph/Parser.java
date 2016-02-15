package iouseph;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import iouseph.model.Playlist;
import iouseph.model.Track;

public class Parser {

	/**
	 * retourne un objet Track, il est parse a partir d'un JSONObject
	 *
	 * @param json	un JSONObject recupere depuis le service de streaming
	 * @return	Track
	 * @see Parser#tracksParse(JSONObject)
	 */
	public static Track trackParse(JSONObject json) {
		Track track = new Track();
		track.setId(String.valueOf(json.getInt("id")));
		track.setTitle(json.getString("title"));
		track.setExternalUrl(json.getString("preview"));
		track.setArtist(((JSONObject) json.get("artist")).getString("name"));
		track.setAlbum(((JSONObject) json.get("album")).getString("title"));
		track.setImage(((JSONObject) json.get("album")).getString("cover_big"));
		System.out.println(track);
		return track;
	}

	/**
	 * retourne une List<Track>, elle est parse a partir d'un JSONObject
	 *
	 * @param json	un JSONObject recupere depuis le service de streaming
	 *
	 * @return	ArrayList<Track>
	 */
	public static List<Track> tracksParse(JSONObject json) {
		List<Track> tracks = new ArrayList<>();
		int i = 0;
		try {
			while (((JSONObject) json.getJSONArray("data").get(i)) != null) {
				tracks.add(trackParse(((JSONObject) json.getJSONArray("data")
						.get(i))));
				i++;;
			}
		} catch (JSONException e) {

			return tracks;
		}
		return tracks;
	}

	/**
	 * retourne un objet Playlist, il est parse a partir d'un JSONObject
	 *
	 * @param json	un JSONObject recupere depuis le service de streaming
	 * @return	Playlist
	 * @see Parser#playlistsParse(JSONObject)
	 */
	public static Playlist playlistParse(JSONObject json){
		Playlist playlist = new Playlist();
		playlist.setId(String.valueOf(json.getInt("id")));
		playlist.setTitle(json.getString("title"));
		playlist.setOwner(((JSONObject) json.get("user")).getString("name"));

		return playlist;
	}


	/**
	 * retourne une List<Playlist>, elle est parse a partir d'un JSONObject
	 *
	 * @param json	un JSONObject recupere depuis le service de streaming
	 *
	 * @return	ArrayList<Playlist>
	 */
	public static List<Playlist> playlistsParse(JSONObject json){
		List<Playlist> playlists = new ArrayList<>();
		int i = 0;
		try {
			while (((JSONObject) json.getJSONArray("data").get(i)) != null) {
				playlists.add(playlistParse(((JSONObject) json.getJSONArray("data")
						.get(i))));
				i++;
			}
		} catch (JSONException e) {
			return playlists;
		}

		return playlists;
	}


	public static List<Track> playlistIdParse(JSONObject json) {
		/*List<Track> tracks = new ArrayList<>();
		int i = 0;
		System.out.println(((JSONObject) (json.getJSONArray("tracks")).get(0)));
		try {
			while (((JSONObject) (json.getJSONArray("tracks")).get(i)) != null) {
				tracks.add(trackParse(((JSONObject) json.getJSONArray("tracks")
						.get(i))));
				i++;
			}
		} catch (JSONException e) {
			return tracks;
		}*/
		return tracksParse(json);
	}
}
