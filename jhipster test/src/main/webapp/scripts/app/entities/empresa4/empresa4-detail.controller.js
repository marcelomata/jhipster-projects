'use strict';

angular.module('jhipsterApp')
    .controller('Empresa4DetailController', function ($scope, $stateParams, Empresa4) {
        $scope.empresa4 = {};
        $scope.load = function (id) {
            Empresa4.get({id: id}, function(result) {
              $scope.empresa4 = result;
            });
        };
        $scope.load($stateParams.id);
    });
