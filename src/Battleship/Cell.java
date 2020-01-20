package Battleship;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Cell extends Rectangle {

    public Cell (int type,int a , int b, int c, int d) {  // 2nd constructor for ships
        super(a,b,c,d);
        Image img = BattleshipMain.getShipPattern(type+10);
        setFill(new ImagePattern(img));  // ship pattern for all ships
    }

    public  Cell (int a, int b){ // 3rd constructor for fields of the  health
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


