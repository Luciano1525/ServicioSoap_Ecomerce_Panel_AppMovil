<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cambiar Contraseña</title>
    <link rel="stylesheet" href="<?php echo BASE_URL . 'assets/css/bootstrap.min.css'; ?>">
    <link rel="stylesheet" href="<?php echo BASE_URL . 'assets/css/style.css'; ?>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@10/sweetalert2.min.css">
</head>
<body>
<!-- Formulario para cambiar contraseña -->
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card mt-5">
                <div class="card-header">
                    <h5 class="card-title">Cambiar Contraseña</h5>
                </div>
                <div class="card-body">
                    <form action="<?php echo BASE_URL; ?>principal/actualizarContraseña" method="POST" id="changePasswordForm">
                        <div class="form-group mb-3">
                            <label for="correo" class="form-label">Tu Correo</label>
                            <input type="email" class="form-control" id="correo" name="correo" required readonly value="<?php echo isset($_GET['correo']) ? htmlspecialchars($_GET['correo']) : ''; ?>">
                        </div>
                        <div class="form-group mb-3">
                            <label for="nuevaContraseña" class="form-label">Nueva Contraseña</label>
                            <div class="input-group">
                                <input type="password" class="form-control" id="nuevaContraseña" name="nuevaContraseña" required>
                                <button type="button" class="btn btn-outline-secondary" onclick="togglePassword('nuevaContraseña')">Mostrar</button>
                            </div>
                        </div>
                        <div class="form-group mb-3">
                            <label for="confirmarContraseña" class="form-label">Confirmar Nueva Contraseña</label>
                            <div class="input-group">
                                <input type="password" class="form-control" id="confirmarContraseña" name="confirmarContraseña" required>
                                <button type="button" class="btn btn-outline-secondary" onclick="togglePassword('confirmarContraseña')">Mostrar</button>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-success">Cambiar Contraseña</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="<?php echo BASE_URL; ?>assets/js/sweetalert2.all.min.js"></script>
<script>
    var BASE_URL = '<?php echo BASE_URL; ?>';
</script>
<script src="<?php echo BASE_URL . 'assets/js/recuperarClave.js'; ?>"></script>
<script src="<?php echo BASE_URL . 'assets/js/bootstrap.bundle.min.js'; ?>"></script>

<script>
function togglePassword(fieldId) {
    const passwordField = document.getElementById(fieldId);
    const passwordToggleBtn = passwordField.nextElementSibling;

    if (passwordField.type === 'password') {
        passwordField.type = 'text';
        passwordToggleBtn.textContent = 'Ocultar';
    } else {
        passwordField.type = 'password';
        passwordToggleBtn.textContent = 'Mostrar';
    }
}

// Validación del formulario
document.getElementById('changePasswordForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const nuevaContraseña = document.getElementById('nuevaContraseña').value;
    const confirmarContraseña = document.getElementById('confirmarContraseña').value;

    if (nuevaContraseña !== confirmarContraseña) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Las contraseñas no coinciden',
        });
    } else {
        Swal.fire({
            icon: 'success',
            title: 'Éxito',
            text: 'Contraseña cambiada con éxito',
        }).then((result) => {
            if (result.isConfirmed) {
                document.getElementById('changePasswordForm').submit();
            }
        });
    }
});
</script>

</body>
</html>
