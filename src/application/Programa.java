package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaXadrez partida = new PartidaXadrez();
		List<PecaXadrez> capturada = new ArrayList<>();

		while (true) {
			try {
				UI.limparTela();
				UI.printPartida(partida, capturada);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicao(sc);

				// declara uma matriz boolean
				boolean[][] possiveisMovimentos = partida.possiveisMovimentos(origem);
				// limpa a tela
				UI.limparTela();
				// imprime o tabuleiro com a versao dos movimentos possiveis da peca
				UI.printTabuleiro(partida.getPecas(), possiveisMovimentos);

				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lerPosicao(sc);

				PecaXadrez pecaCapturada = partida.executarMovimentoXadrez(origem, destino);

				if (pecaCapturada != null)
					capturada.add(pecaCapturada);

			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}

	}

}
