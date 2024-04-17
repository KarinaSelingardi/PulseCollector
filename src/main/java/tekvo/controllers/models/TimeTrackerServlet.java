package tekvo.controllers.models;

import javax.servlet.ServletException;
//Um Servlet é uma classe Java que é usada para estender as capacidades de um servidor hospedeiro.
// Ele funciona como um controlador no modelo de desenvolvimento de aplicativos da web Java,
// permitindo a criação de aplicativos dinâmicos que respondem a solicitações HTTP.
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
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
        String requestBody = getRequestBody(request);

        // Converte o JSON para um objeto TimeData
        TimeData timeData = convertJsonToTimeData(requestBody);

        if (timeData != null) {
            // Obtém o tempo gasto do objeto TimeData
            long timeSpent = timeData.getTimeSpent();
            try (Connection conexao = ConexaoBancoDeDados.obterConexao()) {
                // Prepare a instrução SQL para inserir o tempo na tabela
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
                // Responde ao cliente com erro interno do servidor
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }

            System.out.println("Tempo de permanência na página: " + timeSpent + " milissegundos");
            // Responde ao cliente com sucesso
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            // Responde ao cliente com requisição inválida
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }
        return requestBody.toString();
    }

    private TimeData convertJsonToTimeData(String json) {
        try {
            // instância de Gson para desserializar o JSON
            Gson gson = new Gson();
//a desserialização é utilizada para converter a string JSON recebida do cliente
// de volta para um objeto Java da classe TimeData, para que os dados possam ser
// manipulados e utilizados pelo sistema. Isso é realizado usando a biblioteca Gson,
// que é capaz de desserializar JSON em objetos Java e vice-versa.
            // Desserialização do JSON para um objeto TimeData
            TimeData timeData = gson.fromJson(json, TimeData.class);

            // Validação do objeto desserializado
            if (timeData != null && timeData.getTimeSpent() >= 0) {
                return timeData;
            }
        } catch (Exception e) {
            // Trata qualquer exceção que possa ocorrer durante a desserialização
            e.printStackTrace();
        }
        return null;
    }
}
