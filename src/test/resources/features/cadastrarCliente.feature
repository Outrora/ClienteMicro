# language: pt
  Funcionalidade: Cadatrar Cliente

    Esquema do Cenario: Cadastrar um Cliente Com Sucesso
      Dado que prenchi o cpf com <cpf>
      Dado que prenchi o nome com <nome>
      Dado que prenchi o email com <email>
      Quando envio os dados do formulario
      Entao o cliente deve retornar com sucesso quando busco o cpf <cpf>
      Exemplos:
        | cpf             | nome  | email        |
        | "325.118.050-90" | "paulo" | "paulo@gmail " |
        | "118.827.600-00" | "paulo" | "paulo2@gmail"  |
        | "09581515046"  | "paulo" | "paulo3@gmail"  |


