package com.example.taxidata.util;

public class LimitClickUtil {

    /**
     * 记录最后一次点击的时间
     */
    private static long lastClick = -1;

    /**
     * 两次点击时间隔是否超过limitTime
     * @param limitTime 限制时间
     * @return boolean
     */
    public static boolean isQuick(long limitTime) {
        //发生app初始化后的第一次点击事件
        if (lastClick == -1) {
            lastClick = System.currentTimeMillis();
            return false;
        }
        //是否过快点击
        boolean isQuick = false;
        //记录本次点击事件发生的时间
        long thisClick = System.currentTimeMillis();
        if (thisClick - lastClick < limitTime) {
            isQuick = true;
            ToastUtil.showShortToastBottom("请不要频繁操作");
        }
        lastClick = thisClick;
        return isQuick;
    }
}
