/* 
*	CSCI 2999 Capstone Project 
*	Waystone Property Management Intranet
*   Created on : Feb 21, 2018, 10:56:23 AM
*   Author     : KWelzbacher
*/
$(function(){
    $(".dobDate").datepicker({
    	dateFormat: 'yy-mm-dd',
    	changeYear:true,
    	yearRange: "-100:+0",
    	maxDate: 0,
    });
    $("#invDate").on('click', function(){
    	$(this).datepicker({
    		dateFormat: 'yy-mm-dd',
    		changeYear:true,
    		yearRange: "-100:+0",
    		maxDate: 0,
    	});
    });
    
    $(".maintDate").datepicker({
    	dateFormat: 'yy-mm-dd',
    	minDate: 0,
    });
    
    $(".confirm").on('click', function(){
    	return confirm("Are you sure you want to delete Record?");
    });
    
    $(".confirmAccount").on('click', function(){
    	return confirm("Are you sure you want to delete this Account and all of it's Records?");
    });
    $(".confirmTenant").on('click', function(){
    	return confirm("Are you sure you want to delete this Tenant?");
    });
    $(".confirmUnit").on('click', function(){
    	return confirm("Are you sure you want to delete this Unit?");
    });
    $(".confirmUpdate").on('click', function(){
    	return confirm("Are you sure you want to update these changes?");
    });
    $(".rentButton").on('click', function(){
    	return confirm("Are you sure you want to post rent charges for all Units for this month?");
    });
    $(".lateFeeBtn").on('click', function(){
    	return confirm("Are you sure you want to post late fee charges for all Units for this month?");
    });
    $(".start").on('click', function(){
    	return confirm("Are you sure you want to assign this request to yourself?");
    });
    $(".remove").on('click', function(){
    	return confirm("Are you sure you want to remove yourself from this request?");
    });
    $(".done").on('click', function(){
    	return confirm("Are you sure you want to mark this request as finished?");
    });
    $(".finish").on('click', function(){
    	return confirm("Are you sure you want to mark this request as contacted and remove it from the list?");
    });
    $(".delete").on('click', function(){
    	return confirm("Are you sure you want to delete this item from Inventory?");
    });
    
    $.ajax({
    	success: function(response){
    		if(response.success){
    			$("#invDate").datepicker({
    	    		dateFormat: 'yy-mm-dd',
    	    		changeYear:true,
    	    		yearRange: "-100:+0",
    	    		maxDate: 0,
    	    	});
    		}
    	}
    })
    
    
});
function loadFunction(){
	document.getElementById('loader').style.display='block';
	jQuery(function($){
		$(".table-odd").remove();
		$(".table-even").remove();
	
	});
}
function calendar(){
	jQuery(function($){
		$('.datepick').datepicker({
			dateFormat: 'yy-mm-dd',
	    	changeYear:true,
	    	yearRange: "-100:+0",
	    	maxDate: 0,
		});
	});
}

//Getter
var dateFormat = $(".dobDate").datepicker("option", "dateFormat");
//Setter
$(".dobDate").datepicker("option", "dateFormat", "yy-mm-dd");