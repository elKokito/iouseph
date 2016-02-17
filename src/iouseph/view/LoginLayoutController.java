package iouseph.view;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class LoginLayoutController {
	@FXML
    private WebView loginView;
	@SuppressWarnings("unused")
	private Stage loginStage;

	/**
	 * execute la lecture
	 *
	 * @param url lien vers la chanson dans le service de streaming
	 */
	public void loadTrack(String url){
		loginView.getEngine().load(url);
	}

	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	//TODO lancer le serveur
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }
}
