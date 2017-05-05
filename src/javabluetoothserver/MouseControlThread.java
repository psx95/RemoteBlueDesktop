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
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.io.StreamConnection;

/**
 *
 * @author pranav
 */
public class MouseControlThread implements Runnable {

    // thraed to control all the mouse movements 
    
    private StreamConnection streamConnection;
    private DataInputStream dataInputStream;
    private String command="";
    private InputStream inputStream;
    Robot robot;

    
    public MouseControlThread(StreamConnection connection) throws AWTException {
        this.robot = new Robot();        
        // contructor to initialise and esatblish the connection
        streamConnection = connection;
    }

    @Override
    public void run() {                                
        try {
            inputStream = streamConnection.openInputStream();
        } catch (IOException ex) {
            Logger.getLogger(MouseControlThread.class.getName()).log(Level.SEVERE, null, ex);           
        }
        while (true){
                dataInputStream = new DataInputStream(inputStream);
                command = "";
            try {
                command = dataInputStream.readUTF();
                
            } catch (IOException ex) {
                Logger.getLogger(MouseControlThread.class.getName()).log(Level.SEVERE, null, ex);
            }
                float movex=Float.parseFloat(command.split("   ")[0]);//extract movement in x direction
		float movey=Float.parseFloat(command.split("   ")[1]);//extract movement in y direction
		Point point = MouseInfo.getPointerInfo().getLocation(); //Get current mouse position
                float nowx=point.x;
		float nowy=point.y;
                mouseGlide((int)nowx, (int)nowy, (int)(nowx+(int)movex), (int)(nowy + (int)movey), 1, 100);           
        }               
   }
    
     
        public void mouseGlide(int x1, int y1, int x2, int y2, int t, int n) {       
          double dx = (x2 - x1) / ((double) n);
          double dy = (y2 - y1) / ((double) n);
          double dt = t / ((double) n);
          for (int step = 1; step <= n; step++) {
              robot.delay((int) dt);
              robot.mouseMove((int) (x1 + dx * step), (int) (y1 + dy * step));
          }
    }
        
        private void processCommand (String command){            
            if (command.equals("pauseThread")){
                Thread.currentThread().destroy();
            }
                        
            else if (command.contains("   ")){
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
                                mouseGlide((int)nowx, (int)nowy, (int)(nowx+(int)movex), (int)(nowy + (int)movey), 1, 100);
				//robot.mouseMove((int) (nowx+(int)movex),(int)(nowy+(int)movey));//Move mouse pointer to new location
                    
                 }
            }
            else {
                // do nothing
            }
        }
        
}       
    
