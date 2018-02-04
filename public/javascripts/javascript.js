var seleccionado='';
var puedoPulsar=true;
var table;
var evento;



function dibujarTabla(datos){

    $("#listado").DataTable().destroy();
    $("#listado").show();

    table = $("#listado")
        .DataTable({ language: { url: '/assets/jsons/Spanish.json'},
            pagingType: 'full_numbers',
            data:       datos,
            columns:    [
                        { data: 'id' },
                        { data: 'firstName' },
                        { data: 'lastName' },
                        { data : function ( row, type, val, meta ) {
                                    return '<button id="borrar" class="borrable"><i class="material-icons">delete</i></button>';
                                 }
                        }
                        ]
        });

    $("div#listado_filter input[type=search]").focus();
 }


//BOTÓN LISTAR

function listar(){

    $("div.valor").html("");
    $("table").show();

    ajax_listar();
};

//PARA MODIFICAR un dato pulsamos en cualquier zona de la fila de la tabla

$(document).on("click", "td:nth-child(1),td:nth-child(2),td:nth-child(3)", function (event){
    var lineaSeleccionada = ($(this).parent("tr"));
    seleccionado = (lineaSeleccionada[0].childNodes[0].textContent);
    var id = lineaSeleccionada[0].childNodes[0].textContent;
    var firstName = lineaSeleccionada[0].childNodes[1].textContent;
    var lastName = lineaSeleccionada[0].childNodes[2].textContent;
    datos={};
    datos[0] = { "id":        id,
                 "firstName": firstName,
                 "lastName":  lastName
                };

    $("#listado").DataTable().destroy();
    $("table#listado").hide();

    var modificar ='<p class="insertado">Actualización de Datos</p>\n\
    <div class="pintarId">'+datos[0].id+'</div><label class="modificado">firstName</label>\n\
    <input class="modificado" type="text" id="firstName" tabindex="1" value="'+datos[0].firstName+'" autofocus="autofocus"><br>';
    modificar +='<div class="modificado"><button id="actualizar" class="modificado"><i class="material-icons">input</i></button></div>\n\
    <label class="modificado">lastName</label><input class="modificado" type="text" id="lastName" tabindex="2" value="'+datos[0].lastName+'">';

    $("div.valor").html("").show();
    $("div.valor").append(modificar).show();
    $("input#firstName").focus();

});

// ACTUALIZAR DATOS

function actualizar(){

    var id = ($("div.pintarId").html());
    var nombre = ($("input#firstName").val());
    var apellido = ($("input#lastName").val());

    ajax_modificarButton(id, nombre, apellido);

    $("div.valor").html("").show();

};

//BOTÓN INSERTAR

function insertar(){

    $("#listado").DataTable().destroy();
    $("table").hide();

    var insertar ='<p class="insertado">Insertar Datos</p>\n\
    <label class="insertado">firstName</label>\n\
    <input class="insertado" type="text" id="firstName" tabindex="1" value="" autofocus="autofocus"><br>';
    insertar +='<div class="insertado"><button id="crearNuevo" class="insertado"><i class="material-icons">input</i></button></div>\n\
    <label class="insertado">lastName</label><input class="insertado" type="text" id="lastName" tabindex="2" value="">';

    $("div.valor").html("").show();
    $("div.valor").append(insertar).show();
    $("input#firstName").focus();
}

 //CREAR DATOS, SE LLAMA DESPUES DE HABERLOS INSERTADOS CON LA FUNCION  insertar();

function crearNuevo(){

    var nombre = $("input#firstName").val();
    var apellido = $("input#lastName").val();

    ajax_insertar(nombre,apellido);

    $("div.valor").html("").show();
    $("table").show();
};



//Funcionalidad al botón "buscar" en la base de datos.

function buscar(){
    $("tbody>tr>td").html("");
    $("thead>tr>th").html("");
    $("div.valor").html("");
    $("table").show();
    var nombre = $("input#inputBuscar").val();
    //var apellido  = $("input#inputBuscar").val();
    if (nombre) {
        ajax_inputBuscar(nombre);
        $("input#inputBuscar").val("");
    } 
}

// BOTÓN BORRAR - ICONO ROJO PAPELERA

    function borrar(){

        var id = evento.target.parentElement.parentElement.firstElementChild.textContent;

        ajax_borrar(id);
    }



//  Agrupo todas las ****** FUNCIONES AJAX ******

//Hace la petición de buscar datos en la base de datos le enviamos el firstName y/o lastName y nos devuelve las coincidencias

function ajax_inputBuscar(firstName){
    $.ajax({
        url:        "/persons/"+firstName,
        type:       "GET",
        datatype:   "json",
       // contentType:"application/json; charset=utf-8",
        //data:       JSON.stringify ( {"firstName" : firstName} ),
        success:    function(data){
                        dibujarTabla(data);
                    }
    }); 
};

function ajax_modificarButton(id, firstName, lastName){
    $.ajax({
        url:        "/persons/"+id,
        type:       "PUT",
        datatype:   "json",
        contentType:"application/json; charset=utf-8",
        data:       JSON.stringify ({
                        "id":        id,
                        "firstName": firstName,
                        "lastName":  lastName
                    }),
        success:    function(data) {  dibujarTabla(data); }
    });
};

function ajax_listar(){
    $.ajax({
        url:        "/persons",
        type:       "GET",
        datatype:   "json",
        success:    function(data) { dibujarTabla(data); }
    }) 
    .done(function( data, textStatus, jqXHR ) {
        if ( console && console.log ) {
            console.log( "La solicitud se ha completado correctamente." );
        }
     })
    .fail(function( jqXHR, textStatus, errorThrown ) {
        if ( console && console.log ) {
            console.log( "La solicitud a fallado: " +  textStatus);
        }
    });
};


function ajax_insertar(firstName,lastName){
    $.ajax({
        url:        "/persons/create",
        type:       "POST",
        datatype:   "json",
        contentType:"application/json; charset=utf-8",
        data:       JSON.stringify ( {"firstName" : firstName, "lastName" :  lastName }),
        success:    function(data){
                        dibujarTabla(data);
                        //        ******* ACTIVAR CUANDO ESTE FUNCIONANDO inputBuscar  *******
                        // ajax_inputBuscar($("input#firstName").val(),$("input#lastName").val());
                        }
    });
};

function ajax_borrar(id){
    $.ajax({
        url:        "/persons/delete/"+id,
        type:       "DELETE",
        datatype:   "json",
        contentType:"application/json; charset=utf-8",
        data:       JSON.stringify ({"id": id }),
        success:    function(data) { dibujarTabla(data);}
    });
};




$(document).ready(function(){
    ajax_listar();
});

$("body").on("click",function(e){
console.log(e.target.id);
    evento=e;
    switch (e.target.id) {
        case "actualizar":
            actualizar();
            break;
        case "insertar":
            insertar();
            break;
        case "crearNuevo":
            crearNuevo();
            break;
        case "listar":
            listar();
            break;
        case "borrar":
            borrar();
            break;
        case "buscar":
            buscar();
            break;
    }



});
 
 
    





           
