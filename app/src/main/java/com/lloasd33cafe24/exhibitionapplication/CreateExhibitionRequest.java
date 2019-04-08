package com.lloasd33cafe24.exhibitionapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateExhibitionRequest extends StringRequest {

    final static private String URL = "http://lloasd33.cafe24.com/CreateExhibition.php";
    private Map<String, String> parameters;

    public CreateExhibitionRequest(String adminID, String name, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("adminID", adminID);
        parameters.put("name", name);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
