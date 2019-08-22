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

    private boolean isFirst = true;

    public RepeatTask(RepeatCallBackListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (isFirst) {
            isFirst = false;
            if (listener != null) {
                listener.onFirst();
            }
        }
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        Call<HeatPointInfo> service = null;
        Response<HeatPointInfo> body = null;
        while (true) {
            if (isPause) {
                break;
            }
            try {
                //获取网络连接
                service = RetrofitManager.getInstance().getHttpService().getHeatPoint(integers[0], TaxiApp.getAppNowTime());
                //发送请求
                if (isPause) {
                    break;
                }
                body = service.execute();
                if (!body.isSuccessful()) {
                    throw new Exception("timeout");
                }
                //热力图信息
                if (isPause) {
                    break;
                }
                HeatPointInfo info = body.body();
                if (isPause) {
                    break;
                }
                //更新热力图
                publishProgress(info);
                if (isPause) {
                    break;
                }
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
            if (isPause) {
                break;
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
        this.repeatTime = repeatTime * 1000;
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

        /**
         * 第一次加载热力图
         */
        void onFirst();
    }
}
