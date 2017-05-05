/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javabluetoothserver;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.UUID;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
/**
 * First
 *
 * @author user
 */
public class WaitThread implements Runnable {
    
    javax.swing.JTextArea message;
    public Thread t;
    Thread processThread, mouseThread;
    String url;
    LocalDevice local,local2;
    boolean firstTime = true;
    boolean over = false;
    StreamConnectionNotifier notifier;
    StreamConnection connection = null;

    public WaitThread (javax.swing.JTextArea jLabel1){
        message=jLabel1;
        //do not start a thread in constructor 
        //t=new Thread(this);
        //t.start();
        message.setText("Running");
    }
    
    @Override
    public void run() {
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            local2 = LocalDevice.getLocalDevice();
        } catch (BluetoothStateException ex) {
            Logger.getLogger(WaitThread.class.getName()).log(Level.SEVERE, null, ex);
            message.setText(message.getText()+"\n Error Occoured While starting the process... \n Is the Bluetooth Switched On ?");
        }
        message.setText("Running");
        over = false;
        waitForConnection();
        message.setText(message.getText()+"\n"+ProcessConnectionThread.connectionDropped);
        message.setText(message.getText()+"\n Wait thread is over ");        
    }
    
//    private void setup2 (){
//        try {
//            if (!LocalDevice.isPowerOn())
//            {
//                local2 = LocalDevice.getLocalDevice();
//            } // get the local bluetooth device
//        } catch (BluetoothStateException ex) {
//            Logger.getLogger(WaitThread.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println ("get local device error");
//        }
//        System.out.println ("got local device");
//        message.setText(message.getText()+"\nGot Local Device");
//        try {
//            if (!LocalDevice.isPowerOn())
//            {
//                local2.setDiscoverable(DiscoveryAgent.GIAC);
//            }//make the device discoverable 
//        } catch (BluetoothStateException ex) {
//            Logger.getLogger(WaitThread.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println ("set discoverable error");
//        }
//        System.out.println ("Device set discoverable");
//        message.setText(message.getText()+"\n Device set discoverable");
//        UUID uuid = new UUID ("04c6093b00001000800000805f9b34fb", false);//generate a UUID for the service 
//        System.out.println ("UUID generated");
//        message.setText(message.getText()+"\nUUID generated");
//        String url = "btspp://localhost:"+uuid.toString()+";name=RemoteDevice";
//        try {
//            notifier = (StreamConnectionNotifier)Connector.open(url);// open a Stream Connection            
//        } catch (IOException ex) {
//            Logger.getLogger(WaitThread.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println ("error openeing stream");
//        }
//        System.out.println ("STream connection started");
//        message.setText(message.getText()+"\nStream Connection Started");
//    }
    
    private void setup ()
    {
        
        //setup the server to listen for incoming connections
        try {
            local = LocalDevice.getLocalDevice(); // get the local bluetooth device
            System.out.println ("got local device");
            message.setText(message.getText()+"\nGot Local Device");
            try{                
            // GIAC- General Inquiry Access Code    
            local.setDiscoverable(DiscoveryAgent.GIAC);//make the device discoverable 
            // this is used to specify the type of inquiry to complete or respond to 
            System.out.println ("Device set discoverable");
            message.setText(message.getText()+"\n Device set discoverable");
            }
            catch(Exception ex)
            {   
                message.setText(message.getText()+"\ndiscoverable error");
            }
            UUID uuid = new UUID ("04c6093b00001000800000805f9b34fb", false);//generate a UUID for the service 
            System.out.println ("UUID generated");
            message.setText(message.getText()+"\nUUID generated");
            url = "btspp://localhost:"+uuid.toString()+";name=RemoteDevice";
            notifier = (StreamConnectionNotifier)Connector.open(url);// open a Stream Connection             
            System.out.println ("STream connection started");
            message.setText(message.getText()+"\nStream Connection Started");
        }
        catch (Exception e){
            System.out.println ("error occoured.");
            return;
        }
    }
    
    
    private void waitForConnection (){
        if (firstTime)
        {
            setup(); 
            firstTime = false;
        }
        while (true){
            try {
                System.out.println ("waiting for incoming connection");
                message.setText(message.getText()+"\nWaiting for incoming connection");                
                connection = notifier.acceptAndOpen();                               
                System.out.println ("starting process thread");
                processThread = new Thread(new ProcessConnectionThread(connection, message)); 
               // mouseThread = new Thread (new MouseControlThread(connection));
                //thread.start not working
                //Thread process = new Thread(processThread)              
                processThread.start();              
                processThread.join(); // stops the currently working thread to sto executing untill process thread completes its task              
                System.out.println ("process thread destroyed:"+ProcessConnectionThread.destroyed);
                over = true;
                connection.close();            
                message.setText(message.getText()+"\n process thread Over.");            
               // notifier = (StreamConnectionNotifier) Connector.open(url);                
                System.out.println ("\n the process thread is over ");
                
            }
            catch (Exception e){
                System.out.println ("wait thread out of while ");
                return;
            }
        }
        
    }
    
    public void stopProcess()
    {
        processThread.destroy();
    }
}
