package com.carolsum.jingle.ui.activity;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.carolsum.jingle.R;
import com.carolsum.jingle.widget.CustomSlide;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro2 {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Add your slide's fragments here
    // AppIntro will automatically generate the dots indicator and buttons.
    addSlide(CustomSlide.newInstance(R.layout.first_intro_frag));
    addSlide(CustomSlide.newInstance(R.layout.second_intro_frag));
    addSlide(CustomSlide.newInstance(R.layout.third_intro_frag));

    // Instead of fragments, you can also use our default slide
    // Just set a title, description, background and image. AppIntro will do the rest
//    addSlide(AppIntro2Fragment.newInstance("“Jingle”一下，随时随地挣闲钱", "", image, background_colour));

    // OPTIONAL METHODS

    // SHOW or HIDE the statusbar
//    showStatusBar(false);

    // Edit the color of the nav bar on Lollipop+ devices
//    setNavBarColor(Color.parseColor("#3F51B5"))

    // Turn vibration on and set intensity
    // NOTE: you will need to ask VIBRATE permission in Manifest if you haven't already
//    setVibrate(true);
//    setVibrateIntensity(30);
      showSkipButton(false);
      showStatusBar(true);
      showPagerIndicator(true);

    // Animations -- use only one of the below. Using both could cause errors.
//    setFadeAnimation(); // OR
//    setZoomAnimation(); // OR
      setFlowAnimation(); // OR
//      setSlideOverAnimation(); // OR
//    setDepthAnimation(); // OR
//    setCustomTransformer(yourCustomTransformer);

    // Permissions -- takes a permission and slide number
    askForPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
  }


  @Override
  public void onSkipPressed(Fragment currentFragment) {
    super.onSkipPressed(currentFragment);
    finish();
  }

  @Override
  public void onDonePressed(Fragment currentFragment) {
    super.onDonePressed(currentFragment);
    finish();
  }

}
