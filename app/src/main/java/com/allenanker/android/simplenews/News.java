package com.allenanker.android.simplenews;

import java.util.UUID;

public class News {

    private UUID mUUID;
    private String title;
    private String des;
    private String source;
    private String url;

    public News() {
        this(UUID.randomUUID());
    }

    public News(UUID uuid) {
        mUUID = uuid;
    }

    public News(String title, String source, String des, String url) {
        mUUID = UUID.randomUUID();
        this.title = title;
        this.source = source;
        this.des = des;
        this.url = url;
    }

    public UUID getid() {
        return mUUID;
    }

    public String getTitle() {
        return title;
    }

    public String getDes() {
        return des;
    }

    public String getSource() {
        return source;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
