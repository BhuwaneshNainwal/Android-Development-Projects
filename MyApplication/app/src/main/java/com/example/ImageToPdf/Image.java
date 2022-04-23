package com.example.ImageToPdf;

public class Image {

    String contestName;
    String link;
    String startTime;
    String endTime;
    String twentyFourHours;
    int isRunning;
    int flag = 0;

    private static final int NO_IMAGE_PROVIDED = -1;
    int imageId = NO_IMAGE_PROVIDED;

    public Image(String contestName, String startTime, String endTime, String twentyFourHours, int isRunning, String link, int imageId, int flag) {
        this.contestName = contestName;
        this.link = link;
        this.imageId = imageId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isRunning = isRunning;
        this.twentyFourHours = twentyFourHours;
        this.flag = flag;
    }


    public String getContestName() {
        return contestName;
    }

    public String getLink() {
        return link;
    }

    public int getImageId() {
        return imageId;
    }
    public String getStartTime() {
        return startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public boolean hasImage(){
        return imageId != NO_IMAGE_PROVIDED;
    }
    public int getRunningStatus(){
        return isRunning;
    }

    public String getTwentyFourHours(){
        return twentyFourHours;
    }

    public int getFlagValue(){
        return flag;
    }

}