package geometry;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Retangulo implements Cloneable {

    private int x1, y1, x2, y2;

    // Construtor para criar um retângulo
    public Retangulo(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        // A verificação de validade agora trata casos de interseção em um ponto ou linha
        if (!isValid()) throw new IllegalArgumentException("Coordenadas inválidas para formar um retângulo.");
    }

    // Verifica se o retângulo é válido
    // Agora permite retângulos onde as dimensões podem ser zero (interseções unidimensionais)
    private boolean isValid() {
        return x1 <= x2 && y1 <= y2;
    }

    // Metodo que verifica se dois retângulos se intersectam
    public static boolean intersects(Retangulo r1, Retangulo r2) {
        // Verifica se um retângulo está à esquerda ou à direita do outro
        if (r1.x2 < r2.x1 || r2.x2 < r1.x1) return false;

        // Verifica se um retângulo está acima ou abaixo do outro
        return r1.y2 >= r2.y1 && r2.y2 >= r1.y1;
    }

    // Calcula a área do retângulo atual
    public int calcularArea() {
        int largura = this.x2 - this.x1 + 1; // Inclui os pontos nas bordas
        int altura = this.y2 - this.y1 + 1;  // Inclui os pontos nas bordas

        return largura * altura;
    }

    // Metodo estático para calcular a área de interseção entre dois retângulos
    public static int areaOfIntersection(Retangulo r1, Retangulo r2) {
        // Calcula as coordenadas do ponto inferior esquerdo e superior direito da interseção
        Retangulo interseccao = calcularInterseccao(r1, r2);

        if (interseccao != null) return interseccao.calcularArea();

        return 0;  // Sem interseção
    }

    // Metodo auxiliar para calcular a interseção entre dois retângulos
    private static Retangulo calcularInterseccao(Retangulo r1, Retangulo r2) {
        int interLeft, interRight, interBottom, interTop;

        interLeft = max(r1.x1, r2.x1);
        interRight = min(r1.x2, r2.x2);
        interBottom = max(r1.y1, r2.y1);
        interTop = min(r1.y2, r2.y2);

        // Verifica se existe uma interseção válida
        if (interLeft <= interRight && interBottom <= interTop)
            return new Retangulo(interLeft, interBottom, interRight, interTop);

        return null; // Sem interseção
    }

    // Retorna a largura do retângulo
    public int getWidth() {
        return x2 - x1;
    }

    // Retorna a altura do retângulo
    public int getHeight() {
        return y2 - y1;
    }

    public boolean isPointInside(int x, int y) {
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }

    // Getters
    public int getX1() {
        return x1;
    }
    public int getY1() {
        return y1;
    }
    public int getX2() {
        return x2;
    }
    public int getY2() {
        return y2;
    }

    // Valida o retângulo após qualquer modificação nas coordenadas
    private void validateRectangle() {
        if (!isValid()) {
            throw new IllegalArgumentException("Coordenadas inválidas para formar um retângulo.");
        }
    }

    // Setters
    public void setX1(int x1) {
        this.x1 = x1;
        validateRectangle();  // Valida após a modificação
    }
    public void setY1(int y1) {
        this.y1 = y1;
        validateRectangle();  // Valida após a modificação
    }
    public void setX2(int x2) {
        this.x2 = x2;
        validateRectangle();  // Valida após a modificação
    }
    public void setY2(int y2) {
        this.y2 = y2;
        validateRectangle();  // Valida após a modificação
    }

    // Construtor de cópia
    public Retangulo(Retangulo outro) {
        if (outro == null) throw new IllegalArgumentException("O retângulo a ser copiado não pode ser nulo.");

        // Copia os valores dos atributos
        this.x1 = outro.getX1();
        this.y1 = outro.getY1();
        this.x2 = outro.getX2();
        this.y2 = outro.getY2();
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public Retangulo clone() {
        Retangulo clone = null;
        try {
            clone = new Retangulo(this);
        } catch (Exception ignored) {
        }
        return clone;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = x1;

        hash *= prime * hash + y1;
        hash *= prime * hash + x2;
        hash *= prime * hash + y2;

        if (hash < 0) hash = -hash;

        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Retangulo that = (Retangulo) o;

        return this.x1 == that.x1 &&
               this.y1 == that.y1 &&
               this.x2 == that.x2 &&
               this.y2 == that.y2;
    }

    @Override
    public String toString() {
        return "Retangulo [(" + x1 + ", " + y1 + "), (" + x2 + ", " + y2 + ")]";
    }
}
