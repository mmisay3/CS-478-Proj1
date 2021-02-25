package edu.mmisay3.cs478.myapplicationtest02; // "Unique ID of your app"
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String EnteredName;

    private Button Button_1;
    private Button Button_2;
    public static final int NAME_ENTRY = 1;

    public  boolean button_2_enabler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, Activity_2_EditContact.class);

        Button_1 = (Button) findViewById(R.id.Button_1);
        Button_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivityForResult(intent, NAME_ENTRY); // 2nd Arg is requestCode, used onActivityResult()
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NAME_ENTRY){ // NAME_ENTRY = 1 (declared above)
            // Fetch name entered from Activity 2
            this.EnteredName = data.getStringExtra(Activity_2_EditContact.EXTRA_TEXT);
            if(resultCode == RESULT_OK){
                Log.i("Act1", "EnteredName = " + EnteredName);
                Button_2 = (Button) findViewById(R.id.Button_2);
                Button_2.setOnClickListener(new View.OnClickListener( ){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, EnteredName);
                        startActivity(intent);
                    }
                });
            }
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Incorrect Input: " + EnteredName, Toast.LENGTH_LONG).show( );
            }
        }
    }
}