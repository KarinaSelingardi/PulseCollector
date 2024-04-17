package tekvo.controllers.models;

// Classe que representa os dados de tempo
public class TimeData {
    private long timeSpent; // Variável para armazenar o tempo gasto

    // Construtor padrão (sem parâmetros)
    public TimeData() {
        // Nenhuma inicialização especial necessária no construtor padrão
    }

    // Construtor que aceita um parâmetro para inicializar o tempo gasto
    public TimeData(long timeSpent) {
        this.timeSpent = timeSpent; // Define o tempo gasto com o valor fornecido
    }

    // Método para obter o tempo gasto
    public long getTimeSpent() {
        return timeSpent; // Retorna o tempo gasto
    }

    // Método para definir o tempo gasto
    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent; // Define o tempo gasto com o valor fornecido
    }
}
