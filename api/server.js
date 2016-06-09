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


//define ubición del directorio principal
//app.use('/', express.static(__dirname + '/app'));

app.use(function (req, res, next) {
  next();
});

//End: Server configuration

//Start: Routing

/*
Return all the events
  Entrada: ninguna
  Salida: 
        { success   // éxito: true, fracaso: false
           data        // Array con la información de todos los eventos
           statusCode // éxito: 200, fracaso: 400
        }
  */
app.get('/api/sugef/transaction/all', transactionController.getTransaction);


/*
Devuelve un único evento
  Entrada: 
        id:     // id del evento que se busca
  Salida: 
        { success   // éxito: true, fracaso: false
           data        // éxito: información del evento buscado, fracaso: null
           statusCode // éxito: 200, fracaso: 400
        }
  */
app.get('/api/sugef/transaction/:id', transactionController.getTransactionById);

/*
Agrega un nuevo evento
  Entrada: 
        {
           tipoEvento, // tipo del evento a crear
           nombre,      // nombre del evento
           descripcion // descripción del evento
        }
  Salida: 
        { success   // éxito: true, fracaso: false
           data        // éxito: id del evento insertado, fracaso: null
           message // éxito: 200, fracaso: 400
        }
  */
app.post('/api/sugef/transaction/new', transactionController.newTransaction);

/*
Edita un evento
  Entrada: 
        {
           idEvento,    // id del evento a editar, para ubicar el evento <-- Parámetro
           tipoEvento, // carrera o caminata
           nombre,     // carrera o caminata
           descripcion // descripción del evento
        }
  Salida: 
        { success   // éxito: true, fracaso: false
           data        // éxito: null, fracaso: null
           message // éxito: 200, fracaso: 400
        }
  */
app.put('/api/sugef/transaction/edit', transactionController.editTransaction);

/*
Elimina un evento
  Entrada: 
        {
           idEvento    // id del evento a editar, para ubicar el evento
        }
  Salida: 
        { success   // éxito: true, fracaso: false
           data        // éxito: null, fracaso: null
           message // éxito: 200, fracaso: 400
        }
  */
app.delete('/api/sugef/transaction/delete/:idTransaction', transactionController.deleteTransaction);


/*
Bloquea o desbloquea un usuario
  Entrada: 
        idUsuario,    // id del evento que se busca
        bloqueado // 0 si se quiere desbloquear, 1 si se quiere bloquear
  Salida: 
        { success   // éxito: true, fracaso: false
           data        // éxito: null, fracaso: null
           statusCode // éxito: 200, fracaso: 400
        }
  */
app.put('/api/sugef/transaction/disable', transactionController.disableTransaction);


/*
Available or Disable a transaction
  in:
        ,    // id del evento que se busca
        admin // 0 si se quiere quitar privilegios de administrador, 1 si se quiere hacer administrador
  out:
        { success   // éxito: true, fracaso: false
           data        // éxito: null, fracaso: null
           statusCode // éxito: 200, fracaso: 400
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