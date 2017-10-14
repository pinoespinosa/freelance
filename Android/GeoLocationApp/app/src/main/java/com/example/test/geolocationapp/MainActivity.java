package com.example.test.geolocationapp;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        String texto = "";

        try{
        Context context = this.getApplicationContext();

            texto += dataCell(context);
            texto += dataWIFI(context);
            texto += dataFUll(context);


        }
        catch (Exception e){}

        try{
            Context context = this.getApplicationContext();

            texto += dataCell(context);


        }
        catch (Exception e){
            texto+="\nLocalizacion por torres no disponible\n";

        }        try{
            Context context = this.getApplicationContext();

            texto += dataWIFI(context);


        }
        catch (Exception e){

            texto+="\nLocalizacion por WIFI no disponible\n";

        }        try{
            Context context = this.getApplicationContext();

            texto += dataFUll(context);


        }
        catch (Exception e){

            texto+="\nLocalizacion FULL WIFI + torres no disponible\n";


        }
        tv.setText(texto);

    }

    private String dataCell(Context context){


        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();
        String networkOperator = tm.getNetworkOperator();
        int mcc = Integer.parseInt(networkOperator.substring(0, 3));
        int mnc = Integer.parseInt(networkOperator.substring(3));


        String texto = "\n\n ____________ TELEFONO ____________";
        texto += "\nID del Celular: " + tm.getDeviceId();

        texto+= "\n\n ____________ LOCATION WITH CELL TOWERS ____________";
        String body = "\n{\n" +
                "  \"cellTowers\": [\n" +
                "    {\n" +
                "      \"cellId\": "+location.getCid()+",\n" +
                "      \"locationAreaCode\": "+location.getLac()+",\n" +
                "      \"mobileCountryCode\": "+mcc+",\n" +
                "      \"mobileNetworkCode\": "+mnc+",\n" +
                "      \"age\": 0,\n" +
                "      \"signalStrength\": -60,\n" +
                "      \"timingAdvance\": 15\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        texto += body + "\n\n";
        texto += POST("https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyB4Wz2tuYW4tRVUwrqaV712YkbFlJbXFpY", body);



        texto += "____________ MOBILE NETWORK ____________";
        texto += "\nMobile Country Code: " + mcc;
        texto += "\nMobile Network Code: " + mnc;
        texto += "\nProveedor: " + tm.getNetworkOperatorName();
        texto += "\nLocal Area Code: " + location.getLac();
        texto += "\nCell ID: " + location.getCid();
        texto += "\nCodigo de Pais Movil: " +  getCountry(tm.getSimCountryIso().toUpperCase()) + " / "+ tm.getSimCountryIso().toUpperCase();
        texto += "\nTelefono: " + tm.getLine1Number();

        String senial = "";

        List<CellInfo> info = tm.getAllCellInfo();
        for (CellInfo cell:info ) {
            texto += "\nTipo Red Movil: " + cell.getClass().getSimpleName().toString().replace("CellInfo","");

            if (cell.getClass().equals(CellInfoGsm.class)) {
                senial= ((CellInfoGsm)cell).getCellSignalStrength().getLevel() +"";
                texto += "\nPotencia de la señal:" + senial;
            }

            if (cell.getClass().equals(CellInfoCdma.class)) {
                texto += "\nPotencia de la señal:" + ((CellInfoCdma)cell).getCellSignalStrength().getLevel();
            }

            if (cell.getClass().equals(CellInfoLte.class)) {
                texto += "\nPotencia de la señal:" + ((CellInfoLte)cell).getCellSignalStrength().getLevel();
            }

            if (cell.getClass().equals(CellInfoWcdma.class)) {
                texto += "\nPotencia de la señal:" + ((CellInfoWcdma)cell).getCellSignalStrength().getLevel();
            }

        }

        return texto;
    }


    private String dataWIFI(Context context ){

        WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        String texto= "\n\n ____________ WIFI ____________";
        texto+= "\nMac: " + wifiMan.getConnectionInfo().getMacAddress();
        texto+= "\nSignal: " + wifiMan.getConnectionInfo().getRssi();


         texto+= "\n\n ____________ LOCATION WITH WIFI ____________";

        String bodyWifi = "\n{\n" +
                "  \"macAddress\": \""+wifiMan.getConnectionInfo().getMacAddress()+"\",\n" +
                "  \"signalStrength\": -43,\n" +
                "  \"age\": 0,\n" +
                "  \"channel\": 11,\n" +
                "  \"signalToNoiseRatio\": 0\n" +
                "}";
        texto += bodyWifi + "\n\n";
        texto += POST("https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyB4Wz2tuYW4tRVUwrqaV712YkbFlJbXFpY", bodyWifi);

    return texto;

    }


    private String dataFUll(Context context) throws Exception{

        WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        String bodyWifi = "\n{\n" +
                "  \"macAddress\": \""+wifiMan.getConnectionInfo().getMacAddress()+"\",\n" +
                "  \"signalStrength\": -43,\n" +
                "  \"age\": 0,\n" +
                "  \"channel\": 11,\n" +
                "  \"signalToNoiseRatio\": 0\n" +
                "}";

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();
        String networkOperator = tm.getNetworkOperator();
        int mcc = Integer.parseInt(networkOperator.substring(0, 3));
        int mnc = Integer.parseInt(networkOperator.substring(3));

          String texto= "\n\n ____________ LOCATION FULL ____________";

        String bodyFULL = "\n{\n" +
                "  \"homeMobileCountryCode\": "+mcc+",\n" +
                "  \"homeMobileNetworkCode\": "+mnc+",\n" +
                "  \"radioType\": \"gsm\",\n" +
                "  \"carrier\": \""+tm.getNetworkOperatorName()+"\",\n" +
                "  \"considerIp\": \"true\",\n" +
                "  \"cellTowers\": [\n" +

                "    {\n" +
                "      \"cellId\": "+location.getCid()+",\n" +
                "      \"locationAreaCode\": "+location.getLac()+",\n" +
                "      \"mobileCountryCode\": "+mcc+",\n" +
                "      \"mobileNetworkCode\": "+mnc+",\n" +
                "      \"age\": 0,\n" +
                "      \"signalStrength\": -60,\n" +
                "      \"timingAdvance\": 15\n" +
                "    }\n" +

                "  ],\n" +
                "  \"wifiAccessPoints\": [\n" +

                bodyWifi +

                "  ]\n" +
                "}";


        texto += new JSONObject(bodyFULL).toString(2) + "\n\n";
        texto += POST("https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyB4Wz2tuYW4tRVUwrqaV712YkbFlJbXFpY", bodyFULL);


        return texto;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
    public static String POST(String url, String body){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            // 4. convert JSONObject to JSON to String
            String json = new JSONObject(body).toString();

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
//            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";


            JSONObject RESP = new JSONObject(result);
            result = RESP.toString(2);

        } catch (Exception e) {

            return "no funciono \n" + e.toString() + "\n" + e.fillInStackTrace();
        }

        // 11. return result


        return result;
    }

    public static String POST(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.accumulate("name", person.getName());
//            jsonObject.accumulate("country", person.getCountry());
//            jsonObject.accumulate("twitter", person.getTwitter());

            // 4. convert JSONObject to JSON to String
//            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {

            return "no funciono \n" + e.toString() + "\n" + e.fillInStackTrace();
        }

        // 11. return result
        return result;
    }


    private String getCountry(String paisName){

        String countryZipCode="";

        String[] rl=this.getResources().getStringArray(R.array.CountryCodes);

        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            if(g[1].trim().equals(paisName.trim())){
                countryZipCode=g[0];
                break;
            }
        }

        return countryZipCode;
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
