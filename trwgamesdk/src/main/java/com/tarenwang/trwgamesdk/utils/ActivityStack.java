package com.tarenwang.trwgamesdk.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by zhangyan on 2017/2/7.
 * <p>
 * Activity 管理栈
 */


public class ActivityStack {

    private static Stack<Activity> stack;

    private static ActivityStack activityStack;

    public static ActivityStack getInstance() {
        if (activityStack == null)
            activityStack = new ActivityStack();
        return activityStack;
    }

    public ActivityStack() {
        stack = new Stack<>();
    }
    /**
     *  清除栈顶元素
     */
    public void pop(){
        if (stack == null)
            return;
    }

    /**
     * 在栈顶添加新元素
     */
    public void push(Activity activity) {
        if (stack == null)
            return;
            stack.push(activity);
    }


    /**
     * 清空栈
     */
    public static void clear() {
        if (stack == null)
            return;
        stack.clear();
    }

}
