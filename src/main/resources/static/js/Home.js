$(document).ready(function() {
    let shipmentId='';
    loadOrders().then(() => {
        $('#shipment-table-striped').DataTable();
        $('#shipment-table').on('change', '.shipment-checkbox', function() {
            var row = $(this).closest('tr');
            shipmentId = row.find('td:eq(1)').text();
            var shipmentAddress = row.find('td:eq(2)').text();

            if ($(this).is(':checked')) {
                $('#send-box').append('<p id="shipment-' + shipmentId + '">ID: ' + shipmentId + ' - Dirección: ' + shipmentAddress + '</p>');
            } else {
                $('#shipment-' + shipmentId).remove();
            }
        });
        $('#create-shipment-btn').on('click', function() {
            window.location.href = '/pages/CreateOrderDelivery.html';
        });
        $('#start-route-btn').on('click', function() {
            // let shipmentIds = $('#send-box').children().map(function() {
            //     return $(this).text();
            // }).get();
            document.cookie = 'orderId=' + encodeURIComponent(shipmentId)+ '; path=/';
            fetch('/api/startRoute', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            })
                .then(response =>{
                    if (response.ok) {
                        window.location.href = '/pages/map.html';
                    }else{
                        alert('No se ha seleccionado ninguna orden');
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
        if(order.state==='PENDING') {
            bodyTable += '<tr><td><input type="checkbox" class="shipment-checkbox"></td><td>' + order.id + '</td><td>' + order.destination + '</td><td>' + order.description + '</td></tr>';
        }
    }
    $('#shipment-table').html(bodyTable);
}
