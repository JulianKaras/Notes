package com.barkerville.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import java.util.Map;
import java.util.Set;

public class ColorPreference extends AppCompatActivity {

    RadioButton backRed, backGreen, backWhite, foreYellow,foreGrey, forePurple, foreBlack;
    Button saveButton;
    String colorSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_preference);

        backRed = (RadioButton) findViewById(R.id.backRed);
        backGreen = (RadioButton) findViewById(R.id.backGreen);
        backWhite = (RadioButton) findViewById(R.id.backWhite);

        backRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backRed.setChecked(true);
                backGreen.setChecked(false);
                backWhite.setChecked(false);

            }
        });

        backGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backRed.setChecked(false);
                backGreen.setChecked(true);
                backWhite.setChecked(false);

            }
        });

        backWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backRed.setChecked(false);
                backGreen.setChecked(false);
                backWhite.setChecked(true);

            }
        });

        foreYellow = (RadioButton) findViewById(R.id.foreYellow);
        foreGrey = (RadioButton) findViewById(R.id.foreGrey);
        forePurple = (RadioButton) findViewById(R.id.forePurple);
        foreBlack = (RadioButton)findViewById(R.id.foreBlack);

        foreYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foreYellow.setChecked(true);
                foreGrey.setChecked(false);
                forePurple.setChecked(false);
                foreBlack.setChecked(false);

            }
        });

        foreGrey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foreYellow.setChecked(false);
                foreGrey.setChecked(true);
                forePurple.setChecked(false);
                foreBlack.setChecked(false);

            }
        });

        forePurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foreYellow.setChecked(false);
                foreGrey.setChecked(false);
                forePurple.setChecked(true);
                foreBlack.setChecked(false);

            }
        });

        foreBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foreYellow.setChecked(false);
                foreGrey.setChecked(false);
                forePurple.setChecked(false);
                foreBlack.setChecked(true);

            }
        });

        saveButton = (Button)findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences forePrefs = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = forePrefs.edit();
                editor.setChecked(true);

            };







        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
