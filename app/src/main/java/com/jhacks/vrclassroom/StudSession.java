package com.jhacks.vrclassroom;


import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.opentok.android.Connection;
import com.opentok.android.OpentokError;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import org.json.JSONException;
import org.json.JSONObject;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class StudSession extends AppCompatActivity implements  Session.SessionListener, Session.SignalListener/*, PublisherKit.PublisherListener*/  {

    private static String API_KEY, SESSION_ID, TOKEN, NAME, METADATA; // = "46043342";
    //private static String SESSION_ID = "2_MX40NjA0MzM0Mn5-MTUxNjQ3NzQxODk3Mn5TZnV5MS9kMm9OcUdJQVpCVG9UVmFYR25-fg";
    //private static String TOKEN = "T1==cGFydG5lcl9pZD00NjA0MzM0MiZzaWc9YTM5Zjk1YWM5MjAyZDllN2Q1ZWMwYzIyOGMwMGE3YmVmZWRmMDYzZTpzZXNzaW9uX2lkPTJfTVg0ME5qQTBNek0wTW41LU1UVXhOalEzTnpReE9EazNNbjVUWm5WNU1TOWtNbTlPY1VkSlFWcENWRzlVVm1GWVIyNS1mZyZjcmVhdGVfdGltZT0xNTE2NDc3NTU1Jm5vbmNlPTAuNTIxMjg4MzcyNDM4NTM5MyZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNTE5MDY5NTU0JmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;

    private Session mSession;
    private FrameLayout mSubscriberViewContainer;
    private Subscriber mSubscriber;

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

                    mSession = new Session.Builder(StudSession.this, API_KEY, SESSION_ID).build();
                    mSession.setSessionListener(StudSession.this);
                    mSession.setSignalListener(StudSession.this);
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
        setContentView(R.layout.stud_session);

        Bundle extras = getIntent().getExtras();
        NAME = extras.getString("studSesId");

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
            mSubscriberViewContainer = findViewById(R.id.subscriber_container);

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
            mSubscriberViewContainer = findViewById(R.id.subscriber_container);

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
        METADATA = mSession.getConnection().getData();
        Log.i(LOG_TAG, "METADATA::: " + METADATA);
    }

    @Override
    public void onDisconnected(Session session) {
        Log.i(LOG_TAG, "Session Disconnected");
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Received");

        if (mSubscriber == null) {
            Toast.makeText(this, "You are now connected", Toast.LENGTH_SHORT).show();
            mSubscriber = new Subscriber.Builder(this, stream).build();
            mSession.subscribe(mSubscriber);
            mSubscriberViewContainer.addView(mSubscriber.getView());
        }
        METADATA = mSession.getConnection().getData();
        Log.i(LOG_TAG, "METADATA::: " + METADATA);
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Dropped");

        if (mSubscriber != null) {
            mSubscriber = null;
            mSubscriberViewContainer.removeAllViews();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.e(LOG_TAG, "Session error: " + opentokError.getMessage());
    }

    // Back disconnects session
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Disconnected from Session", Toast.LENGTH_SHORT).show();
        mSession.disconnect();
        finish();
    }

    /*@Override
    protected void onSignalReceived(Session session, String type, String data, Connection connection) {
        String myConnectionId = session.getConnection().getConnectionId();
        if (connection != null && connection.getConnectionId().equals(myConnectionId)) {
            // Signal received from another client
        }
    }*/
    @Override
    public void onSignalReceived(Session session, String type, String data, Connection connection) {

        /*boolean remote = !connection.equals(mSession.getConnection());
        if (SIGNAL_TYPE != null && type.equals(SIGNAL_TYPE)) {
            showMessage(data, remote);
        }*/

        ///////////////////////////////////////////////////////////////////////////////////////////
        //SOMEONE LOOK AT THIS
        ///////////////////////////////////////////////////////////////////////////////////////////
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        //METADATA = mSession.getConnection().getData();
        Log.i(LOG_TAG, "Question::: " + data);
        //String copy = (String) data;
        String[] qData = new String[5];
        for( int index = 0; index < 5; index++) {
            int counter = 0;
            String temp = data.substring(0,1);
            while(  true){
                if(temp.equals(",") || (counter<data.length()) || temp == null){ break;}else{
                    qData[index] = qData[index].concat(temp);
                    counter++;
                    temp = data.substring(counter, counter+1);
                }
            }
            Log.i(LOG_TAG, "Question::: " + index+ qData[index]);
        }


    }
}