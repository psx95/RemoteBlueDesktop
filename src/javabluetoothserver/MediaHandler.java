/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javabluetoothserver;

/**
 *
 * @author user
 */
public class MediaHandler {
    public native void pressVolUpKey ();
    public native void pressVolDownKey ();
    public native void releaseVolUpKey();
    public native void releaseVolDownKey();
    public native void pressPlayKey();
    public native void pressFastForwardKey();
    public native void pressPreviousKey();
    public native void releaseFastForwardKey();
    public native void releasePreviousKey();
    //function for the VLC Media Player
    public native void pressSeekForwardKey();//implementation of shift left, shift right
    public native void releaseSeekForwardKey();
    public native void pressSeekBackwardKey();
    public native void releaseSeekBackwardKey();
    
    static  {
          System.setProperty("java.library.path", "C:\\RemoteBlue");
          System.out.println (System.getProperty("java.library.path"));
        
       // System.load("B:\\media.dll");
          System.loadLibrary("media");
    }
}
