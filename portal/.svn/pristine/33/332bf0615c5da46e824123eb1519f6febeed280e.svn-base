var qopts = Array(); i = 0; var session_finished = false;
qopts[i++] = Array('You are about to interview a fresh IT professional. Would you review all his relevant paperwork ahead of time?',1,0);
qopts[i++] = Array('Your day is packed with more than 6 interviews lined up for the day. To make it easier, would you plan to take the documentation to the interview and take it one at a time?  ',2,0);
qopts[i++] = Array('You have received the resume of a nervous candidate who may need some counselling during the mock interview. Would you review the candidate&#8217;s resume thoroughly during the interview. ',2,0);
qopts[i++] = Array('From the information given to you, you are aware that the candidate you are about to interview is not prepared at all for his forthcoming interview. As an interviewer, would you plan sufficient time to address this issue?',1,0);
qopts[i++] = Array('&#8216;A place for everything and everything in its place.&#8217; Do you believe in this?',1,0);


jQuery.fn.disable = function(){ $(this).addClass('btn1disabled');  $(this).attr("disabled",'disabled'); };
jQuery.fn.enable = function(){ $(this).removeClass('btn1disabled'); $(this).removeAttr("disabled");  };

function setupQuestions() {
	cnt = ''; $('#btn_submit').disable(); $('#btn_reset').disable();
	cnt += '<a name="ddtop" style="position:absolute;">&nbsp;</a><table class="dodontset" cellpadding="3" cellspacing="1" align="center" border="0" style="margin:0px;">';
	for(i=0;i<qopts.length;i++) {
		cnt += '<tr class="trclass" valign="middle">';
		cnt += '<td align="left" >'+qopts[i][0]+'</td>';
		cnt += '<td align="center" class="dotd" style="width:80px; margin-left:-2px;"><input class="opt" type="radio" id="opt_'+i+'_1" name="opt_'+i+'" value="1">&nbsp;&nbsp;&nbsp;Yes </td>';
		cnt += '<td align="center" class="donttd" style="width:80px"><input class="opt" type="radio" id="opt_'+i+'_2" name="opt_'+i+'" value="2">&nbsp;&nbsp;&nbsp;No</td>';
		cnt += '</tr>';
	}
	cnt += '</table>';
	
	$('#ddsdiv').html(cnt);
	
}
setupQuestions();

$('.opt').click(function() {
	if($(this).hasClass('disabled')) { } else {
		var tmp = $(this).attr('name').split('_'); var id = tmp[1];
		qopts[id][2] = $(this).val();
		all_answered = true;
		for(i=0;i<qopts.length;i++) {
			if(qopts[i][2]==0) { all_answered = false; break; }	
		}
		if(all_answered) { $('#btn_submit').enable(); }
		else { $('#btn_submit').disable(); }
	}
});

$('#btn_reset').click(function() { 
   	if(!$(this).hasClass('btn1disabled')) {
		for(i=0;i<qopts.length;i++) {
			$('#opt_'+i+'_1').removeAttr('checked');
			$('#opt_'+i+'_2').removeAttr('checked');
			qopts[i][2] = 0;
		} 
		$('#btn_submit').disable(); $(this).disable(); 
	} else e.preventDefault();
});

var result = '';

$('#btn_submit').click(function() {
	if(!$(this).hasClass('btn1disabled')) {
		result = 'pass'; var score = 0;
		for(i=0;i<qopts.length;i++) {
			if(qopts[i][1] == qopts[i][2]) score++;
		} 
		
		if(score >=5) { parent.loadAudioTranscript('module2_l2_correct','',true,true); $('.rbox').html('Well done! You seem to be well prepared to conduct the interview effectively and make the best of the information provided to you. This will help candidates perform better in their actual interview. ');  } 
		else if(score <=5) { parent.loadAudioTranscript('module2_l2_incorrect','',true,true); $('.rbox').html('Uh-oh! Some of your responses indicate that you need to be more prepared to conduct the interview effectively and make the best of the information provided to you. This will help candidates perform better in their actual interview.');   }
		
		$('.resultbg').fadeTo('fast',0.6); $('#resultdiv').fadeIn('normal'); 
		$(this).disable(); session_finished = true;
	} else e.preventDefault();
});

function closeResultDiv() { 
	$('#resultbg').fadeOut('normal'); $('#resultdiv').fadeOut('normal');
	showSolution();
}

