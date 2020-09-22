// Set imageurl to the path where the nubbin.png image is located

var imageurl = "";

$(document).ready(function(){
			
	
	$().mousemove( function(e) {
		mouseX = e.pageX; 
		mouseY = e.pageY;
	});
	 
	$(".tooltip").hover(
		function () {
			id = $(this).attr('id');
			
			split = id.split('-', 2)
			number = split[1];
			
			clearTimeout(window['ta' + number]);
			$('#'+id).show();

			
		}, 
		function () {
			
			id = $(this).attr('id');
			$('#'+id).fadeOut('fast');
			
		}
	);
	 
	$(".tooltip").each(function (i) {
		var prepend$$i = 0;
		
		$("#jttrigger-"+i).hover(
	      function () {
			
			if(prepend$$i == 0)
			{
				$("#tooltip-"+i).prepend('<img class="nubbin" src="'+imageurl+'imageslayout/nubbin.png" alt="arrow" height="13" width="27">');
				prepend$$i = "done";
			}
			
			var triggerPos = $("#jttrigger-"+i).position();
			var jttipPos = $("#tooltip-"+i).position();
			var triggerHeight = $("#jttrigger-"+i).height();
			var triggerWidth = $("#jttrigger-"+i).width();
			
	      	var jttipWidth = $("#tooltip-"+i).width();
	      	
	      	var offsetX = triggerWidth-jttipWidth;
	      	
	      	$("#tooltip-"+i).css('top',triggerPos.top+triggerHeight);
	      	
	      	if(offsetX > 0)
	      	{
	      		$("#tooltip-"+i).css('left',triggerPos.left-(offsetX));
	      	}
	      	else
	      	{
	      		$("#tooltip-"+i).css('left',triggerPos.left+(offsetX));
	      	}
	      	
	      	window['t' + i] = setTimeout(function() { $("#tooltip-"+i).fadeIn('fast'); },300);
	        
	      }, 
	      function () {
				
				clearTimeout(window['t' + i]);

				if($("#tooltip-"+i).css("display") == 'block')
				{
					window['ta' + i] = setTimeout(function() { $("#tooltip-"+i).hide(); },300);
				}

	      });
	      
		});
	
});