package Battleship;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;

public class Board {

    Cell [][] myBoard = new Cell[10][10];
    Cell [][] enemyBoard = new Cell[10][10];
    Line [] frames = new Line[8];
    double [] startX = new double[100];
    double [] startY = new double[100];
    double [] startX1 = new double[100];
    double [] startY1 = new double[100];


    public Board()  // konstruktor
    {
        for(int j=0;j<10;j++) {
            for (int i = 0; i < 10; i++) {
                this.enemyBoard[j][i] = new Cell(false,450+30*j, 50+30*i, 30, 30);
                this.enemyBoard[j][i].setFill(new ImagePattern(BattleshipMain.getWaterPattern())); // water image pattern
                this.enemyBoard[j][i].setStroke(Color.WHITE);
                this.startX[j+10*i] = this.enemyBoard[j][i].getX(); // koordinate für bot
                this.startY[j+10*i] = this.enemyBoard[j][i].getY(); // koordinate für bot
            }
        }

        for(int j=0;j<10;j++) {
            for (int i = 0; i < 10; i++) {
                this.myBoard[j][i] = new Cell(false,false,450+30*j, 420+30*i, 30, 30);
                this.myBoard[j][i].setFill(new ImagePattern(BattleshipMain.getWaterPattern())); // water image pattern
                this.myBoard[j][i].setStroke(Color.WHITE);
                this.startX1[j+10*i] = this.enemyBoard[j][i].getX();
                this.startY1[j+10*i] = this.enemyBoard[j][i].getY();
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
