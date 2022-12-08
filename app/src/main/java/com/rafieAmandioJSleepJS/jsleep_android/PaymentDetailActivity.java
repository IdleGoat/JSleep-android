package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

public class PaymentDetailActivity extends AppCompatActivity {

    public static String enddate;
    public static String startdate;
    Button paymentdetail_button;
    ImageView paymentdetail_image;
    EditText paymentdetail_edittext_start, paymentdetail_edittext_end;
    DatePickerDialog datePickerDialogEnd,datePickerDialogStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialogStart = new DatePickerDialog(PaymentDetailActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        paymentdetail_edittext_start.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        startdate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    }
                }, mYear, mMonth, mDay);

        datePickerDialogEnd = new DatePickerDialog(PaymentDetailActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        paymentdetail_edittext_end.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        enddate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    }
                }, mYear, mMonth, mDay);

        paymentdetail_edittext_start = findViewById(R.id.paymentdetail_edittext_start);
        paymentdetail_edittext_end = findViewById(R.id.paymentdetail_edittext_end);

        paymentdetail_edittext_start.setOnClickListener(v -> {
            datePickerDialogStart.show();
        });

        paymentdetail_edittext_end.setOnClickListener(v -> {
            datePickerDialogEnd.show();
        });


        paymentdetail_button = findViewById(R.id.paymentdetail_button);
        paymentdetail_image = findViewById(R.id.paymentdetail_title_icon);
        paymentdetail_button.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                startdate = paymentdetail_edittext_start.getText().toString();
                enddate = paymentdetail_edittext_end.getText().toString();
                Intent move = new Intent(PaymentDetailActivity.this,CreatePaymentActivity.class);
                startActivity(move);
            }
        });


    }
}