'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('empresa2', {
                parent: 'entity',
                url: '/empresa2',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.empresa2.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/empresa2/empresa2s.html',
                        controller: 'Empresa2Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('empresa2');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('empresa2Detail', {
                parent: 'entity',
                url: '/empresa2/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.empresa2.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/empresa2/empresa2-detail.html',
                        controller: 'Empresa2DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('empresa2');
                        return $translate.refresh();
                    }]
                }
            });
    });
