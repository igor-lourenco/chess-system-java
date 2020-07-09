package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Programa {

	public static void main(String[] args) {
	
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partida = new PartidaXadrez();
		
		while(true) {
			try {
			UI.limparTela();
			UI.printPartida(partida);
			System.out.println();
			System.out.print("Origem: ");
			PosicaoXadrez origem = UI.lerPosicao(sc);
			
			//declara uma matriz boolean
			boolean [][] possiveisMovimentos = partida.possiveisMovimentos(origem);
			//limpa a tela
			UI.limparTela();
			//imprime o tabuleiro com a versao dos movimentos possiveis da peca
			UI.printTabuleiro(partida.getPecas(), possiveisMovimentos);
			
			System.out.println();
			System.out.print("Destino: ");
			PosicaoXadrez destino = UI.lerPosicao(sc);
			
			PecaXadrez pecaCapturada = partida.executarXadrez(origem, destino);
			}catch(XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		
		

	}

}
