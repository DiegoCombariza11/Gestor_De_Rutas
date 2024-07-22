$(document).ready(function() {
    let shipmentIds = [];

    loadOrders().then(() => {
        $('#shipment-table-striped').DataTable();

        $('#shipment-table').on('change', '.shipment-checkbox', function() {
            var row = $(this).closest('tr');
            var shipmentId = row.find('td:eq(1)').text();
            var shipmentAddress = row.find('td:eq(2)').text();

            if ($(this).is(':checked')) {
                shipmentIds.push(shipmentId);
                $('#send-box').append('<p id="shipment-' + shipmentId + '">ID: ' + shipmentId + ' - Dirección: ' + shipmentAddress + '</p>');
            } else {
                shipmentIds = shipmentIds.filter(id => id !== shipmentId);
                $('#shipment-' + shipmentId).remove();
            }
        });

        $('#create-shipment-btn').on('click', function() {
            window.location.href = '/pages/CreateOrderDelivery.html';
        });

        $('#start-route-btn').on('click', function() {
            if (shipmentIds.length === 0) {
                alert('No se ha seleccionado ninguna orden');
                return;
            }
            document.cookie = 'orderIds=' + encodeURIComponent(JSON.stringify(shipmentIds)) + '; path=/';
            fetch('/api/startRoute', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            })
                .then(response => {
                    if (response.ok) {
                        window.location.href = '/pages/map.html';
                    } else {
                        alert('Error al iniciar la ruta');
                    }
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        });
    });
});

async function loadOrders() {
    const Orders = await fetch('/orderDelivery/showAll', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
    const orders = await Orders.json();
    let bodyTable = "";
    for (let order of orders) {
        if(order.state!=='DELIVERED'){
            bodyTable += '<tr><td><input type="checkbox" class="shipment-checkbox"></td><td>' + order.id + '</td><td>' + order.destination + '</td><td>' + order.description + '</td></tr>';
        }
    }
    $('#shipment-table').html(bodyTable);
}
