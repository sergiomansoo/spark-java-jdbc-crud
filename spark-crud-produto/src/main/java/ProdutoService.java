	import com.google.gson.Gson;
	import java.util.List;
	import static spark.Spark.*;
	public class ProdutoService {
	    private ProdutoDAO produtoDAO;
	    private Gson gson;

	    public ProdutoService() {
	        produtoDAO = new ProdutoDAO();
	        gson = new Gson();
	        setupRoutes();
	    }

	    private void setupRoutes() {
	       
	        staticFiles.location("/public");

	       
	        get("/", (request, response) -> {
	            response.redirect("/index.html");
	            return null;
	        });

	 
	        post("/produtos", (request, response) -> {
	            response.type("application/json");
	            Produto newProduto = gson.fromJson(request.body(), Produto.class);
	            int id = produtoDAO.insert(newProduto);
	            if (id != -1) {
	                newProduto.setId(id); // Define o ID gerado pelo banco
	                response.status(201); // Created
	                return gson.toJson(newProduto);
	            } else {
	                response.status(500); // Internal Server Error
	                return gson.toJson(new Message("Erro ao adicionar produto."));
	            }
	        });

	        // R (Read) - Obter um produto pelo ID
	        get("/produtos/:id", (request, response) -> {
	            response.type("application/json");
	            int id = Integer.parseInt(request.params(":id"));
	            Produto produto = produtoDAO.get(id);
	            if (produto != null) {
	                return gson.toJson(produto);
	            } else {
	                response.status(404); // Not Found
	                return gson.toJson(new Message("Produto não encontrado."));
	            }
	        });

	        // R (Read All) - Obter todos os produtos
	        get("/produtos", (request, response) -> {
	            response.type("application/json");
	            List<Produto> produtos = produtoDAO.getAll();
	            return gson.toJson(produtos);
	        });

	        // U (Update) - Atualizar um produto existente
	        put("/produtos/:id", (request, response) -> {
	            response.type("application/json");
	            int id = Integer.parseInt(request.params(":id"));
	            Produto produtoToUpdate = gson.fromJson(request.body(), Produto.class);
	            produtoToUpdate.setId(id); // Garante que o ID do objeto corresponde ao ID da URL

	            if (produtoDAO.update(produtoToUpdate)) {
	                response.status(200); // OK
	                return gson.toJson(new Message("Produto atualizado com sucesso."));
	            } else {
	                response.status(404); // Not Found ou erro interno
	                return gson.toJson(new Message("Erro ao atualizar produto ou produto não encontrado."));
	            }
	        });

	        // D (Delete) - Excluir um produto
	        delete("/produtos/:id", (request, response) -> {
	            response.type("application/json");
	            int id = Integer.parseInt(request.params(":id"));
	            if (produtoDAO.delete(id)) {
	                response.status(200); // OK
	                return gson.toJson(new Message("Produto excluído com sucesso."));
	            } else {
	                response.status(404); // Not Found
	                return gson.toJson(new Message("Produto não encontrado para exclusão."));
	            }
	        });

	        exception(Exception.class, (e, request, response) -> {
	            response.status(500);
	            response.body(gson.toJson(new Message("Erro interno do servidor: " + e.getMessage())));
	        });
	    }

	    // Classe auxiliar para mensagens de resposta
	    class Message {
	        String message;

	        public Message(String message) {
	            this.message = message;
	        }
	    }
	}

