Minuta del Miercoles 25 de Julio - La Panna

  * Cuando la planificación se termina, se cambia el estado (ya no se puede modificar) y se pasa a Lanzamiento (que va a estar unido a la ejecucion), va a ser un checklist de que recursos tenes y cuales de no, si tenes que modificar algo, te va a llevar directamente al modificar de ese recurso que está en la ejecución.

  * La ventana de lanzamiento, tiene un listado ( filtrado por los 4 tipos de recursos ) que muestra todo los recursos planificados, permite:
    * Cambiar el estado a cada item (lo que antes era un checklist, ahora son algunos estados configurables en el codigo como: "Listo", "No listo", "Pedido", "En espera", "Por recibir").
    * Editarlos (ejemplo: se murio un operario, hay que cambiarlo por otro. En este caso, se llama al editar Personas, PERO DE EJECUCION).
    * Crear ordenes de compra. Puede (como teniamos antes) hacer todas las ordenes de compra relacionadas con un proveedor por obra, para que lo haga rapido.

  * Las tablas de lanzamiento se guardan en un String en cada uno de los recursos de Ejecucion, no hay mas cambios que esos. Cada campo guarda el estado que el usuario indico para ese recurso.

  * Fede se la come

  * Cuando se pasa a ejecucion (copia masiva) , antes hay que limpiar todos los recursos que no se usen.

  * Union de baul de recursos de Ejecucion con la ejecucion misma (solo IDEA, hay que pensarla muy bien).

  * Ahora comenzamos a programar compras y lanzamiento con Emma, mientras Fran termina planificación. ( Esteban tiene que terminar Recurso/RecursoEspecifico y deja HerramientaDeEmpresa para hacer a la par de compras xq necesita el stock).