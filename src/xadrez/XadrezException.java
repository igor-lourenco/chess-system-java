package xadrez;

import tabuleiro.TabuleiroException;

public class XadrezException  extends TabuleiroException{

	private static final long serialVersionUID = 1L;
	
	/*Construtor que recebe a mensagem e repassa para a superclasse RuntimeException*/
	public XadrezException(String msg) {
		super(msg);
	}

}
