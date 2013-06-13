package com.HACK.codersbestfriend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Simple tasks database access helper class.
 *
 * Modified from the notepad example.
 *
 * Defines the basic CRUD operations
 * for the notepad example, and gives the ability to list all notes as well as
 * retrieve or modify a specific note.
 *
 */
public class CodersBFDatabaseAdapter {


        public static final String KEY_NAME = "name";
        public static final String KEY_TAGS = "tags";
        public static final String KEY_ROWID = "_id";

        private static final String TAG = "CodersBFDatabaseAdapter";
        private DatabaseHelper mDbHelper;
        private SQLiteDatabase mDb;

        private static final String DATABASE_NAME = "data";
        private static final String DATABASE_TABLE = "tasks";
        private static final int DATABASE_VERSION = 1;

        /**
         * Database creation sql statement
         */
        private static final String DATABASE_CREATE =
                "create table "+DATABASE_TABLE+" (_id integer primary key autoincrement, "
                        + "name text not null, tags text);";

        private final Context mCtx;

        private static class DatabaseHelper extends SQLiteOpenHelper {

            DatabaseHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(DATABASE_CREATE);
                ContentValues initialValues = new ContentValues();
                initialValues.put(KEY_NAME, "Swipe to the right to delete");
                initialValues.put(KEY_TAGS, "");
                db.insert(DATABASE_TABLE,null,initialValues);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
                db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
                onCreate(db);
            }
        }

        /**
         * Constructor - takes the context to allow the database to be
         * opened/created
         *
         * @param ctx the Context within which to work
         */
        public CodersBFDatabaseAdapter(Context ctx) {
            this.mCtx = ctx;
        }

        /**
         * Open the tasks database. If it cannot be opened, try to create a new
         * instance of the database. If it cannot be created, throw an exception to
         * signal the failure
         *
         * @return this (self reference, allowing this to be chained in an
         *         initialization call)
         * @throws SQLException if the database could be neither opened or created
         */
        public CodersBFDatabaseAdapter open() throws SQLException {
            mDbHelper = new DatabaseHelper(mCtx);
            mDb = mDbHelper.getWritableDatabase();
            return this;
        }

        public void close() {
            mDbHelper.close();
        }


        /**
         * Create a new task using the name and tags provided. If the note is
         * successfully created return the new rowId for that note, otherwise return
         * a -1 to indicate failure.
         *
         * @param task the task
         * @return rowId or -1 if failed
         */
        public long createTask(Task task) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_NAME, task.getTitle());
            Collection<Tag> tags = task.getTags();
            StringBuilder tagString = new StringBuilder();
            for (Tag e:tags)
            {
                tagString.append(e.toString()+" ");
            }
            initialValues.put(KEY_TAGS, tagString.toString());

            long rowId = mDb.insert(DATABASE_TABLE, null, initialValues);
            task.setRowID(rowId);
            return rowId;
        }

        /**
         * Delete the task with the given rowId
         *
         * @param task task to be deleted
         * @return true if deleted, false otherwise
         */
        public boolean deleteTask(Task task) {

            return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + task.getRowID(), null) > 0;
        }

        /**
         * Return a Cursor over the list of all tasks in the database
         *
         * @return Cursor over all tasks
         */
        public Cursor fetchAllTasks() {

            return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                    KEY_TAGS}, null, null, null, null, null);
        }

        /**
         * Return a Cursor positioned at the task that matches the given rowId
         *
         * @param rowId id of task to retrieve
         * @return Cursor positioned to matching task, if found
         * @throws SQLException if task could not be found/retrieved
         */
        public Cursor fetchTask(long rowId) throws SQLException {

            Cursor mCursor =

                    mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                            KEY_NAME, KEY_TAGS}, KEY_ROWID + "=" + rowId, null,
                            null, null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
            }
            return mCursor;

        }

        /**
         * Update the task using the details provided. The task to be updated is
         * specified using the rowId, and it is altered to use the name and tags
         * values passed in
         *
         * @param rowId id of task to update
         * @param name value to set task name to
         * @param tags value to set task tags to
         * @return true if the note was successfully updated, false otherwise
         */
        public boolean updateTask(long rowId, String name, String tags) {
            ContentValues args = new ContentValues();
            args.put(KEY_NAME, name);
            args.put(KEY_TAGS, tags);

            return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
        }
    }
