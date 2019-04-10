package com.mmiranda96.procastinationKiller.sources.task.remote;

import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.task.AddUserToTaskAsyncTask;
import com.mmiranda96.procastinationKiller.util.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AddUserToTaskAsyncTaskRemote extends AddUserToTaskAsyncTask {

    private String server;
    private User user;

    protected AddUserToTaskAsyncTaskRemote(String server, User user, Listener listener) {
        super(listener);
        this.server = server;
        this.user = user;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        int result = AddUserToTaskAsyncTask.ERROR;
        try{
            Integer taskId = Integer.valueOf(strings[0]);
            String emailUser = strings[1];
            //String jsonEmail = "{'email':'" + emailUser + "'}";
            URL url = new URL(this.server + "/tasks/"+ taskId.toString() +"/addUser");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String authentication = user.buildAuthentication();
            conn.setRequestProperty("Authorization", "Basic " + authentication);
            conn.setRequestProperty(Server.CONTENT_TYPE_KEY, Server.CONTENT_TYPE_VALUE);
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            JSONObject jsonEmail = new JSONObject();
            jsonEmail.put("email", emailUser);
            os.write(jsonEmail.toString().getBytes());
            os.close();

            int code = conn.getResponseCode();
            switch (code) {
                case HttpURLConnection.HTTP_OK:
                    result = SUCCESS;
                    break;
                default:
                    result = ERROR;
                    break;
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
