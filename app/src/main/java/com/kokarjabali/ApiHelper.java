package com.kokarjabali;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ApiHelper {



    public void LOGIN_POST(Context context, String url, String[] dataX, final ApiHelper.VolleyCallback callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String[] newrespon = new String[2];
                        newrespon[0] = response;
//                            newrespon[1] = jsonObject.getString("massage");

                        callback.onSuccess(newrespon);


                        try {
                            JSONObject jsonObject = new JSONObject(response);



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }



//                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
//
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            String[] newrespon = new String[2];
//                            newrespon[0] = jsonObject.getString("error");
//                            newrespon[1] = jsonObject.getString("massage");
//
////                            callback.onSuccess(newrespon);
//
//                        } catch (JSONException e) {
////                            callback.onError("error : " + e.toString());
//                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // As of f605da3 the following should work
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);

                        callback.onError(String.valueOf(obj.getString("message")));

                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }



//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    callback.onError("No Connection \n Connection time out error please try again.");
//                } else if (error instanceof AuthFailureError) {
//                    callback.onError("Connection error \n Authentication Failure connection error please try again.");
//                } else if (error instanceof ServerError) {
//                    callback.onError("Connection error \n Server Connection error please try again.");
//                } else if (error instanceof NetworkError) {
//                    callback.onError("Connection error \n Network Connection error please try again.");
//                } else if (error instanceof ParseError) {
//                    callback.onError("Connection error \n Network Connection error please try again.");
//                }

            }
        })
        {

            protected Map<String, String> getParams()   {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", dataX[0]);
                params.put("password", dataX[1]);
                return params;
            }

//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("username", dataX[0]);
//                params.put("password", dataX[1]);
//                return params;
//            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }





    public interface VolleyCallback {
        void onSuccess(String[] result);

        void onError(String result);
    }



}
