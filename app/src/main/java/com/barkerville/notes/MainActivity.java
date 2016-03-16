package com.barkerville.notes;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

        private RecyclerView mRecyclerView;
        private LinearLayoutManager mLayoutManager;
        private NoteListItemAdapter mAdapter;
        private Button mButton;
        private Button colorPrefs;
        private EditText mEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotesDBHelper.getInstance(this).getReadableDatabase();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mEditText = (EditText) findViewById(R.id.edit_text);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new NoteListItemAdapter(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Get the text in the EditText
                // Create a new NoteListItem with the text
                // Add the item to the adapter
                // Set the EditText to an empty string
              final String note = mEditText.getText().toString();
              final NoteListItem item = new NoteListItem(note);
              mAdapter.addItem(item);
              mEditText.setText("");
              mLayoutManager.scrollToPosition(0);

              NoteDAO dao = new NoteDAO(MainActivity.this);
              dao.save(item);


            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setColor();
        colorPrefsButton();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == 1){
            if(data.hasExtra("Note")){
                NoteListItem note = (NoteListItem)data.getSerializableExtra("Note");
                Toast.makeText(this, note.getText(), Toast.LENGTH_LONG).show();
                mAdapter.addItem(note);
            }
        }}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Context context = getApplicationContext();
        CharSequence text = "Settings!";
        int duration = Toast.LENGTH_SHORT;



        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            openColorDialog();
            Toast.makeText(context, text, duration).show();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void openColorDialog() {
        final EditText input = new EditText(this);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.setting_color_title);
            alertDialogBuilder.setMessage(R.string.setting_color_message);
            alertDialogBuilder.setView(input);
            alertDialogBuilder.setPositiveButton(R.string.positive_button_label, new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("NOTE_COLOR", value);
                    editor.commit();

                }
        }).setNegativeButton(R.string.negative_button_label, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // No need for code here
            }
        }).show();
    }

    public void setColor() {
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        String color = prefs.getString("NOTE_COLOR", "W");
        if(color.toUpperCase().contains("G")) {
            mRecyclerView.setBackgroundColor(Color.GREEN);
        } else if (color.toUpperCase().contains("R")){
            mRecyclerView.setBackgroundColor(Color.RED);
        } else {
            mRecyclerView.setBackgroundColor(Color.WHITE);
        }

    }

    private void colorPrefsButton() {
        Button colorPrefs = (Button)findViewById(R.id.colorPrefs);
        colorPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ColorPreference.class));
            }
        });
    }


    }


