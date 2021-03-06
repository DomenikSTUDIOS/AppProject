package com.DCStudios.AppProjectXXX.Rendering;

import java.util.Iterator;

import com.DCStudios.AppProjectXXX.Datastructures.Measure;
import com.DCStudios.AppProjectXXX.Entity.Entity;
import com.DCStudios.AppProjectXXX.Map.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Render {
	
	private Map map;
	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	public boolean renderLight = true;
	public boolean renderBackground = false;
	public boolean renderPhysic = false;
	
	private Measure measure;
	private float zoom = 8f;
	
	public Render(Map map) {
		this.map = map;
		
		measure = new Measure(Gdx.graphics.getWidth() / zoom,
				Gdx.graphics.getHeight() / zoom);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, measure.width, measure.height);
		camera.update();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
	}
	
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		
		map.step();
		batch.begin();
		renderBackground();
		batch.end();
		
		renderPhysic();
		
		batch.begin();
		renderGraphics();
		batch.end();
		renderLight();
	}
	
	private void renderLight() {
		if (renderLight) {
			map.renderLight(camera);
		}
	}
	private void renderBackground() {
		if (renderBackground) {
			map.getBackground().draw(batch);
		}
	}
	
	private void renderPhysic() {
		if (renderPhysic) {
			map.renderPhysics(camera);
		}
	}
	
	private void renderGraphics() {
		Iterator<Entity> graphics = map.getDrawablesInView(camera).iterator();
		Drawable graphic;
		
		while (graphics.hasNext()) {
			graphic = graphics.next();
			graphic.draw(batch);
		}
	}
	
	public void dispose() {
		batch.dispose();
	}
}
