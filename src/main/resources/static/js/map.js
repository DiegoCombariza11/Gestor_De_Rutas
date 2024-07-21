// Variables globales
var map;
var states = ['En Camino', 'Entregado', 'Devuelto'];

// Funciones de inicialización
document.addEventListener('DOMContentLoaded', initialize);

function initialize() {
    map = initializeMap();
    loadOrder();
    addFinishRouteListener();
    setTimeout(loadGeoJSON.bind(null, map), 1000);
}

// Funciones de mapa
function initializeMap() {
    var map = L.map('map').setView([5.704144, -72.9425035], 13);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap rawwr'
    }).addTo(map);
    return map;
}

function loadGeoJSON(map) {
    var uniqueVersion = new Date().getTime();
    Promise.all([
        fetch('/shortestPathAStar.geojson?version=' + uniqueVersion).then(response => response.json()),
        fetch('/shortestPath.geojson?version=' + uniqueVersion).then(response => response.json()),
    ])
    .then(handleGeoJSONData)
    .catch(console.error.bind(null, 'Error loading the geojson:'));
}

function handleGeoJSONData(data) {
    data.forEach(processGeoJSON);
   map.fitBounds(L.geoJSON(data[0]).getBounds());
}

function processGeoJSON(geojson, geojsonIndex) {
    geojson.features.forEach(processFeature.bind(null, geojsonIndex));
}

function processFeature(geojsonIndex, feature) {
    var style = {
        color: geojsonIndex === 0 ? 'blue' : 'green',
        weight: 5,
        opacity: 0.65
    };
    if (feature.geometry.type === "Point") {
        addMarkerToMap(feature);
    } else {
        L.geoJSON(feature, {
            style: style,
            onEachFeature: onEachFeature
        }).addTo(map);
    }
}

function addMarkerToMap(feature) {
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
}

function onEachFeature(feature, layer) {
    if (feature.properties && feature.properties.nombre) {
        layer.bindPopup(feature.properties.nombre);
    }
}

// Funciones de orden
function loadOrder() {
    fetch('/orderDelivery/showOrder')
    .then(handleOrderResponse)
    .then(displayOrder)
    .catch(console.log.bind(null, 'Error:'));
}

function handleOrderResponse(response) {
    if (!response.ok) {
        throw new Error('Error al obtener la orden: ' + response.statusText);
    }
    return response.json();
}

function displayOrder(order) {
    let buyer = order.buyer;
    var orderInfoElement = document.querySelector('#order-info .data-container');

    appendElementToParent(orderInfoElement, 'p', 'ID: ' + order.id);
    appendElementToParent(orderInfoElement, 'p', 'Nombre: ' + buyer.firstName);
    appendElementToParent(orderInfoElement, 'p', 'Contacto: ' + buyer.contact);
    appendElementToParent(orderInfoElement, 'p', 'Observaciones: ' + order.observation);

    var stateElement = createStateElement(order);
    orderInfoElement.appendChild(stateElement);
}

function appendElementToParent(parent, elementType, text) {
    var element = document.createElement(elementType);
    element.innerText = text;
    parent.appendChild(element);
}

function createStateElement(order) {
    var stateElement = document.createElement('select');
    states.forEach(function (state) {
        var optionElement = document.createElement('option');
        optionElement.value = state;
        optionElement.text = state;
        if (state === order.state) {
            optionElement.selected = true;
        }
        stateElement.appendChild(optionElement);
    });

    stateElement.addEventListener('change', updateOrderState);
    return stateElement;
}

function updateOrderState() {
    var newstate = this.value;
    fetch('/orderDelivery/updateState', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({state: newstate})
    })
    .then(handleStateUpdateResponse)
    .catch(console.log.bind(null, 'Error:'));
}

function handleStateUpdateResponse(response) {
    if (!response.ok) {
        throw new Error('Error al actualizar el estado: ' + response.statusText);
    } else {
        console.log("Estado actualizado");
    }
}

// Funciones de ruta
function addFinishRouteListener() {
    document.getElementById('finish-route').addEventListener('click', finishRoute);
}

function finishRoute() {
    console.log('Finish route button clicked');
    fetch('/api/endRoute', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })
    .then(handleFinishRouteResponse)
    .catch(console.error.bind(null, 'No se pudo finalizar la ruta:'));
}

function handleFinishRouteResponse(response) {
    if (response.ok) {
        window.location.href = '/pages/OrderDelivery.html';
    } else {
        alert('No se pudo finalizar la ruta');
    }
}