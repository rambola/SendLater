package android.rr.sendlater;

import android.rr.sendlater.presenter.SplashPresenter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity implements SplashPresenter.SplashView {
    private SplashPresenter mSplashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView mSplashTV = findViewById(R.id.splashTV);

        mSplashPresenter = new SplashPresenter (SplashScreen.this,
                getApplicationContext());
        mSplashPresenter.startAnimation(mSplashTV);
    }

    @Override
    public void animationEnded() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mSplashPresenter.startMainActivity();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
    }
}