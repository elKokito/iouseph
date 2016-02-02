package iouseph;

import java.util.Vector;

import modele.Track;

import org.json.JSONException;
import org.json.JSONObject;

public class Parser {

	public static Track trackParse(JSONObject json) {
		Track track = new Track();
		track.setTitle(json.getString("title"));
		track.setExternalUrl_(json.getString("preview"));
		track.setArtist(((JSONObject) json.get("artist")).getString("name"));
		track.setAlbum_(((JSONObject) json.get("album")).getString("title"));
		track.setImage_(((JSONObject) json.get("album")).getString("cover_big"));

		return track;

	}

	public static Vector<Track> tracksParse(JSONObject json) {
		Vector<Track> tracks = new Vector<>();
		int i = 0;
		try {
			while (((JSONObject) json.getJSONArray("data").get(i)) != null) {
				tracks.add(trackParse(((JSONObject) json.getJSONArray("data")
						.get(i))));
				i++;
			}
		} catch (JSONException e) {
			return tracks;
		}
		return tracks;

	}
}
