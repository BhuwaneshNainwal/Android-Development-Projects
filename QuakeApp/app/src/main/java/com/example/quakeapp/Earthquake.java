package com.example.quakeapp;

public class Earthquake {

    private double mMagnitudeId;

    // Android version number (e.g. 2.3-2.7, 3.0-3.2.6, 4.0-4.0.4)
    private String mNameId;

    // Drawable resource ID
    private String mDateId;

    private long mTimeInMilliseconds;

    private String mPrefixId;

    private String mSuffixId;
    /*
     * Create a new AndroidFlavor object.
     *
     * @param vName is the name of the Android version (e.g. Gingerbread)
     * @param vNumber is the corresponding Android version number (e.g. 2.3-2.7)
     * @param image is drawable reference ID that corresponds to the Android version
     * */
    public Earthquake(double MagnitudeId , String prefix , String suffix , long timeInMilliSeconds)
    {
        mMagnitudeId = MagnitudeId;
        mPrefixId = prefix;
        mSuffixId = suffix;
        mTimeInMilliseconds = timeInMilliSeconds;
    }

    /**
     * Get the name of the Android version
     */
    public double getmMagnitudeId() {
        return mMagnitudeId;
    }

    /**
     * Get the Android version number
     */

    /**
     * Get the image resource ID
     */
    public long getTimeInMilliSeconds() {
        return mTimeInMilliseconds;
    }

    public String getmPrefixId() {
        return mPrefixId;
    }
    public String getmSuffixId() {
        return mSuffixId;
    }

}
