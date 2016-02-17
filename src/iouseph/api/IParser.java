package iouseph.api;

import java.util.List;

import org.json.JSONObject;

import iouseph.model.Playlist;
import iouseph.model.Track;

public interface IParser {

	/**
	 * retourne un objet Track, il est parse a partir d'un JSONObject
	 *
	 * @param json	un JSONObject recupere depuis le service de streaming
	 * @return	Track
	 * @see IParser#tracksParse(JSONObject)
	 */
	public Track trackParse(JSONObject json);

	/**
	 * retourne une List<Track>, elle est parse a partir d'un JSONObject
	 *
	 * @param json	un JSONObject recupere depuis le service de streaming
	 *
	 * @return	ArrayList<Track>
	 */
	public List<Track> tracksParse(JSONObject json);

	/**
	 * retourne un objet Playlist, il est parse a partir d'un JSONObject
	 *
	 * @param json	un JSONObject recupere depuis le service de streaming
	 * @return	Playlist
	 * @see IParser#playlistsParse(JSONObject)
	 */
	public Playlist playlistParse(JSONObject json);

	/**
	 * retourne une List<Playlist>, elle est parse a partir d'un JSONObject
	 *
	 * @param json	un JSONObject recupere depuis le service de streaming
	 *
	 * @return	ArrayList<Playlist>
	 */
	public List<Playlist> playlistsParse(JSONObject json);

	public List<Track> playlistIdParse(JSONObject json);
}
