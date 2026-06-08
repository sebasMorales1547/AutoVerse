document.getElementById("btnRegistro").addEventListener("click", async () => {

    const cedula      = document.getElementById("cedula").value.trim();
    const correo      = document.getElementById("correo").value.trim();
    const nombre      = document.getElementById("nombre").value.trim();
    const apellido    = document.getElementById("apellido").value.trim();
    const contrasena  = document.getElementById("contrasena").value.trim();
    const telefono    = document.getElementById("telefono").value.trim();
    const fotoInput   = document.getElementById("foto");
    const fotoArchivo = fotoInput ? fotoInput.files[0] : null;

    // Validaciones
    if (!cedula || !correo || !nombre || !apellido || !contrasena || !telefono) {
        alert("Todos los campos son obligatorios"); return;
    }
    if (!/^\d+$/.test(cedula)) { alert("La cédula solo puede contener números"); return; }
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(correo)) { alert("Ingrese un correo válido"); return; }
    if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(nombre))   { alert("El nombre solo puede contener letras"); return; }
    if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(apellido)) { alert("El apellido solo puede contener letras"); return; }
    if (contrasena.length < 8) { alert("La contraseña debe tener mínimo 8 caracteres"); return; }
    if (!/^\d+$/.test(telefono) || telefono.length < 10) { alert("Teléfono inválido"); return; }
    if (!fotoArchivo) { alert("Debes subir una foto de tu rostro para el registro biométrico"); return; }

    try {
        // PASO 1: Registrar usuario
        const usuario = {
            cedula: parseInt(cedula), nombre, apellido,
            correo, contrasena, telefono
        };

        const resRegistro = await fetch("/api/usuarios/registro", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(usuario)
        });
        const msgRegistro = await resRegistro.text();

        if (!msgRegistro.toLowerCase().includes("correctamente")) {
            alert(msgRegistro); return;
        }

        // PASO 2: Subir foto biométrica
        const formFoto = new FormData();
        formFoto.append("cedula", cedula);
        formFoto.append("foto", fotoArchivo);

        const resFoto = await fetch("/api/biometria/registrar-foto", {
            method: "POST",
            body: formFoto
        });
        const msgFoto = await resFoto.text();

        if (!msgFoto.toLowerCase().includes("correctamente")) {
            alert("Usuario creado pero error al guardar la foto: " + msgFoto);
            window.location.href = "index.html";
            return;
        }

        // PASO 3: Extraer descriptor biométrico con face-api y guardarlo
        await registrarDescriptorBiometrico(cedula, fotoArchivo);

        alert("✅ Cuenta creada correctamente. Ya puedes iniciar sesión.");
        window.location.href = "index.html";

    } catch (error) {
        console.error(error);
        alert("Error al conectar con el servidor");
    }
});

// Extrae el descriptor facial de la foto usando face-api.js y lo guarda
async function registrarDescriptorBiometrico(cedula, archivoFoto) {
    try {
        // Cargar modelos si no están cargados
        const MODEL_URL = 'https://cdn.jsdelivr.net/npm/face-api.js@0.22.2/weights';
        if (!faceapi.nets.tinyFaceDetector.isLoaded) {
            await Promise.all([
                faceapi.nets.tinyFaceDetector.loadFromUri(MODEL_URL),
                faceapi.nets.faceLandmark68Net.loadFromUri(MODEL_URL),
                faceapi.nets.faceRecognitionNet.loadFromUri(MODEL_URL)
            ]);
        }

        // Crear imagen temporal para procesar
        const img = await faceapi.bufferToImage(archivoFoto);
        const deteccion = await faceapi
            .detectSingleFace(img, new faceapi.TinyFaceDetectorOptions())
            .withFaceLandmarks()
            .withFaceDescriptor();

        if (!deteccion) {
            console.warn("No se detectó rostro en la foto. El descriptor no fue guardado.");
            return;
        }

        // Enviar descriptor al backend
        await fetch("/api/biometria/registrar-descriptor", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                cedula: parseInt(cedula),
                descriptor: Array.from(deteccion.descriptor)
            })
        });

    } catch (err) {
        console.error("Error al registrar descriptor biométrico:", err);
    }
}
