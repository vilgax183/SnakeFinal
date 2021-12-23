import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;

import static java.lang.Thread.sleep;

public class Display extends javafx.scene.canvas.Canvas  {

    private GraphicsContext gc;
    private Game game;

    public Display(Game game) throws InterruptedException {
        super();
        super.setWidth(600);
        super.setHeight(1000);
        gc = super.getGraphicsContext2D();
        gc.setFont(Font.font("Verdana", 20));
        this.game = game;
        update();
    }

    public void update() throws InterruptedException {
        gc.clearRect(0, 0, super.getWidth(), super.getHeight());

        sleep(100);
        drawSquares();
        drawNumbers();drawSnakesAndLadders();
        drawPlayers();
    }

    private void drawSquares() {
        gc.setFill(Color.PINK);
        gc.fillRect(0,0,600,600);
        gc.setFill(Color.MINTCREAM);
        for (int i = 0; i < 100; i++) {
            if ((i / 10) % 2 == 0) {
                if (i % 2 == 0) {
                    gc.fillRect(60 * i % 600, 540 - (i / 10) * 60, 60, 60);
                }
            } else {
                if (i % 2 == 1) {
                    gc.fillRect(60 * i % 600, 540 - (i / 10) * 60, 60, 60);
                }
            }
        }

    }

    private void drawNumbers() {
        gc.setFill(Color.BLACK);
        for (int i = 1; i <= 100; i++) {
            gc.fillText("" + (i), getCoordinates(i).getKey() - 25, getCoordinates(i).getValue() - 11);
        }
    }

    private void drawSnakesAndLadders() {
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Snake2.png"),15,390);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Snake3.png"),170,25);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Snake2.png"),430,25);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Snake2.png"),370,440);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Snake.png"),20,28);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Snake4.png"),450,145);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Snake5.png"),240,265);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Snake5.png"),120,320);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Snake6.png"),300,25);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Snake6.png"),370,200);

        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Ladder1.png"),555,85);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Ladder2.png"),140,30);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Ladder3.png"),320,270);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Ladder4.png"),20,90);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Ladder4.png"),390,30);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Ladder5.png"),20,270);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Ladder5.png"),500,30);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Ladder6.png"),200,390);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Ladder6.png"),260,30);
        gc.drawImage(new Image("C:\\Users\\asus'\\IdeaProjects\\SnakeFinal\\src\\Ladder7.png"),440,210);

    }

    private void drawPlayers() throws InterruptedException {
        for (int i = 0; i < game.getPlayers().length; i++) {
            int square = game.getPosition(i);
            int xCoordinate = getCoordinates(square).getKey();
            int yCoordinate = getCoordinates(square).getValue();
            System.out.println(square);
            xCoordinate = xCoordinate - 26 + ((i % 4) * 14);
            yCoordinate = yCoordinate -26 - ((i / 4) * 14);
            System.out.println(xCoordinate);
            System.out.println(yCoordinate);
            Image img=game.getPlayers()[i].getColor();
            gc.drawImage(img,xCoordinate, yCoordinate);

        }

    }

    private static Pair<Integer, Integer> getCoordinates(int square) {
        int xCoordinate, yCoordinate;
        square--;
        if (((square / 10) % 2) == 0) {
            xCoordinate = 30 + (60 * square % 600);
            yCoordinate = 570 - (square / 10) * 60;
        } else {
            xCoordinate = 570 - (60 * square % 600);
            yCoordinate = 570 - (square / 10) * 60;
        }
        return new Pair<>(xCoordinate, yCoordinate);
    }

}
