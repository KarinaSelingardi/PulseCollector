
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexãoBancoDeDados{



        // Configurações de conexão com o banco de dados
        private static final String URL = "jdbc:mysql://localhost:3306/meu-mysql";
        private static final String USUARIO = "d92bdc6040a71fa13bcc06735939465f6265f2bb54fe69a7088954c032e475b8";
        private static final String SENHA = "minha_senha";

        // Método para obter uma conexão com o banco de dados
        public static Connection obterConexao() throws SQLException {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        }
    }


