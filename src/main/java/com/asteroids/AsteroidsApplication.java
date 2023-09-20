package com.asteroids;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AsteroidsApplication extends Application {
    public final static int WIDTH = 600;
    public final static int HEIGHT = 400;
    private int points = 0;

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Asteroids!");

        Pane pane = new Pane();
        Label label = new Label("Points : "+ points);
        label.setFont(new Font(20));
        pane.getChildren().add(label);
        pane.setPrefSize(WIDTH,HEIGHT);


        Ship ship = new Ship(WIDTH/2,HEIGHT/2);
        List<Asteroid> asteroids = createAsteroids();
        List<Projectile> projectiles = new ArrayList<>();

        asteroids.forEach(Character::accelerate);
        asteroids.forEach(Character::accelerate);

        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));
        pane.getChildren().add(ship.getCharacter());


        Scene scene = new Scene(pane);

        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }

                if(pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();
                }
                if(pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();
                }
                if(pressedKeys.getOrDefault(KeyCode.DOWN,false)){
                    ship.decelerate();
                }
                if(pressedKeys.getOrDefault(KeyCode.SPACE,false) && projectiles.size() < 3){

                    Projectile projectile = new Projectile((int)ship.getCharacter().getTranslateX(),(int)ship.getCharacter().getTranslateY());
                    projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
                    projectiles.add(projectile);

                    projectile.accelerate();
                    projectile.setMovement(projectile.getMovement().normalize().multiply(3.0));

                    pane.getChildren().add(projectile.getCharacter());
                }

                ship.move();
                asteroids.forEach(Character::move);
                projectiles.forEach(Character::move);

                List<Projectile> ToBeRemoved = projectiles.stream().filter(projectile-> {

                    List<Asteroid> collisions = asteroids.stream()
                            .filter(asteroid -> asteroid.collide(projectile))
                            .toList();

                   if(collisions.isEmpty()) return false;

                   collisions.forEach(asteroid -> {
                        asteroids.remove(asteroid);
                        pane.getChildren().remove(asteroid.getCharacter());
                        points += 100;
                        label.setText("Points: " + points);
                   });

                    return true;
                }).toList();

                ToBeRemoved.forEach(projectile -> {
                    pane.getChildren().remove(projectile.getCharacter());
                    projectiles.remove(projectile);
                });

                asteroids.forEach(asteroid -> {
                    if(ship.collide(asteroid)){
                        stop();
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        StackPane pane = new StackPane();
                        Label label = new Label("Game Over! You Scored "+ points+ " Points." );
                        label.setFont(new Font(30));
                        label.setAlignment(Pos.CENTER);
                        pane.getChildren().add(label);
                        Scene endScene = new Scene(pane,600,400);
                        stage.setScene(endScene);
                    }
                });

                if(new Random().nextInt(100) == 1){
                    Asteroid asteroid = new Asteroid(WIDTH,HEIGHT);
                    if(!asteroid.collide(ship)){
                        asteroids.add(asteroid);
                        pane.getChildren().add(asteroid.getCharacter());
                    }
                }
            }
        }.start();

        stage.setScene(scene);
        stage.show();
    }

    private List<Asteroid> createAsteroids() {
        List<Asteroid> asteroids = new ArrayList<>();
        for(int i = 0; i < 5;i++){
            Random random = new Random();
            Asteroid asteroid = new Asteroid(random.nextInt(WIDTH/3),random.nextInt(HEIGHT/2));
            asteroids.add(asteroid);
        }
        return asteroids;
    }

    public static void main(String[] args) {
        launch(AsteroidsApplication.class);
    }
}