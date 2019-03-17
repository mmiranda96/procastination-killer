package com.mmiranda96.procastinationKiller.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.mmiranda96.procastinationKiller.models.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;

public class GetTasks extends AsyncTask<String, Void, ArrayList<Task>> {

    private RequestListener listener;

    public GetTasks(RequestListener listener){
        this.listener = listener;
    }

    @Override
    protected ArrayList<Task> doInBackground(String... strings) {
        if (strings.length != 3) throw new AssertionError("Invalid number of parameters");

        ArrayList<Task> result = new ArrayList<>();
        try{
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            String authentication = strings[1] + ":" + strings[2];
            String encodedAuthentication = Base64.getEncoder().encodeToString(authentication.getBytes());
            connection.setRequestProperty("Authorization", "Basic: " + encodedAuthentication);

            int code = connection.getResponseCode();
            if(code == HttpURLConnection.HTTP_OK){

                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                StringBuilder builder = new StringBuilder();
                String currentLine = "";

                while((currentLine = br.readLine())!=null){
                    Log.wtf("HTTP RESPONSE", currentLine);
                    builder.append(currentLine);
                }

                String jsonContent = builder.toString();
                JSONArray jsonTasks = new JSONArray(jsonContent);
                for(int i = 0; i<jsonTasks.length(); i++){
                    result.add(new Task(jsonTasks.getJSONObject(i)));
                }
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void onPostExecute(ArrayList<Task> result) {
        this.listener.getTasksRequestDone(result);
    }

    public interface RequestListener{
        public void getTasksRequestDone(ArrayList<Task> result);

    }


}
