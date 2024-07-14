fetch("/orderDelivery/save",{
    method: 'POST',
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        'id': 12,
        'direction': "carrera 45",
        'personName': "Juan",
        'personLastName': "Perez",
        'personEmail': "12@gmail.com",
        'personId': "126",
        'personPhone': "123456",
        'deadLine': "2021-06-23",
        'state': "En camino",
        'description': 'no se que hace',
        'observation': 'no se'
    })
})
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response;
    })
    .then(data => {
        console.log(data);
})
$(document).ready(function() {
    $('.shipment-checkbox').on('change', function() {
        var row = $(this).closest('tr');
        var shipmentId = row.find('td:eq(1)').text();
        var shipmentAddress = row.find('td:eq(2)').text();

        if ($(this).is(':checked')) {
            $('#send-box').append('<p id="shipment-' + shipmentId + '">ID: '+ shipmentId + ' - Dirección: ' + shipmentAddress + '</p>');
        } else {
            $('#shipment-' + shipmentId).remove();
        }
    });

    $('#start-route-btn').on('click', function() {
        // Code to start the route goes here
        alert('Route started!');
    });
});
