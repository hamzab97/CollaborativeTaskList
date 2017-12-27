
package com.example.todo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";//used in log cat

    private EditText editText;
    private Button addButton;
    private ListView listView;

    //arraylist to store todolist items
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;

    //firebase database
    private DatabaseReference database;

    private Context context;
    private int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.item);
        addButton = (Button) findViewById(R.id.addItem);
        listView = (ListView) findViewById(R.id.todolist);
        context = getApplicationContext();
        duration = Toast.LENGTH_SHORT;

        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance().getReference();
        database.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String data = dataSnapshot.getValue(String.class);
                arrayList.add(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                arrayList.remove(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        onaddButtonClick();
    }

    public void onaddButtonClick(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = editText.getText().toString();
                if (result.equals("")) {
                    Toast.makeText(context, "No item to add", duration).show();
                }
                else if (arrayList.contains(result)){
                    //create toast to alert user that list already contains item
                    Toast.makeText(context, "Item already exists in list", duration).show();
                }
                else {
                    database.push().setValue(result);
                }
                editText.setText(null);
            }
        });
    }
}