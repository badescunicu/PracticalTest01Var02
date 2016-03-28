package ro.pub.cs.systems.eim.practicaltest01var02;

import android.content.Context;
import android.content.Intent;

import java.util.Date;

/**
 * Created by nicu on 28.03.2016.
 */
public class ProcessingThread extends Thread {
    Context context;
    private int sum;

    public ProcessingThread(Context context, int sum) {
        this.context = context;
        this.sum = sum;
    }

    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        Intent intent = new Intent("broadcast_message_action");
        String message = new Date(System.currentTimeMillis()).toString() + " and result is: " + sum;
        intent.putExtra("broadcast_message", message);
        context.sendBroadcast(intent);
    }
}
