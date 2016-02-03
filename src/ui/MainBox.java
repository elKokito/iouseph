package ui;

import iouseph.Parser;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import modele.DeezerClient;
import modele.Iapi;
import modele.Playlist;
import modele.SoundcloudClient;
import modele.SpotifyClient;
import modele.Track;

import org.json.JSONObject;

public class MainBox extends HBox{
	
	private MenuBox menuBox;
	private PlaylistsBox playlistsBox;
	private TracksBox tracksBox;
	private PlayerBox playerBox;
	private Iapi deezer = new DeezerClient();
	private Iapi soundCloud = new SoundcloudClient();
	private Iapi spotify = new SpotifyClient();
	
	public MainBox(double uI_HEIGHT, double uI_WIDTH){
		super();
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(10,10,10,10));
		this.setHeight(uI_HEIGHT);
		this.setWidth(uI_WIDTH);
		
		menuBox = new MenuBox(uI_HEIGHT, uI_WIDTH, tracksBox, deezer);
		this.getChildren().add(menuBox);
		
		playlistsBox = new PlaylistsBox(uI_HEIGHT, uI_WIDTH);
		this.getChildren().add(playlistsBox);
		
		tracksBox = new TracksBox(uI_HEIGHT, uI_WIDTH);
		this.getChildren().add(tracksBox);
		
		playerBox = new PlayerBox(uI_HEIGHT, uI_WIDTH);
		this.getChildren().add(playerBox);	
		
	}
	public void setSpotifyWaitStatus()
	{
		((SpotifyClient)spotify).GetAuthorizationUrl();
	}
	public void setTrackList(Playlist playlist){
		playlist.setTracks(Parser.playlistIdParse(deezer.get_playlist(playlist.getId())));
		tracksBox.refresh(playlist.getTracks());
	}
	
	public void setTrackList(JSONObject json){
		tracksBox.setList(json);
	}
	
	public void setPlaylistList(JSONObject json){
		playlistsBox.setList(json);
	}
	
	public void setPlayer(Track track){
		playerBox.refresh(track);		
	}

	public Iapi getDeezer() {
		return deezer;
	}

	public void setDeezer(Iapi deezer) {
		this.deezer = deezer;
	}

	public Iapi getSoundCloud() {
		return soundCloud;
	}

	public void setSoundCloud(Iapi soundCloud) {
		this.soundCloud = soundCloud;
	}

	public Iapi getSpotify() {
		return spotify;
	}

	public void setSpotify(Iapi spotify) {
		this.spotify = spotify;
	}
	

}
