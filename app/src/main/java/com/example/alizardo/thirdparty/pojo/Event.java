package com.example.alizardo.thirdparty.pojo;

/**
 * Created by Eduardo Silva on 28/12/2016.
 */

public class Event {

    private String title;
    private String host;
    private String description;
    private String startDate;
    private String endDate;
    private String maxGuests;
    private String url;
    private String local;
    private String slotsLeft;

    public Event() {

    }

    public Event(String title, String host, String local, String description, String startDate, String endDate, String maxGuests, String url, String slotsLeft) {
        this.host = host;
        this.title = title;
        this.local = local;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGuests = maxGuests;
        this.url = url;
        this.slotsLeft = slotsLeft;
    }

    public String getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(String maxGuests) {
        this.maxGuests = maxGuests;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSlotsLeft() {
        
        return slotsLeft;
    }

    public void setSlotsLeft(String slotsLeft) {
        this.slotsLeft = slotsLeft;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
