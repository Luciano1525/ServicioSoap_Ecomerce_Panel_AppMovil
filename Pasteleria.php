<?php
include('Conexion.php');
/** require_once('WSDLDocument.php');
try {
    $wsdl = new WSDLDocument("Pasteleria", "http://localhost/SOAPPasteleria/Pasteleria.php", "http://localhost/SOAPPasteleria/");
    echo $wsdl->SaveXml();
} catch (Exception $e) {
    echo $e->getMessage();
}
 */

class Pasteleria
{

    public $id, $id_venta, $id_cliente, $codigo, $nombre, $cantidad, $precio, $id_producto, $id_usuario, $total, $producto_id, $estado;

    /**
     * Obtiene todas las categorías.
     *
     * @return array
     */
    public function getCategorias()
    {
        $sql = "SELECT * FROM categorias";
        return $this->selectAll($sql);
    }

    /**
     * Obtiene los nuevos productos.
     *
     * @return array
     */
    public function getNuevosProductos()
    {
        $sql = "SELECT * FROM productos ORDER BY id DESC LIMIT 12";
        return $this->selectAll($sql);
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param int $id_producto
     * @return array
     */
    public function getProducto($id_producto)
    {
        $sql = "SELECT p.*, c.id as categoria_id, c.categoria 
                FROM productos p 
                INNER JOIN categorias c ON p.categoria = c.id 
                WHERE p.id = :id_producto";
        $params = ['id_producto' => $id_producto];
        return $this->select($sql, $params);
    }

    /**
     * Obtiene productos con paginación.
     *
     * @param int $desde
     * @param int $porPagina
     * @return array
     */
    public function getProductos($desde, $porPagina)
    {
        $sql = "SELECT * FROM productos LIMIT " . intval($desde) . ", " . intval($porPagina);
        return $this->selectAll($sql);
    }


    /**
     * Obtiene el total de productos.
     *
     * @return array
     */
    public function getTotalProductos()
    {
        $sql = "SELECT COUNT(*) AS total FROM productos";
        return $this->select($sql);
    }

    /**
     * Obtiene productos por categoría con paginación.
     *
     * @param int $id_categoria
     * @param int $desde
     * @param int $porPagina
     * @return array
     */
    public function getProductosCat($id_categoria, $desde, $porPagina)
    {
        $sql = "SELECT * FROM productos WHERE categoria = :id_categoria LIMIT " . intval($desde) . ", " . intval($porPagina);
        $params = [
            'id_categoria' => $id_categoria
        ];
        return $this->selectAll($sql, $params);
    }


    /**
     * Obtiene el total de productos por categoría.
     *
     * @param int $id_categoria
     * @return array
     */
    public function getTotalProductosCat($id_categoria)
    {
        $sql = "SELECT COUNT(*) AS total FROM productos WHERE categoria = :id_categoria";
        $params = ['id_categoria' => $id_categoria];
        return $this->select($sql, $params);
    }

    /**
     * Obtiene productos aleatorios por categoría excluyendo un producto.
     *
     * @param int $id_categoria
     * @param int $id_producto
     * @return array
     */
    public function getAleatorios($id_categoria, $id_producto)
    {
        $sql = "SELECT * FROM productos WHERE categoria = :id_categoria AND id != :id_producto ORDER BY RAND() LIMIT 12";
        $params = [
            'id_categoria' => $id_categoria,
            'id_producto' => $id_producto
        ];
        return $this->selectAll($sql, $params);
    }

    /**
     * Verifica si un usuario ya existe.
     *
     * @param string $usuario
     * @return bool
     */
    public function verificarUsuarioExistente($usuario)
    {
        $sql = "SELECT COUNT(*) AS total FROM clientes WHERE usuario = :usuario";
        $params = ['usuario' => $usuario];
        $resultado = $this->select($sql, $params);
        return $resultado['total'] > 0;
    }

    /**
     * Verifica si un correo ya existe.
     *
     * @param string $correo
     * @return bool
     */
    public function verificarCorreoExistente($correo)
    {
        $sql = "SELECT COUNT(*) AS total FROM clientes WHERE correo = :correo";
        $params = ['correo' => $correo];
        $resultado = $this->select($sql, $params);
        return $resultado['total'] > 0;
    }

    /**
     * Verifica si un RUC ya existe.
     *
     * @param string $ruc
     * @return bool
     */
    public function verificarRucExistente($ruc)
    {
        $sql = "SELECT COUNT(*) AS total FROM clientes WHERE ruc = :ruc";
        $params = ['ruc' => $ruc];
        $resultado = $this->select($sql, $params);
        return $resultado['total'] > 0;
    }

    /**
     * Inserta un nuevo cliente.
     *
     * @param string $ruc
     * @param string $nombre
     * @param string $direccion
     * @param string $telefono
     * @param string $municipio
     * @param string $estado_municipio
     * @param string $correo
     * @param string $genero
     * @param string $usuario
     * @param string $contraseña
     * @param string $preferencias 
     * @param string $estado
     * @return bool
     */
    public function insertarCliente(string $ruc, string $nombre, string $direccion, string $telefono, string $municipio, string $estado_municipio, string $correo, string $genero, string $usuario, string $contraseña, string $preferencias, string $estado)
    {
        $sql = "INSERT INTO clientes (ruc, nombre, direccion, telefono, municipio, estado_municipio, correo, genero, usuario, contraseña, preferencias, estado)
            VALUES (:ruc, :nombre, :direccion, :telefono, :municipio, :estado_municipio, :correo, :genero, :usuario, :contrasena, :preferencias, :estado)";
        $params = [
            ':ruc' => $ruc,
            ':nombre' => $nombre,
            ':direccion' => $direccion,
            ':telefono' => $telefono,
            ':municipio' => $municipio,
            ':estado_municipio' => $estado_municipio,
            ':correo' => $correo,
            ':genero' => $genero,
            ':usuario' => $usuario,
            ':contrasena' => $contraseña,
            ':preferencias' => $preferencias,
            ':estado' => $estado
        ];
        return $this->insert($sql, $params);
    }


    /**
     * Selecciona un usuario por su nombre de usuario.
     *
     * @param string $usuario
     * @return array
     */
    public function selectUsuario($usuario)
    {
        $sql = "SELECT * FROM clientes WHERE usuario = :usuario";
        $params = ['usuario' => $usuario];
        return $this->select($sql, $params);
    }

    /**
     * Actualiza la información de un usuario.
     *
     * @param string $nombre
     * @param string $direccion
     * @param string $telefono
     * @param string $municipio
     * @param string $estado_municipio
     * @param string $correo
     * @param string $genero
     * @param string $usuario
     * @param string $contraseña
     * @param string $preferencias
     * @return bool
     */
    public function updateUsuario($nombre, $direccion, $telefono, $municipio, $estado_municipio, $correo, $genero, $usuario, $contraseña, $preferencias)
    {
        $query = "UPDATE clientes SET nombre=?, direccion=?, telefono=?, municipio=?, estado_municipio=?, correo=?, genero=?, contraseña=?, preferencias=? WHERE usuario=?";
        $data = [$nombre, $direccion, $telefono, $municipio, $estado_municipio, $correo, $genero, $contraseña, $preferencias, $usuario];
        return $this->update($query, $data);
    }

    /**
     * Actualiza la contraseña de un usuario.
     *
     * @param string $contraseña
     * @param string $correo
     * @return bool
     */
    public function updateContraseña($contraseña, $correo)
    {
        $query = "UPDATE clientes SET contraseña=? WHERE correo=?";
        $data = [$contraseña, $correo];
        return $this->update($query, $data);
    }

    /**
     * Obtiene las compras de un usuario.
     *
     * @param string $usuario
     * @return array
     */
    public function misCompras($usuario)
    {
        $sql = "SELECT p.imagen, p.nombre, v.total, v.fecha, c.usuario
                FROM detalle_venta dv
                INNER JOIN ventas v ON dv.id_venta = v.id
                INNER JOIN productos p ON dv.id_producto = p.id
                INNER JOIN clientes c ON v.id_cliente = c.id
                WHERE c.usuario = :usuario";
        $params = ['usuario' => $usuario];
        return $this->selectAll($sql, $params);
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param int $id_producto
     * @return array
     */
    public function getProductoById($id_producto)
    {
        $sql = "SELECT * FROM productos WHERE id = :id_producto";
        $params = ['id_producto' => $id_producto];
        return $this->select($sql, $params);
    }

    /**
     * Selecciona un usuario por su nombre de usuario.
     *
     * @param string $usuario
     * @return array
     */
    public function selectUsuarios($usuario)
    {
        try {
            $sql = "SELECT * FROM clientes WHERE usuario = :usuario";
            $params = [':usuario' => $usuario];
            return $this->select($sql, $params);
        } catch (Exception $e) {
            // Manejo de errores
            return [];
        }
    }

    /**
     * Selecciona el ID máximo de las ventas.
     *
     * @return array
     */
    public function buscaridC()
    {
        try {
            $sql = "SELECT MAX(id) FROM ventas";
            $res = $this->select($sql);
            return $res;
        } catch (Exception $e) {
            // Manejo de excepción
            return 'Error: ' . $e->getMessage();
        }
    }

    /**
     * Selecciona un producto por su ID y estado.
     *
     * @param int $producto_id
     * @return array
     */
    public function buscarProducto(int $producto_id)
    {
        try {
            $this->producto_id = $producto_id;
            $sql = "SELECT * FROM productos WHERE id = '{$this->producto_id}' AND estado = 1";
            $res = $this->select($sql);
            if (empty($res)) {
                $res = 0;
            }
            return $res;
        } catch (Exception $e) {
            // Manejo de excepción
            return 'Error: ' . $e->getMessage();
        }
    }

    /**
     * Registra una nueva compra.
     *
     * @param int $cliente
     * @param string $total
     * @return string
     */
    public function registrarCompra(int $cliente, string $total)
    {
        $return = "";
        $this->id_cliente = $cliente;
        $this->total = $total;
        $query = "INSERT INTO ventas(id_cliente, total) VALUES (?, ?)";
        $data = array($this->id_cliente, $this->total);
        $resul = $this->insert($query, $data);
        $return = $resul;
        return $return;
    }

    /**
     * Registra el detalle de una venta.
     *
     * @param string $id_venta
     * @param string $nombre
     * @param string $id_producto
     * @param string $cantidad
     * @param string $precio
     * @param string $id_usuario
     * @return array
     */
    public function registrarDetalle(string $id_venta, string $nombre, string $id_producto, string $cantidad, string $precio, string $id_usuario)
    {
        try {
            $datos = "";
            $this->id_venta = $id_venta;
            $this->id_producto = $id_producto;
            $this->cantidad = $cantidad;
            $this->precio = $precio;
            $this->id_usuario = $id_usuario;
            $this->nombre = $nombre;

            $query = "INSERT INTO detalle_venta(id_venta, producto, id_producto, cantidad, precio, id_usuario) VALUES (?,?,?,?,?,?)";
            $data = array($this->id_venta, $this->nombre, $this->id_producto, $this->cantidad, $this->precio, $this->id_usuario);
            $datos = $this->insert($query, $data);
            $datos["estatus"] = "success";
            return $datos;
        } catch (Exception $e) {
            // Manejo de excepción
            $datos["estatus"] = "error";
            $datos["errordesc"] = 'Error: ' . $e->getMessage();
            return $datos;
        }
    }

    /**
     * Verifica los productos de un usuario.
     *
     * @param int $id_usuario
     * @return array
     */
    public function verificarProductos(int $id_usuario)
    {
        try {
            $query = "SELECT * FROM detalle_tempo WHERE id_usuario = :id_usuario";
            $params = [':id_usuario' => $id_usuario];
            $datos = $this->select($query, $params);
            $datos["estatus"] = "success";
            return $datos;
        } catch (Exception $e) {
            // Manejo de excepción
            $datos["estatus"] = "error";
            $datos["errordesc"] = 'Error: ' . $e->getMessage();
            return $datos;
        }
    }

    /**
     * Inserta un detalle temporal.
     *
     * @param string $nombre
     * @param string $cantidad
     * @param string $precio
     * @param string $total
     * @param string $id_producto
     * @param string $id_usuario
     * @param string $id_cliente
     * @return string
     */
    public function insertarDetalle(string $nombre, string $cantidad, string $precio, string $total, string $id_producto, string $id_usuario, string $id_cliente)
    {
        try {
            $return = "";
            $this->nombre = $nombre;
            $this->cantidad = $cantidad;
            $this->precio = $precio;
            $this->total = $total;
            $this->id_producto = $id_producto;
            $this->id_usuario = $id_usuario;
            $this->id_cliente = $id_cliente;

            $query = "INSERT INTO detalle_tempo(nombre, cantidad, precio, total, id_producto, id_usuario, id_cliente) VALUES (?,?,?,?,?,?,?)";
            $data = array($this->nombre, $this->cantidad, $this->precio, $this->total, $this->id_producto, $this->id_usuario, $this->id_cliente);
            $resul = $this->insert($query, $data);
            $return = $resul;
            return $return;
        } catch (Exception $e) {
            // Manejo de excepción
            return 'Error: ' . $e->getMessage();
        }
    }

    /**
     * Vacía la compra temporal de un cliente.
     *
     * @param int $id_cliente
     * @return bool
     */
    public function VaciarCompra(int $id_cliente)
    {
        $sql = "DELETE FROM detalle_tempo WHERE id_cliente = :id_cliente";
        $params = [':id_cliente' => $id_cliente];
        return $this->delete($sql, $params);
    }

    /**
     * Vacía la tabla temporal.
     *
     * @return void
     */
    public function VaciarTempo()
    {
        $sql = "DELETE FROM detalle_tempo";
        $resul = $this->delete($sql);
    }

    /**
     * Vacía las ventas en una fecha específica.
     *
     * @param string $fecha
     * @return bool
     */
    public function VaciarVenta(string $fecha)
    {
        $sql = "DELETE FROM ventas WHERE fecha = :fecha";
        $params = [':fecha' => $fecha];
        return $this->delete($sql, $params);
    }

    /**
     * Selecciona las ventas de un cliente.
     *
     * @param int $id_cliente
     * @return array
     */
    public function ventas(int $id_cliente)
    {
        $sql = "SELECT MAX(id), fecha FROM ventas WHERE id_cliente = :id_cliente";
        $params = [':id_cliente' => $id_cliente];
        return $this->select($sql, $params);
    }

    /**
     * Selecciona los detalles de una venta.
     *
     * @param int $idventa
     * @return array
     */
    public function ventasDetalle(int $idventa)
    {
        $sql = "SELECT * FROM detalle_venta WHERE id_venta = :idventa";
        $params = [':idventa' => $idventa];
        return $this->select($sql, $params);
    }

    /**
     * Selecciona el stock actual de un producto.
     *
     * @param int $id_producto
     * @return array
     */
    public function stockActual(int $id_producto)
    {
        $sql = "SELECT * FROM productos WHERE id = :id_producto";
        $params = [':id_producto' => $id_producto];
        return $this->select($sql, $params);
    }

    /**
     * Actualiza el stock de un producto.
     *
     * @param int $cantidad
     * @param int $id_producto
     * @return bool
     */
    public function registrarStock(int $cantidad, int $id_producto)
    {
        $query = "UPDATE productos SET cantidad=? WHERE id = :id_producto";
        $data = array($cantidad, $id_producto);
        $resul = $this->update($query, $data);
        return $resul;
    }

    /**
     * Elimina un producto del carrito.
     *
     * @param int $id_cliente
     * @param int $id_producto
     * @param string $total
     * @return bool
     */
    public function eliminarCarrito(int $id_cliente, int $id_producto, string $total)
    {
        $sql = "DELETE FROM ventas WHERE id_cliente = :id_cliente AND id_producto = :id_producto AND total = :total";
        $params = [':id_cliente' => $id_cliente, ':id_producto' => $id_producto, ':total' => $total];
        return $this->delete($sql, $params);
    }


    /**
     * Método para realizar una consulta SELECT.
     *
     * @param string $sql
     * @param array $params
     * @return array
     */
    private function select($sql, $params = [])
    {
        $conexion = Conexion::conectar();
        $stmt = $conexion->prepare($sql);
        $stmt->execute($params);
        return $stmt->fetch(PDO::FETCH_ASSOC);
    }

    /**
     * Método para realizar una consulta SELECT que devuelve múltiples filas.
     *
     * @param string $sql
     * @param array $params
     * @return array
     */
    private function selectAll($sql, $params = [])
    {
        $conexion = Conexion::conectar();
        $stmt = $conexion->prepare($sql);
        $stmt->execute($params);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    /**
     * Método para insertar datos.
     *
     * @param string $sql
     * @param array $params
     * @return bool
     */
    private function insert($sql, $params)
    {
        $conexion = Conexion::conectar();
        $stmt = $conexion->prepare($sql);
        return $stmt->execute($params);
    }

    /**
     * Método para actualizar datos.
     *
     * @param string $sql
     * @param array $params
     * @return bool
     */
    private function update($sql, $params)
    {
        $conexion = Conexion::conectar();
        $stmt = $conexion->prepare($sql);
        return $stmt->execute($params);
    }

    /**
     * Método para eliminar datos.
     *
     * @param string $sql
     * @param array $params
     * @return bool
     */
    private function delete($sql, $params = [])
    {
        $conexion = Conexion::conectar();
        $stmt = $conexion->prepare($sql);
        return $stmt->execute($params);
    }
}


try {
    $server = new SoapServer("Pasteleria.wsdl", array('cache_wsdl' => WSDL_CACHE_NONE));
    $server->setClass("Pasteleria");
    //$server->addFunction("GetEmail");
    $server->handle();
} catch (SOAPFault $f) {
    print $f->faultstring;
}
