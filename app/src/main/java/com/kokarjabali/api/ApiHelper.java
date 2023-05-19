package com.kokarjabali.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ApiHelper {



    public void LOGIN_POST(Context context, String url, String[] dataX, final ApiHelper.VolleyCallback callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String[] newrespon = new String[6];
                            newrespon[0] = jsonObject.getString("id");
                            newrespon[1] = jsonObject.getString("nama");
                            newrespon[2] = jsonObject.getString("saldo_awal");
                            newrespon[3] = jsonObject.getString("saldo_akhir");
                            newrespon[4] = jsonObject.getString("wilker");
                            newrespon[5] = jsonObject.getString("tgl_gabung");

                            callback.onSuccess(newrespon);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

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



    public void PENGJUAN_PINJAMAN_POST(Context context, String url, String[] dataX, final ApiHelper.VolleyCallback callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

//                            Toast.makeText(context, String.valueOf(jsonObject), Toast.LENGTH_SHORT).show();
//
                            String[] newrespon = new String[6];
                            newrespon[0] = jsonObject.getString("messages");

                            callback.onSuccess(newrespon);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);

                        }

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

//                        throw new RuntimeException(String.valueOf(obj));

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
                params.put("user_id", dataX[0]);
                params.put("pokok_pinj", dataX[1]);
                params.put("jangka_waktu", dataX[2]);
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





    public void PINJAMAN_GET(Context context, String url, final ApiHelper.VolleyCallback callback) {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonarray = new JSONArray(response);

                            for(int i=0; i < jsonarray.length();i++) {

                                if (i == (jsonarray.length()-1)) {
                                    JSONObject jsonObject = new JSONObject(jsonarray.getString(i));

                                    String[] newrespon = new String[12];
                                    newrespon[0] = jsonObject.getString("id");
                                    newrespon[1] = jsonObject.getString("formatted_pokok_pinj");
                                    newrespon[2] = jsonObject.getString("formatted_jangka_waktu");
                                    newrespon[3] = jsonObject.getString("bunga_persen");
                                    newrespon[4] = jsonObject.getString("formatted_jmlh_pinj");
                                    newrespon[5] = jsonObject.getString("formatted_angsuran");
                                    newrespon[6] = jsonObject.getString("formatted_tgl_pinj");
                                    newrespon[7] = jsonObject.getString("formatted_tgl_jatuh_tempo");
                                    newrespon[8] = jsonObject.getString("cicilan_ke");
                                    newrespon[9] = jsonObject.getString("formatted_sisa_pinj");
                                    newrespon[10] = jsonObject.getString("tgl_pinj");
                                    newrespon[11] = jsonObject.getString("jangka_waktu");
                                    callback.onSuccess(newrespon );
                                }
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

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
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }







    public interface VolleyCallback {
        void onSuccess(String[] result);

        void onError(String result);
    }



}
