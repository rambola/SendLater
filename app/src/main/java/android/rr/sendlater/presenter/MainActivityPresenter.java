package android.rr.sendlater.presenter;

import android.content.Context;
import android.rr.sendlater.MainActivity;
import android.rr.sendlater.R;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

public class MainActivityPresenter implements View.OnClickListener,
        ViewPager.OnPageChangeListener {
    private MainActivity mMainActivity;
    private Context mContext;
    private boolean isFabMenuShown = false;

    public MainActivityPresenter (MainActivity mainActivity, Context context) {
        mMainActivity = mainActivity;
        mContext = context;
    }

    public void showFABMenu(FloatingActionButton baseFab, LinearLayout createNewLayout,
                            LinearLayout remainingLayout, LinearLayout sentLayout) {
        Animation mFabOpenAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fab_menu_opening);
        ViewCompat.animate(baseFab).rotation(45.0F).withLayer().setDuration(300).
                setInterpolator(new OvershootInterpolator(10.0F)).start();
        createNewLayout.startAnimation(mFabOpenAnimation);
        remainingLayout.startAnimation(mFabOpenAnimation);
        sentLayout.startAnimation(mFabOpenAnimation);
        mMainActivity.isFabMenuShown(true);
        isFabMenuShown = true;
    }

    public void closeFABMenu (FloatingActionButton baseFab, LinearLayout createNewLayout,
                              LinearLayout remainingLayout, LinearLayout sentLayout) {
        Animation mFabCloseAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fab_menu_closing);
        ViewCompat.animate(baseFab).rotation(0.0F).withLayer().
                setDuration(300).setInterpolator(new OvershootInterpolator(10.0F)).start();
        createNewLayout.startAnimation(mFabCloseAnimation);
        remainingLayout.startAnimation(mFabCloseAnimation);
        sentLayout.startAnimation(mFabCloseAnimation);
        mMainActivity.isFabMenuShown(false);
        isFabMenuShown = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.baseFloatingActionButton:
                if (isFabMenuShown)
                    mMainActivity.closeFabMenu();
                else
                    mMainActivity.showFabMenu();
                break;
            case R.id.createNewFab:
                mMainActivity.showThisFragment(0);
                break;
            case R.id.remainingFab:
                mMainActivity.showThisFragment(1);
                break;
            case R.id.sentFab:
                mMainActivity.showThisFragment(2);
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case 0:
                mMainActivity.changeActionBarTitle(mContext.getString(R.string.createNew));
                break;
            case 1:
                mMainActivity.changeActionBarTitle(mContext.getString(R.string.remaining));
                break;
            case 2:
                mMainActivity.changeActionBarTitle(mContext.getString(R.string.sent));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public interface MainActivityView {
        void isFabMenuShown(boolean isFabMenuShown);
        void closeFabMenu();
        void showFabMenu();
        void showThisFragment(int fragmentPos);
    }
}