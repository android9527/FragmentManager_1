package io.github.fragmentmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import io.github.fragmentmanager.library.SwipeBackUtil;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by chenfeiyue on 17/7/3.
 * Description ：
 */
public class BaseFragmentJava extends me.yokeyword.swipebackfragment.SwipeBackFragment {

    protected AutoCompleteTextView email;
    protected EditText password;
    protected Toolbar toolbar;

    protected FragmentManager mFragmentManager;

    private final String TAG = this.getClass().getSimpleName();

    public interface BackHandlerInterface {
        void setSelectedFragment(BaseFragmentJava baseFragment);
    }

    protected BackHandlerInterface backHandlerInterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        backHandlerInterface = (BaseActivity) getActivity();
        mFragmentManager = getActivity().getSupportFragmentManager();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.e(TAG, "-----onCreateView()----");
        View view = inflater.inflate(R.layout.reg_1, container, false);
//        return SwipeBackUtil.enableSwipeBackAtFragment(this, view);
//        return view;
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "-----onViewCreated()----");
        email = (AutoCompleteTextView) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        toolbar.setTitle(this.getClass().getSimpleName());
// Sub Title
        toolbar.setSubtitle("Sub title");

// Navigation Icon 要設定在 setSupoortActionBar 才有作用
// 否則會出現 back button
        toolbar.setNavigationIcon(R.mipmap.mine_three_point);

        view.findViewById(R.id.email_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });
        initData();
    }

    protected void onButtonClick() {
    }

    protected void initData() {
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "-----onActivityCreated()----");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "-----onResume()----");
        backHandlerInterface.setSelectedFragment(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "-----onPause()----");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, "-----onHiddenChanged()----" + hidden);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "-----onDestroyView()----");
        backHandlerInterface.setSelectedFragment(null);
    }

    /**
     * 基类Fragment供子类实现的处理返回键
     */
    protected boolean onFragmentBackPressed() {
        return false;
    }
}
