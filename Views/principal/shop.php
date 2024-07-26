<?php include_once 'Views/template-principal/header.php'; ?>

<!-- Start Content -->
<div class="container py-5">
    <div class="row">

        <div class="col-lg-2">
        </div>

        <div class="col-lg-9">
            <div class="row">
                <?php foreach ($data['productos'] as $producto) { ?>

                    <div class="col-md-4">
                        <div class="card mb-4 product-wap rounded-0">
                            <div class="card rounded-0">
                                <div class="img-container" style="width: 100%; height: 0; padding-bottom: 75%; position: relative; overflow: hidden;">
                                    <img class="card-img rounded-0 img-fluid" src="<?php echo BASE_URL_IMG . $producto['imagen']; ?>" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; object-fit: cover;">
                                </div>
                                <div class="card-img-overlay rounded-0 product-overlay d-flex align-items-center justify-content-center">
                                    <ul class="list-unstyled">
                                        <li><a class="btn btn-success text-white btnAddDeseo" href="#" prod="<?php echo $producto['id']; ?>"><i class="fas fa-heart"></i></a></li>
                                        <li><a class="btn btn-success text-white mt-2" href="<?php echo BASE_URL . 'principal/shopsingle/' . $producto['id']; ?>"><i class="fas fa-eye"></i></a></li>
                                        <li><a class="btn btn-success text-white mt-2 btnAddCarrito" href="#" prod="<?php echo $producto['id']; ?>"><i class="fas fa-cart-plus"></i></a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="card-body">
                                <a href="<?php echo BASE_URL . 'principal/shopsingle/' . $producto['id']; ?>" class="h3 text-decoration-none"> <?php echo $producto['nombre']; ?> </a>
                                <ul class="w-100 list-unstyled d-flex justify-content-between mb-0">
                                    <li class="pt-2">
                                        <span class="product-color-dot color-dot-red float-left rounded-circle ml-1"></span>
                                        <span class="product-color-dot color-dot-blue float-left rounded-circle ml-1"></span>
                                        <span class="product-color-dot color-dot-black float-left rounded-circle ml-1"></span>
                                        <span class="product-color-dot color-dot-light float-left rounded-circle ml-1"></span>
                                        <span class="product-color-dot color-dot-green float-left rounded-circle ml-1"></span>
                                    </li>
                                </ul>
                                <p class="text-center mb-0">$<?php echo MONEDA . ' ' . $producto['precio']; ?></p>
                            </div>
                        </div>
                    </div>


                <?php } ?>


            </div>
            <div div="row">
                <ul class="pagination pagination-lg justify-content-end">
                    <?php
                    $anterior = $data['pagina'] - 1;
                    $siguiente = $data['pagina'] + 1;
                    $url = BASE_URL . 'principal/shop/';
                    if ($data['pagina'] > 1) {
                        echo '<li class="page-item">
                        <a class="page-link active rounded-0 mr-3 shadow-sm border-top-0 border-left-0" href=" ' . $url . $anterior . ' ">Anterior</a>
                    </li>';
                    }
                    if ($data['total'] >= $siguiente) {
                        echo '<li class="page-item">
                        <a class="page-link active rounded-0 mr-3 shadow-sm border-top-0 border-left-0 text-white" href=" ' . $url . $siguiente . ' ">Siguiente</a>
                    </li>';
                    }

                    ?>

                </ul>
            </div>
        </div>

    </div>
</div>
<!-- End Content -->



<?php include_once 'Views/template-principal/footer.php'; ?>

<script>
    // Verificar si hay un parámetro en la URL que indica que la compra fue exitosa
    const urlParams = new URLSearchParams(window.location.search);
    const compraExitosa = urlParams.get('compra_exitosa');

    // Mostrar la alerta si la compra fue exitosa
    if (compraExitosa === 'true') {
        Swal.fire(
            'Pago Realizado con Éxito',
            'Gracias por Comprar con Nosotros!',
            'success'
        );
    }
</script>

</body>

</html>