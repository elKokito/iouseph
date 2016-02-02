package ui;

import iouseph.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
import modele.Track;

import org.json.JSONObject;

public class TracksBox extends VBox{
	
	private ScrollBar sc;
	private ScrollPane sp;
	private VBox box;
	private ObservableList<VBox> items;

	public TracksBox(double uI_HEIGHT, double uI_WIDTH) {
		super();
		this.setPadding(new Insets(10,10,10,10));
		this.setMaxHeight(uI_HEIGHT);
		this.setMaxWidth(uI_WIDTH/5);

		Text tracksText = new Text("Tracks");
		tracksText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
				
		sc = new ScrollBar();
		sc.setLayoutX(this.getWidth()-sc.getWidth());
        sc.setMin(0);
        sc.setOrientation(Orientation.VERTICAL);
        sc.setMaxHeight(uI_HEIGHT);
        sc.setMax(uI_HEIGHT*1.5);
		
		box = new VBox();
		box.setLayoutX(5);
        box.setSpacing(10);
        box.setMaxHeight(uI_HEIGHT-20);
        box.setMaxWidth(this.getMaxWidth()-(sc.getWidth()*2));
		
		
		sp = new ScrollPane();
		sp.setMaxSize(uI_HEIGHT-20, uI_WIDTH-20);
		sp.setContent(box);
		this.getChildren().addAll(tracksText, sp);
		
		sc.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    box.setLayoutY(-new_val.doubleValue());
            }
        });
	}

	public void setList(JSONObject json){
		refresh(Parser.tracksParse(json));		
	}
	
	public void refresh(List<Track> tracks){
		List<VBox> list = new ArrayList<>();

		for ( int i = 0; i < tracks.size(); i++)
			list.add(createTrackItem(tracks.get(i)));
		items = FXCollections.observableArrayList(list);
		box.getChildren().clear();
		box.getChildren().addAll(items);
		sc.setLayoutX(this.getWidth()-sc.getWidth());
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
