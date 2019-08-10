package com.example.taxidata.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: ODM
 * @date: 2019/8/10
 */

@Entity
public class HotSpotOrigin {

    @Id(autoincrement = true)
    Long Id;
    private String hotSpotOriginHistory;
    @Generated(hash = 1860873470)
    public HotSpotOrigin(Long Id, String hotSpotOriginHistory) {
        this.Id = Id;
        this.hotSpotOriginHistory = hotSpotOriginHistory;
    }
    @Generated(hash = 1217445469)
    public HotSpotOrigin() {
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public String getHotSpotOriginHistory() {
        return this.hotSpotOriginHistory;
    }
    public void setHotSpotOriginHistory(String hotSpotOriginHistory) {
        this.hotSpotOriginHistory = hotSpotOriginHistory;
    }

}
