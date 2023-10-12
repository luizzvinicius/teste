package cantina.connections;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import cantina.Venda;

public class VendaDAO {
    private Connection conn;
    
    public VendaDAO(Connection conn) {
        this.conn = conn;
    }

    public int insert(Venda v) {
        final String sql = "insert into venda (codigo, id_funcionario, desconto, total_venda, forma_pagamento, data) values (?,?,?,?,?,?)";
        int codigoVenda = 0;
        try (var stmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, v.getCodigoVenda());
            stmt.setInt(2, v.getIdFuncionario());
            stmt.setDouble(3, v.getDesconto());
            stmt.setDouble(4, v.getTotal());
            stmt.setString(5, v.getFormaPagamento());
            stmt.setTimestamp(6, Timestamp.valueOf(v.getData()));
            stmt.executeUpdate();

            var result = stmt.getGeneratedKeys();
            result.next();
            codigoVenda = result.getInt(1);
        } catch (SQLException e) {
            System.out.println("Não foi possível inserir a venda: " + e.getMessage() + "\n");
        }
        return codigoVenda;
    }

    public void updateVenda(int codVenda, String formaPagamento, double total) {
        final String sql = "update venda set forma_pagamento = ?, total_venda = ? where codigo = ?";
        try (var stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, formaPagamento);
            stmt.setDouble(2, total);
            stmt.setInt(3, codVenda);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Não foi possível atualizar venda: " + e.getMessage() + "\n");
        }
    }
}