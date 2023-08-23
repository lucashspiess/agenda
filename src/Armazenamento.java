import java.util.*;
import java.io.*;

class Armazenamento {
	String arquivo;
	ArrayList<ItemParaArmazenar> arl;
	
	public Armazenamento (String arq){
		arquivo = arq;
		arl = new ArrayList<ItemParaArmazenar>();
		montarLista(arl);
	}
	
	protected void armazenar(ArrayList<ItemParaArmazenar> arl) {
		try {
			File f = new File(arquivo);
			f.delete();
			f = null;
			
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo));
			for (int i=0; i < arl.size() ; i++ ) {
				out.writeObject((ItemParaArmazenar) arl.get(i));	
			}
			out.close();
		}
		catch (FileNotFoundException e) {}
		catch (IOException e) {}
	}
	
	protected void montarLista(ArrayList<ItemParaArmazenar> arl) {
		arl.clear();
		try {
			ObjectInputStream in = new ObjectInputStream (new FileInputStream(arquivo));
			ItemParaArmazenar it;
			while ((it = (ItemParaArmazenar) in.readObject()) != null){
				arl.add(it);
			}
			in.close();
		}
		catch (IOException e) {}
		catch (ClassNotFoundException e) {}		
	}
	
	public void inserir(ItemParaArmazenar ipa) {
		
		arl.add(ipa);
		armazenar(arl);
	}

	public ItemParaArmazenar excluir(int pos){
		ItemParaArmazenar it=null;
		if((pos >= 0) && (pos < quantidadeRegistros()) ){
			it = arl.remove(pos);
			armazenar(arl);
		}		
		return it;
	}

	public int alterar(ItemParaArmazenar ipa){
		ItemParaArmazenar item;
		for (int i=0; i < arl.size(); i++){
			item = arl.get(i);
			if(item.igual(ipa)){
				arl.set(i, ipa);
				armazenar(arl);
				return i;
			}
		}

		return -1; // indica que não houve alteração.

	}
	
	
	public ItemParaArmazenar obter(int pos) {
		
		ItemParaArmazenar it=null;
		if((pos >= 0) && (pos < quantidadeRegistros()) ){
			it = arl.get(pos);
		}
		return it;
	}
	
	public int quantidadeRegistros(){
		return arl.size();
	}
	
	public void limparArquivo(){
		File f = new File(arquivo);
		f.delete();
		f = null;
		arl.clear();
	}
}