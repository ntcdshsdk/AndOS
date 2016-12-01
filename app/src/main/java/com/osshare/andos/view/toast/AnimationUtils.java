package com.osshare.andos.view.toast;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

/**
 * Created by apple on 16/10/6.
 */
public class AnimationUtils {
    public static final int ANIMATION_DEFAULT = 0X000;
    public static final int ANIMATION_DRAWER = 0x001;
    public static final int ANIMATION_SCALE = 0x002;

    public static AnimatorSet getShowAnimation(XToast xToast,int animationType){
//        switch (animationType){
//            case ANIMATION_DRAWER:
//                AnimatorSet drawerSet = new AnimatorSet();
//                drawerSet.playTogether(
//                        ObjectAnimator.ofFloat(xToast.getView(), "translationY", -xToast.getView().getMeasuredHeight(), 0),
//                        ObjectAnimator.ofFloat(xToast.getView(), "alpha", 0, 1)
//                );
//                drawerSet.setDuration(500);
//                return drawerSet;
//
//            case ANIMATION_SCALE:
//                AnimatorSet scaleSet = new AnimatorSet();
//                scaleSet.playTogether(
//                        ObjectAnimator.ofFloat(xToast.getView(), "scaleX", 0, 1),
//                        ObjectAnimator.ofFloat(xToast.getView(), "scaleY", 0, 1)
//                );
//                scaleSet.setDuration(500);
//                return scaleSet;
//
//            default:
                AnimatorSet defaultSet = new AnimatorSet();
//                defaultSet.play(ObjectAnimator.ofFloat(xToast.getView(), "alpha", 0, 1));
//                defaultSet.setDuration(500);
                return defaultSet;
//        }
    }
}
