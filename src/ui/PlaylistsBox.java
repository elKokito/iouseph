package ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import modele.Parser;
import modele.Playlist;

public class PlaylistsBox extends VBox {

	private ScrollPane sp;
	private VBox box;
	private ObservableList<VBox> items;

	/**
	 * cree la section Playlists de la vue
	 * 
	 * @param uI_HEIGHT
	 *            pour la longueur de la fenetre
	 * @param uI_WIDTH
	 *            pour la largeur de la fenetre
	 * 
	 * @see VBox,
	 */
	public PlaylistsBox(double uI_HEIGHT, double uI_WIDTH) {
		super();
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setMaxHeight(uI_HEIGHT);
		this.setMaxWidth(uI_WIDTH / 5);

		Text playlistsText = new Text("Playlists");
		playlistsText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));

		box = new VBox();
		box.setLayoutX(5);
		box.setSpacing(10);
		box.setMaxHeight(uI_HEIGHT - 20);

		sp = new ScrollPane();
		sp.setMaxSize(uI_HEIGHT - 20, uI_WIDTH - 20);
		sp.setContent(box);
		this.getChildren().addAll(playlistsText, sp);

	}

	/**
	 * actualise la liste des playlists dans la vue
	 * 
	 * @param json
	 */
	public void setList(JSONObject json) {
		List<Playlist> playlists = Parser.playlistsParse(json);
		ArrayList<VBox> list = new ArrayList<>();

		for (int i = 0; i < playlists.size(); i++)
			list.add(createPlaylistItem(playlists.get(i)));
		items = FXCollections.observableArrayList(list);

		box.getChildren().clear();
		box.getChildren().addAll(items);
	}

	/**
	 * remplit un item de la list, y ajout le EventHandler pour actualiser le
	 * TracksBox
	 * 
	 * @param playlist
	 * @return VBox
	 * @see VBox
	 */
	private VBox createPlaylistItem(final Playlist playlist) {
		VBox box = new VBox();
		Text title = new Text(playlist.getTitle());
		Text artist = new Text(playlist.getOwner());
		box.getChildren().add(title);
		box.getChildren().add(artist);
		box.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				((MainBox) PlaylistsBox.this.getParent())
						.setTrackList(playlist);
			}
		});
		return box;
	}
}
