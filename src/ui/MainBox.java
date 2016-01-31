package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class MainBox extends HBox{
	
	public MainBox(double uI_HEIGHT, double uI_WIDTH){
		super();
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(10,10,10,10));
		this.setHeight(uI_HEIGHT);
		this.setWidth(uI_WIDTH);
		
		
		MenuBox menuBox = new MenuBox(uI_HEIGHT, uI_WIDTH);
		this.getChildren().add(menuBox);
		
		PlaylistsBox playlistsBox = new PlaylistsBox(uI_HEIGHT, uI_WIDTH);
		this.getChildren().add(playlistsBox);
		
		TracksBox tracksBox = new TracksBox(uI_HEIGHT, uI_WIDTH);
		this.getChildren().add(tracksBox);
		
		PlayerBox playerBox = new PlayerBox(uI_HEIGHT, uI_WIDTH);
		this.getChildren().add(playerBox);
		
		
	}

}
