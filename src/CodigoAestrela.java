/*Alunos:
 *   Gabriely Rodrigues de Carmo
 *   João Pedro Mauad Nogueira
 */

import java.util.*;

public class CodigoAestrela {

    // ------------------ DEFINIÇÃO DE VARIÁVEIS -------------------
    private static int Custo_Hori_Vert = 10;
    private static int Custo_Diagonal = 14;
    private int custoVH;
    private int custoDiagonal;
    private Vertice[][] buscaArea;
    private PriorityQueue<Vertice> elementosVisitados;
    private Set<Vertice> naoVisitados;
    private Vertice verticeInicial;
    private Vertice verticeFinal;

    // --------------------------------------------------------------

    // -------------------------------------------- MÉTODOS ----------------------------------------------------

    // CONSTRUTOR
    public CodigoAestrela(int linhas, int colunas, Vertice verticeInicial, Vertice verticeFinal, int custoVH, int custoDiagonal) {
        this.custoVH = custoVH;
        this.custoDiagonal = custoDiagonal;
        setVerticeInicial(verticeInicial);
        setVerticeFinal(verticeFinal);
        this.buscaArea = new Vertice[linhas][colunas];
        this.elementosVisitados = new PriorityQueue<Vertice>(new Comparator<Vertice>() {
            @Override
            public int compare(Vertice vertice0, Vertice vertice1) {
                return Integer.compare(vertice0.getF(), vertice1.getF());
            }
        });
        ajustaVertices();
        this.naoVisitados = new HashSet<>();
    }

    // CONSTRUTOR
    public CodigoAestrela(int linhas, int colunas, Vertice verticeInicial, Vertice verticeFinal) {
        this(linhas, colunas, verticeInicial, verticeFinal, Custo_Hori_Vert, Custo_Diagonal);
    }

    /*
     * Setta como vertice Pai o atual e soma o custo anterior de percorrer o caminho mais o do vertice atual
     */
    private void ajustaVertices() {
        for (int i = 0; i < buscaArea.length; i++) {
            for (int j = 0; j < buscaArea[0].length; j++) {
                Vertice vertice = new Vertice(i, j);
                vertice.calculaHeuristica(getVerticeFinal());
                this.buscaArea[i][j] = vertice;
            }
        }
    }

    /*
     * Posiciona obstaculos ou paredes definidas no array.
     */
    public void ajustaParede(int[][] paredeArray) {
        for (int i = 0; i < paredeArray.length; i++) {
            int linha = paredeArray[i][0];
            int coluna = paredeArray[i][1];
            setParede(linha, coluna);
        }
    }

    /*
     * Principais elementos do algoritmos A estrela. Adicionando o vertice incial como sendo o primeiro do caminho a ser percorrido.
     */
    public List<Vertice> achaCaminho() {
        elementosVisitados.add(verticeInicial);
        while (!isEmpty(elementosVisitados)) {
            Vertice verticeAtual = elementosVisitados.poll();
            naoVisitados.add(verticeAtual);
            if (isVerticeFinal(verticeAtual)) {
                return getCaminho(verticeAtual);
            } else {
                adicionaVerticeAdjacente(verticeAtual);
            }
        }
        return new ArrayList<Vertice>();
    }

    /*
     * Adiciona no caminho o vertice atual
     */
    private List<Vertice> getCaminho(Vertice verticeAtual) {
        List<Vertice> path = new ArrayList<Vertice>();
        path.add(verticeAtual);
        Vertice verticePai;
        while ((verticePai = verticeAtual.getVerticePai()) != null) {
            path.add(0, verticePai);
            verticeAtual = verticePai;
        }
        return path;
    }

    /*
     * Chama os métodos que adicionam vertices adjacentes
     */
    private void adicionaVerticeAdjacente(Vertice verticeAtual) {
        adicionaVerticeAdjacenteLinhaSuperior(verticeAtual);
        adicionaVerticeAdjacenteLinhaCentral(verticeAtual);
        adicionaVerticeAdjacenteLinhaInferior(verticeAtual);
    }

    private void adicionaVerticeAdjacenteLinhaInferior(Vertice verticeAtual) {
        int linha = verticeAtual.getLinha();
        int coluna = verticeAtual.getColuna();
        int linhaInferior = linha + 1;
        if (linhaInferior < getBuscaArea().length) {
            if (coluna - 1 >= 0) {
                verificaVertice(verticeAtual, coluna - 1, linhaInferior, getCustoDiagonal()); // Comment this line if diagonal movements are not allowed
            }
            if (coluna + 1 < getBuscaArea()[0].length) {
                verificaVertice(verticeAtual, coluna + 1, linhaInferior, getCustoDiagonal()); // Comment this line if diagonal movements are not allowed
            }
            verificaVertice(verticeAtual, coluna, linhaInferior, getCustoVH());
        }
    }

    private void adicionaVerticeAdjacenteLinhaCentral(Vertice verticeAtual) {
        int linha = verticeAtual.getLinha();
        int coluna = verticeAtual.getColuna();
        int linhaCentral = linha;
        if (coluna - 1 >= 0) {
            verificaVertice(verticeAtual, coluna - 1, linhaCentral, getCustoVH());
        }
        if (coluna + 1 < getBuscaArea()[0].length) {
            verificaVertice(verticeAtual, coluna + 1, linhaCentral, getCustoVH());
        }
    }

    private void adicionaVerticeAdjacenteLinhaSuperior(Vertice verticeAtual) {
        int row = verticeAtual.getLinha();
        int col = verticeAtual.getColuna();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            if (col - 1 >= 0) {
                verificaVertice(verticeAtual, col - 1, upperRow, getCustoDiagonal()); // Comment this if diagonal movements are not allowed
            }
            if (col + 1 < getBuscaArea()[0].length) {
                verificaVertice(verticeAtual, col + 1, upperRow, getCustoDiagonal()); // Comment this if diagonal movements are not allowed
            }
            verificaVertice(verticeAtual, col, upperRow, getCustoVH());
        }
    }

    private void verificaVertice(Vertice verticeAtual, int colunas, int linhas, int custo) {
        Vertice adjacentVertice = getBuscaArea()[linhas][colunas];
        if (!adjacentVertice.isParede() && !getNaoVisitados().contains(adjacentVertice)) {
            if (!getElementosVisitados().contains(adjacentVertice)) {
                adjacentVertice.setDataVertice(verticeAtual, custo);
                getElementosVisitados().add(adjacentVertice);
            } else {
                boolean changed = adjacentVertice.verificaMelhorCaminho(verticeAtual, custo);
                if (changed) {
                    // Remove and Add the changed node, so that the PriorityQueue can sort again its
                    // contents with the modified "finalCost" value of the modified node
                    getElementosVisitados().remove(adjacentVertice);
                    getElementosVisitados().add(adjacentVertice);
                }
            }
        }
    }


    /*
     * Verifica se chegou no último vértice
     */
    private boolean isVerticeFinal(Vertice currentVertice) {
        return currentVertice.equals(verticeFinal);
    }

    private boolean isEmpty(PriorityQueue<Vertice> openList) {
        return openList.size() == 0;
    }

    // ------------------------------------------------------------------------------------------------------------------------------

    // ---------------------------------------- GETTERS E SETTERS ---------------------------------

    private void setParede(int row, int col) {
        this.buscaArea[row][col].setParede(true);
    }

    public Vertice getVerticeInicial() {
        return verticeInicial;
    }

    public void setVerticeInicial(Vertice verticeInicial) {
        this.verticeInicial = verticeInicial;
    }

    public Vertice getVerticeFinal() {
        return verticeFinal;
    }

    public void setVerticeFinal(Vertice verticeFinal) {
        this.verticeFinal = verticeFinal;
    }

    public Vertice[][] getBuscaArea() {
        return buscaArea;
    }

    public void setBuscaArea(Vertice[][] buscaArea) {
        this.buscaArea = buscaArea;
    }

    public PriorityQueue<Vertice> getElementosVisitados() {
        return elementosVisitados;
    }

    public void setElementosVisitados(PriorityQueue<Vertice> elementosVisitados) {
        this.elementosVisitados = elementosVisitados;
    }

    public Set<Vertice> getNaoVisitados() {
        return naoVisitados;
    }

    public void setNaoVisitados(Set<Vertice> naoVisitados) {
        this.naoVisitados = naoVisitados;
    }

    public int getCustoVH() {
        return custoVH;
    }

    public void setCustoVH(int custoVH) {
        this.custoVH = custoVH;
    }

    private int getCustoDiagonal() {
        return custoDiagonal;
    }

    private void setCustoDiagonal(int custoDiagonal) {
        this.custoDiagonal = custoDiagonal;
    }

    // -----------------------------------------------------------------------------------------
}


