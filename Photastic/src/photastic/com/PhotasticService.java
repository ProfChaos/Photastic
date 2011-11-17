package photastic.com;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class PhotasticService extends Service
{
	private boolean isRunning;
	private static PhotasticService instance = null;
	

	public boolean isRunning()
	{
		return (instance != null);
	}

	@Override
	public IBinder onBind(Intent arg0) 
	{
		return null;
	}
	
	@Override
	public void onCreate() 
	{
	    super.onCreate();
	    this.instance = this;
	    Toast.makeText(this, "Service created...", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDestroy() 
	{
	    super.onDestroy();
	    Toast.makeText(this, "Service destroyed ...", Toast.LENGTH_LONG).show();
	}
	
	public void onStop()
	{
		Toast.makeText(this, "Service stopped ...", Toast.LENGTH_LONG).show();
		super.stopSelf();	
	}

	public int onStartCommand(Intent intent, int flags, int startId)
	{
	    Toast.makeText(this, "onStartCommand...", Toast.LENGTH_LONG).show();
	    return 1;
	}
	
}

