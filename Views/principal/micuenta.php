<?php include_once 'Views/template-principal/header.php'; ?>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <h2 class="text-center">Mi Cuenta</h2>
            <form action="<?php echo BASE_URL; ?>principal/actualizarCuenta" method="post">
                <div class="form-group mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" value="<?php echo $data['usuario']['nombre']; ?>" required>
                </div>
                <div class="form-group mb-3">
                    <label for="direccion" class="form-label">Dirección</label>
                    <textarea class="form-control" id="direccion" name="direccion" required><?php echo $data['usuario']['direccion']; ?></textarea>
                </div>
                <div class="form-group mb-3">
                    <label for="telefono" class="form-label">Teléfono</label>
                    <input type="text" class="form-control" id="telefono" name="telefono" value="<?php echo $data['usuario']['telefono']; ?>" required>
                </div>
                <div class="form-group mb-3">
                    <label for="municipio" class="form-label">Municipio</label>
                    <select class="form-control" id="municipio" name="municipio" required>
                        <option value="">Selecciona Municipio</option>
                        <?php 
                        $municipios = ["Abalá", "Acanceh", "Akil", "Baca", "Bokobá", "Buctzotz", "Cacalchén", "Calotmul", "Cansahcab", "Cantamayec", "Celestún", "Cenotillo", "Chacsinkín", "Chankom", "Chapab", "Chemax", "Chicxulub Pueblo", "Chichimilá", "Chikindzonot", "Chocholá", "Chumayel", "Conkal", "Cuncunul", "Cuzamá", "Dzán", "Dzemul", "Dzidzantún", "Dzilam de Bravo", "Dzilam González", "Dzitás", "Dzoncauich", "Espita", "Halachó", "Hocabá", "Hoctún", "Homún", "Huhí", "Hunucmá", "Ixil", "Izamal", "Kanasín", "Kantunil", "Kaua", "Kinchil", "Kopomá", "Mama", "Maní", "Maxcanú", "Mayapán", "Mérida", "Mocochá", "Motul", "Muna", "Muxupip", "Opichén", "Oxkutzcab", "Panabá", "Peto", "Progreso", "Quintana Roo", "Río Lagartos", "Sacalum", "Samahil", "Sanahcat", "Santa Elena", "Seyé", "Sinanché", "Sotuta", "Suma", "Tahdziú", "Tahmek", "Teabo", "Tecoh", "Tekal de Venegas", "Tekantó", "Tekax", "Tekit", "Tekom", "Temozón", "Tepakán", "Tetiz", "Teya", "Ticul", "Timucuy", "Tinum", "Tixcacalcupul", "Tixkokob", "Tixmehuac", "Tixpéhual", "Tizimín", "Tunkás", "Tzucacab", "Uayma", "Ucú", "Umán", "Valladolid", "Xocchel", "Yaxcabá", "Yaxkukul", "Yobaín"];
                        foreach ($municipios as $municipio) {
                            echo "<option value=\"$municipio\" " . ($data['usuario']['municipio'] == $municipio ? 'selected' : '') . ">$municipio</option>";
                        }
                        ?>
                    </select>
                </div>
                <div class="form-group mb-3">
                    <label for="estado_municipio" class="form-label">Estado</label>
                    <input type="text" class="form-control" id="estado_municipio" name="estado_municipio" value="Yucatán" readonly>
                </div>
                <div class="form-group mb-3">
                    <label for="correo" class="form-label">Correo</label>
                    <input type="email" class="form-control" id="correo" name="correo" value="<?php echo $data['usuario']['correo']; ?>" required>
                </div>
                <div class="form-group mb-3">
                    <label for="genero" class="form-label">Género</label>
                    <select class="form-control" id="genero" name="genero" required>
                        <option value="">Selecciona Género</option>
                        <option value="Masculino" <?php echo ($data['usuario']['genero'] == 'Masculino' ? 'selected' : ''); ?>>Masculino</option>
                        <option value="Femenino" <?php echo ($data['usuario']['genero'] == 'Femenino' ? 'selected' : ''); ?>>Femenino</option>
                    </select>
                </div>
                <div class="form-group mb-3">
                    <label for="usuario" class="form-label">Usuario</label>
                    <input type="text" class="form-control" id="usuario" name="usuario" value="<?php echo $data['usuario']['usuario']; ?>" required>
                </div>
                <div class="form-group mb-3 password-wrapper">
                    <label for="password" class="form-label">Contraseña</label>
                    <div class="input-group">
                        <input type="password" class="form-control" id="password" name="contraseña" value="<?php echo $data['usuario']['contraseña']; ?>" required>
                        <button type="button" class="btn btn-outline-secondary" onclick="togglePassword('password')">Mostrar</button>
                    </div>
                </div>
                <div class="form-group mb-3 password-wrapper">
                    <label for="password_confirm" class="form-label">Verifica Contraseña</label>
                    <div class="input-group">
                        <input type="password" class="form-control" id="password_confirm" name="password_confirm" value="<?php echo $data['usuario']['contraseña']; ?>" required>
                        <button type="button" class="btn btn-outline-secondary" onclick="togglePassword('password_confirm')">Mostrar</button>
                    </div>
                </div>
                <div class="form-group mb-3">
                    <label>Selecciona tu Interés</label>
                    <?php 
                    $intereses = isset($data['usuario']['preferencias']) ? explode(' ', $data['usuario']['preferencias']) : [];
                    $opciones = ["Pasteles", "Tartas", "CupCakes", "Frutas", "Tres Leches", "Galletas", "Fondan", "Merengue", "Chantilly"];
                    foreach ($opciones as $opcion) {
                        $checked = in_array($opcion, $intereses) ? 'checked' : '';
                        echo "<div class='form-check'>
                                <input class='form-check-input' type='checkbox' name='preferencias[]' value='$opcion' $checked>
                                <label class='form-check-label'>$opcion</label>
                              </div>";
                    }
                    ?>
                </div>
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Actualizar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function togglePassword(fieldId) {
        const field = document.getElementById(fieldId);
        field.type = (field.type === "password") ? "text" : "password";
    }

    $(document).ready(function() {
        $('#register-form').on('submit', function(event) {
            event.preventDefault();

            const password = $('#password').val();
            const passwordConfirm = $('#password_confirm').val();

            if (password !== passwordConfirm) {
                alert('Las contraseñas no coinciden.');
                return;
            }

            $.ajax({
                url: '<?php echo BASE_URL ?>principal/actualizarCuenta',
                method: 'POST',
                data: $(this).serialize(),
                success: function(response) {
                    var res = JSON.parse(response);
                    if (res.success) {
                        alert("¡Actualización Exitosa!");
                        window.location.href = '<?php echo BASE_URL ?>principal/micuenta';
                    } else {
                        alert(res.message);
                    }
                },
                error: function() {
                    alert('Error al actualizar la cuenta.');
                }
            });
        });
    });
</script>

<?php include_once 'Views/template-principal/footer.php'; ?>
</body>
</html>
