package com.example.taxidata.ui.passengerpath.present;

import com.aserbao.aserbaosandroid.functions.database.greenDao.db.OriginEndInfoDao;
import com.example.taxidata.R;
import com.example.taxidata.application.TaxiApp;
import com.example.taxidata.common.GreenDaoManager;
import com.example.taxidata.ui.passengerpath.contract.OriginEndShowContract;
import com.example.taxidata.ui.passengerpath.enity.OriginEndInfo;
import com.example.taxidata.ui.passengerpath.enity.PathInfo;
import com.example.taxidata.ui.passengerpath.model.OriginEndShowModel;
import com.example.taxidata.widget.StatusToast;
import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class OriginEndShowPresent implements OriginEndShowContract.OriginEndShowPresent {

    OriginEndShowContract.OriginEndShowModel model;
    OriginEndShowContract.OriginEndShowView view;

    public OriginEndShowPresent() {
        model = new OriginEndShowModel();
    }

    @Override
    public void getHistory() {
        //获得历史记录
        List<OriginEndInfo> history;
        history = model.getOriginEnd();
        if (history != null){
           view.showHistory(history);
        }
    }

    @Override
    public void saveHistory(OriginEndInfo info) {
        //存储记录
        model.saveOriginEnd(info);
    }

    @Override
    public void managePath(OriginEndInfo info) {
        model.requestPath(info)
                .subscribe(new Observer<PathInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PathInfo pathInfo) {
                        if (pathInfo.getCode() == 1) {
                            view.showPath(pathInfo.getData());
                        }else {
                            StatusToast.getMyToast().ToastShow(TaxiApp.getContext(),null, R.mipmap.ic_sad,pathInfo.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Logger.d(e.getMessage());
                        StatusToast.getMyToast().ToastShow(TaxiApp.getContext(),null,R.mipmap.ic_sad,"网络错误");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void deleteHistory(OriginEndInfo info) {
        QueryBuilder<OriginEndInfo> infoBuilder = GreenDaoManager.getInstance().getDaoSession().queryBuilder(OriginEndInfo.class).where(OriginEndInfoDao.Properties.End.eq(info.getEnd()));
        List<OriginEndInfo> originQuery = infoBuilder.list();
        if (originQuery.isEmpty()){
            return;
        }else {
            model.delete(info);
        }
    }

    @Override
    public void attachView(OriginEndShowContract.OriginEndShowView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
