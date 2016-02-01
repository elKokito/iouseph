package modele;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
	private List<Track> tracks = new ArrayList<Track>();

	/**
	 * @return
	 */
	public List<Track> getTracks() {
		return tracks;
	}
	/**
	 * @param track
	 * @return
	 */
	public boolean addTrack(Track track)
	{
		if(!tracks.contains(track)) {
			tracks.add(track);
			return true;
		}
		return false;
	}
	
	/**
	 * @param track
	 * @return
	 */
	public boolean deleteTrack(Track track){
		return tracks.remove(track);
	}
	
	
	/**
	 * 
	 */
	public void clearPlaylist()
	{
		tracks.clear();
	}
}
