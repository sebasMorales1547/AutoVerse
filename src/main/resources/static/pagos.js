var montoVehiculo = parseInt(localStorage.getItem('montoVehiculo')) || 0;
var idPublicacion = parseInt(localStorage.getItem('idPublicacion')) || 0;

// Mostrar monto en pantalla
document.getElementById('monto-txt').textContent =
    '$' + montoVehiculo.toLocaleString('es-CO');

var seleccionado = null;
var pasarelasExternas = ['stripe', 'wompi', 'epayco'];

function seleccionar(el, nombre, tipo) {
    document.querySelectorAll('.pago-card').forEach(c => c.classList.remove('activo'));
    el.classList.add('activo');
    seleccionado = tipo;
    document.getElementById('metodo-txt').textContent = nombre;
    document.getElementById('btn-pagar').disabled = false;
}

function pagar() {
    if (!seleccionado) return;

    if (pasarelasExternas.includes(seleccionado)) {
        // Pasarela externa → llama al backend y abre el link
        fetch('/api/pagos/pasarela', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                tipo: seleccionado.toUpperCase(),
                idPublicacion: idPublicacion,
                monto: montoVehiculo
            })
        })
        .then(r => r.json())
        .then(data => {
            if (data.url) {
                window.open(data.url, '_blank');
            } else {
                alert('Error al conectar con la pasarela.');
            }
        })
        .catch(() => alert('Error de conexión con el servidor.'));

    } else {
        // Pago directo → llama a VentaServicio en el backend
        fetch('/api/pagos/directo', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                tipo: seleccionado.toUpperCase(),
                idPublicacion: idPublicacion,
                monto: montoVehiculo
            })
        })
        .then(r => r.json())
        .then(data => {
            if (data.exito) {
                window.location.href = 'index.html';
            } else {
                alert('Error: ' + data.mensaje);
            }
        })
        .catch(() => alert('Error de conexión con el servidor.'));
    }
}
