import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GUI extends javafx.application.Application{
    public static final int NUM_DIE=1;
    private enum Phase {
        ROLL, MOVE, EXTRA_MOVE, SWITCH, FINISH
    }

    private Stage gui;
    private Display display;
    private Game game;
    private Player activePlayer;
    private Phase phase;
    private int roll;

    @Override
    public void start(Stage primaryStage) {
        gui = primaryStage;
        gui.setTitle("Snakes & Ladders");
        game = new Game();

        GridPane grid = new GridPane();

        ArrayList<ImageView> diceArray=new ArrayList<>();
//        Random random=new Random();
//        for(int i=0;i< NUM_DIE;i++){
//            ImageView iv=new ImageView(new Image())
//        }
//        Button Roll=new Button("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\dice6.png");
//        Roll.setOnAction(e -> {
//            for(int i=0;i)
//        });

        display = new Display(game);

        grid.add(display,0,0);

//        text = new TextArea();
//        grid.add(text,1,0);
//        text.setPrefSize(240, 600);
//        text.setEditable(false);

        Button DIce = new Button();
        grid.getChildren().add(DIce);
        DIce.setText("Roll");
        DIce.setPrefSize(70,70);
        DIce.setTranslateX(270);
        DIce.setTranslateY(280);
        DIce.setOnAction(event -> {
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

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runGame();
        });


        Scene scene = new Scene(grid);
        gui.setScene(scene);
        gui.show();
        activePlayer = game.getPlayers()[0];
        phase = Phase.ROLL;
        runGame();
    }

    private void runGame() {
        switch (phase) {
            case ROLL:
                roll = Game.rollDice();
                phase = Phase.MOVE;
                break;
            case MOVE:
                makeMove();
                phase = Phase.EXTRA_MOVE;
                checkNextPhase();
                break;
            case EXTRA_MOVE:
                checkExtraMovement();
                phase = Phase.SWITCH;
                checkNextPhase();
                break;
            case SWITCH:
                switchPlayer();
                phase = Phase.ROLL;
                runGame();
                break;
            case FINISH:
                break;
        }
    }

    private void checkNextPhase() {

        if (game.getBoard().getSnakes().containsKey(game.getPosition(activePlayer)) ||
                game.getBoard().getLadders().containsKey(game.getPosition(activePlayer))) {
            phase = Phase.EXTRA_MOVE;
        } else {
            phase = Phase.SWITCH;
        }
    }

    private void makeMove() {
        if (game.getPosition(activePlayer) + roll > 100) {
            // Bounce back at final square
            game.moveTo(activePlayer, (100 - (game.getPosition(activePlayer) + roll) % 100));
            display.update();
        } else {
            game.moveTo(activePlayer, game.getPosition(activePlayer) + roll);
            display.update();
        }
    }

    private void checkExtraMovement() {
        while (true) {
            // Snakes or ladders
            if (game.getBoard().getSnakes().containsKey(game.getPosition(activePlayer))) {
                game.moveTo(activePlayer, game.getBoard().getSnakes().get(game.getPosition(activePlayer)));

                display.update();

            } else if (game.getBoard().getLadders().containsKey(game.getPosition(activePlayer))) {
                game.moveTo(activePlayer, game.getBoard().getLadders().get(game.getPosition(activePlayer)));
                display.update();
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
                    activePlayer = game.getPlayers()[0];
                    break;
                } else {
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
