package com.osshare.andos.view.toast;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.os.Handler;
import android.os.Message;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by apple on 16/10/6.
 */
public class XToastHandler extends Handler{
    private static XToastHandler mToastHandler;
    private final Queue<XToast> mQueue;

    private final static int SHOW_TOAST = 0x123;
    private final static int HIDE_TOAST = 0x456;
    private final static int SHOWNEXT_TOAST = 0X789;

    private XToastHandler()
    {
        mQueue = new LinkedList<>();
    }

    public synchronized static XToastHandler getInstance()
    {
        if(mToastHandler != null)
            return mToastHandler;
        else{
            mToastHandler = new XToastHandler();
            return mToastHandler;
        }
    }

    /**
     *  该方法把XToast添加到消息队列中
     * @param xToast
     */
    public void add(XToast xToast)
    {
        mQueue.offer(xToast);
        showNextToast();
    }

    public void showNextToast()
    {
        if(mQueue.isEmpty()) return;
        //获取队列头部的XToast
        XToast xToast = mQueue.peek();
        if(!xToast.isShowing()){
            Message message = Message.obtain();
            message.what = SHOW_TOAST;
            message.obj = xToast;
//            sendMessage(message);
        }
    }

    @Override
    public void handleMessage(Message msg) {
        XToast xToast = (XToast) msg.obj;
        switch (msg.what)
        {
            case SHOW_TOAST:
                showToast(xToast);
                break;

            case HIDE_TOAST:
                hideToast(xToast);
                break;

            case SHOWNEXT_TOAST:
                showNextToast();
                break;
        }
    }

    private void hideToast(final XToast xToast) {
//        AnimatorSet set = xToast.getHideAnimatorSet();
//        set.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                //如果动画结束了，移除队列头部元素以及从界面中移除mView
//                xToast.getViewGroup().removeView(xToast.getView());
//                xToast.getOnDisappearListener().onDisappear(xToast);
//                mQueue.poll();
//                sendEmptyMessage(SHOWNEXT_TOAST);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//        set.start();
    }

    private void showToast(XToast xToast) {

//        //如果当前有XToast正在展示，直接返回
//        if(xToast.isShowing()) return;
//        //把mView添加到界面中，实现Toast效果
//        xToast.getViewGroup().addView(xToast.getView());
//        //获取动画效果
//        AnimatorSet set = xToast.getShowAnimatorSet();
//        set.start();
//
//        Message message = Message.obtain();
//        message.what = HIDE_TOAST;
//        message.obj = xToast;
//        sendMessageDelayed(message,xToast.getDuration());
    }
}
