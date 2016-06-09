package cr.ac.itcr.bnbank.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cr.ac.itcr.bnbank.R;
import cr.ac.itcr.bnbank.app.EndPoints;
import cr.ac.itcr.bnbank.app.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddTransaction.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddTransaction#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTransaction extends Fragment {

    private String TAG = AddTransaction.class.getSimpleName();
    private EditText txtAddRode;
    private Spinner spAddTransaction;
    private Button btnAddTransaction;
    private String typeTransaction;
    private String actualDate;
    private String rode;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddTransaction() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddTransaction.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTransaction newInstance(String param1, String param2) {
        AddTransaction fragment = new AddTransaction();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_add_transaction, container, false);
        txtAddRode = (EditText)view.findViewById(R.id.txtAddRode);
        spAddTransaction = (Spinner)view.findViewById(R.id.spAddTransaction);
        btnAddTransaction = (Button)view.findViewById(R.id.btnAddTransaction);
        // set the spinner with the
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.transType,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAddTransaction.setAdapter(adapter);

        //get the actual date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd ");
        actualDate = mdformat.format(calendar.getTime());

        spAddTransaction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeTransaction = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //create the nerw transactions
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rode = txtAddRode.getText().toString();
                //check the require spaces
                if(typeTransaction.isEmpty() || rode.isEmpty()){
                    Toast error = Toast.makeText(getContext(), R.string.complete,Toast.LENGTH_LONG);
                    error.show();

                }
                //set the ulr of the api
                String endPoint = EndPoints.NEW_TRANSACTION;

                Log.e(TAG, "endpoint: " + endPoint);

                //star the request
                StringRequest strReq = new StringRequest(Request.Method.POST,
                        endPoint, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {

                    ///if error do this
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        Log.e(TAG, "VolleyNewT error: " + error.getMessage() + ", code: " + networkResponse);
                        Toast.makeText(getContext(), "VolleyNewT error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    //obtain the user from share preferences
                    String selfUserId = MyApplication.getInstance().getPrefManager().getUser().getUser();

                    //put the parameters to send to the api
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user", selfUserId);
                        params.put("date", actualDate);
                        params.put("type",typeTransaction);
                        params.put("rode",rode);
                        params.put("active","1");

                        Log.e(TAG, "Params: " + params.toString());

                        return params;
                    };
                };


                //Adding request to request queue
                MyApplication.getInstance().addToRequestQueue(strReq);
                Toast.makeText(getContext(),"Transaction Created",Toast.LENGTH_LONG).show();
                txtAddRode.setText("");
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
