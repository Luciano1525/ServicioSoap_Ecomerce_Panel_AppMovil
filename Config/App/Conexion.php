<?php
class Conexion{
    private $conect;
    public function __construct()
    {
        //Demo
        $pdo = "mysql:host=".HOST.";dbname=".DB.";".CHARSET;

        //Productivvo
		//$pdo = new PDO('mysql:host=45.55.130.73;port=3306;dbname=pasteleria','root','1525');
        try {
            $this->conect = new PDO($pdo, USER, PASS);
            $this->conect->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        } catch (PDOException $e) {
            echo "Error en la conexion".$e->getMessage();
        }
    }
    public function conect()
    {
        return $this->conect;
    }
}
 
?>