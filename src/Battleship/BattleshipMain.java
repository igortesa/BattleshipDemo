package Battleship;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class BattleshipMain extends Application{

    private Board Board = new Board();
    private Ship Ships = new Ship();
    private Button restartbutton = new Button("Restart");
   /* private Button invisMyShipsButton = new Button("mDive");
    private Button invisEnemyShipsButton = new Button("eDive");
    private Button revealMyShipsButton = new Button("mReveal");
    private Button revealEnemyShipsButoon = new Button("eReveal");

    */
    private Label announcement = new Label("Place ships on the Boards!!!");
     public  Label popUpLabel = new Label(" ");
    public  Button newGame = new Button("New Game");
    public  Button exitGame = new Button("Exit");
    public VBox popUpWindow  = new VBox(50,popUpLabel,newGame,exitGame); // if it's game over this windows pops up
    public HBox enemyHealth = new HBox(); // player 1 health --> we can see health like heart list
    public  HBox playerHealth = new HBox(); // player 2 health --> we can see health like heart list
    public  Label eLabel = new Label(); // it shows a player 1 health number
    public Label pLabel = new Label(); // it shows a player 2 health number
    private boolean enemyMove = true; // player 1 can play
    private boolean playerMove = false;// player 2 can play
    private boolean play;
   //  public int numberOfRechteClick = 0; // number of the right clicks for the ship rotation
     public int eHealth = 14; // 14 because Ships array starts with 0 --> 0-14 = 15
    public int pHealth = 14;


    public static void main(String[] args)
    {
        launch();
    }

    @Override
    public void start(Stage stage) {    //Hauptmethode, hier wird das Spiel programmtechnisch gestartet
        stage.setTitle("BATTLESHIP");

        Group root = new Group();
        root.getChildren().addAll(createComponents());

        Scene scene = new Scene(root,1200, 750, Color.DEEPSKYBLUE);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

        startGame();  //Aufruf des Spiels über die Methode "private void startGame()"
    }

    private Group createComponents(){
        Group components = new Group();

        //Positionierung Restart-Button
        restartbutton.setTranslateY(50);
        restartbutton.setTranslateX(50);
        restartbutton.setPrefSize(100, 50);
       /*
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
        */

        //Positionierung Label
        announcement.setPrefSize(300, 100);
        announcement.setWrapText(true);
        announcement.setTranslateX(50);  // 800
        announcement.setTranslateY(350); // 50
        announcement.setFont(new Font(20.0));

     // pop up window position and appearance
        popUpLabel.setPrefHeight(50);
        popUpLabel.setPrefWidth(150);
        popUpLabel.setFont(new Font(20.0));
        newGame.setPrefWidth(100);
        newGame.setPrefHeight(50);
        exitGame.setPrefWidth(100);
        exitGame.setPrefHeight(50);
        popUpWindow.setTranslateX(500);
        popUpWindow.setTranslateY(250);
        popUpWindow.setPrefWidth(300);
        popUpWindow.setPrefHeight(300);
        popUpWindow.setAlignment(Pos.CENTER);
        popUpWindow.setStyle("-fx-background-color: #7b809c;"); // background color of pop up window
        popUpWindow.setVisible(false); // it should be invisible when game starts

        // Position of health's Hbox
        createHealth();   // method call

        for(int i=0; i<10;i++){  //Erstellung des 10x10 Spielfelds für my & enemy
            for(int j=0;j<10;j++){
                //Erstellung der Linien rund um das 10x10 Feld, Gruen für Myship und Rot für Enemy
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

        for(int i=0; i<2;i++){  //Loop zur Färbung der Schiffe; 0 = darkred = ENEMYSHIP; 1 = green = MYSHIP;
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
        // the bottom line we need to uncomment if we want to show button from first version of the game
       // components.getChildren().addAll(eLabel,pLabel,enemyHealth,playerHealth,restartbutton, invisMyShipsButton, revealMyShipsButton, invisEnemyShipsButton, revealEnemyShipsButoon, announcement,popUpWindow);

        // the bottom line is for all elements we need in final version
        components.getChildren().addAll(eLabel,pLabel,enemyHealth,playerHealth,restartbutton,announcement,popUpWindow);
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
                    if(Ships.allShips[finalI][finalJ].getFill() != Color.TRANSPARENT){  // != transparent zur Überprüfung obs das Schiff farbig (momentan DARKRED & GREEN) und damit nicht im Spielmodus befindet
                        //was passiert bei gedrückter linker maustaste
                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {  //zum Einrasten des Schiffs
                            Ships.allShips[finalI][finalJ].setX(mouseEvent.getSceneX()-Ships.allShips[finalI][finalJ].getWidth()/2);  //sieht kompliziert aus ist aber nur dazu da das schiff zu zentrieren
                            Ships.allShips[finalI][finalJ].setY(mouseEvent.getSceneY()-Ships.allShips[finalI][finalJ].getHeight()/2); // y Koordinate setzen
                        }
                        //was passiert wenn ich die Mause ziehe UND (während) die linke Maustaste gerdrückt ist
                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED && mouseEvent.getButton() == MouseButton.PRIMARY) {  //zum Einrasten des Schiffs
                                Ships.allShips[finalI][finalJ].setX(mouseEvent.getX() - 15);//-Ships.allShips[finalI][finalJ].getWidth()/2);  // x Koordinate setzen
                                Ships.allShips[finalI][finalJ].setY(mouseEvent.getY() - 15); //-Ships.allShips[finalI][finalJ].getHeight()/2);    // y Koordinate setzen
                        }
                        //was passiert wenn ich ein Schiff mit der rechten Maustaste anklicke
                        if(mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED && mouseEvent.getButton() == MouseButton.SECONDARY){ //zum Drehen des Schiffs um 90 Grad Richtung Osten;
                           //Ships.turnShip(finalI, finalJ);
                            double z; //zwischenspeicher für tausch
                            z = Ships.allShips[finalI][finalJ].getWidth();
                            Ships.allShips[finalI][finalJ].setWidth(Ships.allShips[finalI][finalJ].getHeight());
                            Ships.allShips[finalI][finalJ].setHeight(z);

                            // the bottom commented lines are for ship rotation with image patterns
                           /* numberOfRechteClick++;
                            if(numberOfRechteClick%2==0) {
                                Ships.allShips[finalI][finalJ].setFill(new ImagePattern(getShipPattern(finalJ + 10)));
                            } else {
                                Ships.allShips[finalI][finalJ].setFill(new ImagePattern(getShipPattern(finalJ)));
                            }
                            */
                            intersection(finalI, finalJ);

                        }

                        //was passiert wenn ich das Schiff loslasse? -> Mittelpunkt des ersten "quadrats" des Schiffes soll gleich Mittelpunkt des Quatrads sein über dem die Maus sich befindet
                        if(mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED){
                            double mouseX, mouseY;
                            mouseX = mouseEvent.getSceneX();
                            mouseY = mouseEvent.getSceneY();
                            for(int i1 = 0; i1 <10; i1++) {
                                for (int j1 = 0; j1 < 10; j1++) {
                                    if (Board.myBoard[i1][j1].getX() <= mouseX && mouseX <= Board.myBoard[i1][j1].getX() + 30) {
                                        if (Board.myBoard[i1][j1].getY() <= mouseY && mouseY <= Board.myBoard[i1][j1].getY() + 30) {
                                            //System.out.println(""+i+"\n"+j);
                                            double middleX, middleY; //mitte des Kästchens über welchem die Maus ist, dieses muss gleich der Mitte des Schiffes werden
                                            middleX = Board.myBoard[i1][j1].getX() + Board.myBoard[i1][j1].getWidth() / 2;
                                            middleY = Board.myBoard[i1][j1].getY() + Board.myBoard[i1][j1].getHeight() / 2;

                                            Ships.allShips[finalI][finalJ].setX(middleX - Board.myBoard[i1][j1].getWidth() / 2);
                                            Ships.allShips[finalI][finalJ].setY(middleY - Board.myBoard[i1][j1].getHeight() / 2);
                                            if (finalI == 1) {
                                                Ships.inField[finalJ + 5 * finalI] = true;
                                                dropSound();
                                            }
                                        }
                                    }

                                        if (Board.enemyBoard[i1][j1].getX() <= mouseX && mouseX <= Board.enemyBoard[i1][j1].getX() + 30) {
                                            if (Board.enemyBoard[i1][j1].getY() <= mouseY && mouseY <= Board.enemyBoard[i1][j1].getY() + 30) {
                                                //System.out.println(""+i+"\n"+j);
                                                double middleX, middleY; //mitte des Kästchens über welchem die Maus ist, dieses muss gleich der Mitte des Schiffes werden
                                                middleX = Board.enemyBoard[i1][j1].getX() + Board.enemyBoard[i1][j1].getWidth() / 2;
                                                middleY = Board.enemyBoard[i1][j1].getY() + Board.enemyBoard[i1][j1].getHeight() / 2;

                                                Ships.allShips[finalI][finalJ].setX(middleX - Board.enemyBoard[i1][j1].getWidth() / 2);
                                                Ships.allShips[finalI][finalJ].setY(middleY - Board.enemyBoard[i1][j1].getHeight() / 2);
                                                if (finalI == 0) {
                                                    Ships.inField[finalJ + 5 * finalI] = true;
                                                    dropSound();
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
                            hideEnemyShips(); // hide player 1 ships
                            hidePlayerShips(); // hide player 2 ships
                        }
                    }
                    //Was passiert wenn ich ein Schiff anklicke das transparent ist?
                    if(Ships.allShips[finalI][finalJ].getFill() == Color.TRANSPARENT) {

                            if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED && play) {
                                    double mouseX, mouseY;
                                    mouseX = mouseEvent.getSceneX();
                                    mouseY = mouseEvent.getSceneY();
                                    //Ich schaue nach in welchem Feld sich die Maus befindet während das transparente Schiff angeklickt wird, dieses wird rot gefärbt
                                    for (int i1 = 0; i1 < 10; i1++) {
                                        for (int j1 = 0; j1 < 10; j1++) {
                                            if (Board.myBoard[i1][j1].getX() < mouseX && mouseX < Board.myBoard[i1][j1].getX() + 30) {
                                                if (Board.myBoard[i1][j1].getY() < mouseY && mouseY < Board.myBoard[i1][j1].getY() + 30 && playerMove && !Board.myBoard[i1][j1].wasShoot) {
                                                    updatePlayerHealth(pHealth); // method call to update health state for the player 2
                                                    bombSound();
                                                    Board.myBoard[i1][j1].setFill(new ImagePattern(getFireImage())); //  change red color to fire photo
                                                    //Board.myBoard[i][j].setFill(Color.RED);
                                                    Board.myBoard[i1][j1].wasShoot=true;
                                                    playerMove=true;
                                                    announcement.setText("Player 2 your turn!");
                                                }
                                            }
                                            if (Board.enemyBoard[i1][j1].getX() < mouseX && mouseX < Board.enemyBoard[i1][j1].getX() + 30) {
                                                if (Board.enemyBoard[i1][j1].getY() < mouseY && mouseY < Board.enemyBoard[i1][j1].getY() + 30 && enemyMove && !Board.enemyBoard[i1][j1].wasShoot) {
                                                    bombSound();
                                                    updateEnemyHealth(eHealth); // method call to update health state for the player 1
                                                    Board.enemyBoard[i1][j1].setFill(new ImagePattern(getFireImage())); //  change red color to fire photo
                                                    Board.enemyBoard[i1][j1].wasShoot=true;
                                                    //  Board.enemyBoard[i][j].setFill(Color.RED);
                                                    enemyMove=true;
                                                    announcement.setText("Player 1 your turn!");
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
                    if (Board.enemyBoard[finalI][finalJ].getFill() != Color.RED && play && enemyMove && !Board.enemyBoard[finalI][finalJ].wasShoot) {
                        waterSound();
                        Board.enemyBoard[finalI][finalJ].setFill(Color.GREEN);
                        Board.enemyBoard[finalI][finalJ].wasShoot=true;

                        enemyMove=false;
                        playerMove=true;
                        announcement.setText("Player 2 your turn!");
                    }
                });
                Board.myBoard[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    if(Board.myBoard[finalI][finalJ].getFill() != Color.RED && play && playerMove && !Board.myBoard[finalI][finalJ].wasShoot){
                       waterSound();
                        Board.myBoard[finalI][finalJ].setFill(Color.GREEN);
                        Board.myBoard[finalI][finalJ].wasShoot=true;
                        playerMove=false;
                        enemyMove=true;
                        announcement.setText("Player 1 your turn!");
                    }
                });
            }
        }

        //was passiert wenn ich den Reset-Button drücke?
        restartbutton.setOnAction(e -> resetGame()); // event call the method resetGame

        //was passiert wenn ich den New Game -Button on pop up window drücke?
        newGame.setOnAction(e -> resetGame()); // event call the method resetGame

        //was passiert wenn ich den Exit-Button on pop up window drücke?
        exitGame.setOnAction(e -> {
            Stage stage = (Stage) exitGame.getScene().getWindow();
            stage.close();
        });


        //was passiert wenn ich den revealEnemyShipsButton drücke?
       /* revealEnemyShipsButoon.setOnAction(e -> {
            for(int j=0; j<5; j++){
                Ships.allShips[0][j].setFill(Color.DARKRED);

            }
        });

        */

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
      /*  revealMyShipsButton.setOnAction(e -> {
            for(int j=0; j<5; j++){
                Ships.allShips[1][j].setFill(Color.GREEN);
            }
        });

       */
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
        //Prüfung, ob Schiff in falschen 10x10 Raster eingegeben wurde. (zb rotes Schiff in grünes Feld)
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                for(int k=0;k<5;k++){
                    //if(Board.enemyBoard[i][j].intersects(Ships.allShips[0][k])
                    if(Board.myBoard[i][j].getX() <= Ships.allShips[0][k].getX() && Ships.allShips[0][k].getX() <= Board.myBoard[i][j].getX()+30){
                        if(Board.myBoard[i][j].getY() <= Ships.allShips[0][k].getY() && Ships.allShips[0][k].getY() <= Board.myBoard[i][j].getY()+30) {
                            Ships.resetShip(0, k);
                        }
                    }
                    if(Board.enemyBoard[i][j].getX() <= Ships.allShips[1][k].getX() && Ships.allShips[1][k].getX() <= Board.enemyBoard[i][j].getX()+30){
                        if(Board.enemyBoard[i][j].getY() <= Ships.allShips[1][k].getY() && Ships.allShips[1][k].getY() <= Board.enemyBoard[i][j].getY()+30) {
                            Ships.resetShip(1, k);
                        }
                    }
                }
            }
        }
    }


    private void startGame(){

        userInteraction();

       /*if(play){
            announcement.setText("Start --> Player 1 your turn!");
        }

        */
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
            announcement.setText("Start --> Player 1 your turn!");
            play = true;
        }
    }

    public  void waterSound(){ // it plays water sound
        //Instantiating Media class
        Media media = new Media(new File(System.getProperty("user.dir") + "\\src\\res\\water.mp3").toURI().toString());
        //Instantiating MediaPlayer class
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        //by setting this property to true, the audio will be played
        mediaPlayer.setAutoPlay(true);
    }

    public void bombSound(){  // it plays bomb sound
        //Instantiating Media class
        Media media = new Media(new File(System.getProperty("user.dir") + "\\src\\res\\bomb.mp3").toURI().toString());
        //Instantiating MediaPlayer class
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        //by setting this property to true, the audio will be played
        mediaPlayer.setAutoPlay(true);
    }

    public void dropSound(){  // it plays drop sound
        //Instantiating Media class
        Media media = new Media(new File(System.getProperty("user.dir") + "\\src\\res\\drop.mp3").toURI().toString());
        //Instantiating MediaPlayer class
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        //by setting this property to true, the audio will be played
        mediaPlayer.setAutoPlay(true);
    }

    public void updateEnemyHealth(int counter){  // update current state of the player's 1 health
        enemyHealth.getChildren().remove(counter);
        eHealth--;
        eLabel.setText("Player 2 Health: "+(eHealth+1));
        if(eHealth==-1) {
            popUpWindow.setVisible(true);
            popUpLabel.setText("Player 1 Won! ");
            announcement.setText("GAME OVER !");
        }

    }
    public void updatePlayerHealth(int counter){  // update current state of the player's 2 health
        playerHealth.getChildren().remove(counter);
        pHealth--;
        pLabel.setText("Player 1 Health: "+(pHealth+1));
        if(pHealth==-1) {
            popUpWindow.setVisible(true);
            popUpLabel.setText("Player 2 Won! ");
            announcement.setText("GAME OVER !");
        }

    }

  public void createHealth(){ // it creates both health for player 1 and player 2

      for(int i=0; i<15;i++){
          enemyHealth.getChildren().add(new Cell(25,25));
          playerHealth.getChildren().add(new Cell(25,25));
      }
      enemyHealth.setTranslateX(800);
      enemyHealth.setTranslateY(80);
      playerHealth.setTranslateX(800);
      playerHealth.setTranslateY(450);
      eLabel.setPrefSize(200, 50);
      pLabel.setPrefSize(200, 50);
      eLabel.setTranslateX(800);  // 800
      eLabel.setTranslateY(30); // 50
      pLabel.setTranslateX(800);  // 800
      pLabel.setTranslateY(400); // 50
      eLabel.setFont(new Font(20));
      pLabel.setFont(new Font(20));
      eLabel.setText("Player 2 Health: "+(eHealth+1));
      pLabel.setText("Player 1 Health: "+(pHealth+1));

  }

  /* public boolean allShipsInField(){
      int count = 0;
      for (int a = 0; a < 10; a++) {
          if (Ships.inField[a]) {
              count += 1;
          }
      }
      if (count == 9) {
          return true;
      } else return false;
  }

   */
  public void resetGame(){

      //Reset Schiffe
      for(int i=0; i<2;i++){
          for(int j=0;j<5;j++)
          {
              Ships.resetShip(i, j);

              //Transparenz aufheben
              if(i==0){
                 // Ships.allShips[i][j].setFill(new ImagePattern(getShipPattern(j)));
                  Ships.allShips[i][j].setFill(Color.DARKRED);
              }
              if(i==1) {
                  // Ships.allShips[i][j].setFill(new ImagePattern(getShipPattern(j)));
                  Ships.allShips[i][j].setFill(Color.GREEN);
              }
          }
      }
      //Reset Felder
      for(int i=0; i<10; i++){
          for(int j=0; j<10; j++){
              Board.myBoard[i][j].setFill(new ImagePattern(getWaterPattern()));
              Board.enemyBoard[i][j].setFill(new ImagePattern(getWaterPattern()));
              Board.myBoard[i][j].wasShoot=false;
              Board.enemyBoard[i][j].wasShoot=false;
          }
      }
      //reset game status
      play = false;
      //reset Label
      announcement.setText("Place ships on the Boards!!!");

      // reset health -> delete every cell from old health
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
      eHealth=14; // set health to 14 again
      pHealth=14; // set health to 14 again
      createHealth(); // it makes health again
      popUpWindow.setVisible(false); // set pop up window invisible again
      enemyMove=true;
      playerMove=false;
  }

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
/*
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

 */

    public Image getFireImage(){
        FileInputStream file = null;
        try {
            file = new FileInputStream(System.getProperty("user.dir") + "\\src\\res\\fire.gif");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert file != null;
        return new Image(file);
    }


}
