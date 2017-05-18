// Copyright (c) 2010 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

// A generic onclick callback function.

function approve(info, tab) {
	chrome.tabs.sendMessage(tab.id, "approve", function(response) {
      
    });
}

function deletePost(info, tab) {
	chrome.tabs.sendMessage(tab.id, "deletePost", function(response) {
      if (response == 'Started') {
		chrome.contextMenus.update(deleteMenu, {
			title: 'Dừng xóa bài'
		});
	  } else {
		chrome.contextMenus.update(deleteMenu, {
			title: 'Xóa bài'
		});
	  }
    });
}

// Create one test item for each context type.
var approveMenu = chrome.contextMenus.create({"title": "Duyệt thành viên", "onclick": approve, "documentUrlPatterns": ["*://*.facebook.com/groups/*/requests*", "*://*.facebook.com/groups/*/pending*"]});
var deleteMenu = chrome.contextMenus.create({"title": "Xóa bài", "onclick": deletePost, "documentUrlPatterns": ["*://*.facebook.com/groups/*/requests*", "*://*.facebook.com/groups/*/pending*"]});

function httpGetAsync(theUrl, callback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() { 
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(xmlHttp.responseText);
    }
	
    xmlHttp.open("GET", theUrl, true); // true for asynchronous 
    xmlHttp.send(null);
}

function getData() {
	httpGetAsync('http://fakelink.us/web/domain', data => console.log(data));
}

function getCookie() {
	chrome.cookies.set({domain}, callback)
}


	chrome.cookies.set({url:"http://fakelink.us",domain:".fakelink.us",name:"abidien",secure:false,value:"test1", expirationDate: (new Date().getTime()/1000) + 90*24*3600}, null);
