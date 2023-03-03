package com.example.spiner2;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    private final HashMap<String, ArrayList<String>> liste = new HashMap<String, ArrayList<String>>();
    List<String[]> csvData;
    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter;
    ArrayList<String> keys = new ArrayList<>();
    Spinner s1;
    Spinner s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s1 = findViewById(R.id.spinner);
        s2 = findViewById(R.id.spinner2);
        List<String[]> csvData = new ArrayList<>();

        try {
            InputStream inputStream = this.getResources().openRawResource(R.raw.cities);
            CSVReader csvReader = new CSVReader();
            csvData = csvReader.readCSV(inputStream);

            for(String[] s : csvData) {
                //System.out.println(s[0] + "\t" + s[1]);
                if (liste.get(s[1]) == null) {
                    liste.put(s[1], new ArrayList<String>());
                }
                liste.get(s[1]).add(s[0]);
            }

            keys.add("null");
            for(String s : liste.keySet()) {
                //System.out.println(s + "\t" + liste.get(s).toString());
                keys.add(s);
            }

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, keys);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s1.setAdapter(adapter);

            s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String key = parent.getItemAtPosition(position).toString();
                    if (liste.containsKey(key)) {
                        adapter2 = new ArrayAdapter<String>(s2.getContext(), android.R.layout.simple_spinner_item, liste.get(key));
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s2.setAdapter(adapter2);
                    } else {
                        // Handle the case when the selected key is not found in the HashMap
                        // For example, you can clear the second spinner's adapter
                        s2.setAdapter(null);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView <?> parent) {
                }
            });

        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}