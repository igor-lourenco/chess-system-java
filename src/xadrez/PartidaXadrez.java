package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		iniciarPartida();
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
	
	/*método para mover a peca de origem para o destino solicitado*/
	public PecaXadrez executarXadrez(PosicaoXadrez origemPosicao, PosicaoXadrez destinoPosicao) {
		Posicao origem = origemPosicao.toPosicao();
		Posicao destino = destinoPosicao.toPosicao();
		validarOrigemPosicao(origem);
		validarDestinoPosicao(origem, destino);
		Peca capturarPeca = fazerMover(origem, destino);
		return (PecaXadrez)capturarPeca;
	}
	
	/*método para remover da posição de origem e colocar na posição de destino*/
	private Peca fazerMover(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.moverPeca(origem);
		Peca pecaCapturada = tabuleiro.moverPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		return pecaCapturada;
		
	}
	
	/*método para verificar se há peça na posição de origem solicitada pelo usuário*/
	private void validarOrigemPosicao(Posicao posicao) {
		if(!tabuleiro.temUmaPeca(posicao))
			throw new XadrezException("Nao ha peca na posicao de origem");
		if(!tabuleiro.peca(posicao).existeMovimentoPossivel())
			throw new XadrezException("Nao existe movimentos possiveis para peca escolhida");
	}
	
	/*método para verificar se há peça na posição de destino solicitada pelo usuário*/
	private void validarDestinoPosicao(Posicao origem,Posicao destino) {
		if(!tabuleiro.peca(origem).possivelMovimento(destino))
			throw new XadrezException("Peca escolhida nao pode se mover para a posicao de destino");
	}

	/* metodo para converter as coordenadas para o xadrez */
	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	}

	/* metodo responsavel por iniciar a partida, colocando as peças no tabuleiro */
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
