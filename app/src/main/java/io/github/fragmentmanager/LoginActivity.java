package io.github.fragmentmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import io.github.fragmentmanager.nav.FragmentNavigation;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    Fragment currFragment;

    RegisterFragment1 fragment1;
    RegisterFragment2 fragment2;
    RegisterFragment3 fragment3;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment1();
                    return true;
                case R.id.navigation_dashboard:
                    switchFragment2();
                    return true;
                case R.id.navigation_notifications:

                    switchFragment3();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentNavigation.build(mFragmentManager)
                .withBoolean("key_boolean", true)
                .withString("email", "11111")
//                .withTransition(R.anim.fragment_left_enter, R.anim.fragment_left_exit)
                .withTransition(R.anim.fragment_left_enter, R.anim.fragment_left_exit,
                        R.anim.fragment_left_enter, R.anim.fragment_left_exit)
                .add(R.id.layout_content, RegisterFragment1.class);

    }

    void createFragment() {
        if (currFragment == null) {
            RegisterFragment1 fragment1 = FragmentNavigation.build(mFragmentManager, false)
                    .withBoolean("key_boolean", true)
                    .withString("email", "11111")
                    .withTransition(R.anim.fragment_left_enter, R.anim.fragment_left_exit,
                            R.anim.fragment_left_enter, R.anim.fragment_left_exit)
                    .add(R.id.layout_content_, RegisterFragment1.class);
            currFragment = fragment1;
        }
    }

    void switchFragment1() {
        FragmentNavigation.build(mFragmentManager, false).hideFragment(currFragment);
        if (fragment1 == null) {
            fragment1 = FragmentNavigation.build(mFragmentManager, false)
                    .withString("email", "11111")
                    .add(R.id.layout_content_,RegisterFragment1.class);
        }
        FragmentNavigation.build(mFragmentManager, false).showFragment(fragment1);
        currFragment = fragment1;
    }

    void switchFragment2() {
        FragmentNavigation.build(mFragmentManager, false).hideFragment(currFragment);
        if (fragment2 == null) {
            fragment2 = FragmentNavigation.build(mFragmentManager, false)
                    .withBoolean("key_boolean", true)
                    .add(R.id.layout_content_,RegisterFragment2.class);
        }
        FragmentNavigation.build(mFragmentManager, false).showFragment(fragment2);
        currFragment = fragment2;
    }

    void switchFragment3() {
        FragmentNavigation.build(mFragmentManager, false).hideFragment(currFragment);
        if (fragment3 == null) {
            fragment3 = FragmentNavigation.build(mFragmentManager, false)
                    .withBoolean("key_boolean", true)
                    .withString("email", "11111")
                    .add(R.id.layout_content_,RegisterFragment3.class);
        }
        FragmentNavigation.build(mFragmentManager, false).showFragment(fragment3);
        currFragment = fragment3;
    }

}

