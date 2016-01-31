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
		//loginButton.setMinSize(arg0, arg1);
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			 
            @Override
            public void handle(ActionEvent event) {
            	if (browserStage == null)
            		browserStage = new BrowserStage(title, LoginBox.this.url);  
            	browserStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					
					@Override
					public void handle(WindowEvent arg0) {
						browserStage = null;
					}
				});
            }
        });
		this.getChildren().add(loginButton);
	}

}
