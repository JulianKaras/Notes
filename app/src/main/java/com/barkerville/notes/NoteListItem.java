package com.barkerville.notes;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

/**
 * Created by JulianK on 19/02/2016.
 */
public class NoteListItem implements Serializable{

    private long id;
    private String text;
    private String status;
    private Calendar date;

    public NoteListItem (String text){

        this.text = text;

    }

   //*** public NoteListItem(String text, String status, Calendar date) {
   //*** }

    public String getText (){
        return this.text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
