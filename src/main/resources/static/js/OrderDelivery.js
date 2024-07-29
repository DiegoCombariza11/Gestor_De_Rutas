$(document).ready(function () {
    $('#shipment-form').on('submit', function (event) {
        event.preventDefault();
        function replaceWithEmptyString(value) {
            return value.replace(/^\s+|\s+$/g, '') === '' ? '' : value;
        }
        var name = replaceWithEmptyString($('#buyer-name').val());
        var lastName = replaceWithEmptyString($('#buyer-lastname').val());
        var email = replaceWithEmptyString($('#buyer-email').val());
        var contact = replaceWithEmptyString($('#buyer-contact').val());
        var description = replaceWithEmptyString($('#product-description').val());
        var observation = replaceWithEmptyString($('#product-observation').val());
        var price = replaceWithEmptyString($('#product-price').val());
        var weight = replaceWithEmptyString($('#product-weight').val());
        var destination = replaceWithEmptyString($('#destination').val());
        var nameProduct = replaceWithEmptyString($('#product-name').val());
        if (!name || !lastName || !email || !contact || !description || !observation || price<0 || weight<0 || !destination || !nameProduct) {
            document.getElementById('error-message').textContent = 'Por favor, rellene todos los campos correctamente.';
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
                    document.getElementById('error-message').textContent = 'Error con la dirección ingresada.';
                }
            })
            .catch(error => {
                console.error("Error en la solicitud:", error);
            });
    });
});
