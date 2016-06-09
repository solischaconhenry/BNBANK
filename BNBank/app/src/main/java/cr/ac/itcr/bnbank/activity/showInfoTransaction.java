package cr.ac.itcr.bnbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class showInfoTransaction extends AppCompatActivity {
    private static final String TAG = EditText.class.getSimpleName();
    private TextView txtType, txtDate,txtRode;
    private String _id, active, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set the title
        getSupportActionBar().setTitle("Transaction");
        //set the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set the txtviews
        txtType = (TextView) findViewById(R.id.txtTypeS);
        txtDate = (TextView) findViewById(R.id.txtDateS);
        txtRode = (TextView) findViewById(R.id.txtRodeS);

        //set the textview with the parameter sent by sohw transaction
        Intent intent = getIntent();
        txtType.setText(intent.getStringExtra("type"));
        txtDate.setText(intent.getStringExtra("date"));
        txtRode.setText(intent.getStringExtra("rode"));
        _id = intent.getStringExtra("_id");
        user = intent.getStringExtra("user");
        active = intent.getStringExtra("active");




    }
}

