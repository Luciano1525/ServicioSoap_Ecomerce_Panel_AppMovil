document.addEventListener("DOMContentLoaded", function() {
    const cookieBanner = document.getElementById('cookie-banner');
    const acceptCookiesBtn = document.getElementById('accept-cookies-btn');
    const cancelCookiesBtn = document.getElementById('cancel-cookies-btn');
    
    // Verificar si la cookie de aceptación ya está configurada
    const cookiesAccepted = document.cookie.includes('cookies_accepted=true');

    // Función para aceptar cookies y ocultar el banner
    function acceptCookies() {
        cookieBanner.style.display = 'none';
        // Establecer una cookie de consentimiento con una caducidad de 365 días
        document.cookie = "cookies_accepted=true; expires=" + new Date(new Date().getTime() + 365 * 24 * 60 * 60 * 1000).toUTCString() + "; path=/";
    }

    // Función para cancelar y ocultar el banner
    function cancelCookies() {
        cookieBanner.style.display = 'none';
    }

    // Mostrar el banner solo si la cookie de aceptación no está configurada
    if (!cookiesAccepted) {
        cookieBanner.style.display = 'block';
    }

    acceptCookiesBtn.addEventListener('click', acceptCookies);
    cancelCookiesBtn.addEventListener('click', cancelCookies);
});
