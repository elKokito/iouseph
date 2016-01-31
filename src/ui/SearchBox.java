package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SearchBox extends VBox{
	
	private TextField searchField;
	private Button searchButton;
	
	public SearchBox(){
		super();
		this.setMaxHeight(100);
		
		searchField = new TextField("\"Artist\", \"Track\" or \"Album\"");
		this.getChildren().add(searchField);
		
		searchButton = new Button("Search");
		this.setAlignment(Pos.BOTTOM_RIGHT);
		this.getChildren().add(searchButton);
		
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println(searchField.getText());
            }
        });
	}

}
