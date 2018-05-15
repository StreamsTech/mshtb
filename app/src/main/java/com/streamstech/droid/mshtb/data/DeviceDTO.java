package com.streamstech.droid.mshtb.data;

public class DeviceDTO {
    private String brandname;
    private String devicefirmware;
    private long deviceid;
    private String deviceos;
    private String identifier;
    private String imei;
    private String modelname;
    private String platform;

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getBrandname() {
        return this.brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getModelname() {
        return this.modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    public String getDeviceos() {
        return this.deviceos;
    }

    public void setDeviceos(String deviceos) {
        this.deviceos = deviceos;
    }

    public String getDevicefirmware() {
        return this.devicefirmware;
    }

    public void setDevicefirmware(String devicefirmware) {
        this.devicefirmware = devicefirmware;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public long getDeviceid() {
        return this.deviceid;
    }

    public void setDeviceid(long deviceid) {
        this.deviceid = deviceid;
    }
}
