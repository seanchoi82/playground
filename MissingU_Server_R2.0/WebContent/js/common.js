jQuery.fn.center = function () {
    this.css("position","absolute");
    this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) + 
                                                $(window).scrollTop()) + "px");
    this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) + 
                                                $(window).scrollLeft()) + "px");
    return this;
}


// 클릭 이벤트
$(document).on("click", "*[data-click-callback]", function(e) {
	
	e.preventDefault();
	
	if($(e.currentTarget).attr("data-click-callback") != "") {
		eval("" + $(e.currentTarget).attr("data-click-callback"));
	};
});


function getTimeStamp() {

	var d = new Date();

	var month = d.getMonth() + 1

	var date = d.getDate()

	var hour = d.getHours()

	var minute = d.getMinutes()

	var second = d.getSeconds()



	month = (month < 10 ? "0" : "") + month;

	date = (date < 10 ? "0" : "") + date;

	hour = (hour < 10 ? "0" : "") + hour;

	minute = (minute < 10 ? "0" : "") + minute;

	second = (second < 10 ? "0" : "") + second;



	var s =

	d.getFullYear() + month + date + hour + minute + second



	return s;

}

//팝업 레이어 보여주기
function showPopup(id) {
	$.blockUI({message : $('#'+id), css : {width:'500px', border: 'none', 'margin-top':'-200px'}});
}

//팝업 레이어 보여주기
function hidePopup(id) {
	$.unblockUI();
}



$(document).on("click", ".popimg", function() {
	
	var src = $(this).attr("src");
	var original_src = $(this).attr("data-original");
	var image_href = original_src || src;
	
	
	if ($('#lightbox').length > 0) {	
		$('#lightbox-content').html('<img src="' + image_href + '" />');
		$('#lightbox').show();
	}
	else {	
		var lightbox = 
		'<div id="lightbox">' +
			'<p>Click to close</p>' +
			'<div id="lightbox-content">' + //insert clicked link's href into img src
				'<img src="' + image_href +'" />' +
			'</div>' +	
		'</div>';
		$('body').append(lightbox);
	}
	
	$("#lightbox").dialog({
			title : 'Show Image'
		  , width:  'auto'
		  , height:  'auto'
		  , autoOpen: false
		  , position: 'center'
		  , closeOnEsc: true
		  , modal:  true,
		  });
		   
	$('#lightbox').dialog('open');  
	// $('"#lightbox').dialog('option', 'position', 'top');
	//$( "#lightbox" ).position({
    //    my: "center center",
	//   at: "center center",
	//   of: window
	//});
});


$(document).on('click', '#lightbox', function() { //must use live, as the lightbox element is inserted into the DOM
	$('#lightbox').hide();
});


(function($){
    $.fn.extend({
         center: function () {
               return this.each(function() {
                       var top = ($(window).height() - $(this).outerHeight()) / 2;
                       var left = ($(window).width() - $(this).outerWidth()) / 2;
                       $(this).css({position:'absolute', margin:0, top: (top > 0 ? top : 0)+'px', left: (left > 0 ? left : 0)+'px'});
               });
       }
    });
})(jQuery);

$(document).on('click', '.checkInverse', function() {
	var names = $(this).attr("data-name");
	var checked = $(this).is(":checked");
	
	$("input[name='" + names + "']").each(function() {
		$(this).attr("checked", checked);
	});
});

// $.datepicker.setDefaults( $.datepicker.regional[ "ko" ] );

$(document).ready(function() {
	$(".datepicker").datepicker({ dateFormat : 'yymmdd'});
	$('.nyroModal').nyroModal();
});


/** 선택한 체크박스의 Id와 Lebel을 추출해 온다 */
function getCheckedIds(name, type) {
	var rtnIds = '';
	var rtnLabels = '';
	
	$("input[name='" + name + "']:checked").each(function() {
		if(rtnIds != '') { 
			rtnIds += ',';
			rtnLabels += ',';
		}		
		rtnIds += $(this).val();
		rtnLabels  += $(this).attr("data-label");
	});
	
	if(type && type == "array")
		return { ids: rtnIds.split(","), labels:rtnLabels.split(",") };
	else
		return { ids: rtnIds, labels:rtnLabels };
}
/*
 * 
function globalSendMsgSender() {
	
}

function globalSendMsg(opts) {
			
	settings = jQuery.extend({
		sender:"",
	    receivers: {},
	    msgText:""
	  }, opts);
	
	var sendMsgSender= $( "#dialog-message-form-sender" ),
	sendMsgReceiver = $( "#dialog-message-form-receiver" ),
	sendMsgMsgText = $( "#dialog-message-form-msgText" ), 
	sendMsgTips = $( "#dialog-message-form-tips" ), 
	allFields = $( [] ).add( sendMsgSender ).add( sendMsgReceiver ).add( sendMsgMsgText );
	
	sendMsgSender.val(settings.sender);
	sendMsgReceiver.val(settings.receivers);
	sendMsgMsgText.val(settings.msgText);
	
	$( "#dialog-message-form" ).dialog({
	      autoOpen: false,
	      height: 300,
	      width: 350,
	      modal: true,
	      buttons: {
	        "발송하기": function() {
	        	if(!sendMsgSender.val()) {
	        		sendMsgSender.addClass( "ui-state-error" );
	        		sendMsgTips.html("발송자를 선택하세요.");
	        		return;
	        	}
	        	
	        	if(!sendMsgMsgText.val()) {
	        		sendMsgMsgText.addClass( "ui-state-error" );
	        		sendMsgTips.html("쪽지 내용을 입력 하세요.");
	        		return;
	        	}
	        },
	        Cancel: function() {
	          $( this ).dialog( "close" );
	        }
	      },
	      close: function() {
	        allFields.val( "" ).removeClass( "ui-state-error" );
	      }
	});
	 
	$( "#dialog-message-form" ).dialog( "open" );
}
*/
    