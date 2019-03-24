package android.rr.sendlater;

import android.rr.sendlater.adapter.MyViewPagerAdapter;
import android.rr.sendlater.presenter.MainActivityPresenter;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements
        MainActivityPresenter.MainActivityView {

    private MainActivityPresenter mMainActivityPresenter;
    private FloatingActionButton mBaseFab;
    private FloatingActionButton mCreateNewFab;
    private FloatingActionButton mRemainingFab;
    private FloatingActionButton mSentFab;
    private LinearLayout mCreateNewLayout;
    private LinearLayout mRemainingLayout;
    private LinearLayout mSentLayout;
    private ViewPager mViewPager;

    private boolean isFabMenuShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainActivityPresenter = new MainActivityPresenter(MainActivity.this,
                getApplicationContext());

        initViews();

        mBaseFab.setOnClickListener(mMainActivityPresenter);
        mCreateNewFab.setOnClickListener(mMainActivityPresenter);
        mRemainingFab.setOnClickListener(mMainActivityPresenter);
        mSentFab.setOnClickListener(mMainActivityPresenter);
    }

    private void initViews() {
        mBaseFab = findViewById(R.id.baseFloatingActionButton);
        mCreateNewFab = findViewById(R.id.createNewFab);
        mRemainingFab = findViewById(R.id.remainingFab);
        mSentFab = findViewById(R.id.sentFab);
        mCreateNewLayout = findViewById(R.id.createNewLayout);
        mRemainingLayout = findViewById(R.id.remainingLayout);
        mSentLayout = findViewById(R.id.sentLayout);
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(mMainActivityPresenter);
    }

    public void changeActionBarTitle (String title) {
        try {
            getSupportActionBar().setTitle(title);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void isFabMenuShown(boolean isFabMenuShown) {
        this.isFabMenuShown = isFabMenuShown;
    }

    @Override
    public void closeFabMenu() {
        mMainActivityPresenter.closeFABMenu(
                mBaseFab, mCreateNewLayout, mRemainingLayout, mSentLayout);
    }

    @Override
    public void showFabMenu() {
        mMainActivityPresenter.showFABMenu(
                mBaseFab, mCreateNewLayout, mRemainingLayout, mSentLayout);
    }

    @Override
    public void showThisFragment(int fragmentPos) {
        if (fragmentPos != mViewPager.getCurrentItem())
            mViewPager.setCurrentItem(fragmentPos, true);
        mMainActivityPresenter.closeFABMenu(
                mBaseFab, mCreateNewLayout, mRemainingLayout, mSentLayout);

    }

    @Override
    public void onBackPressed() {
        if (isFabMenuShown)
            mMainActivityPresenter.closeFABMenu(
                    mBaseFab, mCreateNewLayout, mRemainingLayout, mSentLayout);
        else
            super.onBackPressed();
    }

}