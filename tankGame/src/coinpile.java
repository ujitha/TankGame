/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.newdawn.slick.Image;
/**
 *
 * @author Ujitha
 */
public class coinpile{
    
    private int X_cod,Y_cod;
    private int lifetime;
    private int appeartime;
    private Image coin;
    private int value;
    
    public coinpile(Image img,int appeartime)
    {
        this.coin=img;
        this.appeartime=appeartime;
    }
    
    public void setCordinates(int X,int Y)
    {
        this.X_cod=X;
        this.Y_cod=Y;
    }
    
    public void setValue(int val)
    {
        this.value=val;
    }
    
    public int getValue()
    {
        return value;
    }

    /**
     * @return the X_cod
     */
    public int getX_cod() {
        return X_cod;
    }

    /**
     * @return the Y_cod
     */
    public int getY_cod() {
        return Y_cod;
    }

    /**
     * @return the lifetime
     */
    public int getLifetime() {
        return lifetime;
    }

    /**
     * @param lifetime the lifetime to set
     */
    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    /**
     * @return the appeartime
     */
    public int getAppeartime() {
        return appeartime;
    }

    /**
     * @param appeartime the appeartime to set
     */
    public void setAppeartime(int appeartime) {
        this.appeartime = appeartime;
    }

    /**
     * @return the coin
     */
    public Image getCoin() {
        return coin;
    }

    /**
     * @param coin the coin to set
     */
    public void setCoin(Image coin) {
        this.coin = coin;
    }
     
}
