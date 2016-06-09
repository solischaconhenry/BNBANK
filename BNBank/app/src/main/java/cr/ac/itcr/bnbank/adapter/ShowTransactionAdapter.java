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
import cr.ac.itcr.bnbank.activity.showInfoTransaction;
import cr.ac.itcr.bnbank.app.EndPoints;
import cr.ac.itcr.bnbank.app.MyApplication;
import cr.ac.itcr.bnbank.model.Transaction;

/**
 * Created by usuario on 8/6/2016.
 */
public class ShowTransactionAdapter  extends BaseAdapter {
    private String TAG = lvTransactionAdapter.class.getSimpleName();
    LayoutInflater minflater;
    ArrayList<Transaction> list;//set the list, that will be use for the list view

    public ShowTransactionAdapter(Context context, ArrayList<Transaction> list) {
        minflater = LayoutInflater.from(context);
        this.list = list;
    }



    //set the size of the list
    @Override
    public int getCount() {
        return list.size();
    }

    //set the position, when you click o need a specific info
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    //contain the information of the custom row for the listview
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null){
            //set the custom row
            convertView = minflater.inflate(R.layout.custom_show_transactions,null);
        }

        //instace the objects used in the list view
        final TextView txtTypeTrans = (TextView)convertView.findViewById(R.id.txtTypeTrans);
        final TextView txtRodeTrans = (TextView)convertView.findViewById(R.id.txtRodeTrans);


        //set the objects with the list values

        txtTypeTrans.setText(list.get(position).getType());
        txtRodeTrans.setText("¢"+list.get(position).getRode());
        //send the parameter for Sgow info transacrion
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selfUserId = MyApplication.getInstance().getPrefManager().getUser().getUser();
                Transaction trans = list.get(position);
                Intent intent = new Intent(parent.getContext(),showInfoTransaction.class);
                intent.putExtra("rode",trans.getRode());
                intent.putExtra("date",trans.getDate());
                intent.putExtra("type",trans.getType());
                intent.putExtra("_id",trans.get_id());
                intent.putExtra("user",selfUserId);
                intent.putExtra("active",trans.getActive());
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
