'use strict';

angular.module('jhipsterApp')
    .controller('EmpresaDetailController', function ($scope, $stateParams, Empresa) {
        $scope.empresa = {};
        $scope.load = function (id) {
            Empresa.get({id: id}, function(result) {
              $scope.empresa = result;
            });
        };
        $scope.load($stateParams.id);
    });
