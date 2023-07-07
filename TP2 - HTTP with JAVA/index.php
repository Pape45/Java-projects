<!DOCTYPE html>
<html>
<head>
    <title>Formulaire de connexion</title>
</head>
<body>
    <h2>Formulaire de connexion</h2>
    <form method="POST" action="">
        <label for="login">Login :</label>
        <input type="text" id="login" name="login" required><br><br>

        <label for="password">Mot de passe :</label>
        <input type="password" id="password" name="password" required><br><br>

        <input type="submit" value="Se connecter">
    </form>

    <?php
// Vérifier les informations de connexion après la soumission du formulaire
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $login = $_POST['login'];
        $password = $_POST['password'];

        // Vérifier les informations de connexion
        if ($login === 'admin' && $password === 'aa') {
            echo '<p>Connexion réussie !</p>';
            http_response_code(200); // Code de succès (200)
        } else {
            echo '<p>Identifiants incorrects. Veuillez réessayer.</p>';
            http_response_code(403); // Accès refusé (403)
        }
    }
?>

</body>
</html>
