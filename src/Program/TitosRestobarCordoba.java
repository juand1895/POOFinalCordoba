package Program;
import Program.View.ViewProduct;

import Program.Controller.ControllerTable;
import Program.Model.Table;
import Program.View.ViewMenu;
import java.sql.SQLException;



public class TitosRestobarCordoba {

    public static void main(String[] args) {   // Iniciar la ventana de mesas
        java.awt.EventQueue.invokeLater(() -> {
            new ViewMenu().setVisible(true);  // Abre ViewProduct
           // new ViewTable().setVisible(true);  // Esto abre ViewTable
        });
    }
}
