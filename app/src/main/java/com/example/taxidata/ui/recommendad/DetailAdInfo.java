package com.example.taxidata.ui.recommendad;

public class DetailAdInfo {

    private AdInfo.DataBean info;

    private String detailInfo;

    public DetailAdInfo() {}

    public DetailAdInfo(AdInfo.DataBean info, String detailInfo) {
        this.info = info;
        this.detailInfo = detailInfo;
    }

    public AdInfo.DataBean getInfo() {
        return info;
    }

    public void setInfo(AdInfo.DataBean info) {
        this.info = info;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }
}
