var xml_file = 'assessment.xml'; // update the xml file path here
var as_xml; var as_content; var as_config; var as_questions; var as_time; var total_questions = 0; var current_question = 0; var current_question_xml; var timer_min; 
var timer_sec = 0; var session_started = false; var session_finished = false; var end_reached = false; var student_name = '';  var num_attempts = 0; var max_attempts;
var user_answers_data = Array(); var user_answers = Array(); var dd_being_dragged = '';  var dd_user_items = Array(); var dd_var_items = Array(); var dd_zindex = 100;
var question_data = Array(); 
$(document).ready(function() {
	$('#mtable_intro').fadeIn('fast');
});

$('#btn_start1').click(function() {  $('#main_div').fadeOut(); $('.resultbg').fadeIn(); $('#instruction_msg_box').fadeIn();});
$('#btn_start').click(function() { startSession(); $('#widget-header').fadeIn();  $('#widget-footer').fadeIn(); $('#instruction_msg_box').fadeOut(); $('.resultbg').fadeOut(); });

jQuery.fn.disable = function(btn){ $(this).addClass(btn+'disabled');  $(this).attr("disabled",true); };
jQuery.fn.enable = function(btn){ $(this).removeClass(btn+'disabled'); $(this).attr("disabled",false); };


function setupSession(restarted) {	
	$('#weldiv').show(); 
	$.ajax({ type: "GET", url: xml_file, dataType: "xml", success: function(xml) { 
		as_xml = xml; 
		
		as_config = $(as_xml).find('config'); as_content = $(as_xml).find('textualContent'); as_time = $(as_config).find('totalTimeAllowed').text(); 
		var course_title = $(as_content).find('asTitle').text(); $('#page_title').html(course_title); 
		$('#qtimer_minutes').html(as_time); max_attempts = parseInt($(as_config).find('numberOfAttempts').text()); 
		
		$('#intro_cell').html(''); $('#intro_cell').append($(as_content).find('welcomeText').text());
		
		as_questions = $(as_xml).find('newquestions');	tas_questions = $(as_xml).find('questions');
                
                
                var qnodes = Array(); var s = 1;
                
                do{
                    rnd = Math.floor(Math.random() * 12) + 1; found = false;
                    for(tt=1;tt<=qnodes.length;tt++) {
                        if(qnodes[tt]==rnd) { found = true; break; }  
                    }
                    if(found==false) qnodes[s++] = rnd;                  
                    
                }while(qnodes.length<=10);
                 
                var sno = 1;
                
                for(i=1;i<=10;i++) {
                    rand = qnodes[i];
                    $(tas_questions).find('question').each(function() { 
                        if($(this).attr('id')==='q'+rand) {  
                            $(this).attr('id','q'+sno);
                            $(as_questions).append($(this)); 
                            sno++;
                        }
                    });
                    
                }
                
		total_questions = 0; 
		$(as_questions).find('question').each(function() { 
			question_data[$(this).attr('id')] = Array(); user_answers_data[$(this).attr('id')] = Array(); total_questions++;  
			if($(this).attr('qtype')=='DD') { dd_user_items[$(this).attr('id')] = Array(); dd_var_items[$(this).attr('id')] = Array(); }
		});
		$('#qhelp').css('display','block'); 
		
		$('#student_name').val('');
				
		
	}, error: function() { } });
	$('#content').removeClass('cntload');
	$('#mtable_test').fadeOut('fast',function() { $('#mtable_intro').fadeIn('fast'); } );
}

function startSession() {	
	if(session_started==false) {	
		$('#result_btns_div').fadeOut('fast'); $('#resdiv').fadeOut('fast');
		$('#mtable_intro').fadeOut('fast',function() { 
			$('#mtable_test').fadeIn('fast'); 
			session_started = true; current_question = 1; initTimer ('00:'+as_time+':00'); $('#qweightdiv').fadeIn('fast'); $('#qtimerdiv').fadeIn('fast');
			showQuestion();
		});
	}
}

function restartSession() {
	if(num_attempts < max_attempts) {
		current_question = 0; current_question_xml = ''; session_started = false; session_finished = false; end_reached = false;
		user_answers_data = Array(); user_answers = Array(); dd_being_dragged = '';  dd_user_items = Array(); dd_var_items = Array();
		total_questions = 0; $(as_questions).find('question').each(function() { 
			question_data[$(this).attr('id')] = Array(); user_answers_data[$(this).attr('id')] = Array(); total_questions++; 			
			if($(this).attr('qtype')=='DD') { dd_user_items[$(this).attr('id')] = Array(); dd_var_items[$(this).attr('id')] = Array(); }
		});
		$('#loqbtndiv').fadeOut('fast'); $('#btn_result').html('Submit Answers');
		$('#qtimer_minutes').html(as_time); $('#qtimer_seconds').html('00');
		$('#weldiv').fadeIn('fast'); $('#qhelp').html("Click 'Start Assessment' button to launch the assessment.");
		$('#btn_previous').disable('previous'); $('#qweightdiv').fadeOut('fast'); $('#qtimerdiv').fadeOut('fast');
		setupSession(1);
	}
}



function showQuestion(showAnswer) {
	var cnt = ''; var qoptions;	 $('#qhelp').html($(as_content).find('pageInstructionText').text()); var qid; $('#result_btns_div').fadeOut('fast');
	
	if(current_question >= total_questions) { 
		if(session_finished==false)
		$('#qhelp').html('This is the last question. Once you have answered ALL questions to your satisfaction, click <b>Submit Answers</b> to see your score.'); 
		else $('#qhelp').html('');
		$('#btn_next').disable('next'); $('#qhelp').disable('qhelp');
	}
	else { if(session_finished==true) $('#qhelp').enable('qhelp'); }
	
	if(current_question <= total_questions) {		
		$(as_questions).find('question').each(function(){
			id = $(this).attr('id');
			if(id == 'q'+current_question) {
				
				current_question_xml = $(this); qid = id;
				if($('#qweightdiv').css('display','none')) $('#qweightdiv').fadeIn('fast'); 
				$('#qweight').html($(this).attr('weightage'));
				
				if($(this).attr('qtype') == 'SS') buildSingleSelectQuestion(qid,current_question_xml,showAnswer);
				else if($(this).attr('qtype') == 'MS') buildMultipleSelectQuestion(qid,current_question_xml,showAnswer);
				else if($(this).attr('qtype') == 'DD') buildDragDropQuestion(qid,current_question_xml,showAnswer);
				
			}
		});
				
		$('#navdiv').show(); nav = '';
		if(current_question >= total_questions) { 
			if(session_finished==false)
			$('#qhelp').html('This is the last question. Once you have answered ALL questions to your satisfaction, click <b>Submit Answers</b> to see your score.'); 
			else $('#qhelp').html('');
			$('#btn_next').disable('next'); $('#qhelp').disable('qhelp');
		}
		if(current_question > 1) $('#btn_previous').enable('previous'); else $('#btn_previous').disable('previous'); 	
	}
}

function prsNextButton(qid,qtype,optlength) {
	answered = false;
	if(qtype=='SS' || qtype=='MS') {
		for(i=0;i<optlength;i++) { 
			if($('#'+qid+'_opt_'+i).is(':checked')) { answered = true; break; } 
		}
		if(answered==true && current_question < total_questions) { $('#btn_next').enable('next'); $('#qhelp').enable('qhelp'); } 
		else { $('#btn_next').disable('next'); $('#qhelp').disable('qhelp'); }
			
	}
	else if(qtype=='DD') {
		answered = true; 
		for(i=1;i<=optlength;i++) if(dd_user_items[qid][i]=='' || dd_user_items[qid][i]==undefined || dd_user_items[qid][i]=='undefined') { answered = false; break; } 
		if(answered==true && current_question < total_questions)  { $('#btn_next').enable('next'); $('#qhelp').enable('qhelp'); }
		else { $('#btn_next').disable('next'); $('#qhelp').disable('qhelp'); }
	}	
	
	if(current_question >= total_questions && answered == true) { end_reached = true; $('#resdiv').fadeIn('fast');  $('#qhelp').enable('qhelp'); }
	else {			
		var all_answered = true;
		$(as_questions).find('question').each(function(){ 
			if(qid!=$(this).attr('id')) { 
				if(user_answers[$(this).attr('id')]==1 || user_answers[$(this).attr('id')]==2) all_answered = true; else all_answered = false; 
			}
			else all_answered = false; 		
		});
		if(all_answered == true && answered==true && end_reached==true) { if($('#resdiv').css('display')=='none') $('#resdiv').fadeIn('fast'); } else $('#resdiv').fadeOut('fast'); 
	}
}

function finishSession() {	
	session_finished = true;
	var msg = ''; var total_weightage = 0; var user_weightage = 0; $('.qins').css('display','none'); $('#qhelp').html(''); $('#qindex').fadeOut('fast'); $('#qweightdiv').fadeOut('fast'); 	
	$(as_questions).find('question').each(function() { 
		total_weightage += parseInt($(this).attr('weightage')); if(user_answers[$(this).attr('id')]==1) user_weightage += parseInt($(this).attr('weightage')); 
	});
		
	var user_percentage = ( user_weightage / total_weightage ) * 100; var res = ''; $('#qtext').html('Assessment Result');
	user_percentage = Math.round(user_percentage * 100)/100;
	user_percentage = user_percentage.toFixed(0);
	
	
	if(user_percentage >= parseInt( $(as_config).find('scoreThreshold').text())) { // pass
		res = 'pass'; msg = $(as_content).find('feedbackTextPass').text().replace('|score|',user_percentage); 
		
		// mark the assessment value as completed
		$.ajax({
           type: 'GET',
           url: '../../assessmentResponse',
           data:'isComplete=1',
            success: function(result){
            },
            error: function(){
            }
       });
		
	}
	else { // fail	
		res = 'fail'; msg = $(as_content).find('feedbackTextFail').text().replace('|score|',user_percentage);
		$.ajax({
           type: 'GET',
           url: '../../assessmentResponse',
           data: 'isComplete=0',
           success: function(result){
            },
            error: function(){
            }
       }); 
	}
	
	cnt  = '<table class="optbox" cellpadding="0" cellspacing="0" border="0"><tr valign="top"><td>'+msg+'</td></tr></table>';
	
	$('#loqbtndiv').fadeIn('fast'); $('#result_btns_div').fadeIn('fast'); 
	
	if(num_attempts>=max_attempts) { $('#btn_repeat_test').disable('btn1');  }
	
	if(res=='pass') $('#certificate_btns').css('display','block'); else $('#certificate_btns').css('display','none'); 
	
	$('#qoptions').html(cnt);
		
	$('#btn_result').html('Show Results Page'); timerComplete(); $('#resdiv').css('display','none'); $('#btn_result').css('margin-top','-32px');
	
	// pool the questions for List of Questions pop up	
	pcnt  = '<table class="optbox" cellpadding="5" cellspacing="0" border="0" style="width:400px; margin:0px">'; var q = 1;
	$(as_questions).find('question').each(function() {
		qid = $(this).attr('id'); ans = '<div class="radio"></div>'; wgt = $(this).attr('weightage'); weight = '0 / '+wgt;
		if(user_answers[qid]==1) { ans = '<div class="acrct"></div>'; weight = wgt+' / '+wgt; } else if(user_answers[qid]==2) ans = '<div class="awrng"></div>';
		pcnt += '<tr valign="middle"><td class="ctrl">'+ans+'</td><td class="opt"><a class="link1 showanswerlink" href="#" ">Question '+q+ ' of '+total_questions+'&nbsp;&nbsp;(Your score: '+weight+')</a></td></tr>';//onclick="loqGotoQuestion('+q+')
		q++;
	});
	pcnt += '</table>';
	
	$('#loqc').html(pcnt); current_question = total_questions + 1; $('#btn_next').disable('next'); $('#btn_previous').enable('previous'); 
	
}

function loqGotoQuestion(qid) { current_question = qid; showQuestion(); $('#btn_loq_close').click(); }

$('#btn_next').click(function (e) { if($(this).hasClass('nextdisabled')) e.preventDefault(); else { evaluateQuestion(); current_question++; showQuestion(); } });
$('#btn_previous').click(function (e) { if($(this).hasClass('previousdisabled')) e.preventDefault(); else { evaluateQuestion(); current_question--; showQuestion(); } });
$('#btn_result').click(function (e) { 
	if($(this).hasClass('btndisabled')) e.preventDefault(); else { 
		if(session_finished==false) {  $('.pop').fadeOut('fast'); $('#popbg').fadeTo('fast',0.6); $('#pop_submiter').fadeIn('fast');  } 
		else finishSession(); 
	}
});

$('#btn_loq').click(function (e) { if(session_finished==true) { $('#popbg').fadeTo('fast',0.6); $('#loqp').fadeIn('fast'); } });
$('#btn_loq_close').click(function (e) { if(session_finished==true) { $('#loqp').fadeOut('fast'); $('#popbg').fadeOut('fast'); } });

$('#icn_help').click(function() { $('.pop').fadeOut('fast'); $('#popbg').fadeTo('fast',0.6); $('#pop_help').fadeIn('fast'); });
$('#btn_help_close').click(function() { $('#popbg').fadeOut('fast'); $('#pop_help').fadeOut('fast'); });

window.onunload=function(){	
	window.opener.location.reload();
};
$('#icn_close').click(function() {window.opener.location.reload();$('#popbg').fadeTo('fast',0.6); $('#pop_closer').fadeIn('fast'); });
$('#btn_closer_close').click(function() { $('#popbg').fadeOut('fast'); $('#pop_closer').fadeOut('fast'); });
$('#btn_closer_yes').click(function() {  top.close(); });
$('#btn_closer_no').click(function() {  $('#popbg').fadeOut('fast'); $('#pop_closer').fadeOut('fast');  });

$('#btn_submiter_yes').click(function() { $('#popbg').fadeOut('fast'); $('#pop_submiter').fadeOut('fast'); num_attempts = num_attempts+1; evaluateQuestion(); finishSession(); $('#btn_previous').addClass('previousdisabled'); });

$('#btn_submiter_no').click(function() {  $('#popbg').fadeOut('fast'); $('#pop_submiter').fadeOut('fast');  });

function evaluateQuestion() {
	var cqid = $(current_question_xml).attr('id'); 
	if(session_started ==true && session_finished==false) {
		qtype = $(current_question_xml).attr('qtype');
		if(qtype=='SS' || qtype=='MS') {
			var i = 0; var j = 0; var is_answered = false; var uans = Array(); var is_correct_answer = 1;
			for(i=0;i<question_data[cqid].length;i++) {
				if($('#'+cqid+'_opt_'+i).is(':checked')) {
					is_answered = true;
					if(parseInt(question_data[cqid][i][1]) == 0) is_correct_answer = 2;
					uans[j] = i; j++; // if the user chose this answer record it to user's answered options list 
				}
				else { if(parseInt(question_data[cqid][i][1]) == 1) is_correct_answer = 2; }
			}
		}
		else if(qtype=='DD') {
			var i = 1; var j = 0; var is_answered = false; var uans = Array(); var is_correct_answer = 1;

			for(i=1;i<question_data[cqid].length;i++) {
				if(dd_user_items[cqid][i]!='') {					

					is_answered = true; if(question_data[cqid][i][1]!=dd_user_items[cqid][i]) is_correct_answer = 2; uans[j] = dd_user_items[cqid][i]; j++;
				}
			}			
		}
		if(is_answered==false) is_correct_answer = 0; user_answers[cqid] = is_correct_answer; user_answers_data[cqid] = uans;  // record user's answered options list to user's answers data	
	}
	
}



// Question Type related functions --------------------------------------------------------

function buildSingleSelectQuestion(qid,question,showAnswer) {
	var qoptions = Array();  var qtype = $(question).attr('qtype');
	
	var qtxt = '<table style="width:100%;" border="0" align="left"><tr valign="top"><td style="width:65px;font-weight:100"><font class="boldText">Q'+current_question+'</font>&nbsp;of&nbsp;<font class="boldText">'+total_questions+':</font></td><td align="left">'+$(question).find('qText').text()+'</td></tr>';
				
	$('#qtext').html(qtxt);
	var qnumbers = '<table style="width:100%;" border="0" align="left"><tr valign="top"><td style="width:65px; font-weight:100"><font class="boldText">Q'+current_question+'</font>&nbsp;of&nbsp;<font class="boldText">'+total_questions+'</font></td></tr>';			
	$('#qtext1').html(qnumbers);
		
	cnt =  '<div class="qins" id="qinstruction">'+$(question).find('qInstruction').text()+'</div>';
	cnt  += '<table class="optbox" cellpadding="0" cellspacing="0" border="0">';
		
	if(session_finished==false) {
		var ret = Array(); var i = 0; var fqid = $(question).attr('id'); var optset = $(question).find('options');
		var optlength = 0; 
		
		if(question_data[qid].length <= 0) { 
			tmp = Array();
			$(optset).find('option').each(function() { 
				tmp[optlength++] = Array($(this).text(),$(this).attr('correct'));
			});  
			for(k=0;k<optlength;k++) question_data[qid][k] = Array(); var j = 0;
			do {
				if($(question).attr('random') == 'true') var pos = Math.floor(Math.random()*tmp.length); else pos = j;
				if(tmp[pos]!='') { question_data[qid][j] = Array(pos,tmp[pos][1]); j++; tmp[pos] = ''; }
				
			}while(j<optlength);
			
			
		} else optlength = question_data[qid].length;
		
		var qoptions = Array(); 	
		
		for(i=0;i<optlength;i++) {
			var tmp = Array(); var seltxt = 0;
			if(user_answers_data[qid].length>0) { for(j=0; j<user_answers_data[qid].length;j++)  if(user_answers_data[qid][j] == i) seltxt = 1; }
			tmp[0] = '<a class="radio" id="pseudo_'+qid+'_opt_'+i+'" onclick="prsOpt('+i+',\''+qid+'\','+optlength+')"></a><input id="'+qid+'_opt_'+i+'" name="'+qid+'_opt" type="radio" />'; 
			tmp[1] = '<label for="'+qid+'_opt_'+i+'" onclick="prsOpt('+i+',\''+qid+'\','+optlength+')" >'+getOptionByPosition(question,question_data[qid][i][0])+'</label>';
			tmp[2] = seltxt;
			qoptions[i] = tmp;
		}
		
	}
	else {
		qoptions = fetchQuestionSummary(question,showAnswer);
	}
	
	for(i=0;i<qoptions.length;i++)
	{
		cnt += '<tr valign="top">';
		if(session_finished==true) cnt += '<td class="ctrl">'+qoptions[i][2]+'</td>'; else cnt += '<td class="ctrl"><div style="width:15px;display:block">&nbsp;</div></td>';
		cnt += '<td class="ctrl">'+qoptions[i][0]+'</td><td class="opt">'+qoptions[i][1]+'</td></tr>';
	}
	cnt += '</table>';
	
	var	ssclass='btn1 btn1big'; var ssclick = 'onclick="showQuestion(1);"';  sol_ins = ''; 
	if(showAnswer==1) { 
		ssclass='btn1disabled btn1disabledbig'; ssclick = ''; 
		sol_ins = '<div class="sol_ins" id="sol_ins_'+qid+'">The correct answers are highlighted in green above.</div>';
	}
	
	var toShowSolution = false; if(parseInt($(as_config).find('showSolution').text())==1 && session_finished==true) 
	cnt += sol_ins+'<div class="sol_div"><a id="btn_show_sol" href="#" class="'+ssclass+'" '+ssclick+' style="margin-left:0px;">Show&nbsp;Correct&nbsp;Answers</a></div>';
		
	$('#qoptions').html(''); $('#qoptions').append(cnt);
	$('#qinstruction').css('display','table');
	
	if(session_finished==false) { for(qi=0;qi<qoptions.length;qi++) if(qoptions[qi][2]==1) $('#pseudo_'+qid+'_opt_'+qi).click();  }
	else { if($('#resdiv').css('display')=='none') $('#resdiv').fadeIn('fast'); }
	
	if(current_question < total_questions) { if(session_finished==true) $('#btn_next').enable('next'); else prsNextButton(qid,qtype,qoptions.length); }
	
}

function prsOpt(itm,qid,optlength) {
	qtype = 'SS';
	for(i=0;i<optlength;i++) { $('#'+qid+'_opt_'+i).attr('checked',false); $('#pseudo_'+qid+'_opt_'+i).removeClass('sel');  }
	$('#'+qid+'_opt_'+itm).attr('checked',true); $('#pseudo_'+qid+'_opt_'+itm).addClass('sel');
	
	prsNextButton(qid,qtype,optlength);	
}

function buildMultipleSelectQuestion(qid,question,showAnswer) {
	var qoptions = Array(); var qtype = $(question).attr('qtype');
	
	var qtxt = '<table style="width:100%;" border="0" align="left"><tr valign="top"><td style="width:65px; font-weight:100"><font class="boldText">Q'+current_question+'</font>&nbsp;of&nbsp;<font class="boldText">'+total_questions+':</font></td><td align="left">'+$(question).find('qText').text()+'</td></tr>';
	var qnumbers = '<table style="width:100%;" border="0" align="left"><tr valign="top"><td style="width:65px; font-weight:100"><font class="boldText">Q'+current_question+'</font>&nbsp;of&nbsp;<font class="boldText">'+total_questions+'</font></td></tr>';			
	$('#qtext1').html(qnumbers);
        $('#qtext').html(qtxt);
	
	cnt =  '<div class="qins" id="qinstruction">'+$(question).find('qInstruction').text()+'</div>';
	cnt  += '<table class="optbox" cellpadding="0" cellspacing="0" border="0">';
		
	if(session_finished==false) {
		var ret = Array(); var i = 0; var qid = $(question).attr('id'); var optset = $(question).find('options');
		var optlength = 0; 
		
		if(question_data[qid].length <= 0) { 
			tmp = Array();
			$(optset).find('option').each(function() { 
				tmp[optlength++] = Array($(this).text(),$(this).attr('correct'));
			});  
			for(k=0;k<optlength;k++) question_data[qid][k] = Array(); var j = 0;
			do {
				if($(question).attr('random') == 'true') var pos = Math.floor(Math.random()*tmp.length); else pos = j;
				if(tmp[pos]!='') { question_data[qid][j] = Array(pos,tmp[pos][1]); j++; tmp[pos] = ''; }
				
			}while(j<optlength);
			
			
		} else optlength = question_data[qid].length;
				
		var qoptions = Array(); 
		
		for(i=0;i<optlength;i++) {
			var tmp = Array(); var seltxt = 0; 
			if(user_answers_data[qid].length>0) { for(j=0; j<user_answers_data[qid].length;j++)  if(user_answers_data[qid][j] == i) seltxt = 1; }		
			tmp[0] = '<a class="checkbox" id="pseudo_'+qid+'_opt_'+i+'" onclick="prsChk('+i+',\''+qid+'\','+optlength+')"></a><input id="'+qid+'_opt_'+i+'" name="'+qid+'_opt" type="checkbox" />';
			tmp[1] = '<label onclick="prsChk('+i+',\''+qid+'\','+optlength+')" >'+getOptionByPosition(question,question_data[qid][i][0])+'</label>';
			tmp[2] = seltxt;
			qoptions[i] = tmp;
		}
	}
	else {
		qoptions = fetchQuestionSummary(question,showAnswer);
	}
	if(qid=='q50') {
		cnt += '<tr valign="top">'; a = 1; wd = 'style="width:200px !important"'; 
		for(i=0;i<qoptions.length;i++)
		{
			
			if(session_finished==true) cnt += '<td class="ctrl">'+qoptions[i][2]+'</td>'; else cnt += '<td class="ctrl"><div style="width:15px;display:block">&nbsp;</div></td>';
			cnt += '<td class="ctrl">'+qoptions[i][0]+'</td><td class="" '+wd+' >'+qoptions[i][1]+'</td>';
			
			if(a==2) { cnt +='</td></tr><tr valitn="top">'; a=1; wd='style="width:200px !important"'; } else { a++; wd = ''; }
		}
		cnt += '</tr>';
	} 
	else {
		for(i=0;i<qoptions.length;i++)
		{
			cnt += '<tr valign="top">';
			if(session_finished==true) cnt += '<td class="ctrl">'+qoptions[i][2]+'</td>'; else cnt += '<td class="ctrl"><div style="width:15px;display:block">&nbsp;</div></td>';
			cnt += '<td class="ctrl">'+qoptions[i][0]+'</td><td class="opt">'+qoptions[i][1]+'</td></tr>';
		}	
	}
	cnt += '</table>';
	
	var	ssclass='btn1 btn1big'; var ssclick = 'onclick="showQuestion(1);"';  sol_ins = ''; 
	if(showAnswer==1) { 
		ssclass='btn1disabled btn1disabledbig'; ssclick = ''; 
		sol_ins = '<div class="sol_ins" id="sol_ins_'+qid+'">The correct answers are highlighted in green above.</div>';
	}
	
	var toShowSolution = false; if(parseInt($(as_config).find('showSolution').text())==1 && session_finished==true) 
	cnt += sol_ins+'<div class="sol_div"><a id="btn_show_sol" href="#" class="'+ssclass+'" '+ssclick+' style="margin-left:0px;">Show&nbsp;Correct&nbsp;Answers</a></div>';
	
	$('#qoptions').html(''); $('#qoptions').append(cnt);
	$('#qinstruction').css('display','table');
	
	if(session_finished==false) { for(qi=0;qi<qoptions.length;qi++) if(qoptions[qi][2]==1) $('#pseudo_'+qid+'_opt_'+qi).click();  }
	else { if($('#resdiv').css('display')=='none') $('#resdiv').fadeIn('fast'); }
	
	if(current_question < total_questions) { if(session_finished==true) $('#btn_next').enable('next'); else prsNextButton(qid,qtype,qoptions.length); }
	
}

function prsChk(itm,qid,optlength) {
	var qtype = 'MS';
	if($('#'+qid+'_opt_'+itm).is(':checked')) {
		$('#'+qid+'_opt_'+itm).attr('checked',false); $('#pseudo_'+qid+'_opt_'+itm).removeClass('sel');
	}
	else {
		$('#'+qid+'_opt_'+itm).attr('checked',true); $('#pseudo_'+qid+'_opt_'+itm).addClass('sel');
	}
	prsNextButton(qid,qtype,optlength);	
}


function buildDragDropQuestion(qid,question,showAnswer) {
	var qoptions = Array();  var qtype = $(question).attr('qtype');
	
	var qtxt = '<table style="width:100%" border="0" align="left" ><tr valign="top"><td style="width:65px;" "font-weight:100"><font class="boldText">Q'+current_question+'</font>&nbsp;of&nbsp;<font class="boldText">'+total_questions+':</font></td><td align="left">'+$(question).find('qText').text()+'</td></tr>';
				
	$('#qtext').html(qtxt);		
	var qnumbers = '<table style="width:100%;" border="0" align="left"><tr valign="top"><td style="width:65px; font-weight:100"><font class="boldText">Q'+current_question+'</font>&nbsp;of&nbsp;<font class="boldText">'+total_questions+'</font></td></tr>';			
	$('#qtext1').html(qnumbers);
	cnt =  '<div class="qins" id="qinstruction">'+$(question).find('qInstruction').text()+'</div>';
	
	
	cnt += '<table class="ddset" cellpadding="0" cellspacing="5" border="0">';
	cnt += '<tr valign="middle">';
	cnt += '<td align="center"><div class="opthead '+qid+'opthead">'+$(question).find('targetHead').text()+'</div></td>';
	cnt += '<td align="center"><div class="opthead '+qid+'opthead">'+$(question).find('dragHead').text()+'</div></td>';
	cnt += '</tr>';	
	cnt += '</table>';
	
	cnt += '<div class="ddsetdiv">';
	cnt += '<table class="ddset" cellpadding="0" cellspacing="5" border="0">';
	
	
	var ret = Array(); var i = 1; var fqid = $(question).attr('id'); var optset = $(question).find('targets');
	var optlength = 0; var qoptions = Array();
	
	if(question_data[qid].length <= 0) { 
		tmp = Array();
		$(optset).find('target').each(function() {				
			tmp[optlength++] = Array($(this).text(),parseInt($(this).attr('match')));
		});
				
		for(k=1;k<=optlength;k++) question_data[qid][k] = Array(); var j = 0;
		
		do {
			if($(question).attr('random') == 'true') var pos = Math.floor(Math.random()*tmp.length); else pos = j;
			if(tmp[pos]!='') { question_data[qid][j+1] = Array(pos,tmp[pos][1]); j++; tmp[pos] = ''; alert(question_data[qid][j]); }
			
			
		} while(j<optlength);
		
	} else optlength = question_data[qid].length - 1;
	
	var preload = false; if(dd_user_items[qid].length > 0) { preload = true; }
	
	
	for(i=1;i<question_data[qid].length;i++){
		
		var solicn = ''; 
		if(session_finished==true) { 
			if( dd_user_items[qid][i] == question_data[qid][i][1]) solicn = '<td id="ddset_'+qid+'_solicn_'+i+'"><div class="acrct"></div></td>'; 
			else solicn =  '<td><div id="ddset_'+qid+'_solicn_'+i+'" class="awrng"></div></td>'; 
		}
		
		cnt += '<tr valign="middle">';
		cnt += '<td align="left"><div class="opt '+qid+'opt" id="ddset_'+qid+'_opt_'+i+'">'+getOptionByPosition(question,question_data[qid][i][0])+'</div></td>';
		cnt += '<td><div class="droppable optcase '+qid+'optcase" id="ddset_'+qid+'_optcase_'+i+'">&nbsp;</div></td>'; 
		cnt += solicn; 
		cnt += '</tr>';				
		qoptions[i] = i;//$(this).attr('id');
	}
	
	
	cnt += '</table><table  class="ddset" cellpadding="0" cellspacing="5" border="0">';
	
	var varset = $(question).find('drags'); var varlength = 0; $(varset).find('drag').each(function() { varlength++; });   var i = 1; var a = 0; 
	cnt += '<tr valign="top">';
	$(varset).find('drag').each(function() {	
		todrag = ''; if(session_finished==false) todrag = 'draggable ';
		cnt += '<td align="left" class="droppable varcase '+qid+'varcase" id="ddset_'+qid+'_varcase_'+i+'"><div class="'+todrag+'var '+qid+'var" id="ddset_'+qid+'_var_'+i+'">'+$(this).text()+'</div></td>';
		dd_var_items[qid][i] = i;
		i++;
		if(a == 1) { cnt+= '</tr><tr valign="top">'; a=0; } else a++;
	});
	
	cnt+= '</tr>';
			
	cnt += '</table>';
	cnt += '</div>';
	
	var	ssclass='btn1 btn1big'; var ssclick = 'onclick="showQuestion(1);"';  sol_ins = ''; 
	if(showAnswer==1) { 
		ssclass='btn1disabled btn1disabledbig'; ssclick = ''; 
		sol_ins = '<div class="sol_ins" id="sol_ins_'+qid+'">The correct answers are highlighted in green above.</div>';
	}
	
	var toShowSolution = false; if(parseInt($(as_config).find('showSolution').text())==1 && session_finished==true) 
	cnt += sol_ins+'<div class="sol_div"><a id="btn_show_sol" href="#" class="'+ssclass+'" '+ssclick+' style="margin-left:0px;">Show&nbsp;Correct&nbsp;Answers</a></div>';
		
	$('#qoptions').html(''); $('#qoptions').append(cnt);
	$('#qinstruction').css('display','table');	
	
	$( ".draggable" ).draggable({ containment: "#qoptions",
 		revert: "invalid",
		start: function( event, ui ) { $(this).css('z-index',dd_zindex+1); dd_zindex++; dd_being_dragged =  $(this); }
		
	});
	
	function doRevert(dropped) {
		if(!$(dropped).attr('id')) { 
			tmp = $(dd_being_dragged).attr('id').split('_'); dragged_item = tmp[tmp.length-1];
			for(i=1;i<=optlength;i++) { 
				var found = false; if(dd_user_items[qid][i] == dragged_item) { found = true; }  
				return found;
			}			
		}
		else return false;
	}
 
    $( ".droppable" ).droppable({
		activeClass: "ddactive", hoverClass: "ddhover",
		drop: function( event, ui ) { 
			// var l = $(this).css('left'); var t = $(this).css('top');  $(dd_being_dragged).animate({ opacity: 0.25, left: l, top:t }, 5000, function() { });
			var dropped_item_id = $(this).attr('id').split("_"); var dropped_item_type = dropped_item_id[2]; var dropped_item = dropped_item_id[3];	
			var drg_id = $(dd_being_dragged).attr('id').split("_"); var dragged_item = drg_id[3];
			
			$(dd_being_dragged).position({ my: "center", at: "center", of: "#"+$(this).attr('id') }); 
			prsDrop(dropped_item_type,dropped_item,dragged_item,qid,optlength,varlength);
		}
    });
	
	if(preload==true)  ddPreload(qid,qtype,optlength,varlength,showAnswer);
	if(session_finished==false) { }
	else { 
		if($('#resdiv').css('display')=='none') $('#resdiv').fadeIn('fast'); 
	}
	
	if(current_question < total_questions) { if(session_finished==true) $('#btn_next').enable('next'); else prsNextButton(qid,qtype,optlength); }
	
}

function prsDrop(dropped_item_type,dropped_item,dragged_item,qid,optlength,varlength) {
	var qtype = 'DD';
	for(i=1;i<=optlength;i++) if(dd_user_items[qid][i]==dragged_item) dd_user_items[qid][i] = '';
	for(j=1;j<=varlength;j++) if(dd_var_items[qid][j]==dragged_item) dd_var_items[qid][j] = ''; 
	
	i = 1; j = 1;
	if(dropped_item_type=='optcase') { // user dropped an item to option.. mark the option as answered	
	
		if(dd_user_items[qid][dropped_item]!='' && dd_user_items[qid][dropped_item]!=undefined && dd_user_items[qid][dropped_item]!='undefined') { // user option case is already holding an option 

			$('#ddset_'+qid+'_var_'+dd_user_items[qid][dropped_item]).position({ my: "center", at: "center", of: '#'+'ddset_'+qid+'_varcase_'+dd_user_items[qid][dropped_item] });  
			dd_var_items[qid][dd_user_items[qid][dropped_item]] = dd_user_items[qid][dropped_item];
			
		}
		dd_user_items[qid][dropped_item] = dragged_item; 
		// mark the dragged item's variant case as empty
				
		
	}
	else if(dropped_item_type=='varcase') { // user dropped an item to var case.. check if dropped item is from options, if so, mark that as unanswered		
		$('#ddset_'+qid+'_var_'+dragged_item).position({ my: "center", at: "center", of: '#'+'ddset_'+qid+'_varcase_'+dragged_item});  
	}
	prsNextButton(qid,qtype,optlength);
}

function ddPreload(qid,qtype,optlength,varlength,showAnswer) {
	if(showAnswer==1) { 
		matched = '';
		for(i=1;i<question_data[qid].length;i++) {
			matched = question_data[qid][i][1]; //alert('matched '+matched+' for position: '+j);
			$('#ddset_'+qid+'_var_'+dd_var_items[qid][matched]).position({ my: "center", at: "center", of: '#'+'ddset_'+qid+'_optcase_'+i });  	
			$('#ddset_'+qid+'_var_'+dd_var_items[qid][matched]).addClass('varsol');			
		}
		
		/* $(current_question_xml).find('targets').find('target').each(function() { 
			matched = $(this).attr('match');
			$('#ddset_'+qid+'_var_'+dd_var_items[qid][matched]).position({ my: "center", at: "center", of: '#'+'ddset_'+qid+'_optcase_'+j });  	
			$('#ddset_'+qid+'_var_'+dd_var_items[qid][matched]).addClass('varsol');			
			j++;  			
		});
		*/
	} 
	else { 
	
		for(i=1;i<=optlength;i++) {
			$('#ddset_'+qid+'_var_'+dd_var_items[qid][dd_user_items[qid][i]]).position({ my: "center", at: "center", of: '#'+'ddset_'+qid+'_optcase_'+i });  
		}
		prsNextButton(qid,qtype,optlength);
	}	
}

function getOptionByPosition(question,pos) {
	var ret = ''; var i = 0; 
	if($(question).attr('qtype')=='DD') {
		$(question).find('targets').find('target').each(function() { if(i==pos) ret = $(this).text(); i++; });		
	}
	else {
		$(question).find('options').find('option').each(function() { if(i==pos) ret = $(this).text(); i++; });	
	}
	return ret;
}

function fetchQuestionSummary(question,showAns) {
	var ret = Array(); var i = 0; var qid = $(question).attr('id'); var optset = $(question).find('options'); var optcls = ''; var toShowSolution = false;
	if(parseInt($(as_config).find('showSolution').text())==1) toShowSolution = true;
	var qtype = $(question).attr('qtype'); if(qtype=='SS') optcls = 'radio'; else if (qtype=='MS') optcls = 'checkbox';
	for(i=0;i<question_data[qid].length;i++) { 
		var tmp = Array(); var seltxt = ''; 
		if(user_answers_data[qid].length>0) { for(j=0; j<user_answers_data[qid].length;j++) { if(user_answers_data[qid][j] == i) seltxt = ' sel'; } }		
		lblcls = ''; if(parseInt(question_data[qid][i][1]) == 1 && toShowSolution==true && showAns==1) lblcls = 'crct';
		tmp[0] = '<div class="'+optcls+seltxt+'"></div>'; 
		tmp[1] = '<font class="'+lblcls+'">'+getOptionByPosition(question,question_data[qid][i][0])+'</font>'; 
		tmp[2] = '<div style="width:15px;display:block">&nbsp;</div>'; 
		if(seltxt == ' sel') 
		{ if(parseInt(question_data[qid][i][1]) == 1) tmp[2] = '<div class="acrct"></div>'; else tmp[2] = '<div class="awrng"></div>'; }
		ret[i] = tmp;
	}
		
	return ret;
}

// ---------- Timer Related Script -------------// 

function initTimer(time) { var x=(time).split(":"); timer.init(x[0],x[1],x[2]); }
var timer = {
    minutes :0, seconds : 0, elm :null, samay : null, sep : ':',

    init : function(h,m,s) {
		h = parseInt(h,10); m = parseInt(m,10); s = parseInt(s,10);
        if(h < 0 || m < 0 || s <0 || isNaN(h) || isNaN(m) || isNaN(s)) { alert('Invalid Time'); return; }
        this.hours = h; this.minutes = m; this.seconds = s; timer.start();
    },
    start : function() { this.samay = setInterval((this.doCountDown),1000); },
    doCountDown : function() {
        if(timer.seconds == 0)  {
            if(timer.minutes == 0)  {
			   if(timer.hours == 0) { alert('Time out!'); finishSession(); return; } else { timer.seconds=60; timer.minutes=59; timer.hours--; }
   			}
    	    else { timer.seconds=60; timer.minutes--; }
        }
        timer.seconds--; timer.updateTimer(timer.hours,timer.minutes,timer.seconds);
    },
    updateTimer :  function(hr,min,secs) {
		hr = (hr < 10 ? '0'+hr : hr); min = (min < 10 ? '0'+min : min); secs = (secs < 10 ? '0'+secs : secs);
		if(hr<=0&&min<=0&&secs<=0) {
			$('#qtimer_minutes').html('00'); $('#qtimer_seconds').html('00');
			return;
    	}
    	else {
			$('#qtimer_minutes').html(min); $('#qtimer_seconds').html(secs);
	    }
	}
}
function timerComplete() {
	clearInterval(timer.samay);
	
}
