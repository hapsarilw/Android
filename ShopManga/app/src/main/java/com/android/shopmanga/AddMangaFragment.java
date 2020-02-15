package com.android.shopmanga;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import es.dmoral.toasty.Toasty;


public class AddMangaFragment extends Fragment {

    private FusedLocationProviderClient client;
    private AutoCompleteTextView mangaName;

    private List<String> listMangaName;
    private Map<String,String> listMangaNameAndUrl;

    private String address;
    private Manga newManga;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_manga, container, false);
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.AddManga_autocomplete_fragment);
        EditText text = autocompleteFragment.getView().findViewById(R.id.places_autocomplete_search_input);
        autocompleteFragment.getView().findViewById(R.id.places_autocomplete_search_button).setVisibility(View.GONE);
        Places.initialize(getContext(), "AIzaSyAejI8898winqzlekeYkhyJ2m1ZEPb3im0");
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));
        mangaName = view.findViewById(R.id.AddMangaName);


        AutoCompletionTask myTask = new AutoCompletionTask();
        myTask.execute();

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                text.setText(place.getAddress());
                address = place.getAddress();
            }

            @Override
            public void onError(Status status) {
                Toasty.error(getContext(),"Error : We can't use this address",Toasty.LENGTH_LONG).show();
            }
        });

        Button positionButton = view.findViewById(R.id.AddManga_MyPositionButton);
        positionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toasty.error(getContext(),"Error : Permisssion Denied",Toasty.LENGTH_LONG).show();
                    return;
                }
                client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        try {
                            if(location != null){
                            String s = getAddressFromLatLng(location.getLatitude(),location.getLongitude());
                            text.setText("My position");
                            address = s;
                            }
                            else
                                Toasty.error(getContext(),"Error : Activate your location",Toasty.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                client.getLastLocation().addOnFailureListener(getActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toasty.error(getContext(),"Error : We can't find your position",Toasty.LENGTH_LONG).show();
                    }
                });
            }

            private void requestPermission() {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        });

        Button button =  view.findViewById(R.id.AddFragementButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressBar progressbar = view.findViewById(R.id.AddMangaFragmentprogressBar);

                EditText volume = view.findViewById(R.id.AddMangaVolume);
                EditText price = view.findViewById(R.id.AddMangaPrice);
                EditText sellerName = view.findViewById(R.id.AddMangaSellerName);
                EditText telephone = view.findViewById(R.id.AddMangaTelephone);

                String mangaNameString = mangaName.getText().toString();
                String volumeString = volume.getText().toString();
                String priceString = price.getText().toString();
                String sellerNameString = sellerName.getText().toString();
                String telephoneString = telephone.getText().toString();
                String imageUrl = listMangaNameAndUrl.get(mangaNameString);

                if(mangaNameString.isEmpty() || volumeString.isEmpty() || priceString.isEmpty() || sellerNameString.isEmpty() || telephoneString.isEmpty() || address.isEmpty())
                    Toasty.error(getContext(),"Error : Fill all the blanks",Toasty.LENGTH_LONG).show();
                else if(!listMangaName.contains(mangaNameString))
                {
                    Toasty.error(getContext(),"Error : Manga name not exists",Toasty.LENGTH_LONG).show();
                }
                else {
                    progressbar.setVisibility(View.VISIBLE);
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    String url = "https://shopmangamobileapi.herokuapp.com/AddManga";

                    newManga = new Manga(UUID.randomUUID().toString(), address, 0, 0, Integer.parseInt(priceString), sellerNameString, telephoneString, mangaNameString, imageUrl, Double.parseDouble(volumeString));
                    JSONObject jsonBody = new JSONObject();
                    try {
                        jsonBody.put("name", mangaNameString);
                        jsonBody.put("volume", volumeString);
                        jsonBody.put("price", priceString);
                        jsonBody.put("sellerName", sellerNameString);
                        jsonBody.put("telephone", telephoneString);
                        jsonBody.put("adresse", address);
                        jsonBody.put("imageUrl", imageUrl);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            progressbar.setVisibility(View.GONE);
                            Toasty.success(getContext(), "Operation succed", Toasty.LENGTH_LONG).show();
                            MainActivity.appDatabase.mangaDao().insertAll(newManga);
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressbar.setVisibility(View.GONE);
                            Toasty.error(getContext(), "Error : We can't Add This manga", Toasty.LENGTH_LONG).show();
                        }
                    });
                    queue.add(jsonObjectRequest);
                }
            }
        });


        return view;
    }
    private String getAddressFromLatLng(double lat,double lng) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());
        addresses = geocoder.getFromLocation(lat, lng, 1);
        return addresses.get(0).getAddressLine(0);
    }
    public class AutoCompletionTask extends AsyncTask<String, Void , String> {

        @Override
        protected String doInBackground(String... text) {
            StringBuilder jsonResults = new StringBuilder();
            String apiUrl = "https://www.mangaeden.com/api/list/0/";

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStreamReader in = new InputStreamReader(
                        conn.getInputStream());
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
                return jsonResults.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            try {
                JSONObject json = new JSONObject(response);
                listMangaNameFromJsonArray(json.getJSONArray("manga"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item, listMangaName);
            mangaName.setAdapter(adapter);

        }
        public void listMangaNameFromJsonArray(JSONArray array) throws JSONException {
            List<String> list = new ArrayList<String>();
            Map<String,String> map = new HashMap<String, String>();
            for(int i = 0;i<array.length() ; i++){
                JSONObject json = (JSONObject) array.get(i);
                list.add(json.getString("t"));
                map.put(json.getString("t"),json.getString("im"));
            }
            listMangaName = list;
            listMangaNameAndUrl = map;
        }
    }

}

