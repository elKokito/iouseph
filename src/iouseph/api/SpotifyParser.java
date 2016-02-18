package iouseph.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import iouseph.model.Playlist;
import iouseph.model.Track;
import iouseph.model.User;

public class SpotifyParser implements IParser{

	@Override
	public Track trackParse(JSONObject json) {
		Track track = new Track();
		track.setId(json.getString("id"));
		track.setTitle(json.getString("name"));
		track.setExternalUrl(json.getJSONObject("external_urls").getString("spotify"));
		track.setArtist(json.getJSONArray("artists").getJSONObject(0).getString("name"));
		track.setAlbum(json.getJSONObject("album").getString("name"));
		track.setImage(json.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url"));
		track.setSource("Spotify");
		return track;
	}

	@Override
	public List<Track> tracksParse(JSONObject json) {
		List<Track> currentUserTracks = new ArrayList<Track>();

		JSONArray jsonobjectsArray = (JSONArray) json.get("items");
		for(int i=0;i<jsonobjectsArray.length();i++)
		{
			currentUserTracks.add(trackParse((jsonobjectsArray.getJSONObject(i)).getJSONObject("track")));
		}
		return currentUserTracks;
	}

	@Override
	public Playlist playlistParse(JSONObject json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Playlist> playlistsParse(JSONObject json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Track> playlistIdParse(JSONObject json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject playlistsParse(List<Playlist> playlists) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User userParse(JSONObject json) {
		// TODO Auto-generated method stub
		return null;
	}

}
