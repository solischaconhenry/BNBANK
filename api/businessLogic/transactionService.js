/**
 * Created by usuario on 3/6/2016.
 */
var repository = require('../dataAccess/repository.js');


exports.getTransaction = function(callback) {
    var params = {
        query: {},
        collection: 'transactions'
    };
    repository.getCollection(params, function(data){
        callback(data);
    });
};

exports.getTransactionById = function(idTransaction, callback) {
    var params = {
        query: {_id: idTransaction},
        collection: 'transactions'
    };
    repository.getDocument(params, function(data){
        callback(data);
    });
};
exports.newTransaction = function(doc, callback) {
    var params = {
        query: doc,
        collection: 'transactions'
    };
    repository.addDocument(params, function(res) {
        callback(res);
    });
}

exports.editTransaction = function(data, callback) {
    var params = {
        query: {_id: data.idTransaction},
        updateQuery: {$set: {date: data.date, type: data.type, rode: data.rode}},
        collection: 'transactions'
    };
    console.log(data);
    repository.updateDocument(params, function(res) {
        callback(res);
    });
}

exports.deleteTransaction = function(idTransaction, callback) {
    var params = {
        query: {_id: idTransaction},
        collection: 'transactions'
    };
    repository.deleteDocument(params, function(res) {
        callback(res);
    });
}

exports.disableTransaction = function(data, callback) {
    var params = {
        query: {_id: data.idTransaction},
        updateQuery: {$set: {active: data.active}},
        collection: 'transactions'
    };

    repository.updateDocument(params, function(res) {
        callback(res);
    });
};

