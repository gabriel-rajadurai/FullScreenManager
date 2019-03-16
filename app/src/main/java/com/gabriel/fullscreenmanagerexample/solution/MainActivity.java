package com.gabriel.fullscreenmanagerexample.solution;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.gabriel.fullscreenmanagerexample.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoView videoView;
    private ImageView ivPlay;
    private FullScreenManager fullScreenManager;
    private int videoPos = 0;
    private boolean isFullscreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        RelativeLayout.LayoutParams paramsForMainView
                = (RelativeLayout.LayoutParams) videoView.getLayoutParams();

        fullScreenManager = new FullScreenManager(
                this, // context of this screen
                videoView,    // the main focus view for full screen mode
                paramsForMainView, // the layout params for the main view
                findViewById(R.id.tv_title),
                findViewById(R.id.tv_description) // views to hide in fullscreen
        );
    }

    @Override
    public void onPause() {
        super.onPause();
        videoPos = videoView.getCurrentPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoPos > 0) {
            videoView.seekTo(videoPos + 250);
            videoView.pause();
        }
    }


    private void initViews() {
        videoView = findViewById(R.id.video_view);
        ivPlay = findViewById(R.id.iv_play);

        ivPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == ivPlay) {
            ivPlay.setVisibility(View.INVISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            playVideo();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fullScreenManager.enterFullScreen();
            isFullscreen = true;
            onWindowFocusChanged(true);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            fullScreenManager.exitFullScreen();
            isFullscreen = false;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isFullscreen) {
            fullScreenManager.hideSystemUI();
        }

    }

    private void playVideo() {

        videoView.setVisibility(View.VISIBLE);

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
            videoView.setVisibility(View.INVISIBLE);
            ivPlay.setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        });
    }
}
