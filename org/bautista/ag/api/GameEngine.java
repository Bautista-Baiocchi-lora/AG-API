package org.bautista.ag.api;

import org.bautista.ag.api.background.Background;
import org.bautista.ag.api.objects.GameObject;

public class GameEngine {
	private static GameEngine instance;
	private Environment environment;

	private GameEngine() {

	}

	public void createEnvironment(Environment environment) {
		this.environment = environment;
		this.environment.initialize();
	}

	public void add(GameObject gameObject) {
		environment.add(gameObject);
	}

	public void remove(GameObject gameObject) {
		environment.remove(gameObject);
	}

	public Environment getEnvironment() {
		return environment;
	}

	public Background getBackground() {
		return environment.getBackground();
	}

	public static GameEngine getInstance() {
		return instance == null ? instance = new GameEngine() : instance;
	}

}
