package geometry;

import static java.lang.Math.max;
import static java.lang.Math.min;

/// Classe que representa um retângulo definido por coordenadas em um plano bidimensional.
///
/// Esta classe permite criar e manipular retângulos utilizando coordenadas inteiras. Fornece métodos para:
///
/// - Calcular a área do retângulo.
/// - Verificar se dois retângulos se intersectam.
/// - Calcular a área de interseção entre dois retângulos.
/// - Verificar se um ponto está dentro do retângulo.
/// - Clonar o retângulo.
/// - Comparar retângulos.
///
/// As coordenadas '(x1, y1)' e '(x2, y2)' representam os cantos inferior esquerdo e superior direito do retângulo, respectivamente. É importante que 'x1 ≤ x2' e 'y1 ≤ y2' para formar um retângulo válido.
///
/// A classe implementa a interface 'Cloneable', permitindo que instâncias de 'Retangulo' sejam clonadas.
public class Rectangle implements Cloneable {

    private int x1, y1, x2, y2;

    /// Construtor que cria um retângulo a partir de coordenadas de dois pontos.
    ///
    /// Este construtor recebe as coordenadas dos cantos inferior esquerdo '(x1, y1)' e superior direito '(x2, y2)' do retângulo.
    /// Após a inicialização, verifica se as coordenadas formam um retângulo válido.
    ///
    /// @param x1 Coordenada x do primeiro ponto.
    /// @param y1 Coordenada y do primeiro ponto.
    /// @param x2 Coordenada x do segundo ponto.
    /// @param y2 Coordenada y do segundo ponto.
    /// @throws IllegalArgumentException Se as coordenadas não formarem um retângulo válido.
    public Rectangle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        if (!isValid()) throw new IllegalArgumentException("Coordenadas inválidas para formar um retângulo.");
    }

    /// Metodo auxiliar que verifica se o retângulo é válido.
    ///
    /// Um retângulo é considerado válido se 'x1 ≤ x2' e 'y1 ≤ y2'.
    ///
    /// @return 'true' se o retângulo é válido; caso contrário, 'false'.
    private boolean isValid() {
        return x1 <= x2 && y1 <= y2;
    }

    /// Metodo estático que verifica se dois retângulos se intersectam.
    ///
    /// Determina se há interseção entre dois retângulos, ou seja, se eles compartilham alguma área em comum.
    /// A verificação é feita comparando as coordenadas para determinar se eles estão separados horizontal ou verticalmente.
    ///
    /// @param r1 O primeiro retângulo.
    /// @param r2 O segundo retângulo.
    /// @return 'true' se os retângulos se intersectam; caso contrário, 'false'.
    public static boolean isIntersection(Rectangle r1, Rectangle r2) {
        if (r1.x2 < r2.x1 || r2.x2 < r1.x1) return false;
        return r1.y2 >= r2.y1 && r2.y2 >= r1.y1;
    }

    /// Calcula a área do retângulo atual.
    ///
    /// A área é calculada multiplicando a largura pela altura, considerando que cada unidade representa um ponto no plano.
    ///
    /// @return A área do retângulo.
    public int calculateArea() {
        int width = this.x2 - this.x1 + 1; // Include the points on the edges
        int height = this.y2 - this.y1 + 1; // Include the points on the edges

        return width * height;
    }

    /// Metodo estático que calcula a área de interseção entre dois retângulos.
    ///
    /// Primeiro, calcula o retângulo resultante da interseção dos dois retângulos fornecidos. Em seguida, calcula a área desse retângulo.
    ///
    /// @param r1 O primeiro retângulo.
    /// @param r2 O segundo retângulo.
    /// @return A área da interseção; se não houver interseção, retorna '0'.
    public static int areaOfIntersection(Rectangle r1, Rectangle r2) {
        Rectangle intersection = calculateIntersection(r1, r2);

        if (intersection != null) return intersection.calculateArea();

        return 0;  // Sem interseção
    }

    /// Metodo auxiliar que calcula o retângulo de interseção entre dois retângulos.
    ///
    /// Determina as coordenadas do retângulo que representa a área comum entre os dois retângulos fornecidos.
    ///
    /// @param r1 O primeiro retângulo.
    /// @param r2 O segundo retângulo.
    /// @return Um novo 'Retangulo' representando a interseção; se não houver interseção, retorna 'null'.
    private static Rectangle calculateIntersection(Rectangle r1, Rectangle r2) {
        int interLeft = max(r1.x1, r2.x1);
        int interRight = min(r1.x2, r2.x2);
        int interBottom = max(r1.y1, r2.y1);
        int interTop = min(r1.y2, r2.y2);

        if (interLeft <= interRight && interBottom <= interTop)
            return new Rectangle(
                    interLeft,
                    interBottom,
                    interRight,
                    interTop
            );

        return null; // Sem interseção
    }

    /// Retorna a largura do retângulo.
    ///
    /// Calcula a largura como a diferença entre as coordenadas x dos pontos extremos.
    ///
    /// @return A largura do retângulo.
    public int getWidth() {
        return x2 - x1;
    }

    /// Retorna a altura do retângulo.
    ///
    /// Calcula a altura como a diferença entre as coordenadas y dos pontos extremos.
    ///
    /// @return A altura do retângulo.
    public int getHeight() {
        return y2 - y1;
    }

    /// Verifica se um ponto está dentro do retângulo.
    ///
    /// Determina se as coordenadas '(x, y)' fornecidas estão dentro ou na borda do retângulo.
    ///
    /// @param x Coordenada x do ponto.
    /// @param y Coordenada y do ponto.
    /// @return `true` se o ponto está dentro do retângulo; caso contrário,`false`.
    public boolean isPointInside(int x, int y) {
        return x >= x1 && x <= x2 &&
               y >= y1 && y <= y2;
    }

    /// Retorna a coordenada x1 (canto inferior esquerdo) do retângulo.
    ///
    /// @return Coordenada x1.
    public int getX1() {
        return x1;
    }

    /// Retorna a coordenada y1 (canto inferior esquerdo) do retângulo.
    ///
    /// @return Coordenada y1.
    public int getY1() {
        return y1;
    }

    /// Retorna a coordenada x2 (canto superior direito) do retângulo.
    ///
    /// @return Coordenada x2.
    public int getX2() {
        return x2;
    }

    /// Retorna a coordenada y2 (canto superior direito) do retângulo.
    ///
    /// @return Coordenada y2.
    public int getY2() {
        return y2;
    }

    /// Metodo auxiliar que valida o retângulo após modificações nas coordenadas.
    ///
    /// Verifica se as coordenadas atuais formam um retângulo válido. Se não forem, lança uma exceção.
    private void validateRectangle() {
        if (!isValid()) throw new IllegalArgumentException("Coordenadas inválidas para formar um retângulo.");
    }

    /// Define a coordenada x1 do retângulo.
    ///
    /// Após a modificação, o retângulo é validado.
    ///
    /// @param x1 Nova coordenada x1.
    /// @throws IllegalArgumentException Se as coordenadas resultarem em um retângulo inválido.
    public void setX1(int x1) {
        this.x1 = x1;
        validateRectangle();
    }

    /// Define a coordenada y1 do retângulo.
    ///
    /// Após a modificação, o retângulo é validado.
    ///
    /// @param y1 Nova coordenada y1.
    /// @throws IllegalArgumentException Se as coordenadas resultarem em um retângulo inválido.
    public void setY1(int y1) {
        this.y1 = y1;
        validateRectangle();
    }

    /// Define a coordenada x2 do retângulo.
    ///
    /// Após a modificação, o retângulo é validado.
    ///
    /// @param x2 Nova coordenada x2.
    /// @throws IllegalArgumentException Se as coordenadas resultarem em um retângulo inválido.
    public void setX2(int x2) {
        this.x2 = x2;
        validateRectangle();
    }

    /// Define a coordenada y2 do retângulo.
    ///
    /// Após a modificação, o retângulo é validado.
    ///
    /// @param y2 Nova coordenada y2.
    /// @throws IllegalArgumentException Se as coordenadas resultarem em um retângulo inválido.
    public void setY2(int y2) {
        this.y2 = y2;
        validateRectangle();
    }

    /// Construtor de cópia que cria um novo retângulo com as mesmas coordenadas de copy.
    ///
    /// @param copy O retângulo a ser copiado.
    /// @throws IllegalArgumentException Se o retângulo fornecido for `null`.
    public Rectangle(Rectangle copy) {
        if (copy == null) throw new IllegalArgumentException("O retângulo a ser copiado não pode ser nulo.");

        this.x1 = copy.getX1();
        this.y1 = copy.getY1();
        this.x2 = copy.getX2();
        this.y2 = copy.getY2();
    }

    /// Cria e retorna uma cópia deste retângulo.
    ///
    /// Implementa o metodo 'clone()' da interface 'Cloneable', permitindo a clonagem do objeto.
    ///
    /// @return Um clone deste retângulo.
    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public Rectangle clone() {
        Rectangle clone = null;
        try {
            clone = new Rectangle(this);
        } catch (Exception ignored) {
        }
        return clone;
    }

    /// Calcula o código hash deste retângulo.
    ///
    /// O código hash é baseado nas coordenadas, garantindo que retângulos com as mesmas coordenadas tenham o mesmo código hash.
    ///
    /// @return O código hash do retângulo.
    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = x1;

        hash *= prime + y1;
        hash *= prime + x2;
        hash *= prime + y2;

        if (hash < 0) hash = -hash;

        return hash;
    }

    /// Verifica se este retângulo é igual a outro objeto.
    ///
    /// A igualdade é determinada comparando as coordenadas dos retângulos.
    ///
    /// @param o O objeto a ser comparado.
    /// @return 'true' se os retângulos têm as mesmas coordenadas; caso contrário, 'false'.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Rectangle that = (Rectangle) o;

        return this.x1 == that.x1 &&
               this.y1 == that.y1 &&
               this.x2 == that.x2 &&
               this.y2 == that.y2;
    }

    /// Retorna uma representação em 'String' deste retângulo.
    ///
    /// A representação inclui as coordenadas dos pontos extremos no formato 'Retangulo [(x1, y1), (x2, y2)]'.
    ///
    /// @return Uma 'String' representando o retângulo.
    @Override
    public String toString() {
        return "Retangulo [(" + x1 + ", " + y1 + "), (" + x2 + ", " + y2 + ")]";
    }
}
