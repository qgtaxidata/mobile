package com.example.taxidata.bean;

/**
 * @author: ODM
 * @date: 2019/8/10
 */
public class HotSpotHint {

    private String  hotSpotName;

    private String hotSpotLocation;

    public HotSpotHint(String hotSpotName ,String hotSpotLocation) {
        this.hotSpotName =hotSpotName ;
        this.hotSpotLocation = hotSpotLocation;
    }

    public String getHotSpotName() {
        return hotSpotName;
    }

    public void setHotSpotName(String hotSpotName) {
        this.hotSpotName = hotSpotName;
    }

    public String getHotSpotLocation() {
        return hotSpotLocation;
    }

    public void setHotSpotLocation(String hotSpotLocation) {
        this.hotSpotLocation = hotSpotLocation;
    }
}
