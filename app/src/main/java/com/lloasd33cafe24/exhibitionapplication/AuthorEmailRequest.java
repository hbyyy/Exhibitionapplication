package com.lloasd33cafe24.exhibitionapplication;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AuthorEmailRequest extends StringRequest {

    final static private String URL = "http://lloasd33.cafe24.com/EmailListShow.php";
    private Map<String, String> parameters1;

    public AuthorEmailRequest(String adminID, String authoremail, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters1 = new HashMap<>();

        parameters1.put("adminID", adminID);
        parameters1.put("AuthorEmail", authoremail );
    }

    @Override
    protected Map<String, String> getParams(){
        return parameters1;
    }
}
