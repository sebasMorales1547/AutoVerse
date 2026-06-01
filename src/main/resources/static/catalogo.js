let vehiculos = [];

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
        "Disponible": "#22c55e",
        "Vendido":    "#ef4444",
        "Reservado":  "#f59e0b"
    };
    const color = colores[estado] || "#888";
    return `<span class="badge" style="background:${color}22; color:${color}; border:1px solid ${color}55">${estado}</span>`;
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
                        ${(v.km || v.kilometraje || 0).toLocaleString()} km
                    </span>
                    <span class="spec">
                        <svg viewBox="0 0 24 24" fill="none"><rect x="6" y="3" width="9" height="15" rx="2" stroke="currentColor" stroke-width="1.5"/><path d="M15 7h2a2 2 0 010 4h-2" stroke="currentColor" stroke-width="1.5"/><path d="M9 18v2M12 18v2" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"/></svg>
                        ${v.combustible || ''}
                    </span>
                    <span class="spec">
                        <svg viewBox="0 0 24 24" fill="none"><rect x="3" y="6" width="18" height="13" rx="2" stroke="currentColor" stroke-width="1.5"/><path d="M8 6V4M16 6V4" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"/><path d="M3 10h18" stroke="currentColor" stroke-width="1.5"/></svg>
                        ${v.anio || v.año || ''}
                    </span>
                </div>
                <button class="btn-detalles" onclick="verDetalle(${v.idPublicacion})">Ver detalles →</button>
            </div>
        `;
        grid.appendChild(card);
    });
}

function verDetalle(id) {
    const v = vehiculos.find(x => x.idPublicacion === id);
    if (!v) return;

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
                <div class="dspec"><span class="dspec-label">Año</span><span class="dspec-valor">${v.anio || v.año || ''}</span></div>
                <div class="dspec"><span class="dspec-label">Kilometraje</span><span class="dspec-valor">${(v.km || v.kilometraje || 0).toLocaleString()} km</span></div>
                <div class="dspec"><span class="dspec-label">Color</span><span class="dspec-valor">${v.color || ''}</span></div>
                <div class="dspec"><span class="dspec-label">Combustible</span><span class="dspec-valor">${v.combustible || ''}</span></div>
                <div class="dspec"><span class="dspec-label">Estado</span><span class="dspec-valor">${v.estado}</span></div>
            </div>
        </div>
    `;

    document.getElementById("detalleOverlay").classList.add("active");
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
        const matchAnio  = !anio  || (v.anio || v.año) === Number(anio);
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

function abrirModal() {
    document.getElementById("modalOverlay").classList.add("active");
}

function cerrarModal(e) {
    if (!e || e.target === document.getElementById("modalOverlay")) {
        document.getElementById("modalOverlay").classList.remove("active");
    }
}

function toggleSubasta() {
    const tipo = document.getElementById("mTipo").value;
    const campos = document.getElementById("camposSubasta");
    campos.style.display = tipo === "SUBASTA" ? "grid" : "none";
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
    formData.append("placa",       placa);
    formData.append("marca",       marca);
    formData.append("modelo",      modelo);
    formData.append("anio",        anio);
    formData.append("kilometraje", km);
    formData.append("color",       color);
    formData.append("precio",      precio);
    formData.append("combustible", combustible);
    formData.append("estado",      estado);
    formData.append("titulo",      titulo);
    formData.append("descripcion", descripcion);
    formData.append("tipo",        tipo);
    if (imagen) formData.append("imagen", imagen);
    if (tipo === "SUBASTA") {
        formData.append("precioMin",   precioMin);
        formData.append("fechaLimite", fechaLimite);
    }

    fetch("/api/publicaciones/crear", {
        method: "POST",
        body: formData
    })
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
    .catch(err => {
        console.error(err);
        alert("Error al guardar vehículo");
    });
}


cargarVehiculos();