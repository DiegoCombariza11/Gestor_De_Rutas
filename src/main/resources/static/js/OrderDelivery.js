$(document).ready(function () {
    $('#shipment-form').on('submit', function (event) {
        event.preventDefault();
        var name = $('#buyer-name').val();
        var lastName = $('#buyer-lastname').val();
        var email = $('#buyer-email').val();
        var contact = $('#buyer-contact').val();
        var description = $('#product-description').val();
        var observation = $('#product-observation').val();
        var price = $('#product-price').val();
        var weight = $('#product-weight').val();
        var destination = $('#destination').val();
        var nameProduct = $('#product-name').val();

        if (!name || !lastName || !email || !contact || !description || !observation || !price || !weight || !destination || !nameProduct) {

            return;
        }

        fetch("/orderDelivery/save", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                'destination': destination,
                'personName': name,
                'personLastName': lastName,
                'personEmail': email,
                'personPhone': contact,
                'description': description,
                'observation': observation,
                'price': price,
                'weight': weight,
                'nameProduct': nameProduct
            })
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = '/pages/OrderDelivery.html';
                } else {
                    document.getElementById('error-message').textContent = 'Error con la direccion ingresada';
                }
            })
            .catch(error => {
                console.error("Error en la solicitud:", error);
            });
    });
});
