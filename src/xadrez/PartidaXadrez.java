package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		iniciarPartida();
	}
	
	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	/* retorna a matriz da peca de xadrez */
	public PecaXadrez[][] getPecas() {

		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	/*m�todo para verificar os movimentos poss�veis da peca que o usuario solicitou */
	public boolean [][] possiveisMovimentos(PosicaoXadrez origemPosicao){
		Posicao posicao = origemPosicao.toPosicao();
		validarOrigemPosicao(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
	}
	
	/*m�todo para mover a peca de origem para o destino solicitado*/
	public PecaXadrez executarXadrez(PosicaoXadrez origemPosicao, PosicaoXadrez destinoPosicao) {
		Posicao origem = origemPosicao.toPosicao();
		Posicao destino = destinoPosicao.toPosicao();
		validarOrigemPosicao(origem);
		validarDestinoPosicao(origem, destino);
		Peca capturarPeca = fazerMover(origem, destino);
		trocaTurno();
		return (PecaXadrez)capturarPeca;
	}
	
	/*m�todo para remover da posi��o de origem e colocar na posi��o de destino*/
	private Peca fazerMover(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.moverPeca(origem);
		Peca pecaCapturada = tabuleiro.moverPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		return pecaCapturada;
		
	}
	
	/*m�todo para verificar se h� pe�a na posi��o de origem solicitada pelo usu�rio*/
	private void validarOrigemPosicao(Posicao posicao) {
		if(!tabuleiro.temUmaPeca(posicao))
			throw new XadrezException("Nao ha peca na posicao de origem");
		if(jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)) .getCor())
			throw new XadrezException("Essa peca nao e " + getJogadorAtual());
		if(!tabuleiro.peca(posicao).existeMovimentoPossivel())
			throw new XadrezException("Nao existe movimentos possiveis para peca escolhida");
	}
	
	/*m�todo para verificar se h� pe�a na posi��o de destino solicitada pelo usu�rio*/
	private void validarDestinoPosicao(Posicao origem,Posicao destino) {
		if(!tabuleiro.peca(origem).possivelMovimento(destino))
			throw new XadrezException("Peca escolhida nao pode se mover para essa posicao");
	}
	
	/*m�todo para trocar turno*/
	private void trocaTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	/* metodo para converter as coordenadas para o xadrez */
	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	}

	/* metodo responsavel por iniciar a partida, colocando as pe�as no tabuleiro */
	private void iniciarPartida() {

		colocarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));

		colocarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));

	}

}
