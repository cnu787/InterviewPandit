var xml_filename='course.xml'; 
var visited_pages = Array(); var course_pages = Array(); var current_page = 0; var number = 1; var audio_media_path = 'content/media/';var status = false;
function setupCourse() {
    $.ajax({
        type: "GET", url: xml_filename, dataType: "xml",
        success: function(crs_xml) {
            var crs_organizations = $(crs_xml).find('organizations'); 
            $('.header-title').html($(crs_organizations).find('organization').find('pagetitle').text()); 
            $(crs_organizations).find('items').each(function() {
                var module_id = $(this).attr('id'); var module_title = $(this).attr('title');
                if($(this).children().length>=1)
                {
                    $(this).find('item').each(function(){  
                        var tmp = {module_id:module_id, module_title: module_title, lesson_id:$(this).attr('id'), lesson_title:$(this).attr('title'), src:$(this).attr('src'), transcript:$(this).attr('transcript')};
                        course_pages[$(this).attr('pageid')] = tmp;
                        visited_pages[$(this).attr('pageid')]=0;
                    });
                }
                else
                {
                    var tmp = {module_id:module_id, module_title: module_title, lesson_id:0, lesson_title:'', src:$(this).attr('src'), transcript:$(this).attr('transcript')};
                    course_pages[$(this).attr('pageid')] = tmp;
                    visited_pages[$(this).attr('pageid')]=0;
                }
            });
            current_page=1;
            
            /*  Fetch suspend Data */
            var vps = getValue("cmi.suspend_data");
            if (vps != undefined && vps != 'undefined' && vps != ''){
                    tmp = vps.split("|"); 
                    if(tmp[0] != undefined && tmp[0] != 'undefined' && tmp[0] != '') vp = tmp[0];
                    if(tmp[1] != undefined && tmp[1] != 'undefined' && tmp[1] != '') current_page = tmp[1];
                    
                    visited_pages = vp.split(',');
            }
            
            gotoPage(current_page);
            
            
        }, 
        error: function() { } 
    });
}

function gotoPage(pageid)
{ 
        showPreloader();
        unloadAudioTranscript();
        current_page = pageid;
        if(current_page>0 && current_page<course_pages.length)
        {
                $('.audio-transcript').html(course_pages[pageid].transcript);
                $('#container').attr('src',course_pages[pageid].src);
                updateMenu();
                updateNavBar();
                markPageVisit(pageid);
        } 
        $('#glow1').css('display','none');
}

function updateMenu() {
    var cur_module = ''; var menu_content = '';
    for(i=1;i<course_pages.length;i++) {
        var page = course_pages[i]; var cls=''; var cls1=''; var cls2=''; 
        
        if(i==current_page){ cls = 'current-li'; cls1 = 'current-text'; cls2='current-module'; }
        else if(visited_pages[i]==1){ cls = 'visited-li';  cls1 = 'visited-text'; }
        if(cur_module !== page.module_id) {
            cur_module = page.module_id; if(i>1) menu_content += '</div>';
            if(page.lesson_id===0) {
                menu_content += '<a id="a_'+page.module_id+'" href="#demo_'+page.module_id+'" pageid="'+i+'" class="menu-item list-group-item list-group-item-success a_count '+cls2+'" data-toggle="collapse" data-parent="#MainMenu" >'+page.module_title+'</a><div>';
            }
            else {
                menu_content += '<a id="a_'+page.module_id+'" href="#demo_'+page.module_id+'" class="list-group-item list-group-item-success a_count '+cls2+'" data-toggle="collapse" data-parent="#MainMenu" >'+page.module_title+'<span class="item plus">&#043;</span><span class="item minus current-text">&#150;</span></a>';
                menu_content += '<div module="'+page.module_id+'" class="collapse opa" id="demo_'+page.module_id+'">';
                menu_content += '<a href="#" pageid="'+i+'" class="menu-item list-group-item list-submenu item-a" data-toggle="collapse"><ul class="item-ul '+cls1+'"><li class="'+cls+'" parentid="'+page.module_id+'" id="li_'+page.lesson_id+'"></li></ul><span class="item-nav '+cls1+'">'+page.lesson_title+'</span></a>';
            }
        }
        else {
           menu_content += '<a href="#" pageid="'+i+'" class="menu-item list-group-item list-submenu item-a hello" data-toggle="collapse"><ul class="item-ul '+cls1+'"><li class="'+cls+'" parentid="'+page.module_id+'" id="li_'+page.lesson_id+'"></li></ul><span class="item-nav '+cls1+'">'+page.lesson_title+'</span></a>';
        }
    }
    $('.menu').html(menu_content);
    $('ul.current-text').parent().parent().addClass('in').attr('aria-expanded',true);
    $("#a_"+$('ul.current-text').parent().parent().attr('module')).attr('aria-expanded',true);
    $('a').click(function(){
        if($(this).hasClass('collapsed'))
        {
            $('.list-group-item-success').addClass('collapsed');
            $('.list-group-item-success').removeAttr('aria-expanded');
            $('.list-group-item-success').attr('aria-expanded','false');
            $('.opa').removeClass('in');
            $('.opa').removeAttr('aria-expanded');
            $('.opa').attr('aria-expanded','false');
        }
    });
       if(visited_pages[8]=='1' && visited_pages[9]=='1') { $("#a_8").addClass('subdone'); }
    if(visited_pages[10]=='1' && visited_pages[11]=='1') { $("#a_9").addClass('subdone'); }
    if(visited_pages[12]=='1' && visited_pages[13]=='1') { $("#a_10").addClass('subdone'); }
    if(visited_pages[14]=='1' && visited_pages[15]=='1') { $("#a_11").addClass('subdone'); }
    if(visited_pages[16]=='1' && visited_pages[17]=='1') { $("#a_12").addClass('subdone'); }
}

function updateNavBar() {
    var count = current_page+' of '+(course_pages.length-1);
    $('#pageindex').html(count);
    if(current_page===1){
        $('#prev').removeClass('footer-img-prev');
        $('#prev').addClass('footer-img-prev-disabled');
        $('#next').addClass('footer-img-next');
        $('#next').removeClass('footer-img-next-disabled');
    }
    if(current_page==(course_pages.length-1)){
        $('#prev').removeClass('footer-img-prev-disabled');
        $('#prev').addClass('footer-img-prev');
        $('#next').removeClass('footer-img-next');
        $('#next').addClass('footer-img-next-disabled');
    }
    if(current_page>1&&current_page<(course_pages.length-1)){
        $('#prev').removeClass('footer-img-prev-disabled');
        $('#prev').addClass('footer-img-prev');
        $('#next').removeClass('footer-img-next-disabled');
        $('#next').addClass('footer-img-next');
    }
    
}    
 
function markPageVisit(page) {
    visited_pages[page] = 1;
    updateScorm();
}
function updateScorm() { 
    visitedPageCount = 0; var vpstr = '';

    for(var i=1; i<visited_pages.length; i++){
        if(i==1) vpstr = visited_pages[i]; else vpstr += ','+visited_pages[i];
        if(visited_pages[i] == 1){
            visitedPageCount = visitedPageCount + 1; 
        } 
    }
    
    vpstr += '|'+current_page;
    setValue('cmi.suspend_data',vpstr);
    
    var tp = getValue('cmi.suspend_data');
    
    
    if(visitedPageCount == (visited_pages.length-1)){
        $.ajax({
           type: 'GET',
           url: '../../courseResponse',
           data:'isComplete=1',
            success: function(result){
                    //$("#information_container1").append("<p>" + result + "</p>");
                    //$(".loader").fadeOut('normal');
            },
            error: function(){
                    //$("#information_container1").append("<p>" + "Server not found" + "</p>"); 
                    //$(".loader").fadeOut('normal');
            }
       });
        setValue("cmi.completion_status", "completed");
        setValue("cmi.success_status", "passed");
    }
    else {
        $.ajax({
           type: 'GET',
           url: '../../courseResponse',
           data: 'isComplete=0',
           success: function(result){
//                    $("#information_container2").append("<p>" + result + "</p>");
//                    $(".loader").fadeOut('normal');
            },
            error: function(){
//                   $("#information_container2").append("<p>" + "Server not found" + "</p>");
//                    $(".loader").fadeOut('normal');
            }
       });
        setValue("cmi.completion_status", "incomplete");
    }
}

$(document).ready(function() { 
    $("#left_menu").click(function(){
        $(".mainmenu").animate({ left: "0px" }, 500 );
    });

    $("#menu_first").click(function(){
        $(".mainmenu").animate({ left: "-300px" }, 500 );
    });
    
    $(document).on('click','.menu-item',function() {
        gotoPage($(this).attr('pageid'));
        $(".mainmenu").animate({ left: "-300px" }, 500 );
    });
    
    $("#prev").click(function(){
        if($(this).hasClass('footer-img-prev'))
        {
            gotoPage(parseInt(current_page,10)-parseInt(number,10));
        }
    });
    
    $("#next").click(function(){
        if($(this).hasClass('footer-img-next'))
        {
            gotoPage(parseInt(current_page,10)+parseInt(number,10));
        }
    });
    
    $('.resource').click(function() {
        $(this).addClass('selected');
        $('.glossary').removeClass('selected');
        $('.help').removeClass('selected');
        $('.close').removeClass('selected');
    });

    $('.glossary').click(function() {
        $(this).addClass('selected');
        $('.resource').removeClass('selected');
        $('.help').removeClass('selected');
        $('.close').removeClass('selected');
    });

    $('.help').click(function() {
        $(this).addClass('selected');
        $('.glossary').removeClass('selected');
        $('.resource').removeClass('selected');
        $('.close').removeClass('selected');
    });
    
    $('.close').click(function() {
        $(this).addClass('selected');
        $('.glossary').removeClass('selected');
        $('.resource').removeClass('selected');
        $('.help').removeClass('selected');
    });
    
    $('#btn_close').click(function(){
        window.close();
    });
    
    $('#close_id').click(function(){
        $('.close').removeClass('selected');
    });
    
    $('#btn_close_id').click(function(){
        $('.close').removeClass('selected');
    });

    $('#resource_close').click(function(){
        $('.resource').removeClass('selected');
    });

    $('#glossary_close').click(function(){
        $('.glossary').removeClass('selected');
    });

    $('#help_close').click(function(){
        $('.help').removeClass('selected');
    });
    
    $('#transcript').click(function(){
        if($(this).hasClass('audio-text')){
            $('.audio-subtitle').slideDown();
            $(this).removeClass('audio-text');
            $(this).addClass('audio-text-off');
        }
        else
        {
            $('.audio-subtitle').slideUp();
            $(this).removeClass('audio-text-off');
            $(this).addClass('audio-text');
        }
    });
    
    $(window).resize(function() {
        iframeHeight();
    });
        
}); 

function iframeHeight()
{
    var window_height = $(window).height(); //alert(window_height);
    ifram_height = window_height - 111; /* header-height = 60px, footer-height=50px*/
    $('.embed-container iframe').css('height',ifram_height+'px');
    $('.embed-container').css('height',ifram_height+'px');
}
var is_first_play = false;
function loadAudioTranscript(audio_file,content,post_audio_play,enableNext) {
    is_first_play = true; 
    if(enableNext==undefined) enableNext=true;
    showPreloader();
    $("#jquery_jplayer_1").jPlayer("destroy");
    $("#jquery_jplayer_1").jPlayer({
            ready: function (event) {
                    $(this).jPlayer("setMedia", {
                            mp3: audio_media_path+''+audio_file+".mp3",
                            aac: audio_media_path+''+audio_file+".acc",
                            ogg: audio_media_path+''+audio_file+".ogg"
                    }).jPlayer("play"); // auto play 
            },
            solution:"flash,html",
            swfPath: "lib/js",
            supplied: "ogg,mp3,aac",
            wmode: "window",
            useStateClassSkin: true,
            autoBlur: false,
            smoothPlayBar: true,
            keyEnabled: true,
            remainingDuration: true,
            toggleDuration: true,
            preload: "auto",
            play: function() { 
		hidePreloader();
                $(this).jPlayer("pauseOthers");
		if(is_first_play==true) { 
                    if(post_audio_play==true) document.getElementById('container').contentWindow.postAudioPlay();
                    is_first_play = false;
                }
                else {
                    document.getElementById('container').contentWindow.resumeAnimation($("#jquery_jplayer_1").data("jPlayer").status.currentTime);
                }
            },
            pause: function() {
                document.getElementById('container').contentWindow.pauseAnimation($("#jquery_jplayer_1").data("jPlayer").status.currentTime);
            },
            ended: function() {
            if(enableNext==true){
                next_enable();
            }
        }
    })
    .bind($.jPlayer.event.play, function() { // pause other instances of player when current one play
        $(this).jPlayer("pauseOthers");
    });
}

function unloadAudioTranscript() {
    //if(audio_enabled==true) {
        $('#jquery_jplayer_1').jPlayer("stop"); 
        $("#jquery_jplayer_1").jPlayer("destroy");
        
        
    //}
}

function initialize()
{
    iframeHeight();
    $(window).on("orientationchange",function(){
        iframeHeight();
    });
    doInitialize();
    setupCourse();
}

function showPreloader(){
    $(".preloaderbg").fadeIn('normal');
    $(".preloaderdiv").fadeIn('normal');
}

function hidePreloader(){
    $(".preloaderbg").fadeOut('normal');
    $(".preloaderdiv").fadeOut('normal');
   
}

function terminate()
{ 
    doTerminate();
}

function setValue(element,value)
{
    doSetValue(element,value);
    doCommit();
    return;
}

function getValue(element,value)
{
    return doGetValue(element,value); 
}

function showPopupGreen() {
    $('#glow1').fadeIn('fast',function() { $('#glow1').fadeOut('fast',function() { $('#glow1').fadeIn('fast',function() { $('#glow1').fadeOut('fast',function() {  $("#glow1").fadeIn('fast', function() {  }); }); }); }); });
}

function next_enable() {
                           $('#next').removeClass('footer-img-next-disabled'); 
                           $('#next').addClass('footer-img-next');
                        }


function next_disable() {
                           $('#next').addClass('footer-img-next-disabled'); 
                           $('#next').removeClass('footer-img-next');
                        }
