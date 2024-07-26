// Variables globales
var map;
var states = ['En Camino', 'Entregado', 'Devuelto'];

// Funciones de inicialización
document.addEventListener('DOMContentLoaded', initialize);


function initialize() {
    map = initializeMap();
    loadOrder();
    loadTricoInfo();
    addFinishRouteListener();
    setTimeout(loadGeoJSON.bind(null, map), 100);
}

// Funciones de mapa
function initializeMap() {
    let map = L.map('map').setView([5.704144, -72.9425035], 12);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap rawwr'
    }).addTo(map);
    return map;
}

function loadGeoJSON(map) {
    let uniqueVersion = new Date().getTime();
    Promise.all([
        fetch('/shortestPathAStar1.geojson?version=' + uniqueVersion).then(response => response.json()),
        fetch('/shortestPathAStar2.geojson?version=' + uniqueVersion).then(response => response.json()),
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
    let style = {
        color: geojsonIndex === 0 ? 'blue' : (geojsonIndex === 1) ? 'orange': 'green',
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
    let marker = L.marker([feature.geometry.coordinates[1], feature.geometry.coordinates[0]]);
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
    fetch('/orderDelivery/showOrders')
    .then(handleOrderResponse)
    .then(displayOrder)
    .catch(console.log.bind(null, 'Error:'));
}



function loadTricoInfo(){
    console.log('Loading trico info')
    fetch('/api/tricoInfo')
        .then(handleOrderResponse)
        .then(data => {
            document.getElementById('trico-speed').innerText = "Tiempo: "+ data.time+"min";
            document.getElementById('trico-distance').innerText = "Distancia: "+ data.distance+"m";
            console.log(data.distance+ data.time);
        });
}




function handleOrderResponse(response) {
    if (!response.ok) {
        throw new Error('Error al obtener la orden: ' + response.statusText);
    }
    return response.json();
}

function displayOrder(orders) {

    let orderInfoElement = document.querySelector('#order-info .data-container');
    orders.forEach(order =>{
        let buyer = order.buyer;
        appendElementToParent(orderInfoElement, 'p', 'ID: ' + order.id);
        appendElementToParent(orderInfoElement, 'p', 'Nombre: ' + buyer.firstName);
        appendElementToParent(orderInfoElement, 'p', 'Contacto: ' + buyer.contact);
        appendElementToParent(orderInfoElement, 'p', 'Observaciones: ' + order.observation);
        appendElementToParent(orderInfoElement, 'p', 'Dirección: ' + order.destination);
        let stateElement = createStateElement(order);
        orderInfoElement.appendChild(stateElement);

    })


}

function appendElementToParent(parent, elementType, text) {
    let element = document.createElement(elementType);
    element.innerText = text;
    parent.appendChild(element);
}

function createStateElement(order) {
    let stateElement = document.createElement('select');
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
    let newstate = this.value;
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
    let orderState = document.querySelector('#order-info select').value;
    if(orderState == 'En Camino'){
        let confirmResult =window.confirm('La orden no ha sido entregada, ¿desea marcar como cancelado y finalizar la ruta?');
        if (confirmResult){
            updateOrderState('Cacelado')
        }else {
            return;
        }
    }
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