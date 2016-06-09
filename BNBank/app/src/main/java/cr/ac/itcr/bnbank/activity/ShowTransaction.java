package cr.ac.itcr.bnbank.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cr.ac.itcr.bnbank.R;
import cr.ac.itcr.bnbank.adapter.ShowTransactionAdapter;
import cr.ac.itcr.bnbank.adapter.lvTransactionAdapter;
import cr.ac.itcr.bnbank.app.EndPoints;
import cr.ac.itcr.bnbank.app.MyApplication;
import cr.ac.itcr.bnbank.model.Transaction;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowTransaction.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowTransaction#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowTransaction extends Fragment {
    private String TAG = EditTransactions.class.getSimpleName();
    private ArrayList<Transaction> transactionsArraylist; //global list
    private ShowTransactionAdapter mAdapter; //adaptaer for the list view, use a custom adapter
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ShowTransaction() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowTransaction.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowTransaction newInstance(String param1, String param2) {
        ShowTransaction fragment = new ShowTransaction();
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
        View view = inflater.inflate(R.layout.fragment_show_transaction, container, false);

        transactionsArraylist = new ArrayList<>();
        //set the list view
        ListView lvTransactions = (ListView)view.findViewById(R.id.lvShowTransaction);
        //execute the request to the api for obtain transactions values
        fetchTransactions();

        //set with the array charged with the objects, use a custom adapter
        mAdapter = new ShowTransactionAdapter(getContext(),transactionsArraylist);

        lvTransactions.setAdapter(mAdapter);


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


    private void fetchTransactions() {

        String endPoint = EndPoints.GET_TRANSACTIONS;

        //set the url for the request
        StringRequest strReq = new StringRequest(Request.Method.GET,
                endPoint, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "response: " + response);

                try {
                    JSONObject obj = new JSONObject(response);

                    // check for error flag
                    if (obj.getBoolean("success") == true) {  //check if the result of consult is succesful
                        //convert the data in json array
                        JSONArray eventoArraylist = obj.getJSONArray("data");
                        String selfUserId = MyApplication.getInstance().getPrefManager().getUser().getUser();
                        for (int i = 0; i < eventoArraylist.length(); i++) {
                            JSONObject transObj = (JSONObject) eventoArraylist.get(i);
                            //move into the array an set the data to an object type transaction
                            if(selfUserId.equals(transObj.getString("user"))&& transObj.getInt("active")==1)
                            {
                                Transaction cr = new Transaction();
                                cr.set_id(transObj.getString("_id"));
                                cr.setDate(transObj.getString("date"));
                                cr.setRode(transObj.getString("rode"));
                                cr.setType(transObj.getString("type"));
                                if (Integer.parseInt(transObj.getString("active")) == 1) {
                                    cr.setActive("true");
                                } else {
                                    cr.setActive("false");
                                }
                                //add the object to an array type transaction
                                transactionsArraylist.add(cr);
                            }
                        }

                    } else {
                        // error in fetching chat rooms
                        Toast.makeText(getContext(), "" + obj.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "json parsing error: " + e.getMessage());
                    Toast.makeText(getContext(), "Json parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.e(TAG, "VolleyTrans error: " + error.getMessage() + ", code: " + networkResponse);
                Toast.makeText(getContext(), "VolleyTrans error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
    }
}
