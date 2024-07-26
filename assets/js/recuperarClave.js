$(document).ready(function() {
    if (typeof BASE_URL === 'undefined') {
        console.error('BASE_URL no está definido');
        return;
    }

    $('#verificar').on('click', function(event) {
        event.preventDefault();

        var correo = $('#email').val();

        if (correo && correo.trim()) {
            $.ajax({
                url: BASE_URL + 'principal/RecuperarContraseña',
                method: 'POST',
                data: { correo: correo, verificar: true },
                success: function(response) {
                    try {
                        var res = JSON.parse(response);
                        if (res.success) {
                            Swal.fire('Aviso', 'Correo Verificado con Éxito.', 'success').then(() => {
                                $('#codigo').val(res.codigo);
                                $('#codigoSection').show();
                            });
                        } else {
                            Swal.fire('Aviso', res.message, 'error');
                        }
                    } catch (error) {
                        console.error('Error al parsear respuesta JSON:', error);
                    }
                },
                error: function(xhr, status, error) {
                    console.error('Error en la solicitud AJAX:', status, error);
                }
            });
        } else {
            Swal.fire('Aviso', 'Por favor ingresa un correo válido.', 'warning');
        }
    });

    $('#recuperar').on('click', function(event) {
        event.preventDefault();

        var codigoIngresado = $('#codigo').val();
        var correo = $('#email').val();

        if (codigoIngresado && codigoIngresado.trim()) {
            $.ajax({
                url: BASE_URL + 'principal/verificarCodigo',
                method: 'POST',
                data: { correo: correo, codigo: codigoIngresado },
                success: function(response) {
                    try {
                        var res = JSON.parse(response);
                        if (res.success) {
                            window.location.href = BASE_URL + 'principal/contraseña?correo=' + encodeURIComponent(correo);
                        } else {
                            Swal.fire('Aviso', 'Código de verificación incorrecto.', 'error');
                        }
                    } catch (error) {
                        console.error('Error al parsear respuesta JSON:', error);
                    }
                },
                error: function(xhr, status, error) {
                    console.error('Error en la solicitud AJAX:', status, error);
                }
            });
        } else {
            Swal.fire('Aviso', 'Por favor ingresa un código válido.', 'warning');
        }
    });
});
