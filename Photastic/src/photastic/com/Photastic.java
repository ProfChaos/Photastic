package photastic.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Photastic extends Activity {
    /** Called when the activity is first created. */
	
	public static final String PREFS_NAME = "details";
	private String key;
	Context con;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        key = settings.getString("key", "");
        con = getApplicationContext();

    	String response;
        if(key != "")
        {
            setContentView(R.layout.register);        	
        }else {
        	response = auth(key);
        	if(response == "1")
        	{
        		setContentView(R.layout.dashboard);
        	}else {
                setContentView(R.layout.login);
        	}
        }
    }
    protected void onStop()
    {
    	super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
       SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("key", key);

        // Commit the edits!
        editor.commit();
    }
    public void login()
    {
       EditText username = (EditText) findViewById (R.id.username);
       EditText password = (EditText) findViewById (R.id.password);  
    }
    public void register(View view)
    {
    	String response = null,
    			username = null,
    			email = null,
    			pswrd = null,
    			repswrd = null;
    	EditText mail = (EditText)findViewById(R.id.email);
    	EditText usernm = (EditText)findViewById(R.id.username);
    	EditText pass = (EditText)findViewById(R.id.password);
    	EditText repass = (EditText)findViewById(R.id.repassword);
    	username = usernm.getEditableText().toString();
    	email = mail.getEditableText().toString();
    	pswrd = pass.getEditableText().toString();
    	repswrd = repass.getEditableText().toString();
    	
        // Add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("password", pswrd));
        
        response = post("auth", nameValuePairs);
        if(response == "1")
        {
        	setContentView(R.layout.login);
        }else {
        	Toast.makeText(con, "Something went wrong on the server!", Toast.LENGTH_LONG).show();
        }
    }
    private String auth(String s)
    {
        String response = null;

        String[] sp = s.split(",");
        String mail = (String) sp[0], auth = (String) sp[1];

        // Add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("email", mail));
        nameValuePairs.add(new BasicNameValuePair("auth", auth));
        
        response = post("auth", nameValuePairs);
    	return response;
    }
    public String post(String page, List<NameValuePair> nameValuePairs)
	{
	    String response = null;
		HttpClient httpclient = new DefaultHttpClient();
		ResponseHandler <String> res = new BasicResponseHandler();
		HttpPost httppost = new HttpPost("http://play.weheartgaming.com/"+page);
		try
		{
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost, res);
		} catch (ClientProtocolException e) {	
		}catch (IOException io)
		{
			
		}
		return response;
	}
}