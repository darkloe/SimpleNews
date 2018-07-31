package database.NewsDbSchema;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import database.NewsDbSchema.NewsDbSchema.NewsTable;

public class NewsBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "newsBase.db";

    public NewsBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + NewsTable.NAME + "(" +
                        " _id integer primary key autoincrement, " +
                        NewsTable.Cols.UUID + ", " +
                        NewsTable.Cols.TITLE + ", " +
                        NewsTable.Cols.SOURCE + ", " +
                        NewsTable.Cols.DES + ", " +
                        NewsTable.Cols.URL +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
