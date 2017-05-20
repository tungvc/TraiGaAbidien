//document.querySelectorAll('div > button:last-child[type=submit]');
//document.querySelectorAll('a[data-testid="pending_post_delete_button"]');
//document.querySelectorAll('a[ajaxify*="/groups/unified_queue/async_response/?queue=pending&groupid="]')[0].click();
//document.querySelector('#userNav .linkWrap').innerHTML;


chrome.runtime.onMessage.addListener(function(request, sender, sendResponse) {
	if (request.request == 'addCurrentUser') {
		var userName = document.querySelector('#userNav .linkWrap').innerHTML;
		chrome.runtime.sendMessage({request: 'api', rest: 'fb_acc', method: 'save', params: {name: userName}}, function(){sendResponse('Done!!');});
	}
});

