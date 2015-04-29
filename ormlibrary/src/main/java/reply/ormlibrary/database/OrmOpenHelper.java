package reply.ormlibrary.database;

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

        if (_instance == null) {
            _instance = new OrmOpenHelper(databaseName, context);
        }

        return _instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void execQuery(String sql) {
        getReadableDatabase().execSQL(sql);
    }

    public Cursor execRead(String sql, String[] selectionArgs) {
        return getReadableDatabase().rawQuery(sql, selectionArgs);
    }

    public long execInsert(String tableName, String nullColumnHack, ContentValues contentValues) {
        return getWritableDatabase().insert(tableName, nullColumnHack, contentValues);
    }

    public long execDelete(String tableName, String[] whereColumns, String[] whereArgs) {
        String whereClause = getWhereClause(whereColumns);
        return getWritableDatabase().delete(tableName, whereClause, whereArgs);
    }

    public int execUpdate(String table, ContentValues values, String[] whereColumns, String[] whereArgs) {

        ContentValues cvNormalized = new ContentValues();
        String whereClause = getWhereClause(whereColumns);

        for (String key : values.keySet()) {
            String value = (String) values.get(key);
            if (key.contains(".")) {
                String newKey = key.substring(key.lastIndexOf(".") + 1, key.length());

                cvNormalized.put(newKey, value);
            } else {
                cvNormalized.put(key, value);
            }
        }

        return getWritableDatabase().update(table, cvNormalized, whereClause, whereArgs);
    }

    private String getWhereClause(String[] whereColumns) {
        String whereClause = "";
        for (String where : whereColumns) {
            if (where.contains(".")) {
                where = where.substring(where.lastIndexOf(".") + 1, where.length());
            }

            where = where + "=?";

            if (whereClause.length() == 0) {
                whereClause = where;
            } else {
                whereClause = whereClause + " AND " + where;
            }
        }

        return whereClause;
    }

}
