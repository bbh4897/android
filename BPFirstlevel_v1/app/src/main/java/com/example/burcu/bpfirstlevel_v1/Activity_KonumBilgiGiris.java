package com.example.burcu.bpfirstlevel_v1;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_KonumBilgiGiris extends AppCompatActivity {

    EditText editText;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__konum_bilgi_giris);

        editText = (EditText)findViewById(R.id.editText);

        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText.getText().toString().equals("aaas")){
                    Snackbar.make(v, "sadasdasd", Snackbar.LENGTH_LONG)
                       .setAction("Action", null).show();
                }
                else{
                    Snackbar.make(v, "ssss", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });




    }
}
