package com.example.kozlovskiy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.kozlovskiy.Transform.APP_PREFERENCES;
import static com.example.kozlovskiy.Transform.SaveUser;
import static com.example.kozlovskiy.Transform.StringNoNull;
import static com.example.kozlovskiy.Transform.Vibrate;
import static com.example.kozlovskiy.UserStaticInfo.LOGIN;
import static com.example.kozlovskiy.UserStaticInfo.PASSWORD;
import static com.example.kozlovskiy.UserStaticInfo.PROFILE_ID;
import static com.example.kozlovskiy.UserStaticInfo.USERS_SIGN_IN_INFO;

public class SignActivity extends AppCompatActivity{
//    protected LocationManager locationManager;
//    protected LocationListener locationListener;
//    public void onLocationChanged(Location location) {
//
//        Toast.makeText(this, "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude(), Toast.LENGTH_SHORT).show();
//    }

    private EditText LoginTextView, PasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        Init();

        CheckSignInInfo();
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        if ( Build.VERSION.SDK_INT >= 23){
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
//                    PackageManager.PERMISSION_GRANTED  ){
//                requestPermissions(new String[]{
//                                android.Manifest.permission.ACCESS_FINE_LOCATION},
//                        REQUEST_CODE_ASK_PERMISSIONS);
//                return ;
//            }
//        }
//
//
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }
    //get access to location permission
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_CODE_ASK_PERMISSIONS:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                } else {
//                    // Permission Denied
//                    Toast.makeText( this,"your message" , Toast.LENGTH_SHORT)
//                            .show();
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }
//    public  void getLocation(){
////        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
////        Location myLocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////        if (myLocation == null)
////        {
////            myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
////
////        }
////        Toast.makeText(this, "Latitude:" + myLocation.getLatitude() + ", Longitude:" + myLocation.getLongitude(), Toast.LENGTH_SHORT).show();
//    }
//    private void getLocation() {
//
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
//
//    }
//
//    //Get location
//    public void getLocation() {
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        if (myLocation == null)
//        {
//            myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//            if(myLocation!=null)
//            Toast.makeText(SignActivity.this, "Location changed: Lat: " + myLocation.getLatitude() + " Lng: " + myLocation.getLongitude(), Toast.LENGTH_SHORT).show();
//
//
//        }
//    }
//    private class MyLocationListener implements LocationListener {
//        @Override
//        public void onLocationChanged(Location loc) {
//            Toast.makeText(getBaseContext(), "Location changed: Lat: " + loc.getLatitude() + " Lng: " + loc.getLongitude(), Toast.LENGTH_SHORT).show();
//
//        }
//    }

    private void CheckSignInInfo() {

        SharedPreferences sp = getSharedPreferences(APP_PREFERENCES,
                Context.MODE_PRIVATE);
        String login = sp.getString(LOGIN, "");
        String password = sp.getString(PASSWORD, "");
        LoginTextView.setText(login);
        PasswordTextView.setText(password);
        if (StringNoNull(login))
            SignIn(LoginTextView);
    }

    private void Init() {
        LoginTextView = findViewById(R.id.LoginTextView);
        PasswordTextView = findViewById(R.id.PasswordTextView);
    }

    public void SignIn(View view) {
        if( StringNoNull(getPassword()) && StringNoNull(getLogin()) )
        {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(USERS_SIGN_IN_INFO);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String login = getLogin();
                Object value = dataSnapshot.child(login).child(PASSWORD).getValue();
                if(value!=null)
                {
                    if(value.toString().equals(getPassword()))
                    {
                       goNext(dataSnapshot.child(login).child(PROFILE_ID).getValue().toString());
                    }
                    else CantSignIn();

                }
                else CantSignIn();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        }
        else
        {
            Vibrate(SignActivity.this);

            Toast.makeText(SignActivity.this,
                    getResources().getText(R.string.NullParametersMessage),
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void CantSignIn() {
        Toast.makeText(SignActivity.this,
                getResources().getText(R.string.CantSignInMessage),
                Toast.LENGTH_SHORT).show();
    }


    private String getPassword() {
        return  PasswordTextView.getText().toString();
    }

    private String getLogin() {
        return  LoginTextView.getText().toString();
    }

    private void goNext(String profileId) {
        UserStaticInfo.profileId = profileId;
        SaveUser(getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE)
                    , getLogin(), getPassword());
        Intent intent = new Intent(SignActivity.this,ProfileMapsActivity.class);
        startActivity(intent);
        finish();
    }
}