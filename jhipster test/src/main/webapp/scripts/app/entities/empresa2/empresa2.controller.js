'use strict';

angular.module('jhipsterApp')
    .controller('Empresa2Controller', function ($scope, Empresa2) {
        $scope.empresa2s = [];
        $scope.loadAll = function() {
            Empresa2.query(function(result) {
               $scope.empresa2s = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Empresa2.get({id: id}, function(result) {
                $scope.empresa2 = result;
                $('#saveEmpresa2Modal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.empresa2.id != null) {
                Empresa2.update($scope.empresa2,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Empresa2.save($scope.empresa2,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Empresa2.get({id: id}, function(result) {
                $scope.empresa2 = result;
                $('#deleteEmpresa2Confirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Empresa2.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEmpresa2Confirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveEmpresa2Modal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.empresa2 = {nome: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
