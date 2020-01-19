package Battleship;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Board {

    Rectangle [][] myBoard = new Rectangle[10][10];
    Rectangle [][] enemyBoard = new Rectangle[10][10];
    Line [] frames = new Line[8];

    public Board()  //konstruktor
    {
        for(int j=0;j<10;j++) {
            for (int i = 0; i < 10; i++) {
                this.enemyBoard[j][i] = new Rectangle(450+30*j, 50+30*i, 30, 30);
                this.enemyBoard[j][i].setFill(new ImagePattern(MyMethods.getWaterPattern()));
                this.enemyBoard[j][i].setStroke(Color.WHITE);
            }
        }
        for(int j=0;j<10;j++) {
            for (int i = 0; i < 10; i++) {
                this.myBoard[j][i] = new Rectangle(450+30*j, 420+30*i, 30, 30);
                this.myBoard[j][i].setFill(new ImagePattern(MyMethods.getWaterPattern()));
                this.myBoard[j][i].setStroke(Color.WHITE);
            }
        }

        //Umrandung der Boarder
        //Durch x und y Koordinate des ersten feldes -> alle anderen Koordinaten klar, somit wird der Rand auch jederzeit anpassbar
        double x, y;
        //Abstand der Linien zu den Boards
        double offset=20;
        x = this.enemyBoard[0][0].getX();
        y = this.enemyBoard[0][0].getY();
        //Horizontale Linien von oben nach unten
        frames[0] = new Line(x-offset, y-offset, x+300+offset, y-offset);
        frames[1] = new Line(x-offset, y+300+offset, x+300+offset, y+300+offset);
        frames[2] = new Line(x-offset, y+270+100-offset, x+300+offset, y+270+100-offset);
        frames[3] = new Line(x-offset, y+670+offset, x+300+offset, y+670+offset);
        //Vertikale Linien von Links nach Rechts
        frames[4] = new Line(x-offset, y-offset, x-offset, y+300+offset);
        frames[5] = new Line(x+300+offset, y-offset, x+300+offset, y+300+offset);
        frames[6] = new Line(x-offset, y+370-offset, x-offset, y+670+offset);
        frames[7] = new Line(x+300+offset, y+370-offset, x+300+offset, y+670+offset);
    }

}
