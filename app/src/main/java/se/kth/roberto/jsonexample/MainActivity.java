package se.kth.roberto.jsonexample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

// https://developer.android.com/guide/topics/data/data-storage.html
// https://developer.android.com/reference/org/json/JSONTokener.html


public class MainActivity extends AppCompatActivity {

    private JSONArray messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void def(View view ) {
        String content = " [\n" +
                "   {\n" +
                "     \"id\": 912345678901,\n" +
                "     \"text\": \"How do I read JSON on Android?\",\n" +
                "     \"geo\": null,\n" +
                "     \"user\": {\n" +
                "       \"name\": \"android_newb\",\n" +
                "       \"followers_count\": 41\n" +
                "      }\n" +
                "   },\n" +
                "   {\n" +
                "     \"id\": 912345678902,\n" +
                "     \"text\": \"@android_newb just use android.util.JsonReader!\",\n" +
                "     \"geo\": [50.454722, -104.606667],\n" +
                "     \"user\": {\n" +
                "       \"name\": \"jesse\",\n" +
                "       \"followers_count\": 2\n" +
                "     }\n" +
                "   }\n" +
                " ]";
        try {
            FileOutputStream fos = openFileOutput("mydocument.json", Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Failed to open the file", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Failed to write into the file", Toast.LENGTH_SHORT).show();
        }
    }

    public String readFullyAsString(InputStream inputStream, String encoding)
            throws IOException {
        return readFully(inputStream).toString(encoding);
    }

    public byte[] readFullyAsBytes(InputStream inputStream)
            throws IOException {
        return readFully(inputStream).toByteArray();
    }

    private ByteArrayOutputStream readFully(InputStream inputStream)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos;
    }

    public void load(View view ) {
        try {
            FileInputStream stream = openFileInput("mydocument.json");
            String content = readFullyAsString(stream, "UTF-8");
            messages = (JSONArray) new JSONTokener(content).nextValue();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void process(View view ) throws JSONException {
        JSONObject message1 = new JSONObject();
        message1.put("id", 48);
        message1.put("text", "Nice new message");

        JSONObject user1 = new JSONObject();
        user1.put("name", "AAAA");
        message1.put("user", user1);
        messages.put(message1);
    }
    public void save(View view ) throws JSONException {
        String result = messages.toString(2);
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("mydocument.json", Context.MODE_PRIVATE);
            fos.write(result.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void read(View view ) throws JSONException {
        /*
        TextView text = (TextView) findViewById(R.id.text);

        JSONArray messages = (JSONArray) new JSONTokener(content).nextValue();

        String textToPrint = "";
        for (int i =0; i< messages.length(); i++) {
            JSONObject message = (JSONObject) messages.get(i);
            textToPrint += (message.get("text")) + "\n";
        }

        JSONObject message = (JSONObject)messages.get(1);
        JSONObject user = (JSONObject) message.get("user");
        String name = (String) user.get("name");

        user.put("name", "Roberto");
        user.put("age", 38);

        JSONObject message1 = new JSONObject();
        message1.put("id", 48);
        message1.put("text", "Nice new message");

        JSONObject user1 = new JSONObject();
        user1.put("name", "AAAA");
        message1.put("user", user1);
        messages.put(message1);

        String result = messages.toString(2);


        text.setText(result);*/
    }
}
