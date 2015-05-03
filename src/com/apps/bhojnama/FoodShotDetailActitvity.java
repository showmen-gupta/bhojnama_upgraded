package com.apps.bhojnama;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import android.widget.ImageView;
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
import com.androidquery.AQuery;
import com.apps.adapter.FoodShotCommentsAdapter;
import com.apps.bhojnama.sharedpref.SharedPref;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.datamodel.FoodShotsCommentsInfo;
import com.apps.jsonparser.JsonParser;
import com.apps.utility.ConstantValue;

public class FoodShotDetailActitvity extends Activity implements
		OnClickListener {
	TextView txtCommentTitle;
	TextView txtDetails, title;
	TextView txtCommentDetails;
	TextView txtCommentCount;
	EditText edtTextComment;
	ListView listComment;
	ImageView image;
	Button btnSend;
	FoodShotCommentsAdapter commentsAdapter;
	View headerView;
	int position;

	@Override
	protected void onCreate(Bundle splash) {
		super.onCreate(splash);
		setContentView(R.layout.activity_food_shot_details);
		initView();
		loadData();
		setListener();
		getActionBar().setTitle("Food Shot Details");
	}

	private void setListener() {
		btnSend.setOnClickListener(this);
	}

	private void loadData() {
		position = getIntent().getExtras().getInt("position") - 1;
		txtDetails.setText(BhojNamaSingleton.getInstance()
				.getArrayListFoodShots().get(position).getFoodShotDetails());
		title.setText(BhojNamaSingleton.getInstance().getArrayListFoodShots()
				.get(position).getFoodShotName());
		String img_url = "http://api.bhojnama.com";

		new AQuery(image).image(
				img_url
						+ BhojNamaSingleton.getInstance()
								.getArrayListFoodShots().get(position)
								.getPhotUrl(), true, true, 0,
				R.drawable.demo_food);

		txtCommentCount.setText(BhojNamaSingleton.getInstance()
				.getArrayListFoodShots().get(position).getFoodShotsComments()
				.size()
				+ " Comments");
		commentsAdapter = new FoodShotCommentsAdapter(
				FoodShotDetailActitvity.this, BhojNamaSingleton.getInstance()
						.getArrayListFoodShots().get(position)
						.getFoodShotsComments(), 1);
		listComment.setAdapter(commentsAdapter);
	}

	private void initView() {
		btnSend = (Button) findViewById(R.id.btnSend);
		edtTextComment = (EditText) findViewById(R.id.edtTextComment);
		txtCommentTitle = (TextView) findViewById(R.id.txt_view_title);
		txtCommentDetails = (TextView) findViewById(R.id.txt_view_details);
		listComment = (ListView) findViewById(R.id.list_view_comments);
		headerView = getLayoutInflater().inflate(
				R.layout.header_foodshot_details, null);
		txtDetails = (TextView) headerView.findViewById(R.id.txt_view_details);
		txtCommentCount = (TextView) headerView
				.findViewById(R.id.txt_view_comments_count);
		title = (TextView) headerView.findViewById(R.id.title);
		image = (ImageView) headerView.findViewById(R.id.view_pager_img);
		listComment.addHeaderView(headerView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSend:
			SharedPref sharedPref = new SharedPref(FoodShotDetailActitvity.this);
			if (sharedPref.getLoginStatus().equalsIgnoreCase("1")) {
				postComments();
				Toast.makeText(FoodShotDetailActitvity.this,
						"Your Comment has been posted", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(FoodShotDetailActitvity.this,
						"Please Login First ", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	ProgressDialog progress;

	private void postComments() {
		RequestQueue queue = Volley
				.newRequestQueue(FoodShotDetailActitvity.this);
		StringRequest myReq = new StringRequest(Method.POST,
				ConstantValue.BASE_REVIEW_POST_API
						+ BhojNamaSingleton.getInstance()
								.getArrayListFoodShots().get(position)
								.getFoodShotId() + "/review",
				createMyReqSuccessListener(), createMyReqErrorListener()) {
			String timeStamp = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss")
					.format(Calendar.getInstance().getTime());
			final SharedPref sharedPref = new SharedPref(
					FoodShotDetailActitvity.this);

			protected Map<String, String> getParams()
					throws com.android.volley.AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("title", edtTextComment.getText().toString().trim());
				params.put("detail", edtTextComment.getText().toString().trim());
				params.put("published_on", timeStamp);
				params.put("user_id", sharedPref.getUserID());
				params.put("token", sharedPref.getUserToken());
				params.put("status", "1");
				return params;
			};
		};

		int socketTimeout = 30000;// 30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		myReq.setRetryPolicy(policy);
		queue.add(myReq);

		progress = new ProgressDialog(FoodShotDetailActitvity.this);
		progress.setMessage("Please wait....");
		progress.show();
	}

	private ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(FoodShotDetailActitvity.this,
						"Server Response Error", Toast.LENGTH_SHORT).show();
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

					updateCommentArray();
					Toast.makeText(FoodShotDetailActitvity.this,
							"Your Comment has been posted", Toast.LENGTH_SHORT)
							.show();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		};
	}

	private void updateCommentArray() {
		FoodShotsCommentsInfo newFoodShotComments = new FoodShotsCommentsInfo();
		newFoodShotComments.setAuthorId(BhojNamaSingleton.getInstance()
				.getArrayListFoodShotComments().get(position).getAuthorId());
		newFoodShotComments.setCommentDetails(BhojNamaSingleton.getInstance()
				.getArrayListFoodShotComments().get(position)
				.getCommentDetails());
		newFoodShotComments.setAuthorName(BhojNamaSingleton.getInstance()
				.getArrayListFoodShotComments().get(position).getAuthorName());
		newFoodShotComments.setCommentId(BhojNamaSingleton.getInstance()
				.getArrayListFoodShotComments().get(position).getCommentId());
		newFoodShotComments.setCommentsTitle(BhojNamaSingleton.getInstance()
				.getArrayListFoodShotComments().get(position)
				.getCommentsTitle());

		BhojNamaSingleton.getInstance().getArrayListFoodShots().get(position)
				.getFoodShotsComments().add(0, newFoodShotComments);
		commentsAdapter.notifyDataSetChanged();

	}

}
