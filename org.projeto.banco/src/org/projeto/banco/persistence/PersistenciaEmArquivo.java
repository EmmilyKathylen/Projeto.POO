package org.projeto.banco.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.projeto.banco.modelo.Cliente;

public class PersistenciaEmArquivo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private List<Cliente> cadastroClientes = new ArrayList<>();

	private static PersistenciaEmArquivo instance;
	
	private PersistenciaEmArquivo() {
		carregarDadosDeArquivo();
	}
	
	public static PersistenciaEmArquivo getInstance() {
		if(instance!=null)
			return instance;
		else
			return new PersistenciaEmArquivo();
	}

	public void salvarCliente(Cliente c) {
		if (!cadastroClientes.contains(c)) {
			cadastroClientes.add(c);
			salvarDadosEmArquivo();
			System.out.println("Cliente cadastrados com sucesso!");
		} else
			System.err.println("Cliente ja cadastrado no sistema!");

	}
	
	public Cliente localizarClientePorCPF(String cpf) {
		Cliente c = new Cliente();
		c.setCpf(cpf);
		if(cadastroClientes.contains(c)) {
			int index = cadastroClientes.indexOf(c);
			c = cadastroClientes.get(index);
			return c;
		}else
			return null;
	}
	
	public void atualizarClienteCadastro(Cliente c) {
		if(cadastroClientes.contains(c)) {
			int index = cadastroClientes.indexOf(c);
			cadastroClientes.set(index, c);
			salvarDadosEmArquivo();
		}else
			System.err.println("Cliente não encontrado!");
	}

	public void salvarDadosEmArquivo() {
		try {
			FileOutputStream fos = new FileOutputStream("dados");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(cadastroClientes);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void carregarDadosDeArquivo() { 
		try {
			FileInputStream fis = new FileInputStream("dados");
			ObjectInputStream ois = new ObjectInputStream(fis);
			 List<?> obj = (List<?>) ois.readObject();
		        cadastroClientes = new ArrayList<>();
		        for (Object item : obj) {
		            if (item instanceof Cliente) {
		                cadastroClientes.add((Cliente) item);
		            } else {
		                System.out.println("erro");
		            }
		        }
			ois.close();
			fis.close();		
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
