import static java.lang.Character.getNumericValue;

public class CNPJValidator {

    /*
    1) Validate CNPJ format and check digits
    Given a string, check if it looks like a CNPJ, considering two formats:
    Formatted: "00.000.000/0000-00"

    Number only: "00000000000000"

    Validate if it's a well-formed CNPJ, considering the "check digits", as defined by Receita Federal.
    Important: Don't use a library. You should write the validation code.

    fonte: https://www.macoratti.net/alg_cnpj.htm
     */

    // Metodo principal de validação do CNPJ
    public static boolean validarCNPJ(String cnpj) {
        String cnpjNumeros, cnpjBase, digitoVerificadorCalculado, digitoVerificadorOriginal;

        // Remove todos os caracteres não numéricos
        cnpjNumeros = cnpj.replaceAll("\\D", "");

        // Verifica se o CNPJ tem exatamente 14 dígitos
        if (cnpjNumeros.length() != 14) {
            System.err.println("CNPJ com tamanho incorreto: " + cnpjNumeros.length());
            return false;
        }

        // Verifica se todos os dígitos são iguais (CNPJ inválido)
        if (cnpjNumeros.chars().distinct().count() == 1) {
            System.err.println("CNPJ com todos os dígitos iguais.");
            return false;
        }

        // Calcula e compara os dois dígitos verificadores
        cnpjBase = cnpjNumeros.substring(0, 12);
        digitoVerificadorCalculado = calcularDigitosVerificadores(cnpjBase);
        digitoVerificadorOriginal = cnpjNumeros.substring(12, 14);

        // Verifica se os dígitos calculados coincidem com os fornecidos
        return digitoVerificadorCalculado.equals(digitoVerificadorOriginal);
    }

    // Metodo para calcular os dois dígitos verificadores
    private static String calcularDigitosVerificadores(String cnpjBase) {
        int primeiroDigito, segundoDigito;

        // Primeiro dígito verificador
        primeiroDigito = calcularDigitoVerificador(cnpjBase, new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});

        // Concatena o primeiro dígito ao CNPJ base para calcular o segundo dígito
        cnpjBase += primeiroDigito;

        // Segundo dígito verificador
        segundoDigito = calcularDigitoVerificador(cnpjBase, new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});

        return "" + primeiroDigito + segundoDigito;
    }

    // Metodo para calcular um dígito verificador
    private static int calcularDigitoVerificador(String cnpjBase, int[] pesos) {
        int soma, resto;

        soma = 0;

        for (int i = 0; i < cnpjBase.length(); i++)
            soma += getNumericValue(cnpjBase.charAt(i)) * pesos[i];

        resto = soma % 11;

        return (resto < 2) ? 0 : 11 - resto;
    }
}
