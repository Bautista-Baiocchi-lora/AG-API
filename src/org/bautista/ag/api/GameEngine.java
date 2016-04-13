package org.bautista.ag.api;

import org.bautista.ag.api.background.Background;
import org.bautista.ag.api.objects.GameObject;

public class GameEngine {
	private static GameEngine instance;

	public static GameEngine getInstance() {
		return instance == null ? instance = new GameEngine() : instance;
	}

	private Environment environment;

	private GameEngine() {

	}

	public void add(final GameObject gameObject) {
		environment.add(gameObject);
	}

	public void createEnvironment(final Environment environment) {
		this.environment = environment;
		this.environment.initialize();
	}

	public Background getBackground() {
		return environment.getBackground();
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void remove(final GameObject gameObject) {
		environment.remove(gameObject);
	}

}
