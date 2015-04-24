package com.apps.bhojnama;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;


public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle splash) {
		// TODO Auto-generated method stub
		super.onCreate(splash);

		try {
	        PackageInfo info = getPackageManager().getPackageInfo(
	                "Your package name", 
	                PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.e("Your Tag", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            
	            Toast.makeText(this, "*** " + Base64.encodeToString(md.digest(), Base64.DEFAULT) , Toast.LENGTH_LONG).show();
	            }
	    } catch (NameNotFoundException e) {

	    } catch (NoSuchAlgorithmException e) {

	    }
		setContentView(R.layout.splash);

		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent openintent = new Intent(SplashActivity.this, MainActivity.class);
					startActivity(openintent);
					finish();

				}
			}
		};
		timer.start();
	}

}
