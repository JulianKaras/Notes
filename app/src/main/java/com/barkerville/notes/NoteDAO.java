package com.barkerville.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by JulianK on 05/03/2016.
 */
public class NoteDAO {

    private Context context;

    public NoteDAO(Context context) {

        this.context = context;
    }

    public void save(NoteListItem note) {

        NotesDBHelper helper = new NotesDBHelper(context); // removed .getInstance method from between context and new NotesDBHelper
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NotesDBContract.Note.COLUMN_NAME_NOTE_TEXT, note.getText());
        values.put(NotesDBContract.Note.COLUMN_NAME_STATUS, note.getStatus());
        values.put(NotesDBContract.Note.COLUMN_NAME_NOTE_DATE, (note.getDate().getTimeInMillis()/1000));

        db.insert(NotesDBContract.Note.TABLE_NAME, null, values);
    }

    public List<NoteListItem> list() {
        NotesDBHelper helper = new NotesDBHelper(context); // removed .getInstance method from between context and new NotesDBHelper
        SQLiteDatabase db = helper.getReadableDatabase();



        String[] projection = {                                 // shows which columns you'd like to query.
                NotesDBContract.Note.COLUMN_NAME_ID,
                NotesDBContract.Note.COLUMN_NAME_NOTE_TEXT,
                NotesDBContract.Note.COLUMN_NAME_STATUS,
                NotesDBContract.Note.COLUMN_NAME_NOTE_DATE
        };

        String sortOrder = NotesDBContract.Note.COLUMN_NAME_NOTE_DATE + "DESC";

        Cursor c = db.query(
                NotesDBContract.Note.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<NoteListItem> notes = new ArrayList<NoteListItem>();

        while (c.moveToNext()) {

            String text = c.getString(c.getColumnIndex(NotesDBContract.Note.COLUMN_NAME_NOTE_TEXT));
            String status = c.getString(c.getColumnIndex(NotesDBContract.Note.COLUMN_NAME_STATUS));
            Calendar date = new GregorianCalendar();
            date.setTimeInMillis(c.getLong(c.getColumnIndex(NotesDBContract.Note.COLUMN_NAME_NOTE_DATE)) * 1000);
            Long id = c.getLong(c.getColumnIndex(NotesDBContract.Note.COLUMN_NAME_ID));
            notes.add(new NoteListItem(id, text, status, date));
        }
            Log.i("NOTES", notes.size() + "notes loaded");
            return notes;

    }

    public void update (NoteListItem noteListItem){
        NotesDBHelper helper = NotesDBHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();//get a reference to the SQLite database
        ContentValues values = new ContentValues();
        values.put(NotesDBContract.Note.COLUMN_NAME_NOTE_TEXT, noteListItem.getText()); // specify the column and values the user wants to change

        String selection = NotesDBContract.Note.COLUMN_NAME_ID + " =?";
        String[] selectionArgs = {String.valueOf(noteListItem.getId())};

        int count = db.update(
                NotesDBContract.Note.TABLE_NAME,
                values,
                selection,
                selectionArgs);

     }

    public void delete (NoteListItem noteListItem) {
        String selection = NotesDBContract.Note.COLUMN_NAME_ID + " = ?";
        String [] selectionArgs = {String.valueOf(noteListItem.getId())};  // this array of values will replace the question  mark in the selection string
        NotesDBHelper helper = NotesDBHelper.getInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();         // get a reference to the SQLite database
        db.delete(NotesDBContract.Note.TABLE_NAME, selection, selectionArgs);   // executes the delete on the database

    }
}
