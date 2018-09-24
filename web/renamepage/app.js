/**
 * Created by abidien on 3/31/2016.
 */
(function(){
    var app = angular.module('store', []);

    app.controller('StoreController', ['$scope', '$http', function($scope, $http){
		
		$scope.rename = function() {
			
			$scope.user;
			$scope.pass;
			$scope.message = "";
			$scope.loading = true;
			$http(
				{
					
					method: 'POST',
					url: '/web/rename/rename_fanpage?username=' + $scope.user + '&password=' + $scope.pass + '&pageID=' + $scope.pageID + '&oldName=' + $scope.oldName + '&newName=' + $scope.newName,
					headers: {
					}
				}).success(function (resp, status, headers) {
					$scope.message = resp;
					
				}).finally(function() {
					$scope.loading = false;
				});
		}
		
		
		
        $scope.comments = [];
        $scope.key = "";
        $scope.token = ""
        $scope.count = 0;
        $scope.interval = 120;

        $scope.getData = function() {
            if ($scope.interval < 60) {
                alert("Thời gian lặp lại từ 60s trở lên!!")
                $scope.off = false;
                return;
            }
            $scope.comments = [];
            var listGroupIds = $scope.groupIds.split(",");
            var search = ""
            if ($scope.key != "") {
                $scope.key.split(",").map(function (f) {
                    return "strpos(lower(text),lower('" + f + "')) >= 0";
                })
                search = " and (" + search.join(" or ") + ")";
            }
            for (indexGroup = 0; indexGroup < listGroupIds.length; indexGroup++) {
                //if ($scope.groups[indexGroup].val) {
                    var url = "https://graph.facebook.com/fql?q=SELECT id,text FROM comment WHERE (post_id in (SELECT post_id FROM stream where source_id = " + listGroupIds[indexGroup] + " LIMIT 0,1000))" + search + " LIMIT 0,10000&access_token="+$scope.token
                    url = encodeURI(url)
                    $http.get(url)
                        .success(function (resp, status, headers) {
                            $scope.comments = resp.data;
                            for (i = 0; i < resp.data.length; i++) {
                                //$http.get("https://graph.facebook.com/"+resp.data[i].id+"?method=delete&access_token="+$scope.token)
                                $scope.count++;
                            }
                        })
                        .error(function (data, status) {
                            if (data.error)
                                alert(JSON.stringify(data.error.message));
                            else alert(JSON.stringify(data));
                        });
                //}
            }
            setTimeout(function(){$scope.getData(); $scope.$apply();}, $scope.interval * 1000);
        };

        $scope.getGroup = function() {
            $http.get("https://graph.facebook.com/v2.5/me/groups?fields=name,id&limit=1000&access_token="+$scope.token)
                .success(function (resp, status, headers) {
                    $scope.groups = resp.data;
                })
                .error(function (data, status) {
                    if (data.error)
                        alert(JSON.stringify(data.error.message));
                    else alert(JSON.stringify(data));
                });
        };
    }]);
})();
