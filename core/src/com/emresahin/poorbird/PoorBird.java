package com.emresahin.poorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class PoorBird extends ApplicationAdapter {
	SpriteBatch batch;
    Texture bg1,bg2,bg3,bg4,foxframe1,enemy1,enemy2,enemy3,otherEnemy1,otherEnemy2,otherEnemy3;
	float foxX = 0;
	float foxY = 0;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.2f;
	Random random;
	Circle foxCircle;
	ShapeRenderer shapeRenderer;
	int score = 0;
	int scoredEnemy =0;
	//
	int otherScore = 0;
	int otherScoredEnemy = 0;
	BitmapFont font,gameover;
	//
	int numberOfEnemies = 4;
	float[] enemyX = new float[numberOfEnemies];
	int maxDistance = 0;
	float enemyVelocity = 2;

	float [] enemyOfset = new float[numberOfEnemies];
	float [] enemyOfset2 = new float[numberOfEnemies];
	float [] enemyOfset3 = new float[numberOfEnemies];
	Circle[] enemyCircle,enemyCircle2,enemyCircle3;
	Circle[] otherEnemyCircle,otherEnemyCircle2;

	//

	//
	int otherNumberOfEnemies = 2;
	float [] otherEnemyX = new float[otherNumberOfEnemies];
	int otherMaxDistance = 0;
	float otherEnemyVelocity = 2;

	float[] otherEnemyOfset = new float[otherNumberOfEnemies];
	float[] otherEnemyOfset2 = new float[otherNumberOfEnemies];
	//

	@Override
	public void create () {
		//scoreBoard
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(5);
		gameover = new BitmapFont();
		gameover.setColor(Color.WHITE);
		gameover.getData().setScale(10);

		random = new Random();
		maxDistance = Gdx.graphics.getWidth() / 3;
		otherMaxDistance = Gdx.graphics.getWidth() / 2;
		shapeRenderer = new ShapeRenderer();

		foxCircle = new Circle();
		enemyCircle = new Circle[numberOfEnemies];
		enemyCircle2 = new Circle[numberOfEnemies];
		enemyCircle3 = new Circle[numberOfEnemies];

		otherEnemyCircle = new Circle[otherNumberOfEnemies];
		otherEnemyCircle2 = new Circle[otherNumberOfEnemies];

		batch = new SpriteBatch();
		bg1 = new Texture("0.png");
		bg2 = new Texture("1.png");
		bg3 = new Texture("2.png");
		bg4 = new Texture("3.png");
		foxframe1 = new Texture("foxframe1.png");
		enemy1 = new Texture("ufoframe3.png");
		enemy2 = new Texture("ufoframe3.png");
		enemy3 = new Texture("ufoframe3.png");
		otherEnemy1 = new Texture("ssframe5.png");
		otherEnemy2 = new Texture("ssframe5.png");
		otherEnemy3 = new Texture("ssframe5.png");

		foxX = Gdx.graphics.getWidth() / 4 - foxframe1.getHeight() / 3;
		foxY = Gdx.graphics.getHeight() / 3;

		for (int i = 0; i<numberOfEnemies; i++){

			enemyOfset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOfset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOfset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyX[i] = Gdx.graphics.getWidth()	- enemy1.getWidth() / 2 + i * maxDistance;

			enemyCircle[i] = new Circle();
			enemyCircle2[i] = new Circle();
			enemyCircle3[i] = new Circle();

		}

		for (int i = 0; i < otherNumberOfEnemies; i++){

			otherEnemyOfset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			otherEnemyOfset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			otherEnemyCircle[i] = new Circle();
			otherEnemyCircle2[i] = new Circle();

			otherEnemyX[i] = Gdx.graphics.getWidth()	- otherEnemy1.getWidth() / 2 + i * otherMaxDistance;
		}

	}




	@Override
	public void render () {

		batch.begin();

		batch.draw(bg1, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(bg2, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(bg3, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(bg4, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1) {
			//score
			// I have tried so many way but i cant solve this issue, i found just one solution, the solution is adding another if statement for scoring the game

			if (otherEnemyX[otherScoredEnemy]  < Gdx.graphics.getWidth() / 4 - foxframe1.getHeight() / 3 )  {

				otherScore++;
				if (otherScoredEnemy < otherNumberOfEnemies - 1){
					otherScoredEnemy++;
				}else{
					otherScoredEnemy = 0;
				}
			}
			//
			if (enemyX[scoredEnemy]  < Gdx.graphics.getWidth() / 4 - foxframe1.getHeight() / 3 )  {

				 score++;
				if (scoredEnemy < numberOfEnemies - 1){
					scoredEnemy++;
				}else{
					scoredEnemy = 0;
				}
			}
			//
			if (Gdx.input.justTouched()) {
				velocity = -7;
			}
			for (int i = 0; i < otherNumberOfEnemies; i++) {
				if (otherEnemyX[i] < -otherEnemy1.getWidth()) {
					otherEnemyX[i] = otherEnemyX[i] + otherNumberOfEnemies * otherMaxDistance;

					otherEnemyOfset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					otherEnemyOfset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

				} else {

					otherEnemyX[i] = otherEnemyX[i] - otherEnemyVelocity;


				}

				otherEnemyX[i] = otherEnemyX[i] - otherEnemyVelocity;

				batch.draw(otherEnemy1, otherEnemyX[i], Gdx.graphics.getHeight() / 2 + otherEnemyOfset[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 8);
				batch.draw(otherEnemy2, otherEnemyX[i], Gdx.graphics.getHeight() / 2 + otherEnemyOfset2[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 8);

				otherEnemyCircle[i] = new Circle(otherEnemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + otherEnemyOfset[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 30);
				otherEnemyCircle[i] = new Circle(otherEnemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + otherEnemyOfset[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 30);
			}

			for (int i = 0; i < numberOfEnemies; i++) {
				if (enemyX[i] < -enemy1.getWidth()) {
					enemyX[i] = enemyX[i] + numberOfEnemies * maxDistance;

					enemyOfset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOfset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOfset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				}
				enemyX[i] = enemyX[i] - enemyVelocity;

				batch.draw(enemy1, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOfset[i], Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight() / 9);
				batch.draw(enemy2, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOfset2[i], Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight() / 9);
				batch.draw(enemy3, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOfset3[i], Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight() / 9);

				enemyCircle[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOfset[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 30);
				enemyCircle2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOfset2[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 30);
				enemyCircle3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOfset3[i] + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 30);


			}


			if (foxY > 0) {
				velocity = velocity + gravity;
				foxY = foxY - velocity;
			} else {
				gameState = 2;
			}
		} else if (gameState == 0) {
			gameover.draw(batch,"Tap To Start",700,Gdx.graphics.getHeight() /2);
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
			} else if (gameState == 2) {
				gameover.draw(batch,"Game Over!",700,Gdx.graphics.getHeight() /2);
				if (Gdx.input.justTouched()) {
					gameState = 1;

					foxY = Gdx.graphics.getHeight() / 3;

					for (int i = 0; i < numberOfEnemies; i++) {

						enemyOfset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
						enemyOfset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
						enemyOfset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

						enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth() / 2 + i * maxDistance;

						enemyCircle[i] = new Circle();
						enemyCircle2[i] = new Circle();
						enemyCircle3[i] = new Circle();

					}

					for (int i = 0; i < otherNumberOfEnemies; i++) {

						otherEnemyOfset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
						otherEnemyOfset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

						otherEnemyCircle[i] = new Circle();
						otherEnemyCircle2[i] = new Circle();

						otherEnemyX[i] = Gdx.graphics.getWidth() - otherEnemy1.getWidth() / 2 + i * otherMaxDistance;
					}
				}
				velocity = 0;
				scoredEnemy = 0;
				score = 0;
				otherScoredEnemy = 0;
				otherScore = 0;
			}


		batch.draw(foxframe1, foxX, foxY, Gdx.graphics.getWidth() / 13, Gdx.graphics.getHeight() / 7);
		font.draw(batch,String.valueOf(score + otherScore),50,100);

		batch.end();

		foxCircle.set(foxX + Gdx.graphics.getWidth() / 20, foxY + Gdx.graphics.getHeight() / 16, Gdx.graphics.getWidth() / 30);

		//
		//if you wanna see the hit circle open comment line under bellow this line
		//
		/*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.circle(foxCircle.x, foxCircle.y, foxCircle.radius);
		 */


		for (int i = 0; i < numberOfEnemies; i++) {
			//
			//if you wanna see the hit circle open comment line under bellow this line
			//
			/*shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 34, Gdx.graphics.getHeight() / 2 + enemyOfset[i] + Gdx.graphics.getHeight() /12, Gdx.graphics.getWidth() / 65);
			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 34, Gdx.graphics.getHeight() / 2 + enemyOfset2[i] + Gdx.graphics.getHeight() /12, Gdx.graphics.getWidth() / 65);
			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 34, Gdx.graphics.getHeight() / 2 + enemyOfset3[i] + Gdx.graphics.getHeight() /12, Gdx.graphics.getWidth() / 65);
			*/
			if (Intersector.overlaps(foxCircle, enemyCircle[i]) || Intersector.overlaps(foxCircle, enemyCircle2[i]) || Intersector.overlaps(foxCircle, enemyCircle3[i])) {
				gameState = 2;
			}
		}
		for (int i = 0; i < otherNumberOfEnemies; i++) {

			//
			//if you wanna see the hit circle open comment line under bellow this line
			//
			/*shapeRenderer.circle(otherEnemyX[i] + Gdx.graphics.getWidth() / 34, Gdx.graphics.getHeight() / 2 + otherEnemyOfset[i] + Gdx.graphics.getHeight() /12, Gdx.graphics.getWidth() / 65);
			shapeRenderer.circle(otherEnemyX[i] + Gdx.graphics.getWidth() / 34, Gdx.graphics.getHeight() / 2 + otherEnemyOfset2[i] + Gdx.graphics.getHeight() /12, Gdx.graphics.getWidth() / 65);
			 */

			if (Intersector.overlaps(foxCircle, otherEnemyCircle[i]) || Intersector.overlaps(foxCircle, otherEnemyCircle2[i])) {
				gameState = 2;
			}


		}
	}
		//shapeRenderer.end();

	
	@Override
	public void dispose () {

	}
}
