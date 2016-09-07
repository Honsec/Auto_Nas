package genius.baselib.frame.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chief on 2015-12-22.
 */
public  abstract class BaseDB extends SQLiteOpenHelper {

    /*
    *
    *  @Override
        public void onCreate(SQLiteDatabase db) {
            UtilsLog.i("onCreateDB");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + COLUMN_PARTIN.column_create());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion >= newVersion) return;
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTIN);
            onCreate(db);
        }
    *
    *
    * */



    protected Context mContext;

    protected SQLiteDatabase mDB_Writer = null;


    public BaseDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,name , factory, version);
        this.mContext = context;
    }


    public void openDB() {
        try {
            mDB_Writer = getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeDB() {
        try {
            if (mDB_Writer != null) {
                mDB_Writer.close();
                SQLiteDatabase.releaseMemory();
            }
                close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected Cursor fetchAllvalues(String TABLE_NAME) {
        if (mDB_Writer != null)
            return mDB_Writer.query(TABLE_NAME, null, null, null, null, null, null);
        return null;
    }


    protected int isExistRows(String TABLE_NAME) {
        int result = 0;
        Cursor fetchAllValues = fetchAllvalues(TABLE_NAME);
        if (fetchAllValues != null) {
            result = fetchAllValues.getCount();
            fetchAllValues.close();
        }
        return result;
    }





    protected boolean deleteAll(String TABLE_NAME) {
        int id = -1;
        if (mDB_Writer != null) {
            id = mDB_Writer.delete(TABLE_NAME, null, null);
        }
        if (id > 0) {
            return true;
        }
        return false;
    }


    /**
     *
     * @param table
     * @param contentValues
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @return  true이면 성곡
     */
    protected boolean update(String table,ContentValues contentValues, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        long id = -1;

        if (mDB_Writer != null) {

            boolean result =false;
            Cursor query = mDB_Writer.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
            if (query != null) {
                if (query.getCount() > 0) {
                    result = true;
                }
                query.close();
            }

            if (result) {
                id = mDB_Writer.update(table, contentValues, selection,selectionArgs);
            } else {
                id = mDB_Writer.insert(table, null, contentValues);
            }
        }

        if (id < 1) {
            return false;
        } else {
            return true;
        }
    }








    /*--------------------------------------------------------------------------------------------------------------------------------*/
    //ContentProvider





}
