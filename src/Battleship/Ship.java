package Battleship;

public class Ship{

   // Rectangle [][] allShips = new Rectangle[2][5];  // that was like this in first version
   Cell [][] allShips = new Cell[2][5];
    double [] startX = new double[10]; //Alle Start x-Koordinaten der Schiffe für Reset und spätere Verwendung bei der Kollision (zum Rücksetzten des Schiffes)
    double [] startY = new double[10]; //Alle y-Koordinaten
    boolean[] inField = new boolean[10]; //true wenn schiff richtig positioniert, false wenn flasch


    public Ship() {
        for(int i=0;i<2;i++){               // 0 = MYSHIP, 1 = ENEMYSHIP
            for(int j=0;j<5;j++){           // 5, weil das größte schiff 5x1 quadrate hoch ist
                this.allShips[i][j] = new Cell(j,50+j*50, 150+350*i, 30, 30+j*30);  //Abstand Höhe, schiffe reihe1 und reihe 2
              //  this.startX[j+5*i] = this.allShips[i][j].getX();
                this.startX[j+5*i] = this.allShips[i][j].getX();
                this.startY[j+5*i] = this.allShips[i][j].getY();
                this.inField[j+5*i] = false;
            }
        }
    }

    public void resetShip(int finalI, int finalJ){
        this.allShips[finalI][finalJ].setX(this.startX[finalJ + finalI*5]);
        this.allShips[finalI][finalJ].setY(this.startY[finalJ + finalI*5]);
        this.inField[finalJ+5*finalI] = false;
        //Ist das Schiff gedreht?? Wenn ja zurückdrehen!
        if(this.allShips[finalI][finalJ].getWidth() > this.allShips[finalI][finalJ].getHeight()){
            double z;
            z = this.allShips[finalI][finalJ].getWidth();
            this.allShips[finalI][finalJ].setWidth(this.allShips[finalI][finalJ].getHeight());
            this.allShips[finalI][finalJ].setHeight(z);
        }
    }

    }


