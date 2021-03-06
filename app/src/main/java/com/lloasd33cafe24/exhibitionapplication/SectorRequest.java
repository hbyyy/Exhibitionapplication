package com.lloasd33cafe24.exhibitionapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SectorRequest extends StringRequest {
    final static private String URL = "http://lloasd33.cafe24.com/sectoradd.php";
    private Map<String, String> parameters;

    public SectorRequest(String adminID, String name, String worksector, int numberofwork, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("adminID", adminID);
        parameters.put("name", name);
        parameters.put("worksector", worksector);
        parameters.put("numberofwork", String.valueOf(numberofwork));

    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
