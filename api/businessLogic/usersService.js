var repository = require('../dataAccess/repository.js');


exports.getUsers = function(callback) {
	var params = {
		query: {},
		collection: 'users'
	};
	repository.getCollection(params, function(data){
	        callback(data);
	});
};

exports.getUserById = function(user, callback) {
	var params = {
		query: {user: user},
		collection: 'users'
	};
	repository.getDocument(params, function(data){
		callback(data);
	});
};

exports.newUser = function(doc, callback) {
	var params = {
		query: doc,
		collection: 'users'
	};
	repository.addDocument(params, function(res) {
		callback(res);
	});
}

