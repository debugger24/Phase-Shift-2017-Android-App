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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    TextView[] txtQuestions = new TextView[10];
    TextView txtWinners, txtWinnersTitle, txtNoQuiz;
    String[] answersArray = new String[10];
    RadioGroup[] radioGroup = new RadioGroup[10];
    RadioButton[][] radioOptions = new RadioButton[10][4];
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

        txtQuestions[0] = (TextView) findViewById(R.id.txtQuestion0);
        txtQuestions[1] = (TextView) findViewById(R.id.txtQuestion1);
        txtQuestions[2] = (TextView) findViewById(R.id.txtQuestion2);
        txtQuestions[3] = (TextView) findViewById(R.id.txtQuestion3);
        txtQuestions[4] = (TextView) findViewById(R.id.txtQuestion4);
        txtQuestions[5] = (TextView) findViewById(R.id.txtQuestion5);
        txtQuestions[6] = (TextView) findViewById(R.id.txtQuestion6);
        txtQuestions[7] = (TextView) findViewById(R.id.txtQuestion7);
        txtQuestions[8] = (TextView) findViewById(R.id.txtQuestion8);
        txtQuestions[9] = (TextView) findViewById(R.id.txtQuestion9);

        radioGroup[0] = (RadioGroup) findViewById(R.id.radioGroup0);
        radioGroup[1] = (RadioGroup) findViewById(R.id.radioGroup1);
        radioGroup[2] = (RadioGroup) findViewById(R.id.radioGroup2);
        radioGroup[3] = (RadioGroup) findViewById(R.id.radioGroup3);
        radioGroup[4] = (RadioGroup) findViewById(R.id.radioGroup4);
        radioGroup[5] = (RadioGroup) findViewById(R.id.radioGroup5);
        radioGroup[6] = (RadioGroup) findViewById(R.id.radioGroup6);
        radioGroup[7] = (RadioGroup) findViewById(R.id.radioGroup7);
        radioGroup[8] = (RadioGroup) findViewById(R.id.radioGroup8);
        radioGroup[9] = (RadioGroup) findViewById(R.id.radioGroup9);

        radioOptions[0][0] = (RadioButton) findViewById(R.id.answer0_a);
        radioOptions[0][1] = (RadioButton) findViewById(R.id.answer0_b);
        radioOptions[0][2] = (RadioButton) findViewById(R.id.answer0_c);
        radioOptions[0][3] = (RadioButton) findViewById(R.id.answer0_d);

        radioOptions[1][0] = (RadioButton) findViewById(R.id.answer1_a);
        radioOptions[1][1] = (RadioButton) findViewById(R.id.answer1_b);
        radioOptions[1][2] = (RadioButton) findViewById(R.id.answer1_c);
        radioOptions[1][3] = (RadioButton) findViewById(R.id.answer1_d);

        radioOptions[2][0] = (RadioButton) findViewById(R.id.answer2_a);
        radioOptions[2][1] = (RadioButton) findViewById(R.id.answer2_b);
        radioOptions[2][2] = (RadioButton) findViewById(R.id.answer2_c);
        radioOptions[2][3] = (RadioButton) findViewById(R.id.answer2_d);

        radioOptions[3][0] = (RadioButton) findViewById(R.id.answer3_a);
        radioOptions[3][1] = (RadioButton) findViewById(R.id.answer3_b);
        radioOptions[3][2] = (RadioButton) findViewById(R.id.answer3_c);
        radioOptions[3][3] = (RadioButton) findViewById(R.id.answer3_d);

        radioOptions[4][0] = (RadioButton) findViewById(R.id.answer4_a);
        radioOptions[4][1] = (RadioButton) findViewById(R.id.answer4_b);
        radioOptions[4][2] = (RadioButton) findViewById(R.id.answer4_c);
        radioOptions[4][3] = (RadioButton) findViewById(R.id.answer4_d);

        radioOptions[5][0] = (RadioButton) findViewById(R.id.answer5_a);
        radioOptions[5][1] = (RadioButton) findViewById(R.id.answer5_b);
        radioOptions[5][2] = (RadioButton) findViewById(R.id.answer5_c);
        radioOptions[5][3] = (RadioButton) findViewById(R.id.answer5_d);

        radioOptions[6][0] = (RadioButton) findViewById(R.id.answer6_a);
        radioOptions[6][1] = (RadioButton) findViewById(R.id.answer6_b);
        radioOptions[6][2] = (RadioButton) findViewById(R.id.answer6_c);
        radioOptions[6][3] = (RadioButton) findViewById(R.id.answer6_d);

        radioOptions[7][0] = (RadioButton) findViewById(R.id.answer7_a);
        radioOptions[7][1] = (RadioButton) findViewById(R.id.answer7_b);
        radioOptions[7][2] = (RadioButton) findViewById(R.id.answer7_c);
        radioOptions[7][3] = (RadioButton) findViewById(R.id.answer7_d);

        radioOptions[8][0] = (RadioButton) findViewById(R.id.answer8_a);
        radioOptions[8][1] = (RadioButton) findViewById(R.id.answer8_b);
        radioOptions[8][2] = (RadioButton) findViewById(R.id.answer8_c);
        radioOptions[8][3] = (RadioButton) findViewById(R.id.answer8_d);

        radioOptions[9][0] = (RadioButton) findViewById(R.id.answer9_a);
        radioOptions[9][1] = (RadioButton) findViewById(R.id.answer9_b);
        radioOptions[9][2] = (RadioButton) findViewById(R.id.answer9_c);
        radioOptions[9][3] = (RadioButton) findViewById(R.id.answer9_d);

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
                switch (radioGroup[0].getCheckedRadioButtonId()) {
                    case (R.id.answer0_a):
                        answersArray[0] = "A";
                        break;
                    case (R.id.answer0_b):
                        answersArray[0] = "B";
                        break;
                    case (R.id.answer0_c):
                        answersArray[0] = "C";
                        break;
                    case (R.id.answer0_d):
                        answersArray[0] = "D";
                        break;
                    default:
                        answersArray[0] = "";
                }
                switch (radioGroup[1].getCheckedRadioButtonId()) {
                    case (R.id.answer1_a):
                        answersArray[1] = "A";
                        break;
                    case (R.id.answer1_b):
                        answersArray[1] = "B";
                        break;
                    case (R.id.answer1_c):
                        answersArray[1] = "C";
                        break;
                    case (R.id.answer1_d):
                        answersArray[1] = "D";
                        break;
                    default:
                        answersArray[1] = "";
                }
                switch (radioGroup[2].getCheckedRadioButtonId()) {
                    case (R.id.answer2_a):
                        answersArray[2] = "A";
                        break;
                    case (R.id.answer2_b):
                        answersArray[2] = "B";
                        break;
                    case (R.id.answer2_c):
                        answersArray[2] = "C";
                        break;
                    case (R.id.answer2_d):
                        answersArray[2] = "D";
                        break;
                    default:
                        answersArray[2] = "";
                }
                switch (radioGroup[3].getCheckedRadioButtonId()) {
                    case (R.id.answer3_a):
                        answersArray[3] = "A";
                        break;
                    case (R.id.answer3_b):
                        answersArray[3] = "B";
                        break;
                    case (R.id.answer3_c):
                        answersArray[3] = "C";
                        break;
                    case (R.id.answer3_d):
                        answersArray[3] = "D";
                        break;
                    default:
                        answersArray[3] = "";
                }
                switch (radioGroup[4].getCheckedRadioButtonId()) {
                    case (R.id.answer4_a):
                        answersArray[4] = "A";
                        break;
                    case (R.id.answer4_b):
                        answersArray[4] = "B";
                        break;
                    case (R.id.answer4_c):
                        answersArray[4] = "C";
                        break;
                    case (R.id.answer4_d):
                        answersArray[4] = "D";
                        break;
                    default:
                        answersArray[4] = "";
                }
                switch (radioGroup[5].getCheckedRadioButtonId()) {
                    case (R.id.answer5_a):
                        answersArray[5] = "A";
                        break;
                    case (R.id.answer5_b):
                        answersArray[5] = "B";
                        break;
                    case (R.id.answer5_c):
                        answersArray[5] = "C";
                        break;
                    case (R.id.answer5_d):
                        answersArray[5] = "D";
                        break;
                    default:
                        answersArray[5] = "";
                }
                switch (radioGroup[6].getCheckedRadioButtonId()) {
                    case (R.id.answer6_a):
                        answersArray[6] = "A";
                        break;
                    case (R.id.answer6_b):
                        answersArray[6] = "B";
                        break;
                    case (R.id.answer6_c):
                        answersArray[6] = "C";
                        break;
                    case (R.id.answer6_d):
                        answersArray[6] = "D";
                        break;
                    default:
                        answersArray[6] = "";
                }
                switch (radioGroup[7].getCheckedRadioButtonId()) {
                    case (R.id.answer7_a):
                        answersArray[7] = "A";
                        break;
                    case (R.id.answer7_b):
                        answersArray[7] = "B";
                        break;
                    case (R.id.answer7_c):
                        answersArray[7] = "C";
                        break;
                    case (R.id.answer7_d):
                        answersArray[7] = "D";
                        break;
                    default:
                        answersArray[7] = "";
                }
                switch (radioGroup[8].getCheckedRadioButtonId()) {
                    case (R.id.answer8_a):
                        answersArray[8] = "A";
                        break;
                    case (R.id.answer8_b):
                        answersArray[8] = "B";
                        break;
                    case (R.id.answer8_c):
                        answersArray[8] = "C";
                        break;
                    case (R.id.answer8_d):
                        answersArray[8] = "D";
                        break;
                    default:
                        answersArray[8] = "";
                }
                switch (radioGroup[9].getCheckedRadioButtonId()) {
                    case (R.id.answer9_a):
                        answersArray[9] = "A";
                        break;
                    case (R.id.answer9_b):
                        answersArray[9] = "B";
                        break;
                    case (R.id.answer9_c):
                        answersArray[9] = "C";
                        break;
                    case (R.id.answer9_d):
                        answersArray[9] = "D";
                        break;
                    default:
                        answersArray[9] = "";
                }
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
                        resetViews();
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
                if (getApplicationContext() != null) {
                    Toast.makeText(getApplicationContext(), "FAILED TO SUBMIT. Try Again", Toast.LENGTH_LONG).show();
                }
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
                params.put("a1", answersArray[0]);
                params.put("a2", answersArray[1]);
                params.put("a3", answersArray[2]);
                params.put("a4", answersArray[3]);
                params.put("a5", answersArray[4]);
                params.put("a6", answersArray[5]);
                params.put("a7", answersArray[6]);
                params.put("a8", answersArray[7]);
                params.put("a9", answersArray[8]);
                params.put("a10", answersArray[9]);

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
                resetViews();
                viewNoQuiz.setVisibility(View.VISIBLE);

                txtNoQuiz.setText(response.getString("next_quiz"));

                if (response.getString("old_winners").equals("True")) {
                    // Old Winners
                    txtWinnersTitle.setVisibility(View.VISIBLE);
                    txtWinners.setVisibility(View.VISIBLE);
                    txtWinners.setText(response.getString("winners"));
                } else {
                    // No Old Winners
                    txtWinners.setVisibility(View.GONE);
                    txtWinnersTitle.setVisibility(View.GONE);
                }
            } else {
                // Quiz Available
                resetViews();
                viewQuestions.setVisibility(View.VISIBLE);

                // Set Questions
                JSONArray questionsArray = response.getJSONArray("questions_set");

                // First Question
                for (int i = 0; i < 10; i++) {
                    JSONObject questionObject = questionsArray.getJSONObject(i);
                    txtQuestions[i].setText(questionObject.getString("question"));

                    JSONArray optionsArray = questionObject.getJSONArray("options");
                    for (int j = 0; j < 4; j++) {
                        radioOptions[i][j].setText(optionsArray.getString(j));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void resetViews() {
        viewQuestions.setVisibility(View.GONE);
        viewNoQuiz.setVisibility(View.GONE);
        viewSuccess.setVisibility(View.GONE);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
