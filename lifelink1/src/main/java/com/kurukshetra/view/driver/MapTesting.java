package com.kurukshetra.view.driver;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MapTesting extends Application {

    // =========================================================
    // THINGSPEAK
    // =========================================================

    private static final String CHANNEL_ID = "3484394";

    private static final String THINGSPEAK_URL = "https://api.thingspeak.com/channels/3484394/feeds.json?api_key=NGNFZNO5KV90OLMJ&results=2";
            // "https://api.thingspeak.com/channels/"
            //         + CHANNEL_ID
            //         + "/feeds.json?results=1";


    // =========================================================
    // LIVE UPDATE INTERVAL
    // =========================================================

    private static final int UPDATE_INTERVAL = 5000;


    // =========================================================
    // JAVAFX
    // =========================================================

    private WebView webView;
    private WebEngine webEngine;

    private volatile boolean running = true;


    // =========================================================
    // LAST COORDINATES
    // =========================================================

    private double lastLatitude = 0;
    private double lastLongitude = 0;


    // =========================================================
    // HTTP CLIENT
    // =========================================================

    private final HttpClient httpClient =
            HttpClient.newBuilder()
                    .build();


    // =========================================================
    // START
    // =========================================================

    @Override
    public void start(Stage stage) {

        webView = new WebView();

        webEngine = webView.getEngine();

        webEngine.setJavaScriptEnabled(true);

        webEngine.loadContent(createMapHTML());


        // =====================================================
        // WAIT FOR MAP TO LOAD
        // =====================================================

        webEngine.getLoadWorker()
                .stateProperty()
                .addListener((obs, oldState, newState) -> {

                    if (newState ==
                            Worker.State.SUCCEEDED) {

                        System.out.println(
                                "Leaflet map loaded."
                        );

                        startLiveTracking();
                    }
                });


        // =====================================================
        // ROOT
        // =====================================================

        BorderPane root =
                new BorderPane();

        root.setCenter(webView);


        // =====================================================
        // SCENE
        // =====================================================

        Scene scene =
                new Scene(
                        root,
                        1200,
                        700
                );


        stage.setTitle(
                "LifeLink - Live Ambulance Tracking"
        );

        stage.setScene(scene);

        stage.show();


        // =====================================================
        // CLOSE
        // =====================================================

        stage.setOnCloseRequest(event -> {

            running = false;

            System.out.println(
                    "Live tracking stopped."
            );
        });
    }


    // =========================================================
    // HTML MAP
    // =========================================================

    private String createMapHTML() {

        return """
<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width,
                   initial-scale=1.0">

    <title>LifeLink Live Map</title>

    <link rel="stylesheet"
          href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />

    <style>

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        html, body {

            width: 100%;
            height: 100%;

            margin: 0;
            padding: 0;

            overflow: hidden;

            background-color: #f8fafc;

            font-family:
                'Segoe UI',
                -apple-system,
                sans-serif;
        }

        #map {

            width: 100%;
            height: 100%;

            display: block;

            background: #f8fafc;
        }

        .leaflet-container {

            width: 100% !important;
            height: 100% !important;

            background: #f8fafc !important;
        }

        .leaflet-tile {

            visibility: visible !important;
        }


        /* ================================================
           PICKUP BADGE
           ================================================ */

        .badge-pickup {

            background: #ef4444 !important;

            color: #ffffff !important;

            border:
                2px solid #ffffff !important;

            border-radius:
                6px !important;

            font-weight:
                800 !important;

            font-size:
                11px !important;

            padding:
                4px 8px !important;

            box-shadow:
                0 2px 8px
                rgba(239, 68, 68, 0.35)
                !important;
        }


        /* ================================================
           DESTINATION BADGE
           ================================================ */

        .badge-destination {

            background: #0284c7 !important;

            color: #ffffff !important;

            border:
                2px solid #ffffff !important;

            border-radius:
                6px !important;

            font-weight:
                800 !important;

            font-size:
                11px !important;

            padding:
                4px 8px !important;

            box-shadow:
                0 2px 8px
                rgba(2, 132, 199, 0.35)
                !important;
        }


        /* ================================================
           AMBULANCE
           ================================================ */

        .marker-amb {

            width: 38px;
            height: 38px;

            background: #ffffff;

            border-radius: 50%;

            display: flex;

            align-items: center;

            justify-content: center;

            font-size: 20px;

            box-shadow:
                0 2px 10px
                rgba(0, 0, 0, 0.2);
        }


        /* ================================================
           DESTINATION
           ================================================ */

        .marker-dest-pin {

            width: 34px;
            height: 34px;

            background: #0284c7;

            border:
                2.5px solid #ffffff;

            border-radius: 50%;

            display: flex;

            align-items: center;

            justify-content: center;

            font-size: 16px;

            color: white;

            box-shadow:
                0 2px 10px
                rgba(2, 132, 199, 0.4);
        }

    </style>

</head>


<body>

<div id="map"></div>


<script src=
"https://unpkg.com/leaflet@1.9.4/dist/leaflet.js">
</script>


<script>

    // =====================================================
    // DISABLE 3D
    // =====================================================

    L.Browser.any3d = false;


    // =====================================================
    // VARIABLES
    // =====================================================

    let map;

    let destMarker;

    let ambulanceMarker;

    let corridorCasing;

    let corridorCore;

    let routeCoords = [];


    // =====================================================
    // AMBULANCE ICON
    // =====================================================

    const ambIcon =
        L.divIcon({

            className: '',

            html:
                '<div class="marker-amb">&#128657;</div>',

            iconSize:
                [38, 38],

            iconAnchor:
                [19, 19]
        });


    // =====================================================
    // DESTINATION ICON
    // =====================================================

    const destIcon =
        L.divIcon({

            className: '',

            html:
                '<div class="marker-dest-pin">&#127973;</div>',

            iconSize:
                [34, 34],

            iconAnchor:
                [17, 17]
        });


    // =====================================================
    // INITIALIZE MAP
    // =====================================================

    function initMap() {

        if (map)
            return;


        map =
            L.map(
                'map',
                {

                    zoomControl: false,

                    attributionControl: false,

                    zoomAnimation: false,

                    fadeAnimation: false
                }
            )
            .setView(
                [18.4800, 73.8600],
                13
            );


        // =================================================
        // OPEN STREET MAP
        // =================================================

        L.tileLayer(
            'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
            {

                maxZoom: 19,

                tileSize: 256
            }
        ).addTo(map);


        // =================================================
        // RESIZE OBSERVER
        // =================================================

        const resizeObserver =
            new ResizeObserver(() => {

                if (map)
                    map.invalidateSize(true);

            });


        resizeObserver.observe(
            document.getElementById('map')
        );
    }


    // =====================================================
    // UPDATE LIVE AMBULANCE
    // =====================================================

    function updateAmbulance(
        latitude,
        longitude
    ) {

        latitude =
            parseFloat(latitude);

        longitude =
            parseFloat(longitude);


        // -------------------------------------------------
        // Validate
        // -------------------------------------------------

        if (
            isNaN(latitude) ||
            isNaN(longitude)
        ) {

            console.log(
                "Invalid GPS coordinates"
            );

            return;
        }


        // Reject 0,0

        if (
            latitude === 0 ||
            longitude === 0
        ) {

            console.log(
                "Ignoring 0,0 location"
            );

            return;
        }


        console.log(
            "LIVE:",
            latitude,
            longitude
        );


        // =================================================
        // FIRST LOCATION
        // =================================================

        if (
            ambulanceMarker === null ||
            ambulanceMarker === undefined
        ) {

            ambulanceMarker =
                L.marker(
                    [
                        latitude,
                        longitude
                    ],
                    {

                        icon:
                            ambIcon,

                        zIndexOffset:
                            3000
                    }
                )
                .addTo(map);


            ambulanceMarker.bindTooltip(

                "&#128657; Ambulance (Live Location)",

                {

                    permanent: true,

                    direction: "top",

                    className:
                        "badge-pickup",

                    offset:
                        [0, -14]
                }
            );


            ambulanceMarker.openTooltip();


            map.setView(
                [
                    latitude,
                    longitude
                ],
                16
            );

        }


        // =================================================
        // MOVE EXISTING AMBULANCE
        // =================================================

        else {

            ambulanceMarker.setLatLng(
                [
                    latitude,
                    longitude
                ]
            );


            map.panTo(
                [
                    latitude,
                    longitude
                ],
                {

                    animate: true,

                    duration: 1
                }
            );
        }
    }


    // =====================================================
    // SET DESTINATION + ROUTE
    // =====================================================

    async function setDestination(
        destLat,
        destLng,
        destName
    ) {

        if (!map)
            initMap();


        destLat =
            parseFloat(destLat);

        destLng =
            parseFloat(destLng);


        if (
            isNaN(destLat) ||
            isNaN(destLng)
        ) {

            console.log(
                "Invalid destination"
            );

            return;
        }


        // -------------------------------------------------
        // Remove previous destination
        // -------------------------------------------------

        if (destMarker)
            map.removeLayer(
                destMarker
            );


        // -------------------------------------------------
        // Remove previous route
        // -------------------------------------------------

        if (corridorCasing)
            map.removeLayer(
                corridorCasing
            );


        if (corridorCore)
            map.removeLayer(
                corridorCore
            );


        // =================================================
        // DESTINATION MARKER
        // =================================================

        destMarker =
            L.marker(
                [
                    destLat,
                    destLng
                ],
                {
                    icon:
                        destIcon
                }
            )
            .addTo(map);


        destMarker.bindTooltip(

            "&#127973; "
                +
            (
                destName ||
                "Emergency Destination"
            ),

            {

                permanent: true,

                direction: "top",

                className:
                    "badge-destination",

                offset:
                    [0, -16]
            }
        );


        destMarker.openTooltip();


        // =================================================
        // ROUTE
        // =================================================

        if (
            ambulanceMarker === null ||
            ambulanceMarker === undefined
        ) {

            return;
        }


        const current =
            ambulanceMarker.getLatLng();


        const url =
            'https://router.project-osrm.org/route/v1/driving/'
            +
            current.lng
            +
            ','
            +
            current.lat
            +
            ';'
            +
            destLng
            +
            ','
            +
            destLat
            +
            '?overview=full&geometries=geojson';


        try {

            const res =
                await fetch(url);


            const data =
                await res.json();


            if (
                data.routes &&
                data.routes[0]
            ) {

                const route =
                    data.routes[0];


                routeCoords =
                    route.geometry.coordinates
                        .map(
                            p => [
                                p[1],
                                p[0]
                            ]
                        );


                // =================================================
                // BLUE ROUTE OUTER
                // =================================================

                corridorCasing =
                    L.polyline(
                        routeCoords,
                        {

                            color:
                                '#005a9e',

                            weight:
                                8,

                            opacity:
                                0.85
                        }
                    )
                    .addTo(map);


                // =================================================
                // BLUE ROUTE INNER
                // =================================================

                corridorCore =
                    L.polyline(
                        routeCoords,
                        {

                            color:
                                '#08a1e5',

                            weight:
                                4.5,

                            opacity:
                                1.0
                        }
                    )
                    .addTo(map);


                // =================================================
                // FIT MAP
                // =================================================

                map.fitBounds(
                    L.latLngBounds(
                        routeCoords
                    ),
                    {

                        paddingTopLeft:
                            [30, 60],

                        paddingBottomRight:
                            [30, 60]
                    }
                );
            }

        } catch (err) {

            console.log(
                "Routing error:",
                err
            );

        }


        setTimeout(
            () => {

                if (map)
                    map.invalidateSize(true);

            },
            150
        );
    }


    // =====================================================
    // INITIAL LOAD
    // =====================================================

    window.onload =
        function() {

            initMap();

        };


</script>

</body>

</html>
""";
    }


    // =========================================================
    // START LIVE TRACKING
    // =========================================================

    private void startLiveTracking() {

        Thread thread =
                new Thread(() -> {

                    while (running) {

                        fetchLocation();

                        try {

                            Thread.sleep(
                                    UPDATE_INTERVAL
                            );

                        } catch (
                                InterruptedException e
                        ) {

                            Thread
                                    .currentThread()
                                    .interrupt();

                            break;
                        }
                    }

                });


        thread.setDaemon(true);

        thread.start();
    }


    // =========================================================
    // FETCH THINGSPEAK LOCATION
    // =========================================================

    private void fetchLocation() {

        try {

            HttpRequest request =
                    HttpRequest.newBuilder()

                            .uri(
                                    URI.create(
                                            THINGSPEAK_URL
                                    )
                            )

                            .header(
                                    "User-Agent",
                                    "LifeLink-JavaFX"
                            )

                            .GET()

                            .build();


            HttpResponse<String> response =
                    httpClient.send(

                            request,

                            HttpResponse.BodyHandlers
                                    .ofString()
                    );


            // =================================================
            // HTTP CHECK
            // =================================================

            if (
                    response.statusCode()
                            != 200
            ) {

                System.out.println(
                        "ThingSpeak HTTP Error: "
                                +
                        response.statusCode()
                );

                return;
            }


            // =================================================
            // JSON
            // =================================================

            JSONObject json =
                    new JSONObject(
                            response.body()
                    );


            JSONArray feeds =
                    json.getJSONArray(
                            "feeds"
                    );


            if (
                    feeds.length() == 0
            ) {

                System.out.println(
                        "No ThingSpeak GPS data."
                );

                return;
            }


            // =================================================
            // LATEST ENTRY
            // =================================================

            JSONObject latestFeed =
                    feeds.getJSONObject(
                            feeds.length() - 1
                    );


            String latitudeText =
                    latestFeed.optString(
                            "field1",
                            ""
                    );


            String longitudeText =
                    latestFeed.optString(
                            "field2",
                            ""
                    );


            // =================================================
            // EMPTY CHECK
            // =================================================

            if (
                    latitudeText.isEmpty()
                            ||
                    longitudeText.isEmpty()
            ) {

                System.out.println(
                        "Latitude or Longitude missing."
                );

                return;
            }


            // =================================================
            // CONVERT TO DOUBLE
            // =================================================

            double latitude =
                    Double.parseDouble(
                            latitudeText
                    );


            double longitude =
                    Double.parseDouble(
                            longitudeText
                    );


            // =================================================
            // 0,0 CHECK
            // =================================================

            if (
                    latitude == 0
                            ||
                    longitude == 0
            ) {

                System.out.println(
                        "Ignoring invalid 0,0 location."
                );

                return;
            }


            // =================================================
            // IGNORE DUPLICATE LOCATION
            // =================================================

            if (
                    latitude == lastLatitude
                            &&
                    longitude == lastLongitude
            ) {

                System.out.println(
                        "Location unchanged."
                );

                return;
            }


            lastLatitude =
                    latitude;

            lastLongitude =
                    longitude;


            // =================================================
            // CONSOLE
            // =================================================

            System.out.println(
                    "================================"
            );

            System.out.println(
                    "LIVE AMBULANCE LOCATION"
            );

            System.out.println(
                    "Latitude  : "
                            + latitude
            );

            System.out.println(
                    "Longitude : "
                            + longitude
            );

            System.out.println(
                    "================================"
            );


            // =================================================
            // UPDATE MAP
            // =================================================

            Platform.runLater(() -> {

                String javascript =
                        "updateAmbulance("
                                +
                        latitude
                                +
                        ","
                                +
                        longitude
                                +
                        ");";


                webEngine.executeScript(
                        javascript
                );
            });


        } catch (Exception e) {

            System.out.println(
                    "ThingSpeak fetch error:"
            );

            e.printStackTrace();
        }
    }


    // =========================================================
    // OPTIONAL:
    // SET HOSPITAL DESTINATION
    // =========================================================

    public void setHospitalDestination(
            double latitude,
            double longitude,
            String hospitalName
    ) {

        Platform.runLater(() -> {

            String safeName =
                    hospitalName
                            .replace(
                                    "'",
                                    "\\'"
                            );


            String javascript =
                    "setDestination("
                            +
                    latitude
                            +
                    ","
                            +
                    longitude
                            +
                    ",'"
                            +
                    safeName
                            +
                    "');";


            webEngine.executeScript(
                    javascript
            );
        });
    }


    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        launch(args);
    }
}