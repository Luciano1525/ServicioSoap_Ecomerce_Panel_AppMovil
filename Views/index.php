<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="<?php echo BASE_URL . 'assets/css/bootstrap.min.css'; ?>">
    <link rel="stylesheet" href="<?php echo BASE_URL . 'assets/css/style.css'; ?>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@10/sweetalert2.min.css">
</head>
<body>
<div class="container d-flex justify-content-center align-items-center min-vh-100">
    <div class="row justify-content-center w-100">
        <div class="col-lg-6 col-md-8 col-sm-10">
            <div class="card">
                <div class="card-header text-center">
                    <h2 class="h2 text-success">Login</h2>
                </div>
                <div class="card-body">
                    <?php if (isset($_GET['msg'])) { ?>
                        <div class="alert alert-danger" role="alert">
                            <strong>Usuario o contraseña Incorrecta</strong>
                        </div>
                    <?php } ?>
                    <form action="<?php echo BASE_URL . 'principal/login'; ?>" method="post" autocomplete="off">
                        <div class="form-group mb-3">
                            <label for="username_email" class="form-label">Nombre de Usuario</label>
                            <input type="text" class="form-control" id="username_email" name="usuario" required>
                        </div>
                        <div class="form-group mb-3">
                            <label for="password" class="form-label">Contraseña</label>
                            <div class="password-wrapper">
                                <input type="password" class="form-control" id="password" name="password" required autocomplete="current-password">
                                <button type="button" onclick="togglePassword()">Mostrar</button>
                            </div>
                        </div>
                        <div class="d-grid">
                            <button type="submit" class="btn btn-success btn-block">Iniciar Sesión</button>
                        </div>
                    </form>
                </div>
                <div class="card-footer text-center">
                    <p><a href="<?php echo BASE_URL . 'principal/inicio'; ?>">Navegar sin Cuenta</a></p>
                    <p><a href="#" data-bs-toggle="modal" data-bs-target="#forgotPasswordModal">¿Olvidaste tu Contraseña?</a></p>
                    <p>¿No tienes una cuenta? <a href="<?php echo BASE_URL . 'principal/register'; ?>">Regístrate</a></p>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal para recuperar contraseña -->
<div class="modal fade" id="forgotPasswordModal" tabindex="-1" aria-labelledby="forgotPasswordModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="forgotPasswordModalLabel">Recuperar Contraseña</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="forgotPasswordForm">
                    <div class="form-group mb-3">
                        <label for="email" class="form-label">Ingresa tu correo</label>
                        <div class="input-group">
                            <input type="email" class="form-control" id="email" name="email" required>
                            <button type="button" class="btn btn-success" id="verificar">Verificar</button>
                        </div>
                    </div>
                    <div id="codigoSection" style="display: none;">
                        <div class="form-group mb-3">
                            <label for="codigo" class="form-label">Código de verificación</label>
                            <input type="text" class="form-control" id="codigo" name="codigo" required>
                        </div>
                        <button type="button" class="btn btn-success" id="recuperar">Recuperar Contraseña</button>
                    </div>
                </form>
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

</body>
</html>
