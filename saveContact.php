<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $name = htmlspecialchars($_POST["name"]);
    $email = htmlspecialchars($_POST["email"]);
    $message = htmlspecialchars($_POST["message"]);

    $file = "contacts.txt";
    $data = "Name: $name\nEmail: $email\nMessage: $message\n---\n";

    // Save data to the file
    file_put_contents($file, $data, FILE_APPEND);

    // Redirect to a thank-you page or display a success message
    echo "<h2>Thank you for contacting us, $name!</h2>";
    echo "<a href='contact.html'>Go back</a>";
} else {
    echo "<h2>Invalid Request</h2>";
}
?>
