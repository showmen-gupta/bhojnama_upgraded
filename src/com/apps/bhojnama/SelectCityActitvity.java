package com.apps.bhojnama;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apps.adapter.FoodShotCommentsAdapter;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.datamodel.FoodShotsCommentsInfo;
import com.apps.jsonparser.JsonParser;
import com.apps.utility.ConstantValue;


public class SelectCityActitvity extends Activity implements OnClickListener{
	TextView txtCommentTitle;
	TextView txtDetails;
	TextView txtCommentDetails;
	TextView txtCommentCount;
	EditText edtTextComment;
	ListView listViewCity;
	Button btnSend;
	FoodShotCommentsAdapter commentsAdapter;
	View headerView;
	int position;
	@Override
	protected void onCreate(Bundle splash) {
		super.onCreate(splash);
		setContentView(R.layout.activity_select_city);
		//initView();
		//loadData();
		//setListener();
	}

	private void setListener() {
		btnSend.setOnClickListener(this);
	}

	private void loadData() {
		//position = getIntent().getExtras().getInt("position") - 1;
		//txtDetails.setText(BhojNamaSingleton.getInstance().getArrayListFoodShots().get(position).getFoodShotDetails());
		//txtCommentCount.setText(BhojNamaSingleton.getInstance().getArrayListFoodShots().get(position).getFoodShotsComments().size() + " Comments");
		commentsAdapter = new FoodShotCommentsAdapter(SelectCityActitvity.this, BhojNamaSingleton.getInstance().getArrayListFoodShots().get(position).getFoodShotsComments(), 1);
		listViewCity.setAdapter(commentsAdapter);
	}

	private void initView() {
		btnSend = (Button) findViewById(R.id.btnSend);
		edtTextComment = (EditText) findViewById(R.id.edtTextComment);
		txtCommentTitle = (TextView) findViewById(R.id.txt_view_title);
		txtCommentDetails = (TextView) findViewById(R.id.txt_view_details);
		listViewCity = (ListView) findViewById(R.id.list_view_comments);
		headerView = getLayoutInflater().inflate(R.layout.header_foodshot_details, null);
		txtDetails = (TextView) headerView.findViewById(R.id.txt_view_details);
		txtCommentCount = (TextView) headerView.findViewById(R.id.txt_view_comments_count);
		listViewCity.addHeaderView(headerView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case  R.id.btnSend:
			postComments();
			break;
		default:
			break;
		}
	}

	ProgressDialog progress;
	private void postComments() {
		RequestQueue queue = Volley.newRequestQueue(SelectCityActitvity.this);
		StringRequest myReq = new StringRequest(Method.POST, ConstantValue.BASE_REVIEW_POST_API + "1" + "/review" , createMyReqSuccessListener(), createMyReqErrorListener()) {

			protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("title", edtTextComment.getText().toString().trim());
				params.put("detail", edtTextComment.getText().toString().trim());
				params.put("published_on", "Today");
				params.put("status", "1");
				return params;
			};
		};
        
        int socketTimeout = 30000;//30 seconds - change to what you want
    	RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    	myReq.setRetryPolicy(policy);
        queue.add(myReq);
        
        progress = new ProgressDialog(SelectCityActitvity.this);
        progress.setMessage("Please wait....");
        progress.show();
	}

	private ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	Toast.makeText(SelectCityActitvity.this, "Server Response Error", Toast.LENGTH_SHORT).show();
            }
        };
	}

	private Listener<String> createMyReqSuccessListener() {
		return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            	progress.dismiss();
            	Log.e("Login Test", "*****" + response);
            	try {
            		int status = JsonParser.parseLoginInfo(response);
					if (status == 1) {
						updateCommentArray();
						Toast.makeText(SelectCityActitvity.this, "Your review has been posted", Toast.LENGTH_SHORT).show();
					
					} else {
						Toast.makeText(SelectCityActitvity.this, "Username or password is not correct", Toast.LENGTH_SHORT).show();
					} 
					
				} catch (Exception e) {
					e.printStackTrace();
				}
            	
            }

        };
	}
	
	private void updateCommentArray() {
		FoodShotsCommentsInfo newFoodShotComments = new FoodShotsCommentsInfo();
		newFoodShotComments.setAuthorId(1);
		newFoodShotComments.setCommentDetails(edtTextComment.getText().toString().trim());
		newFoodShotComments.setAuthorName("Showmen");
		newFoodShotComments.setCommentId(1);
		newFoodShotComments.setCommentsTitle("Test");
		newFoodShotComments.setPublishDate("");
		
		BhojNamaSingleton.getInstance().getArrayListFoodShots().get(position).getFoodShotsComments().add(0, newFoodShotComments);
		commentsAdapter.notifyDataSetChanged();
		
	}
	

}
