package ui;

import iouseph.Parser;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import modele.DeezerClient;
import modele.Iapi;
import modele.Playlist;
import modele.SoundcloudClient;
import modele.SpotifyClient;
import modele.Track;

import org.json.JSONObject;

public class MainBox extends HBox{
	
	private MenuBox menuBox;
	private PlaylistsBox playlistsBox;
	private TracksBox tracksBox;
	private PlayerBox playerBox;
	private Iapi deezer = new DeezerClient();
	private Iapi soundCloud = new SoundcloudClient();
	private Iapi spotify = new SpotifyClient();
	
	
	
	/**
	 * cree le Panel principale de la vue
	 * 
	 * @param uI_HEIGHT	pour la longueur de la fenetre 
	 * @param uI_WIDTH	pour la largeur de la fenetre
	 * 
	 * 
	 * @see HBox
	 */
	public MainBox(double uI_HEIGHT, double uI_WIDTH){
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
	
	
	public void setSpotifyWaitStatus()
	{
		((SpotifyClient)spotify).GetAuthorizationUrl();
	}
	
	
	/**
	 * actualise la liste des Tracks dans le TracksBox
	 * 
	 * @param playlist
	 * @see {@link TracksBox#refresh(java.util.List)}
	 */
	public void setTrackList(Playlist playlist){
		playlist.setTracks(Parser.playlistIdParse(deezer.get_playlist(playlist.getId())));
		tracksBox.refresh(playlist.getTracks());
	}
	
	
	/**
	 * actualise la liste des Tracks dans le TracksBox
	 * 
	 * @param json
	 * @see {@link TracksBox#refresh(java.util.List)}, {@link TracksBox#setList(JSONObject)}
	 */
	public void setTrackList(JSONObject json){
		tracksBox.setList(json);
	}
	
	/**
	 * actualise la liste des Playlists dans le TracksBox
	 * 
	 * @param json
	 * @see {@link PlaylistsBox#setList(JSONObject)}
	 */
	public void setPlaylistList(JSONObject json){
		playlistsBox.setList(json);
	}
	
	/**
	 * change les donnees dna sle PlayerBox et lance la lecture de la musique
	 * 
	 * @param track
	 * @see {@link PlayerBox#refresh(Track)}
	 */
	public void setPlayer(Track track){
		playerBox.refresh(track);		
	}

	/**
	 * @return DeezerClient
	 */
	public Iapi getDeezer() {
		return deezer;
	}

	/**
	 * @param deezer
	 */
	public void setDeezer(Iapi deezer) {
		this.deezer = deezer;
	}

	/**
	 * @return SoundCloudClient
	 */
	public Iapi getSoundCloud() {
		return soundCloud;
	}

	/**
	 * @param soundCloud
	 */
	public void setSoundCloud(Iapi soundCloud) {
		this.soundCloud = soundCloud;
	}

	/**
	 * @return SpotifyClient
	 */
	public Iapi getSpotify() {
		return spotify;
	}

	/**
	 * @param spotify
	 */
	public void setSpotify(Iapi spotify) {
		this.spotify = spotify;
	}
	

}
