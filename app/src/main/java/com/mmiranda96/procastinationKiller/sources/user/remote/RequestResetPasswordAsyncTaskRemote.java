package com.mmiranda96.procastinationKiller.sources.user.remote;

import com.mmiranda96.procastinationKiller.sources.user.RequestResetPasswordAsyncTask;
import com.mmiranda96.procastinationKiller.util.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class RequestResetPasswordAsyncTaskRemote extends RequestResetPasswordAsyncTask {
    private String server;

    RequestResetPasswordAsyncTaskRemote(String server, Listener listener) {
        super(listener);
        this.server = server;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        int result = ERROR;

        try {

            String emailUser = strings[0];

            URL url = new URL(this.server + "/users/sendResetPasswordEmail");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
                case HttpURLConnection.HTTP_FORBIDDEN:
                    result = FAILURE;
                    break;
                default:
                    result = ERROR;
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