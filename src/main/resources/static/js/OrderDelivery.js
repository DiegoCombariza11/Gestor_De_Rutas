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
