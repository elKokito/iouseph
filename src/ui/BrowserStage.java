package ui;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class BrowserStage extends Stage{
	    
	private WebView webView;

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
