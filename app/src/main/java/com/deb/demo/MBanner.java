package com.deb.demo;

public class MBanner {
    private String banner_url;
    private String down_url;

    public MBanner() {
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public String getDown_url() {
        return down_url;
    }

    public void setDown_url(String down_url) {
        this.down_url = down_url;
    }

    public MBanner(String banner_url, String down_url) {
        this.banner_url = banner_url;
        this.down_url = down_url;
    }

}
