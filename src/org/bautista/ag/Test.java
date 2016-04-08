package org.bautista.ag;

import org.bautista.ag.api.Background;
import org.bautista.ag.api.Environment;
import org.bautista.ag.api.locatable.Dimension;
import org.bautista.ag.api.locatable.Position;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Test extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Background background = new Background(new Dimension(800, 800));
		Environment env = new Environment(background);

		LLama spr = new LLama(new Image(getClass().getResourceAsStream("picture.png")),
				new Position(1, 1));
		spr.setXVelocity(1);
		spr.setYVelocity(0);
		env.add(spr);
		//env.getGravity().setEnabled(false);

		env.initialize();
	}

}
