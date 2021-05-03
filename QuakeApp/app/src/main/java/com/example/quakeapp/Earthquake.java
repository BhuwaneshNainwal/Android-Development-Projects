package com.example.quakeapp;

public class Earthquake {

    private String mMagnitudeId;

    // Android version number (e.g. 2.3-2.7, 3.0-3.2.6, 4.0-4.0.4)
    private String mNameId;

    // Drawable resource ID
    private String mDateId;

    private long mTimeInMilliseconds;

    /*
     * Create a new AndroidFlavor object.
     *
     * @param vName is the name of the Android version (e.g. Gingerbread)
     * @param vNumber is the corresponding Android version number (e.g. 2.3-2.7)
     * @param image is drawable reference ID that corresponds to the Android version
     * */
    public Earthquake(String MagnitudeId , String nameId , long timeInMilliSeconds)
    {
        mMagnitudeId = MagnitudeId;
        mNameId = nameId;
        mTimeInMilliseconds = timeInMilliSeconds;
    }

    /**
     * Get the name of the Android version
     */
    public String getmMagnitudeId() {
        return mMagnitudeId;
    }

    /**
     * Get the Android version number
     */
    public String getmNameId() {
        return mNameId;
    }

    /**
     * Get the image resource ID
     */
    public long getTimeInMilliSeconds() {
        return mTimeInMilliseconds;
    }

}
