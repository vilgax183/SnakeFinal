
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.stage.Stage;

import javax.sound.sampled.*;

import javafx.scene.image.ImageView;


import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class GUI extends javafx.application.Application {
    public static final int NUM_DIE=1;
    private  Button Player1,Player2,DIce;
    private Label texta;
    private enum Phase {
        ROLL, MOVE, EXTRA_MOVE, SWITCH, FINISH
    }

    private Stage gui;
    private Display display;
    private Game game;
    private Player activePlayer;
    private Phase phase;
    private int roll;
    private ImageView alpha;

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        gui = primaryStage;
        gui.setTitle("Snakes & Ladders");
        gui.getIcons();
        game = new Game();

        GridPane grid = new GridPane();

        display = new Display(game);

        grid.add(display,0,0);

        Player1=new Button();
        grid.getChildren().add(Player1);
        Player1.setText("Player 1");
        Player1.setFont(new Font("Arial",24));
        Player1.setPrefSize(180,60);
        Player1.setTranslateX(60);
        Player1.setTranslateY(250);

        Player2=new Button();
        grid.getChildren().add(Player2);
        Player2.setText("Player 2");
        Player2.setFont(new Font("Arial",24));
        Player2.setPrefSize(180,60);
        Player2.setTranslateX(360);
        Player2.setTranslateY(250) ;


        DIce = new Button();
        grid.getChildren().add(DIce);

        DIce.setPrefSize(120,120);
        DIce.setTranslateX(240);
        DIce.setTranslateY(250);
        Image img=new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\dice1.png");
        ImageView iview=new ImageView(img);
        DIce.setGraphic(iview);
        DIce.setOnAction(event -> {
            try {
                roll=Game.rollDice();
                Image img1=new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\dice"+roll+".png");
                ImageView iview1=new ImageView(img1);
                DIce.setGraphic(iview1);
                File file=new File("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Rol.wav");
                try {
                    AudioInputStream audio= AudioSystem.getAudioInputStream(file);
                    Clip clip=AudioSystem.getClip();
                    clip.open(audio);
                    clip.start();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
                runGame();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                try {
                    sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        });
        alpha=new ImageView();
        alpha.setFitHeight(180);
        alpha.setFitWidth(10);
        alpha.setTranslateY(280);
        alpha.setTranslateX(120);
        grid.getChildren().add(alpha);

        Scene scene = new Scene(grid);
        gui.setScene(scene);
        gui.show();
        activePlayer = game.getPlayers()[0];


        phase = Phase.ROLL;

    }

    private void runGame() throws InterruptedException {

        switch (phase) {
            case ROLL:

                phase = Phase.MOVE;
                break;
            case MOVE:
                makeMove();

                phase = Phase.EXTRA_MOVE;

            case EXTRA_MOVE:
                checkExtraMovement();
                phase = Phase.SWITCH;

            case SWITCH:
                switchPlayer();
                phase = Phase.ROLL;
                runGame();

            case FINISH:
                break;
        }

    }


    private void makeMove() throws InterruptedException {
        if (game.getPosition(activePlayer) + roll > 100) {
            game.moveTo(activePlayer, (game.getPosition(activePlayer)));
            display.update();
        }
        else if(game.getPosition(activePlayer) + roll == 100){
            DIce.setDisable(true);
            for(int i=0;i<roll;i++) {
                game.moveTo(activePlayer, game.getPosition(activePlayer) + 1);
                File file = new File("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\pawn.wav");
                try {
                    AudioInputStream audio = AudioSystem.getAudioInputStream(file);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audio);
                    clip.start();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
                if(activePlayer.getName()=="Player1") {
                    alpha = new ImageView("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\PLAYER 1 WON.png");
                }
                alpha=new ImageView(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\PLAYER 2 WON.png"));

                display.update();
                File file1 = new File("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\audiomass-output2.wav");
                try {
                    AudioInputStream audio = AudioSystem.getAudioInputStream(file1);
                    Clip clip1 = AudioSystem.getClip();
                    clip1.open(audio);
                    clip1.start();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }


                sleep(200);

            }
        }
        else{
            for(int i=0;i<roll;i++){
                game.moveTo(activePlayer, game.getPosition(activePlayer) + 1);
                display.update();
                File file=new File("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\pawn.wav");
                try {
                    AudioInputStream audio= AudioSystem.getAudioInputStream(file);
                    Clip clip=AudioSystem.getClip();
                    clip.open(audio);
                    clip.start();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
                display.update();
                sleep(200);

            }



        }

    }

    private void checkExtraMovement() throws InterruptedException {
        while (true) {
            // Snakes or ladders
            if (game.getBoard().getSnakes().containsKey(game.getPosition(activePlayer))) {
                game.moveTo(activePlayer, game.getBoard().getSnakes().get(game.getPosition(activePlayer)));
                // add snake voice
                File file=new File("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Hiss.wav");
                try {
                    AudioInputStream audio= AudioSystem.getAudioInputStream(file);
                    Clip clip=AudioSystem.getClip();
                    clip.open(audio);
                    clip.start();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }

                display.update();
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            } else if (game.getBoard().getLadders().containsKey(game.getPosition(activePlayer))) {
                game.moveTo(activePlayer, game.getBoard().getLadders().get(game.getPosition(activePlayer)));
                // add ladder voice
                File file=new File("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\ladderAudio.wav");
                try {
                    AudioInputStream audio= AudioSystem.getAudioInputStream(file);
                    Clip clip=AudioSystem.getClip();
                    clip.open(audio);
                    clip.start();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
                display.update();
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {  // No snakes or ladders
                break;
            }
        }
    }

    private void switchPlayer() {
        for (int i = 0; i < game.getPlayers().length; i++) {
            if (game.getPlayers()[i] == activePlayer) {
                // If cycle is at the end
                if (i == game.getPlayers().length - 1) {
                    Player1.setTextFill(Color.RED);
                    Player2.setTextFill(Color.WHEAT);
                    activePlayer = game.getPlayers()[0];
                    break;
                } else {
                    Player1.setTextFill(Color.WHEAT);
                    Player2.setTextFill(Color.RED);
                    activePlayer = game.getPlayers()[i + 1];
                    break;
                }
            }
        }
    }






    public static void main(String[] args) {
        launch(args);
    }
}
