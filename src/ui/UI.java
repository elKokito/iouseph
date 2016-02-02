package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import modele.DeezerClient;
import modele.Iapi;
import modele.SoundcloudClient;
import modele.SpotifyClient;

public class UI extends Application {
	
	private double UI_HEIGHT = 500;
	private double UI_WIDHT = 600;

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
