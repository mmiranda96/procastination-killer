package com.mmiranda96.procastinationKiller.sources.user.remote;

import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.user.SignupAsyncTask;
import com.mmiranda96.procastinationKiller.util.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class SignupAsyncTaskRemote extends SignupAsyncTask {
    private String server;

    SignupAsyncTaskRemote(String server, Listener listener) {
        super(listener);
        this.server = server;
    }

    @Override
    protected Integer doInBackground(User... users) {
        int result = ERROR;

        try {
            User user = users[0];

            URL url = new URL(this.server + "/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String authentication = user.buildAuthentication();
            conn.setRequestProperty("Authorization", "Basic " + authentication);
            conn.setRequestProperty(Server.CONTENT_TYPE_KEY, Server.CONTENT_TYPE_VALUE);
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            os.write(user.toJSONObject().toString().getBytes());
            os.close();

            int code = conn.getResponseCode();
            switch (code) {
                case HttpURLConnection.HTTP_OK:
                    result = SUCCESS;
                    break;
                case HttpURLConnection.HTTP_CONFLICT:
                    result = FAILURE;
                    break;
                default:
                    result = ERROR;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
