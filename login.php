<?php
require "cons.php";  // Database connection

// Function to decrypt the password
function decrypt_password($encrypted_password) {
    $encryption_key = "your-encryption-key";  // Make sure this is the same key used in registration
    list($encrypted_data, $iv) = explode("::", base64_decode($encrypted_password), 2);  // Split encrypted data and IV
    return openssl_decrypt($encrypted_data, 'aes-256-cbc', $encryption_key, 0, $iv);
}

if (!empty($_POST['uname']) && !empty($_POST['pword'])) {
    $uname = $_POST['uname'];
    $pword = $_POST['pword'];

    // Query the database to find the user by username or email
    $check_query = "SELECT * FROM user_test WHERE user_uname = '$uname' OR user_email = '$uname'";
    $result = mysqli_query($connect, $check_query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);

        // Decrypt the stored password
        $decrypted_password = decrypt_password($row['user_pword']);

        // Verify the password
        if ($pword == $decrypted_password) {
            echo "Login successful";  // If the password matches, login is successful
        } else {
            echo "Invalid Username or Password";  // If password doesn't match
        }
    } else {
        echo "Invalid Username or Password";  // If no user found with that username/email
    }
} else {
    echo "Field Required";  // If any field is empty
}
?>
