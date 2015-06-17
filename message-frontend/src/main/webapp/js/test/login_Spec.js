describe('LoginController Test', function() {
    var scope, rootScope, location, authService;
    beforeEach(module('ngCookies', 'Authentication'));
    beforeEach(inject(function($controller, $rootScope, $location, AuthenticationService) {
        scope = $rootScope.$new();
        rootScope = $rootScope;
        location = $location;
        authService = AuthenticationService;
        $controller('LoginController', {
            $scope: scope,
            $rootScope: rootScope,
            $location: location,
            AuthenticationService: authService
        });
        successCallback = jasmine.createSpy();
        errorCallback = jasmine.createSpy();
        scope.dataLoading = false;
        scope.error = '';
        scope.username = '';
        scope.password = '';
        scope.$digest();
    }));
    it('must instantiate LoginController', function() {
        expect(scope).toBeDefined();
    });
    it('must call the login() function', function() {
        scope.username = 'currency';
        scope.password = 'fair';
        spyOn(authService, 'Login');
        scope.login();
        expect(authService.Login).toHaveBeenCalled();
    });
});
