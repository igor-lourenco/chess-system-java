package tabuleiro;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private Peca[][] pecas;

	public Tabuleiro(int linhas, int colunas) {

		if (linhas < 1 || colunas < 1)
			throw new TabuleiroException(
					"Erro na cria��o do tabuleiro: Necess�rio tem pelo menos 1 linha e 1 uma coluna");
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	/* metodo para retornar peca */
	public Peca peca(int linha, int coluna) {
		if (!posicaoExiste(linha, coluna))
			throw new TabuleiroException("Posi�ao fora do Tabuleiro");
		return pecas[linha][coluna];
	}

	/* metodo para a posi��o da peca */
	public Peca peca(Posicao posicao) {
		if (!posicaoExiste(posicao))
			throw new TabuleiroException("Posi�ao fora do Tabuleiro");
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}

	/* metodo responsavel por colocar a peca na posi��o do tabuleiro */
	public void colocarPeca(Peca peca, Posicao posicao) {
		if (temUmaPeca(posicao))
			throw new TabuleiroException("J� existe uma pe�a na posi��o " + posicao);
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}

	/* m�todo para mover a pe�a */
	public Peca moverPeca(Posicao posicao) {
		if (!posicaoExiste(posicao))
			throw new TabuleiroException("Posi��o fora do Tabuleiro");
		if (peca(posicao) == null)
			return null;
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;

	}

	/* metodo auxiliar para a verifica�ao de posi��o */
	private boolean posicaoExiste(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}

	/* verifica se a posi��o existe */
	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna());
	}

	public boolean temUmaPeca(Posicao posicao) {
		if (!posicaoExiste(posicao))
			throw new TabuleiroException("Posi�ao fora do Tabuleiro");
		return peca(posicao) != null;
	}

}
