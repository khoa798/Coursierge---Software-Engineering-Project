package com.jhacks.vrclassroom;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.opentok.android.BaseVideoCapturer;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;

import org.json.JSONException;
import org.json.JSONObject;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class ProfSession extends AppCompatActivity implements  Session.SessionListener, PublisherKit.PublisherListener  {

    private static String API_KEY, SESSION_ID, TOKEN, NAME, METADATA; // = "46043342";
    //private static String SESSION_ID = "2_MX40NjA0MzM0Mn5-MTUxNjQ3NzQxODk3Mn5TZnV5MS9kMm9OcUdJQVpCVG9UVmFYR25-fg";
    //private static String TOKEN = "T1==cGFydG5lcl9pZD00NjA0MzM0MiZzaWc9YTM5Zjk1YWM5MjAyZDllN2Q1ZWMwYzIyOGMwMGE3YmVmZWRmMDYzZTpzZXNzaW9uX2lkPTJfTVg0ME5qQTBNek0wTW41LU1UVXhOalEzTnpReE9EazNNbjVUWm5WNU1TOWtNbTlPY1VkSlFWcENWRzlVVm1GWVIyNS1mZyZjcmVhdGVfdGltZT0xNTE2NDc3NTU1Jm5vbmNlPTAuNTIxMjg4MzcyNDM4NTM5MyZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNTE5MDY5NTU0JmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;

    public static Session mSession;
    private Publisher mPublisher;
    private BaseVideoCapturer bVC;
    private FrameLayout mPublisherViewContainer;

    public void fetchSessionConnectionData() {
        RequestQueue reqQueue = Volley.newRequestQueue(this);
        reqQueue.add(new JsonObjectRequest(Request.Method.GET,
                "https://vr-classroom.herokuapp.com" + "/room/" + NAME,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    API_KEY = response.getString("apiKey");
                    SESSION_ID = response.getString("sessionId");
                    TOKEN = response.getString("token");

                    Log.i(LOG_TAG, "API_KEY: " + API_KEY);
                    Log.i(LOG_TAG, "SESSION_ID: " + SESSION_ID);
                    Log.i(LOG_TAG, "TOKEN: " + TOKEN);
                    Log.i(LOG_TAG, "NAME: " + NAME);

                    mSession = new Session.Builder(ProfSession.this, API_KEY, SESSION_ID).build();
                    mSession.setSessionListener(ProfSession.this);
                    mSession.connect(TOKEN);



                } catch (JSONException error) {
                    Log.e(LOG_TAG, "Web Service error: " + error.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, "Web Service error: " + error.getMessage());
            }
        }));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof_session);

        Bundle extras = getIntent().getExtras();
        NAME = extras.getString("profSesId");

        requestPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            // initialize view objects from your layout
            mPublisherViewContainer = findViewById(R.id.publisher_container);

            // initialize and connect to the session
            fetchSessionConnectionData();

        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }

    /*@AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            // initialize view objects from your layout
            mPublisherViewContainer = (FrameLayout)findViewById(R.id.publisher_container);

            // initialize and connect to the session
            mSession = new Session.Builder(this, API_KEY, SESSION_ID).build();
            mSession.setSessionListener(this);
            mSession.connect(TOKEN);

        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }*/

    // SessionListener methods

    @Override
    public void onConnected(Session session) {
        Log.i(LOG_TAG, "Session Connected");
        Toast.makeText(this, "You are now streaming live", Toast.LENGTH_SHORT).show();

        mPublisher = new Publisher.Builder(this).build();
        mPublisher.setPublisherListener(this);

        mPublisherViewContainer.addView(mPublisher.getView());
        mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {
        Log.i(LOG_TAG, "Session Disconnected");
    }


    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Received");
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Dropped");
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.e(LOG_TAG, "Session error: " + opentokError.getMessage());
    }

    // PublisherListener methods

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        Log.i(LOG_TAG, "Publisher onStreamCreated");
        METADATA = mSession.getConnection().getData();
        Log.i(LOG_TAG, "METADATA::: " + METADATA);
        //bVC = BaseVideoCapturer();
        //bVC.init();
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        Log.i(LOG_TAG, "Publisher onStreamDestroyed");
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        Log.e(LOG_TAG, "Publisher error: " + opentokError.getMessage());
    }

    // Back disconnects session
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Disconnected from Session", Toast.LENGTH_SHORT).show();
        METADATA = mSession.getConnection().getData();
        Log.i(LOG_TAG, "METADATA::: " + METADATA);
        mSession.disconnect();
        finish();
    }

    public void onClickSendMessage(View view){
        mPublisher.cycleCamera();
        mSession.sendSignal("chat", "Hello");
        Toast.makeText(this, "Sending message", Toast.LENGTH_SHORT).show();
        METADATA = mSession.getConnection().getData();
        Log.i(LOG_TAG, "METADATA::: " + METADATA);
    }

    public void onClickMore(View view){
        Intent intent = new Intent(this, ProfMenu.class);
        startActivity(intent);
    }
}
