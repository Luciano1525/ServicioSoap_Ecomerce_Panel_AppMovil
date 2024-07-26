<?php
class Productos extends Controllers
{
    public function __construct()
    {
        session_start();
        if (empty($_SESSION['activo'])) {
            header("location: " . base_url());
        }
        parent::__construct();
    }
    public function Listar()
    {
        $data = $this->model->selectProductos();
        $this->views->getView($this, "Listar", $data, "");
    }
    public function eliminados()
    {
        $data = $this->model->selectProductosInactivos();
        $this->views->getView($this, "Eliminados", $data, "");
    }
    public function insertar()
{
    $codigo = $_POST['codigo'];
    $nombre = $_POST['nombre'];
    $precio = floatval($_POST['precio']); // Convertir a tipo float
    $categoria = intval($_POST['categoria']); // Convertir a entero
    $descripcion = $_POST['descripcion'];

    // Manejo de la subida de imagen
    $imagenNombre = $_FILES['imagen']['name'];
    $imagenTemp = $_FILES['imagen']['tmp_name'];
    $imagenPath = "Assets/img/uploads/" . $imagenNombre;

    if (move_uploaded_file($imagenTemp, $imagenPath)) {
        $imagen = "Assets/img/uploads/" . $imagenNombre; // Ruta relativa para almacenar en la base de datos
        $insert = $this->model->insertarProductos($codigo, $nombre, $precio, $imagen, $categoria, $descripcion);
        if ($insert > 0) {
            $alert = 'registrado';
        } else if ($insert == 'existe') {
            $alert = 'existe';
        } else {
            $alert = 'error';
        }
    } else {
        $alert = 'error_imagen';
    }

    $this->model->selectProductos();
    header("location: " . base_url() . "Productos/Listar?msg=$alert");
    die();
}




    public function editar()
    {
        $id = $_GET['id'];
        $data = $this->model->editarProductos($id);
        if ($data == 0) {
            $this->Listar();
        } else {
            $this->views->getView($this, "Editar", $data);
        }
    }
    public function actualizar() {
        $id = $_POST['id'];
        $codigo = $_POST['codigo'];
        $nombre = $_POST['nombre'];
        $cantidad = intval($_POST['cantidad']);
        $precio = floatval($_POST['precio']);
        $categoria = intval($_POST['categoria']); // Asegurarse de que la categoría sea un número entero
        $descripcion = $_POST['descripcion'];
    
        // Manejo de la subida de imagen
        if (isset($_FILES['imagen']) && $_FILES['imagen']['error'] == 0) {
            $imagenNombre = $_FILES['imagen']['name'];
            $imagenTemp = $_FILES['imagen']['tmp_name'];
            $imagenPath = "Assets/img/uploads/" . $imagenNombre;
    
            if (move_uploaded_file($imagenTemp, $imagenPath)) {
                $imagen = "Assets/img/uploads/" . $imagenNombre; // Ruta relativa para almacenar en la base de datos
            } else {
                $alert = 'error_imagen';
                header("location: " . base_url() . "Productos/Listar?msg=$alert");
                die();
            }
        } else {
            // Si no se sube una nueva imagen, mantener la actual
            $producto = $this->model->selectProducto($id);
            $imagen = $producto['imagen'];
        }
    
        $actualizar = $this->model->actualizarProductos($codigo, $nombre, $cantidad, $precio, $imagen, $categoria, $descripcion, $id);
        if ($actualizar) {
            $alert = 'modificado';
        } else {
            $alert = 'error';
        }
    
        header("location: " . base_url() . "Productos/Listar?msg=$alert");
        die();
    }
    
    



    public function eliminar()
    {
        $id = $_GET['id'];
        $eliminar = $this->model->eliminarProductos($id);
        $data = $this->model->selectProductos();
        header('location: ' . base_url() . 'Productos/Listar');
        //$this->views->getView($this, "Listar", $data, $eliminar);
        die();
    }
    public function reingresar()
    {
        $id = $_GET['id'];
        $this->model->reingresarProductos($id);
        $data = $this->model->selectProductos();
        header('location: ' . base_url() . 'Productos/Listar');
        //$this->views->getView($this, "Listar", $data);
        die();
    }
}
?>