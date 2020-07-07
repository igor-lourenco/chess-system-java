package xadrez;

import tabuleiro.Posicao;

public class PosicaoXadrez {

	private char coluna;
	private int linha;
	
	public PosicaoXadrez(char coluna, int linha) {
		if(coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8)
			throw new XadrezException("Erro ao instanciar a po��o do Xadrez. Valores v�lidos s�o de a1 at� h8");
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}

	/*m�todo para converter a posicao do xadrez para posicao normal */
	protected Posicao toPosicao() {
		return new Posicao(8 - linha, coluna - 'a');
	}
	
	/*m�todo para converter a posi��o normal para a posi��o do xadrez*/
	protected static PosicaoXadrez converterPosicao(Posicao posicao) {
		return new PosicaoXadrez((char)('a' - posicao.getColuna()), 8 - posicao.getLinha());
	}
	 
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
	
	
	
}