import java.util.Random;
import java.util.Scanner;

public class CampoMinado {

    private static final int bomba = -1;
    private static final int livre = 0;
    private static final int escolhido = 1;

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.println("Bem-vindo ao Campo Minado!");
        System.out.print("Informe o número de linhas da matriz: ");
        int linhas = entrada.nextInt();
        System.out.print("Informe o número de colunas da matriz: ");
        int colunas = entrada.nextInt();
        System.out.print("Informe a quantidade de bombas: ");
        int bombas = entrada.nextInt();

        if (linhas < 2 || colunas < 2 || bombas <= 0 || bombas >= linhas * colunas) {
            System.out.println("Entrada inválida. Certifique-se de que a matriz tenha no mínimo 2x2 e o número de bombas seja maior que 0 e menor que a quantidade de células da matriz.");
            return;
        }

        int[][] campoMinado = criarCampoMinado(linhas, colunas, bombas);
        int pontuacaoMaxima = (linhas * colunas) - bombas;

        while (true) {
            imprimirCampo(campoMinado);

            System.out.print("Escolha uma posição (linha coluna): ");
            int linhaEscolhida = entrada.nextInt();
            int colunaEscolhida = entrada.nextInt();

            if (!validarEntrada(linhaEscolhida, colunaEscolhida, linhas, colunas) ||
                    campoMinado[linhaEscolhida][colunaEscolhida] == escolhido) {
                System.out.println("Posição inválida ou já escolhida. Tente novamente.");
                continue;
            }

            if (campoMinado[linhaEscolhida][colunaEscolhida] == bomba) {
                System.out.println("Game Over! Você escolheu uma posição com bomba.");
                break;
            }

            if (temBombaAoRedor(campoMinado, linhaEscolhida, colunaEscolhida)) {
                System.out.println("Cuidado: bomba próxima!");
            }

            campoMinado[linhaEscolhida][colunaEscolhida] = escolhido;
            pontuacaoMaxima--;

            if (pontuacaoMaxima == 0) {
                imprimirCampo(campoMinado);
                System.out.println("Parabéns, você ganhou o jogo!");
                break;
            }
        }
    }

    private static int[][] criarCampoMinado(int linhas, int colunas, int bombas) {
        int[][] campoMinado = new int[linhas][colunas];
        Random random = new Random();

        while (bombas > 0) {
            int linha = random.nextInt(linhas);
            int coluna = random.nextInt(colunas);

            if (campoMinado[linha][coluna] != bomba) {
                campoMinado[linha][coluna] = bomba;
                bombas--;
            }
        }

        return campoMinado;
    }

    private static void imprimirCampo(int[][] campoMinado) {
        System.out.println("Campo Minado:");
        for (int[] linha : campoMinado) {
            for (int celula : linha) {
                if (celula == livre) {
                    System.out.print(" _ ");
                } else if (celula == escolhido) {
                    System.out.print(" x ");
                } else {
                    System.out.print(" b ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean temBombaAoRedor(int[][] campoMinado, int linha, int coluna) {
        int linhas = campoMinado.length;
        int colunas = campoMinado[0].length;

        for (int i = linha - 1; i <= linha + 1; i++) {
            for (int j = coluna - 1; j <= coluna + 1; j++) {
                if (validarEntrada(i, j, linhas, colunas) && campoMinado[i][j] == bomba) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean validarEntrada(int linha, int coluna, int linhas, int colunas) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }
}
