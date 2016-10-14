package com.example.markus.wawiscan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import google.zxing.integration.android.IntentIntegrator;
import google.zxing.integration.android.IntentResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private TextView formatTxt, contentTxt;
    private Button buttonScannen, buttonEinlagern, buttonAuslagern;

    private String z ="0001WAWI0001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonScannen = (Button) findViewById(R.id.btnScan);
        buttonEinlagern = (Button) findViewById(R.id.btnEinlagern);
        buttonAuslagern = (Button) findViewById(R.id.btnAuslagern);

        buttonScannen.setOnClickListener(this);

        buttonEinlagern.setVisibility(View.INVISIBLE);
        buttonAuslagern.setVisibility(View.INVISIBLE);

        formatTxt = (TextView) findViewById(R.id.TVformat);
        contentTxt = (TextView) findViewById(R.id.TVcontent);

    }

    public void onClick(View v){
        if(v.getId()==R.id.btnScan) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
        if(v.getId()==R.id.btnEinlagern){
            Connect();
        }
        if(v.getId()==R.id.btnAuslagern){

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();


            formatTxt.setText("Format: " + scanFormat);
            contentTxt.setText("Content: " + scanContent);
            z = "" + scanContent;


        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Keine Scandaten!", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (z.contains("WAWI")) {
            // 0001WAWI0001
            String regal = z.substring(0, 4);
            String fach = z.substring(8, 12);

            Toast toast = Toast.makeText(getApplicationContext(),
                    regal + " " + fach, Toast.LENGTH_SHORT);
            toast.show();

            formatTxt.setText("Regal: " + regal);
            contentTxt.setText("Fach: " + fach);

            for(int i = 0 ; i<regal.length(); i++){

            }
            for (int i = 0; i<fach.length(); i++){

            }

            buttonEinlagern.setVisibility(View.VISIBLE);
            buttonAuslagern.setVisibility(View.VISIBLE);
        }
    }

        public void Connect() {
            try {
                Connection con =
                        DriverManager.getConnection("jdbc:oracle:thin:@//oracle-srv.edvsz.hs-osnabrueck.de:1521/oraclestud", "omaescher", "omaescher");

                Toast toast1;
                if (!con.isClosed()) {
                    toast1 = Toast.makeText(getApplicationContext(), "OPEN", Toast.LENGTH_SHORT);
                    toast1.show();
                } else {
                    toast1 = Toast.makeText(getApplicationContext(), "CLOSED", Toast.LENGTH_SHORT);
                    toast1.show();
                }
                buttonEinlagern.setVisibility(View.INVISIBLE);
                buttonAuslagern.setVisibility(View.INVISIBLE);

            }catch(Exception e){
                Toast toast2;
                toast2 = Toast.makeText(getApplicationContext(), "CATCHED", Toast.LENGTH_SHORT);
                toast2.show();
            }
    }
/*
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con =
                        DriverManager.getConnection("jdbc:oracle:thin:@//oracle-srv.edvsz.hs-osnabrueck.de:1521/oraclestud","omaescher","omaescher");

                Toast toast1;
                if(!con.isClosed()){
                     toast1 = Toast.makeText(getApplicationContext(),"OPEN", Toast.LENGTH_SHORT);
                    toast1.show();
                }
                else{
                    toast1 = Toast.makeText(getApplicationContext(),"CLOSED", Toast.LENGTH_SHORT);
                    toast1.show();
                }


                Statement st = con.createStatement();
                String sql = "select anr, bezeichnung, bestandsmenge from arti";
                ResultSet rs=st.executeQuery(sql);



                while(rs.next()){
                    z = rs.getInt(1)+ " "+ rs.getString(2)+ " "+ rs.getString(3);

                }
                contentTxt.setText(z);

                con.close();
            }catch(Exception e){
                System.out.println(e);
            }
        */

}
