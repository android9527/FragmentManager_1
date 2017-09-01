package io.github.fragmentmanager;

import io.github.fragmentmanager.nav.FragmentNavigation;

/**
 * Created by chenfeiyue on 17/8/3.
 * Description ：
 */
public class RegisterFragment4 extends BaseFragmentJava {

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void onButtonClick() {
        FragmentNavigation.build(mFragmentManager)
                .withBoolean("key_boolean", true)
                .withString("email", "11111")
//                .withTransition(R.anim.fragment_left_enter, R.anim.fragment_left_exit)
                .withTransition(R.anim.fragment_left_enter, R.anim.fragment_left_exit,
                        R.anim.fragment_left_enter, R.anim.fragment_left_exit)
                .add(R.id.layout_content, RegisterFragment1.class);
    }

    @Override
    public boolean onFragmentBackPressed() {

        FragmentNavigation.build(mFragmentManager).popBackStack(RegisterFragment2.class.getSimpleName());

        return false;
    }
}
