package iouseph.view;

import iouseph.MainApp;
import iouseph.model.Playlist;
import iouseph.model.Track;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;

public class MainLayoutController {
	@FXML
    private ListView<Track> trackList;
	@FXML
    private ListView<Playlist> playlistList;
	@FXML
    private Label trackTitleLabel;
    @FXML
    private Label artistNameLabel;
    @FXML
    private Label albumTitleLabel;
    @FXML
    private ImageView coverImage;
    @FXML
    private WebView player;

 // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
	public MainLayoutController() {
		super();
	}

	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the track list
        //trackList.setCellFactory(cellData -> cellData.getTitle());

    	// Clear person details.
        showTrackDetails(null);

        // Listen for selection changes and show the person details when changed.
        trackList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTrackDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the ListViews
        trackList.setItems(this.mainApp.getTracks());
        playlistList.setItems(this.mainApp.getPlaylists());
    }

    /**
     * Fills all text fields to show details about a track.
     * If the specified track is null, all text fields are cleared.
     *
     * @param track
     */
    private void showTrackDetails(Track track) {
        if (track != null) {
            // Fill the labels with info from the track object.
        	trackTitleLabel.setText(track.getTitle());
        	artistNameLabel.setText(track.getArtist());
        	albumTitleLabel.setText(track.getAlbum());
        	//duration.setText(Float.toString(track.getDuration()));
        	Image image = new Image(track.getImage());
        	coverImage.setImage(image);
        	loadTrack(track.getExternalUrl());

        } else {
            // Person is null, remove all the text.
        	trackTitleLabel.setText("Title");
        	artistNameLabel.setText("Artist");
        	albumTitleLabel.setText("Album");
        	Image image = new Image("http://code.makery.ch/assets/library/javafx-8-tutorial/part3/handle-delete.png");
        	coverImage.setImage(image);
        	loadTrack("https://github.com/elKokito/iouseph");
        }
    }

    /**
	 * execute la lecture
	 *
	 * @param url lien vers la chanson dans le service de streaming
	 */
	public void loadTrack(String url){
		player.getEngine().load(url);
	}

	@FXML
	private void handleDeezer() {
	    mainApp.showLoginLayout("http://www.deezer.com");

	}
}
