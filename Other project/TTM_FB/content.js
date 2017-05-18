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

function confirmDelete() {
	var accepts = document.querySelectorAll('div > button:last-child[type=submit]');
	for (i = 0; i < accepts.length; i++) 
		accepts[i].click();
}

var confirmDeleteInterval;
var delePostInterval;
function deletePost() {
	delePostInterval = setInterval(function() {
		var inputs = document.querySelectorAll('a[data-testid="pending_post_delete_button"]');
		console.log("Delete " + inputs.length + " posts");
		for (i = 0; i < inputs.length; i++) 
			inputs[i].click();

		//while (document.querySelectorAll('a[data-testid="pending_post_delete_button"]').length > 0) {}
		document.querySelectorAll('a[ajaxify*="/groups/unified_queue/async_response/?queue=pending&groupid="]')[0].click();
	}, 5000);
	
	confirmDeleteInterval = setInterval(confirmDelete, 1000); // assigned to a variable                
}

var isDeletePost = false

chrome.runtime.onMessage.addListener(function(request, sender, sendResponse) {
	if (request == 'approve') {
		var inputs = document.querySelectorAll('a[name="approve button"]');
		console.log("Approve " + inputs.length + " members");
		multiClick(inputs, 2);
		sendResponse(null);
	} else if (request == 'deletePost') {
		if (isDeletePost) {
			clearInterval(confirmDeleteInterval);
			clearInterval(delePostInterval);
			sendResponse(null);
		} else {
			sendResponse('Started');
			deletePost();
		}
		isDeletePost = !isDeletePost
	}
});

