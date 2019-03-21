package android.comp3074.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ForumDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "forums.db";
    private static final String TABLE_NAME = "FORUMS";

    // Columns
    private static final String FIELD0 = "ID";
    private static final String FIELD1 = "NAME";
    private static final String FIELD2 = "TITLE";
    private static final String FIELD3 = "DETAILS";
    private static final String FIELD4 = "DATES";

    // Create Table
    private static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME + "("+ FIELD0 + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            FIELD1 +" TEXT , "+ FIELD2 +" TEXT, " + FIELD3 + " TEXT, " + FIELD4 + " DATE DEFAULT (DATETIME('NOW', 'LOCALTIME')))";

    // Drop Table
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public ForumDatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    // Insert data
    public boolean insertData(String name, String title, String details){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(FIELD1, name);
        content.put(FIELD2, title);
        content.put(FIELD3, details);
        long result = db.insert(TABLE_NAME, null, content);
        return result != -1;
    }

    // Return all records which are in the table
    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
    }

    // Get information for specific record
    public Cursor getDataByTitle(String title){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME +" WHERE " + FIELD2 +"=?", new String[]{title});
        return cursor;
    }

    // Edit data
    public boolean editData(String id, String title, String details){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("UPDATE "+ TABLE_NAME +" SET "+ FIELD2 + "=?, " + FIELD3 + "=?, " + FIELD4 +
                "=DATETIME('NOW', 'LOCALTIME') WHERE " +
                FIELD0 + "=?", new String[]{title, details, id});
        //return cursor.getCount() != -1;
        if(cursor.getCount() == -1)
            return false;

        db.close();
        return true;
    }

    // Delete data
    public boolean deleteData(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TABLE_NAME, FIELD0 + " = ?", new String[]{id}) > 0;
    }
}
