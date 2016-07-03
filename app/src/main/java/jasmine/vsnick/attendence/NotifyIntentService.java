package jasmine.vsnick.attendence;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by vsnick on 02-07-2016.
 */
public class NotifyIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public NotifyIntentService() {
        super(NotifyIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Toast.makeText(getApplicationContext(),"Service Started",Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(getApplicationContext(), FillActivity.class);
        Calendar calendar = Calendar.getInstance();
        String date = calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR);
        Log.d("vsn", "onHandleIntent: "+date);
        myIntent.putExtra("Date",date);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,myIntent,0);
        Notification.Builder builder =
                new Notification.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Update Attendence")
                .setTicker("Notification")
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setContentText("Touch to update")
                .setDefaults(Notification.DEFAULT_SOUND |
                        Notification.DEFAULT_VIBRATE)
                .setSound(
                        RingtoneManager.getDefaultUri(
                                RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setLights(Color.RED, 0, 1);
        String svc = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager
                = (NotificationManager)getSystemService(svc);
        int NOTIFICATION_REF = 1;
        Notification notification = builder.getNotification();
        notificationManager.notify(NOTIFICATION_REF, notification);
        stopSelf();
    }
}
