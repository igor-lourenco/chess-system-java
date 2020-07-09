package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {
	
	private Cor cor;
	private int contadorTurno;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContadorTurno() {
		return contadorTurno;
	}
	
	public void increaseContadorTurno() {
		contadorTurno++;
	}
	
	public void decreaseContadorTurno() {
		contadorTurno--;
	}
	
	/*método para pegar a posição da peca no tabuleiro*/
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.converterPosicao(posicao);
	}
	
	/*método verifica se há alguma peca adversária na posicao*/
	protected boolean existePecaOponente(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}

	
	
}
