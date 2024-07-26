<?php
class Clientes extends Controllers
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
        $data = $this->model->selectClientes();
        $this->views->getView($this, "Listar", $data, "");
    }
    public function Cliente()
    {
        $data = $this->model->selectClientes();
        $this->views->getView($this, "Cliente", $data, "");
    }
    public function eliminados()
    {
        $data = $this->model->selectClientesInactivos();
        $this->views->getView($this, "Eliminados", $data, "");
    }
    public function insertar()
    {
        $ruc = $_POST['ruc'];
        $nombre = $_POST['nombre'];
        $usuario = $_POST['usuario'];
        $telefono = $_POST['telefono'];
        $direccion = $_POST['direccion'];
        $insert = $this->model->insertarClientes($ruc, $nombre, $usuario, $telefono, $direccion);
        if ($insert > 0) {
            $alert = 'registrado';
        } else if ($insert == 'existe') {
            $alert = 'existe';
        } else {
            $alert = 'error';
        }
        $this->model->selectClientes();
        header("location: " . base_url() . "Clientes/Listar?msg=$alert");
        die();
    }

    public function editar()
    {
        $id = $_GET['id'];
        $data = $this->model->editarClientes($id);
        if ($data == 0) {
            $this->Listar();
        } else {
            $this->views->getView($this, "Editar", $data);
        }
    }
    public function actualizar()
    {
        $id = $_POST['id'];
        $ruc = $_POST['ruc'];
        $nombre = $_POST['nombre'];
        $direccion = $_POST['direccion'];
        $telefono = $_POST['telefono'];
        $municipio = $_POST['municipio'];
        $estado_municipio = 'Yucatán'; // No editable y siempre 'Yucatán'
        $correo = $_POST['correo'];
        $genero = $_POST['genero'];
        $usuario = $_POST['usuario'];
        $contraseña = $_POST['contraseña'];
        $preferencias = $_POST['preferencias'];
        $estado = $_POST['estado'];

        $actualizar = $this->model->actualizarClientes($ruc, $nombre, $direccion, $telefono, $municipio, $estado_municipio, $correo, $genero, $usuario, $contraseña, $preferencias, $estado, $id);
        if ($actualizar == 1) {
            $alert = 'modificado';
        } else {
            $alert = 'error';
        }
        header("location: " . base_url() . "Clientes/Listar?msg=$alert");
        die();
    }


    public function eliminar()
    {
        $id = $_GET['id'];
        $this->model->eliminarClientes($id);
        header("location: " . base_url() . "Clientes/Listar");
        die();
    }
    public function reingresar()
    {
        $id = $_GET['id'];
        $this->model->reingresarClientes($id);
        $data = $this->model->selectClientes();
        header("location: " . base_url() . "Clientes/Listar");
        die();
    }
    public function buscar()
    {
        $ruc = $_POST['ruc'];
        $data = $this->model->BuscarCliente($ruc);
        echo json_encode($data);
    }


    public function verCliente($id)
{
    $cliente = $this->model->selectClienteById($id);

    $this->views->getView($this, "Cliente", $cliente, "");
}

public function Graficar($id)
{
    // Obtener los datos
    $Comprados = $this->model->selectProductosComprados($id);
    $Frecuencia = $this->model->selectFrecuenciaCompra($id);
    $Preferencias = $this->model->selectProductosPreferidos($id);

    // Preparar los datos para la vista
    $data['title'] = 'Análisis de Datos';
    $data['Comprados'] = $Comprados;
    $data['Frecuencia'] = $Frecuencia;

    // Convertir las preferencias en columnas
    $Preferencia = [];
    foreach ($Preferencias as $pref) {
        $Preferencia[] = ['preferencia' => $pref];
    }
    $data['Preferencia'] = $Preferencia;

    // Convertir los datos a JSON y devolverlos
    echo json_encode($data);
    die();
}




}
