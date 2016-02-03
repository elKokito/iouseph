package ui;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class BrowserStage extends Stage{
	    
	private WebView webView;

	/**
	 * ouvre une fenetre ayant comme titre title et contenant une {@link WebView} qui est dirigee vers l'url passe en parametre  
	 * 
	 * @param title titre de la fenetre
	 * @param url	lien de navigation
	 * @see Stage
	 */
	public BrowserStage(String title, String url){
		super();
				
		StackPane root = new StackPane();
		webView = new WebView();
        root.getChildren().add(webView);      

        Scene scene = new Scene(root, 300, 250);

        this.setTitle(title);
        this.setScene(scene);
        webView.getEngine().load(url); 
        this.show();
        
	}
	

}
