package com.example.taxidata.net;

import android.os.AsyncTask;
import android.util.Log;

import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.bean.HeatPointInfo;

import retrofit2.Call;
import retrofit2.Response;

public class RepeatTask extends AsyncTask<Integer, HeatPointInfo,Integer> {

    private static final String TAG = "RepeatTask";

    private static final Integer ON_FINISH = 1;

    private static final Integer ON_FAIL = 2;

    private boolean isPause = false;

    private RepeatCallBackListener listener;

    private int repeatTime = 3000;

    public RepeatTask(RepeatCallBackListener listener) {
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        Call<HeatPointInfo> service = null;
        Response<HeatPointInfo> body = null;
        while (true) {
            if (isPause) {
                break;
            }
            //获取网络连接
            service = RetrofitManager.getInstance().getHttpService().getHeatPoint(integers[0], TaxiApp.getAppNowTime());
            try {
                //发送请求
                body = service.execute();
                //热力图信息
                HeatPointInfo info = body.body();
                //更新热力图
                publishProgress(info);
            }catch (Exception e) {
                e.printStackTrace();
                if (listener != null) {
                    listener.onFail(e);
                }
                return ON_FAIL;
            }
            try {
                //轮询间隔
                Thread.sleep(repeatTime);
            }catch (Exception e) {
                Log.d(TAG,e.getMessage());
            }
        }
        return ON_FINISH;
    }

    @Override
    protected void onProgressUpdate(HeatPointInfo... values) {
        super.onProgressUpdate(values);
        if (listener != null) {
            listener.onUpData(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        switch (integer) {
            case 1:
                if (listener != null) {
                    listener.onFinsh();
                }
                break;
            default:
        }
    }

    /**
     * 设置轮询时间
     * @param repeatTime
     */
    public void setRepeatTime(int repeatTime) {
        this.repeatTime = repeatTime;
    }

    /**
     * 提供暂停
     */
    public void pause() {
        isPause = true;
    }

    public interface RepeatCallBackListener {

        /**
         * 更新热力图
         * @param info 热力图信息
         */
        void onUpData(HeatPointInfo info);

        /**
         * 报错
         * @param e 异常
         */
        void onFail(Exception e);

        /**
         * 轮询结束
         */
        void onFinsh();
    }
}
