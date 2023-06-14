import java.io.*;
import java.net.*;


import javax.swing.*;
public class Server extends JFrame{
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
   


    public Server(){
        try{
            server= new ServerSocket(7777);
            System.out.println("Server is ready to accept connection");
            System.out.println("waiting for connection");
         socket=   server.accept();
         br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
         out= new PrintWriter(socket.getOutputStream());
         startReading();
         startWriting();
       

        }
        catch(Exception e){
     e.printStackTrace();
        }
    
    }
    
   
    public void startReading(){
        
        Runnable r1= ()->{
            
            System.out.println("Reading Start...");
            try {
        while(true){
            
           
             String   msg = br.readLine();
            
            if(msg.equals("exit")){
                System.out.println("Client Terminated the chat");
                break;
            }
            System.out.println("client :"+msg);
       
    }
}catch(Exception e){
    System.out.println("connection is closed");
}
        };
        new Thread(r1).start();
        
    
    }
    public void startWriting(){
        Runnable r2= ()->{
            System.out.println("writer started");
            try{
            while(true && !socket.isClosed()){
              
                    BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));
                    String content=br1.readLine();
                    out.println(content);
                    out.flush();

                    if(content.equals("exit")){
socket.close();
break;
                    }
                   
               
            }
        }catch(Exception e){
            System.out.println("connection is closed");
          }
          System.out.println("connection is closed");
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("This is server going to start server");
        new Server();
    }
}