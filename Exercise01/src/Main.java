import static java.util.Arrays.asList;

public static void main(String[] ignoredArgs) {

    // Lista de CNPJs formatados e não formatados
    List<String> cnpjs = asList(
            "12.345.678/0001-95",
            "12345678000195",
            "48.724.911/0001-99",
            "18.781.203/0001-28",
            "61.611.250/0001-52",
            "57720021000107",
            "19.583.866/0001-09",
            "14779454000117",
            "53.597.206/0001-07",
            "30.843.829/0001-17",
            "68965122000156",
            "66.231.013/0001-80"
    );

    String cnpj, resultado;
    boolean isValid;

    // Itera sobre a lista de CNPJs e valida cada um
    for (int i = 0; i < cnpjs.size(); i++) {
        cnpj = cnpjs.get(i);
        isValid = CNPJValidator.validarCNPJ(cnpj);
        resultado = isValid ? "válido" : "inválido";

        // Definir o tamanho fixo do CNPJ, assumindo no máximo 18 caracteres (caso seja com pontuação)
        System.out.printf("CNPJ %2d (%-18s) é %s%n", (i + 1), cnpj, resultado);
    }
}