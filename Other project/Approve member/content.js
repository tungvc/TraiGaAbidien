function multiClick(inputs, time) {
	if (inputs.length > 0) {
		(function myLoop (i) {          
		   setTimeout(function () {   
			  inputs[i].click();            
			  if (i--) myLoop(i);
		   }, time)
		})(inputs.length - 1);
	}
}

chrome.runtime.onMessage.addListener(function(request, sender, sendResponse) {
	if (request == 'approve') {
		var inputs = document.querySelectorAll('a[name="approve button"]');
		console.log("Approve " + inputs.length + " members");
		multiClick(inputs, 2);
		sendResponse(null);
	} else if (request == 'deletePost') {
		var inputs = document.querySelectorAll('a[data-tooltip-content="Delete"]');
		console.log("Delete " + inputs.length + " posts");
		multiClick(inputs, 2);
		
		//for (i = 0; i < inputs.length; i++) { 
		//	inputs[i].click();
		//}
		setTimeout(function(){ 
			var accepts = document.querySelectorAll('div > button:last-child[type=submit]');
			multiClick(accepts);
			sendResponse(null);
		}, 3000); 
		
	}
});

