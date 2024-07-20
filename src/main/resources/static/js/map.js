document.addEventListener('DOMContentLoaded', function () {
    var map = initializeMap();
    loadOrders();
    loadGeoJSON(map);
    addFinishRouteListener();
});

function initializeMap() {
    var map = L.map('map').setView([5.7207, -72.9292], 13);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap rawwr'
    }).addTo(map);
    return map;
}

function onEachFeature(feature, layer) {
    if (feature.properties && feature.properties.nombre) {
        layer.bindPopup(feature.properties.nombre);
    }
}

function loadGeoJSON(map) {
    fetch('/pages/paths.geojson')
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            data.features.forEach(function (feature, index) {
                var style = {
                    color: index === 0 ? 'blue' : 'grey',
                    weight: 5,
                    opacity: 0.65
                };
                if (feature.geometry.type === "Point") {
                    var marker = L.marker([feature.geometry.coordinates[1], feature.geometry.coordinates[0]]);
                    if (feature.properties && feature.properties.osmid) {
                        marker.bindPopup("OSM ID: " + feature.properties.osmid);
                        marker.setIcon(L.divIcon({
                            className: 'custom-icon',
                            html: `<div>${feature.properties.osmid}</div>`,
                            iconSize: [30, 30]
                        }));
                    }
                    marker.addTo(map);
                } else {
                    L.geoJSON(feature, {
                        style: style,
                        onEachFeature: onEachFeature
                    }).addTo(map);
                }
            });
            map.fitBounds(L.geoJSON(data).getBounds());
        })
        .catch(function (error) {
            console.error('Error cargando el geojson:', error);
        });
}


function loadOrders() {
    fetch('/orderDelivery/allOrders')
        .then(response => {
            if (!response.ok) {
                throw new Error('la respuesta no estuvo ok');
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
                console.error('Se esperaban varias órdenes pero se obtuvo:', ordenes);
            }
        })
        .catch(error => console.error('Error al cargar las órdenes:', error));
}

function addFinishRouteListener() {
    document.getElementById('finish-route').addEventListener('click', function () {
        console.log('Finish route button clicked');
        fetch('/api/endRoute', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = '/pages/OrderDelivery.html';
                }else{
                    alert('No se pudo finalizar la ruta');
                }
            })
            .catch(error => console.error('No se pudo finalizar la ruta:', error));
    });
}