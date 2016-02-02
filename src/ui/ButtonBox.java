package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import modele.NetworkWrapper;

public class ButtonBox extends VBox{
	
	private Button myPlaylistsButton;
	private String soundCloudImagePath = "https://a-v2.sndcdn.com/assets/images/sc-icons/fluid-b4e7a64b.png";
	private String spotifyImagePath = "https://d2c87l0yth4zbw.global.ssl.fastly.net/i/_global/touch-icon-144.png";
	private String deezerImagePath = "http://e-cdn-files.deezer.com/images/common/favicon/favicon-196x196-v00347634.png";        
		
	public ButtonBox(){
		super();
		this.setAlignment(Pos.TOP_CENTER);
		this.setMinHeight(400);
		this.setMinWidth(100);
		this.setPadding(new Insets(25, 25, 0, 0));				
		
		//TODO changer pour les URLs de connexion des clients
        LoginBox soundCloudLoginBox = new LoginBox(soundCloudImagePath, "SoundCloud", "http://www.soundcloud.com");
        LoginBox spotifyLoginBox = new LoginBox(spotifyImagePath, "Spotify", "https://accounts.spotify.com/authorize/?client_id=ccb24bc509974a72babd14e92902f816&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A8888%2Fcallback&scope=playlist-read-private+playlist-read-collaborative+playlist-modify-public+playlist-modify-private+streaming+user-follow-modify+user-follow-read+user-library-read+user-library-modify+user-read-private+user-read-birthdate+user-read-email");
        LoginBox deezerLoginBox = new LoginBox(deezerImagePath, "Deezer", "http://www.deezer.com");
        this.getChildren().add(soundCloudLoginBox);
        this.getChildren().add(spotifyLoginBox);
        this.getChildren().add(deezerLoginBox);        
       
		myPlaylistsButton = new Button("My Playlists");
		myPlaylistsButton.setOnAction(new EventHandler<ActionEvent>() {
			 
            @Override
            public void handle(ActionEvent event) {
                //Verifier si au moins un compte logger
            	//getMyPlaylists()
            }
        });
		this.getChildren().add(myPlaylistsButton);
	}

}
