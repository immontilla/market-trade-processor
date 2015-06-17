describe('AuthenticationService Test', function() {
    var authService, httpBackend, q, b64;
    beforeEach(module('ngCookies', 'Authentication'));
    beforeEach(inject(function (AuthenticationService, Base64, $http, $cookieStore, $rootScope, $timeout, $httpBackend, $q, Base64) {
        authService = AuthenticationService;
        httpBackend = $httpBackend;
        q = $q; 
        b64 = Base64;       
    }));
    it('must instatantiate AuthenticationService', function () {
        expect(authService).toBeDefined();
        authService.Login();
        authService.SetCredentials();
        authService.ClearCredentials();
    });
    it('must encode/decode with Base64', function () {
        var encrypted = b64.encode('currency:fair');
        expect(encrypted).toMatch('Y3VycmVuY3k6ZmFpcg==');
        var decrypted = b64.decode('Y3VycmVuY3k6ZmFpcg==');
        expect(decrypted).toMatch('currency:fair');
    });
});
