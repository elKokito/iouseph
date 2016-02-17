package iouseph;

import java.io.IOException;

import iouseph.model.Playlist;
import iouseph.model.Track;
import iouseph.view.LoginLayoutController;
import iouseph.view.MainLayoutController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

	private Stage mainStage;
    private BorderPane rootLayout;
    private ObservableList<Track> tracks = FXCollections.observableArrayList();
    private ObservableList<Playlist> playlists = FXCollections.observableArrayList();

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainLayout() {
        try {
            // Load main layout.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("view/MainLayout.fxml"));
            VBox mainLayout = (VBox) loader.load();

            // Set main layout into the center of root layout.
            rootLayout.setCenter(mainLayout);

            // Give the controller access to the main app.
            MainLayoutController controller = loader.getController();
            controller.setMainController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getMainStage() {
        return mainStage;
    }

    /**
     * Returns the data as an observable list of Tracks.
     * @return
     */
    public ObservableList<Track> getTracks() {
        return tracks;
    }

    /**
     * Returns the data as an observable list of Playlists.
     * @return
     */
    public ObservableList<Playlist> getPlaylists() {
        return playlists;
    }

	public MainController(){

	}

	public MainController(Stage mainStage){
		this();
		this.mainStage = mainStage;
        this.mainStage.setTitle("Iouseph");
        this.mainStage.getIcons().add(new Image("file:res/Iouseph-icon.png"));

        initRootLayout();

        showMainLayout();
	}

    public boolean showLoginLayout(String url) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource("view/LoginLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.getIcons().add(new Image("file:res/Iouseph-icon.png"));
            loginStage.initModality(Modality.WINDOW_MODAL);
            loginStage.initOwner(mainStage);
            Scene scene = new Scene(page);
            loginStage.setScene(scene);

            LoginLayoutController controller = loader.getController();
            controller.setLoginStage(loginStage);

            // Show the dialog and wait until the user closes it
            loginStage.show();
            controller.loadTrack(url);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
