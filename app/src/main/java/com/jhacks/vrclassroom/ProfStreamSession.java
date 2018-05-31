package com.jhacks.vrclassroom;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.opentok.android.BaseVideoCapturer;
import com.opentok.android.BaseVideoRenderer;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.android.volley.VolleyLog.TAG;

public class ProfStreamSession extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, Session.SessionListener, Publisher.PublisherListener {
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;
    private static final String LOG_TAG = "screen-sharing " + MainActivity.class.getSimpleName();

    private static String API_KEY, SESSION_ID, TOKEN, NAME, METADATA;

    private Session mSession;
    private Publisher mPublisher;
    private Subscriber mSubscriber;

    private FrameLayout mPublisherViewContainer; //RelativeLayout?
    private FrameLayout mSubscriberViewContainer;
    private WebView mScreensharedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof_screensharing);

        mPublisherViewContainer = findViewById(R.id.publisher_container);
        mSubscriberViewContainer = findViewById(R.id.subscriber_container);
        mScreensharedView = findViewById(R.id.screenshared_view);
        //mScreensharedView.addJavascriptInterface(new WebViewInterface(), "webviewinterface");

        mScreensharedView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Toast.makeText(ProfStreamSession.this, "A web resource error has occurred.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!(url.equals(view.getUrl()))) view.loadUrl(url);
            }
        });

        mScreensharedView.getSettings().setJavaScriptEnabled(true);
        mScreensharedView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mScreensharedView.loadUrl("https://www.google.com/drive/");

        Bundle extras = getIntent().getExtras();
        NAME = extras.getString("profSesId");
        requestPermissions();
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            //mPublisherViewContainer = findViewById(R.id.publisherview);
            fetchSessionConnectionData();
        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setRationale(getString(R.string.rationale_ask_again))
                    //.setPositiveButton(getString(R.string.setting))
                    //.setNegativeButton(getString(R.string.cancel))
                    .setRequestCode(RC_SETTINGS_SCREEN_PERM)
                    .build()
                    .show();
        }
    }

    public void fetchSessionConnectionData() {
        Log.d(TAG, "fetching Session Connection data... ");
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

                    //initialize the session
                    mSession = new Session.Builder(ProfStreamSession.this, API_KEY, SESSION_ID).build();
                    mSession.setSessionListener(ProfStreamSession.this);
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
    public void onConnected(Session session) {
        Log.d(TAG, "onConnected: Connected to session " + session.getSessionId());
        Toast.makeText(this, "Within onConnected", Toast.LENGTH_LONG).show();
        ScreenSharingCapturer sc = new ScreenSharingCapturer(mScreensharedView);

        //initialize the publisher
        mPublisher = new Publisher.Builder(ProfStreamSession.this)
                .name("publisher")
                .capturer(sc)
                .build();
        mPublisher.setPublisherListener(this);
        mPublisher.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
        mPublisher.setPublisherVideoType(PublisherKit.PublisherKitVideoType.PublisherKitVideoTypeScreen);
        mPublisher.setAudioFallbackEnabled(false);
        mPublisherViewContainer.addView(mPublisher.getView());

        /*mWebViewContainer.setWebViewClient(new WebViewClient());
        WebSettings webSettings = mWebViewContainer.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebViewContainer.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mWebViewContainer.loadUrl("http://www.tokbox.com");*/

        //mPublisher.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
        if (mPublisher != null) {
            mSession.publish(mPublisher);
        }
    }

    @Override
    public void onDisconnected(Session session) {
        Log.d(TAG, "onDisconnected: disconnected from session " + session.getSessionId());
        mSession = null;
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.d(TAG, "onStreamReceived: New stream " + stream.getStreamId() + " in session " + session.getSessionId());
        /*if (mSubscriber == null) {
            mSubscriber = new Subscriber(this, stream);
            mSubscriber.setSubscriberListener(this);
            mSubscriber.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE,
                    BaseVideoRenderer.STYLE_VIDEO_FILL);
            mSession.subscribe(mSubscriber);
        }*/
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.d(TAG, "onStreamDropped: Stream " + stream.getStreamId() + " dropped from session " + session.getSessionId());
        if (mSubscriber != null) {
            mSubscriber = null;
            mSubscriberViewContainer.removeAllViews();
        }
    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        Log.d(TAG, "onStreamCreated: Own stream " + stream.getStreamId() + " created");
        METADATA = mSession.getConnection().getData();
        Log.i(LOG_TAG, "METADATA::: " + METADATA);
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        Log.d(TAG, "onStreamDestroyed: Own stream " + stream.getStreamId() + " destroyed");
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        if (mSession == null) {
            return;
        }
        mSession.onPause();

        if (isFinishing()) {
            disconnectSession();
        }
    }
//
    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        if (mSession == null) {
            return;
        }
        mSession.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        disconnectSession();
        super.onDestroy();
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.d(TAG, "onError: Error (" + opentokError.getMessage() + ") in session " + session.getSessionId());

        //Toast.makeText(this, "Session error. See the logcat please.", Toast.LENGTH_LONG).show();
        Toast.makeText(this, "(" + opentokError.getMessage() + ")", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        Log.d(TAG, "onError: Error (" + opentokError.getMessage() + ") in publisher");

        //Toast.makeText(this, "Session error. See the logcat please.", Toast.LENGTH_LONG).show();
        Toast.makeText(this, "(" + opentokError.getMessage() + ")", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onClickMore(View view){
        Intent intent = new Intent(this, ProfMenu.class);
        startActivity(intent);
    }

    private void disconnectSession() {
        if (mSession == null) {
            return;
        }

        if (mPublisher != null) {
            mPublisherViewContainer.removeView(mPublisher.getView());
            mSession.unpublish(mPublisher);
            mPublisher.destroy();
            mPublisher = null;
        }
        mSession.disconnect();
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


}
