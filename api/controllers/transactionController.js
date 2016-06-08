/**
 * Created by usuario on 3/6/2016.
 */
var transactionService = require('../businessLogic/transactionService.js');

exports.getTransaction = function(eRequest, eResponse) {
    transactionService.getTransaction(function(data){
        eResponse.send(data);
    });
};

exports.getTransactionById = function(eRequest, eResponse) {
    transactionService.getTransactionById(eRequest.params.id, function(data){
        eResponse.send(data);
    });
};

exports.newTransaction = function(eRequest, eResponse) {
    transactionService.newTransaction(eRequest.body, function(data){
        eResponse.send(data);
    });
};

exports.editTransaction = function(eRequest, eResponse) {
    transactionService.editTransaction(eRequest.body, function(data){
        eResponse.send(data);
    });
};

exports.deleteTransaction = function(eRequest, eResponse) {
    transactionService.deleteTransaction(eRequest.params.idTransaction, function(data){
        eResponse.send(data);
    });
};

exports.disableTransaction = function(uRequest, uResponse) {
    transactionService.disableTransaction(uRequest.body, function(data){
        uResponse.send(data);
    });
};

