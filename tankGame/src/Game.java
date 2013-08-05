/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Ujitha
 */
public class Game extends BasicGame {

    private TiledMap gameMap;
    private Image brick, stone, water, tank0, tank1, tank2, tank3, tank4, coins, life,tankDisplay,bullet;
    
    LinkedList<Tank> tankarray;
    LinkedList<Brick> brickarray;
    LinkedList<Stone> stonearray;
    LinkedList<Water> waterarray;
    ArrayList<Coin> coinarray;
    ArrayList<LifePack> lifearray;
    
    SpriteSheet mapsheet;
    SpriteSheet tanks;
    SpriteSheet stonesheet;
    SpriteSheet bricksheet;
    float count = 0;
    int x;
    int y;
    
    
       

    public Game() {
        
        super("Tank wars - Let the war begins");

        tankarray = new LinkedList<Tank>();
        brickarray = new LinkedList<Brick>();
        stonearray = new LinkedList<Stone>();
        waterarray = new LinkedList<Water>();
        coinarray = new ArrayList<Coin>();
        lifearray = new ArrayList<LifePack>();
             
        
    }
//Initialize the resources
    @Override
    public void init(GameContainer gc) throws SlickException {
        gameMap = new TiledMap("Images/gameMap.tmx");
        mapsheet = new SpriteSheet("Images/gamemap.jpg", 32, 32);
        tanks = new SpriteSheet("Images/MulticolorTanks.png", 32, 32);
        stonesheet = new SpriteSheet("Images/tankStone.jpg", 32, 32);
        bricksheet = new SpriteSheet("Images/Bricks.jpg", 32, 32);

        brick = bricksheet.getSprite(3, 4);
        stone = stonesheet.getSprite(5, 4);
        tank0 = tanks.getSprite(0, 0);
        tank1 = tanks.getSprite(1, 1);
        tank2 = tanks.getSprite(2, 2);
        tank3 = tanks.getSprite(3, 3);
        tank4 = tanks.getSprite(4, 4);
        water = mapsheet.getSprite(0, 7);

        coins = new Image("Images/coin.jpg");
        life = new Image("Images/life.png");
        tankDisplay=new Image("Images/worldtank.jpg");
        bullet=new Image("Images/bullet.jpg");
        
    }

    //update the 
    @Override
    public void update(GameContainer gc, int delta) throws SlickException {

        count += delta / 1000f;
        if (count >= 1.0) {
            x += 32;
            y += 32;
            count = 0;
        }

    }

    //Render the screen and map to frames
    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {

        grphcs.setColor(Color.yellow);
        gameMap.render(0, 0);

        Brick brk;
        Stone stn;
        Water wtr;
        Tank tnk;
        Coin cp;
        LifePack lif;

        //load the bricks into the map 
        if (!brickarray.isEmpty()) {
            
            for (int i = 0; i < brickarray.size(); i++) {
                brk = brickarray.get(i);
                brick.draw((float) brk.getX_cordinates() * 32, (float) brk.getY_cordinates() * 32);

            }
        }
        
        //load the stones into the map
        if (!stonearray.isEmpty()) {
            for (int i = 0; i < stonearray.size(); i++) {
                stn = stonearray.get(i);
                stone.draw((float) stn.getxCord() * 32, (float) stn.getyCord() * 32);
            }
        }
        
        //load the water into the map
        if (!waterarray.isEmpty()) {
            for (int i = 0; i < waterarray.size(); i++) {
                wtr = waterarray.get(i);
                water.draw((float) wtr.getX() * 32, (float) wtr.getY() * 32);
            }
        }
        
        //load the tank images to tha map if it is not dead
        if (!tankarray.isEmpty()) {
            for (int i = 0; i < tankarray.size(); i++) {
                tnk = tankarray.get(i);
                if(tnk.getHealth()>0)
                {
                tnk.getTankImg().draw((float) tnk.getX_cordination() * 32, (float) tnk.getY_cordination() * 32);
                } 
                
            }

        }
        
        //load the coins into the map
        if (!coinarray.isEmpty()) {
            for (int i = 0; i < coinarray.size(); i++) {
                cp = coinarray.get(i);

                coins.draw(cp.x * 32, cp.y * 32,32,32);             

            }
        }

        //load the life packs into the map
        if (!lifearray.isEmpty()) {
            for (int i = 0; i < lifearray.size(); i++) {
                lif = lifearray.get(i);
                life.draw(lif.x*32, lif.y*32,32,32);

                
            }
        }
        
        for (int a = 0; a <= 640; a += 32) {
            grphcs.drawLine(0, a, 640, a);
            grphcs.drawLine(a, 0, a, 640);
        }
        
        grphcs.setColor(Color.white);
        
        // Draw the score board
        
        for(int k=22;k<=34;k+=3)
        {
            grphcs.drawLine(k*32,4*32, k*32,10*32);
        }
        
        for(int m=4;m<=10;m++)
        {
            grphcs.drawLine(22*32,m*32,34*32,m*32);
        }
        
        grphcs.drawString(" Player ID",22*32,4*32);
        grphcs.drawString(" Points",25*32,4*32);
        grphcs.drawString(" Coins",28*32,4*32);
        grphcs.drawString(" Health",31*32,4*32);
        grphcs.drawString("    P0",22*32,5*32);
        grphcs.drawString("    P1",22*32,6*32);
        grphcs.drawString("    P2",22*32,7*32);
        grphcs.drawString("    P3",22*32,8*32);
        grphcs.drawString("    P4",22*32,9*32);
        
       
        tankDisplay.draw(23*32,11*32,10*32,6*32);
                     
        int a1=5;
        //update the current points, coins and health to the score board
        for(int b=0;b<tankarray.size();b++)
        {
            try{
            grphcs.drawString("    "+Integer.toString(tankarray.get(b).getPoints())+"$",25*32 ,a1*32);
            grphcs.drawString("    "+Integer.toString(tankarray.get(b).getCoins())+"$",28*32 ,a1*32);
            grphcs.drawString("    "+Integer.toString(tankarray.get(b).getHealth())+"%",31*32 ,a1*32);
            a1++;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        grphcs.drawString("Created  by :", 23*32, 18*32); 
        grphcs.drawString("Ujitha Iroshan ", 27*32, 18*32);
        grphcs.drawString("Chamila Dilshan", 27*32, 19*32);
        
        
    
    }

    
  
    //Display function take the map details, tank details, coin details,life pack details as a parameter and load them for draw
    public void display(int[][] maparr, int[][] tankarr, ArrayList<Coin> coinP, ArrayList<LifePack> lifeP) 
    {
        this.coinarray=coinP;;
        this.lifearray.clear();
        System.out.println(lifearray.size()+"+++++++++++++++++");
        Tank tanki;
        this.tankarray.clear();

       for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {


                if (maparr[i][j] == 1) {
                    
                    Brick brik = new Brick(brick);
                    brik.setX_cordinates(i);
                    brik.setY_cordinates(j);
                    this.brickarray.add(brik);
                    
                } else if (maparr[i][j] == 2) {
                    Stone stne = new Stone(stone);
                    stne.setxy(i, j);
                    this.stonearray.add(stne);

                } else if (maparr[i][j] == 3) {
                    Water wtr = new Water(water);
                    wtr.setCordinates(i, j);
                    this.waterarray.add(wtr);
                }


            }
        }
       
             
        for (int a = 0; a < tankarr.length; a++) {
            
            if (a == 0) {
                tanki = new Tank(tank0);
            } else if (a == 1) {
                tanki = new Tank(tank1);
            } else if (a == 2) {
                tanki = new Tank(tank2);
            } else if (a == 3) {
                tanki = new Tank(tank3);
            } else {
                tanki = new Tank(tank4);

            }

            for (int b = 0; b <7; b++) {
                if (b == 0) {
                    tanki.setX_cordination(tankarr[a][b]);
                } else if (b == 1) {
                    tanki.setY_cordination(tankarr[a][b]);
                } else if (b == 2) {
                    tanki.setDirection(tankarr[a][b]);
                } else if (b == 3) {
                    tanki.setWhether_shot(tankarr[a][b]);
                } else if (b == 4) {
                    tanki.setHealth(tankarr[a][b]);
                }
                else if(b==5)
                {
                    tanki.setCoins(tankarr[a][b]);
                }
                else if(b==6)
                {
                    tanki.setPoints(tankarr[a][b]);
                }


            }

            this.tankarray.add(tanki);
           
        }
        System.out.println(lifeP.size()+"*****************");
        for(int n=0;n<lifeP.size();n++)
        {
            this.lifearray.add(lifeP.get(n));
        }
    }
    
    

    public static void main(String[] args) throws SlickException {

        Game sg = new Game();
        try {
           
            Thread t = new insocket(7000,sg);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AppGameContainer app = new AppGameContainer(sg);
        app.setDisplayMode(1120, 640, false);
        app.start();
        


    }
}
