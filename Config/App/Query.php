<?php
class Query extends Conexion {
    private $pdo, $con, $arrvalues, $strquery;

    public function __construct() {
        $this->pdo = new Conexion();
        $this->con = $this->pdo->conect();
    }

    public function select(string $sql, array $params = []) {
        $stmt = $this->con->prepare($sql);
        $stmt->execute($params);
        $data = $stmt->fetch(PDO::FETCH_ASSOC);
        return $data;
    }

    public function selectAll(string $sql, array $params = []) {
        $stmt = $this->con->prepare($sql);
        $stmt->execute($params);
        $data = $stmt->fetchAll(PDO::FETCH_ASSOC);
        return $data;
    }

    public function insert(string $sql, array $params) {
        $stmt = $this->con->prepare($sql);
        $data = $stmt->execute($params);
        if ($data) {
            $res = $this->con->lastInsertId();
        } else {
            $res = 0;
        }
        return $res;
    }

    public function updateS(string $sql, array $params) {
        $stmt = $this->con->prepare($sql);
        $data = $stmt->execute($params);
        return $data;
    }

    public function update(string $query, array $arrvalues) {
        $this->strquery = $query;
        $this->arrvalues = $arrvalues;
        $update = $this->con->prepare($this->strquery);
        $res = $update->execute($this->arrvalues);
        return $res;
    }
    
    public function delete(string $query, array $arrvalues = []) {
        $this->strquery = $query;
        $this->arrvalues = $arrvalues;
        $result = $this->con->prepare($this->strquery);
        if (!empty($this->arrvalues)) {
            $result->execute($this->arrvalues);
        } else {
            $result->execute();
        }
        return $result;
    } 

    // Métodos de transacción
    public function beginTransaction() {
        $this->con->beginTransaction();
    }

    public function commit() {
        $this->con->commit();
    }

    public function rollBack() {
        $this->con->rollBack();
    }


    
}
?>
