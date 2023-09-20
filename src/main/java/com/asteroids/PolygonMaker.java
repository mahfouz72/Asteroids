package com.asteroids;

import javafx.scene.Parent;
import javafx.scene.shape.Polygon;

import java.util.Random;

public class PolygonMaker {

    // formulas from  http://mathworld.wolfram.com/Pentagon.html
    public Polygon createPolygon(){
        Random random = new Random();
        double size = 10 + random.nextInt(15);
        Polygon polygon = new Polygon();

        double x = Math.cos(Math.PI * 2 / 5);
        double y = Math.cos(Math.PI / 5);
        double z = Math.sin(Math.PI * 2 / 5);
        double w = Math.sin(Math.PI * 4 / 5);

        polygon.getPoints().addAll(size,0.0,size*x,-1*size*z,-1*size*y,-1*size*w,-1*size*y,size*w,size*x,size*z);
        polygon.getPoints().replaceAll(aDouble -> aDouble + random.nextInt(5) - 2);

        return polygon;
    }
}
