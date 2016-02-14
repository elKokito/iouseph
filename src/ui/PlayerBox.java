package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import modele.Track;

public class PlayerBox extends VBox{
	
	private WebView webView;
	private ImageView imageView;
	private Image image;
	private Text trackTitle, artistName, albumTitle;

	/**
	 * cree la section Player de la vue
	 * 
	 * @param uI_HEIGHT
	 *            pour la longueur de la fenetre
	 * @param uI_WIDTH
	 *            pour la largeur de la fenetre
	 * 
	 * @see VBox,
	 */
	public PlayerBox(double uI_HEIGHT, double uI_WIDTH) {
		super();
		this.setAlignment(Pos.TOP_LEFT);
		this.setPadding(new Insets(10,10,10,10));
		this.setMaxHeight(uI_HEIGHT);
		this.setMaxWidth(uI_WIDTH*2/5);

		Text playerText = new Text("Player");
		playerText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		this.getChildren().add(playerText);
		
		imageView = new ImageView();
        image = new Image("https://pbs.twimg.com/profile_images/426420605945004032/K85ZWV2F_400x400.png");
        imageView.setImage(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        
        trackTitle = new Text("Track");
        artistName = new Text("Artist");
        albumTitle = new Text("Album");      

		VBox trackInfo = new VBox();
		trackInfo.setAlignment(Pos.CENTER);
		trackInfo.setPadding(new Insets(10,10,10,10));
		trackInfo.setMinHeight(uI_HEIGHT/2);
		
        trackInfo.getChildren().add(imageView);
        trackInfo.getChildren().add(trackTitle);
        trackInfo.getChildren().add(artistName);
        trackInfo.getChildren().add(albumTitle);
		this.getChildren().add(trackInfo);
		
        webView = new WebView();
        webView.setMaxHeight(uI_HEIGHT/4);
        this.getChildren().add(webView);        
	}
	
	/**
	 * execute la lecture
	 * 
	 * @param url lien vers la chanson dans le service de streaming
	 */
	public void loadTrack(String url){
		webView.getEngine().load(url);
	}

	
	/**
	 * rafrechit les donnees affiche sur la vue PlayerBox
	 * 
	 * @param track le Track dont les donnees vont etre affichees
	 * @see PlayerBox#loadTrack(String)
	 */
	public void refresh(Track track) {
		trackTitle.setText(track.getTitle());
		artistName.setText(track.getArtist());
		albumTitle.setText(track.getAlbum());
		image = new Image(track.getImage_());
		imageView.setImage(image);
		loadTrack(track.getExternalUrl());
	}

}
