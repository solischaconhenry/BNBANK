var usersService = require('../businessLogic/usersService.js'); 

exports.getUsers = function(eRequest, eResponse) {
    usersService.getUsers(function(data){
        eResponse.send(data);
    });
};

exports.getUserById = function(eRequest, eResponse) {
    usersService.getUserById(eRequest.params.user, function(data){
        eResponse.send(data);
    });
};

exports.newUser = function(eRequest, eResponse) {
    usersService.newUser(eRequest.body, function(data){
        eResponse.send(data);
    });
};

exports.loginUser = function(eRequest, eResponse) {
    usersService.postLogin(eRequest.body, function(data){
        eResponse.send(data);
    });
};

