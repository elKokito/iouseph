package ui;

import org.json.JSONObject;

import iouseph.Iapi;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MenuBox extends VBox{
	
	public MenuBox(double uI_HEIGHT, double uI_WIDTH, TracksBox tracksBox, Iapi deezer){
		super();
		this.setAlignment(Pos.TOP_LEFT);
		this.setPadding(new Insets(10,10,10,10));
		this.setMaxHeight(uI_HEIGHT);
		this.setMaxWidth(uI_WIDTH/5);
		
		Text menuText = new Text("Menu");
		menuText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		this.getChildren().add(menuText);
		
		SearchBox searchBox = new SearchBox(tracksBox, deezer);
		this.getChildren().add(searchBox);
		
		ButtonBox buttonBox = new ButtonBox();
		this.getChildren().add(buttonBox);
	}

}
