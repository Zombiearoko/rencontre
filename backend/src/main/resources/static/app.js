var appModule = angular.module('myApp', []);

appModule.controller('MainCtrl', ['mainService','$scope','$http',
        function(mainService, $scope, $http) {
            $scope.greeting = 'Welcome Saphir to the JSON Web Token / AngularJS / Spring Boot!!!!';
            $scope.token = null;
            $scope.error = null;
            $scope.roleUser = false;
            $scope.roleAdmin = false;
            $scope.roleFoo = false;
//            //$scope. meetingName: string;
//            $scope.currentMeeting: Meeting;
//            $scope. meetings: Meeting[] = [];

            $scope.login = function() {
                $scope.error = null;
                mainService.login($scope.pseudonym,$scope.password,$scope.meetingName).then(function(token) {
                    $scope.token = token;
                    $http.defaults.headers.common.Authorization = 'Bearer ' + token;
                    $scope.checkRoles();
                },
                function(error){
                    $scope.error = error
                    $scope.pseudonym = '';
                    $scope.password = '';
                    $scope. meetingName='';
                });
            }

           /* $scope.checkRoles = function() {
                mainService.hasRole('ROLE_USER').then(function(user) {$scope.roleUser = user});
                mainService.hasRole('ROLE_ADMIN').then(function(admin) {$scope.roleAdmin = admin});
                mainService.hasRole('foo').then(function(foo) {$scope.roleFoo = foo});
            }*/

            $scope.logout = function() {
            	 $scope.pseudonym = '';
                 $scope.password = '';
                 $scope.meetingName='';
                 $scope.token = null;
                 $http.defaults.headers.common.Authorization = '';
                $http.get('/user/logout');
                       
            }
            
           
            		$scope.demo = function(){
            			 $http.get('/rencontre/Member/retrieve' +  
            					 $http.defaults.headers.common.Authorization);
            	}
            	
            

            $scope.loggedIn = function() {
                return $scope.token !== null;
            }
        } ]);



appModule.service('mainService', function($http) {
    return {
        login : function(pseudonym,password,meetingName) {
            return $http.post('/user/login', {name: pseudonym , pass: password, meetingName: meetingName}).then(function(response) {
                return response.data.token;
            });
        },

        hasRole : function(role) {
            return $http.get('/api/role/' + role).then(function(response){
                console.log(response);
                return response.data;
            });
        }
    };
});
