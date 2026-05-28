document.getElementById("loginForm").addEventListener("submit", function(e) {
    e.preventDefault();

    const email    = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    if (email === "admin@autoverse.com" && password === "123456") {
        window.location.href = "catalogo.html";
    } else {
        alert("Correo o contraseña incorrectos");
    }
});