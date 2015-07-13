'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('empresa4', {
                parent: 'entity',
                url: '/empresa4',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.empresa4.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/empresa4/empresa4s.html',
                        controller: 'Empresa4Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('empresa4');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('empresa4Detail', {
                parent: 'entity',
                url: '/empresa4/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.empresa4.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/empresa4/empresa4-detail.html',
                        controller: 'Empresa4DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('empresa4');
                        return $translate.refresh();
                    }]
                }
            });
    });
