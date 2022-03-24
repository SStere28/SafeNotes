package com.SSI.SafeNotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Meniu extends AppCompatActivity {

    private static final int TIME_INTERVAL = 1500;
    private long mBackPressed;
    private ListView list;
    private ListItemAdapter adapter;
    private List<Note> file;
    private List<Note> filec;
    private Toolbar toolbar;
    private DatabaseHandler db;
    private static final String PREFS_NAME = "SafeNotes";
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meniu);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        //action bar
      toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Safe Notes");
        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setNavigationIcon(R.drawable.ic_settings);
        setSupportActionBar(toolbar);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Meniu.this, Settings.class);
                intent.putExtra("set", 1);
                startActivity(intent);

                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });


        FloatingActionButton plus = (FloatingActionButton) findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Meniu.this, ViewNotes.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        db = new DatabaseHandler(this);
        file = db.getAllNotes();


        Collections.sort(file, new Comparator<Note>() {
            @Override
            public int compare(Note p1, Note p2) {
                return Integer.parseInt(p2.getModified()) - Integer.parseInt(p1.getModified());
            }

        });

        filec = new ArrayList<>();
        for (int i = 0; i < file.size(); i++) {
            filec.add(new Note(file.get(i).id, file.get(i).getTitlu(), file.get(i).getText(), file.get(i).getModified(), sharedPreferences.getString("Text", "c1"), sharedPreferences.getString("Type", "c1")));
        }

        adapter = new ListItemAdapter(this, R.layout.list_item_adapter, filec);
        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent intent = new Intent(Meniu.this, ViewNotes.class);
                intent.putExtra("Text", filec.get(position).getText());
                intent.putExtra("ID", filec.get(position).getID());
                intent.putExtra("Size", filec.get(position).getSize());
                intent.putExtra("Type", filec.get(position).getType());
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        list.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                final int checkedCount = list.getCheckedItemCount();
                mode.setTitle(checkedCount + " Selected");


                getSupportActionBar().hide();
                adapter.toggleSelection(position);
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_file:
                        final SparseBooleanArray selected = adapter
                                .getSelectedIds();
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                db.deleteNote(filec.get(selected.keyAt(i)));
                                adapter.remove(filec.get(selected.keyAt(i)));
                            }

                        }
                        adapter.notifyDataSetChanged();



                        mode.finish();

                        return true;
                    case R.id.select_all:

                        for (int i = 0; i < list.getCount(); i++) {
                            list.setItemChecked(i, true);
                        }

                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.note_menu, menu);

                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                getSupportActionBar().show();
                adapter.removeSelection();


            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }
        });


    }


    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
            finish();
            return;
        } else {
            Toast.makeText(getBaseContext(),
                    "Press back button in order to exit", Toast.LENGTH_SHORT)
                    .show();
        }

        mBackPressed = System.currentTimeMillis();
    }

}
