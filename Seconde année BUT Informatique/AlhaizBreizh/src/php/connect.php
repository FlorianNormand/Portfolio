<?php
// Fichier PHP pour se connecter à la base de données
$pdo = null;
try {
    $pdo = new PDO("pgsql:host=postgresdb;port=5432;dbname=sae;user=sae;password=Phiegoosequ9en9o"); //serveur
} catch (PDOException $e) {
    print "Erreur PDO !: " . $e->getMessage() . "<br/>";
    //die();
}
return $pdo;
