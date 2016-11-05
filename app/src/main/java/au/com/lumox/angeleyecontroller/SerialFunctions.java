package au.com.lumox.angeleyecontroller;

import android.app.Service;

/**
 * Created by Vignesh on 24/10/16.
 */



import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SerialFunctions extends Service {

    Socket socket;
    DataOutputStream out;

    private final IBinder myBinder = new MyLocalBinder();

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return myBinder;
    }

    @Override
    public void onCreate (){
        //Connect to Socket
        Runnable connect = new connectToSocket();
        new Thread(connect).start();


    }

    @Override
    public void onDestroy (){
        //Connect to Socket
        try {
            socket.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void transmitMessage(String message){
        // Toast.makeText(SomeTest.this, message, Toast.LENGTH_SHORT).show();
        try {
            out.writeBytes(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public class MyLocalBinder extends Binder {
        SerialFunctions getService() {
            return SerialFunctions.this;
        }
    }



    class connectToSocket implements Runnable{
        @Override
        public void run() {
            try {
                socket = new Socket(getString(R.string.ESP_IP_Address),23);
                socket.setTcpNoDelay(true);
                //socket.setPerformancePreferences(0,6,2);
                out = new DataOutputStream(socket.getOutputStream());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}