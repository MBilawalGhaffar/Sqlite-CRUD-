package com.example.sqllitepractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {
    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "coursedb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "mycourses";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our course name column
    private static final String NAME_COL = "name";

    // below variable id for our course duration column.
    private static final String DURATION_COL = "duration";

    // below variable for our course description column.
    private static final String DESCRIPTION_COL = "description";

    // below variable is for our course tracks column.
    private static final String TRACKS_COL = "tracks";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query =
                        "CREATE TABLE "+TABLE_NAME+" ("
                        +ID_COL +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                        +NAME_COL+" TEXT,"
                        +DURATION_COL+" TEXT,"
                        +DESCRIPTION_COL+" TEXT, "
                        +TRACKS_COL+" TEXT)";

        String query2 =
                "CREATE TABLE "+TABLE_NAME+" ("
                        +NAME_COL+" TEXT PRIMARY KEY, "
                        +DURATION_COL+" TEXT,"
                        +DESCRIPTION_COL+" TEXT, "
                        +TRACKS_COL+" TEXT)";
        sqLiteDatabase.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addNewCourse(String courseName, String courseDuration, String courseDescription, String courseTracks) {
        SQLiteDatabase database=this.getWritableDatabase();
        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();
        values.put(NAME_COL,courseName);
        values.put(DURATION_COL,courseDuration);
        values.put(DESCRIPTION_COL,courseDescription);
        values.put(TRACKS_COL,courseTracks);
        database.insert(TABLE_NAME,null,values);
        database.close();
    }

    public ArrayList<CourseModal> readCourses() {
        SQLiteDatabase database=this.getReadableDatabase();
        ArrayList<CourseModal> courseModalArrayList = new ArrayList<>();
        Cursor cursor=database.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        if(cursor!=null){
            // moving our cursor to first position.
            if (cursor.moveToFirst()) {
                do {
                    // on below line we are adding the data from cursor to our array list.
                    courseModalArrayList.add(new CourseModal(cursor.getString(0),
                            cursor.getString(3),
                            cursor.getString(1),
                            cursor.getString(2)));
                } while (cursor.moveToNext());
                // moving our cursor to next.
            }
        }
        cursor.close();
        return courseModalArrayList;

    }

    public void updateCourse(String courseName, String courseNameNew, String courseDuration, String courseDescription, String courseTracks) {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, courseNameNew);
        values.put(DURATION_COL, courseDuration);
        values.put(DESCRIPTION_COL, courseDescription);
        values.put(TRACKS_COL, courseTracks);
        database.update(TABLE_NAME,values,"name=?",new String[]{courseName});
        database.close();

    }

    public void deleteCourse(String courseName) {
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(TABLE_NAME,"name=?",new String[]{courseName});
        database.close();
    }
}
