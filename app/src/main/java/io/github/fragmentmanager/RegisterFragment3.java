package io.github.fragmentmanager;

import android.os.Bundle;
import android.widget.Toast;

import io.github.fragmentmanager.nav.FragmentNavigation;

/**
 * Created by chenfeiyue on 17/8/3.
 * Description ：
 */
public class RegisterFragment3 extends BaseFragmentJava {

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            boolean toast = bundle.getBoolean("key_boolean", false);
            String email = bundle.getString("email");
            Toast.makeText(getActivity(), email + "   " + toast, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onButtonClick() {
        RegisterFragment4 fragment2 = FragmentNavigation.build(mFragmentManager)
                .withBoolean("key_boolean", true)
                .withString("email", "11111")
//                .withTransition(R.anim.fragment_left_enter, R.anim.fragment_left_exit)
                .withTransition(R.anim.fragment_left_enter, R.anim.fragment_left_exit,
                        R.anim.fragment_left_enter, R.anim.fragment_left_exit)
                .add(R.id.layout_content, RegisterFragment4.class);
    }

    @Override
    public boolean onFragmentBackPressed() {

        FragmentNavigation.build(mFragmentManager).popBackStack();
        return true;
    }
}
