package com.example.taxidata.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: ODM
 * @date: 2019/8/10
 */

@Entity
public class HotSpotHistorySearch {

    public String getHotSpotHistory() {
        return hotSpotHistory;
    }

    public void setHotSpotHistory(String hotSpotHistory) {
        this.hotSpotHistory = hotSpotHistory;
    }

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    @Id(autoincrement = true)
    Long ID;
    private String hotSpotHistory;

    public HotSpotHistorySearch(String hotSpotHistory) {
        this.hotSpotHistory = hotSpotHistory;
    }

    @Generated(hash = 712093131)
    public HotSpotHistorySearch(Long ID, String hotSpotHistory) {
        this.ID = ID;
        this.hotSpotHistory = hotSpotHistory;
    }

    @Generated(hash = 1799766375)
    public HotSpotHistorySearch() {
    }
}
