package com.android.shopmanga;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener {

    private MapView mapView;
    private MapboxMap mapboxMap;
    private View Layout;
    private Button detailsButton;
    private TextView detailsTextView;
    private ProgressBar progressBar;

    private List<Manga> mangaList = new ArrayList<Manga>();
    private Manga mangaSelected;
    private boolean markerSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_maps);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        progressBar = findViewById(R.id.progressBar);

        Layout = findViewById(R.id.Layout);
        detailsButton = findViewById(R.id.detailsButton);
        detailsTextView = findViewById(R.id.detailTextView);

        mapView.getMapAsync( this);
    }
    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;

        mapboxMap.getUiSettings().setAttributionEnabled(false);
        mapboxMap.getUiSettings().setLogoEnabled(false);

        mapboxMap.setCameraPosition(new CameraPosition.Builder()
                .target(new LatLng(getIntent().getDoubleExtra("lat",0), getIntent().getDoubleExtra("lng",0)))
                .zoom(12)
                .build());
        mapboxMap.setStyle(Style.OUTDOORS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                try {
                    sendSelectRequest(style);
                    progressBar.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        Style style = mapboxMap.getStyle();
        if (style != null) {
            final SymbolLayer selectedMarkerSymbolLayer =
                    (SymbolLayer) style.getLayer("selected-marker-layer");

            final PointF pixel = mapboxMap.getProjection().toScreenLocation(point);
            List<Feature> features = mapboxMap.queryRenderedFeatures(pixel, "layer-id");
            List<Feature> selectedFeature = mapboxMap.queryRenderedFeatures(
                    pixel, "selected-marker-layer");
            if (selectedFeature.size() > 0 && markerSelected) {
                return false;
            }

            if(selectedFeature.size() > 0 && !markerSelected){
                selectMarker(selectedMarkerSymbolLayer);
                MakeLayoutVisible(selectedFeature.get(0));
                return true;
            }

            if (features.isEmpty()) {
                if (markerSelected) {
                    deselectMarker(selectedMarkerSymbolLayer);
                    MakeLayoutInVisible();
                }
                return false;
            }


            GeoJsonSource source = style.getSourceAs("selected-marker");
            if (source != null) {
                source.setGeoJson(FeatureCollection.fromFeatures(
                        new Feature[]{Feature.fromGeometry(features.get(0).geometry())}));
            }

            if (markerSelected) {
                deselectMarker(selectedMarkerSymbolLayer);
            }
            if (features.size() > 0) {
                selectMarker(selectedMarkerSymbolLayer);
                MakeLayoutVisible(features.get(0));
            }
        }
        return true;
    }

    private void sendSelectRequest(@NonNull Style loadedMapStyle) throws JSONException {
        RequestQueue    queue = Volley.newRequestQueue(this);
        String url ="https://shopmangamobileapi.herokuapp.com/SelectMangas";

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("name", getIntent().getStringExtra("mangaName"));
        jsonBody.put("lat", getIntent().getDoubleExtra("lat",0));
        jsonBody.put("lng",getIntent().getDoubleExtra("lng",0));
        jsonBody.put("volume", getIntent().hasExtra("volume") ? getIntent().getDoubleExtra("volume",0) : null);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            TransformeResponseToMangas(response);
                            Log.d("resultas", String.valueOf(response));
                            addMarkerIconsToMap(loadedMapStyle, response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("webRequetteErreur", error.toString());
                    }
                });
        queue.add(jsonObjectRequest);
    }
    private void addMarkerIconsToMap(@NonNull Style loadedMapStyle, JSONObject response) throws Exception {

        List<Feature> markerCoordinates = new ArrayList<>();
        for(Manga manga: mangaList){
            Feature feature = Feature.fromGeometry(
                    Point.fromLngLat(
                            manga.getLng(),
                            manga.getLat()
                    ));
            markerCoordinates.add(feature);
        }
        List<Feature> AddressemarkerCoordinates = new ArrayList<>();
        AddressemarkerCoordinates.add(
                Feature.fromGeometry(
                        Point.fromLngLat(
                                getIntent().getDoubleExtra("lat",0),
                                getIntent().getDoubleExtra("lng",0)))
        );

        loadedMapStyle.addImage("icon-id", BitmapUtils.getBitmapFromDrawable(
                getResources().getDrawable(R.drawable.icon_pokemon)));

        loadedMapStyle.addImage("addresse-position-id", BitmapUtils.getBitmapFromDrawable(
                getResources().getDrawable(R.drawable.selected_icon)));

        loadedMapStyle.addImage("selected-marker-image", BitmapFactory.decodeResource(
                MapsActivity.this.getResources(),R.drawable.icon_poke_selected));


        //Mangas Icons
        loadedMapStyle.addSource(new GeoJsonSource("source-id",
                FeatureCollection.fromFeatures(markerCoordinates)));

        loadedMapStyle.addLayer(new SymbolLayer("layer-id","source-id")
                .withProperties(
                        iconImage("icon-id"),
                        iconOffset(new Float[]{0f,-8f})
                ));

        //addresse Icons
        loadedMapStyle.addSource(new GeoJsonSource("addresse-id",
                FeatureCollection.fromFeatures(AddressemarkerCoordinates)));

        loadedMapStyle.addLayer(new SymbolLayer("addresse-layer-id","addresse-id")
                .withProperties(
                        iconImage("addresse-position-id"),
                        iconOffset(new Float[]{0f,-10f})
                ));

        //Mangas selected Icons
        loadedMapStyle.addSource(new GeoJsonSource("selected-marker"));

        loadedMapStyle.addLayer(new SymbolLayer("selected-marker-layer", "selected-marker")
                .withProperties(
                        iconImage("icon-id"),
                        iconOffset(new Float[]{0f, -9f})
                ));

        mapboxMap.addOnMapClickListener(MapsActivity.this);

    }

    private void TransformeResponseToMangas(JSONObject response) throws JSONException {
        JSONArray responseArray = response.getJSONObject("map").getJSONObject("resultas").getJSONArray("myArrayList");
        for(int i=0; i<responseArray.length(); i++){
            mangaList.add(new Manga(responseArray.getJSONObject(i)));
        }
    }

    private void selectMarker(final SymbolLayer iconLayer) {
        markerSelected = true;
        iconLayer.setProperties(PropertyFactory.iconImage("selected-marker-image"));

    }
    private void deselectMarker(final SymbolLayer iconLayer) {
        markerSelected = false;
        iconLayer.setProperties(PropertyFactory.iconImage("icon-id"));
    }

    private void AddMangaToLayout(Feature feature){
        for(Manga m: mangaList){
            final PointF pixel = mapboxMap.getProjection().toScreenLocation(new LatLng(m.getLat(),m.getLng()));
            List<Feature> fs = mapboxMap.queryRenderedFeatures(pixel, "layer-id");
            List<Feature> fsSelected = mapboxMap.queryRenderedFeatures(pixel, "selected-marker-layer");
            if(!fs.isEmpty()){
                if(fs.get(0).geometry().equals(feature.geometry())){
                    detailsTextView.setText(
                            "Price = " + m.getPrice() + "\n" +
                            "Volume = " + m.getVolume() +  "\n"
                                    + m.getAddress()
                    );
                    mangaSelected = m;
                }
            }
            if(!fsSelected.isEmpty()){
                if(fsSelected.get(0).geometry().equals(feature.geometry())){
                    detailsTextView.setText(
                            "Price = " + m.getPrice() + "\n"
                                    + m.getAddress()
                    );
                    mangaSelected = m;
                }
            }
            }
    }
    private void MakeLayoutVisible(Feature feature){
        AddMangaToLayout(feature);
        detailsButton.setVisibility(View.VISIBLE);
        detailsTextView.setVisibility(View.VISIBLE);
        Layout.setVisibility(View.VISIBLE);

    }
    private void MakeLayoutInVisible(){
        detailsButton.setVisibility(View.GONE);
        detailsTextView.setVisibility(View.GONE);
        Layout.setVisibility(View.GONE);

        detailsTextView.setText("");
    }

    public void sendToMangaDetailsActivity(View view) {
        Intent intent = new Intent(this, MangadetailsActivity.class);
        intent.putExtra("manga", (Serializable) mangaSelected);
        startActivity(intent);

    }

    public void listMangaView(View view) {

        Intent intent = new Intent(this, List_MangaActivity.class);
        intent.putParcelableArrayListExtra("mangaList", (ArrayList<? extends Parcelable>) mangaList);
        startActivity(intent);
    }
}
