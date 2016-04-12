package org.bautista.ag.api.locatable.collisions;

import org.bautista.ag.api.objects.GameObject;

public class Collision {

	private CollisionFlag flag;
	private GameObject object;

	public Collision(GameObject object, CollisionFlag flag) {
		this.object = object;
		this.flag = flag;
	}

	public CollisionFlag getFlag() {
		return flag;
	}

	public GameObject getObject() {
		return object;
	}

}
