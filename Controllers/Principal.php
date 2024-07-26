<?php
/*
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;
*/

class Principal extends Controller
{
    public function __construct()
    {
        parent::__construct(); // Llama al constructor de la clase base
        session_start();
    }
    public function inicio()
    {
        $data['title'] = 'Inicio';
        $data['categorias'] = $this->model->getCategorias();
        $data['nuevoProductos'] = $this->model->getNuevosProductos();
        $this->views->getView('principal', "inicio", $data);
    }

    public function register()
    {
        $data['title'] = 'Registro';
        $this->views->getView('principal', "register", $data);
    }

    //Vista About
    public function about()
    {
        $data['title'] = 'Nuestro Equipo';
        $this->views->getView('principal', "about", $data);
    }

    //Vista Tienda
    public function shop($page)
    {
        $pagina = (empty($page)) ? 1 : $page;
        $porPagina = 9;
        $desde = ($pagina - 1) * $porPagina;
        $data['title'] = 'Nuestros Productos';
        $data['productos'] = $this->model->getProductos($desde, $porPagina);
        $data['pagina'] = $pagina;
        $total = $this->model->getTotalProductos();
        $data['total'] = ceil($total['total'] / $porPagina);
        $this->views->getView('principal', "shop", $data);
    }

    //Vista Detalles
    public function shopsingle($id_producto)
    {
        $data['producto'] = $this->model->getProducto($id_producto);
        if ($data['producto']) {
            $id_categoria = $data['producto']['categoria_id'];
            $data['relacionados'] = $this->model->getAleatorios($id_categoria, $data['producto']['id']);
            $data['title'] = $data['producto']['nombre'];
            $this->views->getView('principal', "shopsingle", $data);
        } else {
            echo "Producto no encontrado";
        }
    }


    //Vista Categorias
    public function categorias($datos)
    {
        $id_categoria = 1;
        $page = 1;
        $array = explode(',', $datos);
        if (isset($array[0])) {
            if (!empty($array[0])) {
                $id_categoria = $array[0];
            }
        }

        if (isset($array[1])) {
            if (!empty($array[1])) {
                $page = $array[1];
            }
        }

        $pagina = (empty($page)) ? 1 : $page;
        $porPagina = 3;
        $desde = ($pagina - 1) * $porPagina;
        $data['pagina'] = $pagina;
        $total = $this->model->getTotalProductosCat($id_categoria);
        $data['total'] = ceil($total['total'] / $porPagina);
        $data['productos'] = $this->model->getProductosCat($id_categoria, $desde, $porPagina);
        $data['title'] = 'Categorias';
        $data['id_categoria'] = $id_categoria;
        $this->views->getView('principal', "categorias", $data);
    }

    //Vista Contacto
    public function contact()
    {
        $data['title'] = 'Contactos';
        $this->views->getView('principal', "contact", $data);
    }

    //Vista Deseos
    public function deseo()
    {
        $data['title'] = 'Tu Lista de Deseos';
        $this->views->getView('principal', "deseo", $data);
    }

    //Vista de Compra
    public function compra()
    {
        $data['title'] = 'Confirmar Compra';
        $this->views->getView('principal', "compra", $data);
    }


    // Vista Clientes
    public function micuenta()
    {
        // Verificar si el usuario está logueado
        if (!isset($_SESSION['usuario'])) {
            header("Location: " . BASE_URL . "Home/index");
            exit();
        }

        // Obtener los datos del usuario
        $usuario = $_SESSION['usuario'];
        $data['usuario'] = $this->model->selectUsuario($usuario);
        $data['title'] = 'Usuario';

        // Pasar los datos a la vista
        $this->views->getView('principal', "micuenta", $data);
    }

    // Vista Compras
    public function miscompras()
    {
        // Verificar si el usuario está logueado
        if (!isset($_SESSION['usuario'])) {
            header("Location: " . BASE_URL . "Home/index");
            exit();
        }

        $usuario = $_SESSION['usuario'];
        $data['title'] = 'Mis Compras';
        $data['compras'] = $this->model->misCompras($usuario);

        // Pasar los datos a la vista
        $this->views->getView('principal', "miscompras", $data);
    }

    public function listaDeseo()
    {
        $datos = file_get_contents('php://input');
        $json = json_decode($datos, true);
        $array['productos'] = array();
        foreach ($json as $producto) {
            $result = $this->model->getProducto($producto['idProducto']);
            $data['id'] =  $result['id'];
            $data['nombre'] =  $result['nombre'];
            $data['precio'] =  $result['precio'];
            $data['cantidad'] =  $producto['cantidad'];
            $data['imagen'] =  $result['imagen'];
            array_push($array['productos'], $data);
        }
        $array['moneda'] = MONEDA;
        $array['base_url_img'] = BASE_URL_IMG; // Agregar la URL base de las imágenes
        echo json_encode($array, JSON_UNESCAPED_UNICODE);
        die();
    }

    //Obtener Productos a Partir de la Lista Carrito
    public function listaCarrito()
    {
        $datos = file_get_contents('php://input');
        $json = json_decode($datos, true);
        $array['productos'] = array();
        $total = 0.00;
        foreach ($json as $producto) {
            $result = $this->model->getProducto($producto['idProducto']);
            $data['id'] =  $result['id'];
            $data['nombre'] =  $result['nombre'];
            $data['precio'] =  $result['precio'];
            $data['cantidad'] =  $producto['cantidad'];
            $data['imagen'] =  $result['imagen'];
            $subTotal = $result['precio'] * $producto['cantidad'];
            $data['subTotal'] =  number_format($subTotal, 2);
            array_push($array['productos'], $data);
            $total += $subTotal;
        }
        $array['total'] = number_format($total, 2);
        $array['moneda'] = MONEDA;
        $array['base_url_img'] = BASE_URL_IMG; // Agregar la URL base de las imágenes
        echo json_encode($array, JSON_UNESCAPED_UNICODE);
        die();
    }

    // Método de registro de clientes
    public function registrar()
    {
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            // Filtrar y validar los datos de entrada
            $nombre_completo = filter_input(INPUT_POST, 'nombre_completo', FILTER_SANITIZE_STRING);
            $direccion = filter_input(INPUT_POST, 'direccion', FILTER_SANITIZE_STRING);
            $telefono = filter_input(INPUT_POST, 'telefono', FILTER_SANITIZE_STRING);
            $municipio = filter_input(INPUT_POST, 'municipio', FILTER_SANITIZE_STRING);
            $estado = 'Yucatán';
            $correo = filter_input(INPUT_POST, 'correo', FILTER_SANITIZE_EMAIL);
            $genero = filter_input(INPUT_POST, 'genero', FILTER_SANITIZE_STRING);
            $usuario = filter_input(INPUT_POST, 'usuario', FILTER_SANITIZE_STRING);
            $contrasena = $_POST['contrasena']; // Guardar la contraseña sin hash

            if (isset($_POST['interes']) && is_array($_POST['interes'])) {
                $preferencias = implode(' ', $_POST['interes']);
            } else {
                $preferencias = '';
            }

            // Verificar si el nombre de usuario ya existe
            $usuarioExistente = $this->model->verificarUsuarioExistente($usuario);
            if ($usuarioExistente) {
                header('Content-Type: application/json');
                echo json_encode(['success' => false, 'message' => 'El nombre de usuario ya está registrado. Por favor, elija otro.']);
                exit;
            }

            // Verificar si el correo electrónico ya existe
            $correoExistente = $this->model->verificarCorreoExistente($correo);
            if ($correoExistente) {
                header('Content-Type: application/json');
                echo json_encode(['success' => false, 'message' => 'El correo electrónico ya está registrado. Por favor, elija otro.']);
                exit;
            }

            // Generar un código RUC único de 6 dígitos
            do {
                $ruc = random_int(100000, 999999);
                $rucExistente = $this->model->verificarRucExistente($ruc);
            } while ($rucExistente);

            $resultado = $this->model->insertarCliente($ruc, $nombre_completo, $direccion, $telefono, $municipio, $estado, $correo, $genero, $usuario, $contrasena, $preferencias, $estado);

            header('Content-Type: application/json');
            if ($resultado) {
                echo json_encode(['success' => true, 'message' => '¡Registro Exitoso!']);
            } else {
                echo json_encode(['success' => false, 'message' => 'Error al registrar el cliente.']);
            }
            exit;
        }
    }




    // Método login
    public function login()
    {
        if (empty($_POST["usuario"]) || empty($_POST["password"])) {
            echo "Los Campos Están Vacíos";
        } else {
            $usuario = $_POST['usuario'];
            $password = $_POST['password'];

            // Obtener los datos del cliente desde la base de datos
            $data = $this->model->selectUsuario($usuario);

            // Verificar si se encontró un usuario con ese nombre de usuario
            if ($data) {
                // Comparar la contraseña ingresada con la contraseña almacenada en la base de datos
                if ($password === $data['contraseña']) {
                    // Iniciar sesión y guardar el nombre de usuario en una cookie
                    $_SESSION['usuario'] = $usuario;
                    setcookie('usuario', $usuario, time() + (86400 * 30), "/"); // Cookie válida por 30 días
                    header("Location: " . BASE_URL . "principal/inicio");
                    exit();
                } else {
                    $error = 0;
                    header("Location: " . BASE_URL . "?msg=$error");
                    exit();
                }
            } else {
                $error = 0;
                header("Location: " . BASE_URL . "?msg=$error");
                exit();
            }
        }
    }

    public function cerrarSesion()
    {
        // Borrar la cookie del usuario
        setcookie('usuario', '', time() - 3600, "/");

        // Destruir la sesión
        session_start();
        session_destroy();

        // Redirigir al usuario a la página de inicio de sesión
        header("Location: " . BASE_URL . "Home/index");
        exit();
    }


    public function actualizarCuenta()
    {
        // Verificar si el usuario está logueado
        if (!isset($_SESSION['usuario'])) {
            header("Location: " . BASE_URL . "Home/index");
            exit();
        }

        // Obtener los datos del formulario
        $nombre = $_POST['nombre'];
        $direccion = $_POST['direccion'];
        $telefono = $_POST['telefono'];
        $municipio = $_POST['municipio'];
        $estado_municipio = 'Yucatán'; // No editable y siempre 'Yucatán'
        $correo = $_POST['correo'];
        $genero = $_POST['genero'];
        $usuario = $_POST['usuario'];
        $contraseña = $_POST['contraseña']; // Cambiado de 'contrasena' a 'contraseña'
        $preferencias = implode(' ', $_POST['preferencias']); // Convertir array a cadena

        $actualizar = $this->model->updateUsuario($nombre, $direccion, $telefono, $municipio, $estado_municipio, $correo, $genero, $usuario, $contraseña, $preferencias);
        if ($actualizar == 1) {
            // Redireccionar a micuenta.php después de la actualización exitosa
            header("Location: " . BASE_URL . "principal/micuenta");
            exit();
        } else {
            // Manejar el error o mostrar un mensaje de error
            $alert = 'error';
        }
    }


    public function RecuperarContraseña()
    {
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            if (isset($_POST['verificar'])) {
                $correo = $_POST['correo'];

                // Verificar si el correo existe en la base de datos
                if ($this->model->verificarCorreoExistente($correo)) {
                    // Generar código aleatorio de 4 dígitos
                    $codigo = rand(1000, 9999);

                    // Guardar el código en la sesión
                    $_SESSION['correo_recuperacion'] = $correo;
                    $_SESSION['codigo_recuperacion'] = $codigo;

                    echo json_encode(array('success' => true, 'codigo' => $codigo));
                } else {
                    echo json_encode(array('success' => false, 'message' => 'El correo no está registrado.'));
                }
                exit();
            }
        }
    }

    public function verificarCodigo()
    {
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            if (isset($_POST['codigo']) && isset($_POST['correo'])) {
                $codigoIngresado = $_POST['codigo'];
                $correo = $_POST['correo'];

                // Verificar si el código ingresado es correcto
                if (isset($_SESSION['codigo_recuperacion']) && $_SESSION['codigo_recuperacion'] == $codigoIngresado && $_SESSION['correo_recuperacion'] == $correo) {
                    echo json_encode(array('success' => true));
                } else {
                    echo json_encode(array('success' => false, 'message' => 'Código de verificación incorrecto.'));
                }
                exit();
            }
        }
    }


    // Vista Contraseña
    public function contraseña()
    {
        $data['title'] = 'Cambiar Comtraseña';
        // Pasar los datos a la vista
        $this->views->getView('principal', "contraseña", $data);
    }

    public function actualizarContraseña()
    {
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            // Obtener los datos del formulario
            $contraseña = $_POST['nuevaContraseña'] ?? null;
            $confirmarContraseña = $_POST['confirmarContraseña'] ?? null;
            $correo = $_POST['correo'] ?? null;

            if ($contraseña && $confirmarContraseña && $correo) {
                if ($contraseña === $confirmarContraseña) {
                    $actualizar = $this->model->updateContraseña($contraseña, $correo);
                    if ($actualizar == 1) {
                        // Redireccionar a micuenta.php después de la actualización exitosa
                        header("Location: " . BASE_URL . "Home/index");
                        exit();
                    } else {
                        // Manejar el error o mostrar un mensaje de error
                        $alert = 'error';
                    }
                } else {
                    // Manejar el error si las contraseñas no coinciden
                    $alert = 'error';
                }
            } else {
                // Manejar el error si falta algún dato
                $alert = 'error';
            }
        }
    }


    // Método para registrar la compra
    public function compras()
    {
        // Verificar si el usuario está logueado
        if (!isset($_SESSION['usuario'])) {
            header("Location: " . BASE_URL . "Home/index");
            exit();
        }
        
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $id_producto = filter_input(INPUT_POST, 'idProducto', FILTER_SANITIZE_NUMBER_INT);
            $cantidad = filter_input(INPUT_POST, 'cantidad', FILTER_SANITIZE_NUMBER_INT);

            // Recuperar el usuario de la sesión
            $usuario = isset($_SESSION['usuario']) ? $_SESSION['usuario'] : null;

            // Obtener el cliente
            $cliente = $this->model->selectUsuarios($usuario);
            $id_cliente = $cliente['id'];

            if (!$id_producto || !$cantidad) {
                echo json_encode(['success' => false, 'message' => 'Datos de producto inválidos']);
                return;
            }

            $producto = $this->model->getProductoById($id_producto);
            if (!$producto) {
                echo json_encode(['success' => false, 'message' => 'Producto no encontrado']);
                return;
            }

            //Obtener Producto
            $product = $this->model->buscarProducto($id_producto);
            $nombre = $product['nombre'];
            $precio = $product['precio'];
            $total = $precio * $cantidad;
            $id = $product['id'];
            $id_usuario = 1;

            $insert = $this->model->insertarDetalle($nombre, $cantidad, $precio, $total, $id, $id_usuario, $id_cliente);

            if ($id_cliente == 0 || $id_cliente == "") {
                # code...
                $this->model->registrarCompra(1, $total);
            }else{
                $this->model->registrarCompra($id_cliente, $total);
            } 

            if ($insert) {
                echo json_encode(['success' => true, 'message' => '¡Registro Exitoso!']);
            } else {
                echo json_encode(['success' => false, 'message' => 'Error al registrar el cliente.']);
            }

            // Guardar los datos del producto en cookies
            setcookie('producto_id', $producto['id'], time() + 3600, "/");
            setcookie('producto_nombre', $producto['nombre'], time() + 3600, "/");
            setcookie('producto_precio', $producto['precio'], time() + 3600, "/");
            setcookie('producto_cantidad', $cantidad, time() + 3600, "/");

            // Redirigir al usuario a la página de compra
            header('Location: ' . BASE_URL . 'principal/compra');
            exit();
        }
    }

    // Método para finalizar la compra
    public function finalizarCompra()
    {
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $cardNumber = filter_input(INPUT_POST, 'card-number', FILTER_SANITIZE_STRING);
            $cardExpiryMonth = filter_input(INPUT_POST, 'card-expiry-month', FILTER_SANITIZE_STRING);
            $cardExpiryYear = filter_input(INPUT_POST, 'card-expiry-year', FILTER_SANITIZE_STRING);
            $cardCVC = filter_input(INPUT_POST, 'card-cvc', FILTER_SANITIZE_STRING);

            if (empty($cardNumber) || empty($cardExpiryMonth) || empty($cardExpiryYear) || empty($cardCVC)) {
                echo "Datos de la tarjeta inválidos";
                return;
            }

            // Recuperar el usuario de la sesión
            $usuarioCliente = isset($_SESSION['usuario']) ? $_SESSION['usuario'] : null;
            $usuarioClient = $_POST['usuario'];

            if (!$usuarioCliente) {
                echo json_encode(['success' => false, 'message' => 'Usuario no encontrado']);
                return;
            }

                $usuario = 1;
                $productos = $this->model->verificarProductos($usuario);
                if ($productos["estatus"] == "success") {
                    $nombre = $productos['nombre'];
                    $cantidad = $productos['cantidad'];
                    $precio = $productos['precio'];
                    $id_producto = $productos['id_producto'];
                    $id_usuario = $productos['id_usuario'];

                    $data = $this->model->buscaridC();
                    $result = $data['MAX(id)'];
                    
                    foreach ($productos as $data) {
                        $insertar = $this->model->registrarDetalle($result, $nombre, $id_producto, $cantidad, $precio, $id_usuario);
                    }
                    
            } else {
                echo json_encode(['success' => false, 'message' => 'Datos del Producto no recuperado']);
            }

        }
        
    }

    public function Vaciar(){
        $usuarioCliente = isset($_SESSION['usuario']) ? $_SESSION['usuario'] : null;
        $cliente = $this->model->selectUsuarios($usuarioCliente);
        $id_cliente = $cliente['id'];
        $this->model->VaciarCompra($id_cliente);

        //Recuperar el id de la venta
        $datos = $this->model->ventas($id_cliente);
        $idventa = $datos['id'];

        $datos1 = $this->model->ventasDetalle($idventa);
        $id_producto = $datos1['id_producto'];
        $cantidad = $datos1['cantidad'];
        
        foreach ($datos1 as $datos) {
            $stock = $this->model->stockActual($id_producto);
            $stockActual = $stock['cantidad'];
            $this->model->registrarStock($stockActual - $cantidad, $id_producto);
        }

    }
    public function Cancelar(){
        $usuarioCliente = isset($_SESSION['usuario']) ? $_SESSION['usuario'] : null;
        $cliente = $this->model->selectUsuarios($usuarioCliente);
        $id_cliente = $cliente['id'];
        $datos = $this->model->ventas($id_cliente);
        $fecha = $datos['fecha'];
        $this->model->VaciarVenta($fecha);
        
        $this->model->VaciarTempo();
        header("Location: " . BASE_URL . "principal/inicio");
    }
    
    
}
