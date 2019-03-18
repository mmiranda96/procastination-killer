package com.mmiranda96.procastinationKiller.asyncTasks;

import android.os.AsyncTask;

import com.mmiranda96.procastinationKiller.models.Task;
import com.mmiranda96.procastinationKiller.models.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetTasksAsyncTask extends AsyncTask<User, Void, ArrayList<Task>> {

    public interface RequestListener{
        void getTasksRequestDone(ArrayList<Task> result);
    }

    private String host;
    private RequestListener listener;

    public GetTasksAsyncTask(String host, RequestListener listener){
        this.host = host;
        this.listener = listener;
    }

    @Override
    protected ArrayList<Task> doInBackground(User... users) {
        ArrayList<Task> result = new ArrayList<>();

        try{
            User user = users[0];

            URL url = new URL(this.host + "/tasks");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String authentication = user.buildAuthentication();
            conn.setRequestProperty("Authorization", "Basic: " + authentication);
            conn.setRequestMethod("GET");

            int code = conn.getResponseCode();
            if(code == HttpURLConnection.HTTP_OK){
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                StringBuilder builder = new StringBuilder();
                String currentLine = "";

                while((currentLine = br.readLine())!=null){
                    builder.append(currentLine);
                }

                String jsonContent = builder.toString();
                JSONArray jsonTasks = new JSONArray(jsonContent);
                for(int i = 0; i<jsonTasks.length(); i++){
                    result.add(new Task(jsonTasks.getJSONObject(i)));
                }
            }
        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<Task> result) {
        this.listener.getTasksRequestDone(result);
    }
}
