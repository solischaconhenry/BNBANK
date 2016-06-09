package cr.ac.itcr.bnbank.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import cr.ac.itcr.bnbank.R;
import cr.ac.itcr.bnbank.app.EndPoints;
import cr.ac.itcr.bnbank.app.MyApplication;

public class Register extends AppCompatActivity {
    private String TAG = AddTransaction.class.getSimpleName();
    EditText etUser;
    EditText etPass;
    EditText etEmail;
    EditText etType;
    Button btnRegister;
    String user;
    String pass;
    String email;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etUser = (EditText)findViewById(R.id.etUserR);
        etPass = (EditText)findViewById(R.id.etPassR);
        etEmail = (EditText)findViewById(R.id.etEmailR);
        etEmail = (EditText)findViewById(R.id.etEmailR);
        etType = (EditText)findViewById(R.id.etTypeR);
        btnRegister = (Button) findViewById(R.id.btnSingUpR);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = etUser.getText().toString();
                pass = etPass.getText().toString();
                email = etEmail.getText().toString();
                type = etType.getText().toString();
                //check if the spaces are full
                if(user.isEmpty() || pass.isEmpty()){
                    Toast e = Toast.makeText(getApplicationContext(),"You have to complete all the spaces",Toast.LENGTH_LONG);
                    e.show();

                }
                //set the url of the of the api request
                String endPoint= EndPoints.NEW_USER;
                StringRequest strReq = new StringRequest(Request.Method.POST,
                        endPoint, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        Log.e(TAG, "VolleyNewT error: " + error.getMessage() + ", code: " + networkResponse);
                        Toast.makeText(getApplicationContext(), "VolleyNewT error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    //parameters to send
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", user);
                        params.put("password", pass);
                        params.put("email",email);
                        params.put("type",type);
                        Log.e(TAG, "Params: " + params.toString());

                        return params;
                    };
                };
                //add to request queue and clean the edittext
                MyApplication.getInstance().addToRequestQueue(strReq);
                Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_LONG).show();
                etUser.setText("");
                etPass.setText("");
                etEmail.setText("");
                etType.setText("");
            }
        });
    }

}
