package com.mmiranda96.procastinationKiller.sources.user.remote;

import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.user.SignupAsyncTask;
import com.mmiranda96.procastinationKiller.sources.user.UpdateFirebaseTokenAsyncTask;
import com.mmiranda96.procastinationKiller.util.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class UpdateFirebaseTokenAsyncTaskRemote extends UpdateFirebaseTokenAsyncTask {
    private String server;
    private User user;

    UpdateFirebaseTokenAsyncTaskRemote(String server, User user) {
        super();
        this.user = user;
        this.server = server;
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            String token = strings[0];

            URL url = new URL(this.server + "/users/updateFirebaseToken");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String authentication = user.buildAuthentication();
            conn.setRequestProperty("Authorization", "Basic " + authentication);
            conn.setRequestProperty(Server.CONTENT_TYPE_KEY, Server.CONTENT_TYPE_VALUE);
            conn.setRequestMethod("PUT");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firebase_token", token);

            OutputStream os = conn.getOutputStream();
            os.write(jsonObject.toString().getBytes());
            os.close();

            conn.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
