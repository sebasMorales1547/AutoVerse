// Lee los datos guardados por catalogo.js
var montoVehiculo = parseInt(sessionStorage.getItem('montoVehiculo')) || 0;
var idPublicacion = parseInt(sessionStorage.getItem('idPublicacion')) || 0;
var cedulaUsuario = Number(sessionStorage.getItem('cedula'));

// Si no hay sesión, volver al inicio
if (!cedulaUsuario) window.location.href = 'index.html';

// Mostrar monto en pantalla
document.getElementById('monto-txt').textContent =
    '$' + montoVehiculo.toLocaleString('es-CO');

var seleccionado = null;
var pasarelasExternas = ['stripe', 'wompi', 'epayco'];

// Links reales de cada pasarela (los mismos que tiene el backend)
var urlsPasarelas = {
    stripe: 'https://buy.stripe.com/test_6oU28t0yW3ed281axDbo400',
    epayco: 'https://payco.link/fee1ce6e-c699-4307-8a3b-e9f93ad24423',
    wompi:  'https://checkout.wompi.co/l/test_VPOS_1IcxE5'
};

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
        // Abre la pasarela externa en pestaña nueva directamente
        window.open(urlsPasarelas[seleccionado], '_blank');

    } else {
        // Pago directo → llama a la compra en el backend
        fetch('/api/ofertas/comprar', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                idPublicacion: idPublicacion,
                cedula: cedulaUsuario
            })
        })
        .then(r => r.text())
        .then(msg => {
            alert(msg);
            if (msg.toLowerCase().includes('éxito') || msg.toLowerCase().includes('correctamente')) {
                sessionStorage.removeItem('idPublicacion');
                sessionStorage.removeItem('montoVehiculo');
                window.location.href = 'catalogo.html';
            }
        })
        .catch(() => alert('Error de conexión con el servidor.'));
    }
}
