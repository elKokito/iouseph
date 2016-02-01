package ui;

import org.json.JSONObject;

import iouseph.DeezerClient;
import iouseph.Iapi;
import iouseph.SoundcloudClient;
import iouseph.SpotifyClient;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UI extends Application {
	
	private double UI_HEIGHT = 500;
	private double UI_WIDHT = 600;
	private JSONObject json;

	@Override
	public void start(Stage stage) throws Exception {	
		Iapi deezer = new DeezerClient();
		Iapi soundCloud = new SoundcloudClient();
		Iapi spotify = new SpotifyClient();
        
        StackPane root = new StackPane();
        MainBox grid = new MainBox(UI_HEIGHT, UI_WIDHT, deezer);
        root.getChildren().add(grid);

        Scene scene = new Scene(root, UI_WIDHT, UI_HEIGHT);

        stage.setTitle("Iouseph");
        stage.setScene(scene);
        stage.show();       

	}

	public static void main(String[] args) {
		launch(args);

	}

}
