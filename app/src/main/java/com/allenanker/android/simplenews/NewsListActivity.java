package com.allenanker.android.simplenews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class
NewsListActivity extends AppCompatActivity implements NewsListFragment.CallBacks {

    private BottomNavigationView mBottomNavigationView;
    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private MenuItem mMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        mBottomNavigationView = findViewById(R.id.bottom_nav_view);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        mViewPager = findViewById(R.id.news_viewpager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mMenuItem != null) {
                    mMenuItem.setChecked(false);
                } else {
                    mBottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                mMenuItem = mBottomNavigationView.getMenu().getItem(position);
                mMenuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        List<Fragment> fragments = new ArrayList<>();
        NewsListFragment newsListFragment = new NewsListFragment();
        fragments.add(newsListFragment.newInstance(1));
        fragments.add(newsListFragment.newInstance(2));
        fragments.add(newsListFragment.newInstance(3));
        fragments.add(newsListFragment.newInstance(4));
        fragments.add(newsListFragment.newInstance(5));
        mViewPagerAdapter.setFragments(fragments);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mMenuItem = item;
            switch (item.getItemId()) {
                case R.id.navigation_politics:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_international:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_finance:
                    mViewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_collection:
                    mViewPager.setCurrentItem(3);
                    return true;
                case R.id.navigation_profile:
                    mViewPager.setCurrentItem(4);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onNewsSelected(News news) {
        Toast.makeText(this, "You clicked News " + news.getTitle(), Toast.LENGTH_SHORT).show();
        String url = news.getUrl();
        if (url.startsWith("http://") || url.startsWith("https://"));
        else url = "http://" + url;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments != null ? mFragments.size() : 0;
        }

        public void setFragments(List<Fragment> fragments) {
            mFragments = fragments;
            notifyDataSetChanged();
        }
    }
}


/**
 * This is class is the solution I found online to eliminate the effect of shifting in BottomNavigationView
 */
class BottomNavigationViewHelper {
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        //get the BottomNavigationMenuView object
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            //set the private field mShiftingMode to modifiable one
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //remove shifting effect
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "No field named mShiftingMode founded", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Cannot modify the value of the field named mShiftingMode", e);
        }
    }
}