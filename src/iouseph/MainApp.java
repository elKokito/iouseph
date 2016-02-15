package iouseph;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

	private MainController controller;

    @Override
    public void start(Stage mainStage) {
       controller = new MainController(mainStage);
    }

	public static void main(String[] args) {
		launch(args);
	}

	/**
     * Constructor
     */
    public MainApp() {
    }

}
