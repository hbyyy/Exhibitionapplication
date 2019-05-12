package com.lloasd33cafe24.exhibitionapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateWorkRequest extends StringRequest {

    final static private String URL = "http://lloasd33.cafe24.com/CreateExhibition.php";
    private Map<String, String> parameters;

    public CreateWorkRequest(String adminID, String workname, String authorname, String workdescription, String name, String worksector ,Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("adminID", adminID);
        parameters.put("workname", workname);
        parameters.put("authorname", authorname);
        parameters.put("workdescription", workdescription);
        parameters.put("name", name);
        parameters.put("worksector", worksector);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}

