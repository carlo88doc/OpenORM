package openorm.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by carlo on 03/04/15.
 */
public class OrmOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static OrmOpenHelper _instance;

    private OrmOpenHelper(String dbName, Context context) {
        super(context, dbName, null, DATABASE_VERSION);
    }

    public static OrmOpenHelper getInstance(Context context, String databaseName) {
        _instance = new OrmOpenHelper(databaseName, context);
        return _instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void execQuery(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        database.execSQL(sql);
    }

    public Cursor execQuery(String sql, String[] selectionArgs) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, selectionArgs);
    }

    public int execUpdate(String table, ContentValues values, String[] whereColumns, String[] whereArgs) {

        ContentValues cvNormalized = new ContentValues();
        String whereClause = "";

        for (String key : values.keySet()) {
            if (key.contains(".")) {
                String newKey = key.substring(key.lastIndexOf(".") + 1, key.length());
                cvNormalized.put(newKey, (String) values.get(key));
            }
        }

        for (String where : whereColumns) {
            if (where.contains(".")) {
                where = where.substring(where.lastIndexOf(".") + 1, where.length());
                where = where + "=?";
            }

            if (whereClause.length() == 0) {
                whereClause = where;
            } else {
                whereClause = whereClause + " AND " + where;
            }
        }

        SQLiteDatabase database = getReadableDatabase();
        return database.update(table, cvNormalized, whereClause, whereArgs);
    }
}
