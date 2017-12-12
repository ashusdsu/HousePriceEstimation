package com.example.ashu.housepredictor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class ClickImage extends AppCompatActivity {

    private Button mUploadBtn;
    private ImageView mImageView;
    private Button mUpBtn;

    private static final int CAMERA_REQUEST_CODE = 1;

    private StorageReference mStorage;

    private ProgressDialog mProgress;

    String mCurrentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_image);

        mStorage = FirebaseStorage.getInstance().getReference();

        mProgress = new ProgressDialog(this);

        mUploadBtn = (Button) findViewById(R.id.upload);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mUpBtn = (Button) findViewById(R.id.button4);



        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);

                //dispatchTakePictureIntent();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){


            mProgress.setMessage("Uploading Image ...");
            mProgress.show();

            //Uri uri = data.getExtras();
            //Uri photoURI = data.getExtras();

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Canvas tempCanvas = new Canvas(photo);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataBAOS = baos.toByteArray();

            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://housepredictor-d4676.appspot.com");

            StorageReference imagesRef = storageRef.child("filename" + new Date().getTime());

            UploadTask uploadTask = imagesRef.putBytes(dataBAOS);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getApplicationContext(),"Sending failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

//handle success


                    mProgress.dismiss();
                }
            });

            mImageView.setImageBitmap(photo);

        }

    }

    public void uploadImage(View view)
    {
        //creating the intent about the image
        Intent intent  = new Intent(ClickImage.this, GeneralInfo.class);
        startActivity(intent);

    }
}
