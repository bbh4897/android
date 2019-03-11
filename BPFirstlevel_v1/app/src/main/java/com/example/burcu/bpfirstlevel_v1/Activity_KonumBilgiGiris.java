package com.example.burcu.bpfirstlevel_v1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Activity_KonumBilgiGiris extends AppCompatActivity {

    EditText editText;
    Button btn, btn3;
    ImageView imImageView;
    final int REQUEST_CODE_GALLERY = 999;

    public static Veritabani veritabani;
    public static KonumListAdapter a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__konum_bilgi_giris);

        ActionBar actionBar  =getSupportActionBar();
        actionBar.setTitle("Yeni Kayıt");

        editText = (EditText)findViewById(R.id.editText);
        imImageView = (ImageView)findViewById(R.id.imageView);
        btn = (Button)findViewById(R.id.button);
        btn3 = (Button)findViewById(R.id.button3);

        veritabani = new Veritabani(this, "Bitirmedb.sqlite", null, 1 );
        veritabani.queryData("CREATE TABLE IF NOT EXISTS KONUMBILGILERI(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, image BLOB)");

        imImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(Activity_KonumBilgiGiris.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    veritabani.insertData(editText.getText().toString().trim(), imageViewToByte(imImageView));
                    Snackbar.make(v, "Veri Eklendi", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    editText.setText("");
                   // imImageView.setImageResources(R.drawable.imageview_background);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Activity_KonumBilgiGiris.this, RecordList.class));
            }
        });
    }

    public static byte[] imageViewToByte(ImageView imImageView) {

        Bitmap bitmap = ((BitmapDrawable)imImageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            }
            else {
                Log.i("burcuuuuuu" , "erısım ıznı yok");

            }
            return;

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(this);
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                imImageView.setImageURI(resultUri);
            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
