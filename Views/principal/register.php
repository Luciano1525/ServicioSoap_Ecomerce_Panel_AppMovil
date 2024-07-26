<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro</title>
    <link rel="stylesheet" href="<?php echo BASE_URL . 'assets/css/bootstrap.min.css'; ?>">
    <link rel="stylesheet" href="<?php echo BASE_URL . 'assets/css/register.css'; ?>">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body>

    <div class="container d-flex justify-content-center align-items-center min-vh-100">
        <div class="row justify-content-center w-100">
            <div class="col-lg-6 col-md-8 col-sm-10">
                <div class="card">
                    <div class="card-header text-center">
                        <h2 class="h2 text-success">Registro</h2>
                    </div>
                    <div class="card-body">
                        <div id="error-message" class="alert alert-danger" style="display: none;"></div>
                        <form id="register-form">
                            <div class="form-group mb-3">
                                <label for="nombre_completo" class="form-label">Nombre completo</label>
                                <input type="text" class="form-control" id="nombre_completo" name="nombre_completo" required>
                            </div>
                            <div class="form-group mb-3">
                                <label for="direccion" class="form-label">Dirección</label>
                                <input type="text" class="form-control" id="direccion" name="direccion" required>
                            </div>
                            <div class="form-group mb-3">
                                <label for="telefono" class="form-label">Teléfono</label>
                                <input type="text" class="form-control" id="telefono" name="telefono" required>
                            </div>
                            <div class="form-group mb-3">
                                <label for="municipio" class="form-label">Municipio</label>
                                <select class="form-control" id="municipio" name="municipio" required>
                                    <option value="">Selecciona Municipio</option>
                                    <option value="Abalá">Abalá</option>
                                    <option value="Acanceh">Acanceh</option>
                                    <option value="Akil">Akil</option>
                                    <option value="Baca">Baca</option>
                                    <option value="Bokobá">Bokobá</option>
                                    <option value="Buctzotz">Buctzotz</option>
                                    <option value="Cacalchén">Cacalchén</option>
                                    <option value="Calotmul">Calotmul</option>
                                    <option value="Cansahcab">Cansahcab</option>
                                    <option value="Cantamayec">Cantamayec</option>
                                    <option value="Celestún">Celestún</option>
                                    <option value="Cenotillo">Cenotillo</option>
                                    <option value="Chacsinkín">Chacsinkín</option>
                                    <option value="Chankom">Chankom</option>
                                    <option value="Chapab">Chapab</option>
                                    <option value="Chemax">Chemax</option>
                                    <option value="Chicxulub Pueblo">Chicxulub Pueblo</option>
                                    <option value="Chichimilá">Chichimilá</option>
                                    <option value="Chikindzonot">Chikindzonot</option>
                                    <option value="Chocholá">Chocholá</option>
                                    <option value="Chumayel">Chumayel</option>
                                    <option value="Conkal">Conkal</option>
                                    <option value="Cuncunul">Cuncunul</option>
                                    <option value="Cuzamá">Cuzamá</option>
                                    <option value="Dzán">Dzán</option>
                                    <option value="Dzemul">Dzemul</option>
                                    <option value="Dzidzantún">Dzidzantún</option>
                                    <option value="Dzilam de Bravo">Dzilam de Bravo</option>
                                    <option value="Dzilam González">Dzilam González</option>
                                    <option value="Dzitás">Dzitás</option>
                                    <option value="Dzoncauich">Dzoncauich</option>
                                    <option value="Espita">Espita</option>
                                    <option value="Halachó">Halachó</option>
                                    <option value="Hocabá">Hocabá</option>
                                    <option value="Hoctún">Hoctún</option>
                                    <option value="Homún">Homún</option>
                                    <option value="Huhí">Huhí</option>
                                    <option value="Hunucmá">Hunucmá</option>
                                    <option value="Ixil">Ixil</option>
                                    <option value="Izamal">Izamal</option>
                                    <option value="Kanasín">Kanasín</option>
                                    <option value="Kantunil">Kantunil</option>
                                    <option value="Kaua">Kaua</option>
                                    <option value="Kinchil">Kinchil</option>
                                    <option value="Kopomá">Kopomá</option>
                                    <option value="Mama">Mama</option>
                                    <option value="Maní">Maní</option>
                                    <option value="Maxcanú">Maxcanú</option>
                                    <option value="Mayapán">Mayapán</option>
                                    <option value="Mérida">Mérida</option>
                                    <option value="Mocochá">Mocochá</option>
                                    <option value="Motul">Motul</option>
                                    <option value="Muna">Muna</option>
                                    <option value="Muxupip">Muxupip</option>
                                    <option value="Opichén">Opichén</option>
                                    <option value="Oxkutzcab">Oxkutzcab</option>
                                    <option value="Panabá">Panabá</option>
                                    <option value="Peto">Peto</option>
                                    <option value="Progreso">Progreso</option>
                                    <option value="Quintana Roo">Quintana Roo</option>
                                    <option value="Río Lagartos">Río Lagartos</option>
                                    <option value="Sacalum">Sacalum</option>
                                    <option value="Samahil">Samahil</option>
                                    <option value="Sanahcat">Sanahcat</option>
                                    <option value="Santa Elena">Santa Elena</option>
                                    <option value="Seyé">Seyé</option>
                                    <option value="Sinanché">Sinanché</option>
                                    <option value="Sotuta">Sotuta</option>
                                    <option value="Suma">Suma</option>
                                    <option value="Tahdziú">Tahdziú</option>
                                    <option value="Tahmek">Tahmek</option>
                                    <option value="Teabo">Teabo</option>
                                    <option value="Tecoh">Tecoh</option>
                                    <option value="Tekal de Venegas">Tekal de Venegas</option>
                                    <option value="Tekantó">Tekantó</option>
                                    <option value="Tekax">Tekax</option>
                                    <option value="Tekit">Tekit</option>
                                    <option value="Tekom">Tekom</option>
                                    <option value="Temozón">Temozón</option>
                                    <option value="Tepakán">Tepakán</option>
                                    <option value="Tetiz">Tetiz</option>
                                    <option value="Teya">Teya</option>
                                    <option value="Ticul">Ticul</option>
                                    <option value="Timucuy">Timucuy</option>
                                    <option value="Tinum">Tinum</option>
                                    <option value="Tixcacalcupul">Tixcacalcupul</option>
                                    <option value="Tixkokob">Tixkokob</option>
                                    <option value="Tixmehuac">Tixmehuac</option>
                                    <option value="Tixpéhual">Tixpéhual</option>
                                    <option value="Tizimín">Tizimín</option>
                                    <option value="Tunkás">Tunkás</option>
                                    <option value="Tzucacab">Tzucacab</option>
                                    <option value="Uayma">Uayma</option>
                                    <option value="Ucú">Ucú</option>
                                    <option value="Umán">Umán</option>
                                    <option value="Valladolid">Valladolid</option>
                                    <option value="Xocchel">Xocchel</option>
                                    <option value="Yaxcabá">Yaxcabá</option>
                                    <option value="Yaxkukul">Yaxkukul</option>
                                    <option value="Yobaín">Yobaín</option>
                                </select>
                            </div>
                            <div class="form-group mb-3">
                                <label for="estado" class="form-label">Estado</label>
                                <input type="text" class="form-control" id="estado" name="estado" value="Yucatán" readonly>
                            </div>
                            <div class="form-group mb-3">
                                <label for="correo" class="form-label">Correo</label>
                                <input type="email" class="form-control" id="correo" name="correo" required>
                            </div>
                            <div class="form-group mb-3">
                                <label for="genero" class="form-label">Género</label>
                                <select class="form-control" id="genero" name="genero" required>
                                    <option value="">Selecciona Género</option>
                                    <option value="Masculino">Masculino</option>
                                    <option value="Femenino">Femenino</option>
                                </select>
                            </div>
                            <div class="form-group mb-3">
                                <label for="usuario" class="form-label">Usuario</label>
                                <input type="text" class="form-control" id="usuario" name="usuario" required>
                            </div>
                            <div class="form-group mb-3 password-wrapper">
                                <label for="password" class="form-label">Contraseña</label>
                                <div class="input-group">
                                    <input type="password" class="form-control" id="password" name="contrasena" required>
                                    <button type="button" class="btn btn-outline-secondary" onclick="togglePassword('password')">Mostrar</button>
                                </div>
                            </div>
                            <div class="form-group mb-3 password-wrapper">
                                <label for="password_confirm" class="form-label">Verifica Contraseña</label>
                                <div class="input-group">
                                    <input type="password" class="form-control" id="password_confirm" name="password_confirm" required>
                                    <button type="button" class="btn btn-outline-secondary" onclick="togglePassword('password_confirm')">Mostrar</button>
                                </div>
                            </div>

                            <div class="form-group mb-3">
                                <label>Selecciona tu Interés</label>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="interes[]" value="Pasteles">
                                    <label class="form-check-label">Pasteles</label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="interes[]" value="Tartas">
                                    <label class="form-check-label">Tartas</label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="interes[]" value="CupCakes">
                                    <label class="form-check-label">CupCakes</label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="interes[]" value="Frutas">
                                    <label class="form-check-label">Frutas</label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="interes[]" value="Tres Leches">
                                    <label class="form-check-label">Tres Leches</label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="interes[]" value="Galletas">
                                    <label class="form-check-label">Galletas</label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="interes[]" value="Fondan">
                                    <label class="form-check-label">Fondan</label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="interes[]" value="Merengue">
                                    <label class="form-check-label">Merengue</label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="interes[]" value="Chantilly">
                                    <label class="form-check-label">Chantilly</label>
                                </div>
                            </div>
                            <div class="form-check mb-3">
                                <input class="form-check-input" type="checkbox" id="terminos" name="terminos" required>
                                <label class="form-check-label" for="terminos">Aceptar términos y condiciones</label>
                            </div>
                            <div class="form-check mb-3">
                                <input class="form-check-input" type="checkbox" id="notificaciones" name="notificaciones">
                                <label class="form-check-label" for="notificaciones">Recibir notificaciones de ofertas</label>
                            </div>
                            <div class="form-group mb-3 text-center">
                                <button type="submit" class="btn btn-success">Registrar</button>
                                <a class="btn btn-success" href="<?php echo BASE_URL . 'Home/index'; ?>">Cancelar</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
    function togglePassword(fieldId) {
        const field = document.getElementById(fieldId);
        if (field.type === "password") {
            field.type = "text";
        } else {
            field.type = "password";
        }
    }

    $(document).ready(function() {
        $('#register-form').on('submit', function(event) {
            event.preventDefault();

            // Validar si las contraseñas coinciden
            const password = $('#password').val();
            const passwordConfirm = $('#password_confirm').val();

            if (password !== passwordConfirm) {
                $('#error-message').text('Las contraseñas no coinciden.').show();
                $('html, body').animate({ scrollTop: 0 }, 'fast');
                return; // Detiene el envío del formulario si las contraseñas no coinciden
            }

            // Si las contraseñas coinciden, enviar el formulario
            $.ajax({
                url: '<?php echo BASE_URL ?>principal/registrar',
                method: 'POST',
                data: $(this).serialize(),
                success: function(response) {
                    if (response.success) {
                        alert(response.message);
                        window.location.href = '<?php echo BASE_URL ?>';
                    } else {
                        $('#error-message').text(response.message).show();
                        $('html, body').animate({ scrollTop: 0 }, 'fast');
                    }
                },
                error: function() {
                    $('#error-message').text('Error al registrar el cliente.').show();
                    $('html, body').animate({ scrollTop: 0 }, 'fast');
                }
            });
        });
    });
</script>


</body>
</html>