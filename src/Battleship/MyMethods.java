package Battleship;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Igor on 18/01/2020
 * @e-mail igor.tesanovic@stud.fh-campuswien.ac.at
 */
public class MyMethods {

    public static Image getWaterPattern() {  // water pattern for fields on board
        FileInputStream file = null;
        try {
            file = new FileInputStream(System.getProperty("user.dir") + "\\src\\res\\water.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert file != null;
        return new Image(file);
    }

    public static Image getShipPattern(int i){
        FileInputStream file = null;
        try {
            file = new FileInputStream(System.getProperty("user.dir") + "\\src\\res\\"+i+".png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert file != null;
        return new Image(file);
    }

    public static Image getFireImage(){
        FileInputStream file = null;
        try {
            file = new FileInputStream(System.getProperty("user.dir") + "\\src\\res\\fire.gif");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Image(file);
    }


}
