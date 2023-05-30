package ma.enset.contactappmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class addContact extends AppCompatActivity {

    TextView txtName,txtEmail,txtJob,txtPhone;
    Button btn_add,btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        txtEmail=findViewById(R.id.contact_mail);
        txtName=findViewById(R.id.contact_name);
        txtJob=findViewById(R.id.contact_job);
        txtPhone=findViewById(R.id.contact_phone);
        btn_add=findViewById(R.id.add_cont);
        btn_back=findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(addContact.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String job = txtJob.getText().toString().trim();
                String phone = txtPhone.getText().toString().trim();

                // Vérifiez si les champs sont vides avant d'ajouter le contact
                if (name.isEmpty() || email.isEmpty() || job.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(addContact.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    addContact(name, email, job, phone);
                }
            }
        });

    }
    private void addContact(String name, String email, String job, String phone) {
        String url = "http://192.168.56.1/contacts/addContact.php"; // Remplacez avec l'URL de votre script PHP

        // Créez une StringRequest pour effectuer une requête GET avec les paramètres du nouveau contact
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url +
                "?name=" + name +
                "&email=" + email +
                "&job=" + job +
                "&phone=" + phone,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Traitement de la réponse du serveur (si nécessaire)
                        Toast.makeText(addContact.this, "Contact ajouté avec succès", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Gestion de l'erreur de réponse
                        Toast.makeText(addContact.this, "Erreur lors de l'ajout du contact : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Ajoutez la requête à la RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}