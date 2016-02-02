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
	/*final ScrollBar sc = new ScrollBar();
    final Image[] images = new Image[5];
    final ImageView[] pics = new ImageView[5];
    final VBox vb = new VBox();
    DropShadow shadow = new DropShadow();
 
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 180, 500);
        scene.setFill(Color.BLACK);
        stage.setScene(scene);
        stage.setTitle("Scrollbar");
        root.getChildren().addAll(vb, sc);
 
       
 
        vb.setLayoutX(5);
        vb.setSpacing(10);
 
        sc.setLayoutX(scene.getWidth()-sc.getWidth());
        sc.setMin(0);
        sc.setOrientation(Orientation.VERTICAL);
        sc.setPrefHeight(500);
        sc.setMax(1000);
 
        for (int i = 0; i < 5; i++) {
            final Image image = images[i] =
                new Image("https://a-v2.sndcdn.com/assets/images/sc-icons/fluid-b4e7a64b.png"
                );
            final ImageView pic = pics[i] =
                new ImageView(images[i]);
            vb.getChildren().add(pics[i]);
        }
 
        sc.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    vb.setLayoutY(-new_val.doubleValue());
            }
        });
 
        stage.show();
    }*/

	public static void main(String[] args) {
		launch(args);

	}

}
