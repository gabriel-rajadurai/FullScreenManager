package com.gabriel.fullscreenmanagerexample.solution;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Class responsible for changing the view from full screen to non-full screen and vice versa.
 *
 * Note: This is not the original version of this class. For original version, please refer
 * https://github.com/PierfrancescoSoffritti/Android-YouTube-Player
 */
class FullScreenManager {

    private View[] views;
    private View mainView;
    private ViewGroup.MarginLayoutParams paramsForMainView;

    private int[] margins = new int[4];
    private int mainViewHeight;

    private View decorView;

    /**
     * Constructor to initialize the FullScreenManager
     *
     * @param context           context of the current screen
     * @param mainView          the mainView that is to fill the entire screen
     * @param paramsForMainView layout params for the mainView
     * @param views             the views that have to be hidden or made visible on entering fullscreen or
     *                          the normal mode respectively
     */
    FullScreenManager(Activity context, View mainView,
                      ViewGroup.MarginLayoutParams paramsForMainView, View... views) {

        this.views = views;
        this.mainView = mainView;
        this.paramsForMainView = paramsForMainView;

        margins[0] = paramsForMainView.leftMargin;
        margins[1] = paramsForMainView.topMargin;
        margins[2] = paramsForMainView.rightMargin;
        margins[3] = paramsForMainView.bottomMargin;
        mainViewHeight = paramsForMainView.height;

        decorView = context.getWindow().getDecorView(); //Getting a reference to the system ui
    }

    /**
     * call this method to enter full screen
     */
    void enterFullScreen() {


        //hideSystemUI(decorView);

        for (View view : views) {
            view.setVisibility(View.GONE);
            view.invalidate();
        }

        ViewGroup.MarginLayoutParams fullScreenParams = paramsForMainView;
        fullScreenParams.setMargins(0, 0, 0, 0);
        fullScreenParams.width = MATCH_PARENT;
        fullScreenParams.height = MATCH_PARENT;
        mainView.setLayoutParams(fullScreenParams);

    }

    /**
     * call this method to exit full screen
     */
    void exitFullScreen() {

        showSystemUI(decorView);

        for (View view : views) {
            view.setVisibility(View.VISIBLE);
            view.invalidate();
        }

        paramsForMainView.setMargins(margins[0], margins[1], margins[2], margins[3]);
        paramsForMainView.width = MATCH_PARENT;
        paramsForMainView.height = mainViewHeight;
        mainView.setLayoutParams(paramsForMainView);
    }

    // hides the system bars.
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
    private void showSystemUI(View mDecorView) {
        mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        mDecorView.setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
