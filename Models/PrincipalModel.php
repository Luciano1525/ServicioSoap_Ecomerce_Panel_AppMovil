<?php
class PrincipalModel extends Query
{
    public $id, $id_venta, $id_cliente, $codigo, $nombre, $cantidad, $precio, $id_producto, $id_usuario, $total, $producto_id;
    public function __construct()
    {
        parent::__construct();
    }

    public function getCategorias()
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método getCategorias del servicio web SOAP
            $categorias = $soapClient->getCategorias();
    
            return $categorias;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }
    public function getNuevosProductos()
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método getNuevosProductos del servicio web SOAP
            $nuevosProductos = $soapClient->getNuevosProductos();
    
            return $nuevosProductos;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }
    
    public function getProducto($id_producto)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método getProducto del servicio web SOAP
            $nuevosProductos = $soapClient->getProducto($id_producto);
    
            return $nuevosProductos;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }


    //Paginacion
    public function getProductos($desde, $porPagina)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método getProductos del servicio web SOAP
            $Productos = $soapClient->getProductos($desde, $porPagina);
    
            return $Productos;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    //Obtener el total de productos
    public function getTotalProductos()
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método getTotalProductos del servicio web SOAP
            $totslProductos = $soapClient->getTotalProductos();
    
            return $totslProductos;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    //Productos Relacionados con la Categorias
    public function getProductosCat($id_categoria, $desde, $porPagina)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método getProductosCat del servicio web SOAP
            $catProductos = $soapClient->getProductosCat($id_categoria, $desde, $porPagina);
    
            return $catProductos;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    //Obtener el total de Productos relacionado con su categoria
    public function getTotalProductosCat($id_categoria)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método getTotalProductosCat del servicio web SOAP
            $catProductosTotal = $soapClient->getTotalProductosCat($id_categoria);
    
            return $catProductosTotal;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    //Productos Relacionados con la Categorias en Detalles del Producto Seleccionado
    public function getAleatorios($id_categoria, $id_producto)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método getAleatorios del servicio web SOAP
            $Aleatorio = $soapClient->getAleatorios($id_categoria, $id_producto);
    
            return $Aleatorio;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    // Consulta para verificar registros
    public function verificarUsuarioExistente($usuario)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método verificarUsuarioExistente del servicio web SOAP
            $veriUsuario = $soapClient->verificarUsuarioExistente($usuario);
    
            return $veriUsuario;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }


    public function verificarCorreoExistente($correo)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método verificarCorreoExistente del servicio web SOAP
            $veriCorreo = $soapClient->verificarCorreoExistente($correo);
    
            return $veriCorreo;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    public function verificarRucExistente($ruc)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método verificarRucExistente del servicio web SOAP
            $veriRuc = $soapClient->verificarRucExistente($ruc);
    
            return $veriRuc;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    //El Cliente se registra
    public function insertarCliente($ruc, $nombre, $direccion, $telefono, $municipio, $estado_municipio, $correo, $genero, $usuario, $contraseña, $preferencias, $estado)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método insertarCliente del servicio web SOAP
            $clientes = $soapClient->insertarCliente($ruc, $nombre, $direccion, $telefono, $municipio, $estado_municipio, $correo, $genero, $usuario, $contraseña, $preferencias, $estado);
    
            return $clientes;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }


    public function selectUsuario(string $usuario)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método selectUsuario del servicio web SOAP
            $selectUsuario = $soapClient->selectUsuario($usuario);
    
            return $selectUsuario;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }


    public function updateUsuario(String $nombre, String $direccion, String $telefono, String $municipio, String $estado_municipio, String $correo, String $genero, String $usuario, String $contraseña, String $preferencias)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método updateUsuario del servicio web SOAP
            $Actualizar = $soapClient->updateUsuario($nombre, $direccion, $telefono, $municipio, $estado_municipio, $correo, $genero, $usuario, $contraseña, $preferencias);
    
            return $Actualizar;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    public function updateContraseña(String $contraseña, String $correo)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método updateContraseña del servicio web SOAP
            $actualizarClave = $soapClient->updateContraseña($contraseña, $correo);
    
            return $actualizarClave;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    public function misCompras(string $usuario)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método misCompras del servicio web SOAP
            $misCompras = $soapClient->misCompras($usuario);
    
            return $misCompras;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    public function getProductoById($id_producto)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método getProductoById del servicio web SOAP
            $ProductoByID = $soapClient->getProductoById($id_producto);
    
            return $ProductoByID;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    // Método para seleccionar un usuario por su nombre de usuario
    public function selectUsuarios($usuario)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método selectUsuarios del servicio web SOAP
            $selectUsuarios = $soapClient->selectUsuarios($usuario);
    
            return $selectUsuarios;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    // Nuevo dato
    public function buscaridC()
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método buscaridC del servicio web SOAP
            $buscarIdC = $soapClient->buscaridC();
    
            return $buscarIdC;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    } 

    public function buscarProducto(int $producto_id)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método buscarProducto del servicio web SOAP
            $buscarProduct = $soapClient->buscarProducto($producto_id);
    
            return $buscarProduct;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    public function registrarCompra(int $cliente, String $total)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método registrarCompra del servicio web SOAP
            $registroCompra = $soapClient->registrarCompra($cliente, $total);
    
            return $registroCompra;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    //Falta este
    public function registrarDetalle(String $id_venta, string $nombre, string $id_producto, string $cantidad, string $precio, string $id_usuario)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método registrarDetalle del servicio web SOAP
            $registraDetalles = $soapClient->registrarDetalle($id_venta, $nombre, $id_producto, $cantidad, $precio, $id_usuario);
    
            return $registraDetalles;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    } 

    public function verificarProductos($id_usuario)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método verificarProductos del servicio web SOAP
            $veriProduct = $soapClient->verificarProductos($id_usuario);
    
            return $veriProduct;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    public function insertarDetalle(String $nombre, string $cantidad, string $precio, string $total, string $id_producto, string $id_usuario, string $id_cliente)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método registrarCompra del servicio web SOAP
            $insertarDetalles = $soapClient->insertarDetalle($nombre, $cantidad, $precio, $total, $id_producto, $id_usuario, $id_cliente);
    
            return $insertarDetalles;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    public function VaciarCompra($id_cliente)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método VaciarCompra del servicio web SOAP
            $vaciar = $soapClient->VaciarCompra($id_cliente);
    
            return $vaciar;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    public function VaciarTempo()
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método VaciarTempo del servicio web SOAP
            $vaciarTem = $soapClient->VaciarTempo();
    
            return $vaciarTem;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    public function VaciarVenta($fecha)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método VaciarVenta del servicio web SOAP
            $vaciarVenta = $soapClient->VaciarVenta($fecha);
    
            return $vaciarVenta;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    public function ventas($id_cliente)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método ventas del servicio web SOAP
            $Venta = $soapClient->ventas($id_cliente);
    
            return $Venta;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    public function ventasDetalle($idventa)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método ventasDetalle del servicio web SOAP
            $VentaDet = $soapClient->ventasDetalle($idventa);
    
            return $VentaDet;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    public function stockActual($id_producto)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método stockActual del servicio web SOAP
            $stockAct = $soapClient->stockActual($id_producto);
    
            return $stockAct;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

    public function registrarStock(int $cantidad, int $id_producto)
    {
        try {
            $soapClient = new SoapClient(BASE_SOAP);
    
            // Llamada al método registrarStock del servicio web SOAP
            $stockRegis = $soapClient->registrarStock($cantidad, $id_producto);
    
            return $stockRegis;
        } catch (SoapFault $e) {
            // Manejo de errores de SOAP
            return 'Error al consumir el servicio web SOAP: ' . $e->getMessage();
        }
    }

}
