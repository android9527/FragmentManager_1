package io.github.fragmentmanager.library

import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.AnimRes
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.SparseArray

import java.io.Serializable
import java.util.ArrayList

/**
 * Created by chenfeiyue on 17/8/4.
 * Description ：Fragment导航控制
 * 添加到回退栈和pop要用同一个FragmentManager的实例
 */
object FragmentNavigation {


    private val POP_BACK_STACK_EXCLUSIVE = 0
    private val POP_BACK_STACK_INCLUSIVE = 1

    /**

     * @param fragmentManager fragmentManager
     * *
     * @return FragmentNavBuild
     */
    fun build(fragmentManager: FragmentManager): FragmentNavBuild {
        return FragmentNavBuild(fragmentManager)
    }

    /**

     * @param fragmentManager fragmentManager
     * *
     * @param isAddToBackStack 是否需要添加到回退栈里，默认true
     * *
     * @return FragmentNavBuild
     */
    fun build(fragmentManager: FragmentManager, isAddToBackStack: Boolean): FragmentNavBuild {
        return FragmentNavBuild(fragmentManager, isAddToBackStack)
    }

    class FragmentNavBuild
    /**
     * FragmentNavBuild

     * @param fragmentManager  fragmentManager
     * *
     * @param isAddToBackStack 是否需要添加到回退栈
     */
    (private val fragmentManager: FragmentManager, isAddToBackStack: Boolean = true) {
        private var mBundle: Bundle? = null

        // 是否需要添加到回退栈里，默认true
        private var isAddToBackStack = true

        @AnimRes
        private var enterAnim = 0
        @AnimRes
        private var exitAnim = 0
        @AnimRes
        private var popEnterAnim = 0
        @AnimRes
        private var popExitAnim = 0

        init {
            this.isAddToBackStack = isAddToBackStack
            this.mBundle = Bundle()
        }

        /**
         * @param containerViewId containerViewId
         * *
         * @param clz             clz
         * *
         * @param tag             tag 默认SimpleName
         * *
         * @param <T>             Fragment
         * *
         * @return Fragment
        </T> */
        private fun <T : Fragment> replace(@IdRes containerViewId: Int, clz: Class<out T>,
                                           tag: String?): T? {
            try {
                val fragment = clz.newInstance()
                fragment.arguments = mBundle
                replaceFragment(containerViewId, fragment, tag)
                return fragment
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        /**
         * @param containerViewId containerViewId
         * *
         * @param clz             clz
         * *
         * @param <T>             Fragment
         * *
         * @return Fragment
        </T> */
        fun <T : Fragment> replace(@IdRes containerViewId: Int, clz: Class<out T>): T? {
            return replace(containerViewId, clz, clz.simpleName)
        }

        /**
         * @param containerViewId containerViewId
         * *
         * @param clz             clz
         * *
         * @param <T>             Fragment
         * *
         * @return Fragment
        </T> */
        fun <T : Fragment> add(@IdRes containerViewId: Int, clz: Class<out T>): T? {
            return add(containerViewId, clz, clz.simpleName)
        }


        /**
         * @param clz             clz
         * *
         * @param <T>             Fragment
         * *
         * @return Fragment
        </T> */
        fun <T : Fragment> create(clz: Class<out T>): T? {
            try {
                val fragment = clz.newInstance()
                fragment.arguments = mBundle
                return fragment
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        private fun <T : Fragment> add(@IdRes containerViewId: Int, clz: Class<out T>,
                                       tag: String?): T? {
            try {
                val fragment = clz.newInstance()
                fragment.arguments = mBundle
                addFragment(containerViewId, fragment, tag)
                return fragment
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }


        /**
         * @param containerViewId containerViewId
         * *
         * @param fragment               f
         * *
         * @param tag             tag 默认SimpleName
         */
        private fun replaceFragment(@IdRes containerViewId: Int, fragment: Fragment,
                                    tag: String?) {
            val ft = fragmentManager.beginTransaction()
            ft.setCustomAnimations(enterAnim,
                    exitAnim,
                    popEnterAnim,
                    popExitAnim)
            ft.replace(containerViewId, fragment, tag)
            if (isAddToBackStack)
                ft.addToBackStack(tag)
            ft.commitAllowingStateLoss()
        }

        fun addFragment(@IdRes containerViewId: Int, fragment: Fragment, tag: String?) {
            val ft = fragmentManager.beginTransaction()
            ft.setCustomAnimations(enterAnim,
                    exitAnim,
                    popEnterAnim,
                    popExitAnim)
            ft.add(containerViewId, fragment, tag)
            if (isAddToBackStack)
                ft.addToBackStack(tag)
            ft.commitAllowingStateLoss()
        }

        fun hideFragment(fragment: Fragment?) {
            val ft = fragmentManager.beginTransaction()
            if (fragment != null && fragment.isAdded) {
                ft.hide(fragment)
            }
            ft.commitAllowingStateLoss()
        }

        fun showFragment(fragment: Fragment?) {
            val ft = fragmentManager.beginTransaction()
            if (fragment != null && fragment.isAdded) {
                ft.show(fragment)
            }
            ft.commitAllowingStateLoss()
        }


        /**
         * @param containerViewId
         * *
         * @param showFragment
         * *
         * @param hideFragment
         */
        fun showAndHideFragment(@IdRes containerViewId: Int, showFragment: Fragment, hideFragment: Fragment) {
            showAndHideFragment(containerViewId, showFragment, showFragment.javaClass.name, hideFragment)
        }

        fun showAndHideFragment(@IdRes containerViewId: Int, showFragment: Fragment, tag: String?, hideFragment: Fragment?) {
            val ft = fragmentManager.beginTransaction()
            ft.setCustomAnimations(enterAnim,
                    exitAnim,
                    popEnterAnim,
                    popExitAnim)

            if (hideFragment != null && hideFragment.isAdded) {
                ft.hide(hideFragment)
            }

            if (showFragment.isAdded) {
                ft.show(hideFragment)
            } else {
                ft.add(containerViewId, showFragment, tag)
            }
            ft.commitAllowingStateLoss()
        }

        fun showAndHideFragment(showFragment: Fragment, hideFragment: Fragment?) {
            val ft = fragmentManager.beginTransaction()
            ft.setCustomAnimations(enterAnim,
                    exitAnim,
                    popEnterAnim,
                    popExitAnim)

            if (hideFragment != null && hideFragment.isAdded) {
                ft.hide(hideFragment)
            }

            if (showFragment.isAdded) {
                ft.show(hideFragment)
            }
            ft.commitAllowingStateLoss()
        }


        /**
         * 从FragmentManager回退栈里取出一个Fragment
         */
        fun popBackStack() {
            fragmentManager.popBackStack()
        }

        /**
         * 从FragmentManager回退栈里取出一个Fragment
         * flags 控制是否弹出自身，POP_BACK_STACK_INCLUSIVE弹出自身
         * 0 只弹出顶部记录， 默认为0
         * @param name fragment tag
         * *
         * @param included 控制是否弹出自身, 默认false
         */
        @JvmOverloads fun popBackStack(name: String, included: Boolean = false) {
            val flags = if (included) POP_BACK_STACK_INCLUSIVE else POP_BACK_STACK_EXCLUSIVE
            fragmentManager.popBackStack(name, flags)
        }


        fun with(bundle: Bundle?): FragmentNavBuild {
            if (null != bundle) {
                this.mBundle = bundle
            }
            return this
        }

        fun withParcelable(key: String?, value: Parcelable?): FragmentNavBuild {
            this.mBundle!!.putParcelable(key, value)
            return this
        }

        fun withString(key: String?, value: String?): FragmentNavBuild {
            this.mBundle!!.putString(key, value)
            return this
        }

        fun withBoolean(key: String?, value: Boolean): FragmentNavBuild {
            this.mBundle!!.putBoolean(key, value)
            return this
        }

        fun withShort(key: String?, value: Short): FragmentNavBuild {
            this.mBundle!!.putShort(key, value)
            return this
        }

        fun withInt(key: String?, value: Int): FragmentNavBuild {
            this.mBundle!!.putInt(key, value)
            return this
        }

        fun withLong(key: String?, value: Long): FragmentNavBuild {
            this.mBundle!!.putLong(key, value)
            return this
        }

        fun withDouble(key: String?, value: Double): FragmentNavBuild {
            this.mBundle!!.putDouble(key, value)
            return this
        }

        fun withByte(key: String?, value: Byte): FragmentNavBuild {
            this.mBundle!!.putByte(key, value)
            return this
        }

        fun withChar(key: String?, value: Char): FragmentNavBuild {
            this.mBundle!!.putChar(key, value)
            return this
        }

        fun withFloat(key: String?, value: Float): FragmentNavBuild {
            this.mBundle!!.putFloat(key, value)
            return this
        }

        fun withCharSequence(key: String?, value: CharSequence?): FragmentNavBuild {
            this.mBundle!!.putCharSequence(key, value)
            return this
        }

        fun withParcelableArray(key: String?, value: Array<Parcelable>?): FragmentNavBuild {
            this.mBundle!!.putParcelableArray(key, value)
            return this
        }

        fun withParcelableArrayList(key: String?, value: ArrayList<out Parcelable>?): FragmentNavBuild {
            this.mBundle!!.putParcelableArrayList(key, value)
            return this
        }

        fun withSparseParcelableArray(key: String?, value: SparseArray<out Parcelable>?): FragmentNavBuild {
            this.mBundle!!.putSparseParcelableArray(key, value)
            return this
        }

        fun withIntegerArrayList(key: String?, value: ArrayList<Int>?): FragmentNavBuild {
            this.mBundle!!.putIntegerArrayList(key, value)
            return this
        }

        fun withStringArrayList(key: String?, value: ArrayList<String>?): FragmentNavBuild {
            this.mBundle!!.putStringArrayList(key, value)
            return this
        }

        fun withCharSequenceArrayList(key: String?, value: ArrayList<CharSequence>?): FragmentNavBuild {
            this.mBundle!!.putCharSequenceArrayList(key, value)
            return this
        }

        fun withSerializable(key: String?, value: Serializable?): FragmentNavBuild {
            this.mBundle!!.putSerializable(key, value)
            return this
        }

        fun withByteArray(key: String?, value: ByteArray?): FragmentNavBuild {
            this.mBundle!!.putByteArray(key, value)
            return this
        }

        fun withShortArray(key: String?, value: ShortArray?): FragmentNavBuild {
            this.mBundle!!.putShortArray(key, value)
            return this
        }

        fun withCharArray(key: String?, value: CharArray?): FragmentNavBuild {
            this.mBundle!!.putCharArray(key, value)
            return this
        }

        fun withFloatArray(key: String?, value: FloatArray?): FragmentNavBuild {
            this.mBundle!!.putFloatArray(key, value)
            return this
        }

        fun withCharSequenceArray(key: String?, value: Array<CharSequence>?): FragmentNavBuild {
            this.mBundle!!.putCharSequenceArray(key, value)
            return this
        }

        fun withBundle(key: String?, value: Bundle?): FragmentNavBuild {
            this.mBundle!!.putBundle(key, value)
            return this
        }

        /**
         * Fragment切换动画
         * @param enterAnim 新的fragment进入动画
         * *
         * @param exitAnim  旧的fragment退出动画
         * *
         * @return FragmentNavBuild
         */
        fun withTransition(@AnimRes enterAnim: Int,
                           @AnimRes exitAnim: Int): FragmentNavBuild {
            this.enterAnim = enterAnim
            this.exitAnim = exitAnim
            return this
        }

        /**
         * Fragment切换动画
         * @param enterAnim 新的fragment进入动画
         * *
         * @param exitAnim  旧的fragment退出动画
         * *
         * @param popEnterAnim 调用popBackStack时新的fragment进入动画
         * *
         * @param popExitAnim  调用popBackStack时旧的fragment退出动画
         * *
         * @return FragmentNavBuild
         */
        fun withTransition(@AnimRes enterAnim: Int,
                           @AnimRes exitAnim: Int,
                           @AnimRes popEnterAnim: Int,
                           @AnimRes popExitAnim: Int): FragmentNavBuild {
            this.enterAnim = enterAnim
            this.exitAnim = exitAnim
            this.popEnterAnim = popEnterAnim
            this.popExitAnim = popExitAnim
            return this
        }
    }

}
