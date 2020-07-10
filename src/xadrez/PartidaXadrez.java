package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque;
	private boolean xequeMate;
	private PecaXadrez enPassantVulnerable;
	private PecaXadrez promoted;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

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

	public boolean getXeque() {
		return xeque;
	}

	public boolean getXequeMate() {
		return xequeMate;
	}

	public PecaXadrez getEnPassantVulnerable() {
		return enPassantVulnerable;
	}

	public PecaXadrez getPromoted() {
		return promoted;
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

	/*
	 * método para verificar os movimentos possíveis da peca que o usuario solicitou
	 */
	public boolean[][] possiveisMovimentos(PosicaoXadrez origemPosicao) {
		Posicao posicao = origemPosicao.toPosicao();
		validarOrigemPosicao(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
	}

	/* método para mover a peca de origem para o destino solicitado */
	public PecaXadrez executarMovimentoXadrez(PosicaoXadrez origemPosicao, PosicaoXadrez destinoPosicao) {
		Posicao origem = origemPosicao.toPosicao();
		Posicao destino = destinoPosicao.toPosicao();
		validarOrigemPosicao(origem);
		validarDestinoPosicao(origem, destino);
		Peca capturarPeca = fazerMovimento(origem, destino);

		if (testarXeque(jogadorAtual)) {
			desfazerMovimento(origem, destino, capturarPeca);
			throw new XadrezException("Voce nao pode se colocar em xeque");
		}

		PecaXadrez pecaMoveu = (PecaXadrez) tabuleiro.peca(destino);
		
		//moimento especial promotion
		promoted = null;
		if(pecaMoveu instanceof Peao) {
			if(pecaMoveu.getCor() == Cor.BRANCO && destino.getLinha() == 0 ||pecaMoveu.getCor() == Cor.PRETO && destino.getLinha() == 7 ) {
				promoted = (PecaXadrez)tabuleiro.peca(destino);
				promoted = substituirPecaPromovida("R");
			}
		}
		
		xeque = (testarXeque(oponente(jogadorAtual))) ? true : false;

		if (testarXequeMate(oponente(jogadorAtual)))
			xequeMate = true;
		else
			trocaTurno();

		// movimento especial en Passant
		if (pecaMoveu instanceof Peao
				&& (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2))
			enPassantVulnerable = pecaMoveu;
		else
			enPassantVulnerable = null;

		return (PecaXadrez) capturarPeca;
	}
	
	/*método para promover a peca peao para a peca que o usuário selecionar*/
	public PecaXadrez substituirPecaPromovida(String tipo) {
		if(promoted == null)
			throw new IllegalStateException("Nao ha peca a ser promovida");
		if(!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("R") && !tipo.equals("T"))
		throw new InvalidParameterException("Peca invalida");
		
		Posicao pos = promoted.getPosicaoXadrez().toPosicao();
		Peca p = tabuleiro.moverPeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaXadrez novaPeca = novaPeca(tipo, promoted.getCor());
		tabuleiro.colocarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
		
	}
	
	/*método para criar uma peca nova*/
	public PecaXadrez novaPeca(String tipo, Cor cor) {
		if(tipo.equals("B")) return new Bispo(tabuleiro, cor);
		if(tipo.equals("C")) return new Cavalo(tabuleiro, cor);
		if(tipo.equals("R")) return new Rainha(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
	}
	
	

	/* método para remover da posição de origem e colocar na posição de destino */
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez) tabuleiro.moverPeca(origem);
		p.increaseContadorTurno();
		Peca pecaCapturada = tabuleiro.moverPeca(destino);
		tabuleiro.colocarPeca(p, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		// movimento especial castling Torre pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.moverPeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.increaseContadorTurno();
		}

		// movimento especial castling Torre grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.moverPeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.increaseContadorTurno();
		}

		// movimento especial en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO)
					posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				else
					posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());

				pecaCapturada = tabuleiro.moverPeca(posicaoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}
		return pecaCapturada;

	}

	/* método para desfazer o movimento se o usuário se colocar em xeque */
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez) tabuleiro.moverPeca(destino);
		p.decreaseContadorTurno();
		tabuleiro.colocarPeca(p, origem);

		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}

		// movimento especial castling Torre pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.moverPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.decreaseContadorTurno();
		}

		// movimento especial castling Torre grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.moverPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.decreaseContadorTurno();
		}

		// movimento especial en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == enPassantVulnerable) {
				PecaXadrez peao = (PecaXadrez) tabuleiro.moverPeca(destino);
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO)
					posicaoPeao = new Posicao(3, destino.getColuna());
				else
					posicaoPeao = new Posicao(4, destino.getColuna());

				tabuleiro.colocarPeca(peao, posicaoPeao);

			}
		}
	}

	/* método para testar se o rei está em xeque */
	private boolean testarXeque(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> pecaOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());

		for (Peca p : pecaOponente) {
			boolean[][] mat = p.possiveisMovimentos();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()])
				return true;
		}
		return false;
	}

	private boolean testarXequeMate(Cor cor) {
		if (!testarXeque(cor))
			return false;
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaXadrez) p).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean testarXeque = testarXeque(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testarXeque)
							return false;
					}
				}
			}
		}
		return true;

	}

	/*
	 * método para verificar se há peça na posição de origem solicitada pelo usuário
	 */
	private void validarOrigemPosicao(Posicao posicao) {
		if (!tabuleiro.temUmaPeca(posicao))
			throw new XadrezException("Nao ha peca na posicao de origem");
		if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor())
			throw new XadrezException("Essa peca nao e " + getJogadorAtual());
		if (!tabuleiro.peca(posicao).existeMovimentoPossivel())
			throw new XadrezException("Nao existe movimentos possiveis para peca escolhida");
	}

	/*
	 * método para verificar se há peça na posição de destino solicitada pelo
	 * usuário
	 */

	private void validarDestinoPosicao(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).possivelMovimento(destino))
			throw new XadrezException("Peca escolhida nao pode se mover para essa posicao");
	}

	/* método para trocar turno */
	private void trocaTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	/* método para retornar a cor contrário do oponente */
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private PecaXadrez rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());

		for (Peca p : list) {
			if (p instanceof Rei)
				return (PecaXadrez) p;
		}
		throw new IllegalStateException("Nao ha rei" + cor + " no tabuleiro");
	}

	/* metodo para converter as coordenadas para o xadrez */
	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}

	/* metodo responsavel por iniciar a partida, colocando as peças no tabuleiro */
	private void iniciarPartida() {

		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));

	}

}
