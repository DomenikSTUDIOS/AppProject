package com.DCStudios.AppProjectXXX.Map;

import java.util.Iterator;

import box2dLight.RayHandler;

import com.DCStudios.AppProjectXXX.Background.BackGround;
import com.DCStudios.AppProjectXXX.Datastructures.Measure;
import com.DCStudios.AppProjectXXX.Entity.Entity;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Map implements MapInterface {
	
	protected Screen screen;
	
	protected World world;
	protected Box2DDebugRenderer box2DRenderer;
	protected RayHandler rayHandler;
	
	protected Array<Entity> entitys = new Array<Entity>();
	
	protected Measure measure;	
	protected BackGround background;
	
	public Map(Screen screen) {
		this.screen = screen;
		world = new World(new Vector2(0, -10f), false);
		box2DRenderer = new Box2DDebugRenderer();
		rayHandler = new RayHandler(world);		
	}

	@Override
	public void renderLight(OrthographicCamera camera) {
		rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.updateAndRender();
	}

	@Override
	public void renderPhysics(OrthographicCamera camera) {
		box2DRenderer.render(world, camera.combined);
	}

	@Override
	public void step() {
		world.step(1/60f, 6, 2);
		update();
	}
	
	protected void addEntity(Entity entity) {
		entitys.add(entity);
		entity.setWorld(world);
	}

	@Override
	public void update() {
		Iterator<Entity> eIter = entitys.iterator();
		while (eIter.hasNext()) {
			Entity updateEntity = eIter.next();
			updateEntity.update();
		}	
	}

	@Override
	public void dispose() {
		background.dispose();
		world.dispose();
		box2DRenderer.dispose();
	}

	@Override
	public BackGround getBackground() {
		return background;
	}

	@Override
	public Array<Entity> getDrawables() {
		return entitys;
	}
	
	@Override
	public Array<Entity> getDrawablesInView(OrthographicCamera camera) {
		Vector2 position = new Vector2(camera.position.x, camera.position.y);
		Measure measure = new Measure(camera.viewportWidth, camera.viewportHeight);
		
		float borderLeft = position.x - measure.width / 2 - 5f;
		float borderRight = position.x + measure.width /2 + 5f;
		float borderTop = position.y + measure.height / 2 + 5f;
		float borderBottom = position.y - measure.height / 2 - 5f;
		
		Array<Entity> temp = new Array<Entity>();
		Iterator<Entity> eIter = entitys.iterator();
		Entity entity;
		
		while (eIter.hasNext()) {
			entity = eIter.next();
			if (entity.getPosition().x + entity.getMeasure().width / 2 >= borderLeft &&
				entity.getPosition().x - entity.getMeasure().width / 2 <= borderRight &&
				entity.getPosition().y - entity.getMeasure().height / 2 <= borderTop &&
				entity.getPosition().y + entity.getMeasure().height / 2 >= borderBottom) {
				temp.add(entity);
			}
		}
		
		return temp;
	}
	
	public Screen getScreen() {
		return screen;
	}
	
	public Measure getMeasure() {
		return measure;
	}

}
