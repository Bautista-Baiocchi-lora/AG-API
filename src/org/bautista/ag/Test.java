package org.bautista.ag;

import org.bautista.ag.api.Environment;
import org.bautista.ag.api.GameEngine;
import org.bautista.ag.api.background.Background;
import org.bautista.ag.api.background.scroll.ScrollType;
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
		GameEngine.getInstance().createEnvironment(new Environment.Builder(background, new Gravity(), ScrollType.RIGHT)
				.ricochet(new Ricochet(Ricochet.SLIGHT_DECREASE)).build());

		Ball ball = new Ball(new Image(getClass().getResourceAsStream("picture.png")), new Position(100, 100));
		ball.setXVelocity(-3);
		ball.setYVelocity(10);
		GameEngine.getInstance().add(ball);
	}

}
