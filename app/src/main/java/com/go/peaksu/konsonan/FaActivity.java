package com.go.peaksu.konsonan;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.go.peaksu.R;

import java.util.ArrayList;

public class FaActivity extends AppCompatActivity implements OnGesturePerformedListener {
    private GestureLibrary gLibrary;
    private GestureOverlayView gesture;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.fa);

        this.gesture = (GestureOverlayView) findViewById(R.id.gestureOverlay);
        this.gesture.addOnGesturePerformedListener(this);
        this.gLibrary = GestureLibraries.fromRawResource(this, R.raw.gestdasar);
        this.gLibrary.load();
        ImageView myAnimation = (ImageView) findViewById(R.id.myanimation);
        final AnimationDrawable myAnimationDrawable = (AnimationDrawable) myAnimation.getDrawable();
        myAnimation.post(new Runnable() {
            public void run() {
                myAnimationDrawable.start();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("Fa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = this.gLibrary.recognize(gesture);
        Builder ab = new Builder(this);
        if (predictions.size() <= 0 || ((Prediction) predictions.get(0)).score <= 3.0d) {
            ab.setTitle("Maaf");
            ab.setMessage("Salah, aksara yang anda tulis tidak tepat.");
            ab.setPositiveButton("Coba lagi", null);
            ab.show();
            return;
        }
        if (((Prediction) predictions.get(0)).name.equals("fa")) {
            ab = new Builder(this);
            ab.setTitle("Bagus");
            ab.setMessage("Benar, aksara yang anda tulis sudah tepat.");
            ab.setPositiveButton("OK", null);
            ab.show();
        } else {
            ab = new Builder(this);
            ab.setTitle("Maaf");
            ab.setMessage("Salah, aksara yang anda tulis tidak tepat.");
            ab.setPositiveButton("Coba lagi", null);
            ab.show();
        }
        for (int i = 0; i < predictions.size(); i++) {
            Log.v("TestGesture", "Prediction: " + ((Prediction) predictions.get(i)).name + " - score = " + ((Prediction) predictions.get(i)).score);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }
}
