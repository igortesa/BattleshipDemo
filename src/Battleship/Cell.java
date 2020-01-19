package Battleship;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Cell extends Rectangle { // 25px x25px fields on Board
    public int x, y;
    // public Ship ship = null;
    public boolean wasShot = false;
   // private Board board;
    public int type;
    public int health;

    public Cell (int type,int a , int b, int c, int d) throws FileNotFoundException {  // 2nd constructor for ships
        super(a,b,c,d);
        this.type = type;
        health = type;
        Image img = MyMethods.getShipPattern(type+10);
        setFill(new ImagePattern(img));  // ship pattern for all ships
    }

    public  Cell (int a, int b){
        super(a,b);
        setStroke(Color.WHITE);
        FileInputStream file = null;
        try {
            file = new FileInputStream(System.getProperty("user.dir") + "\\src\\res\\herz.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert file != null;
       setFill(new ImagePattern(new Image(file)));
    }
    }


