package iouseph;

import iouseph.api.DeezerClient;
import iouseph.api.Iapi;
import iouseph.api.SpotifyClient;
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

	private MainController mainController;
	private Iapi api;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
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
		// trackList.setCellFactory(cellData -> cellData.getTitle());

		// Clear person details.
		showTrackDetails(null);

		// Listen for selection changes and show the person details when
		// changed.
		trackList.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showTrackDetails(newValue));
		playlistList.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showPlaylistDetails(newValue));
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
	 * Fills all text fields to show details about a track. If the specified
	 * track is null, all text fields are cleared.
	 *
	 * @param track
	 */
	private void showTrackDetails(Track track) {
		if (track != null) {
			// Fill the labels with info from the track object.
			trackTitleLabel.setText(track.getTitle());
			artistNameLabel.setText(track.getArtist());
			albumTitleLabel.setText(track.getAlbum());
			// duration.setText(Float.toString(track.getDuration()));
			coverImage.setImage(new Image(track.getImage()));
			loadTrack(track.getExternalUrl());
		} else {
			// track is null, remove all the text.
			trackTitleLabel.setText("Title");
			artistNameLabel.setText("Artist");
			albumTitleLabel.setText("Album");
			coverImage.setImage(new Image("file:res/Iouseph-logo.png"));
			loadTrack("https://github.com/elKokito/iouseph");
		}
	}

	private void showPlaylistDetails(Playlist playlist) {
		mainController.getTracks().clear();
		playlist.setTracks(api.get_playlist(playlist.getId()));// TODO affecter
																// les track a
																// la playlist
																// dans le parse
		mainController.getTracks().addAll(playlist.getTracks());
		showTrackDetails(playlist.getTracks().get(0));
	}

	/**
	 * execute la lecture
	 *
	 * @param url
	 *            lien vers la chanson dans le service de streaming
	 */
	public void loadTrack(String url) {
		player.getEngine().load(url);
	}

	@FXML
	private void handleDeezer() {
		mainController.showLoginLayout("http://www.deezer.com/login");

	}

	@FXML
	private void handleSpotify() {
		mainController.showLoginLayout(
				"https://accounts.spotify.com/login?continue=https%3A%2F%2Fwww.spotify.com%2Fca-fr%2Faccount%2Foverview%2F");// TODO

	}

	@FXML
	private void handleSoundCloud() {
		mainController.showLoginLayout(
				"https://soundcloud.com/connect?client_id=02gUJC0hH2ct1EGOcYXQIzRFU91c72Ea&response_type=token&scope=non-expiring%20fast-connect%20purchase%20upload&display=next&redirect_uri=https%3A//soundcloud.com/soundcloud-callback.html#tab-connect");// TODO

	}

	/**
	 * verifie si le bouton ENTRER est cliquer, si c'est le cas la methode
	 * {@link MainLayoutController#handleSearch()} est executee
	 *
	 * @param ke
	 *            valeur du bouton clique
	 */
	@FXML
	private void handleSearchTextField(KeyEvent ke) {
		if (ke.getCode().toString().equals("ENTER")) {
			handleSearch();
		}
	}

	@FXML
	private void handleSearch() {
		api = new DeezerClient();
		mainController.getTracks().clear();
		mainController.getTracks().addAll(api.get_search(searchTextField.getText()));
		mainController.getPlaylists().clear();
		mainController.getPlaylists().addAll(api.get_playlists(searchTextField.getText()));
	}

}
