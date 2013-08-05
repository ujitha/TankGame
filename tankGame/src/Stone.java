/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.newdawn.slick.Image;
/**
 *
 * @author Ujitha
 */
public class Stone {

    private int xCord;
    private int yCord;
    private Image image;

    public Stone(Image img) {
        this.image = img;
    }

    public void setxy(int xCord, int yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public Image getImage() {
        return image;
    }

    public int getxCord() {
        return xCord;
    }

    public int getyCord() {
        return yCord;
    }
}