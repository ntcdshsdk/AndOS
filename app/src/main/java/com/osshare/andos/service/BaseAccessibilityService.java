package com.osshare.andos.service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by apple on 16/9/29.
 */
public class BaseAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        switch (event.getAction()){

        }
    }

    @Override
    public void onInterrupt() {

    }
}
