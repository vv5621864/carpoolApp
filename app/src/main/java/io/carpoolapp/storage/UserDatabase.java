package io.carpoolapp.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import io.carpoolapp.R;
import io.carpoolapp.constants.Constants;
import io.carpoolapp.model.UserDetails;

public class UserDatabase extends SQLiteOpenHelper {

    private Context context = null;
    private SQLiteDatabase db = null;

    public UserDatabase(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = new StringBuilder().append("CREATE TABLE ").append(Constants.TABLE_NAME).append(" ( ").
                append(Constants.MOBILE).append(" VARCHAR(10) PRIMARY KEY, ").append(Constants.GENDER).append(" VARCHAR(6), ").
                append(Constants.NAME).append(" VARCHAR(30), ").append(Constants.EMAIL).append(" VARCHAR(40), ").append(Constants.PASSWORD).
                append(" VARCHAR(40), ").append(Constants.BIRTH_YEAR).append(" VARCHAR(4), ").append(Constants.PROFILE_CREATION_TIME).
                append(" VARCHAR(20), ").append(Constants.PROFILE_LAST_MODIFICATION_TIME).append(" VARCHAR(20), ").
                append(Constants.PROFILE_URI).append(" VARCHAR(50) );").toString();
        db.execSQL(createUserTable);
        String createRideTable = new StringBuilder().append("CREATE TABLE ").append(Constants.RIDE_TABLE_NAME).append(" (").append(Constants.RIDE_ID).append(" VARCHAR(20) PRIMARY KEY, ").append(Constants.DRIVER_ID).append(" VARCHAR(10), ").append(Constants.PICK_LAT).append(" VARCHAR(15), ").append(Constants.PICK_LONG).append(" VARCHAR(15), ").append(Constants.PICK_ADDRESS).append(" VARCHAR(150), ").append(Constants.DROP_LAT).append(" VARCHAR(15), ").append(Constants.DROP_LONG).append(" VARCHAR(15), ").append(Constants.DROP_ADDRESS).append(" VARCHAR(150), ").append(Constants.DATE_TIME).append(" VARCHAR(20), ").append(Constants.SEATS_AVAILABLE).append(" NUMBER, ").append(Constants.STATUS).append(" VARCHAR(10));").toString();
        db.execSQL(createRideTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(context.getString(R.string.dropTable) + Constants.TABLE_NAME);
        onCreate(db);
    }

    public String addTasks(UserDetails userDetails) {
        String status = null;
        Cursor res = null;
        try {
            res = db.rawQuery("SELECT " + Constants.MOBILE + " FROM " + Constants.TABLE_NAME + " WHERE " + Constants.MOBILE + " = " + userDetails.getMobile(), null);
        } catch (Exception e) {
            Log.e(context.getString(R.string.error), "" + e.getMessage());
            return status = context.getString(R.string.error);
        }
        if (res != null && res.moveToNext()) {
            return status = context.getString(R.string.mobileExist);
        } else {
            ContentValues values = new ContentValues();
            values.put(Constants.MOBILE, userDetails.getMobile());
            values.put(Constants.GENDER, userDetails.getGender());
            values.put(Constants.NAME, userDetails.getName());
            values.put(Constants.BIRTH_YEAR, userDetails.getYearOfBirth());
            values.put(Constants.EMAIL, userDetails.getEmail());
            values.put(Constants.PASSWORD, userDetails.getPassword());
            values.put(Constants.PROFILE_CREATION_TIME, userDetails.getProfileCreationTime());
            values.put(Constants.PROFILE_LAST_MODIFICATION_TIME, userDetails.getProfileModificationTime());
            long rowId = db.insert(Constants.TABLE_NAME, null, values);
            if (rowId == -1) {
                return status = context.getString(R.string.error);
            } else {
                return status = context.getString(R.string.success);
            }
        }
    }

    public Cursor getUserDetails(String Id) {
        Cursor res = null;
        try {
            res = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.MOBILE + " = " + Id, null);
        } catch (Exception e) {
            return null;
        }
        return res;
    }

    public void addProfilePhoto(String Id, String uri) {
        ContentValues values = new ContentValues();
        values.put(Constants.PROFILE_URI, uri);
        String query = new StringBuilder().append("UPDATE ").append(Constants.TABLE_NAME).append(" SET ").append(Constants.PROFILE_URI).append(" = '").append(uri.toString()).append("' WHERE ").append(Constants.MOBILE).append(" = ").append(Id).toString();
        db.execSQL(query);
    }

    public Uri getProfileUri(String Id) {
        Uri uri = null;
        Cursor res = null;
        try {
            res = db.rawQuery("SELECT " + Constants.PROFILE_URI + " FROM " + Constants.TABLE_NAME + " WHERE " + Constants.MOBILE + " = " + Id, null);
            while (res.moveToNext()) {
                String prifileUri = res.getString(0);
                uri = Uri.parse(prifileUri);
            }
        } catch (Exception e) {
            return null;
        }
        return uri;
    }

    public Cursor loginValidate(String id, String pass) {
        String Regex = "[0-9]{10}";
        Cursor res = null;
        try {
            res = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.MOBILE + " = " + id, null);
        } catch (Exception e) {
            return null;
        }
        if (res != null && res.moveToNext()) {
            if (res.getString(4).equalsIgnoreCase(pass)) {
                return res;
            }
            return null;
        } else {
            return null;
        }
    }
}