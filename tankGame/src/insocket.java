/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class insocket extends Thread {

    private int c1, c2, c3, c4;
    private ServerSocket serverSocket;
    private Main client;
    private int myNumber;
    private int myx;
    private int myy;
    private int mydir;
    private int[][] players;
    private StringTokenizer tok;
    private int[][] grid;
    private boolean status=false;
    private node[][] graph;
    private ArrayList<node> path=new ArrayList<node>();
    private Game game;
    private String[] s1;
    private int c;
    private int time;
    private ArrayList<Coin> coins = new ArrayList<Coin>();
    private ArrayList<LifePack> life = new ArrayList<LifePack>();

    public insocket(int port,Game gme) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(100000);
        client = new Main();
        game = gme;
        client.write("JOIN#");

        // 1= Brick 2=stone 3=water  5=coin 7=Life
        grid = new int[20][20];

        //[0] - x cordinate
        // [1] - y cordinate
        //[2] - direction
        //[3] - whether shot (1/0)
        // [4] - health
        //[5] - Coins
        //[6] - Points
        players = new int[5][8];
        time = 0;
    }
     public insocket(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(100000);
        client = new Main();
        //game=gme;
        client.write("JOIN#");

        // 1= Brick 2=stone 3=water  5=coin 7=Life
        grid = new int[20][20];

        //[0] - x cordinate
        // [1] - y cordinate
        //[2] - direction
        //[3] - whether shot (1/0)
        // [4] - health
        players = new int[5][8];
        time = 0;
    }
     String s;
     String l;
    public void run() {
        while (true) {
            try {

                Socket server = serverSocket.accept();
                DataInputStream in = new DataInputStream(server.getInputStream());
                s = "";
                int c = in.readChar();
                char d1 = (char) (c / 256);
                char d2 = (char) (c % 256);
                while (c != 0) {


                    if (d1 == 35) {
                        break;
                    }
                    s = s + d1;

                    if (d2 == 35) {
                        break;
                    }
                    s = s + d2;
                    c = in.readChar();
                    d1 = (char) (c / 256);
                    d2 = (char) (c % 256);

                }
                //System.out.println(s);
//                if(s.charAt(0)=='G')
//                client.write("RIGHT#");

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                //System.out.println(s);
            }
            try{
            process(s);
            }catch(Exception e){
            e.printStackTrace();
            }
        }
    }

    public void process(String s) {
        System.out.println(s);

        if (s.charAt(0) == 'I' && s.charAt(1)==':') {

            tok = new StringTokenizer(s.substring(2), ":");
            myNumber = Integer.parseInt(tok.nextToken().substring(1));
            System.out.println(myNumber);
            s1 = tok.nextToken().split(";");
            for (int i = 0; i < s1.length; i++) {
                grid[Integer.parseInt((s1[i].split(","))[0])][Integer.parseInt((s1[i].split(","))[1])] = 1;
            }

            s1 = tok.nextToken().split(";");
            for (int i = 0; i < s1.length; i++) {
                grid[Integer.parseInt((s1[i].split(","))[0])][Integer.parseInt((s1[i].split(","))[1])] = 2;
            }

            s1 = tok.nextToken().split(";");
            for (int i = 0; i < s1.length; i++) {
                grid[Integer.parseInt((s1[i].split(","))[0])][Integer.parseInt((s1[i].split(","))[1])] = 3;
            }

        }

        else if (s.charAt(0) == 'S' && s.charAt(1)==':') {
            c = 0;
            tok = new StringTokenizer(s.substring(2), ":");
            while (tok.hasMoreTokens()) {
                s1 = tok.nextToken().split(";");
                players[c][0] = Integer.parseInt(s1[1].split(",")[0]);
                players[c][1] = Integer.parseInt(s1[1].split(",")[1]);
                players[c][2] = Integer.parseInt(s1[2]);
                c++;
            }
            //System.out.println("c =" + c);
            for (int i = 0; i < c; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(players[i][j] + " ");
                }
                System.out.println("");

            }
            if(players[myNumber][1]==0){
                client.write("#DOWN");
            }


        }

        else if (s.charAt(0) == 'G' && s.charAt(1)==':') {
            tok = new StringTokenizer(s.substring(2), ":");
            l=tok.nextToken();
            for (int i = 0; i < c || l.charAt(0)=='P'; i++) {
                if(c<=i){
                    c++;
                }
                System.out.println(c+ "  " + i);
                s1 = l.split(";");
                players[i][0] = Integer.parseInt(s1[1].split(",")[0]);
                players[i][1] = Integer.parseInt(s1[1].split(",")[1]);
                players[i][2] = Integer.parseInt(s1[2]);
                players[i][3] = Integer.parseInt(s1[3]);
                players[i][4] = Integer.parseInt(s1[4]);
                 players[i][5] = Integer.parseInt(s1[5]);
                players[i][6] = Integer.parseInt(s1[6]);
                if(players[i][4]==0 && players[i][7]==0){
                    coins.add(new Coin(10000, 60000, players[i][0], players[i][1]));
                    players[i][7]=1;
                    grid[players[i][0]][players[i][1]]=5;
                    status=true;
                }
                for (int j = 0; j < coins.size(); j++) {
                    //System.out.println(c + "   " + coins.size());
                    if (players[i][0] == coins.get(j).x && players[i][1] == coins.get(j).y && players[i][4]>0) {
                        grid[coins.get(j).x][coins.get(j).y] = 0;
                        if(q.size()>0){
                        if(coins.get(j).x==q.get(q.size()-1).x && coins.get(j).y==q.get(q.size()-1).y){
                            status=true;
                        }
                        }
                        coins.remove(j);
                        j--;
                    }
                }
                for (int j = 0; j < life.size(); j++) {
                    if (players[i][0] == life.get(j).x && players[i][1] == life.get(j).y && players[i][4]>0) {
                        grid[life.get(j).x][life.get(j).y] = 0;
                        life.remove(j);
                        j--;
                    }
                }
                
                l=tok.nextToken();
            }
            if(path.size()>0){
            if( players[myNumber][0]==path.get(0).x && players[myNumber][1]==path.get(0).y){
              path.remove(0);
            }}
            time++;
            for (int i = 0; i < coins.size(); i++) {
                if (coins.get(i).vanish <= time) {
                    grid[coins.get(i).x][coins.get(i).y] = 0;
                    coins.remove(i);
                    i--;
                }
            }
            for (int i = 0; i < life.size(); i++) {
                if (life.get(i).vanish <= time) {
                    grid[life.get(i).x][life.get(i).y] = 0;
                    life.remove(i);
                    i--;
                }
            }
//            for (int i = 0; i < 20; i++) {
//                for (int j = 0; j < 20; j++) {
//                    System.out.print(grid[i][j] + " ");
//                }
//                System.out.println("");
//
//            }
            
            if (status) {
                setPath();
            }
            status=false;
            if(path.isEmpty() && coins.isEmpty() && life.size()>0){
                setPathtoL();
            }
            if(path.isEmpty() && players[myNumber][1]==0 && players[myNumber][2]==0){
                client.write("#DOWN");
            }
            else if(path.isEmpty() && players[myNumber][1]==19 && players[myNumber][2]==2){
                client.write("#UP");
            }
            else if(path.isEmpty() && players[myNumber][0]==0 && players[myNumber][2]==3){
                client.write("#RIGHT");
            }else if(path.isEmpty() && players[myNumber][0]==19 && players[myNumber][2]==1){
                client.write("#LEFT");
            }
            else{
            play();
            }
            //System.out.println("Called1");
            game.display(grid, players, coins, life);
            //System.out.println("Called");
        }

        else if (s.charAt(0) == 'C' && s.charAt(1)==':') {
            tok = new StringTokenizer(s.substring(2), ":");
            s1 = tok.nextToken().split(",");
            c3 = Integer.parseInt(s1[0]);
            c4 = Integer.parseInt(s1[1]);
            c2 = time + Integer.parseInt(tok.nextToken()) / 1000;
            c1 = Integer.parseInt(tok.nextToken());
            coins.add(new Coin(c1, c2, c3, c4));
            grid[c3][c4] = 5;
            setPath();
          }

        else if (s.charAt(0) == 'L' && s.charAt(1)==':') {
            tok = new StringTokenizer(s.substring(2), ":");
            s1 = tok.nextToken().split(",");
            c3 = Integer.parseInt(s1[0]);
            c4 = Integer.parseInt(s1[1]);
            c2 = time + Integer.parseInt(tok.nextToken()) / 1000;

            life.add(new LifePack(c2, c3, c4));
            grid[c3][c4] = 7;
        }




    }

    public void play() {
        //System.out.println(players[myNumber][0] + "   " + players[myNumber][1]);
        for(int i=0;i<path.size();i++){
                //System.out.print(path.get(i).x + " " + path.get(i).y + ",  ");
            }
        if(players[myNumber][2]==0){
            for (int i = players[myNumber][1]-1; i >= 0; i--) {
                for (int j = 0; j < c; j++) {
                    if(players[j][0]==players[myNumber][0] && players[j][1]==i && players[j][4]!=0 && (players[j][4]<=players[myNumber][4]&& (players[myNumber][2]-players[j][2]!=2 || players[myNumber][2]-players[j][2]!=-2))){
                        client.write("SHOOT#");
                        return;
                    }
                }
                if(grid[players[myNumber][0]][i]==1 || grid[players[myNumber][0]][i]==2 || grid[players[myNumber][0]][i]==3){
                    break;
                }
            }
        }else if(players[myNumber][2]==1){
            for (int i = players[myNumber][0]+1; i <20; i++) {
                for (int j = 0; j < c; j++) {
                    if(players[j][1]==players[myNumber][1] && players[j][0]==i && players[j][4]!=0 && (players[j][4]<=players[myNumber][4]&& (players[myNumber][2]-players[j][2]!=2 || players[myNumber][2]-players[j][2]!=-2))){
                        client.write("SHOOT#");
                        return;
                    }
                }
                if(grid[i][players[myNumber][1]]==1 || grid[i][players[myNumber][1]]==2 || grid[i][players[myNumber][1]]==3){
                    break;
                }
            }
        }
        else if(players[myNumber][2]==2){
            for (int i = players[myNumber][1]+1; i <20; i++) {
                for (int j = 0; j < c; j++) {
                    if(players[j][0]==players[myNumber][0] && players[j][1]==i && players[j][4]!=0 && (players[j][4]<=players[myNumber][4]&& (players[myNumber][2]-players[j][2]!=2 || players[myNumber][2]-players[j][2]!=-2))){
                        client.write("SHOOT#");
                        return;
                    }
                }
                if(grid[players[myNumber][0]][i]==1 || grid[players[myNumber][0]][i]==2 || grid[players[myNumber][0]][i]==3){
                    break;
                }
            }
        }else if(players[myNumber][2]==3){
            for (int i = players[myNumber][0]-1; i >=0; i--) {
                for (int j = 0; j < c; j++) {
                    if(players[j][1]==players[myNumber][1] && players[j][0]==i && players[j][4]!=0 && (players[j][4]<=players[myNumber][4]&& (players[myNumber][2]-players[j][2]!=2 || players[myNumber][2]-players[j][2]!=-2))){
                        client.write("SHOOT#");
                        return;
                    }
                }
                if(grid[i][players[myNumber][1]]==1 || grid[i][players[myNumber][1]]==2 || grid[i][players[myNumber][1]]==3){
                    break;
                }
            }
        }
        if(path.size()>0){
            //.out.println("Next" + path.get(0).);
            if(path.get(0).x==players[myNumber][0] && path.get(0).y==players[myNumber][1]-1){
                client.write("UP#");
                if(players[myNumber][2]==0){
                   // path.remove(0);
                }
            }else if(path.get(0).x==players[myNumber][0]+1 && path.get(0).y==players[myNumber][1]){
                client.write("RIGHT#");
                if(players[myNumber][2]==1){
                   // path.remove(0);
                }
            }else if(path.get(0).x==players[myNumber][0] && path.get(0).y==players[myNumber][1]+1){
                client.write("DOWN#");
                if(players[myNumber][2]==2){
                   // path.remove(0);
                }
            }else if(path.get(0).x==players[myNumber][0]-1 && path.get(0).y==players[myNumber][1]){
                client.write("LEFT#");
                if(players[myNumber][2]==3){
                   // path.remove(0);
                }
            }
        }

    }
    ArrayList<node> q;
    int counter;
    node toAdd;
    public void setPath(){
        q=new ArrayList<node>();
        graph=new node[20][20];
        path=new ArrayList<node>();
        if(players[myNumber][4]<50){
            setPathtoL();
        }
        for (int k= 0; k < 20; k++) {
            for (int i = 0; i < 20; i++) {
                graph[k][i]=new node(k,i);
            }
        }
        graph[players[myNumber][0]][players[myNumber][1]].reached=1;
        graph[players[myNumber][0]][players[myNumber][1]].time=0;
        q.add(graph[players[myNumber][0]][players[myNumber][1]]);

        while(q.size()>0){
            if(q.get(0).y!=0 && grid[q.get(0).x][q.get(0).y-1]!=1 && grid[q.get(0).x][q.get(0).y-1]!=2 && grid[q.get(0).x][q.get(0).y-1]!=3 && graph[q.get(0).x][q.get(0).y-1].reached==0){
                graph[q.get(0).x][q.get(0).y-1].reached=1;
                graph[q.get(0).x][q.get(0).y-1].predecesor=graph[q.get(0).x][q.get(0).y];
                graph[q.get(0).x][q.get(0).y-1].time=graph[q.get(0).x][q.get(0).y-1].predecesor.time+1;
                q.add(graph[q.get(0).x][q.get(0).y-1]);
                if(grid[q.get(0).x][q.get(0).y-1]==5){
                    for (int i = 0; i < coins.size(); i++) {
                        if(coins.get(i).x==q.get(0).x && coins.get(i).y==q.get(0).y-1 && coins.get(i).vanish >= time+graph[q.get(0).x][q.get(0).y-1].time){
                            //Add node to path, exit while loop and fil path
                            // write same on following 4 ifs
                            path=new ArrayList<node>();
                            toAdd=graph[q.get(0).x][q.get(0).y-1];
                           // path.add(toAdd);
                            //System.out.println("Add1");
                            while(players[myNumber][0]!=toAdd.x || players[myNumber][1]!=toAdd.y){
                                System.out.println(players[myNumber][0] + "--" + toAdd.x + "        " + players[myNumber][1] + "--" + toAdd.y);
                                path.add(0, toAdd);
                                toAdd=toAdd.predecesor;
                                
                            }
                            return;
                        }
                    }
                }
            }

            if(q.get(0).x!=19 && grid[q.get(0).x+1][q.get(0).y]!=1 && grid[q.get(0).x+1][q.get(0).y]!=2 && grid[q.get(0).x+1][q.get(0).y]!=3 && graph[q.get(0).x+1][q.get(0).y].reached==0){
                graph[q.get(0).x+1][q.get(0).y].reached=1;
                graph[q.get(0).x+1][q.get(0).y].predecesor=graph[q.get(0).x][q.get(0).y];
                graph[q.get(0).x+1][q.get(0).y].time=graph[q.get(0).x+1][q.get(0).y].predecesor.time;
                q.add(graph[q.get(0).x+1][q.get(0).y]);
                if(grid[q.get(0).x+1][q.get(0).y]==5){
                    for (int i = 0; i < coins.size(); i++) {
                        if(coins.get(i).x==q.get(0).x+1 && coins.get(i).y==q.get(0).y && coins.get(i).vanish >= time+graph[q.get(0).x+1][q.get(0).y].time){
                            //Add node to path, exit while loop and fil path
                            // write same on following 4 ifs
                            path=new ArrayList<node>();
                            toAdd=graph[q.get(0).x+1][q.get(0).y];
                            //path.add(toAdd);
                            //System.out.println("Add2");
                            while(players[myNumber][0]!=toAdd.x || players[myNumber][1]!=toAdd.y){
                                //System.out.println(players[myNumber][0] + "--" + toAdd.x + "        " + players[myNumber][1] + "--" + toAdd.y);
                                 path.add(0, toAdd);
                                toAdd=toAdd.predecesor;
                               
                            }
                            return;
                        }
                    }
                }
            }

            if(q.get(0).y!=19 && grid[q.get(0).x][q.get(0).y+1]!=1 && grid[q.get(0).x][q.get(0).y+1]!=2 && grid[q.get(0).x][q.get(0).y+1]!=3 && graph[q.get(0).x][q.get(0).y+1].reached==0){
                graph[q.get(0).x][q.get(0).y+1].reached=1;
                graph[q.get(0).x][q.get(0).y+1].predecesor=graph[q.get(0).x][q.get(0).y];
                graph[q.get(0).x][q.get(0).y+1].time=graph[q.get(0).x][q.get(0).y+1].predecesor.time;
                q.add(graph[q.get(0).x][q.get(0).y+1]);
                if(grid[q.get(0).x][q.get(0).y+1]==5){
                    for (int i = 0; i < coins.size(); i++) {
                        if(coins.get(i).x==q.get(0).x && coins.get(i).y==q.get(0).y+1 && coins.get(i).vanish >= time+graph[q.get(0).x][q.get(0).y+1].time){
                            //Add node to path, exit while loop and fil path
                            // write same on following 4 ifs
                            path=new ArrayList<node>();
                            toAdd=graph[q.get(0).x][q.get(0).y+1];
                            //path.add(toAdd);
                            //System.out.println("Add3");
                            while(players[myNumber][0]!=toAdd.x || players[myNumber][1]!=toAdd.y){
                                //System.out.println(players[myNumber][0] + "--" + toAdd.x + "        " + players[myNumber][1] + "--" + toAdd.y);
                                path.add(0, toAdd);
                                toAdd=toAdd.predecesor;
                                
                            }
                            return;
                        }
                    }
                }
            }

             if(q.get(0).x!=0 && grid[q.get(0).x-1][q.get(0).y]!=1 && grid[q.get(0).x-1][q.get(0).y]!=2 && grid[q.get(0).x-1][q.get(0).y]!=3 && graph[q.get(0).x-1][q.get(0).y].reached==0){
                graph[q.get(0).x-1][q.get(0).y].reached=1;
                graph[q.get(0).x-1][q.get(0).y].predecesor=graph[q.get(0).x][q.get(0).y];
                graph[q.get(0).x-1][q.get(0).y].time=graph[q.get(0).x-1][q.get(0).y].predecesor.time;
                q.add(graph[q.get(0).x-1][q.get(0).y]);
                if(grid[q.get(0).x-1][q.get(0).y]==5){
                    for (int i = 0; i < coins.size(); i++) {
                        if(coins.get(i).x==q.get(0).x-1 && coins.get(i).y==q.get(0).y && coins.get(i).vanish >= time+graph[q.get(0).x-1][q.get(0).y].time){
                            //Add node to path, exit while loop and fil path
                            // write same on following 4 ifs
                            path=new ArrayList<node>();
                            toAdd=graph[q.get(0).x-1][q.get(0).y];
                            //path.add(toAdd);
                            //System.out.println("Add4");
                            while(players[myNumber][0]!=toAdd.x || players[myNumber][1]!=toAdd.y){
                                //System.out.println(players[myNumber][0] + "--" + toAdd.x + "        " + players[myNumber][1] + "--" + toAdd.y);
                                 path.add(0, toAdd);
                                toAdd=toAdd.predecesor;
                                //System.out.println(players[myNumber][0] + " ++ " + toAdd.x + " ++ " + players[myNumber][1] + " ++ " + toAdd.y);
                            }
                            return;
                        }
                    }
                }
            }

            q.remove(0);
        }


    }

    public void setPathtoL(){
        q=new ArrayList<node>();
        graph=new node[20][20];
        path=new ArrayList<node>();
        for (int k= 0; k < 20; k++) {
            for (int i = 0; i < 20; i++) {
                graph[k][i]=new node(k,i);
            }
        }
        graph[players[myNumber][0]][players[myNumber][1]].reached=1;
        graph[players[myNumber][0]][players[myNumber][1]].time=0;
        q.add(graph[players[myNumber][0]][players[myNumber][1]]);

        while(q.size()>0){
            if(q.get(0).y!=0 && grid[q.get(0).x][q.get(0).y-1]!=1 && grid[q.get(0).x][q.get(0).y-1]!=2 && grid[q.get(0).x][q.get(0).y-1]!=3 && graph[q.get(0).x][q.get(0).y-1].reached==0){
                graph[q.get(0).x][q.get(0).y-1].reached=1;
                graph[q.get(0).x][q.get(0).y-1].predecesor=graph[q.get(0).x][q.get(0).y];
                graph[q.get(0).x][q.get(0).y-1].time=graph[q.get(0).x][q.get(0).y-1].predecesor.time+1;
                q.add(graph[q.get(0).x][q.get(0).y-1]);
                if(grid[q.get(0).x][q.get(0).y-1]==5){
                    for (int i = 0; i < life.size(); i++) {
                        if(life.get(i).x==q.get(0).x && life.get(i).y==q.get(0).y-1 && life.get(i).vanish >= time+graph[q.get(0).x][q.get(0).y-1].time){
                            //Add node to path, exit while loop and fil path
                            // write same on following 4 ifs
                            path=new ArrayList<node>();
                            toAdd=graph[q.get(0).x][q.get(0).y-1];
                           // path.add(toAdd);
                            //System.out.println("Add1");
                            while(players[myNumber][0]!=toAdd.x || players[myNumber][1]!=toAdd.y){
                                //System.out.println(players[myNumber][0] + "--" + toAdd.x + "        " + players[myNumber][1] + "--" + toAdd.y);
                                path.add(0, toAdd);
                                toAdd=toAdd.predecesor;

                            }
                            return;
                        }
                    }
                }
            }

            if(q.get(0).x!=19 && grid[q.get(0).x+1][q.get(0).y]!=1 && grid[q.get(0).x+1][q.get(0).y]!=2 && grid[q.get(0).x+1][q.get(0).y]!=3 && graph[q.get(0).x+1][q.get(0).y].reached==0){
                graph[q.get(0).x+1][q.get(0).y].reached=1;
                graph[q.get(0).x+1][q.get(0).y].predecesor=graph[q.get(0).x][q.get(0).y];
                graph[q.get(0).x+1][q.get(0).y].time=graph[q.get(0).x+1][q.get(0).y].predecesor.time;
                q.add(graph[q.get(0).x+1][q.get(0).y]);
                if(grid[q.get(0).x+1][q.get(0).y]==5){
                    for (int i = 0; i < life.size(); i++) {
                        if(life.get(i).x==q.get(0).x+1 && life.get(i).y==q.get(0).y && life.get(i).vanish >= time+graph[q.get(0).x+1][q.get(0).y].time){
                            //Add node to path, exit while loop and fil path
                            // write same on following 4 ifs
                            path=new ArrayList<node>();
                            toAdd=graph[q.get(0).x+1][q.get(0).y];
                            //path.add(toAdd);
                            //System.out.println("Add2");
                            while(players[myNumber][0]!=toAdd.x || players[myNumber][1]!=toAdd.y){
                                //System.out.println(players[myNumber][0] + "--" + toAdd.x + "        " + players[myNumber][1] + "--" + toAdd.y);
                                 path.add(0, toAdd);
                                toAdd=toAdd.predecesor;

                            }
                            return;
                        }
                    }
                }
            }

            if(q.get(0).y!=19 && grid[q.get(0).x][q.get(0).y+1]!=1 && grid[q.get(0).x][q.get(0).y+1]!=2 && grid[q.get(0).x][q.get(0).y+1]!=3 && graph[q.get(0).x][q.get(0).y+1].reached==0){
                graph[q.get(0).x][q.get(0).y+1].reached=1;
                graph[q.get(0).x][q.get(0).y+1].predecesor=graph[q.get(0).x][q.get(0).y];
                graph[q.get(0).x][q.get(0).y+1].time=graph[q.get(0).x][q.get(0).y+1].predecesor.time;
                q.add(graph[q.get(0).x][q.get(0).y+1]);
                if(grid[q.get(0).x][q.get(0).y+1]==5){
                    for (int i = 0; i < life.size(); i++) {
                        if(life.get(i).x==q.get(0).x && life.get(i).y==q.get(0).y+1 && life.get(i).vanish >= time+graph[q.get(0).x][q.get(0).y+1].time){
                            //Add node to path, exit while loop and fil path
                            // write same on following 4 ifs
                            path=new ArrayList<node>();
                            toAdd=graph[q.get(0).x][q.get(0).y+1];
                            //path.add(toAdd);
                            //System.out.println("Add3");
                            while(players[myNumber][0]!=toAdd.x || players[myNumber][1]!=toAdd.y){
                                //System.out.println(players[myNumber][0] + "--" + toAdd.x + "        " + players[myNumber][1] + "--" + toAdd.y);
                                path.add(0, toAdd);
                                toAdd=toAdd.predecesor;

                            }
                            return;
                        }
                    }
                }
            }

             if(q.get(0).x!=0 && grid[q.get(0).x-1][q.get(0).y]!=1 && grid[q.get(0).x-1][q.get(0).y]!=2 && grid[q.get(0).x-1][q.get(0).y]!=3 && graph[q.get(0).x-1][q.get(0).y].reached==0){
                graph[q.get(0).x-1][q.get(0).y].reached=1;
                graph[q.get(0).x-1][q.get(0).y].predecesor=graph[q.get(0).x][q.get(0).y];
                graph[q.get(0).x-1][q.get(0).y].time=graph[q.get(0).x-1][q.get(0).y].predecesor.time;
                q.add(graph[q.get(0).x-1][q.get(0).y]);
                if(grid[q.get(0).x-1][q.get(0).y]==5){
                    for (int i = 0; i < life.size(); i++) {
                        if(life.get(i).x==q.get(0).x-1 && life.get(i).y==q.get(0).y && life.get(i).vanish >= time+graph[q.get(0).x-1][q.get(0).y].time){
                            //Add node to path, exit while loop and fil path
                            // write same on following 4 ifs
                            path=new ArrayList<node>();
                            toAdd=graph[q.get(0).x-1][q.get(0).y];
                            //path.add(toAdd);
                            //System.out.println("Add4");
                            while(players[myNumber][0]!=toAdd.x || players[myNumber][1]!=toAdd.y){
                                //System.out.println(players[myNumber][0] + "--" + toAdd.x + "        " + players[myNumber][1] + "--" + toAdd.y);
                                 path.add(0, toAdd);
                                toAdd=toAdd.predecesor;

                            }
                            return;
                        }
                    }
                }
            }

            q.remove(0);
        }


    }

    public static void main(String[] args) {
        try {
            Thread t = new insocket(7000);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class node{

    public int x;
    public int y;
    public int reached;
    public int discovered;
    public node predecesor;
    public int time;
    public node(int x, int y){
        this.x=x;
        this.y=y;
        this.reached=0;
        this.discovered=0;
        this.predecesor=null;
    }

}
