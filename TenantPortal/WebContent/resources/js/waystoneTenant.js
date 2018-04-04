$(function() {
	$(".availDate").datepicker({ dateFormat : 'yy-mm-dd',
		minDate : 0,
		maxDate : "+30d"
	});
	
	$(".dobDate").datepicker({ dateFormat : 'yy-mm-dd',
		maxDate : "-6570d"
	});
});

// Getter
var dateFormat = $(".availDate").datepicker("option", "dateFormat");
// Setter
$(".availDate").datepicker("option", "dateFormat", "yy-mm-dd");

//Getter
var dateFormat = $(".dobDate").datepicker("option", "dateFormat");
// Setter
$(".dobDate").datepicker("option", "dateFormat", "yy-mm-dd");
