package Battleship;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Cell extends Rectangle {
    boolean wasShoot;
  public  boolean isShip;
   int type;

    public Cell (int j,int a , int b, int c, int d) {  // 2nd constructor for ships
        super(a,b,c,d); // super overrides the values of the rectangle class --> a,b,c,d are from class Ship --> see our class Ship
        this.wasShoot=false;
        this.type=j;
}
    public Cell (boolean wasShoot,int a , int b, int c, int d) {  // 2nd constructor for enemy board
        super(a, b, c, d); // super overrides the values of the rectangle class --> a,b,c,d are from class  Board --> see our class Board
        this.wasShoot = wasShoot;
    }
    public Cell (boolean isShip,boolean wasShoot,int a , int b, int c, int d) {  // 2nd constructor for player board
        super(a, b, c, d); // super overrides the values of the rectangle class --> a,b,c,d are from class  Board --> see our class Board
        this.wasShoot = wasShoot;
        this.isShip = isShip;
        this.setOnDragDropped(new EventHandler<>() {
            @Override
            public void handle(DragEvent event) {

                Cell cell = (Cell)event.getSource();
                cell.isShip=true;
                event.consume();
            }
        });
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


