package io.github.fragmentmanager.nav;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by chenfeiyue on 17/8/4.
 * Description ：Fragment导航控制
 * 添加到回退栈和pop要用同一个FragmentManager的实例
 */
public class FragmentNavigation {


    private static final int POP_BACK_STACK_EXCLUSIVE = 0;
    private static final int POP_BACK_STACK_INCLUSIVE = 1;

    /**
     *
     * @param fragmentManager fragmentManager
     * @return FragmentNavBuild
     */
    public static FragmentNavBuild build(@NonNull FragmentManager fragmentManager) {
        return new FragmentNavBuild(fragmentManager);
    }

    /**
     *
     * @param fragmentManager fragmentManager
     * @param isAddToBackStack 是否需要添加到回退栈里，默认true
     * @return FragmentNavBuild
     */
    public static FragmentNavBuild build(@NonNull FragmentManager fragmentManager, boolean isAddToBackStack) {
        return new FragmentNavBuild(fragmentManager, isAddToBackStack);
    }
    
    public static class FragmentNavBuild {
        private FragmentManager fragmentManager;
        private Bundle mBundle;

        // 是否需要添加到回退栈里，默认true
        private boolean isAddToBackStack = true;

        @AnimRes
        private int enterAnim = 0;
        @AnimRes
        private int exitAnim = 0;
        @AnimRes
        private int popEnterAnim = 0;
        @AnimRes
        private int popExitAnim = 0;

        private FragmentNavBuild(FragmentManager fragmentManager) {
            this(fragmentManager, true);
        }

        /**
         * FragmentNavBuild
         *
         * @param fragmentManager  fragmentManager
         * @param isAddToBackStack 是否需要添加到回退栈
         */
        private FragmentNavBuild(FragmentManager fragmentManager, boolean isAddToBackStack) {
            this.fragmentManager = fragmentManager;
            this.isAddToBackStack = isAddToBackStack;
            this.mBundle = new Bundle();
        }

        /**
         * @param containerViewId containerViewId
         * @param clz             clz
         * @param tag             tag 默认SimpleName
         * @param <T>             Fragment
         * @return Fragment
         */
        private <T extends Fragment> T replace(@IdRes int containerViewId, Class<? extends T> clz,
                                              @Nullable String tag) {
            try {
                final Fragment fragment = clz.newInstance();
                fragment.setArguments(mBundle);
                replaceFragment(containerViewId, fragment, tag);
                return (T) fragment;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * @param containerViewId containerViewId
         * @param clz             clz
         * @param <T>             Fragment
         * @return Fragment
         */
        public <T extends Fragment> T replace(@IdRes int containerViewId, Class<? extends T> clz) {
            return replace(containerViewId, clz, clz.getSimpleName());
        }

        /**
         * @param containerViewId containerViewId
         * @param clz             clz
         * @param <T>             Fragment
         * @return Fragment
         */
        public <T extends Fragment> T add(@IdRes int containerViewId, Class<? extends T> clz) {
            return add(containerViewId, clz, clz.getSimpleName());
        }


        /**
         * @param clz             clz
         * @param <T>             Fragment
         * @return Fragment
         */
        public <T extends Fragment> T create(Class<? extends T> clz) {
            try {
                final Fragment fragment = clz.newInstance();
                fragment.setArguments(mBundle);
                return (T) fragment;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private <T extends Fragment> T add(@IdRes int containerViewId, Class<? extends T> clz,
                                               @Nullable String tag) {
            try {
                final Fragment fragment = clz.newInstance();
                fragment.setArguments(mBundle);
                addFragment(containerViewId, fragment, tag);
                return (T) fragment;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        /**
         * @param containerViewId containerViewId
         * @param fragment               f
         * @param tag             tag 默认SimpleName
         */
        private void replaceFragment(@IdRes int containerViewId, @NonNull Fragment fragment,
                                     @Nullable String tag) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setCustomAnimations(enterAnim,
                    exitAnim,
                    popEnterAnim,
                    popExitAnim);
            ft.replace(containerViewId, fragment, tag);
            if (isAddToBackStack)
                ft.addToBackStack(tag);
            ft.commitAllowingStateLoss();
        }

        public void addFragment(@IdRes int containerViewId, @NonNull Fragment fragment, @Nullable String tag) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setCustomAnimations(enterAnim,
                    exitAnim,
                    popEnterAnim,
                    popExitAnim);
            ft.add(containerViewId, fragment, tag);
            if (isAddToBackStack)
                ft.addToBackStack(tag);
            ft.commitAllowingStateLoss();
        }

        public void hideFragment(Fragment fragment) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (fragment != null && fragment.isAdded()) {
                ft.hide(fragment);
            }
            ft.commitAllowingStateLoss();
        }

        public void showFragment(Fragment fragment) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (fragment != null && fragment.isAdded()) {
                ft.show(fragment);
            }
            ft.commitAllowingStateLoss();
        }


        /**
         * @param containerViewId
         * @param showFragment
         * @param hideFragment
         */
        public void showAndHideFragment(@IdRes int containerViewId, @NonNull Fragment showFragment, Fragment hideFragment) {
            showAndHideFragment(containerViewId, showFragment, showFragment.getClass().getName(), hideFragment);
        }

        public void showAndHideFragment(@IdRes int containerViewId, @NonNull Fragment showFragment, @Nullable String tag, Fragment hideFragment) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setCustomAnimations(enterAnim,
                    exitAnim,
                    popEnterAnim,
                    popExitAnim);

            if (hideFragment != null && hideFragment.isAdded()) {
                ft.hide(hideFragment);
            }

            if (showFragment.isAdded()) {
                ft.show(hideFragment);
            } else {
                ft.add(containerViewId, showFragment, tag);
            }
            ft.commitAllowingStateLoss();
        }

        public void showAndHideFragment(@NonNull Fragment showFragment, Fragment hideFragment) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setCustomAnimations(enterAnim,
                    exitAnim,
                    popEnterAnim,
                    popExitAnim);

            if (hideFragment != null && hideFragment.isAdded()) {
                ft.hide(hideFragment);
            }

            if (showFragment.isAdded()) {
                ft.show(hideFragment);
            }
            ft.commitAllowingStateLoss();
        }


        /**
         * 从FragmentManager回退栈里取出一个Fragment
         */
        public void popBackStack() {
            fragmentManager.popBackStack();
        }

        /**
         * 从FragmentManager回退栈里取出一个Fragment
         * flags 控制是否弹出自身，POP_BACK_STACK_INCLUSIVE弹出自身
         * 0 只弹出顶部记录， 默认为0
         * @param name fragment tag
         * @param included 控制是否弹出自身, 默认false
         */
        public void popBackStack(String name, boolean included) {
            int flags = included ? POP_BACK_STACK_INCLUSIVE : POP_BACK_STACK_EXCLUSIVE;
            fragmentManager.popBackStack(name, flags);
        }

        public void popBackStack(String name) {
            popBackStack(name, false);
        }


        public FragmentNavBuild with(Bundle bundle) {
            if (null != bundle) {
                this.mBundle = bundle;
            }
            return this;
        }

        public FragmentNavBuild withParcelable(@Nullable String key, @Nullable Parcelable value) {
            this.mBundle.putParcelable(key, value);
            return this;
        }

        public FragmentNavBuild withString(@Nullable String key, @Nullable String value) {
            this.mBundle.putString(key, value);
            return this;
        }

        public FragmentNavBuild withBoolean(@Nullable String key, boolean value) {
            this.mBundle.putBoolean(key, value);
            return this;
        }

        public FragmentNavBuild withShort(@Nullable String key, short value) {
            this.mBundle.putShort(key, value);
            return this;
        }

        public FragmentNavBuild withInt(@Nullable String key, int value) {
            this.mBundle.putInt(key, value);
            return this;
        }

        public FragmentNavBuild withLong(@Nullable String key, long value) {
            this.mBundle.putLong(key, value);
            return this;
        }

        public FragmentNavBuild withDouble(@Nullable String key, double value) {
            this.mBundle.putDouble(key, value);
            return this;
        }

        public FragmentNavBuild withByte(@Nullable String key, byte value) {
            this.mBundle.putByte(key, value);
            return this;
        }

        public FragmentNavBuild withChar(@Nullable String key, char value) {
            this.mBundle.putChar(key, value);
            return this;
        }

        public FragmentNavBuild withFloat(@Nullable String key, float value) {
            this.mBundle.putFloat(key, value);
            return this;
        }

        public FragmentNavBuild withCharSequence(@Nullable String key, @Nullable CharSequence value) {
            this.mBundle.putCharSequence(key, value);
            return this;
        }

        public FragmentNavBuild withParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
            this.mBundle.putParcelableArray(key, value);
            return this;
        }

        public FragmentNavBuild withParcelableArrayList(@Nullable String key, @Nullable ArrayList<? extends Parcelable> value) {
            this.mBundle.putParcelableArrayList(key, value);
            return this;
        }

        public FragmentNavBuild withSparseParcelableArray(@Nullable String key, @Nullable SparseArray<? extends Parcelable> value) {
            this.mBundle.putSparseParcelableArray(key, value);
            return this;
        }

        public FragmentNavBuild withIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
            this.mBundle.putIntegerArrayList(key, value);
            return this;
        }

        public FragmentNavBuild withStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
            this.mBundle.putStringArrayList(key, value);
            return this;
        }

        public FragmentNavBuild withCharSequenceArrayList(@Nullable String key, @Nullable ArrayList<CharSequence> value) {
            this.mBundle.putCharSequenceArrayList(key, value);
            return this;
        }

        public FragmentNavBuild withSerializable(@Nullable String key, @Nullable Serializable value) {
            this.mBundle.putSerializable(key, value);
            return this;
        }

        public FragmentNavBuild withByteArray(@Nullable String key, @Nullable byte[] value) {
            this.mBundle.putByteArray(key, value);
            return this;
        }

        public FragmentNavBuild withShortArray(@Nullable String key, @Nullable short[] value) {
            this.mBundle.putShortArray(key, value);
            return this;
        }

        public FragmentNavBuild withCharArray(@Nullable String key, @Nullable char[] value) {
            this.mBundle.putCharArray(key, value);
            return this;
        }

        public FragmentNavBuild withFloatArray(@Nullable String key, @Nullable float[] value) {
            this.mBundle.putFloatArray(key, value);
            return this;
        }

        public FragmentNavBuild withCharSequenceArray(@Nullable String key, @Nullable CharSequence[] value) {
            this.mBundle.putCharSequenceArray(key, value);
            return this;
        }

        public FragmentNavBuild withBundle(@Nullable String key, @Nullable Bundle value) {
            this.mBundle.putBundle(key, value);
            return this;
        }

        /**
         * Fragment切换动画
         * @param enterAnim 新的fragment进入动画
         * @param exitAnim  旧的fragment退出动画
         * @return FragmentNavBuild
         */
        public FragmentNavBuild withTransition(@AnimRes int enterAnim,
                                               @AnimRes int exitAnim) {
            this.enterAnim = enterAnim;
            this.exitAnim = exitAnim;
            return this;
        }

        /**
         * Fragment切换动画
         * @param enterAnim 新的fragment进入动画
         * @param exitAnim  旧的fragment退出动画
         * @param popEnterAnim 调用popBackStack时新的fragment进入动画
         * @param popExitAnim  调用popBackStack时旧的fragment退出动画
         * @return FragmentNavBuild
         */
        public FragmentNavBuild withTransition(@AnimRes int enterAnim,
                                               @AnimRes int exitAnim,
                                               @AnimRes int popEnterAnim,
                                               @AnimRes int popExitAnim) {
            this.enterAnim = enterAnim;
            this.exitAnim = exitAnim;
            this.popEnterAnim = popEnterAnim;
            this.popExitAnim = popExitAnim;
            return this;
        }
    }

}
