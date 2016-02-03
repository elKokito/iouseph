package ui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import modele.Iapi;

public class SearchBox extends VBox{
	
	private TextField searchField;
	private Button searchButton;
	private Iapi api;
	
	/**
	 * cree la section recherche de la vue
	 */
	public SearchBox(final TracksBox tracksBox, final Iapi api){
		super();
		this.setMaxHeight(100);
		this.api = api;
		
		searchField = new TextField("\"Artist\", \"Track\" or \"Album\"");
		this.getChildren().add(searchField);
		
		searchField.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				searchField.setText("");
			}
			
		});
		
		searchField.setOnKeyPressed(new EventHandler<KeyEvent>(){
			    
	        /**
	         * verifie si le bouton ENTRER est cliquer, si c'est le cas la methode {@link SearchBox#refresh()} est executee
	         * 
	         * @param ke valeur du bouton clique
	         */
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().toString().equals("ENTER"))
	            {
	                refresh();
	            }
	        }
	    });
		
		searchButton = new Button("Search");
		this.setAlignment(Pos.BOTTOM_RIGHT);
		this.getChildren().add(searchButton);
		
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {                
                refresh();
            }
        });
	}
	
	
	/**
	 * rafrechit le contenu de TracksBox et de PlaylistsBox 
	 * 
	 * @see {@link MainBox#setTrackList(org.json.JSONObject)}, {@link MainBox#setPlaylistList(org.json.JSONObject)}
	 */
	private void refresh(){
		((MainBox)this.getParent().getParent()).setTrackList(this.api.get_search(searchField.getText()));
		((MainBox)this.getParent().getParent()).setPlaylistList(this.api.get_playlists(searchField.getText()));
	}

}
