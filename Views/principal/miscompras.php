<?php include_once 'Views/template-principal/header.php'; ?>

<!-- Start Content -->
<div class="container py-5">
    <div class="alert alert-success d-flex align-items-center" role="alert">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img" aria-label="Warning:">
            <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
        </svg>
        <div class="h3">
            <?php echo ucfirst($data['title']); ?>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-body shadow-lg">
                    <div class="table-responsive">
                        <table class="table table-borderer align-middle table-striped table-hover" id="tableListaCompras" name="tableListaCompras" style="width:100%;">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Producto</th>
                                    <th>Precio</th>
                                    <th>Fecha</th>
                                </tr>
                            </thead>
                            <tbody>
                                <?php if (empty($data['compras'])): ?>
                                    <tr>
                                        <td colspan="4" class="text-center">No tienes ninguna compra registrada</td>
                                    </tr>
                                <?php else: ?>
                                    <?php foreach ($data['compras'] as $index => $compra): ?>
                                        <tr>
                                            <td>
                                                <img src="<?php echo BASE_URL_IMG . $compra['imagen']; ?>" alt="<?php echo $compra['nombre']; ?>" style="width: 70px; height: 70px; object-fit: cover; border-radius: 50%; border: 2px solid #ccc;">
                                            </td>
                                            <td><?php echo $compra['nombre']; ?></td>
                                            <td><?php echo number_format($compra['total'], 2); ?></td>
                                            <td><?php echo date('d/m/Y', strtotime($compra['fecha'])); ?></td>
                                        </tr>
                                    <?php endforeach; ?>
                                <?php endif; ?>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- End Content -->

<?php include_once 'Views/template-principal/footer.php'; ?>

</body>
</html>
