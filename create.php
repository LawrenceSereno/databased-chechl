<?php
require "cons.php";  // Database connection

// Function to encrypt the password
function encrypt_password($password) {
    $encryption_key = "your-encryption-key";  // Make sure this is the same key used in login
    $iv = openssl_random_pseudo_bytes(openssl_cipher_iv_length('aes-256-cbc'));
    $encrypted_data = openssl_encrypt($password, 'aes-256-cbc', $encryption_key, 0, $iv);
    return base64_encode($encrypted_data . "::" . $iv);  // Save both the encrypted password and the IV
}

if (!empty($_POST['uname']) && !empty($_POST['pword']) && !empty($_POST['email'])) {
    $uname = $_POST['uname'];
    $pword = $_POST['pword'];
    $email = $_POST['email'];

    // Check if the username or email already exists
    $check_query = "SELECT * FROM user_test WHERE user_uname = '$uname' OR user_email = '$email'";
    $result = mysqli_query($connect, $check_query);

    if (mysqli_num_rows($result) > 0) {
        echo "Exists";  // Username or email already exists
    } else {
        // Encrypt the password before saving
        $encrypted_pword = encrypt_password($pword);

        // Insert the new user into the database
        $insert_query = "INSERT INTO user_test (user_uname, user_pword, user_email) VALUES ('$uname', '$encrypted_pword', '$email')";
        if (mysqli_query($connect, $insert_query)) {
            echo "Success";  // Successfully added user
        } else {
            echo "Error: " . mysqli_error($connect);  // Error in inserting data
        }
    }
} else {
    echo "Field Required";  // If any field is empty
}
?>
