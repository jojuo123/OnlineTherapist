package com.example.onlinetherapist.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.onlinetherapist.Constant;
import com.example.onlinetherapist.Login.Admin;
import com.example.onlinetherapist.Login.UI.LoginActivity;
import com.example.onlinetherapist.MedicalRecord.patient.MedicalRecordMainActivity;
import com.example.onlinetherapist.appointment.BookAppointmentActivity;
import com.example.onlinetherapist.appointment.ViewAppointmentActivity;
import com.example.onlinetherapist.appointment.therapist.TherapistViewAppointmentActivity;
import com.example.onlinetherapist.homescreen.HomeActivity;
import com.example.onlinetherapist.homescreen.HomePresenter;
import com.example.onlinetherapist.homescreen.IHomePresenter;
import com.example.onlinetherapist.homescreen.IHomeView;
import com.example.onlinetherapist.homescreen.therapist.ITherapistHomePresenter;
import com.example.onlinetherapist.homescreen.therapist.ITherapistHomeView;
import com.example.onlinetherapist.homescreen.therapist.TherapistHomePresenter;
import com.example.onlinetherapist.noteadvice.patient.NoteAdvicePatientActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.onlinetherapist.Fragments.HomeFragment;
import com.example.onlinetherapist.Fragments.ProfileFragment;
import com.example.onlinetherapist.Fragments.SettingsFragment;
import com.example.onlinetherapist.Models.Post;
import com.example.onlinetherapist.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IHomeView, ITherapistHomeView {

    IHomePresenter homePresenter;
    ITherapistHomePresenter therapistHomePresenter;

    private static final int PReqCode = 2 ;
    private static final int REQUESCODE = 2 ;
    FirebaseAuth mAuth;
    FirebaseUser currentUser ;
    Dialog popAddPost ;
    ImageView popupUserImage,popupPostImage,popupAddBtn;
    TextView popupTitle,popupDescription;
    ProgressBar popupClickProgress;
    private Uri pickedImgUri = null;

    Admin customCurrentUser = null;
    String patient_admin_uname;

    public String SavedCurrentUsername(){
        String Username;
        SharedPreferences saved = getSharedPreferences("SavedUsername", MODE_PRIVATE);
        Username = saved.getString("username","");
        return Username;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        mAuth = FirebaseAuth.getInstance();
//        currentUser = mAuth.getCurrentUser();

        patient_admin_uname = SavedCurrentUsername();

        // ini popup
        iniPopup();
        setupPopupImageClick();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popAddPost.show();
            }
        });

        homePresenter = new HomePresenter(this);
        therapistHomePresenter = new TherapistHomePresenter(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (!Constant.isTherapist) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_home2_drawer_patient);
        }
        else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_home2_drawer);
        }


        updateNavHeader();


        // set the home fragment as the default one

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();

    }

    private void setupPopupImageClick() {


        popupPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here when image clicked we need to open the gallery
                // before we open the gallery we need to check if our app have the access to user files
                // we did this before in register activity I'm just going to copy the code to save time ...

                checkAndRequestForPermission();


            }
        });



    }


    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(Home.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Home.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(Home.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(Home.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else
            // everything goes well : we have permission to access user gallery
            openGallery();

    }

    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image !

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    // when user picked an image ...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {

            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            pickedImgUri = data.getData() ;
            popupPostImage.setImageURI(pickedImgUri);
        }
    }

    private void iniPopup() {

        popAddPost = new Dialog(this);
        popAddPost.setContentView(R.layout.popup_add_post);
        popAddPost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popAddPost.getWindow().getAttributes().gravity = Gravity.TOP;

        // ini popup widgets
        popupUserImage = popAddPost.findViewById(R.id.popup_user_image);
        popupPostImage = popAddPost.findViewById(R.id.popup_img);
        popupTitle = popAddPost.findViewById(R.id.popup_title);
        popupDescription = popAddPost.findViewById(R.id.popup_description);
        popupAddBtn = popAddPost.findViewById(R.id.popup_add);
        popupClickProgress = popAddPost.findViewById(R.id.popup_progressBar);

        // load Current user profile photo

        //Glide.with(Home.this).load(currentUser.getPhotoUrl()).into(popupUserImage);


        // Add post click Listener

        popupAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupAddBtn.setVisibility(View.INVISIBLE);
                popupClickProgress.setVisibility(View.VISIBLE);

                // we need to test all input fields (Title and description ) and post image

                if (!popupTitle.getText().toString().isEmpty()
                    && !popupDescription.getText().toString().isEmpty()
                    && pickedImgUri != null ) {

                    //everything is okey no empty or null value
                    // TODO Create Post Object and add it to firebase database
                    // first we need to upload post Image
                    // access firebase storage
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("blog_images");
                    final StorageReference imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());
                    imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageDownlaodLink = uri.toString();
                                    // create post Object
                                    Post post = new Post(popupTitle.getText().toString(),
                                            popupDescription.getText().toString(),
                                            imageDownlaodLink,
                                            //currentUser.getUid(),
                                            (patient_admin_uname != null) ? patient_admin_uname : "anonymous"
                                            //currentUser.getPhotoUrl().toString()
                                    );

                                    // Add post to firebase database

                                    addPost(post);



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // something goes wrong uploading picture

                                    showMessage(e.getMessage());
                                    popupClickProgress.setVisibility(View.INVISIBLE);
                                    popupAddBtn.setVisibility(View.VISIBLE);



                                }
                            });


                        }
                    });
                }
                else {
                    showMessage("Please verify all input fields and choose Post Image") ;
                    popupAddBtn.setVisibility(View.VISIBLE);
                    popupClickProgress.setVisibility(View.INVISIBLE);

                }



            }
        });

    }

    private void addPost(Post post) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (database != null) Log.e("not null", "not null");
        else Log.e("null", "null");
        DatabaseReference myRef = database.getReference("Posts").push();

        // get post unique ID and upadte post key
        String key = myRef.getKey();
        post.setPostKey(key);


        // add post data to firebase database

        myRef.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("Post Added successfully");
                popupClickProgress.setVisibility(View.INVISIBLE);
                popupAddBtn.setVisibility(View.VISIBLE);
                popAddPost.dismiss();
            }
        });

    }


    private void showMessage(String message) {

        Toast.makeText(Home.this,message,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void PatientBookAppointment()
    {
        try {
            Intent k = new Intent(Home.this, BookAppointmentActivity.class);
            //Intent k = new Intent(HomeActivity.this, TherapistViewAppointmentActivity.class);
            startActivity(k);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void PatientViewAppointment()
    {
        homePresenter.onClickAppointment(Home.this,
                ViewAppointmentActivity.class, patient_admin_uname, "");
    }

    public void PatientViewRecords(){
        Intent intent = new Intent(Home.this, MedicalRecordMainActivity.class);
        intent.putExtra("username",SavedCurrentUsername());
        startActivity(intent);
    }

    public void PatientViewTodo()
    {
        Intent intent = new Intent(this.getApplicationContext(), NoteAdvicePatientActivity.class);
        intent.putExtra("username", SavedCurrentUsername());
        startActivity(intent);
    }

    public void PatientLogout()
    {
        if (patient_admin_uname != "")
        {
            homePresenter.Logout(Home.this, patient_admin_uname);
        }
    }

    public void TherapistViewAppointment()
    {
        Intent intent = new Intent(this.getApplicationContext(), TherapistViewAppointmentActivity.class);
        startActivity(intent);
    }

    public void TherapistLogout()
    {
        if (patient_admin_uname != "")
        {
            therapistHomePresenter.Logout(Home.this, patient_admin_uname);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_appointment) {

            TherapistViewAppointment();
            return true;

        } else if (id == R.id.nav_signout) {

            TherapistLogout();
            return true;

        } else if (id == R.id.nav_appointment_patient) {
            PatientViewAppointment();
            return true;
        }
        else if (id == R.id.nav_signout_patient) {
            PatientLogout();
            return true;
        }
        else if (id == R.id.nav_book)
        {
            PatientBookAppointment();
            return true;
        }
        else if (id == R.id.nav_todo)
        {
            PatientViewTodo();
            return true;
        }
        else if(id == R.id.nav_records){
            PatientViewRecords();
            return true;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
//        TextView navUsername = headerView.findViewById(R.id.nav_username);
//        TextView navUserMail = headerView.findViewById(R.id.nav_user_mail);
//        ImageView navUserPhot = headerView.findViewById(R.id.nav_user_photo);

//        navUserMail.setText(currentUser.getEmail());
//        navUsername.setText(currentUser.getDisplayName());

        // now we will use Glide to load user image
        // first we need to import the library

        // Glide.with(this).load(currentUser.getPhotoUrl()).into(navUserPhot);

    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
