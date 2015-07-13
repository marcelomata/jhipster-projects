'use strict';

angular.module('jhipsterApp')
    .controller('Empresa2DetailController', function ($scope, $stateParams, Empresa2) {
        $scope.empresa2 = {};
        $scope.load = function (id) {
            Empresa2.get({id: id}, function(result) {
              $scope.empresa2 = result;
            });
        };
        $scope.load($stateParams.id);
    });
