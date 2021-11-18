
var seenMsg = '<div id="deliver-msg" class="text-right"><small>Seen</small></div>';
var deliverMsg = '<div id="deliver-msg" class="text-right"><small>Delivered</small></div>';

var username = $("#username").val();
var profileid = $("#profileid").val();
var lastStatus;

function sendMessage(){
	var text = $("#text").val();
	$("#text").val("");
	if(text.trim()!==""){
		$.ajax({
			url: "/u/send",
			data: {"text": text, "username": username},
			method: "get",
			success: function(data){
				if(data==="done"){
					$("#chat-area").append('<div class="chat-area-r"><div class="chat-receiver"><label>'+text.trim()+'</label><br/><small>'+dayjs(new Date()).format("DD/MMM/YYYY hh:mm a")+'</small></div></div>');
					$("#chat-area").scrollTop($("#chat-area").prop("scrollHeight"));
					checkSeen = true;
					$("#deliver-msg").remove();
					seenMsgOrNotFn();
				}
			},
			error: function(){
				alert("Something went wrong");
				location.reload();
			}
		});
	}
}
function getNewMessage() {
	$.ajax({
		url: "/u/getmorechat",
		data: {"username": username},
		method: "get",
		success: function(data){
			if(data.length>0 && checkSeen){
				checkSeen = false;
				$("#deliver-msg").remove();
			}
			data.forEach(msg=>{
				$("#chat-area").append('<div class="chat-area-s"><div class="chat-sender"><label>'+msg.text+'</label><br/><small>'+dayjs(msg.date).format("DD/MMM/YYYY hh:mm a")+'</small></div></div>');
				$("#chat-area").scrollTop($("#chat-area").prop("scrollHeight"));
			})
		}
	})
}
function seenMsgOrNotFn(){
	$.ajax({
		url: "/u/checkchatseen",
		data: {"username": username},
		method: "get",
		success: function(data){
			if(data==="seen"){
				$("#chat-area").append(seenMsg);
				lastStatus = true;
			} else if(data==="no"){
				$("#chat-area").append(deliverMsg);
				lastStatus = false;
			}
			$("#chat-area").scrollTop($("#chat-area").prop("scrollHeight"));
		},
		error: function(){
			alert("Something went wrong");
			location.reload();
		}
	});
}
function loadMoreChat(){
	var firstChat = $("#first-chat").val();
	$.ajax({
		url: "/u/loadmorechat",
		data: {"firstChat": firstChat,"username": username},
		method: "get",
		success: function(data){
			if(data.length<20){
				$("#chat-profile").show();
				$("#load-more").remove();
			}
			$("#first-chat").val(data[0].id);
			var temp = "";
			data.forEach(msg=>{
				if(msg.userId==profileid){
					temp+=('<div class="chat-area-s"><div class="chat-sender"><label>'+msg.text+'</label><br/><small>'+dayjs(msg.date).format("DD/MMM/YYYY hh:mm a")+'</small></div></div>');	
				} else {
					temp+=('<div class="chat-area-r"><div class="chat-receiver"><label>'+msg.text+'</label><br/><small>'+dayjs(msg.date).format("DD/MMM/YYYY hh:mm a")+'</small></div></div>');
				}
			})
			$("#chat-upper-area").html(temp+$("#chat-upper-area").html());
		}
	})
}

function checkAgainAndAgainSeen(){
	if(checkSeen){
		$.ajax({
			url: "/u/checkchatseen",
			data: {"username": username},
			method: "get",
			success: function(data){
				if(data==="seen" && !lastStatus){
					$("#deliver-msg").remove();
					$("#chat-area").append(seenMsg);
					lastStatus = true;
					$("#chat-area").scrollTop($("#chat-area").prop("scrollHeight"));
				} else if(data==="no" && lastStatus){
					$("#deliver-msg").remove();
					$("#chat-area").append(deliverMsg);
					lastStatus = false;
					$("#chat-area").scrollTop($("#chat-area").prop("scrollHeight"));
				}
			},
			error: function(){
				alert("Something went wrong");
				location.reload();
			}
		});	
	}
}

$(document).ready(function(){
	$("#chat-area").scrollTop($("#chat-area").prop("scrollHeight"));
	$("#text").keypress(function(event){
	    var keycode = (event.keyCode ? event.keyCode : event.which);
	    if(keycode == '13' &&  !event.shiftKey){
	    	event.preventDefault();
	    	sendMessage();
	    }
	});
	$("#send-text").on("click", function(){
		sendMessage();
	});
	if(checkSeen){
		seenMsgOrNotFn();
	}
	setInterval(getNewMessage, 1000);
	setInterval(checkAgainAndAgainSeen ,500);
});

