package ui;

import org.json.JSONArray;
import org.json.JSONObject;

import iouseph.Iapi;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class MainBox extends HBox{
	
	private MenuBox menuBox;
	private PlaylistsBox playlistsBox;
	private TracksBox tracksBox;
	private PlayerBox playerBox;
	
	public MainBox(double uI_HEIGHT, double uI_WIDTH, Iapi deezer){
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
	
	public void setList(JSONObject jsonArray){
		tracksBox.setList(jsonArray);
	}

}
