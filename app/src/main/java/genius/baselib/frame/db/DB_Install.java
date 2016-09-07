package genius.baselib.frame.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import genius.baselib.frame.base.BaseDB;

/**
 * Created by Hongsec on 2016-09-06.
 */
public class DB_Install extends BaseDB {
    public static final String TABLE_PARTIN = "db_install";
    protected final static int DB_APP_VERSION = 2;


    public DB_Install(Context context,SQLiteDatabase.CursorFactory factory) {
        super(context, TABLE_PARTIN, factory, DB_APP_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS " + COLUMN_PARTIN.column_create());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (oldVersion >= newVersion) return;
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTIN);
//        onCreate(db);
    }


//    private static class COLUMN_INSTALL {
//        private static final String pkg = "pkg";
//        private static final String state = "state";
//
//        private static String column_create() {
//            return TABLE_PARTIN + "(" +
//                    pkg + " TEXT PRIMARY KEY," +
//                    state + " TEXT DEFAULT ''" +
//                    ");";
//        }
//    }

}
