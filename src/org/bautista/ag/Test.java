package org.bautista.ag;

import java.awt.Dimension;

import org.bautista.ag.api.Background;
import org.bautista.ag.api.Environment;
import org.bautista.ag.api.locatable.Position;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Test extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Game Frame");
		Group root = new Group();
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		
		Background background = new Background(new Dimension(800,800));
		Environment env = new Environment(background);
		env.initialize();
		root.getChildren().add(background);
		
		LLama spr = new LLama(new Image(getClass().getResourceAsStream("picture.jpg")),new Position(1,1));
		env.add(spr);
		
		env.remove(spr);
		
		primaryStage.show();
	}

}
