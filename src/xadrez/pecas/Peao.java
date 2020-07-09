package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez{

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);
		
		if(getCor() == Cor.BRANCO) {
			p.setValues(posicao.getLinha() - 1,  posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p))
				mat[p.getLinha()][p.getColuna()] = true;
		
		
		//primeiro movimento do peao
		p.setValues(posicao.getLinha() - 2,  posicao.getColuna());
		Posicao p2 = new Posicao(posicao.getLinha() - 1,  posicao.getColuna());
		if(getTabuleiro().posicaoExiste(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temUmaPeca(p2) && getContadorTurno() == 0)
			mat[p.getLinha()][p.getColuna()] = true;
		
		//movimento para noroeste se tiver peca adversaria
		p.setValues(posicao.getLinha() - 1,  posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p))
			mat[p.getLinha()][p.getColuna()] = true;
		
		//movimento para nordeste se tiver peca adversaria
		p.setValues(posicao.getLinha() - 1,  posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p))
			mat[p.getLinha()][p.getColuna()] = true;
		}
		else {
			p.setValues(posicao.getLinha() + 1,  posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p))
				mat[p.getLinha()][p.getColuna()] = true;
		
		
		//primeiro movimento do peao
		p.setValues(posicao.getLinha() + 2,  posicao.getColuna());
		Posicao p2 = new Posicao(posicao.getLinha() + 1,  posicao.getColuna());
		if(getTabuleiro().posicaoExiste(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temUmaPeca(p2) && getContadorTurno() == 0)
			mat[p.getLinha()][p.getColuna()] = true;
		
		//movimento para noroeste se tiver peca adversaria
		p.setValues(posicao.getLinha() + 1,  posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p))
			mat[p.getLinha()][p.getColuna()] = true;
		
		//movimento para nordeste se tiver peca adversaria
		p.setValues(posicao.getLinha() + 1,  posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExiste(p) && existePecaOponente(p))
			mat[p.getLinha()][p.getColuna()] = true;
			
		}
		return mat;
	}
	
	public String toString() {
		return "P";
	}

}
