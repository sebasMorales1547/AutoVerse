document.getElementById("btnRegistro").addEventListener("click", () => {

    const cedula = document.getElementById("cedula").value.trim();
    const correo = document.getElementById("correo").value.trim();
    const nombre = document.getElementById("nombre").value.trim();
    const apellido = document.getElementById("apellido").value.trim();
    const contrasena = document.getElementById("contrasena").value.trim();
    const telefono = document.getElementById("telefono").value.trim();

    // Campos obligatorios
    if (!cedula || !correo || !nombre || !apellido || !contrasena || !telefono) {
        alert("Todos los campos son obligatorios");
        return;
    }

    // Cédula
    if (!/^\d+$/.test(cedula)) {
        alert("La cédula solo puede contener números");
        return;
    }

    // Correo
    const regexCorreo = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!regexCorreo.test(correo)) {
        alert("Ingrese un correo válido");
        return;
    }

    // Nombre
    if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(nombre)) {
        alert("El nombre solo puede contener letras");
        return;
    }

    // Apellido
    if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(apellido)) {
        alert("El apellido solo puede contener letras");
        return;
    }

    // Contraseña
    if (contrasena.length < 8) {
        alert("La contraseña debe tener mínimo 8 caracteres");
        return;
    }

    // Teléfono
    if (!/^\d+$/.test(telefono)) {
        alert("El teléfono solo puede contener números");
        return;
    }

    if (telefono.length < 10) {
        alert("El teléfono debe tener al menos 10 dígitos");
        return;
    }

    alert("Registro válido");

});