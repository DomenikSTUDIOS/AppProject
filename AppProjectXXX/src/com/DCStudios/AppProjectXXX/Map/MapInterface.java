package com.DCStudios.AppProjectXXX.Map;

import com.DCStudios.AppProjectXXX.Background.BackGround;
import com.DCStudios.AppProjectXXX.Entity.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;

public interface MapInterface {
	void renderLight(OrthographicCamera camera);
	void renderPhysics(OrthographicCamera camera);
	void step();
	void update();
	void dispose();
	BackGround getBackground();
	Array<Entity> getDrawables();
	Array<Entity> getDrawablesInView(OrthographicCamera camera);
}
