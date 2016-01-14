dd_zindex = 1; var dragged_item = ''; var dd_being_dragged = ''; optlength = 5; varlength = 5;
var dd_user_items = Array(); dd_user_items[1] = Array(); var dd_var_items = Array(); dd_var_items[1] = Array();

dd_user_items[1][1] = ''; dd_var_items[1][1] = 1;
dd_user_items[1][2] = ''; dd_var_items[1][2] = 2;
dd_user_items[1][3] = ''; dd_var_items[1][3] = 3;
dd_user_items[1][4] = ''; dd_var_items[1][4] = 4;
dd_user_items[1][5] = ''; dd_var_items[1][5] = 5;


jQuery.fn.disable = function(){ $(this).addClass('btn1disabled');  $(this).attr("disabled",'disabled'); };
jQuery.fn.enable = function(){ $(this).removeClass('btn1disabled'); $(this).removeAttr("disabled");  };

$(document).ready(function() {
	qid = 1; $('#btn_submit').disable(); 
	$( ".draggable" ).draggable({ containment: "#content",
		revert: "invalid",
		start: function( event, ui ) { $(this).css('z-index',dd_zindex+1); dd_zindex++; dd_being_dragged =  $(this); }
	});
		
	$( ".droppable" ).droppable({
           
		activeClass: "ddactive", hoverClass: "ddhover",
		drop: function( event, ui ) {  $('#glow1').fadeOut();
			var dropped_item_id = $(this).attr('id').split("_"); var dropped_item_type = dropped_item_id[2]; var dropped_item = dropped_item_id[3];	
			var drg_id = $(dd_being_dragged).attr('id').split("_"); var dragged_item = drg_id[3];
			$(dd_being_dragged).position({ my: "center", at: "center", of: "#"+$(this).attr('id') }); 
			prsDrop(dropped_item_type,dropped_item,dragged_item,qid,optlength,varlength);
		}
	
	});
});

function doRevert(dropped) {
    
	if(!$(dropped).attr('id')) {
		tmp = $(dd_being_dragged).attr('id').split('_'); dragged_item = tmp[tmp.length-1];
		for(i=1;i<=optlength;i++) { 
			var found = false; if(dd_user_items[qid][i] == dragged_item) { found = true; }  return found;
		}
	}
	else return false;
}
function prsDrop(dropped_item_type,dropped_item,dragged_item,qid,optlength,varlength) {
	var qtype = 'DD';  
	for(i=1;i<=optlength;i++) if(dd_user_items[qid][i]==dragged_item) dd_user_items[qid][i] = '';
	for(j=1;j<=varlength;j++) if(dd_var_items[qid][j]==dragged_item) dd_var_items[qid][j] = ''; 
	
	i = 1; j = 1;
	if(dropped_item_type=='optcase') {  // user dropped an item to option.. mark the option as answered	
		if(dd_user_items[qid][dropped_item]!='' && dd_user_items[qid][dropped_item]!=undefined && dd_user_items[qid][dropped_item]!='undefined') { 
			$('#ddset_'+qid+'_var_'+dd_user_items[qid][dropped_item]).position({ my: "center", at: "center", of: '#'+'ddset_'+qid+'_varcase_'+dd_user_items[qid][dropped_item] });  
			dd_var_items[qid][dd_user_items[qid][dropped_item]] = dd_user_items[qid][dropped_item];
			
		}
		dd_user_items[qid][dropped_item] = dragged_item; 		
	}
	else if(dropped_item_type=='varcase') { // user dropped an item to var case.. check if dropped item is from options, if so, mark that as unanswered		
		$('#ddset_'+qid+'_var_'+dragged_item).position({ my: "center", at: "center", of: '#'+'ddset_'+qid+'_varcase_'+dragged_item});  
	}
	
	prsNextButton(qid,qtype,optlength);
}

function prsNextButton(qid,qtype,optlength) {
	answered = true;
	for(i=1;i<=optlength;i++) if(dd_user_items[qid][i]=='' || dd_user_items[qid][i]==undefined || dd_user_items[qid][i]=='undefined') { answered = false; break; } 
	
	if(answered==true)  { $('#btn_submit').enable(); }
	else { $('#btn_submit').disable(); }
}
$('#btn_submit').click(function() {
     
        if(!$(this).hasClass('btn1disabled')) {
		//$('.draggable').removeClass('draggable');
		var i = 1; var j = 0; var is_answered = false; var uans = Array(); var is_correct_answer = true;
		if(dd_user_items[1][1] == 4 ) {$('#ddset_1_var_1').addClass('ddcrct'); }
		else { $('#ddset_1_var_1').addClass('ddwrng'); is_correct_answer = false;  }
                
                if(dd_user_items[1][2] == 2 ) $('#ddset_1_var_2').addClass('ddcrct'); 
		else { $('#ddset_1_var_2').addClass('ddwrng'); is_correct_answer = false;  }
                
                if(dd_user_items[1][3] == 5 ) $('#ddset_1_var_3').addClass('ddcrct'); 
		else { $('#ddset_1_var_3').addClass('ddwrng'); is_correct_answer = false; }
                
                if(dd_user_items[1][4] == 3 ) $('#ddset_1_var_4').addClass('ddcrct'); 
		else { $('#ddset_1_var_4').addClass('ddwrng'); is_correct_answer = false; }
                
                if(dd_user_items[1][5] == 1 ) $('#ddset_1_var_5').addClass('ddcrct'); 
		else { $('#ddset_1_var_5').addClass('ddwrng'); is_correct_answer = false;  }
                
		$(this).disable(); 
		if(is_correct_answer==1) { parent.loadAudioTranscript('m3_09_correct','',true,true); $("#correctrdiv").fadeIn('normal');  }
		else { parent.loadAudioTranscript('m3_09_incorrect','',true,true);  $("#incorrectrdiv").fadeIn('normal'); }
		
                $('.resultbg').fadeIn('normal'); 
        }
});

$('#show_me').click(function() {
                var click_count=1;
                
                $('.resultbg').fadeOut('normal'); $('.rdiv').fadeOut('normal'); 
                $('#btn_submit').disable();
                
                dd_user_items[1][1] = ''; dd_user_items[1][2] = ''; dd_user_items[1][3] = ''; dd_user_items[1][4] = ''; dd_user_items[1][5] = ''; 

                for(ii=1;ii<=varlength;ii++) {
                    dd_var_items[1][ii] = ii;
                    $('#ddset_1_var_'+ii).position({ my: "center", at: "center", of: '#'+'ddset_1_varcase_'+ii });  
                }

                $('#ddset_1_var_'+1).position({ my: "center", at: "center", of: '#'+'ddset_1_optcase_5' });
                $('#ddset_1_var_'+2).position({ my: "center", at: "center", of: '#'+'ddset_1_optcase_2' });
                $('#ddset_1_var_'+3).position({ my: "center", at: "center", of: '#'+'ddset_1_optcase_4' });
                $('#ddset_1_var_'+4).position({ my: "center", at: "center", of: '#'+'ddset_1_optcase_1' });
                $('#ddset_1_var_'+5).position({ my: "center", at: "center", of: '#'+'ddset_1_optcase_3' });
               
                
                $("#ddset_1_var_1").draggable({ disabled: true });
                $("#ddset_1_var_2").draggable({ disabled: true });
                $("#ddset_1_var_3").draggable({ disabled: true });
                $("#ddset_1_var_4").draggable({ disabled: true });
                $("#ddset_1_var_5").draggable({ disabled: true });
                });

$('#btn_continue').click(function() {
                parent.gotoPage(26);
                });
