package com.example.burcu.bpfirstlevel_v1;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class RecordList extends AppCompatActivity {
    ListView listView;
    Button btn2;

    ArrayList<Model> mList;
    KonumListAdapter adapter = null;
    Model model;

    ImageView imgIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        ActionBar actionBar  =getSupportActionBar();
        actionBar.setTitle("Kayıtlı Konumlar Listesi");

        listView = (ListView)findViewById(R.id.list_view);
        mList = new ArrayList<>();
        adapter = new KonumListAdapter(this, R.layout.row, mList);
        listView.setAdapter(adapter);



        Cursor cursor = Activity_KonumBilgiGiris.veritabani.getData("SELECT * FROM KONUMBILGILERI");
        mList.clear();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            mList.add(new Model(id, name, image));
        }
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                model = (Model) adapter.getItem(position);
                Intent intent  =new Intent(RecordList.this, ActivityKonumDetay.class);
                intent.putExtra("id",model.getId());
                intent.putExtra("name",model.getName());
                intent.putExtra("iamge", model.getImage());

                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long l) {

                final CharSequence[] items = {"Guncelle", "Sil"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(RecordList.this);
                dialog.setTitle("İşlem Seçiniz");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        if(i==0){
                            Cursor c = Activity_KonumBilgiGiris.veritabani.getData("SELECT id FROM KONUMBILGILERI");
                            ArrayList<Integer> arrId = new ArrayList<>();
                            while (c.moveToNext()){
                                arrId.add(c.getInt(0));
                               // Log.e("testttttttt" , position);
                            }
                            showDialogUpdate(RecordList.this, arrId.get(position));
                        }
                        if(i==1) {
                            Cursor c = Activity_KonumBilgiGiris.veritabani.getData("SELECT id FROM KONUMBILGILERI");
                            ArrayList<Integer> arrId = new ArrayList<>();
                            while (c.moveToNext()){
                                arrId.add(c.getInt(0));
                            }
                            showDialogDelete(arrId.get(position));

                        }

                    }
                });
                dialog.show();
                return true;

            }
        });
    }

    private void showDialogDelete(final Integer idRecord) {

        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(RecordList.this);
        dialogDelete.setTitle("Uyarı!");
        dialogDelete.setMessage("Silmek İstediğinizden Emin misiniz?");
        dialogDelete.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    Activity_KonumBilgiGiris.veritabani.deleteData(idRecord);
                    Toast.makeText(RecordList.this, "Silindi", Toast.LENGTH_LONG).show();

                }catch (Exception e){
                    Log.e("Error", e.getMessage());
                }
                updateRecordList();
            }
        });

        dialogDelete.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        dialogDelete.show();



    }

    private void showDialogUpdate(Activity activity, final int position){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_dialog);
        dialog.setTitle("Guncelle");

        imgIcon = dialog.findViewById(R.id.ImageViewRecord);
        final EditText edtName = dialog.findViewById(R.id.edtName);
        final Button btnUpdate = dialog.findViewById(R.id.btnUpdate);


        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.7);

        dialog.getWindow().setLayout(width,height);
        dialog.show();


        imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(RecordList.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},888);


            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    Activity_KonumBilgiGiris.veritabani.updateData(edtName.getText().toString().trim(), Activity_KonumBilgiGiris.imageViewToByte(imgIcon), position);
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Güncellendi", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Log.e("Güncelleme Başarısız" , e.getMessage());
                }
                updateRecordList();
            }
        });

    }

    private void updateRecordList() {

        Cursor cursor = Activity_KonumBilgiGiris.veritabani.getData("SELECT * FROM KONUMBILGILERI");
        mList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);

            mList.add(new Model(id,name,image));

        }
        adapter.notifyDataSetChanged();

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
        if(requestCode == 888){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 888);
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
        if(requestCode == 888 && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(this);
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                imgIcon.setImageURI(resultUri);
            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}