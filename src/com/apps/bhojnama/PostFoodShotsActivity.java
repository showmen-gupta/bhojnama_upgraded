package com.apps.bhojnama;

import java.io.File;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.BitmapAjaxCallback;
import com.apps.bhojnamainfo.BhojNamaSingleton;
import com.apps.customviews.CustomProgressDialog;
import com.apps.datamodel.FoodShotsInfo;
import com.apps.jsonparser.OnImageUploadComplete;
import com.apps.jsonparser.UploadImageAsyncTask;

public class PostFoodShotsActivity extends Activity implements OnClickListener{
	public Dialog dialogUploadImage;
	private Uri mImageCaptureUri;
	private ImageView imgViewPick;
	private Button btnMakeFoodShot;
	ProgressBar progBarHottestList;
	private EditText editTextTitle, editTextDescription;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_GALLERY = 2;
	String  selectedImagePath = null;
	@Override
	protected void onCreate(Bundle splash) {
		super.onCreate(splash);
		setContentView(R.layout.activity_make_food_shots);
		getActionBar().setTitle("Post Food Shots");
		initViews();
	}
	
	private void initViews() {
		imgViewPick = (ImageView) findViewById(R.id.image_view_btn_pick);
		imgViewPick.setOnClickListener(this);
		btnMakeFoodShot = (Button) findViewById(R.id.btn_make_food_shots);
		btnMakeFoodShot.setOnClickListener(this);
		editTextTitle = (EditText) findViewById(R.id.text_view_title);
		editTextDescription = (EditText) findViewById(R.id.text_view_description);
		progBarHottestList = (ProgressBar) findViewById(R.id.progBarHottestList);
		// TODO Auto-generated method stub
		
	}

	private void initUploadImageOptionUI() {
		dialogUploadImage = new Dialog(this, R.style.drawerImageUploadStyle);
		Window window = dialogUploadImage.getWindow();
		window.setGravity(Gravity.CENTER);
		dialogUploadImage.setContentView(R.layout.upload_image_option_dialog);
		dialogUploadImage.setCanceledOnTouchOutside(true);
		dialogUploadImage.setCancelable(true);

		dialogUploadImage.getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		dialogUploadImage.getWindow().setBackgroundDrawable( new ColorDrawable(android.graphics.Color.TRANSPARENT));

		final Button btnCamera = (Button) dialogUploadImage.findViewById(R.id.btn_post_cam);
		final Button btnGallary = (Button) dialogUploadImage.findViewById(R.id.btn_post_gallary);

		btnCamera.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "/bhojnama_pic_"
							+ String.valueOf(System.currentTimeMillis()) + ".jpg"));
					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
					intent.putExtra("return-data", true);
					startActivityForResult(intent, PICK_FROM_CAMERA);
				} catch (ActivityNotFoundException e) {
					e.printStackTrace();
				}
				
				dialogUploadImage.dismiss();
			}
		});

		btnGallary.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
				dialogUploadImage.dismiss();
			}
		});
		
		dialogUploadImage.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		AQuery aq = new AQuery(PostFoodShotsActivity.this);
		BitmapAjaxCallback bmCallBack = new BitmapAjaxCallback();
		switch (requestCode) {
			case PICK_FROM_CAMERA:
				selectedImagePath = mImageCaptureUri.getPath().toString();
				bmCallBack.url(selectedImagePath).targetWidth(300).rotate(true);
				bmCallBack.memCache(true);
				bmCallBack.fileCache(true);
				aq.id(imgViewPick).image(bmCallBack);
				break;
	
			case PICK_FROM_GALLERY:
				mImageCaptureUri = data.getData();
				selectedImagePath = mImageCaptureUri.getPath().toString();
				
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(mImageCaptureUri, filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

				// the path where the image is located is stored in string variable
				selectedImagePath = cursor.getString(columnIndex);
				cursor.close();
				
				Log.e("GALLERY IMAGE", "****" + selectedImagePath);
				
				bmCallBack.url(selectedImagePath).targetWidth(300).rotate(true);
				bmCallBack.memCache(true);
				bmCallBack.fileCache(true);
				aq.id(imgViewPick).image(bmCallBack);
			
				break;
	
	//		case CROP_FROM_CAMERA:
	//			
	//			break;
	//		}
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.image_view_btn_pick) {
			initUploadImageOptionUI();
		} else if (v.getId() == R.id.btn_make_food_shots) {
			postFoodShot();
		}
		
	}
	ProgressDialog progress;
	private void postFoodShot() {
		progress = new CustomProgressDialog(this);
		progress.show();
		
		new UploadImageAsyncTask(this, new OnImageUploadComplete() {
			@Override
			public void OnResponse(String uploadedPhotoName) {
				progress.dismiss();
				Toast.makeText(PostFoodShotsActivity.this, PostFoodShotsActivity.this.getResources().getString(R.string.post_msg), Toast.LENGTH_SHORT).show();
				Log.i("Upload Response", "====" + uploadedPhotoName);
				resetInputText();
			}
		}, true).execute(editTextTitle.getText().toString(), editTextDescription.getText().toString(), selectedImagePath);
	}
	
	private void resetInputText() {
		FoodShotsInfo newFoodShotsInfo = new FoodShotsInfo();
		newFoodShotsInfo.setFoodShotName(editTextTitle.getText().toString().trim());
		newFoodShotsInfo.setFoodShotDetails(editTextDescription.getText().toString().trim());
		BhojNamaSingleton.getInstance().getArrayListFoodShots().add(newFoodShotsInfo);
		
		//BhojNamaSingleton.getInstance().getArrayListFoodShots().get(position).getFoodShotsComments().add(0, newFoodShotComments);
		
		
		editTextTitle.setText("");
		editTextDescription.setText("");
		imgViewPick.setImageBitmap(null);
	}
	
}
