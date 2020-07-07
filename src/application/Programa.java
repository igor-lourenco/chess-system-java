package application;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.PartidaXadrez;

public class Programa {

	public static void main(String[] args) {
	
		PartidaXadrez partida = new PartidaXadrez();
		
		UI.printBoard(partida.getPecas());
		

	}

}
