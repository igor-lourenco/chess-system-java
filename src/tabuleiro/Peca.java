package tabuleiro;

public abstract class Peca {

	protected Posicao posicao;
	private Tabuleiro tabuleiro;

	public Peca(Tabuleiro tabuleiro) {

		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public abstract boolean[][] possiveisMovimentos();

	/* m�todo para verificar os movimentos poss�veis que a pe�a pode fazer */
	public boolean possivelMovimento(Posicao posicao) {
		return possiveisMovimentos()[posicao.getLinha()][posicao.getColuna()];
	}

	public boolean existeMovimentoPossivel() {
		boolean[][] mat = possiveisMovimentos();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j])
					return true;
			}
		}
		return false;
	}

}
