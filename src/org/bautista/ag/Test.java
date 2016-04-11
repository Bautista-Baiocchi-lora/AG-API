package org.bautista.ag;

import org.bautista.ag.api.Background;
import org.bautista.ag.api.Environment;
import org.bautista.ag.api.locatable.Dimension;
import org.bautista.ag.api.locatable.Position;
import org.bautista.ag.api.physics.Gravity;
import org.bautista.ag.api.physics.Ricochet;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Test extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Background background = new Background(new Group(), new Dimension(600, 600));
		Environment env = new Environment.Builder(background, new Gravity()).infinite(true)
				.ricochet(new Ricochet(Ricochet.NONE, true)).build();
		env.initialize();

		LLama spr = new LLama(new Image(getClass().getResourceAsStream("picture.png")), new Position(100, 100));
		spr.setXVelocity(3);
		spr.setYVelocity(1);
		env.add(spr);
	}

}
