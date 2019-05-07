package com.mmiranda96.procastinationKiller.sources.task.remote;

import com.mmiranda96.procastinationKiller.models.Task;
import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.task.GetMostUrgentTasksAsyncTask;
import com.mmiranda96.procastinationKiller.sources.task.GetTasksAsyncTask;

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

public class GetMostUrgentTasksAsyncTaskRemote extends GetMostUrgentTasksAsyncTask {


    private String server;
    private User user;

    GetMostUrgentTasksAsyncTaskRemote(String server, User user, GetMostUrgentTasksAsyncTask.Listener listener) {
        super(listener);
        this.server = server;
        this.user = user;
    }

    @Override
    protected ArrayList<Task> doInBackground(Void... voids) {
        ArrayList<Task> result = new ArrayList<>();

        try {
            URL url = new URL(this.server + "/tasks/urgent");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String authentication = this.user.buildAuthentication();
            conn.setRequestProperty("Authorization", "Basic " + authentication);
            conn.setRequestMethod("GET");

            int code = conn.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                StringBuilder builder = new StringBuilder();
                String currentLine = "";

                while ((currentLine = br.readLine()) != null) {
                    builder.append(currentLine);
                }

                String jsonContent = builder.toString();
                JSONArray jsonTasks = new JSONArray(jsonContent);
                for (int i = 0; i < jsonTasks.length(); i++) {
                    result.add(new Task(jsonTasks.getJSONObject(i)));
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}


