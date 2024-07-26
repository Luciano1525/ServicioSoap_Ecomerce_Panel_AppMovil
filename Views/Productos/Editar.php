<?php encabezado() ?>
<!-- Begin Page Content -->
<div class="page-content bg-light">
    <section>
        <div class="container-fluid">
            <div class="row mt3">
                <div class="col-lg-6 m-auto">
                    <form method="post" action="<?php echo base_url(); ?>Productos/actualizar" enctype="multipart/form-data" autocomplete="off">
                        <div class="card-header bg-dark">
                            <h6 class="title text-white text-center">Modificar Producto</h6>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="codigo">Código de barras</label>
                                <input type="hidden" name="id" value="<?php echo $data['id']; ?>">
                                <input id="codigo" class="form-control" type="text" name="codigo" value="<?php echo $data['codigo']; ?>">
                            </div>
                            <div class="form-group">
                                <label for="imagen">Imagen</label>
                                <input id="imagen" class="form-control" type="file" name="imagen">
                                <img src="<?php echo base_url() . $data['imagen']; ?>" width="100" alt="Imagen actual">
                            </div>
                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input id="nombre" class="form-control" type="text" name="nombre" value="<?php echo $data['nombre']; ?>">
                            </div>
                            <div class="form-group">
                                <label for="cantidad">Stock</label>
                                <input id="cantidad" class="form-control" type="text" name="cantidad" value="<?php echo $data['cantidad']; ?>">
                            </div>
                            <div class="form-group">
                                <label for="precio">Precio</label>
                                <input id="precio" class="form-control" type="text" name="precio" value="<?php echo $data['precio']; ?>">
                            </div>
                            <div class="form-group">
                                <label for="categoria">Categoría</label>
                                <select id="categoria" class="form-control" name="categoria">
                                    <option value="1" <?php echo $data['categoria'] == 1 ? 'selected' : ''; ?>>Pasteles</option>
                                    <option value="2" <?php echo $data['categoria'] == 2 ? 'selected' : ''; ?>>CupCake</option>
                                    <option value="3" <?php echo $data['categoria'] == 3 ? 'selected' : ''; ?>>Tartas</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="descripcion">Descripción</label>
                                <input id="descripcion" class="form-control" type="text" name="descripcion" value="<?php echo $data['descripcion']; ?>">
                            </div>
                        </div>
                        <div class="card-footer">
                            <button class="btn btn-dark" type="submit">Modificar</button>
                            <a href="<?php echo base_url(); ?>Productos/Listar" class="btn btn-danger">Regresar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</div>
<?php pie() ?>
