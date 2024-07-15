$(document).ready(function() {
    loadOrders().then(() => {
        $('#shipment-table-striped').DataTable();
        $('#shipment-table').on('change', '.shipment-checkbox', function() {
            var row = $(this).closest('tr');
            var shipmentId = row.find('td:eq(1)').text();
            var shipmentAddress = row.find('td:eq(2)').text();

            if ($(this).is(':checked')) {
                $('#send-box').append('<p id="shipment-' + shipmentId + '">ID: ' + shipmentId + ' - Dirección: ' + shipmentAddress + '</p>');
            } else {
                $('#shipment-' + shipmentId).remove();
            }
        });

        $('#start-route-btn').on('click', function() {
            alert('Route started!');
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
        bodyTable += '<tr><td><input type="checkbox" class="shipment-checkbox"></td><td>' + order.id + '</td><td>' + order.shopper.direction + '</td><td>' + order.description + '</td></tr>';
    }
    $('#shipment-table').html(bodyTable);
}
