<!DOCTYPE html>
<html lang="es">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Formulario de Pago</title>
  <link rel="stylesheet" href="<?php echo BASE_URL . 'assets/css/bootstrap.min.css'; ?>">
  <link rel="stylesheet" href="<?php echo BASE_URL . 'assets/css/compra.css'; ?>">

  <link rel="apple-touch-icon" sizes="57x57" href="<?php echo BASE_URL; ?>assets/favicon/apple-icon-57x57.png">
  <link rel="apple-touch-icon" sizes="60x60" href="<?php echo BASE_URL; ?>assets/favicon/apple-icon-60x60.png">
  <link rel="apple-touch-icon" sizes="72x72" href="<?php echo BASE_URL; ?>assets/favicon/apple-icon-72x72.png">
  <link rel="apple-touch-icon" sizes="76x76" href="<?php echo BASE_URL; ?>assets/favicon/apple-icon-76x76.png">
  <link rel="apple-touch-icon" sizes="114x114" href="<?php echo BASE_URL; ?>assets/favicon/apple-icon-114x114.png">
  <link rel="apple-touch-icon" sizes="120x120" href="<?php echo BASE_URL; ?>assets/favicon/apple-icon-120x120.png">
  <link rel="apple-touch-icon" sizes="144x144" href="<?php echo BASE_URL; ?>assets/favicon/apple-icon-144x144.png">
  <link rel="apple-touch-icon" sizes="152x152" href="<?php echo BASE_URL; ?>assets/favicon/apple-icon-152x152.png">
  <link rel="apple-touch-icon" sizes="180x180" href="<?php echo BASE_URL; ?>assets/favicon/apple-icon-180x180.png">
  <link rel="icon" type="image/png" sizes="192x192" href="<?php echo BASE_URL; ?>assets/favicon/android-icon-192x192.png">
  <link rel="icon" type="image/png" sizes="32x32" href="<?php echo BASE_URL; ?>assets/favicon/favicon-32x32.png">
  <link rel="icon" type="image/png" sizes="96x96" href="<?php echo BASE_URL; ?>assets/favicon/favicon-96x96.png">
  <link rel="icon" type="image/png" sizes="16x16" href="<?php echo BASE_URL; ?>assets/favicon/favicon-16x16.png">
  <link rel="manifest" href="<?php echo BASE_URL; ?>assets/favicon/manifest.json">
  <meta name="msapplication-TileColor" content="#ffffff">
  <meta name="msapplication-TileImage" content="<?php echo BASE_URL; ?>assets/favicon/ms-icon-144x144.png">
  <meta name="theme-color" content="#ffffff">
</head>

<body>
  <div class="container">
    <h2>Formulario de Pago con Tarjeta</h2>
    <form id="payment-form" action="<?php echo BASE_URL . 'principal/finalizarCompra'; ?>" method="post">

      <label for="Usuario">Usuario:</label>
      <input type="email" class="form-control" id="usuario" name="usuario" required readonly value="<?php echo isset($_SESSION['usuario']) ? htmlspecialchars($_SESSION['usuario']) : ''; ?>">

      <label for="card-number">Número de Tarjeta:</label>
      <input type="text" id="card-number" name="card-number" maxlength="19" placeholder="XXXX-XXXX-XXXX-XXXX" required><br><br>

      <label for="card-expiry-month">Mes de Expiración:</label>
      <select id="card-expiry-month" name="card-expiry-month" required>
        <option value="">Seleccionar Mes</option>
        <option value="01">01 - Enero</option>
        <option value="02">02 - Febrero</option>
        <option value="03">03 - Marzo</option>
        <option value="04">04 - Abril</option>
        <option value="05">05 - Mayo</option>
        <option value="06">06 - Junio</option>
        <option value="07">07 - Julio</option>
        <option value="08">08 - Agosto</option>
        <option value="09">09 - Septiembre</option>
        <option value="10">10 - Octubre</option>
        <option value="11">11 - Noviembre</option>
        <option value="12">12 - Diciembre</option>
      </select><br><br>

      <label for="card-expiry-year">Año de Expiración:</label>
      <select id="card-expiry-year" name="card-expiry-year" required>
        <option value="">Seleccionar Año</option>
        <option value="2024">2024</option>
        <option value="2025">2025</option>
        <option value="2026">2026</option>
        <option value="2027">2027</option>
        <option value="2028">2028</option>
        <option value="2029">2029</option>
        <option value="2030">2030</option>
        <option value="2031">2031</option>
        <option value="2032">2032</option>
        <option value="2033">2033</option>
        <option value="2034">2034</option>
        <option value="2035">2035</option>
        <option value="2036">2036</option>
        <option value="2037">2037</option>
        <option value="2038">2038</option>
      </select><br><br>

      <label for="card-cvc">CVC:</label>
      <input type="password" id="card-cvc" name="card-cvc" maxlength="3" placeholder="XXX" required><br><br>

      <div style="text-align: center;">
      <button type="submit" class="btn btn-success">Realizar Compra</button>
      <a class="btn btn-success" href="<?php echo BASE_URL . 'principal/Cancelar'; ?>">Cancelar</a>
      </div>
    </form>
  </div>

  <script>
    var BASE_URL = '<?php echo BASE_URL; ?>';
  </script>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script>
    $(document).ready(function() {
        $('#payment-form').on('submit', function(event) {
            event.preventDefault();
            $.ajax({
                url: '<?php echo BASE_URL ?>principal/finalizarCompra',
                method: 'POST',
                data: $(this).serialize(),
                success: function(response) {
                    try {
                        response = JSON.parse(response); // Asegurarse de que la respuesta es un objeto JSON
                        console.log(response); // Para depuración
                        if (response.success) {
                            alert(response.message1);
                            
                        } else {
                            $('#error-message').text(response.message1).show();
                            $('html, body').animate({ scrollTop: 0 }, 'fast');
                        }
                    } catch (e) {
                        console.error("Error parsing JSON response: ", e);
                        $('#error-message').text('Datos del cliente no recuperado').show();
                    }
                },
                error: function() {
                    $('#error-message').text('Datos del cliente no recuperado').show();
                    $('html, body').animate({ scrollTop: 0 }, 'fast');
                }
            });
        });
    });
  </script>


  <script src="<?php echo BASE_URL; ?>assets/js/bootstrap.bundle.min.js"></script>
  <script src="<?php echo BASE_URL; ?>assets/js/sweetalert2.all.min.js"></script>
  <script src="<?php echo BASE_URL; ?>assets/js/compra.js"></script>

</body>

</html>