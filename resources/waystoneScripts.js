// Scroll Events
$(window).scroll(function(){
  var $distanceTop = $(document).scrollTop();
  var $headerHeight = 100 - ($distanceTop / 2.5);
  var $whitespaceHeight = 60 - ($distanceTop / 2.5);
/*
  var $MSbackgroundPosition = 40 + ($distanceTop / 30);
  var $miamiBackgroundPosition = 40 + ($distanceTop / 30);
  var $newyorkBackgroundPosition = 40 + (($distanceTop - 448) / 40);
  var $chicagoBackgroundPosition = 40 + (($distanceTop - 955) / 50);
  var $vegasBackgroundPosition = 40 + (($distanceTop - 1463) / 30);
  var $losangelesBackgroundPosition = 40 + (($distanceTop - 1957) / 50);

  if ($MSbackgroundPosition >= 90) {
    $MSbackgroundPosition = 90;
  }
  if ($miamiBackgroundPosition >= 90) {
    $miamiBackgroundPosition = 90;
  }
  if ($newyorkBackgroundPosition >= 90) {
    $newyorkBackgroundPosition = 90;
  }
  if ($chicagoBackgroundPosition >= 90) {
    $chicagoBackgroundPosition = 90;
  }
  if ($vegasBackgroundPosition >= 90) {
    $vegasBackgroundPosition = 90;
  }
  if ($losangelesBackgroundPosition >= 90) {
    $losangelesBackgroundPosition = 90;
  }
  $('.MS-Wrapper').css('background-position','50% ' + $MSbackgroundPosition + '%');
  $('.miami-Wrapper').css('background-position','50% ' + $miamiBackgroundPosition + '%');
  if ($distanceTop > 448) {
    $('.newyork-Wrapper').css('background-position','50% ' + $newyorkBackgroundPosition + '%');
    if ($distanceTop > 955) {
      $('.chicago-Wrapper').css('background-position','50% ' + $chicagoBackgroundPosition + '%');
      if ($distanceTop > 1463) {
        $('.vegas-Wrapper').css('background-position','50% ' + $vegasBackgroundPosition + '%');
        if ($distanceTop > 1957) {
          $('.losAngeles-Wrapper').css('background-position','50% ' + $losangelesBackgroundPosition + '%');
        }
      }
    }
  }
*/
  $('header').css('height',$headerHeight);
  $('.navWhitespace').css('height',$whitespaceHeight);
  $('.logsignWhitespace').css('height',$whitespaceHeight);
});
$(document).ready(function() {
  $('#contactForm').on('submit', function (e) {
    e.preventDefault();
    $.ajax({
      type: 'post',
      url: 'formProcess.php',
      data: $('#contactForm').serialize()
    });
    console.log($('#contactForm').serialize());
  });

  $('#signupForm').on('submit', function (e) {
    e.preventDefault();
    $.ajax({
      type: 'post',
      url: 'formProcess.php',
      data: $('#signupForm').serialize()
    });
    console.log($('#signupForm').serialize());
  });
  var $loginModule = "<div id = 'loginModule'><script>$('#loginForm').on('submit', function (e) {e.preventDefault();$.ajax({type: 'post',url: 'formProcess.php',data: $('#loginForm').serialize()});console.log($('#loginForm').serialize());});$('.loginButton').click(function() {if (!$('.loginButton').hasClass('validate')) {$('.loginButton').addClass('onclic', 250);validateLogin();}});function validateLogin() {setTimeout(function() {$('.loginButton').removeClass('onclic');$('.loginButton').addClass('validate', 450);}, 2250);}</script><div class = 'logSignTitle'>Login</div><form class = 'loginForm' name='loginForm' id='loginForm' action='loginSignup.php' method='post'><input type='text' name='email' id='email' placeholder='Email Address' maxlength='64'><input type='password' name='password' id='password' placeholder='Password' maxlength='32'><button type='submit' class = 'loginButton'></button></form></div>";
  var $signupModule = "<div id = 'signupModule'><div class = 'logSignTitle'>Sign Up</div><form class = 'signupForm' name='signupForm' id='signupForm' action='loginSignup.php' method='post'><div class = 'signupFormInputs'><input type='text' name='firstName' id='fName' placeholder='First Name' maxlength='32'><input type='text' name='middleInitial' id='mIni' placeholder='MI' maxlength='1'><input type='text' name='lastName' id='lName' placeholder='Last Name' maxlength='32'></div><div class='signupFormInputs'><input type='text' name='email' id='email' placeholder='Email Address' maxlength='64'><input type='text' name='confEmail' id='confEmail' placeholder='Confirm Email' maxlength='64'></div><div class='signupFormInputs'><input type='password' name='password' id='pass' placeholder='Password' maxlength='32'><input type='password' name='confPassword' id='confPass' placeholder='Confirm Password' maxlength='32'></div><button type='submit' class = 'signupButton'></button></form></div>";
  $("#loginModuleButton").click(function() {
    if (!$("#loginModuleButton").hasClass("focused")) {
      $("#signupModule").remove();
      $(".logSignButtonsContainer").after($loginModule);
      $(".logSignModuleContiainer").css("width","400px");
      $("#loginModuleButton").removeClass("unfocused");
      $("#signupModuleButton").removeClass("focused");
      $("#loginModuleButton").addClass("focused");
      $("#signupModuleButton").addClass("unfocused");
    }
  });
  $("#signupModuleButton").click(function() {
    if (!$("#signupModuleButton").hasClass("focused")) {
      $("#loginModule").remove();
      $(".logSignButtonsContainer").after($signupModule);
      $(".logSignModuleContiainer").css("width","600px");
      $("#signupModuleButton").removeClass("unfocused");
      $("#loginModuleButton").removeClass("focused");
      $("#signupModuleButton").addClass("focused");
      $("#loginModuleButton").addClass("unfocused");
    }
  });

  $(".signupButton").click(function() {
    if (!$(".signupButton").hasClass("validate")) {
      $(".signupButton").addClass("onclic", 250);
      validateSignup();
    }
  });
  function validateSignup() {
    setTimeout(function() {
      $(".signupButton").removeClass("onclic");
      $(".signupButton").addClass("validate", 450);
    }, 2250);
  }
  $(".submitButton").click(function() {
    if (!$(".submitButton").hasClass("validate")) {
      $(".submitButton").addClass("onclic", 250);
      validateContact();
    }
  });
  function validateContact() {
    setTimeout(function() {
      $(".submitButton").removeClass("onclic");
      $(".submitButton").addClass("validate", 450);
    }, 2250);
  }
  $("#DOB").datepicker();
});
