package ui;

import java.util.Vector;

import iouseph.Parser;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import modele.Track;

import org.json.JSONObject;

public class TracksBox extends VBox{
	
	ListView<VBox> tracksItem;
	VBox sp;

	public TracksBox(double uI_HEIGHT, double uI_WIDTH) {
		super();
		this.setPadding(new Insets(10,10,10,10));
		this.setMaxHeight(uI_HEIGHT);
		this.setMaxWidth(uI_WIDTH/5);

		Text tracksText = new Text("Tracks");
		tracksText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		this.getChildren().add(tracksText);
		
		tracksItem = new ListView<>();
		sp = new VBox();
		/*sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);*/
		//sp.getChildren().add(tracksItem);
		this.getChildren().add(sp);
	}

	public void setList(JSONObject json){
		Vector<Track> tracks = Parser.tracksParse(json);

		for ( int i = 0; i < tracks.size(); i++)
			sp.getChildren().add(createTrackItem(tracks.get(i)));

	}
	
	private VBox createTrackItem(final Track track){
		VBox box = new VBox();
		Text title = new Text(track.getTitle()); 
		Text artist = new Text(track.getArtist()); 
		Text album = new Text(track.getAlbum());
		box.getChildren().add(title);
		box.getChildren().add(artist);
		box.getChildren().add(album);
		box.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				((MainBox)TracksBox.this.getParent()).setPlayer(track);
			}
		});
		return box;
	}
	
	
}
