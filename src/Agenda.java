import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

import javax.swing.table.DefaultTableModel;
import javax.tools.JavaFileManager.Location;

class TelaGeral extends JFrame 
{

    int alterar = 0;

    JPanel imagem = new JPanel();
    JLabel agenda = new JLabel("Agenda");
    Font f = new Font("Old English Text MT", Font.PLAIN, 100);

    JPanel entradaDados;
    JPanel atributos;
    JPanel anotacoes;
    JPanel botoes;
    JPanel painelFundo;
    JPanel consulta;
    
    Color minhaCor = new Color(238, 238, 238);

    JTable tabela;

    DefaultTableModel modelo;

    //--------------------

    JTextField jtcodigo;
    JTextField jtnome;
    JTextField jtendereco;
    JTextField jttelefone;
    JTextArea jtanotacoes;
    JTextField jtconsultar;

    //--------------------

    JButton bsalvar;
    JButton balterar;
    JButton bexcluir;
    JButton bconsultar;
    JButton banotacoes;
    JButton auxConsulta;

    //--------------------

    JScrollPane barraRolagem;
    PessoaAgenda pa;
    Armazenamento arquivo_agenda = new Armazenamento("usuarios.dat");
    Armazenamento id = new Armazenamento("id.dat");

    public TelaGeral()
    {
        Image icon = Toolkit.getDefaultToolkit().getImage("icon_martelo.png");
    	this.setIconImage(icon);
        montarEntradaDados();
        montarAnotacoes();
        montarBotoes();

        criaTabela();
        imagem.setLayout(new FlowLayout(FlowLayout.CENTER));
        imagem.add(agenda);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add("South", botoes);
        getContentPane().add(imagem, BorderLayout.PAGE_START);
        getContentPane().add("West", entradaDados);
        getContentPane().add("Center", anotacoes);
        
        getContentPane().add("East", painelFundo);

        setBackground(minhaCor);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(75,25);
        setSize(1400,700);
        setResizable(false);
        setVisible(true);
    }
    
    public void criaTabela(){
        
        painelFundo = new JPanel();
        painelFundo.setLayout(new GridLayout(1, 1));

        modelo = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("Código");
        modelo.addColumn("Nome");
        modelo.addColumn("Endereço");
        modelo.addColumn("Telefone");

        int q = arquivo_agenda.quantidadeRegistros();

        tabela = new JTable(modelo);

        for(int i=0;i!=q;i++)
        {
            pa = (PessoaAgenda) arquivo_agenda.obter(i);
            modelo.addRow(new Object[]{
                pa.getCodigo(),pa.getNome(),pa.getEndereco(),pa.getTelefone()  
            });
        }
        painelFundo.add(tabela);
        barraRolagem = new JScrollPane(tabela);
        painelFundo.add(barraRolagem);
        getContentPane().add("East", painelFundo);
    }

    public void atualizaTabela()
    {
        if(alterar==1)
        {
            int a = tabela.getSelectedRow();

            modelo.setValueAt(jtnome.getText(), a, 1);
            modelo.setValueAt(jtendereco.getText(), a, 2);
            modelo.setValueAt(jttelefone.getText(), a, 3);
            alterar = 0;
        }
        else{
            int q = arquivo_agenda.quantidadeRegistros()-1;

            pa = (PessoaAgenda) arquivo_agenda.obter(q);
            modelo.addRow(new Object[]{
            pa.getCodigo(),pa.getNome(),pa.getEndereco(),pa.getTelefone()  
            });
        }    
    }        

    protected void montarEntradaDados()
    {
        agenda.setForeground(new Color(0,0,0));
        agenda.setFont(f);
        entradaDados = new JPanel();
        entradaDados.setLayout(new BorderLayout());
        montarAtributos();
        entradaDados.add("West", atributos);
    }

    protected void montarAtributos()
    {
        atributos = new JPanel();
        atributos.setLayout(new BoxLayout(atributos, BoxLayout.Y_AXIS));
        agenda.setSize(350, 150);

        JPanel jpnomebox = new JPanel();
        JPanel jpnomeflow = new JPanel();
        
        atributos.setBackground(minhaCor);

        jpnomeflow.setLayout(new FlowLayout(FlowLayout.LEFT));
        jpnomeflow.add(new JLabel("Nome"));
        jpnomeflow.add(Box.createRigidArea(new Dimension(15, 10)));
        jtnome = new JTextField(10);
        jpnomeflow.add(jtnome);
        jpnomebox.setLayout(new BoxLayout(jpnomebox, BoxLayout.X_AXIS));
        jpnomebox.add(jpnomeflow);

        JPanel jpenderecobox = new JPanel();
        JPanel jpenderecoflow = new JPanel();

        jpenderecoflow.setLayout(new FlowLayout(FlowLayout.LEFT));
        jpenderecoflow.add(new JLabel("Endereço"));
        jtendereco = new JTextField(10);
        jpenderecoflow.add(jtendereco);
        jpenderecobox.setLayout(new BoxLayout(jpenderecobox, BoxLayout.X_AXIS));
        jpenderecobox.add(jpenderecoflow);

        JPanel jptelefonebox = new JPanel();
        JPanel jptelefoneflow = new JPanel();

        jptelefoneflow.setLayout(new FlowLayout(FlowLayout.LEFT));
        jptelefoneflow.add(new JLabel("Telefone"));
        jptelefoneflow.add(Box.createRigidArea(new Dimension(1, 10)));
        jttelefone = new JTextField(10);

        jptelefoneflow.add(jttelefone);
        jptelefonebox.setLayout(new BoxLayout(jptelefonebox, BoxLayout.X_AXIS));
        jptelefonebox.add(jptelefoneflow);

        atributos.add(Box.createRigidArea(new Dimension(10, 100)));
        atributos.add(jpnomebox);
        atributos.add(jpenderecobox);
        atributos.add(jptelefonebox);
    }

    protected void montarAnotacoes()
    {
        anotacoes = new JPanel();
        anotacoes.setLayout(new BoxLayout(anotacoes, BoxLayout.Y_AXIS));

        JPanel jpanotacoesflow = new JPanel();
        barraRolagem = new JScrollPane(jtanotacoes);

        jpanotacoesflow.add(new JLabel("Anotações"));
        jtanotacoes = new JTextArea(10,10);
        jtanotacoes.add(barraRolagem);
        jtanotacoes.setLineWrap(true);
        jtanotacoes.setSize(50, 50);
        anotacoes.add(jpanotacoesflow);
        anotacoes.add(jtanotacoes);
    }

    private class Salvar implements ActionListener
    {
        private String arquivo;

        public Salvar(String arquivo){
            this.arquivo = arquivo;
        }

        protected String id()
        {
            int q = id.quantidadeRegistros();
            if(q!=0)
            {
                pa = (PessoaAgenda)id.obter(q-1);
                String aux = pa.getCodigo();
                int num = Integer.valueOf(aux);
                num++;
                aux = String.valueOf(num);
                return aux;
            }
            return "1";
        }

        public void actionPerformed(ActionEvent evento){
            if(jtnome.getText().equals("")||jtendereco.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Os campos Nome e Endereço são obrigatórios.","Atenção",1);
            }
            else{
                if(bsalvar.getText()=="Salvar Alteração"){   
                    if(jtnome.getText().equals("")||jtendereco.getText().equals("")){
                        JOptionPane.showMessageDialog(null,"Os campos Nome e Endereço são obrigatórios.","Atenção",1);
                    }
                    else{
                        int aux=tabela.getSelectedRow();
                        PessoaAgenda auxpa = (PessoaAgenda)arquivo_agenda.obter(aux);

                        String nome = jtnome.getText();
                        String endereco = jtendereco.getText();
                        String telefone = jttelefone.getText();
                        String anotacoes = jtanotacoes.getText();

                        auxpa.setNome(nome);
                        auxpa.setEndereco(endereco);
                        auxpa.setTelefone(telefone);
                        auxpa.setAnotacoes(anotacoes);
                        arquivo_agenda.alterar(auxpa);
                        if (evento.getSource() == bsalvar) {
                            JOptionPane.showMessageDialog(null,"Alterado com sucesso!");
                            alterar = 1;
                            bsalvar.setText("Inserir");
                            balterar.setText("Alterar");
                            bexcluir.setEnabled(true);
                            banotacoes.setEnabled(true);
                            bconsultar.setEnabled(true);
                        }
                        atualizaTabela();
                        jtendereco.setText("");
                        jtanotacoes.setText("");
                        jttelefone.setText("");
                        jtnome.setText("");	
                    }
                }
                else{
                    String nome = jtnome.getText();
                    String endereco = jtendereco.getText();
                    String telefone = jttelefone.getText();
                    String anotacoes = jtanotacoes.getText();
                    PessoaAgenda auxpa = new PessoaAgenda(id(),nome,endereco,telefone,anotacoes);

                    arquivo_agenda.inserir(auxpa);
                    id.inserir(auxpa);
                    if (evento.getSource() == bsalvar) {
                        JOptionPane.showMessageDialog(null,"Salvo com sucesso!");
                    }
                    atualizaTabela();
                    jtendereco.setText("");
                    jtanotacoes.setText("");
                    jttelefone.setText("");
                    jtnome.setText("");
                }
            }        
        }
    }

    private class Excluir implements ActionListener
    {
        private String arquivo;

        public Excluir(String arquivo){
            this.arquivo = arquivo;
        }
        public void actionPerformed(ActionEvent evento){
            DefaultTableModel modelo = (DefaultTableModel)tabela.getModel();
            if (tabela.getSelectedRow() >= 0){
                int aux;

                aux = tabela.getSelectedRow();
                arquivo_agenda.excluir(aux);
                modelo.removeRow(tabela.getSelectedRow());
                tabela.setModel(modelo);
                jtnome.setText("");
                jtendereco.setText("");
                jttelefone.setText("");
                jtanotacoes.setText("");
                bsalvar.setText("Inserir");
                balterar.setText("Alterar");
                if (evento.getSource() == bexcluir) {
                    JOptionPane.showMessageDialog(null,"Excluído com sucesso! ");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Favor selecionar uma linha");
            }
        }
    }

    private class Alterar implements ActionListener{
        private String arquivo;

        private Alterar(String arquivo){
            this.arquivo = arquivo;
        }

        public void actionPerformed(ActionEvent evento){
            if(balterar.getText()=="Cancelar"){
                bexcluir.setEnabled(true);
                banotacoes.setEnabled(true);
                bconsultar.setEnabled(true);
                bsalvar.setText("Inserir");
                balterar.setText("Alterar");
                jtnome.setText("");
                jtendereco.setText("");
                jttelefone.setText("");
                jtanotacoes.setText("");
            }
            else{
                if(tabela.getSelectedRow()>=0){
                bsalvar.setText("Salvar Alteração");
                balterar.setText("Cancelar");
                bexcluir.setEnabled(false);
                banotacoes.setEnabled(false);
                bconsultar.setEnabled(false);

                int aux=tabela.getSelectedRow();
                PessoaAgenda paux = (PessoaAgenda)arquivo_agenda.obter(aux);

                jtnome.setText(paux.getNome());
                jtendereco.setText(paux.getEndereco());
                jttelefone.setText(paux.getTelefone());
                jtanotacoes.setText(paux.getAnotacoes());
                }
                else{
                    JOptionPane.showMessageDialog(null, "Favor selecionar uma linha");
                }       
            }    
        }
    }

    private class Consultar implements ActionListener{
        private String arquivo;

        private Consultar(String arquivo){
            this.arquivo = arquivo;
        }

        public void actionPerformed(ActionEvent evento){
            if(evento.getSource()==bconsultar){
                if(bconsultar.getText()=="Consultar"){
                    consulta = new JPanel();
                    consulta.setLayout(new FlowLayout(FlowLayout.LEFT));
                    consulta.add(new JLabel("Consulta"));
                    jtconsultar = new JTextField(10);
                    consulta.add(jtconsultar);
                    consulta.add(auxConsulta);

                    bconsultar.setText("Cancelar");
                    
                    bsalvar.setEnabled(false);
                    bexcluir.setEnabled(false);
                    balterar.setEnabled(false);
                    banotacoes.setEnabled(false);
                    jtanotacoes.setEnabled(false);
                    jtnome.setEnabled(false);
                    jtendereco.setEnabled(false);
                    jttelefone.setEnabled(false);
                    atributos.add(consulta);
                }
                else{
                    bconsultar.setText("Consultar");
                    bsalvar.setEnabled(true);
                    bexcluir.setEnabled(true);
                    balterar.setEnabled(true);
                    banotacoes.setEnabled(true);
                    jtanotacoes.setEnabled(true);
                    jtnome.setEnabled(true);
                    jtendereco.setEnabled(true);
                    jttelefone.setEnabled(true);
                    atributos.remove(consulta);
                    atributos.remove(auxConsulta);

                    int q = arquivo_agenda.quantidadeRegistros();
                    int auxnum = modelo.getRowCount();

                    for(int i=0;i<auxnum;i++){
                        modelo.removeRow(0);
                    }

                    for(int i=0;i!=q;i++)
                    {
                        pa = (PessoaAgenda) arquivo_agenda.obter(i);
                        modelo.addRow(new Object[]{
                            pa.getCodigo(),pa.getNome(),pa.getEndereco(),pa.getTelefone()  
                        });
                    }
                }
            }   
        }
    }

    private class Auxiliar implements ActionListener{
        private String arquivo;

        private Auxiliar(String arquivo){
            this.arquivo = arquivo;
        }

        public void actionPerformed(ActionEvent evento){
            String aux = jtconsultar.getText().toLowerCase();
            if(aux.matches("[+-]?\\d+(\\.\\d+)?")){
                jtconsultar.setText("");
                int num = Integer.valueOf(aux);
                int auxnum,  j=0, auxnum1;
                auxnum = modelo.getRowCount();
                for(int i=0;i<auxnum;i++){
                    modelo.removeRow(0);
                }
                auxnum = arquivo_agenda.quantidadeRegistros();
                while(j!=auxnum){
                    pa = (PessoaAgenda) arquivo_agenda.obter(j);
                    auxnum1 = Integer.valueOf(pa.getCodigo());
                    if(auxnum1==num){
                        modelo.addRow(new Object[]{
                        pa.getCodigo(),pa.getNome(),pa.getEndereco(),pa.getTelefone()  
                        });
                    }
                    j++;
                }
                if(modelo.getRowCount()==0){
                    int q = arquivo_agenda.quantidadeRegistros();    
                    for(int k=0;k!=q;k++)
                    {
                        pa = (PessoaAgenda) arquivo_agenda.obter(k);
                        modelo.addRow(new Object[]{
                            pa.getCodigo(),pa.getNome(),pa.getEndereco(),pa.getTelefone()  
                        });
                    }
                    JOptionPane.showMessageDialog(null, "Nenhuma pessoa encontrada.");
                }
            }
            else{
                if(aux.matches("[a-zA-z]+\s?[a-zA-z]*\s?[a-zA-z]*\s?[a-zA-z]*\s?")){
                    jtconsultar.setText("");
                    int auxnum,  j=0, auxnum1;
                    auxnum = modelo.getRowCount();
                    for(int i=0;i<auxnum;i++){
                        modelo.removeRow(0);
                    }
                    auxnum = arquivo_agenda.quantidadeRegistros();
                    while(j!=auxnum){
                        pa = (PessoaAgenda) arquivo_agenda.obter(j);
                        String aux2 = pa.getNome().toLowerCase();
                        if(aux2.startsWith(aux)){
                            modelo.addRow(new Object[]{
                            pa.getCodigo(),pa.getNome(),pa.getEndereco(),pa.getTelefone()  
                            });
                        }
                        j++;
                    }
                    if(modelo.getRowCount()==0){
                        int q = arquivo_agenda.quantidadeRegistros();                        
                        for(int k=0;k!=q;k++)
                        {
                            pa = (PessoaAgenda) arquivo_agenda.obter(k);
                            modelo.addRow(new Object[]{
                                pa.getCodigo(),pa.getNome(),pa.getEndereco(),pa.getTelefone()  
                            });
                        }
                        JOptionPane.showMessageDialog(null, "Nenhuma pessoa encontrada.");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Digite apenas o código ou o nome.");
                }
            } 
        }
    }

    private class Anotacoes implements ActionListener{
        private String arquivo;

        private Anotacoes(String arquivo){
            this.arquivo=arquivo;
        }

        public void actionPerformed(ActionEvent evento){
            if(banotacoes.getText()=="Fechar")
            {
                jtnome.setText("");
                jtendereco.setText("");
                jttelefone.setText("");
                jtanotacoes.setText("");
                bsalvar.setEnabled(true);
                balterar.setEnabled(true);
                bexcluir.setEnabled(true);
                bconsultar.setEnabled(true);
                banotacoes.setText("Mostrar anotações");
                jtanotacoes.setEditable(true);
                jtnome.setEditable(true);
                jttelefone.setEditable(true);
                jtendereco.setEditable(true);
            }
            else{
                if(tabela.getSelectedRow()>=0)
                {
                    int aux = tabela.getSelectedRow();
                    PessoaAgenda paux = (PessoaAgenda)arquivo_agenda.obter(aux);

                    jtanotacoes.setText(paux.getAnotacoes());
                    jtnome.setText("");
                    jtendereco.setText("");
                    jttelefone.setText("");
                    bsalvar.setEnabled(false);
                    balterar.setEnabled(false);
                    bexcluir.setEnabled(false);
                    bconsultar.setEnabled(false);
                    banotacoes.setText("Fechar");
                    jtanotacoes.setEditable(false);
                    jtnome.setEditable(false);
                    jttelefone.setEditable(false);
                    jtendereco.setEditable(false);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Favor selecionar uma linha");
                }
            }    
        }
    }

    protected void montarBotoes()
    {
        botoes = new JPanel();
        botoes.setLayout(new FlowLayout());
        bconsultar = new JButton("Consultar");
        bsalvar = new JButton("Inserir");
        balterar = new JButton("Alterar");
        bexcluir = new JButton("Excluir");
        banotacoes = new JButton("Mostrar anotações");
        auxConsulta = new JButton("Buscar");

        Salvar acaosalvar =  new Salvar("DadosAgenda.dat");
        Excluir acaoexcluir = new Excluir("DadosAgenda.dat");
        Alterar acaoalterar = new Alterar("DadosAgenda.dat");
        Anotacoes acaoanotacoes = new Anotacoes("DadosAgenda.dat");
        Consultar acaoconsultar = new Consultar("DadosAgenda.dat");
        Auxiliar acaoauxconsultar = new Auxiliar("DadosAgenda.dat");

        bsalvar.addActionListener(acaosalvar);
        balterar.addActionListener(acaoalterar);
        bexcluir.addActionListener(acaoexcluir);
        banotacoes.addActionListener(acaoanotacoes);
        bconsultar.addActionListener(acaoconsultar);
        auxConsulta.addActionListener(acaoauxconsultar);

        botoes.add(bconsultar);
        botoes.add(Box.createRigidArea(new Dimension(20,10)));
        botoes.add(bsalvar);
        botoes.add(balterar);
        botoes.add(bexcluir);
        botoes.add(banotacoes);
    }
}