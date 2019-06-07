package br.com.cpsoftware.firebasestorage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

  private static final int GALLERY_REQUEST_CODE = 1000;

  private StorageReference mStorageRef;
  private TextView texto;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mStorageRef = FirebaseStorage.getInstance().getReference();
    texto = findViewById(R.id.texto);

    texto.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openGallery();
      }
    });

  }

  private void openGallery(){
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setType("image/*");
    String[] mimeTypes = {"image/jpeg", "image/png"};
    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
    startActivityForResult(intent, GALLERY_REQUEST_CODE);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if (resultCode == Activity.RESULT_OK) {
      switch (requestCode) {
        case GALLERY_REQUEST_CODE:
          Toast.makeText(MainActivity.this,
                  "Usuário selecionou a foto!", Toast.LENGTH_SHORT).show();

          Uri selectedImage = data.getData();
          uploadFile(selectedImage);

      }
    }
  }

  private void uploadFile(Uri imageSelected){
    final StorageReference imageRef = mStorageRef.child(new Date().getTime() + ".png");

    imageRef.putFile(imageSelected)
      .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
          // Get a URL to the uploaded content
          imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
              Toast.makeText(MainActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();
            }
          }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
              Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
          });
        }
      })
      .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
          Toast.makeText(MainActivity.this,
                  exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
      });
  }

}
