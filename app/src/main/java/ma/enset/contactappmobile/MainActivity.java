package ma.enset.contactappmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn_add;
    List<Contact> contactList = new ArrayList<>(); // Create an empty list to hold contacts
    ContactAdapter contactAdapter = new ContactAdapter(contactList);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        btn_add=findViewById(R.id.add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, addContact.class);
                startActivity(i);
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contactAdapter);

        //Log.d(TAG, "Open fetch Contacts");
        fetchContacts();
        //Log.d(TAG, "Close fetch Contacts");
    }
    private void fetchContacts() {
        String url = "http://192.168.56.1/contacts/getAllContacts.php"; // Replace with your PHP script URL

        // Create a StringRequest to make a GET request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Parse the response JSON using Gson
                        Gson gson = new Gson();
                        Contact[] contacts = gson.fromJson(response, Contact[].class);

                        // Clear the existing contact list
                        contactList.clear();

                        // Add the fetched contacts to the contact list
                        contactList.addAll(Arrays.asList(contacts));

                        // Notify the adapter that the data has changed
                        contactAdapter.notifyDataSetChanged();
                        int cont=contactAdapter.getItemCount();
                        Log.i("count","count : "+cont);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        Toast.makeText(MainActivity.this, "Error fetching contacts: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}