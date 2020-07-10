package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	private PartidaXadrez partidaXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.BRANCO) {
			p.setValues(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p))
				mat[p.getLinha()][p.getColuna()] = true;

			// primeiro movimento do peao
			p.setValues(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temUmaPeca(p2)
					&& getContadorTurno() == 0)
				mat[p.getLinha()][p.getColuna()] = true;

			// movimento para noroeste se tiver peca adversaria
			p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && existePecaOponente(p))
				mat[p.getLinha()][p.getColuna()] = true;

			// movimento para nordeste se tiver peca adversaria
			p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && existePecaOponente(p))
				mat[p.getLinha()][p.getColuna()] = true;

			// movimento especial enPassant peca branca
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExiste(esquerda) && existePecaOponente(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassantVulnerable())
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;

				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExiste(direita) && existePecaOponente(direita)
						&& getTabuleiro().peca(direita) == partidaXadrez.getEnPassantVulnerable())
					mat[direita.getLinha() - 1][direita.getColuna()] = true;

			}
		} else {
			p.setValues(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p))
				mat[p.getLinha()][p.getColuna()] = true;

			// primeiro movimento do peao
			p.setValues(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temUmaPeca(p2)
					&& getContadorTurno() == 0)
				mat[p.getLinha()][p.getColuna()] = true;

			// movimento para noroeste se tiver peca adversaria
			p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && existePecaOponente(p))
				mat[p.getLinha()][p.getColuna()] = true;

			// movimento para nordeste se tiver peca adversaria
			p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && existePecaOponente(p))
				mat[p.getLinha()][p.getColuna()] = true;

			// movimento especial enPassant peca preta
			if (posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExiste(esquerda) && existePecaOponente(esquerda)
						&& getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassantVulnerable())
					mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;

				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExiste(direita) && existePecaOponente(direita)
						&& getTabuleiro().peca(direita) == partidaXadrez.getEnPassantVulnerable())
					mat[direita.getLinha() + 1][direita.getColuna()] = true;

			}
		}

		return mat;
	}

	public String toString() {
		return "P";
	}

}
