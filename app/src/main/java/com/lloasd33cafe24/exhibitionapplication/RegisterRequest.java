package com.lloasd33cafe24.exhibitionapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://lloasd33.cafe24.com/Register.php";
    private Map<String, String> parameters;

    public RegisterRequest(String adminID, String adminPassword, String adminEmail, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("adminID", adminID);
        parameters.put("adminPassword", adminPassword);
        parameters.put("adminEmail", adminEmail);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
