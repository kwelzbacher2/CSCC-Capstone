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
    
    
});

//Getter
var dateFormat = $(".dobDate").datepicker("option", "dateFormat");
//Setter
$(".dobDate").datepicker("option", "dateFormat", "yy-mm-dd");