import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private Connection conexao;

    // Construtor para estabelecer a conexão com o banco de dados
    public ProdutoDAO() {
        try {
            Class.forName("org.postgresql.Driver"); // Carrega o driver JDBC
           
            conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/exercicio_spark", "postgres", "postgres");
            System.out.println("Conexão com o PostgreSQL estabelecida com sucesso!");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro ao carregar o driver JDBC do PostgreSQL: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados PostgreSQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para fechar a conexão
    public void close() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("Conexão com o PostgreSQL fechada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // C (Create) - Insere um novo produto no banco de dados
    public int insert(Produto produto) {
        String sql = "INSERT INTO produto (nome, descricao, preco) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, produto.getNome());
            statement.setString(2, produto.getDescricao());
            statement.setDouble(3, produto.getPreco());
            statement.executeUpdate();

            // Pega o ID gerado pelo banco de dados
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Retorna o ID do produto inserido
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir produto: " + e.getMessage());
            e.printStackTrace();
        }
        return -1; // Retorna -1 em caso de erro
    }

    // R (Read) - Busca um produto pelo ID
    public Produto get(int id) {
        String sql = "SELECT id, nome, descricao, preco FROM produto WHERE id = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nome = resultSet.getString("nome");
                    String descricao = resultSet.getString("descricao");
                    double preco = resultSet.getDouble("preco");
                    return new Produto(id, nome, descricao, preco);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // R (Read All) - Busca todos os produtos
    public List<Produto> getAll() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, preco FROM produto ORDER BY id";
        try (Statement statement = conexao.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String descricao = resultSet.getString("descricao");
                double preco = resultSet.getDouble("preco");
                produtos.add(new Produto(id, nome, descricao, preco));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os produtos: " + e.getMessage());
            e.printStackTrace();
        }
        return produtos;
    }

    // U (Update) - Atualiza um produto existente
    public boolean update(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, descricao = ?, preco = ? WHERE id = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, produto.getNome());
            statement.setString(2, produto.getDescricao());
            statement.setDouble(3, produto.getPreco());
            statement.setInt(4, produto.getId());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // D (Delete) - Exclui um produto
    public boolean delete(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar produto: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
