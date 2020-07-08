package application;

import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {
	
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partida = new PartidaXadrez();
		
		while(true) {
			UI.printBoard(partida.getPecas());
			System.out.println();
			System.out.println("Origem: ");
			PosicaoXadrez origem = UI.lerPosicao(sc);
			
			System.out.println();
			System.out.println("Destino: ");
			PosicaoXadrez destino = UI.lerPosicao(sc);
			
			PecaXadrez pecaCapturada = partida.executarXadrez(origem, destino);
		}
		
		

	}

}
