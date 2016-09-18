package genius.baselib.frame.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import genius.baselib.frame.base.BaseDB;

/**
 * Created by Hongsec on 2016-09-06.
 */
public class DB_Install extends BaseDB {
    public static final String DB_NAME = "db_apps";
    public static final String TABLE_INSTALLED = "db_install";
    protected final static int DB_APP_VERSION = 4;


    public DB_Install(Context context,SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory, DB_APP_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + COLUMN_INSTALL.column_create());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) return;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTALLED);
        onCreate(db);
    }


    public void insertOrupdate(String pkg , String state){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_INSTALL.pkg,pkg);
        contentValues.put(COLUMN_INSTALL.state,state);

        update(TABLE_INSTALLED,contentValues,null,COLUMN_INSTALL.pkg+"=?",new String[]{pkg},null,null,null);
    }



    private static class COLUMN_INSTALL {
        private static final String pkg = "pkg";
        private static final String state = "state";

        private static String column_create() {
            return TABLE_INSTALLED + "(" +
                    pkg + " TEXT PRIMARY KEY," +
                    state + " TEXT DEFAULT ''" +
                    ");";
        }
    }

}
