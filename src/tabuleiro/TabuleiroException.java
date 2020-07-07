package tabuleiro;

public class TabuleiroException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/*Construtor que recebe a mensagem e repassa para a superclasse RuntimeException*/
	public TabuleiroException(String msg) {
		super(msg);
	}

}
