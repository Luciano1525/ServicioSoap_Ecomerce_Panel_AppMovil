<?php encabezado() ?>
<!-- Begin Page Content -->
<div class="page-content bg-light">
    <section>
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12 mt-2">
                    <div class="row">
                        <div class="col-lg-12 mb-2">
                            <a class="btn btn-primary" href="<?php echo base_url(); ?>Clientes/Listar"><i class="fas fa-arrow-left"></i> Regresar</a>
                        </div>
                    </div>
                    <div class="table-responsive mt-4">
                        <table class="table table-hover table-bordered">
                            <thead class="thead-dark">
                                <tr>
                                    <th>Id</th>
                                    <th>Código</th>
                                    <th>Nombre</th>
                                    <th>Dirección</th>
                                    <th>Teléfono</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><?php echo $data['id']; ?></td>
                                    <td><?php echo $data['ruc']; ?></td>
                                    <td><?php echo $data['nombre']; ?></td>
                                    <td><?php echo $data['direccion']; ?></td>
                                    <td><?php echo $data['telefono']; ?></td>
                                    <td>
                                        <a href="<?php echo base_url() ?>Clientes/editar?id=<?php echo $data['id']; ?>" class="btn btn-primary"><i class="fas fa-edit"></i></a>
                                        <form action="<?php echo base_url() ?>Clientes/eliminar?id=<?php echo $data['id']; ?>" method="post" class="d-inline elim">
                                            <button type="submit" class="btn btn-danger"><i class="fas fa-trash-alt"></i></button>
                                        </form>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <h4 class="mt-2 text-center">Reportes Gráficos del Cliente</h4>
<div class="container">
    <div class="row mb-4">
        <h6 class="col-12 text-center">Productos Más Comprados</h6>
        <div class="col-12 text-center">
            <div class="chart-container" style="position: relative; height:300px; width:100%">
                <canvas id="grafica1"></canvas>
            </div>
        </div>
    </div>
    <div class="row mb-4">
        <h6 class="col-12 text-center">Frecuencia de Compra</h6>
        <div class="col-12 text-center">
            <div class="chart-container" style="position: relative; height:300px; width:100%">
                <canvas id="grafica2"></canvas>
            </div>
        </div>
    </div>
    <div class="row mb-4">
        <h6 class="col-12 text-center">Preferencia de Productos</h6>
        <div class="col-12 text-center">
            <div class="chart-container" style="position: relative; height:300px; width:100%">
                <canvas id="grafica3"></canvas>
            </div>
        </div>
    </div>
</div>

    </section>
</div>

<script>
    var BASE_URL = '<?php echo BASE_URL; ?>';
    var CLIENT_ID = '<?php echo $data['id']; ?>';
</script>

<script src="path/to/Clientes.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.7.0/chart.min.js"></script>
<?php pie() ?>
