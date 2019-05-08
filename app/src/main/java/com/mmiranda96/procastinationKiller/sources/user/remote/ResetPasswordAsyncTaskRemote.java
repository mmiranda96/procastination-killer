package com.mmiranda96.procastinationKiller.sources.user.remote;

import android.util.Log;

import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.user.ResetPasswordAsyncTask;
import com.mmiranda96.procastinationKiller.util.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class ResetPasswordAsyncTaskRemote extends ResetPasswordAsyncTask {
    private String server;

    ResetPasswordAsyncTaskRemote(String server, Listener listener) {
        super(listener);
        this.server = server;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        int result = ERROR;

        Log.d("value", "si estoy corriendo");

        try {
            String emailUser = strings[0];
            String passwordUser = strings[1];
            String token = strings[2];

            URL url = new URL(this.server + "/users/resetPassword");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty(Server.CONTENT_TYPE_KEY, Server.CONTENT_TYPE_VALUE);
            conn.setRequestMethod("POST");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);
            jsonObject.put("email", emailUser);
            jsonObject.put("password", passwordUser);

            OutputStream os = conn.getOutputStream();
            os.write(jsonObject.toString().getBytes());
            os.close();

            int code = conn.getResponseCode();
            String res= Integer.toString(code);
            Log.d("value respuesta", res);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
