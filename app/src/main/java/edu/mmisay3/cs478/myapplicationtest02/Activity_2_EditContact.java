package edu.mmisay3.cs478.myapplicationtest02;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.*;

public class Activity_2_EditContact extends AppCompatActivity {
    private Button   Button_Done;
    private EditText EditText_Name;
    private String   EnteredName; // Name entered by user converted to String
    public static final String EXTRA_TEXT = "edu.mmisay3.cs478.myapplicationtest02.EXTRA_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2__edit_contact);
        setTitle("Activity 2 - Enter Legal Name");

        EditText_Name = (EditText) findViewById(R.id.EditText_name);

        // Handler for the text field, EditText_Name
        EditText_Name.setOnEditorActionListener(editorListener);

        Intent intent = getIntent( ); // Get the intent passed from ActivityI (activity_main)

        this.Button_Done = (Button) findViewById(R.id.Button_Done);
        Button_Done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Handler_imeDONE_ButtonDone( );
                }
            });
    }

    // Handler for the IME_ACTION_DONE button found on Softkeyboard.
    private TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener(){
        @SuppressLint("WrongConstant")
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch(actionId){
                case EditorInfo.IME_ACTION_DONE:
                    Handler_imeDONE_ButtonDone( );
                    break;
            }
            return false;
        }
    };

    // Method used for both the IME_DONE (Softkeyboard) and the Done Button
    private void Handler_imeDONE_ButtonDone( ){
        if(EditText_Name.getText().length() > 0) {
            EnteredName = EditText_Name.getText().toString(); // Get the name entered by user then convert to String
            Activity_setResult();
            finish();
        }
        else{
            Toast.makeText(Activity_2_EditContact.this, "No input entry", Toast.LENGTH_SHORT).show();
        }
    }

    // Returns true if the entered name is legal
    // Else, return false for illegal name entry
    private boolean NameIsLegal(String name){
        name = name.trim( ).replaceAll(" +", " ");
        int space_count = 0;
        boolean hasDigit = false;

        for(int i = 0; i < name.length( ); ++i){
            if(name.charAt(i) == ' ')
                ++space_count;
            if(Character.isDigit(name.charAt(i)))
                hasDigit = true;
        }

        if(space_count < 1 || (hasDigit))
            return  false;
        return true;
    }

    // Method for sending result back to startActivityForResult()
    // from the MainActivity.
    private void Activity_setResult( ){
        if(NameIsLegal(EnteredName)){ // int resultCode = RESULT_OK
            Intent resultIntent = new Intent( );
            resultIntent.putExtra(EXTRA_TEXT, EnteredName);
            setResult(RESULT_OK, resultIntent);
        }
        else{  // int resultCOde = RESULT_CANCELED
            Intent resultIntent = new Intent( );
            resultIntent.putExtra(EXTRA_TEXT, EnteredName);
            setResult(RESULT_CANCELED, resultIntent);
        }
    }
}