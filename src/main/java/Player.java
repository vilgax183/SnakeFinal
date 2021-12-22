import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Player {

    private String name;
    private Image Img;

    public Player(String name, Image img) {
        this.name = name;
        this.Img = img;
    }

    public String getName() {
        return name;
    }

    public Image getColor() {
        return Img;
    }
}
