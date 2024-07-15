
document.addEventListener('DOMContentLoaded', function () {
    // Initialize the map
    var map = L.map('map').setView([5.7207, -72.9292], 13);

    // Add OpenStreetMap tile layer to the map
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap rawwr'
    }).addTo(map);

    // Load and display GeoJSON data on the map
    function loadGeoJSON() {
        fetch('path.geojson')
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                L.geoJSON(data, {
                    onEachFeature: function (feature, layer) {
                        if (feature.properties && feature.properties.nombre) {
                            layer.bindPopup(feature.properties.nombre);
                        }
                    },
                    pointToLayer: function (feature, latlng) {
                        return L.marker(latlng, {});
                    }
                }).addTo(map);
                // Adjust map view to include all GeoJSON points
                map.fitBounds(L.geoJSON(data).getBounds());
            })
            .catch(function (error) {
                console.error('Error loading the GeoJSON:', error);
            });
    }

    // Call the function to load GeoJSON data
    loadGeoJSON();

    // Add event listener to the "finish route" button
    document.getElementById('finish-route').addEventListener('click', function () {
        console.log('Finish route button clicked');
        // Add your logic here to handle the route finishing action
    });
});

