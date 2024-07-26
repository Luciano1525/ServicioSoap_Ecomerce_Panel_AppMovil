<?php include_once 'Views/template-principal/header.php'; ?>

<!-- Start Banner Hero -->
<div id="template-mo-zay-hero-carousel" class="carousel slide" data-bs-ride="carousel">
    <ol class="carousel-indicators">
        <li data-bs-target="#template-mo-zay-hero-carousel" data-bs-slide-to="0" class="active"></li>
        <li data-bs-target="#template-mo-zay-hero-carousel" data-bs-slide-to="1"></li>
        <li data-bs-target="#template-mo-zay-hero-carousel" data-bs-slide-to="2"></li>
    </ol>
    <div class="carousel-inner">
        <div class="carousel-item active">
            <div class="container">
                <div class="row p-5">
                    <div class="mx-auto col-md-8 col-lg-6 order-lg-last">
                        <img class="img-fluid fixed-size" src="<?php echo BASE_URL; ?>assets/img/banner_img_01.jpeg" alt="">
                    </div>
                    <div class="col-lg-6 mb-0 d-flex align-items-center">
                        <div class="text-align-left align-self-center">
                            <h1 class="h1 text-success"><b>Pasteles</b></h1>
                            <h3 class="h2">Prueba nuestros más ricos y deliciosos pasteles</h3>
                            <p>
                                ¡Bienvenido a un mundo de delicias! Nuestros pasteles artesanales están hechos con los mejores ingredientes, perfectos para cualquier ocasión. Desde el rico y cremoso pastel de chocolate hasta el fresco y elegante pastel de vainilla y frutos rojos, cada bocado es una explosión de sabor.

                                Sorprende a tus seres queridos con una obra maestra culinaria. Personaliza tu pastel y haz de tu celebración algo inolvidable.

                                ¡Haz tu pedido ahora y disfruta de una experiencia de sabor única y exquisita!
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="carousel-item">
            <div class="container">
                <div class="row p-5">
                    <div class="mx-auto col-md-8 col-lg-6 order-lg-last">
                        <img class="img-fluid fixed-size" src="<?php echo BASE_URL; ?>assets/img/banner_img_02.jpeg" alt="">
                    </div>
                    <div class="col-lg-6 mb-0 d-flex align-items-center">
                        <div class="text-align-left">
                            <h1 class="h1 text-success"><b>CupCakes</b></h1>
                            <h3 class="h2">No te quedes sin ganas y prueba nuestra magia</h3>
                            <p>
                                ¡Bienvenido a la tierra de los cupcakes perfectos! Nuestros cupcakes artesanales están hechos con ingredientes de la más alta calidad y decorados con esmero. Disfruta de sabores clásicos como el rico chocolate, la suave vainilla y la fresca fresa, o atrévete con nuestras creaciones gourmet.

                                Cada cupcake es una pequeña obra de arte, ideal para cualquier celebración o para darte un capricho dulce. Perfectos para compartir y disfrutar en cualquier momento.

                                ¡Haz tu pedido ahora y lleva la felicidad en un bocado!
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="carousel-item">
            <div class="container">
                <div class="row p-5">
                    <div class="mx-auto col-md-8 col-lg-6 order-lg-last">
                        <img class="img-fluid fixed-size" src="<?php echo BASE_URL; ?>assets/img/banner_img_03.jpg" alt="">
                    </div>
                    <div class="col-lg-6 mb-0 d-flex align-items-center">
                        <div class="text-align-left">
                            <h1 class="h1 text-success"><b>Tartas</b></h1>
                            <h3 class="h2">Descubre la magia de nuestras tartas</h3>
                            <p>
                                ¡Descubre el placer en cada bocado con nuestras tartas artesanales! Elaboradas con ingredientes frescos y de alta calidad, nuestras tartas ofrecen una variedad de sabores irresistibles. Desde la clásica tarta de manzana hasta la sofisticada tarta de limón y merengue, cada creación es un festín para tus sentidos.

                                Perfectas para cualquier ocasión, nuestras tartas no solo son deliciosas, sino también una verdadera obra de arte que encantará a todos tus invitados.

                                ¡Haz tu pedido ahora y convierte cada momento en una celebración!
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <a class="carousel-control-prev text-decoration-none w-auto ps-3" href="#template-mo-zay-hero-carousel" role="button" data-bs-slide="prev">
        <i class="fas fa-chevron-left"></i>
    </a>
    <a class="carousel-control-next text-decoration-none w-auto pe-3" href="#template-mo-zay-hero-carousel" role="button" data-bs-slide="next">
        <i class="fas fa-chevron-right"></i>
    </a>
</div>
<!-- End Banner Hero -->

<style>
    .fixed-size {
        width: 100%;
        height: 400px; /* Ajusta esta altura según tus necesidades */
        object-fit: cover;
    }
</style>



<!-- Start Categories of The Month -->
<section class="container py-5">
    <div class="row text-center pt-3">
        <div class="col-lg-6 m-auto">
            <h1 class="h1">Categorias</h1>
            <p>
                Escoge nuestra gran variedad de nuestras categorias para tu gusto.
            </p>
        </div>
    </div>
    <div class="row">

        <?php foreach ($data['categorias'] as $categoria) {   ?>

            <div class="col-12 col-md-2 p-5 mt-3">
                <a href="<?php echo BASE_URL . 'principal/categorias/' . $categoria['id']; ?>"><img src="<?php echo BASE_URL_IMG . $categoria['imagen']; ?>" class="rounded-circle img-fluid border"></a>
                <h5 class="text-center mt-3 mb-3"> <?php echo $categoria['categoria']; ?> </h5>

            </div>

        <?php } ?>

    </div>
</section>
<!-- End Categories of The Month -->

<div id="cookie-banner" class="cookie-banner">
    <p>Este sitio web utiliza cookies para mejorar la experiencia del usuario. Al continuar utilizando este sitio, aceptas el uso de cookies.</p>
    <button id="accept-cookies-btn">Aceptar cookies</button>
    <button id="cancel-cookies-btn">Cancelar</button>
</div>

<script src="<?php echo BASE_URL; ?>assets/js/cookie.js"></script>
<?php include_once 'Views/template-principal/footer.php'; ?>

</body>

</html>