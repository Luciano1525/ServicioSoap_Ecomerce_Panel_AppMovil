document.addEventListener("DOMContentLoaded", function() {

    if (typeof BASE_URL === 'undefined') {
        console.error('BASE_URL no está definido');
        return;
    }

    const cardNumberInput = document.getElementById("card-number");
    const cardCvcInput = document.getElementById("card-cvc");
    const paymentForm = document.getElementById("payment-form");

    cardNumberInput.addEventListener("input", function() {
        let value = cardNumberInput.value.replace(/\D/g, "");
        value = value.replace(/(.{4})/g, "$1-").trim();
        cardNumberInput.value = value.substring(0, 19); // Limit to 19 characters (16 digits + 3 hyphens)
    });

    cardCvcInput.addEventListener("input", function() {
        cardCvcInput.value = cardCvcInput.value.replace(/\D/g, "").substring(0, 3); // Limit to 3 digits
    });

    paymentForm.addEventListener("submit", function(event) {
        event.preventDefault(); // Prevent the default form submission

        if (!paymentForm.checkValidity()) {
            Swal.fire({
                icon: 'error',
                title: 'Datos Incorrectos',
                text: 'Por favor, rellena correctamente todos los campos del formulario.'
            });
        } else {
            Swal.fire({
                icon: 'success',
                title: 'Compra Realizada con Éxito',
                text: 'Gracias por tu compra.'
            }).then((result) => {
                if (result.isConfirmed) {
                    // Llamar a la función Vaciar() a través de AJAX
                    $.ajax({
                        url: BASE_URL + 'principal/vaciar',
                        method: 'POST',
                        success: function(response) {
                            // Manejar la respuesta del servidor si es necesario
                            console.log(response);
                            // Redirigir a la página de inicio
                            window.location.href = BASE_URL + 'principal/inicio';
                        },
                        error: function(xhr, status, error) {
                            // Manejar errores
                            console.error(error);
                        }
                    });
                }
            });
        }

        paymentForm.classList.add("was-validated");
    }, false);
});
