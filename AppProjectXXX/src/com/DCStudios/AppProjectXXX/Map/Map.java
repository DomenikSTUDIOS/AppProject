package com.DCStudios.AppProjectXXX.Map;

import java.util.Iterator;

import box2dLight.RayHandler;

import com.DCStudios.AppProjectXXX.Background.BackGround;
import com.DCStudios.AppProjectXXX.Datastructures.Measure;
import com.DCStudios.AppProjectXXX.Entity.Entity;
import com.DCStudios.AppProjectXXX.Math.MyMath;
import com.DCStudios.AppProjectXXX.Rendering.Drawable;
import com.DCStudios.AppProjectXXX.Rendering.DrawableCollection;
import com.DCStudios.AppProjectXXX.Rendering.LightRender;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Map implements MapInterface, DrawableCollection {
	
	protected Screen screen;
	
	protected World world;
	
	protected Array<Entity> entitys = new Array<Entity>();
	protected BackGround background;
	
	protected Measure measure;	

	public Map(Screen screen) {
		this.screen = screen;
		world = new World(new Vector2(0, -9.4f), false);	
		LightRender.initLightRender(world);
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
	
	protected void addGround(float x, float y, float width, float height) {
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(x, y);

		Body groundBody = world.createBody(groundBodyDef);

		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(width, height);
		groundBody.createFixture(groundBox, 0.0f);
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
	public Array<Drawable> getDrawablesInView(OrthographicCamera camera) {
		Vector2 position = new Vector2(camera.position.x, camera.position.y);
		Measure measure = new Measure(camera.viewportWidth, camera.viewportHeight);
		
		Array<Drawable> temp = new Array<Drawable>();
		Iterator<Entity> eIter = entitys.iterator();
		Entity entity;
		
		while (eIter.hasNext()) {
			entity = eIter.next();
			if (MyMath.fitsIn(position, measure, entity.getPosition(), entity.getMeasure())) {
				temp.add(entity);
			}
		}
		
		return temp;
	}
	
	public World getWorld() {
		return world;
	}
	
	public Screen getScreen() {
		return screen;
	}
	
	public Measure getMeasure() {
		return measure;
	}

}
