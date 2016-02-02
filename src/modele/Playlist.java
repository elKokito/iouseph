package modele;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

	private String id;
	private String title;
	private String owner;
	private String source;
	private List<Track> tracks;
	
	public Playlist() {
		this.id = new String();
		this.title = new String();
		this.owner = new String();
		this.source = new String();
		this.tracks  = new ArrayList<Track>();
	}
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Playlist [id=" + id + ", title=" + title + ", owner=" + owner
				+ ", source=" + source + ", tracks=" + tracks + "]";
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
		
	
}
