package iouseph;

import java.io.IOException;

import iouseph.model.Playlist;
import iouseph.model.Track;
import iouseph.view.LoginLayoutController;
import iouseph.view.MainLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage mainStage;
    private BorderPane rootLayout;
    private ObservableList<Track> tracks = FXCollections.observableArrayList();
    private ObservableList<Playlist> playlists = FXCollections.observableArrayList();

    @Override
    public void start(Stage mainStage) {
        this.mainStage = mainStage;
        this.mainStage.setTitle("Iouseph");

        initRootLayout();

        showMainLayout();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showMainLayout() {
        try {
            // Load main layout.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainLayout.fxml"));
            VBox mainLayout = (VBox) loader.load();

            // Set main layout into the center of root layout.
            rootLayout.setCenter(mainLayout);

            // Give the controller access to the main app.
            MainLayoutController controller = loader.getController();
            controller.setMainApp(this);
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

	public static void main(String[] args) {
		launch(args);
	}

	/**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
    	Track t = new Track();
    	t.setTitle("cdb");
    	t.setArtist("ikhbcd");
    	t.setAlbum("lkjbslpqnih");
    	t.setImage("http://fabrice-bouye.developpez.com/tutoriels/javafx/gui-interface-utilisateur-vue-controleur-fxml-javafx/images/image6.png");
    	tracks.add(t);
    	t = new Track();
    	t.setTitle("cddbgdghhnb");
    	t.setArtist("igfbd hg khbcd");
    	t.setAlbum("lkjbslnhfghgfpqnih");
    	t.setImage("http://fabrice-bouye.developpez.com/tutoriels/javafx/gui-interface-utilisateur-vue-controleur-fxml-javafx/images/image6.png");
    	tracks.add(t);
    	t = new Track();
    	t.setTitle("cdb f gnh");
    	t.setArtist("ikhbcb c fg d");
    	t.setAlbum("lkjbslpfh mfyte hggdfnmjdytvf erthtrh ntrhf ntrddtqnih");
    	t.setImage("http://fabrice-bouye.developpez.com/tutoriels/javafx/gui-interface-utilisateur-vue-controleur-fxml-javafx/images/image6.png");
    	tracks.add(t);
    	Playlist p = new Playlist();
    	p.setTitle("plcdjhvv");
    	p.addTrack(t);
    	playlists.add(p);
    }

    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showLoginLayout(String url) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LoginLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.initModality(Modality.WINDOW_MODAL);
            loginStage.initOwner(mainStage);
            Scene scene = new Scene(page);
            loginStage.setScene(scene);

            LoginLayoutController controller = loader.getController();
            controller.setLoginStage(loginStage);

            // Show the dialog and wait until the user closes it
            loginStage.showAndWait();
            //controller.loadTrack(url);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
