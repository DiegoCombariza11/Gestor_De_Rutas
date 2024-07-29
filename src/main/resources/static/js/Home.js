$(document).ready(function () {

    loadOrders().then(() => {
        $('#shipment-table-striped').DataTable();
        $('#bodega-btn').addClass('btn-primary').removeClass('btn-secondary');
    });

    $('#shipment-table').on('click', '.delete-btn', function () {
        var row = $(this).closest('tr');
        var shipmentId = row.find('td:eq(1)').text();
        if (confirm(`¿Está seguro de que desea eliminar la orden ${shipmentId}?`)) {
            deleteOrder(shipmentId);
        }
    });

    $('#shipment-table').on('click', '.start-route-btn', function () {
        var row = $(this).closest('tr');
        var id = row.find('td:eq(1)').text();
        startRoute(id);
    });

    $('#create-shipment-btn').on('click', function () {
        //document.cookie = 'orderId= ; path=/';
        window.location.href = '/pages/CreateOrderDelivery.html';
    });

    $('#bodega-btn').on('click', function () {
        $('#shipment-table-striped').DataTable().destroy();
        loadOrders().then(() => {
            $('#shipment-table-striped').DataTable();
        });
        $('#bodega-btn').addClass('btn-primary').removeClass('btn-secondary');
        $('#entregados-btn').addClass('btn-secondary').removeClass('btn-primary');
    });

    $('#entregados-btn').on('click', function () {
        $('#shipment-table-striped').DataTable().destroy();
        loadOrdersDelivered().then(() => {
            $('#shipment-table-striped').DataTable();
        });
        $('#entregados-btn').addClass('btn-primary').removeClass('btn-secondary');
        $('#bodega-btn').addClass('btn-secondary').removeClass('btn-primary');
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
    let headers = `
        <tr>
            <th>Acciones</th>
            <th>Id</th>
            <th>Dirección</th>
            <th>Detalles</th>
            <th>Estado</th>
        </tr>`;
    $('#table-headers').html(headers);
    let bodyTable = "";
    for (let order of orders) {
        if (order.state !== 'DELIVERED') {
            orderStateSpanish = null;
            if (order.state === 'PENDING') {
                orderStateSpanish = 'Pendiente';
            }else if (order.state === 'SHIPPED') {
                orderStateSpanish= 'En camino'
            }else if (order.state === 'CANCELED') {
                orderStateSpanish = 'Cancelado';
            }
            bodyTable += `
                <tr>
                    <td>
                        <button class="btn btn-sm btn-danger delete-btn" id="deleteOrder">Borrar</button>
                        <button class="btn btn-sm btn-success start-route-btn" id="startRouteIcon">Iniciar ruta</button>
                    </td>
                    <td>${order.id}</td>
                    <td>${order.destination}</td>
                    <td>${order.description}</td>
                    <td>${orderStateSpanish}</td>
                </tr>`;
        }
    }
    $('#shipment-table').html(bodyTable);
}

async function loadOrdersDelivered() {
    const Orders = await fetch('/orderDelivery/showAll', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
    const orders = await Orders.json();
    let headers = `
        <tr>
            <th>Id</th>
            <th>Dirección</th>
            <th>Detalles</th>
            <th>Estado</th>
        </tr>`;
    $('#table-headers').html(headers);
    let bodyTable = "";
    for (let order of orders) {
        if (order.state === 'DELIVERED') {
            bodyTable += `
                <tr>
                    <td>${order.id}</td>
                    <td>${order.destination}</td>
                    <td>${order.description}</td>
                    <td>Entregado</td>
                </tr>`;
        }
    }
    $('#shipment-table').html(bodyTable);
}

async function deleteOrder(id) {
    document.cookie = 'orderId=' + id + '; path=/';
    const response = await fetch(`/orderDelivery/delete`, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
    if (response.ok) {
        alert('Orden eliminada correctamente');
        loadOrders();
    } else {
        alert('Error al eliminar la orden');
    }
}

async function startRoute(id) {
    document.cookie = 'orderId=' + encodeURIComponent(id) + '; path=/';
    const response = await fetch('/api/startRoute', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        credentials: 'include'
    });
    if (response.ok) {
        window.location.href = '/pages/map.html';
    } else {
        alert('Error al iniciar la ruta');
    }
}
