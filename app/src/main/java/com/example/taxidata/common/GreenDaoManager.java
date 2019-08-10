package com.example.taxidata.common;

import android.content.Context;

import com.aserbao.aserbaosandroid.functions.database.greenDao.db.DaoMaster;
import com.aserbao.aserbaosandroid.functions.database.greenDao.db.DaoSession;

/**
 * @author: ODM
 * @date: 2019/8/10
 */
public class GreenDaoManager {

    private static final String TAG = "GreenDaoManager";

    public static final  String HOTSPOT_DATABASE_NAME = "taxi_data.db";

    /**
     * DaoSession 类型 ： 热点历史
     */
    private DaoSession hotspotDaoSession;

    //判断是否初始化
    private boolean isInited;

    private static final class GreenDaoManagerHolder {
        private static final GreenDaoManager mInstance = new GreenDaoManager();
    }

    public static GreenDaoManager getInstance() {
        return GreenDaoManagerHolder.mInstance;
    }

    private GreenDaoManager() {

    }

    /**
     * 初始化DaoSession
     *
     * @param context
     */
    public void init(Context context) {
        if (!isInited) {
            DaoMaster.OpenHelper openHelperHotspot = new DaoMaster.DevOpenHelper(
                    context.getApplicationContext(), HOTSPOT_DATABASE_NAME, null);
            DaoMaster daoMaster = new DaoMaster(openHelperHotspot.getWritableDatabase());
            hotspotDaoSession = daoMaster.newSession();
            isInited = true;
        }
    }

    public DaoSession getHotSpotDaoSession() {
        return hotspotDaoSession;
    }

}

