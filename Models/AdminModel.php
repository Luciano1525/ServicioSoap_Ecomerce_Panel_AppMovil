<?php
class AdminModel extends Mysql{
    public function __construct()
    {
        parent::__construct();
    }
    public function selectStockM()
{
    $sql = "SELECT nombre, cantidad FROM productos ORDER BY cantidad ASC";
    $res = $this->select_all($sql);
    return $res;
}

public function selectProductos()
{
    $sql = "SELECT producto, SUM(cantidad) as total FROM detalle_venta GROUP BY producto ORDER BY total DESC LIMIT 5";
    $res = $this->select_all($sql);
    return $res;
}

    public function productos()
    {
        $sql = "SELECT COUNT(*) FROM productos WHERE estado = 1";
        $res = $this->selecT($sql);
        return $res;
    }
    public function clientes()
    {
        $sql = "SELECT COUNT(*) FROM clientes WHERE estado = 1";
        $res = $this->selecT($sql);
        return $res;
    }
    public function usuarios()
    {
        $sql = "SELECT COUNT(*) FROM usuarios WHERE estado = 1";
        $res = $this->selecT($sql);
        return $res;
    }
    public function ventas()
    {
        $sql = "SELECT COUNT(*) FROM ventas";
        $res = $this->selecT($sql);
        return $res;
    }


}
