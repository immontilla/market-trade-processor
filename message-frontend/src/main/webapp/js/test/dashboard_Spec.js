describe('DashboardController Test', function() {
    var scope, rootScope, httpBackend, url;
    beforeEach(module('Dashboard', 'nvd3'));
    beforeEach(inject(function($controller, $rootScope, $httpBackend) {
        url = 'http://localhost:8888/messages';
        httpBackend = $httpBackend;
        scope = $rootScope.$new();
        $controller('DashboardController', {
            $scope: scope
        });
        scope.activeCountries = [];
        scope.activeUsers = [];
        scope.dataMostBought = [];
        scope.dataMostSold = [];
        scope.models = {
            searchText: '',
            messages: []
        };
        scope.$digest();
    }));
    it('must instantiate DashboardController', function() {
        expect(scope).toBeDefined();
    });
    it('must call the init() function', function() {        
        scope.init();
    });
    it('must call the loadData() function', function() {
    	var a = '{"id":1,"userId":1000013,"currencyFrom":"CHF","currencyTo":"SEK","amountSell":1027.87,"amountBuy":1594.73,"rate":0.62,"timePlaced":"10-JUN-2015 23:58:42","originatingCountry":"GL"}';
        var b = '{"id":2,"userId":1000008,"currencyFrom":"NOK","currencyTo":"DKK","amountSell":1346.05,"amountBuy":1985.05,"rate":0.63,"timePlaced":"11-JUN-2015 01:15:40","originatingCountry":"NO"}';
        var c = '{"id":3,"userId":1000013,"currencyFrom":"USD","currencyTo":"AUD","amountSell":1174.14,"amountBuy":1622.72,"rate":0.51,"timePlaced":"11-JUN-2015 04:07:34","originatingCountry":"JP"}';
        var d = [];
        d.push(a);
        d.push(b);
        d.push(c);
        httpBackend.when('GET', url).respond(200, d);
        scope.loadData();
        httpBackend.flush();
    });
});
