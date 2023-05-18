package com.example.deliveryfoodapp.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.deliveryfoodapp.R;
import com.example.deliveryfoodapp.Domain.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
public class EditProfileActivity extends AppCompatActivity {

    EditText editName, editEmail, editPhone, editPassword;

    Button saveButton,backButton;
    ImageView imgAvatar;
    ProgressBar progressBar;

    LinearLayout btnEditAvatar;

    String nameUser, emailUser, phoneUser, passwordUser, avatarUser;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    StorageReference storageReference;
    Uri uri;
    FirebaseUser user;
    String userID;
    Boolean check=true;

    private DataSnapshot dataSnapshot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        userID = user.getUid();

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        editPassword = findViewById(R.id.editPassword);
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);
        imgAvatar = findViewById(R.id.imgAvatar);
        btnEditAvatar = findViewById(R.id.btnEditAvatar);
        progressBar = findViewById(R.id.progressbar);

        showData();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0;
                if(isNameChanged()) i++;
                if(isAvatarChanged()) i++;
                if(isPasswordChanged()) i++;
                if(isPhoneChanged()) i++;
                if (i >= 1){
                    Toast.makeText(EditProfileActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else   {
                    Toast.makeText(EditProfileActivity.this, "No Changes Information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        btnEditAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });

        checkAccount();
    }

    private void checkAccount() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.getAccessToken(false).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
            @Override
            public void onSuccess(GetTokenResult getTokenResult) {
                String strProvider = getTokenResult.getSignInProvider();
                if (strProvider.equals("google.com")) {
                    editPassword.setVisibility(View.GONE);
                    btnEditAvatar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            imgAvatar.setImageURI(uri);

        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        User user = new User(nameUser,phoneUser, emailUser, passwordUser, uri.toString());
                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(EditProfileActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(EditProfileActivity.this, "Uploading Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(EditProfileActivity.this, "Uploading Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean isAvatarChanged() {
        if (uri != null) {
            uploadToFirebase(uri);
            return true;
        } else {
            return false;
        }
    }

    private boolean isNameChanged() {
        if (!nameUser.equals(editName.getText().toString())){
            reference.child(userID).child("fullname").setValue(editName.getText().toString());
            nameUser = editName.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isPasswordChanged() {
        if (!passwordUser.equals(editPassword.getText().toString())){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            user.updatePassword(editPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                reference.child(userID).child("password").setValue(editPassword.getText().toString());
                                passwordUser = editPassword.getText().toString();
                                Toast.makeText(EditProfileActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EditProfileActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            return true;
        } else {
            return false;
        }
    }


    private boolean isPhoneChanged() {
        if (phoneUser.equals("") && !editPhone.getText().toString().equals("")) {
            reference.child(userID).child("phone").setValue(editPhone.getText().toString());
            phoneUser = editPhone.getText().toString();
            return true;
        } else if (!phoneUser.equals(editPhone.getText().toString())) {
            reference.child(userID).child("phone").setValue(editPhone.getText().toString());
            phoneUser = editPhone.getText().toString();
            return true;
        } else {
            return false;
        }
    }


    public void showData(){

        Intent intent = getIntent();

        nameUser = intent.getStringExtra("name");
        emailUser = intent.getStringExtra("email");
        phoneUser = intent.getStringExtra("phone");
        passwordUser = intent.getStringExtra("password");
        avatarUser = intent.getStringExtra("avatar");

        editName.setText(nameUser);
        editEmail.setText(emailUser);
        editPhone.setText(phoneUser);
        editPassword.setText(passwordUser);

        Glide.with(EditProfileActivity.this)
                .load(avatarUser)
                .transform(new CircleCrop())
                .into(imgAvatar);

    }
}