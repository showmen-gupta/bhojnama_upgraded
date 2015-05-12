package com.apps.bhojnama;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.apps.utility.InternetCheck;


public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle splash) {
		// TODO Auto-generated method stub
		super.onCreate(splash);
		setContentView(R.layout.splash);
		if (!InternetCheck.isNetworkConnected(this)) {
			showDialog();
			return;
		}

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

	private void showDialog() {
		AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog2.setTitle("Message");

		// Setting Dialog Message
		alertDialog2.setMessage("Please check your internet connection");

		// Setting Icon to Dialog
		alertDialog2.setIcon(R.drawable.ic_launcher);

		// Setting Positive "Yes" Btn
		alertDialog2.setPositiveButton("OK",
		        new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		            	dialog.cancel();
		            	finish();// Write your code here to execute after dialog
		               // Toast.makeText(getApplicationContext(),"You clicked on YES", Toast.LENGTH_SHORT).show();
		            }
		        });
		// Setting Negative "NO" Btn
		/*alertDialog2.setNegativeButton("NO",
		        new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		                // Write your code here to execute after dialog
		                Toast.makeText(getApplicationContext(),"You clicked on NO", Toast.LENGTH_SHORT).show();
		                dialog.cancel();
		            }
		        });*/

		// Showing Alert Dialog
		alertDialog2.show();
	}

}
