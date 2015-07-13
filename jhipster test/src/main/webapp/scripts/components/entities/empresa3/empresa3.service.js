'use strict';

angular.module('jhipsterApp')
    .factory('Empresa3', function ($resource, DateUtils) {
        return $resource('api/empresa3s/:id', {}, {
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
