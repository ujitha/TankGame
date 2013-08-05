/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.newdawn.slick.Image;
/**
 *
 * @author Ujitha
 */
public class Brick {
  
    
    private Image brick;
    private int X_cordinates,Y_cordinates;
    private int damage;
    
    public Brick(Image img)
    {
        this.brick=img;
        
    }

    /**
     * @return the brick
     */
    public Image getBrick() {
        return brick;
    }

    /**
     * @param brick the brick to set
     */
    public void setBrick(Image brick) {
        this.brick = brick;
    }

    /**
     * @return the X_cordinates
     */
    public int getX_cordinates() {
        return X_cordinates;
    }

    /**
     * @param X_cordinates the X_cordinates to set
     */
    public void setX_cordinates(int X_cordinates) {
        this.X_cordinates = X_cordinates;
    }

    /**
     * @return the Y_cordinates
     */
    public int getY_cordinates() {
        return Y_cordinates;
    }

    /**
     * @param Y_cordinates the Y_cordinates to set
     */
    public void setY_cordinates(int Y_cordinates) {
        this.Y_cordinates = Y_cordinates;
    }

    /**
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * @param damage the damage to set
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }
}


