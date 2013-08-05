/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.newdawn.slick.Image;
/**
 *
 * @author Ujitha
 */
public class Water{
    
    private int X_cod,Y_cod;
    private Image waterimg;
    
    public Water(int X,int Y,Image img)
    {
        this.X_cod=X;
        this.Y_cod=Y;
        this.waterimg=img;      
        
    }
    public Water(Image img)
    {
        this.waterimg=img;
    }
    
    public void setCordinates(int X,int Y)
    {
        this.X_cod=X;
        this.Y_cod=Y;
    }
    
    public int getX()
    {
        return X_cod;
    }
    
    public int getY()
    {
        return Y_cod;
    }
    
    public Image getimage()
    {
        return waterimg;
    }
    
}

