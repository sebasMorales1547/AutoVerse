// ── Sesión ──────────────────────────────────────────
const cedulaUsuario = Number(sessionStorage.getItem("cedula"));
const nombreUsuario = sessionStorage.getItem("nombre");

if (!cedulaUsuario) window.location.href = "index.html";

// ── Estado ──────────────────────────────────────────
let vehiculos = [];

// ── Carga inicial ───────────────────────────────────
function cargarVehiculos() {
    fetch("/api/publicaciones/disponibles")
        .then(res => res.json())
        .then(data => {
            vehiculos = data;
            renderVehiculos(vehiculos);
        })
        .catch(err => console.error("Error cargando vehículos:", err));
}

function formatPrecio(n) {
    return "$" + Number(n).toLocaleString("es-CO");
}

function badgeEstado(estado) {
    const colores = {
        "DISPONIBLE": "#22c55e",
        "VENDIDO":    "#ef4444",
        "RESERVADO":  "#f59e0b"
    };
    const color = colores[estado] || "#888";
    return `<span class="badge" style="background:${color}22; color:${color}; border:1px solid ${color}55">${estado}</span>`;
}

function calcularCountdown(fechaLimite) {
    if (!fechaLimite) return null;
    const diff = new Date(fechaLimite) - new Date();
    if (diff <= 0) return "Expirada";
    const dias  = Math.floor(diff / (1000 * 60 * 60 * 24));
    const horas = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const mins  = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
    const segs  = Math.floor((diff % (1000 * 60)) / 1000);
    if (dias > 0) return `${dias}d ${horas}h ${mins}m`;
    return `${horas}h ${mins}m ${segs}s`;
}

function renderVehiculos(lista) {
    const grid  = document.getElementById("vehiclesGrid");
    const empty = document.getElementById("emptyState");
    grid.innerHTML = "";

    if (lista.length === 0) {
        empty.style.display = "flex";
        return;
    }
    empty.style.display = "none";

    lista.forEach((v, i) => {
        const card = document.createElement("div");
        card.className = "vehicle-card";
        card.style.animationDelay = (i * 0.07) + "s";
        card.innerHTML = `
            <div class="card-img-wrap">
                ${v.tipo === 'SUBASTA' ? `<div class="card-countdown" id="cd-${v.idPublicacion}">⏱ ...</div>` : ''}
                <img src="${v.imagen || ''}" alt="${v.marca} ${v.modelo}"
                     onerror="this.src=''; this.parentElement.classList.add('no-img')"
                     loading="lazy">
                <div class="card-badge">${badgeEstado(v.estado)}</div>
            </div>
            <div class="card-body">
                <p class="card-marca">${v.marca}</p>
                <p class="card-modelo">${v.modelo}</p>
                <p class="card-precio">${formatPrecio(v.precio)}</p>
                <div class="card-specs">
                    <span class="spec">
                        <svg viewBox="0 0 24 24" fill="none"><path d="M12 2a7 7 0 017 7c0 5-7 13-7 13S5 14 5 9a7 7 0 017-7z" stroke="currentColor" stroke-width="1.5"/><circle cx="12" cy="9" r="2.5" stroke="currentColor" stroke-width="1.5"/></svg>
                        ${(v.kilometraje || 0).toLocaleString()} km
                    </span>
                    <span class="spec">
                        <svg viewBox="0 0 24 24" fill="none"><rect x="6" y="3" width="9" height="15" rx="2" stroke="currentColor" stroke-width="1.5"/><path d="M15 7h2a2 2 0 010 4h-2" stroke="currentColor" stroke-width="1.5"/><path d="M9 18v2M12 18v2" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"/></svg>
                        ${v.combustible || ''}
                    </span>
                    <span class="spec">
                        <svg viewBox="0 0 24 24" fill="none"><rect x="3" y="6" width="18" height="13" rx="2" stroke="currentColor" stroke-width="1.5"/><path d="M8 6V4M16 6V4" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"/><path d="M3 10h18" stroke="currentColor" stroke-width="1.5"/></svg>
                        ${v.anio || ''}
                    </span>
                </div>
                <button class="btn-detalles" onclick="verDetalle(${v.idPublicacion})">Ver detalles →</button>
            </div>
        `;
        grid.appendChild(card);
    });

    if (window._countdownInterval) clearInterval(window._countdownInterval);
    window._countdownInterval = setInterval(() => {
        vehiculos.forEach(v => {
            if (v.tipo !== 'SUBASTA') return;
            const el = document.getElementById(`cd-${v.idPublicacion}`);
            if (!el) return;
            const texto = calcularCountdown(v.fechaLimite);
            if (texto === "Expirada") {
                el.textContent = "⏱ Subasta cerrada";
                el.style.background = "#ef444488";
                el.style.color = "#fff";
            } else {
                el.textContent = "⏱ " + texto;
            }
        });
    }, 1000);
}

function verDetalle(id) {
    const v = vehiculos.find(x => x.idPublicacion === id);
    if (!v) return;

    const esSubasta = v.tipo === "SUBASTA";
    let seccionAccion = "";

    if (esSubasta) {
        const fechaLimite = v.fechaLimite ? new Date(v.fechaLimite) : null;
        const fechaStr = fechaLimite
            ? `${fechaLimite.toLocaleDateString("es-CO")} ${fechaLimite.toLocaleTimeString("es-CO")}`
            : "Sin fecha límite";

        seccionAccion = `
            <div class="detalle-accion">
                <div class="subasta-info">
                    <span class="subasta-label">Puja actual</span>
                    <span class="subasta-monto">${formatPrecio(v.montoActual)}</span>
                    <span class="subasta-limite">Cierra: ${fechaStr}</span>
                </div>
                <div class="subasta-input-wrap">
                    <input type="number" id="inputPuja" placeholder="Tu oferta" class="input-puja">
                    <button class="btn-pujar" onclick="pujar(${v.idPublicacion}, ${v.montoActual})">Pujar →</button>
                </div>
            </div>
        `;
    } else {
        seccionAccion = `
            <div class="detalle-accion">
                <button class="btn-comprar" onclick="irAPagos(${v.idPublicacion}, ${v.precio})">Comprar ahora →</button>
            </div>
        `;
    }

    document.getElementById("detallePanel").innerHTML = `
        <button class="detalle-cerrar" onclick="cerrarDetalle()">✕</button>
        <div class="detalle-img-wrap">
            <img src="${v.imagen || ''}" alt="${v.marca} ${v.modelo}"
                 onerror="this.style.display='none'">
            <div class="detalle-estado">${badgeEstado(v.estado)}</div>
        </div>
        <div class="detalle-content">
            <p class="detalle-marca">${v.marca}</p>
            <h2 class="detalle-titulo">${v.titulo}</h2>
            <p class="detalle-precio">${formatPrecio(v.precio)}</p>
            <p class="detalle-desc">${v.descripcion}</p>
            <div class="detalle-divider"></div>
            <h3 class="detalle-subtitulo">Especificaciones</h3>
            <div class="detalle-specs-grid">
                <div class="dspec"><span class="dspec-label">Placa</span><span class="dspec-valor">${v.placa || ''}</span></div>
                <div class="dspec"><span class="dspec-label">Marca</span><span class="dspec-valor" style="text-transform:capitalize">${v.marca}</span></div>
                <div class="dspec"><span class="dspec-label">Modelo</span><span class="dspec-valor">${v.modelo}</span></div>
                <div class="dspec"><span class="dspec-label">Año</span><span class="dspec-valor">${v.anio || ''}</span></div>
                <div class="dspec"><span class="dspec-label">Kilometraje</span><span class="dspec-valor">${(v.kilometraje || 0).toLocaleString()} km</span></div>
                <div class="dspec"><span class="dspec-label">Color</span><span class="dspec-valor">${v.color || ''}</span></div>
                <div class="dspec"><span class="dspec-label">Combustible</span><span class="dspec-valor">${v.combustible || ''}</span></div>
                <div class="dspec"><span class="dspec-label">Estado</span><span class="dspec-valor">${v.estado}</span></div>
            </div>
            <div class="detalle-divider"></div>
            ${seccionAccion}
        </div>
    `;

    document.getElementById("detalleOverlay").classList.add("active");
}

// NUEVO: guarda los datos y redirige a pagos.html
function irAPagos(idPublicacion, precio) {
    sessionStorage.setItem('idPublicacion', idPublicacion);
    sessionStorage.setItem('montoVehiculo', precio);
    window.location.href = 'pagos.html';
}

function pujar(idPublicacion, montoActual) {
    const monto = Number(document.getElementById("inputPuja").value);
    if (!monto || monto <= montoActual) {
        alert(`Tu puja debe ser mayor a ${formatPrecio(montoActual)}`);
        return;
    }
    fetch("/api/ofertas/pujar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ idPublicacion, monto, cedula: cedulaUsuario })
    })
    .then(res => res.text())
    .then(msg => { alert(msg); cerrarDetalle(); cargarVehiculos(); })
    .catch(() => alert("Error al pujar"));
}

function cerrarDetalle(e) {
    if (!e || e.target === document.getElementById("detalleOverlay")) {
        document.getElementById("detalleOverlay").classList.remove("active");
    }
}

function aplicarFiltros() {
    const texto   = document.getElementById("searchInput").value.toLowerCase();
    const marca   = document.getElementById("filterMarca").value;
    const precioR = document.getElementById("filterPrecio").value;
    const anio    = document.getElementById("filterAnio").value;

    const resultado = vehiculos.filter(v => {
        const matchTexto = v.marca?.toLowerCase().includes(texto) || v.modelo?.toLowerCase().includes(texto);
        const matchMarca = !marca || v.marca === marca;
        const matchAnio  = !anio  || v.anio === Number(anio);
        let matchPrecio  = true;
        if (precioR) {
            const [min, max] = precioR.split("-").map(Number);
            matchPrecio = v.precio >= min && v.precio <= max;
        }
        return matchTexto && matchMarca && matchAnio && matchPrecio;
    });

    renderVehiculos(resultado);
}

["searchInput", "filterMarca", "filterPrecio", "filterAnio"].forEach(id => {
    document.getElementById(id).addEventListener(id === "searchInput" ? "input" : "change", aplicarFiltros);
});

function abrirModal() { document.getElementById("modalOverlay").classList.add("active"); }

function cerrarModal(e) {
    if (!e || e.target === document.getElementById("modalOverlay")) {
        document.getElementById("modalOverlay").classList.remove("active");
    }
}

function toggleSubasta() {
    const tipo = document.getElementById("mTipo").value;
    document.getElementById("camposSubasta").style.display = tipo === "SUBASTA" ? "grid" : "none";
}

function agregarVehiculo() {
    const get = id => document.getElementById(id).value.trim();

    const placa       = get("mPlaca").toUpperCase();
    const marca       = get("mMarca").toLowerCase();
    const modelo      = get("mModelo");
    const anio        = get("mAnio");
    const km          = get("mKm");
    const color       = get("mColor");
    const precio      = get("mPrecio");
    const combustible = document.getElementById("mCombustible").value;
    const estado      = document.getElementById("mEstado").value;
    const titulo      = get("mTitulo");
    const descripcion = get("mDescripcion");
    const imagen      = document.getElementById("mImagen").files[0];
    const tipo        = document.getElementById("mTipo").value;
    const precioMin   = get("mPrecioMin");
    const fechaLimite = get("mFechaLimite");

    if (!placa || !marca || !modelo || !anio || !km || !precio) {
        alert("Por favor completa los campos obligatorios.");
        return;
    }
    if (tipo === "SUBASTA" && (!precioMin || !fechaLimite)) {
        alert("Para una subasta debes ingresar precio mínimo y fecha límite.");
        return;
    }

    const formData = new FormData();
    formData.append("placa", placa);
    formData.append("marca", marca);
    formData.append("modelo", modelo);
    formData.append("anio", anio);
    formData.append("kilometraje", km);
    formData.append("color", color);
    formData.append("precio", precio);
    formData.append("combustible", combustible);
    formData.append("estado", estado);
    formData.append("titulo", titulo);
    formData.append("descripcion", descripcion);
    formData.append("tipo", tipo);
    if (imagen) formData.append("imagen", imagen);
    if (tipo === "SUBASTA") {
        formData.append("precioMin", precioMin);
        formData.append("fechaLimite", fechaLimite);
    }

    fetch("/api/publicaciones/crear", { method: "POST", body: formData })
        .then(res => res.text())
        .then(() => {
            alert("Vehículo guardado ✔");
            cerrarModal();
            cargarVehiculos();
            ["mPlaca","mMarca","mModelo","mAnio","mKm","mColor","mPrecio","mTitulo","mDescripcion","mPrecioMin","mFechaLimite"]
                .forEach(id => document.getElementById(id).value = "");
            document.getElementById("mImagen").value = "";
            document.getElementById("mTipo").value = "DIRECTA";
            toggleSubasta();
        })
        .catch(() => alert("Error al guardar vehículo"));
}

cargarVehiculos();
