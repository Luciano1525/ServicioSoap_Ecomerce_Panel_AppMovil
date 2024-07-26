const tableLista = document.querySelector('#tableListaDeseo tbody');

document.addEventListener('DOMContentLoaded', function() {
    getListaDeseo();

});

function getListaDeseo() {
    const url = base_url + 'principal/listaDeseo';
    const http = new XMLHttpRequest();
    http.open('POST', url, true);
    http.send(JSON.stringify(listaDeseo));
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
                    <td>
                    <button class="btn btn-danger btnEliminarDeseo" type="button" prod="${producto.id}"><i class="fas fa-trash"></i></button>
                    <button class="btn btn-primary btnAddCart" type="button" prod="${producto.id}"><i class="fas fa-cart-plus"></i></button>
                    </td> 
                </tr>`;
            });
            tableLista.innerHTML = html;
            btnEliminarDeseo();
            btnAgregarProducto();
        }
    }
}
    
function btnEliminarDeseo() {
    let listaEliminar = document.querySelectorAll('.btnEliminarDeseo');
        for (let w = 0; w < listaEliminar.length; w++) {
            listaEliminar[w].addEventListener('click', function(){
                let idProducto = listaEliminar[w].getAttribute('prod');
                eliminarListaDeseo(idProducto);
            });
        }
    
}


function eliminarListaDeseo(idProducto) {
    for (let c = 0; c < listaDeseo.length; c++) {
        if (listaDeseo[c]['idProducto'] == idProducto) {
            listaDeseo.splice(c, 1);
        }
        
    }
    localStorage.setItem('listaDeseo', JSON.stringify(listaDeseo));
    cantidadDeseo();
    getListaDeseo();
    Swal.fire(
        'Aviso',
        'Producto Eliminado de tu Lista',
        'success'
    );
}

function btnAgregarProducto() {
    let listaAgregar = document.querySelectorAll('.btnAddCart');
    for (let w = 0; w < listaAgregar.length; w++) {
        listaAgregar[w].addEventListener('click', function(){
            let idProducto = listaAgregar[w].getAttribute('prod');
            agregarCarrito(idProducto, 1, true);
        });
    }

}