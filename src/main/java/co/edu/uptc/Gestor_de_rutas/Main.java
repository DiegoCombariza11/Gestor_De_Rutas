package co.edu.uptc.Gestor_de_rutas;

import co.edu.uptc.Gestor_de_rutas.controller.GraphController;

public class Main {
    public static void main(String[] args) {

       GraphController graphController = new GraphController();

       graphController.createGraph();
        System.out.println(graphController.getNodes());



    }

}
