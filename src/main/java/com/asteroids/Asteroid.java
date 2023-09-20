package com.asteroids;

import javafx.scene.shape.Polygon;

import java.util.Random;

public class Asteroid extends Character {
    public Asteroid(int x,int y){
        super(new PolygonMaker().createPolygon(),x,y);

        Random random = new Random();
        super.getCharacter().setRotate(random.nextInt(360));

        int accelerateBy = random.nextInt(10) + 1;

        for(int i = 0; i < accelerateBy;i++){
            accelerate();
        }

    }
    @Override
    public void move(){
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + 0.5 - new Random().nextDouble());
    }
}
