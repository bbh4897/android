package com.example.burcu.bpfirstlevel_v1;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Activity_KonumBilgiGiris extends AppCompatActivity {

    EditText editText;
    Button btn;
    ImageView imImageView;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__konum_bilgi_giris);

        editText = (EditText)findViewById(R.id.editText);
        imImageView = (ImageView)findViewById(R.id.imageView);
        btn = (Button)findViewById(R.id.button);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(editText.getText().toString().equals("aaas")){
//                    Snackbar.make(v, "sadasdasd", Snackbar.LENGTH_LONG)
//                       .setAction("Action", null).show();
//                }
//                else{
//                    Snackbar.make(v, "ssss", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }

                Veritabani db = new Veritabani(Activity_KonumBilgiGiris.this);
                db.Ekle(editText.getText().toString());

            }
        });

        imImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent img_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(img_intent, PICK_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imImageView.setImageURI(imageUri);
        }
    }
}
