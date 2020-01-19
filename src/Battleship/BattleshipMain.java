package Battleship;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;


public class BattleshipMain extends Application{

    private Board Board = new Board();
    private Ship Ships = new Ship();
    private Button restartbutton = new Button("Restart");
    private Button invisMyShipsButton = new Button("mDive");
    private Button invisEnemyShipsButton = new Button("eDive");
    private Button revealMyShipsButton = new Button("mReveal");
    private Button revealEnemyShipsButoon = new Button("eReveal");
    private Label announcement = new Label("Place ships and press\nthe Dive-Button!!!");
   HBox enemyHealth = new HBox();
   HBox playerHealth = new HBox();

    private boolean play;
  public int numberOfRechteClick = 0;
   public int eHealth = 14;
   public int pHealth = 14;


    public BattleshipMain() throws FileNotFoundException {
    }

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("BATTLESHIP FIRST TRY");

        Group root = new Group();
        root.getChildren().addAll(createComponents());

        Scene scene = new Scene(root,1200, 750, Color.DEEPSKYBLUE);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

        startGame();
    }
    private Group createComponents(){
        Group components = new Group();

        //Positionierung Restart-Button
        restartbutton.setTranslateY(50);
        restartbutton.setTranslateX(50);
        restartbutton.setPrefSize(100, 50);
        //Positionierung invisMyShipsButton
        invisMyShipsButton.setTranslateY(420);
        invisMyShipsButton.setTranslateX(310);
        invisMyShipsButton.setPrefSize(60, 30);
        //Positionierung invisEnemyShipsButton
        invisEnemyShipsButton.setTranslateY(50);
        invisEnemyShipsButton.setTranslateX(310);
        invisEnemyShipsButton.setPrefSize(60, 30);
        //Positionierung revealMyShipsButton
        revealMyShipsButton.setTranslateY(690);
        revealMyShipsButton.setTranslateX(310);
        revealMyShipsButton.setPrefSize(60, 30);
        //Positionierung revealEnemyShipsButoon
        revealEnemyShipsButoon.setTranslateY(320);
        revealEnemyShipsButoon.setTranslateX(310);
        revealEnemyShipsButoon.setPrefSize(60, 30);

        //Positionierung Label
        announcement.setPrefSize(300, 100);
        announcement.setTranslateX(50);  // 800
        announcement.setTranslateY(350); // 50
        double max_font_size =20.0;
        announcement.setFont(new Font(max_font_size));

        // Position of health Hbox
        createHealth();

        for(int i=0; i<10;i++){
            for(int j=0;j<10;j++){
                components.getChildren().addAll(Board.myBoard[i][j], Board.enemyBoard[i][j]);
            }
            //Färben der einzelnen Linien:
            if(i<8){
                if(i==0 || i==1 || i==4 || i==5){
                    Board.frames[i].setStroke(Color.RED);
                }
                else{
                    Board.frames[i].setStroke(Color.GREEN);
                }
                components.getChildren().addAll(Board.frames[i]);
            }
        }

        for(int i=0; i<2;i++){
            for(int j=0;j<5;j++){
                if(i==0){
                    Ships.allShips[i][j].setFill(Color.DARKRED);
                }
                else{
                    Ships.allShips[i][j].setFill(Color.GREEN);
                }
                components.getChildren().addAll(Ships.allShips[i][j]);
            }
        }

        components.getChildren().addAll(enemyHealth,playerHealth,restartbutton, invisMyShipsButton, revealMyShipsButton, invisEnemyShipsButton, revealEnemyShipsButoon, announcement);
        return components;
    }

    //Bis hierhin wurden alle Elemente des Spiels erzeugt. Regeln für jegliche Interagtion werden in dieser Funktion definiert
    private void userInteraction(){
        this.play = false;
        //Was passiert wenn ich durch Maus mit Schiffen interagiere?
        for(int i=0; i<2; i++){
            for(int j=0; j<5;j++){
                int finalJ = j;
                int finalI = i;
                //hier gehe ich alle Schiffe mir dem Eventhander durch
                Ships.allShips[finalI][finalJ].addEventHandler(MouseEvent.ANY, mouseEvent -> {
                    //was passiert wenn ich eines der Schiffe mit der Linken Maustaste anklicke UND gedrückt halte. Die if condition da ich nicht will das das alles für transparente schiffe gilt
                    //in dieser if-condition wird der großteil des Programms landen, da beim zB loslassen eines Schiffes sehr viel passiert. (Koordinaten korregieren, auf interesection kontrollieren, ...)
                    if(Ships.allShips[finalI][finalJ].getFill() != Color.TRANSPARENT){
                        //was passiert bei gedrückter linker maustaste
                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                            Ships.allShips[finalI][finalJ].setX(mouseEvent.getSceneX()-Ships.allShips[finalI][finalJ].getWidth()/2);  //sieht kompliziert aus ist aber nur dazu da das schiff zu zentrieren
                            Ships.allShips[finalI][finalJ].setY(mouseEvent.getSceneY()-Ships.allShips[finalI][finalJ].getHeight()/2);
                        }

                        //was passiert wenn ich die Mause ziehe UND (während) die linke Maustaste gerdrückt ist
                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && mouseEvent.getButton() == MouseButton.PRIMARY){
                            Ships.allShips[finalI][finalJ].setX(mouseEvent.getX()-15);//-Ships.allShips[finalI][finalJ].getWidth()/2);
                            Ships.allShips[finalI][finalJ].setY(mouseEvent.getY()-15);//-Ships.allShips[finalI][finalJ].getHeight()/2);
                        }

                        //was passiert wenn ich ein Schiff mit der rechten Maustaste anklicke
                        if(mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED && mouseEvent.getButton() == MouseButton.SECONDARY){
                            //Ships.turnShip(finalI, finalJ);
                            double z; //zwischenspeicher für tausch
                            z = Ships.allShips[finalI][finalJ].getWidth();
                            Ships.allShips[finalI][finalJ].setWidth(Ships.allShips[finalI][finalJ].getHeight());
                            Ships.allShips[finalI][finalJ].setHeight(z);
                            numberOfRechteClick++;
                            if(numberOfRechteClick%2==0) {
                                Ships.allShips[finalI][finalJ].setFill(new ImagePattern(MyMethods.getShipPattern(finalJ + 10)));
                            } else {
                                Ships.allShips[finalI][finalJ].setFill(new ImagePattern(MyMethods.getShipPattern(finalJ)));
                            }
                            intersection(finalI, finalJ);
                        }

                        //was passiert wenn ich das Schiff loslasse? -> Mittelpunkt des ersten "quadrats" des Schiffes soll gleich Mittelpunkt des Quatrads sein über dem die Maus sich befindet
                        if(mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED){
                            double mouseX, mouseY;
                            mouseX = mouseEvent.getSceneX();
                            mouseY = mouseEvent.getSceneY();
                            for(int i1 = 0; i1 <10; i1++){
                                for(int j1 = 0; j1 <10; j1++){
                                    if(Board.myBoard[i1][j1].getX() <= mouseX && mouseX <= Board.myBoard[i1][j1].getX()+30){
                                        if(Board.myBoard[i1][j1].getY() <= mouseY && mouseY <= Board.myBoard[i1][j1].getY()+30){
                                            //System.out.println(""+i+"\n"+j);
                                            double middleX, middleY; //mitte des Kästchens über welchem die Maus ist, dieses muss gleich der Mitte des Schiffes werden
                                            middleX = Board.myBoard[i1][j1].getX()+Board.myBoard[i1][j1].getWidth()/2;
                                            middleY = Board.myBoard[i1][j1].getY()+Board.myBoard[i1][j1].getHeight()/2;

                                            Ships.allShips[finalI][finalJ].setX(middleX-Board.myBoard[i1][j1].getWidth()/2);
                                            Ships.allShips[finalI][finalJ].setY(middleY-Board.myBoard[i1][j1].getHeight()/2);
                                            if(finalI == 1){
                                                Ships.inField[finalJ+5*finalI] = true;
                                            }
                                        }
                                    }
                                    if(Board.enemyBoard[i1][j1].getX() <= mouseX && mouseX <= Board.enemyBoard[i1][j1].getX()+30){
                                        if(Board.enemyBoard[i1][j1].getY() <= mouseY && mouseY <= Board.enemyBoard[i1][j1].getY()+30){
                                            //System.out.println(""+i+"\n"+j);
                                            double middleX, middleY; //mitte des Kästchens über welchem die Maus ist, dieses muss gleich der Mitte des Schiffes werden
                                            middleX = Board.enemyBoard[i1][j1].getX()+Board.enemyBoard[i1][j1].getWidth()/2;
                                            middleY = Board.enemyBoard[i1][j1].getY()+Board.enemyBoard[i1][j1].getHeight()/2;

                                            Ships.allShips[finalI][finalJ].setX(middleX-Board.enemyBoard[i1][j1].getWidth()/2);
                                            Ships.allShips[finalI][finalJ].setY(middleY-Board.enemyBoard[i1][j1].getHeight()/2);
                                            if(finalI == 0){
                                                Ships.inField[finalJ+5*finalI] = true;
                                            }
                                        }
                                    }
                                    //Falls schiff vom board weggezogen wird
                                    if(Ships.allShips[finalI][finalJ].getX() < Board.enemyBoard[0][0].getX()){
                                        Ships.inField[finalJ+5*finalI] = false;
                                    }
                                }
                            }
                            //Überprüfung auf Intersection, in Methode da wsl öfter verwendet wird
                            intersection(finalI, finalJ);
                            hideEnemyShips();
                            hidePlayerShips();

                        }
                    }
                    //Was passiert wenn ich ein Schiff anklicke das transparent ist?
                    if(Ships.allShips[finalI][finalJ].getFill() == Color.TRANSPARENT){
                        if(mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED && play){
                            double mouseX, mouseY;
                            mouseX = mouseEvent.getSceneX();
                            mouseY = mouseEvent.getSceneY();
                            //Ich schaue nach in welchem Feld sich die Maus befindet während das transparente Schiff angeklickt wird, dieses wird rot gefärbt
                            for(int i1 = 0; i1 <10; i1++) {
                                for (int j1 = 0; j1 <10; j1++){
                                    if(Board.myBoard[i1][j1].getX() < mouseX && mouseX < Board.myBoard[i1][j1].getX()+30){
                                        if(Board.myBoard[i1][j1].getY() < mouseY && mouseY < Board.myBoard[i1][j1].getY()+30){
                                            updatePlayerHealth(pHealth);

                                          bombSound();
                                            Board.myBoard[i1][j1].setFill(new ImagePattern(MyMethods.getFireImage()));
                                           //Board.myBoard[i][j].setFill(Color.RED);

                                        }
                                    }
                                    if(Board.enemyBoard[i1][j1].getX() < mouseX && mouseX < Board.enemyBoard[i1][j1].getX()+30){
                                        if(Board.enemyBoard[i1][j1].getY() < mouseY && mouseY < Board.enemyBoard[i1][j1].getY()+30){
                                           bombSound();
                                           updateEnemyHealth(eHealth);
                                            Board.enemyBoard[i1][j1].setFill(new ImagePattern(MyMethods.getFireImage()));
                                          //  Board.enemyBoard[i][j].setFill(Color.RED);
                                        }
                                    }
                                }
                            }

                        }
                    }
                });
            }
        }

        //Was passiert wenn ich auf ein Feld klicke
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                int finalI = i;
                int finalJ = j;
                Board.enemyBoard[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    //Das Feld in dem ich mich befinde darf natürlich nicht rot sein
                    if (Board.enemyBoard[finalI][finalJ].getFill() != Color.RED && play) {
                        waterSound();
                        Board.enemyBoard[finalI][finalJ].setFill(Color.GREEN);

                    }
                });
                Board.myBoard[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    if(Board.myBoard[finalI][finalJ].getFill() != Color.RED && play){
                       waterSound();
                        Board.myBoard[finalI][finalJ].setFill(Color.GREEN);
                    }
                });
            }
        }

        //was passiert wenn ich den Reset-Button drücke?
        restartbutton.setOnAction(e -> {
            //Reset Schiffe
            for(int i=0; i<2;i++){
                for(int j=0;j<5;j++)
                {
                    Ships.resetShip(i, j);

                    //Transparenz aufheben
                    if(i==0){
                        Ships.allShips[i][j].setFill(new ImagePattern(MyMethods.getShipPattern(j)));

                    }
                    if(i==1){
                        Ships.allShips[i][j].setFill(new ImagePattern(MyMethods.getShipPattern(j)));

                    }
                }
            }
            //Reset Felder
            for(int i=0; i<10; i++){
                for(int j=0; j<10; j++){
                    Board.myBoard[i][j].setFill(new ImagePattern(MyMethods.getWaterPattern()));
                    Board.enemyBoard[i][j].setFill(new ImagePattern(MyMethods.getWaterPattern()));
                }
            }
            //reset game status
            play = false;
            //reset Label
            announcement.setText("Place ships and press\nthe Dive-Button!!!");
            // reset health
            if(eHealth>0){
           while(eHealth >= 0){
                enemyHealth.getChildren().remove(eHealth);
                eHealth--;
            }
            }
            if(pHealth>0) {
                while (pHealth >= 0) {
                  playerHealth.getChildren().remove(pHealth);
                    pHealth--;
                }
            }
            eHealth=14;
            pHealth=14;
            createHealth();
        });

        //was passiert wenn ich den revealEnemyShipsButton drücke?
        revealEnemyShipsButoon.setOnAction(e -> {
            for(int j=0; j<5; j++){
                Ships.allShips[0][j].setFill(Color.DARKRED);

            }
        });


        //was passiert wenn ich den invisEnemyShipsButton drücke?
        /*invisEnemyShipsButton.setOnAction(e -> {
            //da es "meine" Schiffe sind schau ich mir nur die erste Reihe an -> i=0
            int counter=0;
            for(int j=0; j<5; j++){
                if(Ships.inField[j]){
                    counter++;
                }
                if(counter == 5){
                    for(int k=0; k<5; k++){
                        Ships.allShips[0][k].setFill(Color.TRANSPARENT);
                    }
                }
            }
            counter=0;
            for(int i=0; i<10; i++){
                if(Ships.inField[i]){
                    counter++;
                }
            }
            if(counter==10){
                play = true;
            }
        }); */

        //was passiert wenn ich den invisMyShipsButton drücke?
      /*  invisMyShipsButton.setOnAction(e -> {
            int counter=0;
            for(int j=0; j<5; j++){
                if(Ships.inField[j+5]){
                    counter++;
                }
                if(counter == 5){
                    for(int k=0; k<5; k++){
                        Ships.allShips[1][k].setFill(Color.TRANSPARENT);
                    }
                }
            }
            counter=0;
            for(int i=0; i<10; i++){
                if(Ships.inField[i]){
                    counter++;
                }
            }
            if(counter==10){
                announcement.setText("Start!");
                play = true;
            }
        });
        */

        //was passiert wenn ich den invisMyShipsButton drücke?
        revealMyShipsButton.setOnAction(e -> {
            for(int j=0; j<5; j++){
                Ships.allShips[1][j].setFill(Color.GREEN);
            }
        });
    }

    private void intersection(int finalI, int finalJ){
        //Collision Detection mit dem Rand
        //erster Schritt: Den Umriss jeden Schiffs und jedem Frame als Objekt bekommen und in Feld speichern
        Bounds [] shapes = new Bounds[18]; //der 11te Platz dient als Puffer für späteren Tausch
        for(int i=0;i<2;i++){
            for(int j=0;j<5;j++){
                shapes[j+5*i] = Ships.allShips[i][j].getBoundsInParent();
            }
        }
        for(int i=10;i<18;i++){
            shapes[i] = Board.frames[i-10].getBoundsInParent();
        }

        //Anschließend für jeden Umriss eine Intersection kontrolle durchführen. Nicht vergessen das das alles erst passiert nachdem man das Schiff loslässt
        for(int i=0;i<18;i++){
            if(shapes[finalJ+5*finalI].intersects(shapes[i])){
                //Schiff auf ursprüngliche Koordinaten zurückbewegen falls intersection auftritt
                if(shapes[finalJ+5*finalI] != shapes[i]){ //Außer mit eigenem Umriss
                    Ships.resetShip(finalI, finalJ);
                }

            }
        }
    }


    private void startGame(){

        userInteraction();

        if(play){
            announcement.setText("Start!");
            revealEnemyShipsButoon.setDisable(true); // disable reveal button during  game
            revealMyShipsButton.setDisable(true);// disable reveal button during  game

        }
    }

    public void hideEnemyShips() {
        int counter = 0;
        for (int j = 0; j < 5; j++) {
            if (Ships.inField[j]) {
                counter++;
            }
            if (counter == 5) {
                for (int k = 0; k < 5; k++) {
                    Ships.allShips[0][k].setFill(Color.TRANSPARENT);
                }
            }
        }
        counter = 0;
        for (int i = 0; i < 10; i++) {
            if (Ships.inField[i]) {
                counter++;
            }
        }
        if (counter == 10) {
            play = true;
        }

    }

    public void hidePlayerShips(){
        int counter=0;
        for(int j=0; j<5; j++){
            if(Ships.inField[j+5]){
                counter++;
            }
            if(counter == 5){
                for(int k=0; k<5; k++){
                    Ships.allShips[1][k].setFill(Color.TRANSPARENT);
                }
            }
        }
        counter=0;
        for(int i=0; i<10; i++){
            if(Ships.inField[i]){
                counter++;
            }
        }
        if(counter==10){
            announcement.setText("Start!");
            play = true;
        }
    }

    public  void waterSound(){
        //Instantiating Media class
        Media media = new Media(new File(System.getProperty("user.dir") + "\\src\\res\\water.mp3").toURI().toString());
        //Instantiating MediaPlayer class
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        //by setting this property to true, the audio will be played
        mediaPlayer.setAutoPlay(true);
    }

    public void bombSound(){
        //Instantiating Media class
        Media media = new Media(new File(System.getProperty("user.dir") + "\\src\\res\\bomb.mp3").toURI().toString());
        //Instantiating MediaPlayer class
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        //by setting this property to true, the audio will be played
        mediaPlayer.setAutoPlay(true);
    }

    public void updateEnemyHealth(int counter){
        enemyHealth.getChildren().remove(counter);
        eHealth--;
    }
    public void updatePlayerHealth(int counter){
        playerHealth.getChildren().remove(counter);
        pHealth--;
    }

  public void createHealth(){

      for(int i=0; i<15;i++){
          enemyHealth.getChildren().add(new Cell(25,25));
          playerHealth.getChildren().add(new Cell(25,25));
      }
      enemyHealth.setTranslateX(800);
      enemyHealth.setTranslateY(30);
      playerHealth.setTranslateX(800);
      playerHealth.setTranslateY(400);
  }


}
