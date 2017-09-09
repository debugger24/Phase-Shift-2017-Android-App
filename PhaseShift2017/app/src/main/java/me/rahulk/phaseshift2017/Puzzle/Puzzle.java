package me.rahulk.phaseshift2017.Puzzle;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ProgressBar;
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

import me.rahulk.phaseshift2017.AppConfig;
import me.rahulk.phaseshift2017.AppController;
import me.rahulk.phaseshift2017.R;

import static me.rahulk.phaseshift2017.AppConfig.URL_PUZZLE;
import static me.rahulk.phaseshift2017.AppConfig.URL_QUIZ;

public class Puzzle extends AppCompatActivity {

    private TextView txtNoPuzzle, txtWinnersTitle, txtWinners;
    private TextView txtHint1, txtHint2;
    private Button btnReload, btnSubmit;
    private View viewPuzzle, viewNoPuzzle, viewSuccess;
    private EditText[][] editTexts;
    private EditText editUsername, editEmail, editPhone;
    private GridLayout puzzleGrid;
    private ProgressBar progressBar;
    public Toolbar toolbar;

    float scale;
    int pixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle("PhaseShift Puzzle");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        scale = getApplicationContext().getResources().getDisplayMetrics().density;
        pixels = (int) (34 * scale + 0.5f);

        viewPuzzle = findViewById(R.id.viewPuzzle);
        viewNoPuzzle = findViewById(R.id.viewNoPuzzle);
        viewSuccess = findViewById(R.id.viewSuccess);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        txtNoPuzzle = (TextView) findViewById(R.id.txtNoPuzzle);
        txtWinners = (TextView) findViewById(R.id.txtWinners);
        txtWinnersTitle = (TextView) findViewById(R.id.txtWinners);

        puzzleGrid = (GridLayout) findViewById(R.id.puzzleGrid);
        txtHint1 = (TextView) findViewById(R.id.txtHint1);
        txtHint2 = (TextView) findViewById(R.id.txtHint2);

        editUsername = (EditText) findViewById(R.id.editUsername);
        editEmail = (EditText) findViewById(R.id.editEmailID);
        editPhone = (EditText) findViewById(R.id.editPhoneNumber);


        btnReload = (Button) findViewById(R.id.btnReload);
        btnSubmit = (Button) findViewById(R.id.btnSubmitPuzzle);

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rowStr = "";
                for (int i = 0; i < editTexts.length; i++) {
                    for (int j = 0; j < editTexts[i].length; j++) {
                        if (editTexts[i][j].getVisibility() == View.VISIBLE) {
                            if (editTexts[i][j].getText().toString().equals("")) {
                                rowStr += " ";
                            }
                            rowStr += editTexts[i][j].getText().toString();
                        } else {
                            rowStr += " ";
                        }

                    }
                    rowStr += "\n";
                }
                progressBar.setVisibility(View.VISIBLE);
                submitPuzzle(rowStr);
            }
        });

        refresh();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void submitPuzzle(final String stringAnswer) {

        // Make Request and Submit Answers
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_PUZZLE_SUBMIT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (getApplicationContext() != null) {
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
                    } finally {
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (getApplicationContext() != null) {
                    Toast.makeText(getApplicationContext(), "FAILED TO SUBMIT. Try Again", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();

                // Personal Info
                params.put("name", editUsername.getText().toString());
                params.put("email", editEmail.getText().toString());
                params.put("mobile", editPhone.getText().toString());
                params.put("answers", stringAnswer);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, "Submit Answers");
    }

    private void refresh() {
        progressBar.setVisibility(View.VISIBLE);
        if (isNetworkAvailable()) {
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, URL_PUZZLE, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d("QUIZ RESPONSE", "Response: " + response.toString());
                    if (response != null) {
                        checkStatus(response);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("QUIZ ERROR", "Error: " + error.getMessage());
                    progressBar.setVisibility(View.GONE);
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void checkStatus(JSONObject response) {
        try {
            if (response.getString("status").equals("Offline")) {
                // No Puzzle Available
                resetViews();
                viewNoPuzzle.setVisibility(View.VISIBLE);

                txtNoPuzzle.setText(response.getString("next_puzzle"));

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
                viewPuzzle.setVisibility(View.VISIBLE);

                // Set Puzzle
                int rows = response.getInt("rows");
                int cols = response.getInt("cols");

                puzzleGrid.setRowCount(rows);
                puzzleGrid.setColumnCount(cols);

                editTexts = new EditText[rows][cols];

                JSONArray puzzleRow = response.getJSONArray("puzzle");

                for (int i = 0; i < puzzleRow.length(); i++) {
                    JSONArray puzzleColumn = puzzleRow.getJSONArray(i);
                    for (int j = 0; j < puzzleColumn.length(); j++) {

                        String value = puzzleColumn.getString(j);

                        editTexts[i][j] = (EditText) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_puzzle_grid, null);

                        // Control Visibility
                        Log.v("VALUE", value);
                        if (value.equals(" ")) {
                            editTexts[i][j].setVisibility(View.INVISIBLE);
                        } else if (value.equals("x")) {
                            editTexts[i][j].setVisibility(View.VISIBLE);
                        } else {
                            editTexts[i][j].setVisibility(View.VISIBLE);
                            editTexts[i][j].setHint(value);
                        }

                        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(pixels, pixels); // Width , height
                        editTexts[i][j].setLayoutParams(lparams);

                        puzzleGrid.addView(editTexts[i][j]);
                    }
                }

                txtHint1.setText(response.getString("hint_accross"));
                txtHint2.setText(response.getString("hint_down"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void resetViews() {
        viewPuzzle.setVisibility(View.GONE);
        viewNoPuzzle.setVisibility(View.GONE);
        viewSuccess.setVisibility(View.GONE);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
