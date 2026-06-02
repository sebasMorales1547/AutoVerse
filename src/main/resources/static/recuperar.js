async function solicitarCodigo() {

    const correo = document.getElementById("correo").value.trim();

    if (!correo) {
        alert("Ingrese un correo");
        return;
    }

    try {

        const respuesta = await fetch("/api/usuarios/recuperar", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                correo: correo
            })
        });

        const mensaje = await respuesta.text();

        alert(mensaje);

    } catch (error) {

        console.error(error);
        alert("Error al enviar el código");
    }
}

async function cambiarContrasena() {

    const correo = document.getElementById("correo").value.trim();
    const codigo = document.getElementById("codigo").value.trim();
    const nuevaContrasena = document.getElementById("nuevaContrasena").value.trim();

    if (!correo || !codigo || !nuevaContrasena) {
        alert("Complete todos los campos");
        return;
    }

    try {

        const respuesta = await fetch("/api/usuarios/cambiar-contrasena", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                correo: correo,
                codigo: codigo,
                nuevaContrasena: nuevaContrasena
            })
        });

        const mensaje = await respuesta.text();

        alert(mensaje);

        if (mensaje.toLowerCase().includes("actualizada")) {
            window.location.href = "index.html";
        }

    } catch (error) {

        console.error(error);
        alert("Error al cambiar la contraseña");
    }
}