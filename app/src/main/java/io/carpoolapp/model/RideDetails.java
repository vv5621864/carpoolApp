package io.carpoolapp.model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.carpoolapp.constants.Constants;

public class RideDetails {

    private static final String TAG = "RideDetails";

    String rideId, pickLat, pickLong, pickAddress, dropLat, dropLong, dropAddress, date, time, status;
    int seats;

    public String getPickLat() {
        return pickLat;
    }

    public void setPickLat(String pickLat) {
        this.pickLat = pickLat;
    }

    public String getPickLong() {
        return pickLong;
    }

    public void setPickLong(String pickLong) {
        this.pickLong = pickLong;
    }

    public String getPickAddress() {
        return pickAddress;
    }

    public void setPickAddress(String pickAddress) {
        this.pickAddress = pickAddress;
    }

    public String getDropLat() {
        return dropLat;
    }

    public void setDropLat(String dropLat) {
        this.dropLat = dropLat;
    }

    public String getDropLong() {
        return dropLong;
    }

    public void setDropLong(String dropLong) {
        this.dropLong = dropLong;
    }

    public String getDropAddress() {
        return dropAddress;
    }

    public void setDropAddress(String dropAddress) {
        this.dropAddress = dropAddress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    private static final RideDetails ourInstance = new RideDetails();

    public static RideDetails getInstance() {
        return ourInstance;
    }

    private RideDetails() {
    }

    public String getDateTime() {
        String myDate = this.date + " " + this.time;
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_PATTERN);
        Date date = null;
        try {
            date = sdf.parse(myDate);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return String.valueOf(date.getTime());
    }
}
