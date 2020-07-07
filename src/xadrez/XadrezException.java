package xadrez;

public class XadrezException  extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/*Construtor que recebe a mensagem e repassa para a superclasse RuntimeException*/
	public XadrezException(String msg) {
		super(msg);
	}

}
