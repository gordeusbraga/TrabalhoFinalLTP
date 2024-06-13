
import java.io.*;

public class Carro {

	public static String marcas[] = { "TOYOTA", "HONDA", "VOLKSWAGEN", "CHEVROLET", "FIAT", "HYUNDAI", "BWM",
			"MERCEDES BENS", "RENAULT", "JEEP" };
	public static String origens[] = { "JAPÃO", "JAPÃO", "ALEMANHA", "EUA", "ITÁLIA", "COREIA DO SUL", "ALEMANHA",
			"ALEMANHA", "FRANÇA", "EUA" };
	char ativo;
	String codCarro;
	String marca;
	String modelo;
	char fabricacao;
	String origemMarca;
	String categoria;
	float motorizacao;
	int potencia;
	float peso;
	float preco;
	String mesAnoFab;

	public long pesquisarCarro(String _codCarro) {
		// metodo para localizar um registro no arquivo em disco
		long posicaoCursorArquivo = 0;
		try {
			RandomAccessFile arquivoCarro = new RandomAccessFile("CARROS.DAT", "rw");
			while (true) {
				posicaoCursorArquivo = arquivoCarro.getFilePointer(); // posicao do inicio do registro no arquivo
				ativo = arquivoCarro.readChar();
				codCarro = arquivoCarro.readUTF();
				marca = arquivoCarro.readUTF();
				modelo = arquivoCarro.readUTF();
				fabricacao = arquivoCarro.readChar();
				origemMarca = arquivoCarro.readUTF();
				categoria = arquivoCarro.readUTF();
				motorizacao = arquivoCarro.readFloat();
				potencia = arquivoCarro.readInt();
				peso = arquivoCarro.readFloat();
				preco = arquivoCarro.readFloat();
				mesAnoFab = arquivoCarro.readUTF();

				if (_codCarro.equals(codCarro) && ativo == 'S') {
					arquivoCarro.close();
					return posicaoCursorArquivo;
				}
			}
		} catch (EOFException e) {
			return -1; // registro nao foi encontrado
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
			return -1;
		}
	}

	public void salvarCarro() {
		// metodo para incluir um novo registro no final do arquivo em disco
		try {
			RandomAccessFile arquivoCarro = new RandomAccessFile("CARROS.DAT", "rw");
			arquivoCarro.seek(arquivoCarro.length()); // posiciona o ponteiro no final do arquivo (EOF)
			arquivoCarro.writeChar(ativo);
			arquivoCarro.writeUTF(codCarro);
			arquivoCarro.writeUTF(marca);
			arquivoCarro.writeUTF(modelo);
			arquivoCarro.writeFloat(fabricacao);
			arquivoCarro.writeUTF(origemMarca);
			arquivoCarro.writeUTF(categoria);
			arquivoCarro.writeFloat(motorizacao);
			arquivoCarro.writeInt(potencia);
			arquivoCarro.writeFloat(peso);
			arquivoCarro.writeFloat(preco);
			arquivoCarro.writeUTF(mesAnoFab);
			arquivoCarro.close();
			System.out.println("Dados gravados com sucesso !\n");
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
		}
	}

	public void desativarCarro(long posicao) {
		// metodo para alterar o valor do campo ATIVO para N, tornando assim o registro
		// excluido
		try {
			RandomAccessFile arquivoCarro = new RandomAccessFile("CARROS.DAT", "rw");
			arquivoCarro.seek(posicao);
			arquivoCarro.writeChar('N'); // desativar o registro antigo
			arquivoCarro.close();
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
		}
	}

	public void incluir() {
		String codCarroChave;
		char confirmacao;
		long posicaoRegistro;

		do {
			do {
				Main.leia.nextLine();
				System.out.println("\n ***************  INCLUSAO DE CARRO ***************** ");
				System.out.print("Digite o codigo do carro.( FIM para encerrar): ");
				codCarroChave = Main.leia.nextLine();
				if (codCarroChave.equals("FIM")) {
					break;
				}
				posicaoRegistro = pesquisarCarro(codCarroChave);

				if (posicaoRegistro >= 0) {
					System.out.println("Codigo do carro ja cadastrado, digite outro valor\n");
				}
			} while (posicaoRegistro >= 0);

			if (codCarroChave.equals("FIM")) {
				break;
			}

			ativo = 'S';
			codCarro = codCarroChave;
			System.out.print("Digite a marca do carro:.........................: ");
			marca = Main.leia.nextLine();
			System.out.print("Digite o modelo do carro:.......: ");
			modelo = Main.leia.nextLine();
			System.out.print("Digite a fabricação do carro: (N - Nacional ou I - Importado)..................: ");
			fabricacao = Main.leia.next().charAt(0);
			Main.leia.nextLine();
			System.out
					.print("Digite a categoria do carro: (HATCH, SEDÃ, SUV, PICAPE ou ESPORTIVO)...................: ");
			categoria = Main.leia.nextLine();
			System.out.print("Digite a motorização do carro:..................: ");
			motorizacao = Main.leia.nextFloat();
			System.out.print("Digite a potencia do carro:..................: ");
			potencia = Main.leia.nextInt();
			System.out.print("Digite o peso do carro:..................: ");
			peso = Main.leia.nextFloat();
			System.out.print("Digite o preco do carro:..................: ");
			preco = Main.leia.nextFloat();
			Main.leia.nextLine();
			System.out.print("Digite o mes e ano de fabricação do carro:..................: ");
			mesAnoFab = Main.leia.nextLine();

			do {
				System.out.print("\nConfirma a gravacao dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					salvarCarro();
				}
			} while (confirmacao != 'S' && confirmacao != 'N');

		} while (!codCarro.equals("FIM"));
	}

	public void alterarCarro() {
		String codCarroChave;
		char confirmacao;
		long posicaoRegistro = 0;
		byte opcao;

		do {
			do {
				Main.leia.nextLine();
				System.out.println("\n ************  ALTERACAO DO CARRO  ************** ");
				System.out.print("Digite o codigo do carro que deseja alterar( FIM para encerrar ): ");
				codCarroChave = Main.leia.nextLine();
				if (codCarroChave.equals("FIM")) {
					break;
				}

				posicaoRegistro = pesquisarCarro(codCarroChave);
				if (posicaoRegistro == -1) {
					System.out.println("Carro nao cadastrado no arquivo, digite outro valor\n");
				}
			} while (posicaoRegistro == -1);

			if (codCarroChave.equals("FIM")) {
				break;
			}

			ativo = 'S';

			do {
				System.out.println("[ 1 ] Marca..........................: " + marca);
				System.out.println("[ 2 ] Modelo ........................: " + modelo);
				System.out.println("[ 3 ] Fabricacao.....................: " + fabricacao);
				System.out.println("[ 4 ] Categoria......................: " + categoria);
				System.out.println("[ 5 ] Motorizacao....................: " + motorizacao);
				System.out.println("[ 6 ] Potencia.......................: " + potencia);
				System.out.println("[ 7 ] Peso...........................: " + peso);
				System.out.println("[ 8 ] Preco..........................: " + preco);
				System.out.println("[ 9 ] Mes e Ano de Fabricacao........: " + mesAnoFab);

				do {
					System.out
							.println("Digite o numero do campo que deseja alterar (0 para finalizar as alterações): ");
					opcao = Main.leia.nextByte();
				} while (opcao < 0 || opcao > 9);

				switch (opcao) {
					case 1:
						Main.leia.nextLine();
						System.out.print("Digite o NOVA MARCA do carro..................: ");
						marca = Main.leia.nextLine();
						break;
					case 2:
						Main.leia.nextLine();
						System.out.print("Digite a NOVO MODELO do carro: ");
						modelo = Main.leia.nextLine();
						break;
					case 3:
						System.out.print("Digite a NOVA FABRICACAO do carro...........: ");
						fabricacao = Main.leia.next().charAt(0);
						break;
					case 4:
						System.out.print("Digite a NOVA CATEGORIA do carro............: ");
						categoria = Main.leia.nextLine();
						break;
					case 5:
						System.out.print("Digite a NOVA MOTORIZACAO do carro..........: ");
						motorizacao = Main.leia.nextFloat();
						break;
					case 6:
						System.out.print("Digite a NOVA POTENCIA do carro.............: ");
						potencia = Main.leia.nextInt();
						break;
					case 7:
						System.out.print("Digite o NOVO PESO do carro.................: ");
						peso = Main.leia.nextFloat();
						break;
					case 8:
						System.out.print("Digite o NOVO PRECO do carro................: ");
						preco = Main.leia.nextFloat();
						break;
					case 9:
						Main.leia.nextLine();
						System.out.print("Digite o NOVO MES E ANO DE FABRICACAO do carro: ");
						mesAnoFab = Main.leia.nextLine();
						break;
				}
				System.out.println();
			} while (opcao != 0);

			do {
				System.out.print("\nConfirma a alteracao dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					desativarCarro(posicaoRegistro);
					salvarCarro();
					System.out.println("Dados gravados com sucesso !\n");
				}
			} while (confirmacao != 'S' && confirmacao != 'N');

		} while (!codCarro.equals("FIM"));
	}

	public void excluirCarro() {
		String codCarroChave;
		char confirmacao;
		long posicaoRegistro = 0;

		do {
			do {
				Main.leia.nextLine();
				System.out.println(" ***************  EXCLUSAO DE CARRO  ***************** ");
				System.out.print("Digite o carro que deseja excluir ( FIM para encerrar ): ");
				codCarroChave = Main.leia.nextLine();
				if (codCarroChave.equals("FIM")) {
					break;
				}

				posicaoRegistro = pesquisarCarro(codCarroChave);
				if (posicaoRegistro == -1) {
					System.out.println("Codigo do carro nao cadastrada no arquivo, digite outro valor\n");
				}
			} while (posicaoRegistro == -1);

			if (codCarroChave.equals("FIM")) {
				System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
				break;
			}

			System.out.println("\nDados do carro a ser excluido: ");
			System.out.println("Codigo do carro: " + codCarro);
			System.out.println("Marca do carro: " + marca);
			System.out.println("Modelo do carro: " + modelo);
			System.out.println("Fabricacao do carro: " + fabricacao);
			System.out.println("Categoria do carro: " + categoria);
			System.out.println("Motorizacao do carro: " + motorizacao);
			System.out.println("Potencia do carro: " + potencia);
			System.out.println("Peso do carro: " + peso);
			System.out.println("Preco do carro: " + preco);
			System.out.println("Mes e Ano de Fabricacao do carro: " + mesAnoFab);
			System.out.println();

			do {
				System.out.print("\nConfirma a exclusao deste aluno (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					desativarCarro(posicaoRegistro);
				}
			} while (confirmacao != 'S' && confirmacao != 'N');

		} while (!codCarro.equals("FIM"));
	}

	public void consultar() {
		RandomAccessFile arquivoCarro;
		byte opcao;
		String codCarroChave;
		String anoFabricacaoAux;
		String marcaAux;
		float precoMin, precoMax;
		long posicaoRegistro;
	
		do {
			do {
				System.out.println(" ***************  CONSULTA DE CARROS  ***************** ");
				System.out.println("[1] - Listar todos os carros de uma marca informada.");
				System.out.println("[2] - Listar todos os carros de um ano de fabricação informado.");
				System.out.println("[3] - Listar todos os carros de uma faixa de preço informada.");
				System.out.println("[4] - Listar todos os carros.");
				System.out.println("[0] PARA FINALIZAR.\n");
				opcao = Main.leia.nextByte();
				Main.leia.nextLine(); // Consome a nova linha deixada por nextByte
				if (opcao < 0 || opcao > 4) {
					System.out.println("Opção Inválida, digite novamente.\n");
				}
			} while (opcao < 0 || opcao > 4);
	
			switch (opcao) {
				case 0:
					System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
					break;
				case 1:
					System.out.print("Digite a marca desejada: ");
					marcaAux = Main.leia.nextLine();
					try {
						arquivoCarro = new RandomAccessFile("CARROS.DAT", "rw");
						imprimirCabecalho();
						boolean encontrado = false;
						while (true) {
							ativo = arquivoCarro.readChar();
							codCarro = arquivoCarro.readUTF();
							marca = arquivoCarro.readUTF();
							modelo = arquivoCarro.readUTF();
							fabricacao = arquivoCarro.readChar();
							origemMarca = arquivoCarro.readUTF();
							categoria = arquivoCarro.readUTF();
							motorizacao = arquivoCarro.readFloat();
							potencia = arquivoCarro.readInt();
							peso = arquivoCarro.readFloat();
							preco = arquivoCarro.readFloat();
							mesAnoFab = arquivoCarro.readUTF();
	
							if (marcaAux.equals(marca) && ativo == 'S') {
								imprimirCarro();
								encontrado = true;
							}
						}
					} catch (EOFException e) {
						System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
						Main.leia.nextLine();
					} catch (IOException e) {
						System.out.println("Erro na abertura do arquivo - programa será finalizado");
						System.exit(0);
					}
					break;
	
				case 2:
					System.out.print("Digite o ano de fabricação: ");
					anoFabricacaoAux = Main.leia.nextLine();
					try {
						arquivoCarro = new RandomAccessFile("CARROS.DAT", "rw");
						imprimirCabecalho();
						boolean encontrado = false;
						while (true) {
							ativo = arquivoCarro.readChar();
							codCarro = arquivoCarro.readUTF();
							marca = arquivoCarro.readUTF();
							modelo = arquivoCarro.readUTF();
							fabricacao = arquivoCarro.readChar();
							origemMarca = arquivoCarro.readUTF();
							categoria = arquivoCarro.readUTF();
							motorizacao = arquivoCarro.readFloat();
							potencia = arquivoCarro.readInt();
							peso = arquivoCarro.readFloat();
							preco = arquivoCarro.readFloat();
							mesAnoFab = arquivoCarro.readUTF();
	
							if (anoFabricacaoAux.equals(mesAnoFab.substring(mesAnoFab.length() - 4)) && ativo == 'S') {
								imprimirCarro();
								encontrado = true;
							}
						}
					} catch (EOFException e) {
						System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
						Main.leia.nextLine();
					} catch (IOException e) {
						System.out.println("Erro na abertura do arquivo - programa será finalizado");
						System.exit(0);
					}
					break;
	
				case 3:
					System.out.println("Digite a faixa de preço desejada: ");
					System.out.print("Preço mínimo: ");
					precoMin = Main.leia.nextFloat();
					System.out.print("Preço máximo: ");
					precoMax = Main.leia.nextFloat();
					Main.leia.nextLine(); // Consome a nova linha deixada por nextFloat
	
					try {
						arquivoCarro = new RandomAccessFile("CARROS.DAT", "rw");
						imprimirCabecalho();
						boolean encontrado = false;
						while (true) {
							ativo = arquivoCarro.readChar();
							codCarro = arquivoCarro.readUTF();
							marca = arquivoCarro.readUTF();
							modelo = arquivoCarro.readUTF();
							fabricacao = arquivoCarro.readChar();
							origemMarca = arquivoCarro.readUTF();
							categoria = arquivoCarro.readUTF();
							motorizacao = arquivoCarro.readFloat();
							potencia = arquivoCarro.readInt();
							peso = arquivoCarro.readFloat();
							preco = arquivoCarro.readFloat();
							mesAnoFab = arquivoCarro.readUTF();
	
							if (preco >= precoMin && preco <= precoMax && ativo == 'S') {
								imprimirCarro();
								encontrado = true;
							}
						}
					} catch (EOFException e) {
						System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
						Main.leia.nextLine();
					} catch (IOException e) {
						System.out.println("Erro na abertura do arquivo - programa será finalizado");
						System.exit(0);
					}
					break;
	
				case 4:
					try {
						arquivoCarro = new RandomAccessFile("CARROS.DAT", "rw");
						imprimirCabecalho();
						while (true) {
							ativo = arquivoCarro.readChar();
							codCarro = arquivoCarro.readUTF();
							marca = arquivoCarro.readUTF();
							modelo = arquivoCarro.readUTF();
							fabricacao = arquivoCarro.readChar();
							origemMarca = arquivoCarro.readUTF();
							categoria = arquivoCarro.readUTF();
							motorizacao = arquivoCarro.readFloat();
							potencia = arquivoCarro.readInt();
							peso = arquivoCarro.readFloat();
							preco = arquivoCarro.readFloat();
							mesAnoFab = arquivoCarro.readUTF();
							if (ativo == 'S') {
								imprimirCarro();
							}
						}
					} catch (EOFException e) {
						System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
						Main.leia.nextLine();
					} catch (IOException e) {
						System.out.println("Erro na abertura do arquivo - programa será finalizado");
						System.exit(0);
					}
					break;
			}
		} while (opcao != 0);
	}

	public void imprimirCabecalho() {
		System.out.println(
				"-CodCarro  --------  MARCA  --------  MODELO  --------  ORIGEM  --------  CATEGORIA  --------  MOTOR  --------  POTENCIA  --------  PESO  --------  PRECO  --------  MES/ANO FAB");
	}

	public void imprimirCarro() {
		System.out.println(formatarString(codCarro, 6) + "  " +
				formatarString(marca, marca.length()) + "  " +
				formatarString(modelo, modelo.length()) + "  " +
				formatarString(Character.toString(fabricacao), Character.toString(fabricacao).length()) + "  " +
				formatarString(origemMarca, origemMarca.length()) + "  " +
				formatarString(categoria, categoria.length()) + "  " +
				formatarString(String.valueOf(motorizacao), String.valueOf(motorizacao).length()) + "  " +
				formatarString(String.valueOf(potencia), String.valueOf(potencia).length()) + "  " +
				formatarString(String.valueOf(peso), String.valueOf(peso).length()) + "  " +
				formatarString(String.valueOf(preco), String.valueOf(preco).length()) + "  " +
				formatarString(mesAnoFab, 7));
	}

	public static String formatarString(String texto, int tamanho) {
		// retorna uma string com o numero de caracteres passado como parametro em
		// TAMANHO
		if (texto.length() > tamanho) {
			texto = texto.substring(0, tamanho);
		} else {
			while (texto.length() < tamanho) {
				texto = texto + " ";
			}
		}
		return texto;
	}

}
