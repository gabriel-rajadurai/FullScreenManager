package com.gabriel.fullscreenmanagerexample.issues;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.gabriel.fullscreenmanagerexample.R;

public class IssueActivity2 extends AppCompatActivity implements View.OnClickListener {


    private VideoView videoView;
    private ImageView ivPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue2);

        initViews();
    }

    private void initViews() {
        videoView = findViewById(R.id.video_view);
        ivPlay = findViewById(R.id.iv_play);

        ivPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == ivPlay){
            ivPlay.setVisibility(View.INVISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            playVideo();
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
        });
    }
}
