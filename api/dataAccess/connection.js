var fs = require('fs'); 
var MongoClient = require('mongodb').MongoClient,
             ObjectID = require('mongodb').ObjectID;

var readJsonFile = function() {
    //read the config file of DB
    var connectionParams = fs.readFileSync("./configuration.json");
    var dbConfig = {};

    try {
        //parse to json
        dbConfig = JSON.parse(connectionParams);
    } 
    catch (err) {
        console.log(err);
    }
    return dbConfig;
};

var setObjectId = function(params) {
    if (params.query._id){
        params.query._id = ObjectID(params.query._id);
    }
    return params;
}

exports.findDocuments = function(params, callback) {
    params = setObjectId(params);
    /*
    params = {
            collection, //Collection name
            query // query to execute
    }
    */
    var dbUrl = 'mongodb://';
    var dbConfig = readJsonFile();
    // Make url connection
    dbUrl = dbUrl.concat(dbConfig.params.host , ':',  dbConfig.params.port,  '/' , dbConfig.params.database);
    //Use the method connect fot connect the server
    MongoClient.connect(dbUrl, function(err, db){
        //take back the required collection
        console.log('MongoClient: params');
        console.log(params);
          var collection = db.collection(params.collection);
          // find the documents
          collection.find(params.query).toArray(function(err, docs) {
            var res;
                if (err){ //connection error
                    res = { //connection error
                        success: false,
                        data: null,
                        statusCode: 400
                    };
                }
                else{ //success
                     if (docs.length > 0) {
                        res = {
                            success: true,
                            data: docs,
                            statusCode: 200
                        };    
                    }else{ //registers not found
                        res = {
                            success: true,
                            data: [],
                            statusCode: 200
                        };
                    }
                }
                db.close(); 
                callback(res);
          }); 
    });
};

exports.findOneDocument = function(params, callback) {
    params = setObjectId(params);
    var dbUrl = 'mongodb://';
    var dbConfig = readJsonFile();
    // make the url connection
    dbUrl = dbUrl.concat(dbConfig.params.host , ':',  dbConfig.params.port,  '/' , dbConfig.params.database);
    //use the method connection for connect the server
    MongoClient.connect(dbUrl, function(err, db){
        //take back the request collection
          var collection = db.collection(params.collection);
          //find the documents
          collection.findOne(params.query, function(err, doc) {
            var res;
                if (err){ //error connection
                    res = {
                        success: false,
                        data: null,
                        statusCode: 400
                    };
                }
                else{ //success
                    if (doc) {
                        res = {
                            success: true,
                            data: doc,
                            statusCode: 200
                        };    
                    }else{ //registers not found
                        res = {
                            success: true,
                            data: doc,
                            statusCode: 404
                        };
                    }
                    
                }
                db.close(); 
                callback(res);
          }); 
    });
};



exports.insertOneDocument = function(params, callback) {
    params = setObjectId(params);
    var dbUrl = 'mongodb://';
    var dbConfig = readJsonFile();
    // make the url connection
    dbUrl = dbUrl.concat(dbConfig.params.host , ':',  dbConfig.params.port,  '/' , dbConfig.params.database);

    // Use the connect method fot connect the bd
    MongoClient.connect(dbUrl, function(err, db){
        //recovering the required collection
          var collection = db.collection(params.collection);
          //insert a document
          collection.insert(params.query, function(err, result) {
            var res;
                if (err){ //connection fail
                    res = {
                        success: false,
                        data: null,
                        statusCode: 400
                    };
                }
                else{ //success
                    res = {
                        success: true,
                        data: null,
                        statusCode: 200
                    };
                }
                db.close(); 
                callback(res);
          }); 
    });
};


exports.insertDocuments = function(params, callback) {
    params = setObjectId(params);
    var dbUrl = 'mongodb://';
    var dbConfig = readJsonFile();
    //the connection URL is created
    dbUrl = dbUrl.concat(dbConfig.params.host , ':',  dbConfig.params.port,  '/' , dbConfig.params.database);

    // Usa el método connect para conectar al servidor
    MongoClient.connect(dbUrl, function(err, db){
        //Use the connect method to connect to the server
          var collection = db.collection(params.collection);
          // insert the documents
          console.log('console.log(params.documents);');
          console.log(params.documents);
          collection.insertMany(params.documents, function(err, result) {
            var res;
                if (err){ //connection fail
                    res = {
                        success: false,
                        data: null,
                        statusCode: 400
                    };
                }
                //all documents were not inserted
                else if (result.result.n != params.documents.length || result.ops.length != params.documents.length){
                    res = {
                        success: false,
                        data: null,
                        statusCode: 400
                    };
                }else{  //éxito
                    res = {
                        success: true,
                        data: null,
                        statusCode: 200
                    };
                }
                db.close(); 
                callback(res);
          }); 
    });
};

exports.updateOneDocument = function(params, callback) {
    params = setObjectId(params);
    var dbUrl = 'mongodb://';
    var dbConfig = readJsonFile();
    // the url connection is created
    dbUrl = dbUrl.concat(dbConfig.params.host , ':',  dbConfig.params.port,  '/' , dbConfig.params.database);

    // tuse the connect method fot connect to the server
    MongoClient.connect(dbUrl, function(err, db){
        //recovering the required collection
          var collection = db.collection(params.collection);
          // update the document
          collection.updateOne(params.query, params.updateQuery, function(err, result) {
            var res;
                if (err){ //connection fail
                    res = {
                        success: false,
                        data: null,
                        statusCode: 400
                    };
                }
                else{ //success
                    res = {
                        success: true,
                        data: null,
                        statusCode: 200
                    };
                }
                db.close(); 
                callback(res);
          }); 
    });
};

exports.deleteOneDocument = function(params, callback) {
    params = setObjectId(params);
    var dbUrl = 'mongodb://';
    var dbConfig = readJsonFile();
    // the url connection is created
    dbUrl = dbUrl.concat(dbConfig.params.host , ':',  dbConfig.params.port,  '/' , dbConfig.params.database);

    // Use the connect method for connect to the DB
    MongoClient.connect(dbUrl, function(err, db){
        //recovering the required documentation
          var collection = db.collection(params.collection);
          // Se elimina los documentos
          collection.deleteOne(params.query, function(err, result) {
            var res;
                if (err){ //fail connection
                    res = {
                        success: false,
                        data: null,
                        statusCode: 400
                    };
                }
                else{ //success
                    res = {
                        success: true,
                        data: null,
                        statusCode: 200
                    };
                }
                db.close(); 
                callback(res);
          }); 
    });
};

exports.deleteDocuments = function(params, callback) {
    params = setObjectId(params);
    var dbUrl = 'mongodb://';
    var dbConfig = readJsonFile();
    // the url connection is created
    dbUrl = dbUrl.concat(dbConfig.params.host , ':',  dbConfig.params.port,  '/' , dbConfig.params.database);

    // Use the connect method for connect to the DB
    MongoClient.connect(dbUrl, function(err, db){
        //recovering the required documentation
          var collection = db.collection(params.collection);
          // find documents
          collection.remove(params.query, function(err, result) {
            var res;
                if (err){ //fail connection
                    res = {
                        success: false,
                        data: null,
                        statusCode: 400
                    };
                }
                else{ //success
                    res = {
                        success: true,
                        data: null,
                        statusCode: 200
                    };
                }
                db.close(); 
                callback(res);
          }); 
    });
};

exports.findForLogin = function(params, callback) {
    params = setObjectId(params);
    var dbUrl = 'mongodb://';
    var dbConfig = readJsonFile();
    // make the url connection
    dbUrl = dbUrl.concat(dbConfig.params.host , ':',  dbConfig.params.port,  '/' , dbConfig.params.database);
    //use the method connection for connect the server
    MongoClient.connect(dbUrl, function(err, db){
        //take back the request collection
        var collection = db.collection(params.collection);
        //find the documents
        collection.findOne(params.query, function(err, doc) {
            var res;
            if (err){ //error connection
                res = {
                    success: false,
                    data: null,
                    statusCode: 400
                };
            }
            else{ //success
                if (doc) {
                    res = {
                        success: true,
                        data: doc,
                        statusCode: 200
                    };
                }else{ //registers not found
                    res = {
                        success: true,
                        data: doc,
                        statusCode: 404
                    };
                }

            }
            db.close();
            callback(res);
        });
    });
};
