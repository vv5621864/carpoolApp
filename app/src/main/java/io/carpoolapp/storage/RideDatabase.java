package io.carpoolapp.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import io.carpoolapp.constants.Constants;
import io.carpoolapp.model.RideDetails;
import io.carpoolapp.model.UserDetails;

public class RideDatabase extends SQLiteOpenHelper {

    private Context context;
    private RideDetails rideDetails = null;
    private UserDetails userDetails = null;

    public RideDatabase(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        SQLiteDatabase db = getWritableDatabase();
        this.context = context;
        rideDetails = RideDetails.getInstance();
        userDetails = UserDetails.getInstance();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String query = new StringBuilder().append("CREATE TABLE ").append(Constants.RIDE_TABLE_NAME).append(" (").append(Constants.RIDE_ID).append(" VARCHAR(20), ").append(Constants.DRIVER_ID).append(" VARCHAR(10), ").append(Constants.PICK_LAT).append(" VARCHAR(15), ").append(Constants.PICK_LONG).append(" VARCHAR(15), ").append(Constants.PICK_ADDRESS).append(" VARCHAR(150), ").append(Constants.DROP_LAT).append(" VARCHAR(15), ").append(Constants.DROP_LONG).append(" VARCHAR(15), ").append(Constants.DROP_ADDRESS).append(" VARCHAR(150), ").append(Constants.DATE_TIME).append(" VARCHAR(20), ").append(Constants.SEATS_AVAILABLE).append(" NUMBER, ").append(Constants.STATUS).append(" VARCHAR(10))").toString();
//        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.RIDE_TABLE_NAME);
        onCreate(db);
    }

    public boolean createRide() {
        ContentValues values = new ContentValues();
        values.put(Constants.RIDE_ID, rideDetails.getRideId());
        values.put(Constants.DRIVER_ID, userDetails.getMobile());
        values.put(Constants.PICK_LAT, rideDetails.getPickLat());
        values.put(Constants.PICK_LONG, rideDetails.getPickLong());
        values.put(Constants.PICK_ADDRESS, rideDetails.getPickAddress());
        values.put(Constants.DROP_LAT, rideDetails.getDropLat());
        values.put(Constants.DROP_LONG, rideDetails.getDropLong());
        values.put(Constants.DROP_ADDRESS, rideDetails.getDropAddress());
        values.put(Constants.DATE_TIME, rideDetails.getDateTime());
        values.put(Constants.SEATS_AVAILABLE, rideDetails.getSeats());
        values.put(Constants.STATUS, Constants.INITIATED);
        SQLiteDatabase db = getWritableDatabase();
        long row_id = db.insert(Constants.RIDE_TABLE_NAME, null, values);
        if (row_id == -1) {
            return false;
        }
        return true;
    }

    public void findRide() {
//        Location.distanceBetween();
    }
}



