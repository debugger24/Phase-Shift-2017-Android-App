package me.rahulk.phaseshift2017;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import static me.rahulk.phaseshift2017.AppConfig.URL_QUIZ;
import static me.rahulk.phaseshift2017.AppConfig.URL_QUIZ_SUBMIT;

public class Quiz extends AppCompatActivity {
    TextView txtQuestion1, txtQuestion2, txtQuestion3, txtQuestion4, txtQuestion5, txtQuestion6, txtQuestion7, txtQuestion8, txtQuestion9, txtQuestion10;
    TextView txtWinners, txtWinnersTitle, txtNoQuiz;
    EditText editAnswer1, editAnswer2, editAnswer3, editAnswer4, editAnswer5, editAnswer6, editAnswer7, editAnswer8, editAnswer9, editAnswer10;
    EditText editName, editEmail, editPhone;
    Button btnSubmit, btnRefresh;
    View viewQuestions, viewNoQuiz, viewSuccess;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle("PhaseShift Quiz");

        txtQuestion1 = (TextView) findViewById(R.id.txtQuestion1);
        txtQuestion2 = (TextView) findViewById(R.id.txtQuestion2);
        txtQuestion3 = (TextView) findViewById(R.id.txtQuestion3);
        txtQuestion4 = (TextView) findViewById(R.id.txtQuestion4);
        txtQuestion5 = (TextView) findViewById(R.id.txtQuestion5);
        txtQuestion6 = (TextView) findViewById(R.id.txtQuestion6);
        txtQuestion7 = (TextView) findViewById(R.id.txtQuestion7);
        txtQuestion8 = (TextView) findViewById(R.id.txtQuestion8);
        txtQuestion9 = (TextView) findViewById(R.id.txtQuestion9);
        txtQuestion10 = (TextView) findViewById(R.id.txtQuestion10);

        editAnswer1 = (EditText) findViewById(R.id.editAnswer1);
        editAnswer2 = (EditText) findViewById(R.id.editAnswer2);
        editAnswer3 = (EditText) findViewById(R.id.editAnswer3);
        editAnswer4 = (EditText) findViewById(R.id.editAnswer4);
        editAnswer5 = (EditText) findViewById(R.id.editAnswer5);
        editAnswer6 = (EditText) findViewById(R.id.editAnswer6);
        editAnswer7 = (EditText) findViewById(R.id.editAnswer7);
        editAnswer8 = (EditText) findViewById(R.id.editAnswer8);
        editAnswer9 = (EditText) findViewById(R.id.editAnswer9);
        editAnswer10 = (EditText) findViewById(R.id.editAnswer10);

        editName = (EditText) findViewById(R.id.editUsername);
        editEmail = (EditText) findViewById(R.id.editEmailID);
        editPhone = (EditText) findViewById(R.id.editPhoneNumber);

        btnSubmit = (Button) findViewById(R.id.btnSubmitQuiz);
        btnRefresh = (Button) findViewById(R.id.btnReload);

        txtNoQuiz = (TextView) findViewById(R.id.txtNoQuiz);
        txtWinnersTitle = (TextView) findViewById(R.id.txtWinnersTitle);
        txtWinners = (TextView) findViewById(R.id.txtWinners);

        viewQuestions = findViewById(R.id.viewQuestions);
        viewNoQuiz = findViewById(R.id.viewNoQuiz);
        viewSuccess = findViewById(R.id.viewSuccess);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitAnswer();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });

        btnRefresh.callOnClick();

    }

    private void submitAnswer() {

        // Make Request and Submit Answers
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_QUIZ_SUBMIT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.v("SUB JSON RESPONSE", response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("success")) {
                        Toast.makeText(getApplicationContext(), "SUCCESSFULLY SUBMITTED. Result will be announced soon", Toast.LENGTH_LONG).show();

                        // Hide Questions
                        viewQuestions.setVisibility(View.GONE);
                        viewNoQuiz.setVisibility(View.GONE);
                        viewSuccess.setVisibility(View.VISIBLE);

                    } else {
                        Toast.makeText(getApplicationContext(), "FAILED TO SUBMIT. Try Again", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Something went wrong. Try Again", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "FAILED TO SUBMIT. Try Again", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();

                // Personal Info
                params.put("name", editName.getText().toString());
                params.put("email", editEmail.getText().toString());
                params.put("mobile", editPhone.getText().toString());
                params.put("a1", editAnswer1.getText().toString());
                params.put("a2", editAnswer2.getText().toString());
                params.put("a3", editAnswer3.getText().toString());
                params.put("a4", editAnswer4.getText().toString());
                params.put("a5", editAnswer5.getText().toString());
                params.put("a6", editAnswer6.getText().toString());
                params.put("a7", editAnswer7.getText().toString());
                params.put("a8", editAnswer8.getText().toString());
                params.put("a9", editAnswer9.getText().toString());
                params.put("a10", editAnswer10.getText().toString());

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, "Submit Answers");
    }


    public void refresh() {
        if (isNetworkAvailable()) {
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, URL_QUIZ, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d("QUIZ RESPONSE", "Response: " + response.toString());
                    if (response != null) {
                        checkStatus(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("QUIZ ERROR", "Error: " + error.getMessage());
                    // swipeContainer.setRefreshing(false);
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void checkStatus(JSONObject response) {
        try {
            if (response.getString("status").equals("Offline")) {
                // No Quiz Available
                viewQuestions.setVisibility(View.GONE);
                viewNoQuiz.setVisibility(View.VISIBLE);
                viewSuccess.setVisibility(View.GONE);

                txtNoQuiz.setText(response.getString("next_quiz"));

                if (response.getString("old_winners").equals("True")) {
                    // Old Winners
                    txtWinners.setVisibility(View.VISIBLE);
                    txtWinnersTitle.setVisibility(View.VISIBLE);
                    txtWinners.setText(response.getString("winners"));
                } else {
                    // No Old Winners
                    txtWinners.setVisibility(View.GONE);
                    txtWinnersTitle.setVisibility(View.GONE);
                }
            } else {
                // Quiz Available
                viewQuestions.setVisibility(View.VISIBLE);
                viewNoQuiz.setVisibility(View.GONE);
                viewSuccess.setVisibility(View.GONE);

                // Set Questions
                JSONArray questionsArray = response.getJSONArray("questions");

                txtQuestion1.setText(questionsArray.get(0).toString());
                txtQuestion2.setText(questionsArray.get(1).toString());
                txtQuestion3.setText(questionsArray.get(2).toString());
                txtQuestion4.setText(questionsArray.get(3).toString());
                txtQuestion5.setText(questionsArray.get(4).toString());
                txtQuestion6.setText(questionsArray.get(5).toString());
                txtQuestion7.setText(questionsArray.get(6).toString());
                txtQuestion8.setText(questionsArray.get(7).toString());
                txtQuestion9.setText(questionsArray.get(8).toString());
                txtQuestion10.setText(questionsArray.get(9).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
