package com.learning.uploadimage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    EditText img_Status;
    Button selectButton,uploadButton;
    private int IMG_REQUEST = 21;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        img_Status = findViewById(R.id.img_status);
        selectButton = findViewById(R.id.selectBtn);
        uploadButton = findViewById(R.id.uploadBtn);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST);
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();

            }
        });

    }

    private void uploadImage() {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();

        String message = Base64.encodeToString(imageInByte,Base64.DEFAULT);
        String status = img_Status.getText().toString();


        Call<ResponseModel> call = RetrofitClient.getInstance().getApi().uploadImage(status,message);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel responseModel = response.body();

                Toast.makeText(MainActivity.this, "Server Respons :" +responseModel.getError(), Toast.LENGTH_SHORT).show();
                imageView.setVisibility(View.GONE);
                selectButton.setEnabled(true);
                uploadButton.setEnabled(false);
                img_Status.setText("");
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null){

            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
                img_Status.setVisibility(View.VISIBLE);
                selectButton.setEnabled(true);
                uploadButton.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
}