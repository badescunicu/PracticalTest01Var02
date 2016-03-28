package ro.pub.cs.systems.eim.practicaltest01var02;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PracticalTest02Var02Service extends Service {

    private ProcessingThread processingThread = null;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int sum = intent.getIntExtra("key_sum", -1);
        processingThread = new ProcessingThread(getApplicationContext(), sum);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
