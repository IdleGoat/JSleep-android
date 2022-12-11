package com.rafieAmandioJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rafieAmandioJSleepJS.jsleep_android.model.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A login screen that offers login via email/password.
 * @author Rafie Amandio
 * @version 1.0
 */
public class PaymentDetailActivity extends AppCompatActivity {

    public static String enddate;
    public static String startdate;
    Button paymentdetail_button;
    ImageView paymentdetail_image,paymentdetail_backimage;
    TextView paymentdetail_title_name,paymentdetail_title_address;
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

        paymentdetail_title_name = findViewById(R.id.paymentdetail_title_name);
        paymentdetail_title_address = findViewById(R.id.paymentdetail_title_address);
        paymentdetail_title_name.setText(DetailRoomActivity.clickedRoom.name);
        paymentdetail_title_address.setText(DetailRoomActivity.clickedRoom.address);
        paymentdetail_backimage = findViewById(R.id.paymentdetail_backbutton);
        paymentdetail_backimage.setOnClickListener( v -> {
            Intent intent = new Intent(PaymentDetailActivity.this, DetailRoomActivity.class);
            startActivity(intent);
        });

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
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date startdate = dateFormat.parse(PaymentDetailActivity.startdate);
                    Date enddate = dateFormat.parse(PaymentDetailActivity.enddate);
                    if(availability(startdate,enddate,DetailRoomActivity.clickedRoom)){
                        Intent intent = new Intent(PaymentDetailActivity.this, CreatePaymentActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(PaymentDetailActivity.this, "Date Not Available", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public boolean availability(Date from, Date to, Room room){


        if(from.after(to) || from.equals(to)){
            return false;
        }

        for (Date i : room.booked) {
            if (from.equals(i)) {
                return false;
            } else if(from.before(i)){
                if(from.before(i) && to.after(i)){
                    return false;
                }
            }
        }
        return true;
    }

}