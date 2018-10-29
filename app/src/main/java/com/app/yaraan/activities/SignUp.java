package com.app.yaraan.activities;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.app.yaraan.R;
import com.app.yaraan.retrofit.RestClient;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    private CircleImageView imgUpload;
    private EditText edtUsername,edtEmail,edtPassword,edtConfPww;
    private CheckedTextView remeberMe;
    private Button btnRegister;
    public static String TAG=SignUp.class.getName();
    Bundle bundle;
    public static final int PICK_FROM_CAMERA = 3, PICK_FROM_FILE = 2;
    public static final int MEDIA_TYPE_IMAGE = 1, MEDIA_TYPE_VIDEO = 6;
    Bitmap bitmap = null;
    File imageFile = null;
    public Uri fileUri = null;
    String androidLog="Android";
    private String username,email_id,password,token;
    private String confirmPasswd;
    SharedPreferences sharedPreferences;
    String id;
    boolean checkeckvalue=true;
    boolean haslogin=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sharedPreferences=getSharedPreferences(Constants.KEY,MODE_PRIVATE);
        id=sharedPreferences.getString("userid","");
        email_id=sharedPreferences.getString("useremail","");

        bundle=getIntent().getExtras();
        token=bundle.getString("token");

        imgUpload=(CircleImageView)findViewById(R.id.imgUpload);
        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCustomdialogImage();
            }
        });
        initComp();
    }

    public void validation() {
        if(bitmap==null) {
            Toast.makeText(getApplicationContext(),"Please Upload Image",Toast.LENGTH_LONG).show();
        } else if(username.isEmpty()){
            edtUsername.setError("not empty");

        }else if (email_id.isEmpty() || !validateEmailid(email_id)) {

           checkeckvalue = false;
           Toast.makeText(getApplicationContext(), "Please enter correct email id", Toast.LENGTH_SHORT).show();
       }else if (username.isEmpty()) {
           edtUsername.setError("not empty");

       }else if (TextUtils.isEmpty(password) || password.length() <= 5) {
           edtPassword.setError("Size must be greater than 5");
       }else if (!confirmPasswd.equals(password)) {
           edtConfPww.setError("not match password");
       }else {
    register(email_id, username, password, token,androidLog);

       }
    }
    public void initComp(){
        edtUsername=(EditText)findViewById(R.id.edtUsername);
        edtEmail=(EditText)findViewById(R.id.edtEmail);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        edtConfPww=(EditText)findViewById(R.id.edtConfPww);
        remeberMe=(CheckedTextView)findViewById(R.id.remeberMe);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_id = edtEmail.getText().toString().trim();
                username=edtUsername.getText().toString();
                email_id=edtEmail.getText().toString();
                password=edtPassword.getText().toString();
                confirmPasswd=edtConfPww.getText().toString();
                validation();
            }
        });
    }

    public void register(final String userEmail, String userName, String userpassword, String token,String loginos){
       DialogUtils.showProgressDialog(this,"Registration....");
        RestClient.getServices().signupOtp(userEmail,userName,userpassword,token,loginos).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                        String jsonString=response.body().string();
                        Log.e(TAG,"jsonString"+jsonString);
                        if(!TextUtils.isEmpty(jsonString)){
                        JSONObject jsonObject=new JSONObject(jsonString);
                        int sucess=jsonObject.getInt("success");
                            if(sucess==1){
                            String userid=jsonObject.getString("userId");
                            SharedPreferences sharedPreferences=getSharedPreferences(Constants.KEY,MODE_PRIVATE);
                            SharedPreferences.Editor edt=sharedPreferences.edit();
                            edt.putBoolean("haslogin",haslogin);
                            edt.putString("userid",userid);
                            edt.putString("useremail",userEmail);
                            edt.apply();
                            imagUpload(imageFile,userid);
                        }else {
                            DialogUtils.cancleProgressDialog();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new AlertDialog.Builder(SignUp.this)
                                            .setMessage("User Already reagisted")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                }
                                            }).show();
                                }
                            });
                        }


                    }
                }catch (Exception e) {
                        DialogUtils.cancleProgressDialog();
                        e.printStackTrace();
                        Log.e(TAG,"jsonError "+e);
                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                DialogUtils.cancleProgressDialog();
            }
        });


    }
    public static boolean validateEmailid(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public void OpenCustomdialogImage() {
        Log.d(TAG, "OpenCustomdialogImage: ");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Select Image");
        alert.setCancelable(true);
        alert.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                intent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
                intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
                intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
                try {
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, PICK_FROM_CAMERA);
                } catch (ActivityNotFoundException e) {
                    // Do nothing for now
                }

                dialog.dismiss();
            }
        });
        alert.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent = new Intent();
                // call android default gallery
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                try {
                    intent.putExtra("return-data", true);
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
                } catch (ActivityNotFoundException e) {
                    // Do nothing for now
                }
                dialog.dismiss();
            }
        });
        alert.show();

    }
    public Uri getOutputMediaFileUri(int type) {
//        return Uri.fromFile(getOutputMediaFile(type));
        return FileProvider.getUriForFile(SignUp.this, getApplicationContext().getPackageName() + ".provider", getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "Sesh");
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp.replace(" ", "_") + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp.replace(" ", "_") + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }

    public void imagUpload(File file,String userid){

        RequestBody id=RequestBody.create(MediaType.parse("multipart/form-data"),userid);
        RequestBody imgeFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("photo", imageFile.getName(), imgeFile);
        RestClient.getServices().imageUpload(filePart,id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try{

                    String jsonString=response.body().string();
                    JSONObject jsonObject=new JSONObject(jsonString);

                    int success=jsonObject.getInt("success");
                    if(success==1){
                        Intent intent=new Intent(SignUp.this,OtpVerification.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(),"Upload succsessfull",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"Sorry",Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    Log.e(TAG,"exception"+e);
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_FROM_FILE:
                    // user chose an image from the gallery (Bitmap)data.getExtras().get("data");
                    if (resultCode == RESULT_OK) {
                        try {
                            // We need to recyle unused bitmaps
//                            if (bitmap != null) {
//                                bitmap.recycle();
//                            }
                            imgUpload.setImageBitmap(bitmap);

                            imageFile = new File(getPathKitKat(SignUp.this, data.getData()));
                            Log.e("size before", String.valueOf(getImageLength(imageFile)));
//                            strImage = imageFile.getPath();
                            Log.v("img_path", imageFile.getPath());
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            // downsizing image as it throws OutOfMemory Exception for larger images
                            options.inSampleSize = 4;
                            bitmap = BitmapFactory.decodeFile(imageFile.getPath(), options);

                            try {
                                ExifInterface ei = new ExifInterface(imageFile.getPath());
                                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                                switch (orientation) {
                                    case ExifInterface.ORIENTATION_ROTATE_270:
                                        bitmap = rotateBitmap(bitmap, 270);
                                        break;
                                    case ExifInterface.ORIENTATION_ROTATE_90:
                                        bitmap = rotateBitmap(bitmap, 90);
                                        break;

                                    case ExifInterface.ORIENTATION_ROTATE_180:
                                        bitmap = rotateBitmap(bitmap, 180);
                                        break;
                                }
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            Drawable d = new BitmapDrawable(getResources(), bitmap);
                            imgUpload.setImageBitmap(bitmap);

//                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
//                            inputData = stream.toByteArray();
                            persistImage(imageFile,bitmap);
                            Log.e("size After", String.valueOf(getImageLength(imageFile)));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (resultCode == RESULT_CANCELED) {
                        // user cancelled Image capture
                        Toast.makeText(SignUp.this, "User cancelled image selection. ", Toast.LENGTH_SHORT).show();
                    } else {
                        // failed to capture image
                        Toast.makeText(SignUp.this, "Sorry! Failed to select image.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case PICK_FROM_CAMERA:
                    if (resultCode == RESULT_OK) {
                        // successfully captured the image display it in image view
                        try {
//                            if (bitmap != null) {
//                                bitmap.recycle();
//                            }
                            imgUpload.setImageBitmap(bitmap);
                            // bimatp factory
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            // downsizing image as it throws OutOfMemory Exception for larger images
                            imageFile = new File(fileUri.getPath());
                            Log.e("size Before", String.valueOf(getImageLength(imageFile)));


//                            options.inSampleSize = 4;
                            if(getImageLength(imageFile)>=5120) {
                                options.inSampleSize = 9;
                            }if(getImageLength(imageFile)>=4120){
                                options.inSampleSize=8;
                            }if(getImageLength(imageFile)>=3120){
                                options.inSampleSize=7;
                            }if(getImageLength(imageFile)>=2120){
                                options.inSampleSize=6;
                            }if(getImageLength(imageFile)>=1120){
                                options.inSampleSize=5;
                            }
//                            strImage = fileUri.getPath();
                            //						if (data.getData() == null) {
                            bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
                            //						} else {
                            //						      bitmap = BitmapFactory.decodeFile(data.getData().getPath() , options);
                            //						}
                            Log.d("Camera_img_path", "here" + fileUri.getPath());

                            try {
                                Log.d(TAG, "onActivityResult: ");
                                ExifInterface ei = new ExifInterface(fileUri.getPath());
                                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                                switch (orientation) {
                                    case ExifInterface.ORIENTATION_ROTATE_270:
                                        bitmap = rotateBitmap(bitmap, 270);
                                        Log.d(TAG, "onActivityResult: 1");
                                        break;
                                    case ExifInterface.ORIENTATION_ROTATE_90:
                                        bitmap = rotateBitmap(bitmap, 90);
                                        Log.d(TAG, "onActivityResult: 2");
                                        break;

                                    case ExifInterface.ORIENTATION_ROTATE_180:
                                        bitmap = rotateBitmap(bitmap, 180);
                                        Log.d(TAG, "onActivityResult: 3");
                                        break;
                                }
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            Drawable d = new BitmapDrawable(getResources(), bitmap);
                            imgUpload.setImageBitmap(bitmap);

//                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
//                            inputData = stream.toByteArray();
                            persistImage(imageFile,bitmap);
                            Log.e("size After", String.valueOf(getImageLength(imageFile)));

                            //						File image = new File(fileUri.getPath());
                            //						if(image.exists()){
                            //							image.delete();
                            //						}

                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    } else if (resultCode == RESULT_CANCELED) {
                        // user cancelled Image capture
                        Toast.makeText(SignUp.this, "User cancelled image capture", Toast.LENGTH_SHORT).show();
                    } else {
                        // failed to capture image
                        Toast.makeText(SignUp.this, "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
    private  void persistImage( File mImage,Bitmap bitmap) {

        String myfile="hello";
        String state;
        state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File Root = Environment.getExternalStorageDirectory();

            File Dir = new File(Root.getAbsolutePath() + "/MyAppFile");
            if (!Dir.exists()) {
                Dir.mkdir();
            }

            File file = new File(Dir,mImage.getName());
//            File file = new File(new File(Root.getAbsolutePath()+"/MyAppFile"), String.valueOf(mImage));
            if (file.exists()) {
                file.delete();
            }

            OutputStream os;
            try {
                os = new FileOutputStream(file);
                if(getImageLength(imageFile)>=5120){
                    bitmap.compress(Bitmap.CompressFormat.JPEG,50,os);
                }if(getImageLength(imageFile)>=4120) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 58, os);
                }if(getImageLength(imageFile)>=3120){
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, os);
                }if(getImageLength(imageFile)>=2120){
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 68, os);
                }if(getImageLength(imageFile)>=1120){
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 78, os);
                }else {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);
                }
                os.flush();
                os.close();
                imageFile = new File(file.getAbsolutePath());
            } catch (Exception e) {
                Log.e("Information frag", "Error writing bitmap", e);
            }
        }
    }
    private long getImageLength(File mFile){
        long length = 0;
        try{

            length = mFile.length();
            length = length/1024;

            Log.e("File Size: ", " File size : " + length +" KB");
        }catch(Exception e){
            Log.e("File not found : ",  e.getMessage() + e);
        }
        return length;
    }

    @SuppressLint("NewApi")
    public String getPathKitKat(Context context, Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }



}

