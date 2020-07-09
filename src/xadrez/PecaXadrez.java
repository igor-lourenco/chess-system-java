package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {
	
	private Cor cor;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	/*m�todo para pegar a posi��o da peca no tabuleiro*/
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.converterPosicao(posicao);
	}
	
	/*m�todo verifica se h� alguma peca advers�ria na posicao*/
	protected boolean existePecaOponente(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}

	
	
}
