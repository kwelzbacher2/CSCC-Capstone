// Scroll Events
$(window).scroll(function(){
  var $distanceTop = $(document).scrollTop();
  var $headerHeight = 100 - ($distanceTop / 2.5);
  var $whitespaceHeight = 60 - ($distanceTop / 2.5);

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
  $('header').css('height',$headerHeight);
  $('.navWhitespace').css('height',$whitespaceHeight);
  $('.logsignWhitespace').css('height',$whitespaceHeight);
});
