package Battleship;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Cell extends Rectangle {
    boolean wasShoot;

    public Cell (int a , int b, int c, int d) {  // 2nd constructor for ships
        super(a,b,c,d); // super overrides the values of the rectangle class --> a,b,c,d are from class Ship --> see our class Ship
        this.wasShoot=false;
}
    public Cell (boolean wasShot,int a , int b, int c, int d) {  // 2nd constructor for  board
        super(a, b, c, d); // super overrides the values of the rectangle class --> a,b,c,d are from class  Board --> see our class Board
        this.wasShoot = false;
    }

    public  Cell (int a, int b){ // 3rd constructor for the fields of the health
        super(a,b);
        setStroke(Color.WHITE);
        FileInputStream file = null;
        try {
            file = new FileInputStream(System.getProperty("user.dir") + "\\src\\res\\herz.png"); // small heart image
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert file != null;
       setFill(new ImagePattern(new Image(file)));
    }
    }


