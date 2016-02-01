package ui;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import org.json.JSONArray;
import org.json.JSONObject;

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

	public void setList(JSONObject jsonArray){
		/*Iterator<String> i = jsonArray.keys();
		while (i.hasNext()) {
			String s = i.next();
			System.out.println(s + " : " + jsonArray.get(s));
		}*/
		
		refresh(jsonArray);
	}
	
	private void refresh(JSONObject json){
		System.out.println(((JSONObject)json.getJSONArray("data").get(0)).getString("title"));
		System.out.println(((JSONObject)json.getJSONArray("data").get(0)).getString("preview"));
		System.out.println(((JSONObject)((JSONObject)json.getJSONArray("data").get(0)).get("artist")).getString("name"));
		System.out.println(((JSONObject)((JSONObject)json.getJSONArray("data").get(0)).get("album")).getString("title"));
		System.out.println(((JSONObject)((JSONObject)json.getJSONArray("data").get(0)).get("album")).getString("cover_big"));
	}
	
}
