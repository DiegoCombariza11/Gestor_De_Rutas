fetch('/packages/create', {
    method: 'POST',
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        'id': 12,
        'description': "no se que hace",
        'price': 12.5,
        "weight": "12"
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
    });
