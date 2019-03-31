package com.mmiranda96.procastinationKiller.sources.task.remote;

import com.mmiranda96.procastinationKiller.models.Task;
import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.task.CreateTaskAsyncTask;
import com.mmiranda96.procastinationKiller.util.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class CreateTaskAsyncTaskRemote extends CreateTaskAsyncTask {

    private String server;
    private User user;

    CreateTaskAsyncTaskRemote(String server, User user, Listener listener) {
        super(listener);
        this.server = server;
        this.user = user;
    }

    @Override
    protected Integer doInBackground(Task... tasks) {
        int result = CreateTaskAsyncTask.ERROR;

        try {
            Task task = tasks[0];

            URL url = new URL(this.server + "/tasks");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String authentication = user.buildAuthentication();
            conn.setRequestProperty("Authorization", "Basic " + authentication);
            conn.setRequestProperty(Server.CONTENT_TYPE_KEY, Server.CONTENT_TYPE_VALUE);
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            os.write(task.toJSONObject().toString().getBytes());
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
        }

        return result;
    }
}
