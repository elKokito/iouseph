package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.WindowEvent;

public class LoginBox extends HBox{

	private BrowserStage browserStage;
	private Button loginButton;
	private ImageView imageView;
	private Image image;
	private String url = "www.google.com";

	/**
	 * cree un bouton de connexion
	 * 
	 * @param imagePath	lien vers le logo du service
	 * @param title	nom du service
	 * @param url	lien de connexion
	 */
	public LoginBox(String imagePath, final String title, String url) {
		super();
		
		this.url = url;
		imageView = new ImageView();
        image = new Image(imagePath);
        imageView.setImage(image);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        this.getChildren().add(imageView);
        
        loginButton = new Button("Login");
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			 
            /**
             * cree la fenetre de navigation BrowserStage
             * 
             * @param event	clic sur le bouton
             * @see BrowserStage
             */
            @Override
            public void handle(ActionEvent event) {
            	if (browserStage == null)
            		browserStage = new BrowserStage(title, LoginBox.this.url);  
            	notifyMainBox();
            	
            	
            	browserStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					/**
					 * evite qu'un meme lien de connexion soit ouvert deux fois
					 */
					@Override
					public void handle(WindowEvent arg0) {
						browserStage = null;
					}
				});
            }
        });
		this.getChildren().add(loginButton);
	}
	
	public void notifyMainBox()
	{
    	((MainBox)this.getParent().getParent().getParent()).setSpotifyWaitStatus();
	}

}
