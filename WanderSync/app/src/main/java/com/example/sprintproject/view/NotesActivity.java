package com.example.sprintproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sprintproject.R;
import com.example.sprintproject.model.DatabaseManager;

public class NotesActivity  extends FeatureActivityBase {

    private DatabaseManager dbManager;

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_notes);
        super.onCreate(savedInstanceState);
        Button addNotes = findViewById(R.id.AddNoteButton);
        Button returnButton = findViewById(R.id.NotesCancelButton);
        EditText notesText = findViewById(R.id.NotesEditText);

        if (!dbManager.getCurrentTrip().getNotes().isEmpty()) {
            notesText.setText(dbManager.getCurrentTrip().getNotes());
        }
        dbManager = DatabaseManager.getInstance();

        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.addNotes(notesText.getText().toString());
                Intent intent = new Intent(NotesActivity.this, LogisticsActivity.class);
                startActivity(intent);
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this, LogisticsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return 0;
    }
}
