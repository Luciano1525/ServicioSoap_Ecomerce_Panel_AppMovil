document.addEventListener('DOMContentLoaded', function () {
    // Asegurarse de que BASE_URL y CLIENT_ID están definidos
    if (typeof BASE_URL === 'undefined' || typeof CLIENT_ID === 'undefined') {
        console.error('BASE_URL o CLIENT_ID no están definidos');
        return;
    }

    // Hacer una solicitud AJAX para obtener los datos
    var xhr = new XMLHttpRequest();
    xhr.open('GET', BASE_URL + 'Clientes/Graficar/' + CLIENT_ID, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            var data = JSON.parse(xhr.responseText);

            // Preparar datos para la primera gráfica (Productos Más Comprados)
            var productosCompradosData = [];
            var productosCompradosLabels = [];
            data.Comprados.forEach(function(item) {
                productosCompradosLabels.push(item.producto);
                productosCompradosData.push(item.total);
            });

            var ctx1 = document.getElementById('grafica1');
            if (ctx1) {
                var grafica1 = new Chart(ctx1.getContext('2d'), {
                    type: 'pie',
                    data: {
                        labels: productosCompradosLabels,
                        datasets: [{
                            data: productosCompradosData,
                            backgroundColor: [
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)'
                            ],
                            borderColor: [
                                'rgba(255, 99, 132, 1)',
                                'rgba(54, 162, 235, 1)',
                                'rgba(255, 206, 86, 1)',
                                'rgba(75, 192, 192, 1)',
                                'rgba(153, 102, 255, 1)'
                            ],
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        plugins: {
                            legend: {
                                position: 'bottom',
                            },
                            tooltip: {
                                callbacks: {
                                    label: function(tooltipItem) {
                                        var index = tooltipItem.dataIndex;
                                        return "Producto Comprado: " + productosCompradosLabels[index] + " - Total: " + productosCompradosData[index];
                                    }
                                }
                            }
                        }
                    }
                });
            } else {
                console.error('Element grafica1 no existe en el DOM');
            }

            // Preparar datos para la segunda gráfica (Frecuencia de Compra)
            var frecuenciaCompraLabels = [];
            var frecuenciaCompraData = [];
            data.Frecuencia.forEach(function(item) {
                frecuenciaCompraLabels.push(item.Mes + " - " + item.producto);
                frecuenciaCompraData.push(item.total);
            });

            var ctx2 = document.getElementById('grafica2');
            if (ctx2) {
                var grafica2 = new Chart(ctx2.getContext('2d'), {
                    type: 'pie', // Cambiar a tipo 'pie'
                    data: {
                        labels: frecuenciaCompraLabels,
                        datasets: [{
                            label: 'Frecuencia de Compra',
                            data: frecuenciaCompraData,
                            backgroundColor: [
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)'
                            ],
                            borderColor: [
                                'rgba(54, 162, 235, 1)',
                                'rgba(255, 206, 86, 1)',
                                'rgba(75, 192, 192, 1)',
                                'rgba(153, 102, 255, 1)',
                                'rgba(255, 159, 64, 1)'
                            ],
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        plugins: {
                            legend: {
                                position: 'bottom',
                            },
                            tooltip: {
                                callbacks: {
                                    label: function(tooltipItem) {
                                        var index = tooltipItem.dataIndex;
                                        return "Mes: " + frecuenciaCompraLabels[index] + " - Total: " + frecuenciaCompraData[index];
                                    }
                                }
                            }
                        }
                    }
                });
            } else {
                console.error('Element grafica2 no existe en el DOM');
            }

            // Preparar datos para la tercera gráfica (Preferencia de Productos)
var preferenciasData = {};
data.Preferencia.forEach(function(item) {
    var producto = item.preferencia.trim();
    if (producto !== "") {
        if (preferenciasData[producto]) {
            preferenciasData[producto]++;
        } else {
            preferenciasData[producto] = 1;
        }
    }
});

// Convertir preferenciasData en un array de objetos para Chart.js
var preferenciasLabels = Object.keys(preferenciasData);
var preferenciasCounts = Object.values(preferenciasData);

// Configurar la tercera gráfica como un gráfico de pastel (doughnut)
var ctx3 = document.getElementById('grafica3');
if (ctx3) {
    var grafica3 = new Chart(ctx3.getContext('2d'), {
        type: 'doughnut',
        data: {
            labels: preferenciasLabels,
            datasets: [{
                label: 'Preferencia de Productos',
                data: preferenciasCounts,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'bottom',
                },
                tooltip: {
                    callbacks: {
                        label: function(tooltipItem) {
                            var index = tooltipItem.dataIndex;
                            return "Preferencia: " + preferenciasLabels[index] + " - Total: " + preferenciasCounts[index];
                        }
                    }
                }
            }
        }
    });
} else {
    console.error('Elemento grafica3 no encontrado en el DOM');
}

        }
    };
    xhr.send();
});
