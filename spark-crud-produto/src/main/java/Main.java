import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        // Configura a porta do servidor Spark
        port(4567);

        // Inicializa o serviço de produtos, que configura as rotas
        new ProdutoService();

        System.out.println("Servidor Spark iniciado na porta 4567.");
        System.out.println("Acesse: http://localhost:4567");
    }
}