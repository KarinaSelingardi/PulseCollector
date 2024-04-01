package tekvo.controllers.models;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.google.gson.Gson;

@WebServlet("/track-time")
public class TimeTrackerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recebe o JSON enviado pelo cliente
        String requestBody = request.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);

        // Converte o JSON para um objeto TimeData
        TimeData timeData = convertJsonToTimeData(requestBody);

        // Obtém o tempo gasto do objeto TimeData
        long timeSpent = timeData.getTimeSpent();

        // Conecta ao banco de dados
        try (Connection conexao = ConexãoBancoDeDados.obterConexao()) {
            // Prepare a instrução SQL para insert o tempo na tabela
            String sql = "INSERT INTO tempos (tempo_gasto) VALUES (?)";
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                // Substitui o parâmetro na instrução SQL pelo tempo gasto
                stmt.setLong(1, timeSpent);
                // Executa a instrução SQL
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            // Trata qualquer exceção que possa ocorrer ao interagir com o banco de dados
            e.printStackTrace();
        }

        System.out.println("Tempo de permanência na página: " + timeSpent + " milissegundos");

        // Responde ao cliente
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private TimeData convertJsonToTimeData(String json) {
        // instância de Gson para desserializar o JSON
        Gson gson = new Gson();

        // Desserialização  do JSON para um objeto TimeData
        TimeData timeData = gson.fromJson(json, TimeData.class);

        return timeData;
    }
}

