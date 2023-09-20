package com.asteroids;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Ship extends Character {
    public Ship(int x,int y){
        super(new Polygon(-5,-5,10,0,-5,5),x,y);
    }
    public void decelerate(){
        double changeX = Math.cos(Math.toRadians(super.getCharacter().getRotate()));
        double changeY = Math.sin(Math.toRadians(super.getCharacter().getRotate()));

        super.setMovement(super.getMovement().subtract(changeX*0.05,changeY*0.05));
    }

}
