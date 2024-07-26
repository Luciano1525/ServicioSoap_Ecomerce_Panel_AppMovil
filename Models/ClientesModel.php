<?php
class ClientesModel extends Mysql{
    public $id, $ruc, $nombre, $telefono, $direccion;
    public function __construct()
    {
        parent::__construct();
    }
    public function selectClientesInactivos()
    {
        $sql = "SELECT * FROM clientes WHERE estado = 0";
        $res = $this->select_all($sql);
        return $res;
    }
    public function BuscarCliente(string $ruc)
    {
        $this->ruc = $ruc;
        $sql = "SELECT * FROM clientes WHERE ruc = $this->ruc AND estado = 1";
        $res = $this->select($sql);
        return $res;
    }
    public function selectClientes()
    {
        $sql = "SELECT * FROM clientes WHERE estado = 1";
        $res = $this->select_all($sql);
        return $res;
    }
    public function insertarClientes(String $ruc, String $nombre, String $usuario, String $telefono, String $direccion)
{
    $return = "";
    // Verificar si el cliente ya existe
    $sql = "SELECT * FROM clientes WHERE ruc = '{$ruc}'";
    $existe = $this->select_all($sql);
    if (empty($existe)) {
        $query = "INSERT INTO clientes(ruc, nombre, usuario, telefono, direccion) VALUES (?,?,?,?,?)";
        $data = array($ruc, $nombre, $usuario, $telefono, $direccion);
        $resul = $this->insert($query, $data);
        $return = $resul;
    } else {
        $return = "existe";
    }
    return $return;
}

    public function editarClientes(int $id)
    {
        $this->id = $id;
        $sql = "SELECT * FROM clientes WHERE id = '{$this->id}'";
        $res = $this->select($sql);
        if (empty($res)) {
            $res = 0;
        }
        return $res;
    }
    public function actualizarClientes(String $ruc, String $nombre, String $direccion, String $telefono, String $municipio, String $estado_municipio, String $correo, String $genero, String $usuario, String $contraseña, String $preferencias, int $estado, int $id)
{
    $query = "UPDATE clientes SET ruc=?, nombre=?, direccion=?, telefono=?, municipio=?, estado_municipio=?, correo=?, genero=?, usuario=?, contraseña=?, preferencias=?, estado=? WHERE id=?";
    $data = array($ruc, $nombre, $direccion, $telefono, $municipio, $estado_municipio, $correo, $genero, $usuario, $contraseña, $preferencias, $estado, $id);
    $resul = $this->update($query, $data);
    return $resul;
}


    public function eliminarClientes(int $id)
    {
        $return = "";
        $this->id = $id;
        $query = "UPDATE clientes SET estado = 0 WHERE id=?";
        $data = array($this->id);
        $resul = $this->update($query, $data);
        $return = $resul;
        return $return;
    }
    public function reingresarClientes(int $id)
    {
        $return = "";
        $this->id = $id;
        $query = "UPDATE clientes SET estado = 1 WHERE id=?";
        $data = array($this->id);
        $resul = $this->update($query, $data);
        $return = $resul;
        return $return;
    }
    public function selectClienteById(int $id)
{
    $this->id = $id;
    $sql = "SELECT * FROM clientes WHERE id = '{$this->id}'";
    $res = $this->select($sql);
    return $res;
}

public function selectProductosComprados($id) {
    $sql = "SELECT p.nombre as producto, COUNT(*) as total 
            FROM detalle_venta dv
            INNER JOIN ventas v ON dv.id_venta = v.id
            INNER JOIN productos p ON dv.id_producto = p.id
            WHERE v.id_cliente = ?
            GROUP BY p.nombre
            ORDER BY total DESC";
    $params = array($id);
    return $res = $this->select_all($sql, $params);
}

public function selectFrecuenciaCompra($id) {
    $sql = "SELECT DATE_FORMAT(v.fecha, '%Y-%m') as Mes, COUNT(v.id_cliente) as total
            FROM detalle_venta dv
            INNER JOIN ventas v ON dv.id_venta = v.id
            INNER JOIN productos p ON dv.id_producto = p.id
            WHERE v.id_cliente = ?
            GROUP BY Mes
            ORDER BY Mes ASC";
    $params = array($id);
    return $res = $this->select_all($sql, $params);
}


public function selectProductosPreferidos($id) {
    $sql = "SELECT preferencias 
            FROM clientes 
            WHERE id = ?";
    $params = array($id);
    $result = $this->select($sql, $params);
    
    // Si hay preferencias, separarlas en columnas
    if ($result && isset($result['preferencias'])) {
        $preferencias = explode(' ', $result['preferencias']); // Separar por espacio
        return $preferencias;
    }
    
    return []; // Devolver un array vacío si no hay preferencias
}





}
?>