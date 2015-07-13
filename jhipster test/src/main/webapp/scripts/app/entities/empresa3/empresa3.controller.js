'use strict';

angular.module('jhipsterApp')
    .controller('Empresa3Controller', function ($scope, Empresa3) {
        $scope.empresa3s = [];
        $scope.loadAll = function() {
            Empresa3.query(function(result) {
               $scope.empresa3s = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Empresa3.get({id: id}, function(result) {
                $scope.empresa3 = result;
                $('#saveEmpresa3Modal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.empresa3.id != null) {
                Empresa3.update($scope.empresa3,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Empresa3.save($scope.empresa3,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Empresa3.get({id: id}, function(result) {
                $scope.empresa3 = result;
                $('#deleteEmpresa3Confirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Empresa3.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEmpresa3Confirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveEmpresa3Modal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.empresa3 = {nome: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
