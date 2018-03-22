/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function(){
    $(".dobDate").datepicker({
    	dateFormat: 'yy-mm-dd',
    	changeYear:true,
    	yearRange: "-100:+0"
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
    
    
});

//Getter
var dateFormat = $(".dobDate").datepicker("option", "dateFormat");
//Setter
$(".dobDate").datepicker("option", "dateFormat", "yy-mm-dd");