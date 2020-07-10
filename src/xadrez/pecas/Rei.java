package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {
	
	private PartidaXadrez partidaXadrez;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	public String toString() {
		return "R'";
	}
	
	/*método para testar se o Rei e a Torre não fizeram nenhum movimento*/
	private boolean testarTorreCastling(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContadorTurno() == 0;
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// movimento para cima
		p.setValues(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p))
			mat[p.getLinha()][p.getColuna()] = true;

		// movimento para baixo
		p.setValues(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p))
			mat[p.getLinha()][p.getColuna()] = true;

		// movimento para esquerda
		p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p))
			mat[p.getLinha()][p.getColuna()] = true;

		// movimento para direita
		p.setValues(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p))
			mat[p.getLinha()][p.getColuna()] = true;

		// movimento para noroeste
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p))
			mat[p.getLinha()][p.getColuna()] = true;

		// movimento para nordeste
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p))
			mat[p.getLinha()][p.getColuna()] = true;

		// movimento para sudoeste
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p))
			mat[p.getLinha()][p.getColuna()] = true;

		// movimento para sudeste
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p))
			mat[p.getLinha()][p.getColuna()] = true;
		
		//movimento especial castling Torre
		if(getContadorTurno() == 0 && !partidaXadrez.getXeque()) {
			//castling  Torre pequeno
			Posicao posicaoT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
			if(testarTorreCastling(posicaoT1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
				
				if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null)
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
			}
			//castling Torre grande
			Posicao posicaoT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
			if(testarTorreCastling(posicaoT2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
				
				if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null)
					mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
			}
			
		}

		return mat;
	}
}
