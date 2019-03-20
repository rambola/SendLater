package android.rr.sendlater.presenter;

import android.content.Context;
import android.content.Intent;
import android.rr.sendlater.MainActivity;
import android.rr.sendlater.R;
import android.rr.sendlater.SplashScreen;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashPresenter {
    private SplashScreen mSplashScreen;
    private Context mContext;

    public SplashPresenter(SplashScreen splashScreen, Context context) {
        mSplashScreen = splashScreen;
        mContext = context;
    }

    public void startAnimation (TextView splashTV) {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in_zoom_in);
        animation.setAnimationListener(animationListener);

        splashTV.setAnimation(animation);
    }

    public void startMainActivity () {
        mContext.startActivity(new Intent(mSplashScreen, MainActivity.class));
    }

    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mSplashScreen.animationEnded();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    public interface SplashView {
        void animationEnded();
    }
}
