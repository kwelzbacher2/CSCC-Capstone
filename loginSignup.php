<?php include("templates/header.php") ?>
<div class = "logSignModuleWrapper">
  <div class = "logSignModuleContiainer">
    <div class = "logSignButtonsContainer">
      <div id = "loginModuleButton" class = "focused">Login</div>
      <div id = "signupModuleButton" class = "unfocused">Sign Up</div>
    </div>
    <div id = 'loginModule'>
      <script>
        $('#loginForm').on('submit', function (e) {
          e.preventDefault();
          $.ajax({
            type: 'post',
            url: 'formProcess.php',
            data: $('#loginForm').serialize()
          });
          console.log($('#loginForm').serialize());
        });
        $(".loginButton").click(function() {
          if (!$(".loginButton").hasClass("validate")) {
            $(".loginButton").addClass("onclic", 250);
            validateLogin();
          }
        });
        function validateLogin() {
          setTimeout(function() {
            $(".loginButton").removeClass("onclic");
            $(".loginButton").addClass("validate", 450);
          }, 2250);
        }
      </script>
      <div class = "logSignTitle">Login</div>
      <form class = "loginForm" name="loginForm" id="loginForm" action="loginSignup.php" method="post">
        <input type="text" name="email" id="email" placeholder="Email Address" maxlength="64" autofocus>
        <input type="password" name="password" id="password" placeholder="Password" maxlength="32">
        <button type="submit" class = "loginButton"></button>
      </form>
    </div>
  </div>
</div>
<?php include("templates/footer.php") ?>
