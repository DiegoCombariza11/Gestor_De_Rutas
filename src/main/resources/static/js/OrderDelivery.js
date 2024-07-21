$(document).ready(function() {
    document.getElementById('submit').addEventListener('click', function() {
        var name = document.getElementById('buyer-name').value;
        var lastName = document.getElementById('buyer-lastname').value;
        var email = document.getElementById('buyer-email').value;
        var contact = document.getElementById('buyer-contact').value;
        var description = document.getElementById('product-description').value;
        var observation = document.getElementById('product-observation').value;
        var price = document.getElementById('product-price').value;
        var weight = document.getElementById('product-weight').value;
        var destination = document.getElementById('destination').value;
        var nameProduct= document.getElementById('product-name').value;
        fetch("/orderDelivery/save",{
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
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                window.location.href = '/pages/OrderDelivery.html';
            })
            .then(data => {
                console.log(data);
            })
    });
});
