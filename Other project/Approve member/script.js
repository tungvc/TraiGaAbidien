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


