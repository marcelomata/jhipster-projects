'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('empresa', {
                parent: 'entity',
                url: '/empresa',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.empresa.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/empresa/empresas.html',
                        controller: 'EmpresaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('empresa');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('empresaDetail', {
                parent: 'entity',
                url: '/empresa/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.empresa.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/empresa/empresa-detail.html',
                        controller: 'EmpresaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('empresa');
                        return $translate.refresh();
                    }]
                }
            });
            
    });
