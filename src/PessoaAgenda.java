class PessoaAgenda extends ItemParaArmazenar{
    protected String codigo,
                        nome,
                        endereco,
                        telefone,
                        anotacoes;

    public boolean igual(ItemParaArmazenar pa){
        if (codigo.equals(((PessoaAgenda) pa).getCodigo())) {
                   return true;
               }
        return false;
    }

    // PessoaAgenda(String codigo, String nome){
    //     this.codigo = codigo; //solução para o problema de shadowing (sombreamento)
    //     this.nome = nome;
    // }

    PessoaAgenda(String codigo, String nome, String endereco, 
                    String telefone, String anotacoes){
        this.codigo = codigo;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.anotacoes = anotacoes;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }
    public void setAnotacoes(String anotacoes){
        this.anotacoes = anotacoes;
    }
    public String getCodigo(){
        return codigo;
    }
    public String getNome(){
        return nome;
    }
    public String getEndereco(){
        return endereco;
    }
    public String getTelefone(){
        return telefone;
    }
    public String getAnotacoes(){
        return anotacoes;
    }
}