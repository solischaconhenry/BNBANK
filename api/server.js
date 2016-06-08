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
Devuelve todos los eventos
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
Otorga o revoca privilegios de administrador de un usuario
  Entrada: 
        idUsuario,    // id del evento que se busca
        admin // 0 si se quiere quitar privilegios de administrador, 1 si se quiere hacer administrador
  Salida: 
        { success   // éxito: true, fracaso: false
           data        // éxito: null, fracaso: null
           statusCode // éxito: 200, fracaso: 400
        }
  */
app.post('/api/sugef/users/new', usersController.newUser);

/*
Devuelve todos los usuarios
  Entrada: ninguna
  Salida: 
        { success   // éxito: true, fracaso: false
           data        // Array con la información de todos los usuarios
           statusCode // éxito: 200, fracaso: 400
        }
  */
app.get('/api/sugef/users/all', usersController.getUsers);



/*
 Devuelve todos los usuarios
 Entrada: ninguna
 Salida:
 { success   // éxito: true, fracaso: false
 data        // Array con la información de todos los usuarios
 statusCode // éxito: 200, fracaso: 400
 }
 */
app.get('/api/sugef/users/:user', usersController.getUserById);



 

server.listen(port, function(){
  console.log('Server listening on port: ' + port);
});