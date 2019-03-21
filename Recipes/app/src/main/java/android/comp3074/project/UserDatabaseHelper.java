package android.comp3074.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "users.db";
    private static final String TABLE_NAME = "USERS";

    // Columns
    private static final String FIELD0 = "ID";
    private static final String FIELD1 = "EMAIL";
    private static final String FIELD2 = "NAME";
    private static final String FIELD3 = "PASSWORD";
    private static final String FIELD4 = "PHONE";

    public UserDatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME + "("+ FIELD0 + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                FIELD1 +" TEXT UNIQUE, "+ FIELD2 +" TEXT, " + FIELD3 + " TEXT, " + FIELD4 + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }


    // Insert data
    public boolean insertData(String email, String name, String password, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(FIELD1, email);
        content.put(FIELD2, name);
        content.put(FIELD3, password);
        content.put(FIELD4, phone);
        long result = db.insert(TABLE_NAME, null, content);
        if (result == -1)
            return false;

        db.close();
        return  true;
    }

    // Check if email exists
    public boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME +" WHERE " + FIELD1 +"=?", new String[]{email});
        if(cursor.getCount() > 0)
            return false;

        db.close();
        return true;
    }

    // Check the email and password
    public boolean checkUser(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME +" WHERE "+ FIELD1 +"=? and "+ FIELD3 +"=?",
                new String[]{email, password});
        if(cursor.getCount() <= 0)
            return false;

        db.close();
        return true;
    }

    // Get user information
    public Cursor getUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME +" WHERE "+ FIELD1 +"=?", new String[]{email});
        return cursor;
    }

    // Edit user information
    public boolean editUser(String email, String name, String password, String phone){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("UPDATE "+ TABLE_NAME +" SET "+ FIELD2 + "=?, " + FIELD3 + "=?, "
                        + FIELD4 + "=? WHERE " + FIELD1 + "=?",
                new String[]{name, password, phone, email});
        if(cursor.getCount() == -1)
            return false;

        db.close();
        return true;
    }
}
