'use strict';

angular.module('jhipsterApp')
    .controller('EmpresaController', function ($scope, Empresa, ParseLinks) {
        $scope.empresas = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Empresa.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.empresas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Empresa.get({id: id}, function(result) {
                $scope.empresa = result;
                $('#saveEmpresaModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.empresa.id != null) {
                Empresa.update($scope.empresa,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Empresa.save($scope.empresa,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Empresa.get({id: id}, function(result) {
                $scope.empresa = result;
                $('#deleteEmpresaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Empresa.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEmpresaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveEmpresaModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.empresa = {nome: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
