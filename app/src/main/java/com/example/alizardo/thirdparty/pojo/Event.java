package com.example.alizardo.thirdparty.pojo;

/**
 * Created by Eduardo Silva on 28/12/2016.
 */

public class Event {

    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private String maxGuests;
    private String url;
    private String local;
    private String slotsLeft;
    private String hostName;
    private String hostEmail;
    private String hostURL;
    private String id;
    private boolean isHost;
    private boolean isAccepted;
    private boolean isInvited;
    private boolean isPending;
    private boolean isRejected;

    public Event() {

    }

    public Event(String title, String local, String description, String startDate,
                 String endDate, String maxGuests, String url, String slotsLeft, String hostName,
                 String hostEmail, String hostURL, String id,
                 boolean isHost, boolean isAccepted, boolean isInvited, boolean isPending, boolean isRejected) {
        this.title = title;
        this.local = local;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGuests = maxGuests;
        this.url = url;
        this.slotsLeft = slotsLeft;
        this.hostEmail = hostEmail;
        this.hostName = hostName;
        this.id = id;
        this.hostURL = hostURL;
        this.isAccepted = isAccepted;
        this.isHost = isHost;
        this.isInvited = isInvited;
        this.isPending = isPending;
        this.isRejected = isRejected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public String getHostURL() {
        return hostURL;
    }

    public void setHostURL(String hostURL) {
        this.hostURL = hostURL;
    }
}
