package ru.danila.cockroaches;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class MyGdxGame extends ApplicationAdapter {
	public static final int SCR_WIDTH = 1280, SCR_HEIGHT = 720;
	public static final int GAMESTATE_PLAY = 0;
	public static final int GAMESTATE_CHEESE_EATEN = 1;
	public static final int GAMESTATE_WAIT_RESTART = 2;

	public static int gameState = GAMESTATE_PLAY;

	SpriteBatch batch;
	OrthographicCamera camera;
	Vector3 touchPos;
	BitmapFont font;

	Texture imgCockroach;
	Texture imgCheese;

	Cheese cheese;
	Array<Cockroach> cockroaches = new Array<>();

	long timeSpawnEnemy = 1000;
	long timeLastSpawnEnemy;

	int score;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
		touchPos = new Vector3();

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("dejavu.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.color = new Color(0, 0, 0, 1); // rgba от 0 до 1
		parameter.size = 36;
		parameter.characters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
		font = generator.generateFont(parameter);
		generator.dispose();

		imgCockroach = new Texture("cockroach.png");
		imgCheese = new Texture("cheese.png");

		cheese = new Cheese();
	}

	@Override
	public void render () {
		// обработка нажатий
		if(Gdx.input.justTouched()){
			if(gameState == GAMESTATE_PLAY) {
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touchPos);
				for (int i = 0; i < cockroaches.size; i++) {
					float width = cockroaches.get(i).width;
					float height = cockroaches.get(i).height;
					if (cockroaches.get(i).hit(touchPos.x + width/2, touchPos.y + height/2)) {
						cockroaches.get(i).isAlive = false;
						score++;
					}
				}
			}
			if(gameState == GAMESTATE_WAIT_RESTART) {
				gameState = GAMESTATE_PLAY;
				for(int i = cockroaches.size-1; i>=0; i--) cockroaches.removeIndex(i);
				score = 0;
				cheese = new Cheese();
			}
		}

		// игровые события
		if (gameState == GAMESTATE_PLAY) {
			if (TimeUtils.millis() > timeLastSpawnEnemy+timeSpawnEnemy) {
				cockroaches.add(new Cockroach());
				timeLastSpawnEnemy = TimeUtils.millis();
			}

			for (int i = cockroaches.size - 1; i >= 0; i--) {
				cockroaches.get(i).move();
				if (!cockroaches.get(i).isAlive) cockroaches.removeIndex(i);
			}

			for (int i = 0; i < cockroaches.size; i++) {
				if (cheese.overlap(cockroaches.get(i))) {
					gameState = GAMESTATE_CHEESE_EATEN;
				}
			}
		}

		if(gameState == GAMESTATE_CHEESE_EATEN){
			gameState = GAMESTATE_WAIT_RESTART;
			cheese.isAlive = false;
		}

		// отрисовка всего
		Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		for(int i = 0; i< cockroaches.size; i++)
			/*
			batch.draw(imgCockroach, cockroaches.get(i).x - cockroaches.get(i).width/2,
					cockroaches.get(i).y - cockroaches.get(i).height/2,
					cockroaches.get(i).width, cockroaches.get(i).height);*/
			batch.draw(imgCockroach, cockroaches.get(i).x - cockroaches.get(i).width/2,
					   cockroaches.get(i).y - cockroaches.get(i).height/2,
					   cockroaches.get(i).width/2, cockroaches.get(i).height/2,
				       cockroaches.get(i).width, cockroaches.get(i).height,
		               1, 1, cockroaches.get(i).rotate, 0, 0,
		               imgCockroach.getWidth(), imgCockroach.getHeight(),
		               false, false);


		if(cheese.isAlive)
			batch.draw(imgCheese, cheese.x - cheese.width/2, cheese.y - cheese.height/2,
				cheese.width, cheese.height);

		font.draw(batch, "SCORE: " + score, 10, SCR_HEIGHT-10);
		if (gameState == GAMESTATE_WAIT_RESTART)
			font.draw(batch,"GAME OVER", 0, SCR_HEIGHT/2, SCR_WIDTH, Align.center, false);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

		imgCockroach.dispose();
		imgCheese.dispose();
	}
}

