<?php encabezado() ?>
<!-- Begin Page Content -->
<div class="page-content bg-light">
    <section>
        <div class="container-fluid">
            <div class="row mt-2">
                <div class="col-lg-6 m-auto">
                    <form method="post" action="<?php echo base_url(); ?>Clientes/actualizar" autocomplete="off">
                        <div class="card-header bg-dark">
                            <h6 class="title text-white text-center"><i class="fas fa-user-edit"></i> Modificar Cliente</h6>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="ruc">Código</label>
                                <input type="hidden" name="id" value="<?php echo $data['id']; ?>">
                                <input id="ruc" class="form-control" type="text" name="ruc" value="<?php echo $data['ruc']; ?>">
                            </div>
                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input id="nombre" class="form-control" type="text" name="nombre" value="<?php echo $data['nombre']; ?>">
                            </div>
                            <div class="form-group">
                                <label for="direccion">Dirección</label>
                                <input id="direccion" class="form-control" type="text" name="direccion" value="<?php echo $data['direccion']; ?>">
                            </div>
                            <div class="form-group">
                                <label for="telefono">Teléfono</label>
                                <input id="telefono" class="form-control" type="text" name="telefono" value="<?php echo $data['telefono']; ?>">
                            </div>
                            <div class="form-group">
                                <label for="municipio">Municipio</label>
                                <select id="municipio" class="form-control" name="municipio">
                                    <?php
                                    $municipios = array("Municipio", "Abalá", "Acanceh", "Akil", "Baca", "Bokobá", "Buctzotz", "Cacalchén", "Calotmul", "Cansahcab", "Cantamayec", "Celestún", "Cenotillo", "Conkal", "Cuncunul", "Cuzamá", "Chacsinkín", "Chankom", "Chapab", "Chemax", "Chicxulub Pueblo", "Chichimilá", "Chikindzonot", "Chocholá", "Chumayel", "Dzán", "Dzemul", "Dzidzantún", "Dzilam de Bravo", "Dzilam González", "Dzitás", "Dzoncauich", "Espita", "Halachó", "Hocabá", "Hoctún", "Homún", "Huhí", "Hunucmá", "Ixil", "Izamal", "Kanasín", "Kantunil", "Kaua", "Kinchil", "Kopomá", "Mama", "Maní", "Maxcanú", "Mayapán", "Mérida", "Mocochá", "Motul", "Muna", "Muxupip", "Opichén", "Oxkutzcab", "Panabá", "Peto", "Progreso", "Quintana Roo", "Río Lagartos", "Sacalum", "Samahil", "Sanahcat", "San Felipe", "Santa Elena", "Seyé", "Sinanché", "Sotuta", "Sucilá", "Sudzal", "Suma", "Tahdziú", "Tahmek", "Teabo", "Tecoh", "Tekal de Venegas", "Tekantó", "Tekax", "Tekit", "Tekom", "Telchac Pueblo", "Telchac Puerto", "Temax", "Temozón", "Tepakán", "Tetiz", "Teya", "Ticul", "Timucuy", "Tinum", "Tixcacalcupul", "Tixkokob", "Tixmehuac", "Tixpéhual", "Tizimín", "Tunkás", "Tzucacab", "Uayma", "Ucú", "Umán", "Valladolid", "Xocchel", "Yaxcabá", "Yaxkukul", "Yobaín");
                                    foreach ($municipios as $municipio) {
                                        echo "<option value='$municipio' " . ($data['municipio'] == $municipio ? 'selected' : '') . ">$municipio</option>";
                                    }
                                    ?>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="estado_municipio">Estado</label>
                                <input id="estado_municipio" class="form-control" type="text" name="estado_municipio" value="Yucatán" readonly>
                            </div>
                            <div class="form-group">
                                <label for="correo">Correo</label>
                                <input id="correo" class="form-control" type="email" name="correo" value="<?php echo $data['correo']; ?>">
                            </div>
                            <div class="form-group">
                                <label for="genero">Género</label>
                                <select id="genero" class="form-control" name="genero">
                                    <option value="">Género</option>
                                    <option value="Femenino" <?php echo $data['genero'] == 'Femenino' ? 'selected' : ''; ?>>Femenino</option>
                                    <option value="Masculino" <?php echo $data['genero'] == 'Masculino' ? 'selected' : ''; ?>>Masculino</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="usuario">Usuario</label>
                                <input id="usuario" class="form-control" type="text" name="usuario" value="<?php echo $data['usuario']; ?>">
                            </div>
                            <div class="form-group">
                                <label for="contraseña">Contraseña</label>
                                <input id="contraseña" class="form-control" type="password" name="contraseña" value="<?php echo $data['contraseña']; ?>">
                            </div>
                            <div class="form-group">
                                <label for="preferencias">Preferencias</label>
                                <input id="preferencias" class="form-control" type="text" name="preferencias" value="<?php echo $data['preferencias']; ?>">
                            </div>
                            <div class="form-group">
                                <label for="estado">Estado</label>
                                <input id="estado" class="form-control" type="text" name="estado" value="<?php echo $data['estado']; ?>">
                            </div>
                        </div>
                        <div class="card-footer">
                            <button class="btn btn-dark" type="submit"><i class="fas fa-save"></i> Modificar</button>
                            <a href="<?php echo base_url(); ?>Clientes/Listar" class="btn btn-danger"><i class="fas fa-arrow-alt-circle-left"></i> Regresar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</div>
<?php pie() ?>
