
    /*Alunos:
     *   Gabriely Rodrigues de Carmo
     *   João Pedro Mauad Nogueira
     */

    public class Vertice {

        // ------------------ DEFINIÇÃO DE VARIÁVEIS -------------------

        private int G;
        private int F;
        private int H;
        private int linha;
        private int coluna;
        private boolean isParede;
        private Vertice verticePai;

        // --------------------------------------------------------------

        // -------------------------------------------- MÉTODOS ----------------------------------------------------

        // CONSTRUTOR
        public Vertice(int linha, int coluna) {
            super();
            this.linha = linha;
            this.coluna = coluna;
        }

        /*
         * Função recebe vertice final e calcula distância entre o atual, resultante da soma entre linhas e colunas multiplicadas por 10
         */
        public void calculaHeuristica(Vertice verticeFinal) {
            this.H = Math.abs(verticeFinal.getLinha() - getLinha()) + Math.abs(verticeFinal.getColuna() - getColuna());
        }

        /*
         * Setta como vertice Pai o atual e soma o custo anterior de percorrer o caminho mais o do vertice atual
         */
        public void setDataVertice(Vertice verticeAtual, int custo) {
            int gCost = verticeAtual.getG() + custo;
            setVerticePai(verticeAtual);
            setG(gCost);
            calculaCustoFinal();
        }

        public boolean verificaMelhorCaminho(Vertice verticeAtual, int custo) {
            int gCost = verticeAtual.getG() + custo;
            if (gCost < getG()) {
                setDataVertice(verticeAtual, custo);
                return true;
            }
            return false;
        }

        /*
         * Função soma os valores de G e H obtendo F
         */
        private void calculaCustoFinal() {
            int finalCost = getG() + getH();
            setF(finalCost);
        }

        // ------------------------------------------------------------------------------------------------------------------------------

        @Override
        public boolean equals(Object arg0) {
            Vertice other = (Vertice) arg0;
            return this.getLinha() == other.getLinha() && this.getColuna() == other.getColuna();
        }

        @Override
        public String toString() {
            return "Vertice [linha=" + linha + ", coluna=" + coluna + "]";
        }

        // ---------------------------------------- GETTERS E SETTERS ---------------------------------

        public int getH() {
            return H;
        }

        public void setH(int h) {
            this.H = h;
        }

        public int getG() {
            return G;
        }

        public void setG(int g) {
            this.G = g;
        }

        public int getF() {
            return F;
        }

        public void setF(int f) {
            this.F = f;
        }

        public Vertice getVerticePai() {
            return verticePai;
        }

        public void setVerticePai(Vertice verticePai) {
            this.verticePai = verticePai;
        }

        public int getLinha() {
            return linha;
        }

        public int getColuna() {
            return coluna;
        }

        public boolean isParede() {
            return isParede;
        }

        public void setParede(boolean parede) {
            isParede = parede;
        }
    }
