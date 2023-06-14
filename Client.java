import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.io.*;

public class Client extends JFrame {
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    private JLabel  heading = new JLabel("Client Area");
    private JTextArea messageArea = new JTextArea();
    private JTextField messageInput = new JTextField();
    private Font font = new Font("Roboto",Font.PLAIN,20);

    public Client(){
     
     try{
        System.out.println("Sending request to server");
         socket = new Socket("127.0.0.1",7777);
         System.out.println("connection done");
        br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out= new PrintWriter(socket.getOutputStream());

        createGUI(); 
        handleEvents();

       startReading();
        //startWriting();
     }catch(Exception e){
        e.printStackTrace();
     }
    }

    private void handleEvents() {
        messageInput.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
              //  System.out.println("Key Released"+e.getKeyCode());
                if(e.getKeyCode()==10){

                //    System.out.println("You have pressed enter");
            String contentToSend=messageInput.getText();
            messageArea.append("Me:"+contentToSend+"\n");
            out.println(contentToSend);
            out.flush();
            messageInput.setText("");
            messageInput.requestFocus();
                }
                
            }
            
        });
    }

    public void createGUI(){
        this.setTitle("Client Messenger");
        this.setSize(600,700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        heading.setFont(font);
        messageArea.setFont(font);
        messageInput.setFont(font);
        heading.setIcon(new ImageIcon("cLogo1.png"));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        messageArea.setEditable(false);
        messageInput.setHorizontalAlignment(SwingConstants.CENTER);
        this.setLayout(new BorderLayout());
        this.add(heading,BorderLayout.NORTH);
        JScrollPane jScrollPane = new JScrollPane(messageArea);
        this.add(jScrollPane,BorderLayout.CENTER);
        this.add(messageInput,BorderLayout.SOUTH);
        this.setVisible(true);

    }

    public void startReading(){
        
        Runnable r1= ()->{

            
            System.out.println("Reading Start...");
            try{
        while(true){
            
            
             String   msg = br.readLine();
            
            if(msg.equals("exit")){
                
                System.out.println("Server Terminated the chat");
                JOptionPane.showMessageDialog(this,"Server Terminated the chat");
                messageInput.setEnabled(false);
                socket.close();
                break;
            }
           // System.out.println("Server "+msg);
           messageArea.append("Server :"+msg+"\n");
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
            while( !socket.isClosed()){
              
                    BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));
                    String content=br1.readLine();
                    out.println(content);
                    out.flush();
                    if(content.equals("exit")){
                        socket.close();
                        break;
                                            }

                
            }
            System.out.println("connection is closed");
        }catch(Exception e){
           e.printStackTrace();
          }

        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("This is Client");
        new Client();
    }
    
}
