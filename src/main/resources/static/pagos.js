var montoVehiculo = parseInt(sessionStorage.getItem('montoVehiculo')) || 0;
var idPublicacion = parseInt(sessionStorage.getItem('idPublicacion')) || 0;
var cedulaUsuario = Number(sessionStorage.getItem('cedula'));

if (!cedulaUsuario) window.location.href = 'index.html';

document.getElementById('monto-txt').textContent =
    '$' + montoVehiculo.toLocaleString('es-CO');

var seleccionado = null;

// solo pasarelas externas
var pasarelasExternas = ['stripe', 'wompi', 'epayco'];

var urlsPasarelas = {
    stripe: 'https://buy.stripe.com/test_6oU28t0yW3ed281axDbo400',
    epayco: 'https://payco.link/fee1ce6e-c699-4307-8a3b-e9f93ad24423',
    wompi:  'https://checkout.wompi.co/l/VPOS_wRaEQ1'
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

    // SOLO pasarelas externas
    window.open(urlsPasarelas[seleccionado], '_blank');
}