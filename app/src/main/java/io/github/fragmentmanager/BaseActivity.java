package io.github.fragmentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import io.github.fragmentmanager.library.SwipeBackUtil;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;


public class BaseActivity extends me.yokeyword.swipebackfragment.SwipeBackActivity implements BaseFragmentJava.BackHandlerInterface,
        FragmentManager.OnBackStackChangedListener{

    private static final String TAG = "YKBaseActivity";

    FrameLayout mLayoutContent;
    private View mRootLayoutView;
    protected FragmentManager mFragmentManager;

    BaseFragmentJava currFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);

        Log.e(TAG, "mFragmentManager" + mFragmentManager.toString());
        Log.e(TAG, "getSupportFragmentManager" + getSupportFragmentManager().toString());
        initContentView();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void initContentView() {
        mRootLayoutView = LayoutInflater.from(BaseActivity.this).inflate(R.layout.activity_base_content, null);
        super.setContentView(mRootLayoutView);
        mLayoutContent = (FrameLayout) findViewById(R.id.layout_content);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(layoutResID, mLayoutContent);
        super.setContentView(mRootLayoutView);
    }

    @Override
    public void onBackPressed() {
        if (currFragment == null || !currFragment.onFragmentBackPressed()) {
            super.onBackPressed();
        }

    }

    /**
     * 添加Fragment
     * @param fragment fragment
     */
    public void addFragment(Fragment fragment) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.add(R.id.layout_content, fragment, fragment.getClass().getName());
//        ft.setCustomAnimations(R.anim.fragment_left_enter,
//                R.anim.fragment_left_exit,
//                R.anim.fragment_slide_right_enter,
//                R.anim.fragment_slide_right_exit);

        ft.setCustomAnimations(R.anim.fragment_left_enter,
                R.anim.fragment_left_exit, R.anim.fragment_left_enter,
                R.anim.fragment_left_exit);
//        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void setSelectedFragment(BaseFragmentJava baseFragment) {
        currFragment = baseFragment;
    }

    @Override
    public void onBackStackChanged() {
        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        Log.e(getClass().getSimpleName(), "getBackStackEntryCount = " + backStackEntryCount);
//        List<Fragment> fragmentList = mFragmentManager.getFragments();
//        Log.e(getClass().getSimpleName(), "fragmentList = " + fragmentList);
//        if (fragmentList != null && backStackEntryCount < fragmentList.size()) {
//            Fragment topFragment = fragmentList.get(backStackEntryCount);
//            if (topFragment instanceof BaseFragmentJava) {
////                onBackStackChanged((BaseFragmentJava) topFragment);
//            }
//        }
    }

//    public void removeFragmentWithoutAnim(String tag) {
//        List<BaseManagerFragment> list = fragmentMap.get(tag);
//        if(list.size() <= 1) {
//            if(list.size() == 1) {
//                BaseManagerFragment fragment = list.get(0);
//                fragment.preBackResultData();
//                Intent intent = new Intent();
//                if(fragment.getResultData() != null)
//                    intent.putExtras(fragment.getResultData());
//                setResult(fragment.getResultCode(), intent);
//            }
//            supportFinishAfterTransition();
//        } else {
//            final BaseManagerFragment fragment = list.get(list.size() - 1);
//            list.remove(fragment);
//
//            final BaseManagerFragment fragment1 = list.get(list.size() - 1);
//            fragment.preBackResultData();
//            if(fragment.getRequestCode() != -1)
//                fragment1.onFragmentResult(fragment.getRequestCode(),
//                        fragment.getResultCode(),
//                        fragment.getResultData());
//
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////            showStackByTagNoAnim(currentStackTag, fragmentTransaction1);
//            fragmentTransaction.show(fragment1);
//
//            fragmentTransaction.remove(fragment)
//                    .commit();
//            fragment1.onShow(OnShowMode.ON_BACK);
//        }
//    }
}

