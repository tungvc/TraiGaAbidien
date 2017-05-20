// Copyright (c) 2014 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

/**
 * Get the current URL.
 *
 * @param {function(string)} callback - called when the URL of the current tab
 *   is found.
 */
 var API_ULR = 'http://fakelink.us';
 
function getCurrentTabUrl(callback) {
  // Query filter to be passed to chrome.tabs.query - see
  // https://developer.chrome.com/extensions/tabs#method-query
  var queryInfo = {
    active: true,
    currentWindow: true
  };

  chrome.tabs.query(queryInfo, function(tabs) {
    // chrome.tabs.query invokes the callback with a list of tabs that match the
    // query. When the popup is opened, there is certainly a window and at least
    // one tab, so we can safely assume that |tabs| is a non-empty array.
    // A window can only have one active tab at a time, so the array consists of
    // exactly one tab.
    var tab = tabs[0];

    // A tab is a plain object that provides information about the tab.
    // See https://developer.chrome.com/extensions/tabs#type-Tab
    var url = tab.url;

    callback(url);
  });
}

/**
 * @param {string} searchTerm - Search term for Google Image search.
 * @param {function(string,number,number)} callback - Called when an image has
 *   been found. The callback gets the URL, width and height of the image.
 * @param {function(string)} errorCallback - Called when the image is not found.
 *   The callback gets a string that describes the failure reason.
 */
function getAccountList(tabUrl, callback, errorCallback) {
  // Google image search - 100 searches per day.
  // https://developers.google.com/image-search/
  var searchUrl = API_ULR + '/web/fb_acc/list';
  var x = new XMLHttpRequest();
  x.open('GET', searchUrl);
  x.responseType = 'json';
  x.onload = function() {
    // Parse and process the response from Google Image Search.
    var response = x.response;
    if (!response || Object.prototype.toString.call(response) != '[object Array]') {
      errorCallback('Chưa đăng nhập fakelink!!!');
      return;
    }
    callback(response);
  };
  x.onerror = function() {
    errorCallback('Network error.');
  };
  x.send();
}

function renderStatus(statusText) {
  document.getElementById('status').textContent = statusText;
}

function switchCookie(c_user, xs) {
	chrome.cookies.set({url:"https://facebook.com",domain:".facebook.com",name:"c_user",secure:false,value: c_user, expirationDate: (new Date().getTime()/1000) + 90*24*3600}, null);
	chrome.cookies.set({url:"https://facebook.com",domain:".facebook.com",name:"xs",secure:false,value: xs, expirationDate: (new Date().getTime()/1000) + 90*24*3600}, null);
	refreshCurrentTab();
}

function refreshCurrentTab() {
	chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
        chrome.tabs.update(tabs[0].id, {url: tabs[0].url});
    });
}

function addCurrentUser() {
	chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
		chrome.tabs.sendMessage(tabs[0].id, {request: 'addCurrentUser'}, function(response) {console.log(response);reload();});
	});
}

document.addEventListener('DOMContentLoaded', function() {
  reload();
});


function reload() {
	getCurrentTabUrl(function(url) {
    renderStatus('Loading...');
	if (typeof url != 'string' || url.indexOf('facebook.com') < 0) {
		renderStatus('Hãy chọn tab facebook!!!');
		return;
	}

    getAccountList(url, function(data) {
		var list = document.createElement('ul');
		
		var item = document.createElement('li');
		var aTag = document.createElement('a');
		aTag.innerHTML = "Thêm tài khoản hiện tại";
		aTag.href = '';
		aTag.onclick = addCurrentUser;
		item.appendChild(aTag);
		list.appendChild(item);
			
		for(var i = 0; i < data.length; i++) {
			// Create the list item:
			var item = document.createElement('li');
			var aTag = document.createElement('a');
			var index = i;
			aTag.innerHTML = data[i].name;
			aTag.href = '';
			aTag.onclick = function(c_user, xs) { 
				return function() {
					switchCookie(c_user, xs);
				};
			}(data[index].id, data[index].xs);
			item.appendChild(aTag);
			list.appendChild(item);
		}
		document.getElementById('status').textContent = '';
		document.getElementById('status').appendChild(list);

    }, function(errorMessage) {
      renderStatus(errorMessage);
    });
  });
}