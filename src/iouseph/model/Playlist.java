package iouseph.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Playlist {

	private StringProperty id;
	private StringProperty title;
	private StringProperty owner;
	private StringProperty source;
	private StringProperty url;
	private ListProperty<Track> tracks;

	public Playlist() {
		this(null, null, null, null, null,null);
	}

	public Playlist(String id, String title, String owner, String source,
			List<Track> tracks, String url) {
		super();
		this.id = new SimpleStringProperty(id);
		this.title = new SimpleStringProperty(title);
		this.owner = new SimpleStringProperty(owner);
		this.source = new SimpleStringProperty(source);
		this.tracks = new SimpleListProperty<Track>(javafx.collections.FXCollections.observableList(new ArrayList<Track>()));
		this.url=new SimpleStringProperty(url);
	}



	/**
	 * @return
	 */
	public List<Track> getTracks() {
		return tracks.get();
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
		return title.get();
	}
	public void setTitle(String title) {
		this.title.set(title);
	}
	public String getOwner() {
		return owner.get();
	}
	public void setOwner(String owner) {
		this.owner.set(owner);
	}
	public String getSource() {
		return source.get();
	}
	public void setSource(String source) {
		this.source.set(source);
	}

	public String getId() {
		return id.get();
	}

	public void setId(String id) {
		this.id.set(id);
	}

	@Override
	public String toString() {
		return /*"Playlist [id=" + id + ", title=" + title + ", owner=" + owner
				+ ", source=" + source + ", tracks=" + tracks + "]"*/title.get();
	}

	public void setTracks(List<Track> tracks) {
		this.tracks.setAll(tracks);
	}

	public String getUrl() {
		return url.get();
	}

	public void setUrl(String myurl) {
		this.url.set(myurl);
		System.out.println(myurl);
	}
}
