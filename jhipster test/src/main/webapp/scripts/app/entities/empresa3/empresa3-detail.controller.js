'use strict';

angular.module('jhipsterApp')
    .controller('Empresa3DetailController', function ($scope, $stateParams, Empresa3) {
        $scope.empresa3 = {};
        $scope.load = function (id) {
            Empresa3.get({id: id}, function(result) {
              $scope.empresa3 = result;
            });
        };
        $scope.load($stateParams.id);
    });
