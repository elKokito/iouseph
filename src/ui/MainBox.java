package ui;

import iouseph.Parser;

import java.util.Vector;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import modele.Iapi;
import modele.Playlist;
import modele.Track;

import org.json.JSONObject;

public class MainBox extends HBox{
	
	private MenuBox menuBox;
	private PlaylistsBox playlistsBox;
	private TracksBox tracksBox;
	private PlayerBox playerBox;
	private Iapi api;
	
	public MainBox(double uI_HEIGHT, double uI_WIDTH, Iapi api){
		super();
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(10,10,10,10));
		this.setHeight(uI_HEIGHT);
		this.setWidth(uI_WIDTH);	
		this.api = api;
		
		menuBox = new MenuBox(uI_HEIGHT, uI_WIDTH, tracksBox, api);
		this.getChildren().add(menuBox);
		
		playlistsBox = new PlaylistsBox(uI_HEIGHT, uI_WIDTH);
		this.getChildren().add(playlistsBox);
		
		tracksBox = new TracksBox(uI_HEIGHT, uI_WIDTH);
		this.getChildren().add(tracksBox);
		
		playerBox = new PlayerBox(uI_HEIGHT, uI_WIDTH);
		this.getChildren().add(playerBox);	
		
	}
	
	public void setTrackList(Playlist playlist){
		playlist.setTracks(Parser.playlistIdParse(api.get_playlist(playlist.getId())));
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

}
