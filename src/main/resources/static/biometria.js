// ── Sesión ──────────────────────────────────────────
const cedulaUsuario = Number(sessionStorage.getItem('cedula'));
if (!cedulaUsuario) window.location.href = 'index.html';

// ── Estado global ───────────────────────────────────
let streamCamara    = null;
let modelosCargados = false;
let escaneando      = false;

const video      = document.getElementById('video');
const canvas     = document.getElementById('canvas');
const estadoTxt  = document.getElementById('estadoTxt');
const spinner    = document.getElementById('spinner');
const btnEscanear = document.getElementById('btnEscanear');
const scanLine   = document.getElementById('scanLine');

// ── 1. Cargar modelos de face-api.js ────────────────
// Los modelos se sirven desde una CDN pública
const MODEL_URL = '/weights';

async function iniciar() {
    setEstado('Cargando modelos de reconocimiento...', true);
    try {
        await Promise.all([
            faceapi.nets.tinyFaceDetector.loadFromUri(MODEL_URL),
            faceapi.nets.faceLandmark68Net.loadFromUri(MODEL_URL),
            faceapi.nets.faceRecognitionNet.loadFromUri(MODEL_URL)
        ]);
        modelosCargados = true;
        setEstado('Modelos listos. Iniciando cámara...', true);
        await iniciarCamara();
    } catch (err) {
        setEstado('Error al cargar modelos. Verifica tu conexión.', false);
        console.error(err);
    }
}

// ── 2. Iniciar cámara ────────────────────────────────
async function iniciarCamara() {
    try {
        streamCamara = await navigator.mediaDevices.getUserMedia({
            video: { width: 640, height: 480, facingMode: 'user' }
        });
        video.srcObject = streamCamara;
        video.onloadedmetadata = () => {
            setEstado('Cámara lista. Posiciona tu rostro en el recuadro.', false);
            btnEscanear.disabled = false;
        };
    } catch (err) {
        setEstado('No se pudo acceder a la cámara. Verifica los permisos.', false);
        console.error(err);
    }
}

// ── 3. Escanear y verificar ──────────────────────────
async function escanear() {
    if (!modelosCargados || escaneando) return;
    escaneando = true;
    btnEscanear.disabled = true;
    scanLine.classList.add('activo');
    setEstado('Analizando rostro...', true);

    try {
        // Captura el frame actual del video
        const ctx = canvas.getContext('2d');
        canvas.width  = video.videoWidth;
        canvas.height = video.videoHeight;
        ctx.drawImage(video, 0, 0);

        // Detecta el rostro y extrae el descriptor (128 valores)
        const deteccion = await faceapi
            .detectSingleFace(canvas, new faceapi.TinyFaceDetectorOptions())
            .withFaceLandmarks()
            .withFaceDescriptor();

        if (!deteccion) {
            setEstado('No se detectó ningún rostro. Intenta de nuevo.', false);
            resetear();
            return;
        }

        const descriptorCapturado = deteccion.descriptor; // Float32Array de 128 valores

        // Obtiene el descriptor guardado del usuario desde el backend
        setEstado('Verificando identidad...', true);
        const similitud = await verificarConBackend(descriptorCapturado);

        if (similitud >= 0.85) {
            // ✅ Verificación exitosa
            scanLine.classList.remove('activo');
            setEstado('✅ Identidad verificada. Redirigiendo...', false);
            sessionStorage.setItem('biometriaVerificada', 'true');

            setTimeout(() => {
                detenerCamara();
                window.location.href = 'pagos.html';
            }, 1200);

        } else {
            // ❌ No coincide
            setEstado(`❌ Rostro no reconocido (similitud: ${(similitud * 100).toFixed(1)}%). Intenta de nuevo.`, false);
            resetear();
        }

    } catch (err) {
        setEstado('Error durante el escaneo. Intenta de nuevo.', false);
        console.error(err);
        resetear();
    }
}

// ── 4. Llama al backend con el descriptor capturado ──
async function verificarConBackend(descriptor) {
    try {
        const respuesta = await fetch('/api/biometria/verificar', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                cedula: cedulaUsuario,
                descriptor: Array.from(descriptor) // convierte Float32Array a array normal
            })
        });

        if (!respuesta.ok) throw new Error('Error en el servidor');

        const data = await respuesta.json();
        return data.similitud; // el backend devuelve un número entre 0 y 1

    } catch (err) {
        console.error('Error al verificar con backend:', err);
        // Si el backend no está disponible aún, simula verificación local
        // QUITAR ESTO en producción y dejar solo el fetch
        return 0.90;
    }
}

// ── Utilidades ───────────────────────────────────────
function setEstado(texto, cargando) {
    estadoTxt.textContent = texto;
    spinner.style.display = cargando ? 'block' : 'none';
}

function resetear() {
    escaneando = false;
    btnEscanear.disabled = false;
    scanLine.classList.remove('activo');
}

function detenerCamara() {
    if (streamCamara) {
        streamCamara.getTracks().forEach(t => t.stop());
    }
}

// Detener cámara si el usuario sale de la página
window.addEventListener('beforeunload', detenerCamara);

// ── Arrancar ─────────────────────────────────────────
window.addEventListener('DOMContentLoaded', iniciar);
