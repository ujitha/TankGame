/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.net.*;
import java.io.*;

public class Main
{
    String serverName;
    int port;
    public Main(){
       serverName = "127.0.0.1";
       port = 6000;
    }
   public  void write(String s)
   {
//      String serverName = "162.126.8.2";
//      int port = 6000;
       System.out.println(s);
      try
      {
//         System.out.println("Connecting to " + serverName
//                             + " on port " + port);
         Socket client = new Socket(serverName, port);
//         System.out.println("Just connected to "
//                      + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out =new DataOutputStream(outToServer);
         InputStream inFromServer = client.getInputStream();
         DataInputStream in =
                        new DataInputStream(inFromServer);
         
         for(int i=0;i<s.length();i++){
             out.writeByte(s.charAt(i));
         }

       //out.writeChar(-1);
//         System.out.println("Server says " + in.readUTF());
         client.close();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}