package iouseph.view;

import iouseph.MainController;
import iouseph.model.Playlist;
import iouseph.model.Track;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;
import modele.DeezerClient;
import modele.Iapi;

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
	@FXML
	private TextField searchTextField;

 // Reference to the main application.
    private MainController mainController;

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
        playlistList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPlaylistDetails(newValue));
    }

	/**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;

        // Add observable list data to the ListViews
        trackList.setItems(this.mainController.getTracks());
        playlistList.setItems(this.mainController.getPlaylists());
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
            // track is null, remove all the text.
        	trackTitleLabel.setText("Title");
        	artistNameLabel.setText("Artist");
        	albumTitleLabel.setText("Album");
        	Image image = new Image("http://code.makery.ch/assets/library/javafx-8-tutorial/part3/handle-delete.png");
        	coverImage.setImage(image);
        	loadTrack("https://github.com/elKokito/iouseph");
        }
    }

    private void showPlaylistDetails(Playlist playlist) {
    	mainController.getTracks().clear();
		mainController.getTracks().addAll(api.get_playlist(playlist.getId()));
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
	    mainController.showLoginLayout("http://www.deezer.com");

	}

	@FXML
	private void handleSearchTextField(KeyEvent ke){
		/**
         * verifie si le bouton ENTRER est cliquer, si c'est le cas la methode {@link SearchBox#refresh()} est executee
         *
         * @param ke valeur du bouton clique
         */
		 if (ke.getCode().toString().equals("ENTER"))
         {
			 handleSearch();
         }
	}

	private Iapi api = new DeezerClient();
	@FXML
	private void handleSearch(){

		mainController.getTracks().clear();
		mainController.getTracks().addAll(api.get_search(searchTextField.getText()));
		mainController.getPlaylists().clear();
		mainController.getPlaylists().addAll(api.get_playlists(searchTextField.getText()));
	}


}
