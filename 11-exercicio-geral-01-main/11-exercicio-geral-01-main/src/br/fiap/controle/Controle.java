package br.fiap.controle;
import br.fiap.NotaFiscal.NotaFiscal;
import br.fiap.cliente.Cliente;
import br.fiap.item.ItemProduto;
import br.fiap.produto.Produto;

import javax.management.relation.RelationNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Long.parseLong;
import static java.lang.Integer.parseInt;
import static java.lang.Double.parseDouble;
import static javax.swing.JOptionPane.*;

public class Controle {

    private static List<Cliente> listaCliente = new ArrayList<>();
    private static List<Produto> listaProduto = new ArrayList<>();
    private NotaFiscal nf;

    static {
        // lista de clientes
        listaCliente.add(new Cliente(123, "Ana Maria"));
        listaCliente.add(new Cliente(456, "Roberto carlos"));
        listaCliente.add(new Cliente(789, "Kanye West"));

        //Lista de produtos
        listaProduto.add(new Produto(1, "tênis", 5000,1000));
        listaProduto.add(new Produto(2, "calcinha", 20000,5000));
        listaProduto.add(new Produto(3, "Carteira", 1500,100000));
    }

    public Controle(){
        //sorteando um número
        Random rd = new Random();
        nf = new NotaFiscal(listaCliente.get(rd.nextInt(listaCliente.size())));
    }


    public void menu() {
        int opcao;
        while(true) {
            try {
                opcao = parseInt(showInputDialog(gerarMenu()));
                switch (opcao) {
                    case 1:
                        adicionarProduto();
                        break;
                    case 2:
                        removerProduto();
                        break;
                    case 3:
                        fecharCompra();
                        break;
                    case 4:
                        return;
                    default:
                        showMessageDialog(null, "Burrão");
                }
            }
            catch (NumberFormatException e) {
                showMessageDialog(null, "Viado");
            }
        }
    }

    private void fecharCompra() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        double total = nf.calcularTotal();
        String dado = nf.getCliente().toString();
        showMessageDialog(null, dado + "\nTotal da compra R$ " + df.format(total));
    }

    private void adicionarProduto() {
        String nome;
        int quantidade;

        if (nf.getStatus()) {
            nome = showInputDialog("Qual produto será comprado?: ");
            for (Produto produto : listaProduto){
                if (produto.getNome().equalsIgnoreCase(nome)){
                    quantidade = parseInt(showInputDialog("Qual a quantidade?"));
                        if (produto.getQuantidadeEmEstoque() >= quantidade) {
                            nf.adicionarItem(new ItemProduto(produto, quantidade));
                            produto.debitarEstoque(quantidade);
                        }
                }


            }
        }
    }

    private void removerProduto() {
       String nome = showInputDialog("Qual o produto será removido? ");
       nf.removerProduto(new Produto(nome));
    }


    private String gerarMenu() {
        String aux = "SISTEMA DE COMPRAS ONLINE\n";
        aux += "1. Adicionar produto\n";
        aux += "2. Remover produto\n";
        aux += "3. Fechar compra\n";
        aux += "4. Finalizar compra\n";
        return aux;
    }


}
