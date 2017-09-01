package io.github.fragmentmanager;

import android.util.Log;
import android.view.animation.Animation;

import io.github.fragmentmanager.nav.FragmentNavigation;

/**
 * Created by chenfeiyue on 17/8/3.
 * Description ：
 */
public class RegisterFragment1 extends BaseFragmentJava {

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void onButtonClick() {
        RegisterFragment2 fragment2 = FragmentNavigation.build(mFragmentManager)
                .withTransition(R.anim.fragment_left_enter, R.anim.fragment_left_exit,
                        R.anim.fragment_left_enter, R.anim.fragment_left_exit)
                .add(R.id.layout_content, RegisterFragment2.class);

        Log.e(RegisterFragment1.class.getSimpleName(), fragment2.toString());
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        // 此处设置动画
        return null;
    }
}
