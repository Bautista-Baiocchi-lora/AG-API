package org.bautista.ag.api;

import org.bautista.ag.api.objects.GameObject;

import javafx.animation.AnimationTimer;

public class Environment{

	private static Environment instance;
	private final Background background;

	public Environment(Background background) {
		instance = this;
		this.background=background;
		initialize();
	}
	
	public void initialize(){
		new AnimationTimer(){

			@Override
			public void handle(long now) {
				System.out.println("here");				
			}
			
		}.start();
	}
	
	public void add(GameObject gameObject){
		background.add(gameObject);
	}
	
	public void remove(GameObject gameObject){
		background.remove(gameObject);
	}

	public static Environment getInstance() {
		return instance;
	}

}
