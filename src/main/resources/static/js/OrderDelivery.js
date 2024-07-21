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
    $('#shipment-form').on('submit', function(event) {
        event.preventDefault();
        var formData = $(this).serialize();
        console.log(formData);
        alert('Orden de envío creada con éxito!');
    });
});
