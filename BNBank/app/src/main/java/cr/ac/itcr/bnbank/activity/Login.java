package cr.ac.itcr.bnbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cr.ac.itcr.bnbank.R;
import cr.ac.itcr.bnbank.app.EndPoints;
import cr.ac.itcr.bnbank.app.MyApplication;
import cr.ac.itcr.bnbank.model.User;

public class Login extends AppCompatActivity {
    private String TAG = AddTransaction.class.getSimpleName();
    EditText etUser;
    EditText etPass;
    Button btnLogin;
    TextView lbSingUp;
    String user;
    String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etUser = (EditText)findViewById(R.id.etUser);
        etPass = (EditText)findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        lbSingUp = (TextView) findViewById(R.id.lbNeedAc);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = etUser.getText().toString();
                pass = etPass.getText().toString();
                if(user.isEmpty() || pass.isEmpty()){
                    Toast e = Toast.makeText(getApplicationContext(),"You have to complete all the spaces",Toast.LENGTH_LONG);
                    e.show();

                }
                String endPoint= EndPoints.LOGIN;
                StringRequest strReq = new StringRequest(Request.Method.POST,
                        endPoint, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response: " + response);

                        try {
                            JSONObject obj = new JSONObject(response);

                            // check for error flag
                            if (obj.getInt("statusCode") == 200) {

                                JSONObject userObj = obj.getJSONObject("data");
                                User user = new User(userObj.getString("user"),
                                        userObj.getString("password"),
                                        userObj.getString("_id"),
                                        userObj.getString("email"));

                                // storing user in shared preferences
                               // MyApplication.getInstance().getPrefManager().storeUser(user);

                                // start main activity
                                Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                                startActivity(intent);

                            } else {
                                // login error - simply toast the message
                                Toast.makeText(getApplicationContext(), "" + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            Log.e(TAG, "json parsing error: " + e.getMessage());
                            Toast.makeText(getApplicationContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;

                        Toast.makeText(getApplicationContext(), "VolleyNewT error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", user);
                        params.put("password", pass);
                        Log.e(TAG, "Params: " + params.toString());

                        return params;
                    };
                };

                MyApplication.getInstance().addToRequestQueue(strReq);
            }
        });

        lbSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

    }

}
