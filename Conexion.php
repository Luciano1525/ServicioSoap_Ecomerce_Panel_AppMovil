<?php 

	class Conexion{
		public static function conectar(){
			try {
				// Demo
				$conexionl = new PDO('mysql:host=localhost:3307;dbname=pasteleria','root','1525');

				//Productivvo
				//$conexion = new PDO('mysql:host=45.55.130.73;port=3306;dbname=pasteleria','root','1525?');
				$conexionl->exec('SET CHARACTER SET utf8');
				$conexionl->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

				return $conexionl;
			} catch (Exception $e) {
				return "ERROR DE CONEXION". $e->getMessage. $e->getLine;
			}
		}

		
		
	}

	?>