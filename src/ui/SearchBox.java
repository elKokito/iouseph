package ui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import modele.Iapi;

public class SearchBox extends VBox{
	
	private TextField searchField;
	private Button searchButton;
	
	public SearchBox(final TracksBox tracksBox, final Iapi deezer){
		super();
		this.setMaxHeight(100);
		
		searchField = new TextField("\"Artist\", \"Track\" or \"Album\"");
		this.getChildren().add(searchField);
		
		searchField.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				searchField.setText("");
			}
			
		});
		
		/*searchField.setOnKeyPressed(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				System.out.println(searchField.getText());
                ((MainBox)SearchBox.this.getParent().getParent()).setList(deezer.get_search(searchField.getText()));
			}
			
		});*/
		
		searchButton = new Button("Search");
		this.setAlignment(Pos.BOTTOM_RIGHT);
		this.getChildren().add(searchButton);
		
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println(searchField.getText());
                ((MainBox)SearchBox.this.getParent().getParent()).setList(deezer.get_search(searchField.getText()));
            }
        });
	}

}
