<?php
class ProductosModel extends Mysql{
    public $id, $codigo, $nombre, $cantidad, $precio;
    public function __construct()
    {
        parent::__construct();
    }
    public function selectProductos()
    {
        $sql = "SELECT p.id, p.imagen, p.codigo, p.nombre, p.cantidad, p.precio,
        c.categoria, p.descripcion FROM productos p INNER JOIN categorias c 
        ON p.categoria = c.id WHERE estado = 1";
        $res = $this->select_all($sql);
        return $res;
    }
    public function selectProductosInactivos()
    {
        $sql = "SELECT * FROM productos WHERE estado = 0";
        $res = $this->select_all($sql);
        return $res;
    }
    public function insertarProductos(string $codigo, string $nombre, float $precio, string $imagen, int $categoria, string $descripcion)
{
    $return = "";

    $sql = "SELECT * FROM productos WHERE codigo = '{$codigo}'";
    $result = $this->select_all($sql);

    if (empty($result)) {
        $query = "INSERT INTO productos(codigo, nombre, precio, imagen, categoria, descripcion) VALUES (?,?,?,?,?,?)";
        $data = array($codigo, $nombre, $precio, $imagen, $categoria, $descripcion);
        $resul = $this->insert($query, $data);
        $return = $resul;
    } else {
        $return = "existe";
    }

    return $return;
}


    
    public function editarProductos(int $id)
    {
        $this->id = $id;
        $sql = "SELECT * FROM productos WHERE id = '{$this->id}'";
        $res = $this->select($sql);
        if (empty($res)) {
            $res = 0;
        }
        return $res;
    }
    public function selectProducto(int $id)
{
    $sql = "SELECT * FROM productos WHERE id = ?";
    $data = $this->select($sql, [$id]);
    return $data;
}

public function actualizarProductos(string $codigo, string $nombre, int $cantidad, float $precio, string $imagen, int $categoria, string $descripcion, int $id) {
    $sql = "UPDATE productos SET codigo = ?, nombre = ?, cantidad = ?, precio = ?, imagen = ?, categoria = ?, descripcion = ? WHERE id = ?";
    $data = array($codigo, $nombre, $cantidad, $precio, $imagen, $categoria, $descripcion, $id);
    $result = $this->update($sql, $data);
    return $result;
}






    public function eliminarProductos(int $id)
    {
        $return = "";
        $this->id = $id;
        $query = "UPDATE productos SET estado = 0 WHERE id=?";
        $data = array($this->id);
        $resul = $this->update($query, $data);
        $return = $resul;
        return $return;
    }
    public function reingresarProductos(int $id)
    {
        $return = "";
        $this->id = $id;
        $query = "UPDATE productos SET estado = 1 WHERE id=?";
        $data = array($this->id);
        $resul = $this->update($query, $data);
        $return = $resul;
        return $return;
    }
}
?>