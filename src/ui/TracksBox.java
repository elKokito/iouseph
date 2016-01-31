package ui;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TracksBox extends VBox{

	public TracksBox(double uI_HEIGHT, double uI_WIDTH) {
		super();
		this.setPadding(new Insets(10,10,10,10));
		this.setMaxHeight(uI_HEIGHT);
		this.setMaxWidth(uI_WIDTH/5);

		Text tracksText = new Text("Tracks");
		tracksText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		this.getChildren().add(tracksText);
		
		ScrollPane sp = new ScrollPane();
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		this.getChildren().add(sp);
	}

}
