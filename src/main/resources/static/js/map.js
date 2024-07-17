
document.addEventListener('DOMContentLoaded', function () {
    loadOrders();
    // Initialize the map
    var map = L.map('map').setView([5.7207, -72.9292], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap rawwr'
    }).addTo(map);


    function loadGeoJSON() {
    fetch('paths.geojson')
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {

            data.features.forEach(function(feature, index) {
                var style = {
                    color: index === 0 ? 'blue' : 'grey', // Azul para el primer camino, gris para el segundo
                    weight: 5,
                    opacity: 0.65
                };

                L.geoJSON(feature, {
                    style: style,
                    onEachFeature: function (feature, layer) {
                        if (feature.properties && feature.properties.nombre) {
                            layer.bindPopup(feature.properties.nombre);
                        }
                    },
                    pointToLayer: function (feature, latlng) {
                        return L.marker(latlng, {});
                    }
                }).addTo(map);
            });

            map.fitBounds(L.geoJSON(data).getBounds());
        })
        .catch(function (error) {
            console.error('Error loading the GeoJSON:', error);
        });
}


    loadGeoJSON();

function loadOrders() {
    fetch('/orderDelivery/allOrders')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(ordenes => {
            const lista = document.getElementById('packages');
            lista.innerHTML = '';
            if (Array.isArray(ordenes)) {
                ordenes.forEach(orden => {
                    const elemento = document.createElement('li');
                    elemento.classList.add('list-group-item');
                    elemento.innerHTML = `
                        <h5>Orden ID: ${orden.id}</h5>
                        <p>Descripción: ${orden.description}</p>
                        <p>Fecha límite: ${orden.deadLine}</p>
                        <p>Estado: ${orden.state}</p>
                        <p>Observaciones: ${orden.observation}</p>
                        <p>Paquete: ${orden.pack.description} - ${orden.pack.weight}</p>
                    `;
                    lista.appendChild(elemento);
                });
            } else {
                console.error('Expected an array of orders, but got:', ordenes);
            }
        })
        .catch(error => console.error('Error al cargar las órdenes:', error));
}


    // Add event listener to the "finish route" button
    document.getElementById('finish-route').addEventListener('click', function () {
        console.log('Finish route button clicked');


    });
});

