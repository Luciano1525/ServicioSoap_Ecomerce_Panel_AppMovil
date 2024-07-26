const btnAddDeseo = document.querySelectorAll('.btnAddDeseo');
const btnAddCarrito = document.querySelectorAll('.btnAddCarrito');
const btnDeseo = document.querySelector('#btnCantidadDeseo');
const btnCarrito = document.querySelector('#btnCantidadCarrito');
const verCarrito = document.querySelector('#verCarrito');
const tableListaCarrito = document.querySelector('#tableListaCarrito tbody');
let listaDeseo, listaCarrito;

document.addEventListener('DOMContentLoaded', function() {
    if (localStorage.getItem('listaDeseo') != null) {
        listaDeseo = JSON.parse(localStorage.getItem('listaDeseo'));
        
    }
    if (localStorage.getItem('listaCarrito') != null) {
        listaCarrito = JSON.parse(localStorage.getItem('listaCarrito'));
        
    }

    for (let i = 0; i < btnAddDeseo.length; i++) {
        btnAddDeseo[i].addEventListener('click', function(){
            let idProducto = btnAddDeseo[i].getAttribute('prod');
            agregarDeseo(idProducto);

        });
        
    }

    for (let p = 0; p < btnAddCarrito.length; p++) {
        btnAddCarrito[p].addEventListener('click', function(){
            let idProducto = btnAddCarrito[p].getAttribute('prod');
            agregarCarrito(idProducto, 1);  

        });
        
    }
    cantidadDeseo();
    cantidadCarrito();

    //Ver Carrito
    const myModal = new bootstrap.Modal(document.getElementById('myModal'));
    verCarrito.addEventListener('click', function () {
        getListaCarrito();
        myModal.show();
    });
    
});

function agregarDeseo(idProducto) {
    if (localStorage.getItem('listaDeseo') == null) {
        listaDeseo = [];
        
    } else {
        let listaExiste = JSON.parse(localStorage.getItem('listaDeseo')) || [];
    
        for (let i = 0; i < listaExiste.length; i++) {
            if (listaExiste[i]['idProducto'] === idProducto) {
                Swal.fire(
                    'Aviso',
                    'El Producto Ya Está En Tu Lista De Deseos',
                    'warning'
                );
                return;
            }
        }
        listaDeseo.concat(localStorage.getItem('listaDeseo'));
        
    }
    listaDeseo.push({
        "idProducto" : idProducto,
        "cantidad" : 1
    })
    localStorage.setItem('listaDeseo', JSON.stringify(listaDeseo));
    Swal.fire(
        'Aviso',
        'Producto Agregado Con Exito!',
        'success'
    );

    cantidadDeseo();
    
}

function cantidadDeseo() {
    let listas = JSON.parse(localStorage.getItem('listaDeseo'));
    if (listas != null) {
        btnDeseo.textContent = listas.length;
        
    } else {
        btnDeseo.textContent = 0;
        
    }
        
}



function agregarCarrito(idProducto, Cantidad, Accion = false) {
    if (localStorage.getItem('listaCarrito') == null) {
        listaCarrito = [];
        
    } else {
        let listaExiste = JSON.parse(localStorage.getItem('listaCarrito')) || [];
    
        for (let i = 0; i < listaExiste.length; i++) {
            if (Accion) {
                eliminarListaDeseo(idProducto);
            }
            if (listaExiste[i]['idProducto'] === idProducto) {
                Swal.fire(
                    'Aviso',
                    'El Producto Ya Está Agregado',
                    'warning'
                );
                return;
            }
        }
        listaCarrito.concat(localStorage.getItem('listaCarrito'));
        
    }
    listaCarrito.push({
        "idProducto" : idProducto,
        "cantidad" : Cantidad
    })
    localStorage.setItem('listaCarrito', JSON.stringify(listaCarrito));
    Swal.fire(
        'Aviso',
        'Producto Añadido con exito!',
        'success'
    );

    cantidadCarrito();
    
}

function cantidadCarrito() {
    let listas = JSON.parse(localStorage.getItem('listaCarrito'));
    if (listas != null) {
        btnCarrito.textContent = listas.length;
        
    } else {
        btnCarrito.textContent = 0;
        
    }
        
}


//Mostrar Carrito
function getListaCarrito() {
    const url = base_url + 'principal/listaCarrito';
    const http = new XMLHttpRequest();
    http.open('POST', url, true);
    http.send(JSON.stringify(listaCarrito));
    http.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            const res = JSON.parse(this.responseText);
            let html = '';
            res.productos.forEach(producto => {
                html += `<tr>
                    <td>
                    <img class="img-thumbnail rounded-circle" src="${res.base_url_img + producto.imagen}" alt="" width="120">
                    </td>
                    <td>${producto.nombre}</td>
                    <td><span class="badge bg-warning">${res.moneda + ' ' + producto.precio}</span></td>
                    <td><span class="badge bg-primary">${producto.cantidad}</span></td>
                    <td><span class="badge bg-success">${res.moneda + ' ' + producto.subTotal}</span></td> 
                    <td><button class="btn btn-danger btnEliminarCarrito" type="button" prod="${producto.id}"><i class="fas fa-times-circle"></i></button></td> 
                </tr>`;
            });
            tableListaCarrito.innerHTML = html;
            document.querySelector('#totalGnerado').textContent = 'Total de la Compra: $MXN ' + res.total;
            btnEliminarCarrito();
        }
    }
}

function btnEliminarCarrito() {
    let listaEliminar = document.querySelectorAll('.btnEliminarCarrito');
        for (let l = 0; l < listaEliminar.length; l++) {
            listaEliminar[l].addEventListener('click', function(){
                let idProducto = listaEliminar[l].getAttribute('prod');
                eliminarListaCarrito(idProducto);
            });
        }
    
}


function eliminarListaCarrito(idProducto) {
    for (let c = 0; c < listaCarrito.length; c++) {
        if (listaCarrito[c]['idProducto'] == idProducto) {
            listaCarrito.splice(c, 1);
        }
        
    }
    localStorage.setItem('listaCarrito', JSON.stringify(listaCarrito));
    cantidadCarrito();
    getListaCarrito();
    Swal.fire(
        'Aviso',
        'Producto Eliminado del Carrito',
        'success'
    );
}



function limpiarCarrito() {

  localStorage.removeItem('listaCarrito');

  document.getElementById('btnCarrito').textContent = 0;

}
