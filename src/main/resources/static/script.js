document.getElementById("loginForm").addEventListener("submit", async function (e) {

    e.preventDefault();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    try {

        const respuesta = await fetch("/api/usuarios/login", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                correo: email,
                contrasena: password
            })
        });

        const mensaje = await respuesta.text();

        if (mensaje.startsWith("OK:")) {
            const partes = mensaje.split(":")[1].split("|");
            const cedula = partes[0];
            const nombre = partes[1];
            sessionStorage.setItem("cedula", cedula);
            sessionStorage.setItem("nombre", nombre);
            window.location.href = "catalogo.html";
        } else {
            alert(mensaje);
        }

    } catch (error) {

        console.error(error);
        alert("Error al iniciar sesión");
    }
});