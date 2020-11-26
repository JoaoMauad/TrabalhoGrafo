
    /*Alunos:
     *   Gabriely Rodrigues de Carmo
     *   João Pedro Mauad Nogueira
     */

import java.util.List;

    public class Main {
        static void imprimeFuncao(String[][]imagens, int linha, int coluna){
            for (int x=0; x<linha; x++){
                for (int y=0; y<coluna;y++){
                    System.out.print(imagens[x][y]+" ");
                }
                System.out.println("");
            }
            System.out.println("");
        }
        public static void main(String[] args) {
            Vertice verticeInicial = new Vertice(2, 1);
            Vertice verticeFinal = new Vertice(2, 5);
            int linhas = 6;
            int colunas = 7;

            CodigoAestrela codigoAestrela = new CodigoAestrela(linhas, colunas, verticeInicial, verticeFinal);
            int[][] paredeArray = new int[][]{{1, 3}, {2, 3}, {3, 3}};
            codigoAestrela.ajustaParede(paredeArray);
            String [][] imagem = {{"-","-","-","-","-","-","-"},{"-","-","-","B","-","-","-"},{"-","I","-","B","-","F","-"},
                    {"-","-","-","B","-","-","-"},{"-","-","-","-","-","-","-"},{"-","-","-","-","-","-","-"}};
            final String [][] imagemCopia = {{"-","-","-","-","-","-","-"},{"-","-","-","B","-","-","-"},{"-","I","-","B","-","F","-"},
                    {"-","-","-","B","-","-","-"},{"-","-","-","-","-","-","-"},{"-","-","-","-","-","-","-"}};
            List<Vertice> caminho = codigoAestrela.achaCaminho();
            for (Vertice vertice : caminho) {
                imagemCopia[vertice.getLinha()][vertice.getColuna()] = "*";
                System.out.println(vertice);
            }

            imprimeFuncao(imagem,linhas,colunas);
            imprimeFuncao(imagemCopia,linhas,colunas);


            //
            //      0   1   2   3   4   5   6
            // 0    -   -   -   -   -   -   -
            // 1    -   -   -   B   -   -   -
            // 2    -   I   -   B   -   F   -
            // 3    -   -   -   B   -   -   -
            // 4    -   -   -   -   -   -   -
            // 5    -   -   -   -   -   -   -

            //
            //      0   1   2   3   4   5   6
            // 0    -   -   -   *   -   -   -
            // 1    -   -   *   B   *   -   -
            // 2    -   I*  -   B   -  *F   -
            // 3    -   -   -   B   -   -   -
            // 4    -   -   -   -   -   -   -
            // 5    -   -   -   -   -   -   -

        }
    }


