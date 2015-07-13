'use strict';

angular.module('jhipsterApp')
    .controller('Empresa4Controller', function ($scope, Empresa4) {
        $scope.empresa4s = [];
        $scope.loadAll = function() {
            Empresa4.query(function(result) {
               $scope.empresa4s = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Empresa4.get({id: id}, function(result) {
                $scope.empresa4 = result;
                $('#saveEmpresa4Modal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.empresa4.id != null) {
                Empresa4.update($scope.empresa4,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Empresa4.save($scope.empresa4,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Empresa4.get({id: id}, function(result) {
                $scope.empresa4 = result;
                $('#deleteEmpresa4Confirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Empresa4.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEmpresa4Confirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveEmpresa4Modal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.empresa4 = {nome: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
