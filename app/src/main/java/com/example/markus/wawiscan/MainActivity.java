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
    private Button btnScan, btnEinlagern, btnAuslagern;

    private String z ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan = (Button) findViewById(R.id.btnScan);
        btnEinlagern = (Button) findViewById(R.id.btnEinlagern);
        btnAuslagern = (Button) findViewById(R.id.btnAuslagern);

        btnEinlagern.setVisibility(View.INVISIBLE);
        btnAuslagern.setVisibility(View.INVISIBLE);

        formatTxt = (TextView) findViewById(R.id.TVformat);
        contentTxt = (TextView) findViewById(R.id.TVcontent);

        /*
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con =
                    DriverManager.getConnection("jdbc:oracle:thin:@//oracle-srv.edvsz.hs-osnabrueck.de:1521/oraclestud","omaescher","omaescher");

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

    public void onClick(View v){
        if(v.getId()==R.id.btnScan){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
        if(v.getId()==R.id.btnEinlagern){

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
                z= ""+scanContent;


        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Keine Scandaten!", Toast.LENGTH_SHORT);
            toast.show();
        }

        if(z.contains("/")){
            // 0001/0001
            String regal = z.substring(0,3);
            String fach = z.substring(5,8);

            Toast toast = Toast.makeText(getApplicationContext(),
                    regal + " "+fach, Toast.LENGTH_SHORT);
            toast.show();
        }

        //Wenn Scan erfolgreich dann mit gescanntenCode Datenbankabfrage

    }
}
