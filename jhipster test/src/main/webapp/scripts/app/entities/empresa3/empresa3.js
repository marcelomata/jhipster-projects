'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('empresa3', {
                parent: 'entity',
                url: '/empresa3',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.empresa3.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/empresa3/empresa3s.html',
                        controller: 'Empresa3Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('empresa3');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('empresa3Detail', {
                parent: 'entity',
                url: '/empresa3/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.empresa3.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/empresa3/empresa3-detail.html',
                        controller: 'Empresa3DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('empresa3');
                        return $translate.refresh();
                    }]
                }
            });
    });
