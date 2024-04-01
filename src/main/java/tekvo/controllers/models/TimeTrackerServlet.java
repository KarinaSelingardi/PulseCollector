package tekvo.controllers.models;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.Gson; // Importe a classe Gson

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


        System.out.println("Tempo de permanência na página: " + timeSpent + " milissegundos");
        //armazenar o tempo em um banco de dados:
         myRepository.saveTime(timeSpent);

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
