package cr.ac.itcr.bnbank.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cr.ac.itcr.bnbank.R;
import cr.ac.itcr.bnbank.activity.EditInfoTransaction;
import cr.ac.itcr.bnbank.app.EndPoints;
import cr.ac.itcr.bnbank.app.MyApplication;
import cr.ac.itcr.bnbank.model.Transaction;

/**
 * Created by usuario on 8/6/2016.
 */
public class lvTransactionAdapter extends BaseAdapter {
    private String TAG = lvTransactionAdapter.class.getSimpleName();
    LayoutInflater minflater;
    ArrayList<Transaction> list;

    public lvTransactionAdapter(Context context, ArrayList<Transaction> list) {
        minflater = LayoutInflater.from(context);
        this.list = list;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null){
            convertView = minflater.inflate(R.layout.custom_lv_transactions,null);
        }

        //instace the objects used in the list view
        final TextView txtTypeTrans = (TextView)convertView.findViewById(R.id.txtTypeTrans);
        final TextView txtRodeTrans = (TextView)convertView.findViewById(R.id.txtRodeTrans);
        final Switch swActiveTrans = (Switch)convertView.findViewById(R.id.swActiveTrans);

        //set the objects with the list values

        txtTypeTrans.setText(list.get(position).getType());
        txtRodeTrans.setText("¢"+list.get(position).getRode()); //cambiar xq atenta contra todo
        swActiveTrans.setChecked(Boolean.parseBoolean(list.get(position).getActive()));

        //define the active of the switch for disable or active a transaction
        swActiveTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String endPoint = EndPoints.DISABLE_TRANSACTION;

                Log.e(TAG, "endpoint: " + endPoint);

                StringRequest strReq = new StringRequest(Request.Method.PUT,
                        endPoint, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        Log.e(TAG, "VolleyLVTrans: " + error.getMessage() + ", code: " + networkResponse);
                        Toast.makeText(parent.getContext(), "VolleyLVTrans error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("idTransaction",list.get(position).get_id());
                        if(swActiveTrans.isChecked()) {
                            params.put("active","1");
                        }
                        else{
                            params.put("active","0");
                        }
                        Log.e(TAG, "params: " + params.toString());
                        return params;
                    }
                };

                //Adding request to request queue
                MyApplication.getInstance().addToRequestQueue(strReq);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transaction trans = list.get(position);
                Intent intent = new Intent(parent.getContext(),EditInfoTransaction.class);
                intent.putExtra("rode",trans.getRode());
                intent.putExtra("date",trans.getDate());
                intent.putExtra("type",trans.getType());
                intent.putExtra("_id",trans.get_id());
                intent.putExtra("user","hsolicha");
                intent.putExtra("active",trans.getActive());
                parent.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
