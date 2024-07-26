const btnAddCard = document.querySelector('#btnAddCard');
const Cantidad = document.querySelector('#product-quanity');
const idProducto = document.querySelector('#idProducto');

document.addEventListener('DOMContentLoaded', function() {
    btnAddCard.addEventListener('click', function () {
        agregarCarrito(idProducto.value, Cantidad.value);


    });

});

