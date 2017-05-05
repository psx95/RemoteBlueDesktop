/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javabluetoothserver;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import java.awt.Desktop;
import java.io.DataOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.io.StreamConnection;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import jdk.nashorn.internal.parser.JSONParser;
import ui.ControlPanel;

/**
 *
 * @author user
 */

//this thread processes all kinds of inputs recoeved to the server 

public class ProcessConnectionThread implements Runnable {
   

    public static boolean connectionDropped = false;
    private StreamConnection mConnection;
    private DataInputStream dis;
    private DataOutputStream dos;
    private static final int EXIT_CMD = -1;
    public Thread t;
    private String command;
    public static boolean destroyed = false; //boolean to check if the thread is destroyed or not 
    public Robot robot;
    InputStream inputStream;
    OutputStream outputStream;
    //private static final int \
    JTextArea message;
    public ProcessConnectionThread(StreamConnection connection) throws AWTException {
        mConnection = connection;
       // t = new Thread(this);
      //  t.start();
        robot = new Robot();
    }

    ProcessConnectionThread(StreamConnection connection, JTextArea msg) {
        mConnection = connection;
        message=msg;     
        message.setText(message.getText()+"\nProcess thread started");
        
    }
   

    @Override
    public void run() {
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            message.setText("Process thread running");
            inputStream = mConnection.openInputStream();
            outputStream = mConnection.openOutputStream(); // for sending the output 
        } catch (IOException ex) {
            Logger.getLogger(ProcessConnectionThread.class.getName()).log(Level.SEVERE, null, ex);
            message.setText(message.getText()+"\n error opening input stream");
        }
        dis = new DataInputStream(inputStream);
            while (!connectionDropped){           
           // System.out.println ("waiting to recieve input");
           // message.setText(message.getText()+"\nWaiting to recieve input");
           // message.setText(message.getText()+"\nWorking... ");            
                 command="";               
                try {
                    command = dis.readUTF();                   
                    final String command2 = command;
                } catch (IOException ex) {
                    //Logger.getLogger(ProcessConnectionThread.class.getName()).log(Level.SEVERE, null, ex);
                    message.setText(message.getText()+"\n error reading input");
                    connectionDropped = true;
                    break;
                    //Thread.currentThread().interrupt();
                }
//                if (connectionDropped){
//                    message.setText(message.getText()+"\n connection droppped");
//                    //Thread t = Thread.currentThread();                 
//                    break;
//                }
               
                //    System.out.println("the given command is "+command+connectionDropped);    
                    processCommand(command);
                                
            }
           // t.interrupt();
            //message.setText(message.getText()+"\n out of while");
    }
    
     
    private void processSpecialChar (char x){
        try{
            Robot robot = new Robot();
            System.out.println("processing "+x);
            //message.setText(message.getText()+"\nProcessing "+x);
            switch (x){
                        case ':':
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_SEMICOLON);
                            robot.keyRelease(KeyEvent.VK_SEMICOLON);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            break;
                        case ';':
                            robot.keyPress(KeyEvent.VK_SEMICOLON);
                            robot.keyRelease(KeyEvent.VK_SEMICOLON);
                            break;
                        case ',':
                            robot.keyPress(KeyEvent.VK_COMMA);
                            robot.keyRelease(KeyEvent.VK_COMMA);
                            break;
                        case '/':
                            System.out.println ("printing "+x);
                            //message.setText(message.getText()+"\nPrinting"+x);
                            robot.keyPress(KeyEvent.VK_SLASH);
                            robot.keyRelease(KeyEvent.VK_SLASH);
                            break;
                        case '.':
                            System.out.println ("printing "+x);
                            //message.setText(message.getText()+"\nPrinting"+x);
                            robot.keyPress(KeyEvent.VK_PERIOD);
                            robot.keyRelease(KeyEvent.VK_PERIOD);
                            break;
                        case '@':
                            System.out.println ("printing "+x);
                            //message.setText(message.getText()+"\nPrinting"+x);
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_2);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            robot.keyRelease(KeyEvent.VK_2);
                            break;
                        case '$':
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_4);
                            robot.keyRelease(KeyEvent.VK_4);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            break;
                        case '&':
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_7);
                            robot.keyRelease(KeyEvent.VK_7);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            break;
                        case '!':
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_1);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            robot.keyRelease(KeyEvent.VK_1);
                            break;
                        case '#':
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_3);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            robot.keyRelease(KeyEvent.VK_3);
                            break;
                        case '%':
                            //robot.keyPress(KeyEvent.);
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_5);
                            robot.keyRelease(KeyEvent.VK_5);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            break;
                        case '^':
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_6);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            robot.keyRelease(KeyEvent.VK_6);
                            break;
                        case '*':
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_8);
                            robot.keyRelease(KeyEvent.VK_8);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            break;
                        case '(':
                            //robot.keyPress;
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_9);
                            robot.keyRelease(KeyEvent.VK_9);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            break;
                        case ')':
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_0);
                            robot.keyRelease(KeyEvent.VK_0);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            break;
                        case '+':
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_EQUALS);                         
                            robot.keyRelease(KeyEvent.VK_EQUALS);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            break;
                        case '=':
                            robot.keyPress(KeyEvent.VK_EQUALS);
                            robot.keyRelease(KeyEvent.VK_EQUALS);
                            break;
                        case '-':
                            robot.keyPress(KeyEvent.VK_MINUS);
                            robot.keyRelease(KeyEvent.VK_MINUS);
                            break;
                        case '[':
                            robot.keyPress(KeyEvent.VK_OPEN_BRACKET);
                            robot.keyRelease(KeyEvent.VK_OPEN_BRACKET);
                            break;
                        case ']':
                            robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);
                            robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET);
                            break;
                        case '\\':
                            robot.keyPress(KeyEvent.VK_BACK_SLASH);
                            robot.keyRelease(KeyEvent.VK_BACK_SLASH);
                            break;
                        case '"':
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_QUOTE);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            robot.keyRelease(KeyEvent.VK_QUOTE);
                            break;
                        case '\'' :
                            robot.keyPress(KeyEvent.VK_QUOTE);
                            robot.keyRelease(KeyEvent.VK_QUOTE);
                            break;
                        case '?' :
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_SLASH);
                            robot.keyRelease(KeyEvent.VK_SLASH);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            break;
                        case '{':
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_OPEN_BRACKET);
                            robot.keyRelease(KeyEvent.VK_OPEN_BRACKET);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            break;
                        case '}':
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);
                            robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            break;
                        case '_':
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_MINUS);
                            robot.keyRelease(KeyEvent.VK_MINUS);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            break;
                        case '<':
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_COMMA);
                            robot.keyRelease(KeyEvent.VK_COMMA);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            break;
                        case '>':
                            robot.keyPress(KeyEvent.VK_SHIFT);
                            robot.delay(100);
                            robot.keyPress(KeyEvent.VK_PERIOD);
                            robot.keyRelease(KeyEvent.VK_PERIOD);
                            robot.keyRelease(KeyEvent.VK_SHIFT);
                            break;
                    }
        }
        catch (AWTException e){
            System.out.println ("Caught Exception");
            message.setText(message.getText()+"\nCaught Exception");
            e.printStackTrace();
        }
    }
    private void typeWord (String word){
        try{
            Robot robot = new Robot();
        if (word.length() == 1){
            char x = word.charAt(0);
            if (Character.isLetter(x)){
                if (Character.isUpperCase(x)){
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.delay(100);
            }
            robot.keyPress(Character.toUpperCase(x));
            robot.keyRelease(Character.toUpperCase(x));
            if (Character.isUpperCase(x)){
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
            }
            else if (Character.isWhitespace(x)){
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            }
            else if (Character.isDigit(x)){
                robot.keyPress(x);
                robot.keyRelease(x);
            }
            else{
                processSpecialChar(x);
            }
        }
        else {
            for (int i=0;i<word.length();i++){
                char x = word.charAt(i);
                if (Character.isLetter(x)){
                    if (Character.isUpperCase(x)){
                        robot.keyPress(KeyEvent.VK_SHIFT);
                        robot.delay(100);
                    }
                    robot.keyPress(Character.toUpperCase(x));
                    robot.keyRelease(Character.toUpperCase(x));
                    if (Character.isUpperCase(x)){
                        robot.keyRelease(KeyEvent.VK_SHIFT);
                    }
                }
                else if (Character.isWhitespace(x)){
                    robot.keyPress(KeyEvent.VK_SPACE);
                    robot.keyRelease(KeyEvent.VK_SPACE);
                }
                else if (Character.isDigit(x)){
                    robot.keyPress(x);
                    robot.keyRelease(x);
                }
                else{
                    processSpecialChar(x);
                }
            }
            
        }
        }
        catch (AWTException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void mouseGlide(int x1, int y1, int x2, int y2, int t, int n) {
    try {
        Robot r = new Robot();
        double dx = (x2 - x1) / ((double) n);
        double dy = (y2 - y1) / ((double) n);
        double dt = t / ((double) n);
        for (int step = 1; step <= n; step++) {
            r.delay((int) dt);
            r.mouseMove((int) (x1 + dx * step), (int) (y1 + dy * step));
        }
    } catch (AWTException e) {
        e.printStackTrace();
    } 
//    catch (InterruptedException ex) {
//            Logger.getLogger(ProcessConnectionThread.class.getName()).log(Level.SEVERE, null, ex);
//        }
}
    private void processCommand (String command){
        try {
            Runtime r = Runtime.getRuntime();
            Robot robot = new Robot ();          
             if (command.contains("   ")){
                 if (command.contains("mouse")){
                     int wheel = Integer.parseInt(command.split("mouse")[1]);
                     if (wheel > 0)
                     robot.mouseWheel(1 * (1 + 50 / 100));
                     else
                         robot.mouseWheel(-1);
                 }
                 else{
                                float movex=Float.parseFloat(command.split("   ")[0]);//extract movement in x direction
				float movey=Float.parseFloat(command.split("   ")[1]);//extract movement in y direction
				Point point = MouseInfo.getPointerInfo().getLocation(); //Get current mouse position
				float nowx=point.x;
				float nowy=point.y;
                                //starting a new thread here
                               // mouseGlide((int)nowx, (int)nowy, (int)(nowx+(int)movex), (int)(nowy + (int)movey), 1, 100);
				//robot.mouseMove((int) (nowx+(int)movex),(int)(nowy+(int)movey));//Move mouse pointer to new location
                    
                                robot.setAutoDelay(5);
                                robot.mouseMove((int)(nowx+movex), (int)(nowy+movey));
                 }
               }
             else if (command.equals ("pauseThread")){
                 destroyed = true;
                 Thread.currentThread().destroy();                 
                 System.out.println ("process thread destroyed by the user");
             }
             else if (command.equals("taskview")){
                 robot.keyPress(KeyEvent.VK_WINDOWS);
                 robot.delay(100);
                 robot.keyPress(KeyEvent.VK_TAB);
                 robot.keyRelease(KeyEvent.VK_TAB);
                 robot.keyRelease(KeyEvent.VK_WINDOWS);
             }
             else if (command.equals("doubletap")){
//                 robot.mouseRelease(InputEvent.BUTTON1_MASK);
//                 robot.mousePress(InputEvent.BUTTON1_MASK);
//                 robot.mouseRelease(InputEvent.BUTTON1_MASK);
//                 robot.delay(50);
//                 robot.mousePress(InputEvent.BUTTON1_MASK);         
//                 robot.mousePress(InputEvent.BUTTON1_MASK);
                   robot.mousePress(InputEvent.BUTTON1_MASK);
             }
             else if (command.equals("mouserelease")){
                 robot.mouseRelease(InputEvent.BUTTON1_MASK);
                 robot.delay(50);
                 robot.mousePress(InputEvent.BUTTON1_MASK);
                 robot.mouseRelease(InputEvent.BUTTON1_MASK);
             }
             else if (command.equals("backspace")){
                 robot.keyPress(KeyEvent.VK_BACK_SPACE);
                 robot.keyRelease(KeyEvent.VK_BACK_SPACE);
             }
             else if (command.equals("enter")){
                 robot.keyPress(KeyEvent.VK_ENTER);
                 robot.keyRelease(KeyEvent.VK_ENTER);
             }
             else if (command.equals("STARTTHREAD")){
                 //start a new thread to send results to android 
                 //pass the current StreamConnection instance and the JTEXTarea instance
                 //open the output Stream of the current Stream Connection
                 dos = new DataOutputStream(outputStream);
                 // these are the default directories 
                 dos.writeUTF("Root Directory\nC:\\\nDirectory");
                 dos.writeUTF("Root Directory\nD:\\\nDirectory");
                 dos.writeUTF("END_OF_REQUEST_REACHED"); //command so that the client stops listenning any further.f
             }
             else if (command.contains("keyboard")){
                 // keyboard input events           
                 if (command.equals("keyboard")){
                     command +=" ";
                 }
                 if (command.length()>8){
                     String word="";
                     word = command.substring(8);
                     //type the word by simulating keystrokes 
                     typeWord(word);
                 }
             }
             else if (command.equals("lmb")||command.equals("rmb")){
                 if (command.equals("lmb")){
                    robot.mousePress(InputEvent.BUTTON1_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                 }
                 else{
                    robot.mousePress(InputEvent.BUTTON3_MASK);
                    robot.mouseRelease(InputEvent.BUTTON3_MASK);
                 }
             }
             else if (command.equals("lmb_press")){
                 robot.mousePress(InputEvent.BUTTON1_MASK);
             }
             else if (command.equals("lmb_release")){
                 robot.mouseRelease(InputEvent.BUTTON1_MASK);
             }
             else if (command.equals("longClickUp")){
                 robot.mouseRelease(InputEvent.BUTTON1_MASK);
             }
             else if (command.contains("power")){
                switch (command) {
                    case "power_shutdown":
                        r.exec("shutdown -s -f -t 05");
                        break;
                    case "power_restart":
                        r.exec("Shutdown.exe -r -t 05");
                        break;
                    case "power_lock":
                        r.exec("rundll32.exe user32.dll, LockWorkStation");
                        break;
                    case "power_hibernate":
                        r.exec("rundll32.exe PowrProf.dll,SetSuspendState");
                        break;
                }
             }
             else if (command.contains("explorer")){
                 r.exec(command);
             }
             else if (command.equals("volup_press"))
             {
                 //new A().vol_up();
                new MediaHandler().pressVolUpKey();
             }
             else if (command.equals("voldown_press")){
                 new MediaHandler().pressVolDownKey();
             }
             else if (command.equals("volup_release")){
                 new MediaHandler().releaseVolUpKey();
             }
             else if (command.equals("voldown_release")){
                 new MediaHandler().releaseVolDownKey();
             }
             else if (command.equals("play_press")){
                 new MediaHandler().pressPlayKey();
             }
             else if (command.equals("play_release")){
                 //do nothing
             }
             else if (command.equals("fast_forward_press")){
                 new MediaHandler().pressFastForwardKey();
             }
             else if (command.equals("fast_forward_release")){
                 new MediaHandler().releaseFastForwardKey();
             }
             else if (command.equals("fast_reverse_press")){
                 new MediaHandler().pressPreviousKey();
             }
             else if (command.equals("fast_reverse_release")){
                 new MediaHandler().releasePreviousKey();
             }
             else if (command.equals("seek_forward_press")){
                 //new MediaHandler().pressSeekForwardKey();
                 robot.keyPress(KeyEvent.VK_SHIFT);
                 robot.delay(20);
                 robot.keyPress(KeyEvent.VK_RIGHT);
             }
             else if (command.equals("seek_forward_release")){
                 //new MediaHandler().releaseSeekForwardKey();
                 robot.keyRelease(KeyEvent.VK_RIGHT);
                 robot.keyRelease(KeyEvent.VK_SHIFT);
             }
             else if (command.equals("seek_backward_press")){
                 //new MediaHandler().pressSeekBackwardKey();
                 robot.keyPress(KeyEvent.VK_SHIFT);
                 robot.delay(20);
                 robot.keyPress(KeyEvent.VK_LEFT);
             }
             else if (command.equals("seek_backward_release")){
                 //new MediaHandler().releaseSeekBackwardKey();
                 robot.keyRelease(KeyEvent.VK_SHIFT);
                 robot.keyRelease(KeyEvent.VK_LEFT);
             }
             else if (command.contains("explore")){                   
                 //The Desktop class allows a Java application to launch associated applications registered
                 //on the native desktop to handle a URI or a file.
                 File folder = new File (command.substring(7,command.length()));              
                 File files[] = folder.listFiles();
                 String fileOrDirectory="";
                try{
                     for (File file : files){ 
                     if (file.isFile()){
                         fileOrDirectory = "file";
                         // execute the file 
                         // try to open the file display a download option in the android app
                        // Desktop dt = Desktop.getDesktop();   
                        // dt.open(file);
                       
                     }
                     else{
                         fileOrDirectory = "directory";
                     }
                     dos.writeUTF(file.getName()+"\n"+file.getAbsolutePath()+"\n"+fileOrDirectory);
                 }
                }
                catch (NullPointerException e){
                 System.out.println ("Printing end of request...");
                 dos.writeUTF("END_OF_REQUEST_REACHED");
                }
                 
                 System.out.println ("Printing end of request...");
                 dos.writeUTF("END_OF_REQUEST_REACHED");
             }
             else if (command.contains("open")){
                 File file = new File(command.substring(4,command.length()));
                 Desktop d = Desktop.getDesktop();
                 try{
                    d.open(file);
                 }
                 catch (IOException e){
                     System.out.println("UNABLE TO OPEN FILE");
                 }
             }
             else if (command.equals("LASER_ON")){
                                      
                   // robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    robot.keyPress(KeyEvent.VK_CONTROL);      
                    robot.mousePress(InputEvent.BUTTON1_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    robot.delay(5);
                    robot.mousePress(InputEvent.BUTTON1_MASK);
             }
             else if (command.equals("LASER_OFF")){
                 robot.mouseRelease(InputEvent.BUTTON1_MASK);
                 robot.keyRelease(KeyEvent.VK_CONTROL);
             }
             else if (command.equals("presenatation_next_slide")){
                 // press the right arrow key
                 robot.keyPress(KeyEvent.VK_RIGHT);
                 robot.keyRelease(KeyEvent.VK_RIGHT);
             }
             else if (command.equals("presenation_back_slide")){
                 // press the left arrow key
                 robot.keyPress(KeyEvent.VK_LEFT);
                 robot.keyRelease(KeyEvent.VK_LEFT);
             }
             else if (command.equals("presentation_start_slideshow")){
                 robot.keyPress(KeyEvent.VK_SHIFT);
                 robot.delay(100);
                 robot.keyPress(KeyEvent.VK_F5);
                 robot.keyRelease(KeyEvent.VK_F5);
                 robot.keyRelease(KeyEvent.VK_SHIFT);
             }
             else if (command.equals("presentation_pause_slide_show")){
                 robot.keyPress(KeyEvent.VK_ESCAPE);
                 robot.keyRelease(KeyEvent.VK_ESCAPE);
             }
             else if (command.equals("presenation_toggle_annotation")){
                 robot.keyPress(KeyEvent.VK_CONTROL);
                 robot.delay(100);
                 robot.keyPress(KeyEvent.VK_P);
                 robot.keyRelease(KeyEvent.VK_P);
                 robot.keyRelease(KeyEvent.VK_CONTROL);
             }
        }
        catch (AWTException | IOException e){
            {
                e.printStackTrace();
                System.out.println ("Error parsing the command");
                message.setText(message.getText()+"\nError parsing the command");
                try {
                    dos.writeUTF("END_OF_REQUEST_REACHED"); // in case any error occours, send an end of request.
                } catch (IOException ex) {
                    Logger.getLogger(ProcessConnectionThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}