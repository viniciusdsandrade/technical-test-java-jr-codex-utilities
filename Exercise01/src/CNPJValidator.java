import static java.lang.Character.getNumericValue;

/// Classe responsável por realizar a validação de um CNPJ.
///
/// Esta classe não faz uso de bibliotecas externas, implementando manualmente a
/// lógica de validação conforme os critérios estabelecidos pela Receita Federal.
/// O CNPJ pode ser validado tanto no formato com pontuações, como "00.000.000/0000-00",
/// quanto no formato sem pontuações, como "00000000000000".
///
/// O principal objetivo é verificar se o CNPJ é válido, analisando seu tamanho, a
/// estrutura numérica, e calculando os dígitos verificadores.
///
/// A lógica principal é dividida em:
/// - Remoção de caracteres não numéricos.
/// - Verificação de tamanho correto.
/// - Cálculo dos dígitos verificadores.
/// - Comparação dos dígitos calculados com os dígitos fornecidos.
public class CNPJValidator {

    /// Metodo principal para validar o CNPJ.
    ///
    /// Este metodo realiza a validação completa de um CNPJ, verificando:
    /// - Se contém apenas números.
    /// - Se possui exatamente 14 dígitos.
    /// - Se os dígitos verificadores estão corretos.
    ///
    /// @param cnpj Uma string representando o CNPJ a ser validado.
    /// Pode estar no formato "00.000.000/0000-00" ou "00000000000000".
    /// @return true se o CNPJ for válido, false caso contrário.
    public static boolean validarCNPJ(String cnpj) {
        String cnpjNumeros, cnpjBase, digitoVerificadorCalculado, digitoVerificadorOriginal;

        // Remove todos os caracteres não numéricos do CNPJ.
        cnpjNumeros = cnpj.replaceAll("\\D", "");

        // Verifica se o CNPJ contém exatamente 14 dígitos após remover pontuações.
        if (cnpjNumeros.length() != 14) {
            System.err.println("CNPJ com tamanho incorreto: " + cnpjNumeros.length());
            return false;
        }

        // Verifica se todos os dígitos do CNPJ são iguais, pois um CNPJ assim seria inválido.
        if (cnpjNumeros.chars().distinct().count() == 1) {
            System.err.println("CNPJ com todos os dígitos iguais.");
            return false;
        }

        // Extrai a parte base do CNPJ (os primeiros 12 dígitos).
        cnpjBase = cnpjNumeros.substring(0, 12);

        // Calcula os dois dígitos verificadores do CNPJ base.
        digitoVerificadorCalculado = calcularDigitosVerificadores(cnpjBase);

        // Obtém os dígitos verificadores fornecidos no CNPJ.
        digitoVerificadorOriginal = cnpjNumeros.substring(12, 14);

        // Compara os dígitos calculados com os dígitos fornecidos.
        return digitoVerificadorCalculado.equals(digitoVerificadorOriginal);
    }

    /// Metodo para calcular os dois dígitos verificadores de um CNPJ.
    ///
    /// Este metodo segue a fórmula oficial da Receita Federal para calcular os dígitos
    /// verificadores. Primeiramente, é calculado o primeiro dígito, que é usado para
    /// calcular o segundo.
    ///
    /// @param cnpjBase Os primeiros 12 dígitos do CNPJ (sem os dígitos verificadores).
    /// @return Uma string representando os dois dígitos verificadores calculados.
    private static String calcularDigitosVerificadores(String cnpjBase) {
        int primeiroDigito, segundoDigito;

        // Calcula o primeiro dígito verificador usando os 12 primeiros dígitos do CNPJ.
        primeiroDigito = calcularDigitoVerificador(cnpjBase, new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});

        // Adiciona o primeiro dígito ao CNPJ base para calcular o segundo dígito.
        cnpjBase += primeiroDigito;

        // Calcula o segundo dígito verificador usando os 13 primeiros dígitos (base + primeiro dígito).
        segundoDigito = calcularDigitoVerificador(cnpjBase, new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});

        // Retorna os dois dígitos verificadores como uma string.
        return "" + primeiroDigito + segundoDigito;
    }

    /// Metodo para calcular um único dígito verificador conforme os pesos fornecidos.
    ///
    /// Este metodo aplica os pesos definidos pela Receita Federal a cada dígito do CNPJ base,
    /// realiza a soma ponderada, e calcula o resto da divisão por 11. O resultado é ajustado
    /// conforme a regra dos dígitos verificadores (se o resto for menor que 2, o dígito é 0,
    /// caso contrário, o dígito é 11 menos o resto).
    ///
    /// @param cnpjBase Os dígitos do CNPJ aos quais os pesos serão aplicados.
    /// @param pesos Um array de inteiros representando os pesos para cada posição dos dígitos.
    /// @return O dígito verificador calculado.
    private static int calcularDigitoVerificador(String cnpjBase, int[] pesos) {
        int soma, resto;

        soma = 0;

        // Para cada dígito no CNPJ base, multiplica pelo peso correspondente e acumula a soma.
        for (int i = 0; i < cnpjBase.length(); i++)
            soma += getNumericValue(cnpjBase.charAt(i)) * pesos[i];

        // Calcula o resto da divisão da soma por 11.
        resto = soma % 11;

        // Se o resto for menor que 2, retorna 0. Caso contrário, retorna 11 menos o resto.
        return (resto < 2) ? 0 : 11 - resto;
    }
}
