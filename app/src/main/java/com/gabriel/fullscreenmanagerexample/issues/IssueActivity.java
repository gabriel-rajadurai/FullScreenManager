package com.gabriel.fullscreenmanagerexample.issues;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.gabriel.fullscreenmanagerexample.R;

public class IssueActivity extends AppCompatActivity {

    private VideoView videoView;
    private boolean isFullscreen;
    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        videoView = findViewById(R.id.video_view);

        decorView = getWindow().getDecorView();

        playVideo();
    }

    private void playVideo() {

        Uri videoUri;

        videoUri = Uri.parse("android.resource://"
                + getPackageName() + "/"
                + R.raw.test_video);

        MediaController mc = new MediaController(this);
        mc.setAnchorView(videoView);

        videoView.setMediaController(mc);
        videoView.setVideoURI(videoUri);

        videoView.requestFocus();
        videoView.start();
        videoView.setOnCompletionListener(mediaPlayer -> {
            videoView.stopPlayback();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        });
    }


    // Solution

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isFullscreen = true;
            hideSystemUI();

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showSystemUI();
            isFullscreen = false;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isFullscreen) {
            hideSystemUI();
        }
    }

    void hideSystemUI() {
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    // This snippet shows the system bars.
    private void showSystemUI() {
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }
}
