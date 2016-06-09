bodyParser = require('body-parser');
var transactionController = require('./controllers/transactionController.js'),
       usersController = require('./controllers/usersController.js');

//-------------------------------------------------------------------------
var express       = require('express'),
      app              = express(),
      server          = require('http').createServer(app),
      port              = 9000;
//-------------------------------------------------------------------------

app.use(bodyParser.urlencoded({ extended: false }));

// parse application/json
app.use(bodyParser.json());


//define the location of the main directory
//app.use('/', express.static(__dirname + '/app'));

app.use(function (req, res, next) {
  next();
});

//End: Server configuration

//Start: Routing

/*
Return all the transactions
  Input: nothing
  Output:
        { success   // success: true, failure: false
           data        // Array with the information of the all transactions
           statusCode // success: 200, failure: 400
        }
  */
app.get('/api/sugef/transaction/all', transactionController.getTransaction);


/*
Return an unique transaction
 Input:
        id:     // transaction id
 Output:
        { success   // success: true,failure: false
           data        // success: transaction information sought, failure: null
           statusCode // success: 200, failure: 400
        }
  */
app.get('/api/sugef/transaction/:id', transactionController.getTransactionById);

/*
Add a new transaction
 Input:
        {
           user, //  owner of the transaction
           date, // date of the transaction
           type // type of transaction
           rode // rode of the transaction
        }
 Output:
        { success   // success: true, failure: false
           data        // success: transaction id inserted, failure: null
           message // success: 200, failure: 400
        }
  */
app.post('/api/sugef/transaction/new', transactionController.newTransaction);

/*
Edit an transaction
 Input:
        {

         date, // date of the transaction
         type // type of  transaction
         rode // rode of the transaction
         id, //  transaction id
        }
 Output:
        { success   // success: true, failure: false
           data        // success: null, failure: null
           message // success: 200, failure: 400
        }
  */
app.put('/api/sugef/transaction/edit', transactionController.editTransaction);

/*
Delete a transaction
  Input:
        {
           id    // id of the transaction to delete
        }
  Output:
        { success   // success: true, failure: false
           data        // success: null, failure: null
           message // success: 200, failure: 400
        }
  */
app.delete('/api/sugef/transaction/delete/:idTransaction', transactionController.deleteTransaction);


/*

 Enable or Disable a transaction
 Input:
        idTransaction,    // transaction id that is sought
        active // 0  if you want to Disable, 1 if you want to Enable
 Output:
        { success   // success: true, failure: false
           data        // success: null, failure: null
           statusCode // success: 200, failure: 400
        }
  */
app.put('/api/sugef/transaction/disable', transactionController.disableTransaction);


/*
 Create a new user
  in:
        username   // username unique
        password  // password of the user
        email // email address of the user
        type

  out:
        { success   // success: true, failure: false
           data        // success: null, failure: null
           statusCode // success: 200, failure: 400
        }
  */
app.post('/api/sugef/users/new', usersController.newUser);

/*
Return all the users
  in: nothing
  out:
        { success   // success: true, fail: false
           data        // Array with the information of all the users
           statusCode // success: 200, fail: 400
        }
  */
app.get('/api/sugef/users/all', usersController.getUsers);



/*
 Return all the users info
 IN: username
 Out:
 { success   // success: true, fail: false
 data        // Array with the all information of the users
 statusCode // success: 200, fail: 400
 }
 */
app.get('/api/sugef/users/:user', usersController.getUserById);


/*
 Return all the users info
 IN: username
 Out:
 { success   // success: true, fail: false
 data        // Array with the all information of the users
 statusCode // success: 200, fail: 400
 }
 */

app.post('/api/sugef/users/login', usersController.loginUser);
/*
 Return user info of a consult of user with their pass
 In: {username:"data", password:""}
 Out:
 { success   // success: true, fail: false
 data        // Array with the information of the user
 statusCode // success: 200, fail: 400, fail search:404
 }
 */

 

server.listen(port, function(){
  console.log('Server listening on port: ' + port);
});