package com.example.bhasingursifath.feedbackform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReviewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String Name="entry.1256178576";
    public static final String Gender="entry.426491975";
    public static final String Age="entry.2000641666";
    public static final String NameOfWorkshopAttended="entry.1350691592";
    public static final String AreaOfWorkshop="entry.1015570159";
    public static final String Feedback="entry.721038449";

    String []Workshops={"Widening scope of internet","C++","Tally"};
    String []Areas={"head office","Saharanpur","Uttam Nager","Rourkilla","East delhi","Trans Yamuna"};
    String WorkshopSelected,AreaSelected,gender;
    Button submit_button;
    EditText _Name,_Age,_Feedback;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        radioGroup=(RadioGroup) findViewById(R.id.radiogroup);
        submit_button=(Button) findViewById(R.id.angry_btn);
        _Name=(EditText) findViewById(R.id.ET_Name);
        _Age=(EditText) findViewById(R.id.ET_Age);
        _Feedback=(EditText) findViewById(R.id.feedback);


        Spinner spinner=(Spinner) findViewById(R.id.SP_NameOfWorkshop);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Workshops);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner1=(Spinner) findViewById(R.id.SP_AreaOFWorkshop);
        spinner1.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Areas);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        retrofit_call();
    }


    private void retrofit_call() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://docs.google.com/forms/d/")
                .build();

        final PostRequestInterface postRequestInterface = retrofit.create(PostRequestInterface.class);

        findViewById(R.id.angry_btn).setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                if (TextUtils.isEmpty(_Name.getText().toString()) || TextUtils.isEmpty(_Age.getText().toString()) || TextUtils.isEmpty(_Feedback.getText().toString())) {
                                                                    Toast.makeText(ReviewActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                                                                }

                                                                int radioID = radioGroup.getCheckedRadioButtonId();
                                                                if(radioID==-1)
                                                                {
                                                                    Toast.makeText(ReviewActivity.this,"Please selet your Gender",Toast.LENGTH_SHORT).show();
                                                                }
                                                                else {
                                                                    radioButton = (RadioButton) findViewById(radioID);
                                                                    gender = radioButton.getText().toString();
                                                                }


                                                                String NameInput = _Name.getText().toString();
                                                                String GenderInput = gender;
                                                                String AgeInput = _Age.getText().toString();
                                                                String NameofWorkshopInput = WorkshopSelected;
                                                                String AreaOFWorkshopInput = AreaSelected;
                                                                String FeedbackInput = _Feedback.getText().toString();

                                                                Log.d("Output",NameInput+" "+GenderInput+" "+AgeInput+" "+NameofWorkshopInput+" "+AreaOFWorkshopInput+" "+FeedbackInput);

                                                                Call<Void> CompleteFormCall = postRequestInterface.CompleteForm(NameInput, GenderInput, AgeInput, NameofWorkshopInput, AreaOFWorkshopInput, FeedbackInput);
                                                                CompleteFormCall.enqueue(new Callback<Void>() {
                                                                    @Override
                                                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                                                        Log.d("YYY", "Submitted. code" + String.valueOf(response.code()));
                                                                        Log.d("XXX", "Submitted. message " + String.valueOf(response.message()));

                                                                        Toast.makeText(ReviewActivity.this,"Thank you for your feedback",Toast.LENGTH_SHORT).show();
                                                                        Intent i=new Intent();
                                                                        i.setClass(ReviewActivity.this,MainActivity.class);
                                                                        startActivity(i);
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<Void> call, Throwable t) {
                                                                        Log.e("XXX", "Failed " +t);
                                                                    }
                                                                });

                                                            }

                                                        }
        );
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spin = (Spinner)parent;
        Spinner spin1 = (Spinner)parent;
        if(spin.getId() == R.id.SP_NameOfWorkshop)
        {
            WorkshopSelected=Workshops[position].toString();
        }
        if(spin1.getId() == R.id.SP_AreaOFWorkshop)
        {
            AreaSelected=Areas[position].toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Toast.makeText(this,"All fields are mandatory!",Toast.LENGTH_SHORT).show();
    }

}
