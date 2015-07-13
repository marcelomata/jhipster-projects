'use strict';

angular.module('jhipsterApp')
    .factory('Empresa2', function ($resource, DateUtils) {
        return $resource('api/empresa2s/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
