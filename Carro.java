
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
				ativo       = arquivoCarro.readChar();
				codCarro    = arquivoCarro.readUTF();
				marca       = arquivoCarro.readUTF();
				modelo      = arquivoCarro.readUTF();
				fabricacao  = arquivoCarro.readChar();
				origemMarca = arquivoCarro.readUTF();
				categoria   = arquivoCarro.readUTF();
				motorizacao = arquivoCarro.readFloat();
				potencia    = arquivoCarro.readInt();
				peso        = arquivoCarro.readFloat();
				preco       = arquivoCarro.readFloat();
				mesAnoFab   = arquivoCarro.readUTF();

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
			arquivoCarro.writeChar(fabricacao);
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
	/// INCLUIR CARRO
	public void incluir() {
		String codCarroChave;
		char confirmacao;
		long posicaoRegistro;
		int numCodAux;
		boolean valido = false;
		String letrasAux;
		do {
			do {
				Main.leia.nextLine();
				System.out.println("\n ***************  INCLUSAO DE CARRO ***************** ");
				do {

					System.out.print("Digite o codigo do carro.( FIM para encerrar): ");
					codCarroChave = Main.leia.nextLine();
					try {
						valido = true;
						numCodAux = Integer.parseInt(codCarroChave.substring(3));
					}catch(NumberFormatException e){
						valido = false;
						System.out.println("Formato Invalido, tente novamente.");
					}
					letrasAux = codCarroChave.substring(0,3);
					valido = letrasAux.matches("[a-zA-Z]+");
					if(!valido) {
						System.out.println("Formato Invalido, tente novamente.");
					}

				}while(!valido);
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

			do {
				System.out.print("Digite a marca do carro:.........................: ");
				marca = Main.leia.nextLine();
				consistirMarca(marca);
				if(consistirMarca(marca).equals("ERRO")){
					System.out.println("Digite novamente, marca invalida");
				}else {
					origemMarca = consistirMarca(marca);
				}
			}while(consistirMarca(marca).equals("ERRO"));
			do {
				System.out.print("Digite o modelo do carro:.......: ");
				modelo = Main.leia.nextLine();
				if(modelo.length() < 5) {
					System.out.println("O tamanho deve ser maior que 5 caracters.");
				}
			}while(modelo.length() < 5);
			do {
				System.out.print("Digite a fabricação do carro: (N - Nacional ou I - Importado)..................: ");
				fabricacao = Main.leia.next().charAt(0);
				if(fabricacao != 'N' && fabricacao != 'I') {
					System.out.println("E aceito somente N ou I. Digite novamente");
				}
			}while(fabricacao != 'N' && fabricacao != 'I');
			Main.leia.nextLine();
			do {
				System.out.print("Digite a categoria do carro: (HATCH, SEDÃ, SUV, PICAPE ou ESPORTIVO)...................: ");
				categoria = Main.leia.nextLine();
				if(!categoria.equals("HATCH") && !categoria.equals("SEDÃ")&& !categoria.equals("SUV")
						&& !categoria.equals("PICAPE") && !categoria.equals("ESPORTIVO")){
					System.out.println("Digite novamente, categorias nao permitidas.");
				}
			}while(!categoria.equals("HATCH") && !categoria.equals("SEDÃ")&& !categoria.equals("SUV")
					&& !categoria.equals("PICAPE") && !categoria.equals("ESPORTIVO"));
			do {
				System.out.print("Digite a motorização do carro:..................: ");
				motorizacao = Main.leia.nextFloat();
				if(motorizacao < 1.0 || motorizacao > 5.0) {
					System.out.println("O valor deve ser entre 1.0 e 5.0, digite novamente.");
				}
			}while(motorizacao < 1.0 || motorizacao > 5.0);
			do {
				System.out.print("Digite a potencia do carro:..................: ");
				potencia = Main.leia.nextInt();
				if(potencia <= 0) {
					System.out.println("O valor deve ser acima de 0.");
				}
			}while(potencia <= 0);
			do {
				System.out.print("Digite o peso do carro:..................: ");
				peso = Main.leia.nextFloat();
				if(peso < 500) {
					System.out.println("O valor minimo e 500KG, digite novamente.");
				}
			}while(peso < 500);
			do {
				System.out.print("Digite o preco do carro:..................: ");
				preco = Main.leia.nextFloat();
				if(preco <= 10000.00) {
					System.out.println("O preco deve ser acima de 10000.00");
				}
			}while(preco <= 10000.00);
			Main.leia.nextLine(); // Consome a nova linha deixada por nextFloat
			do {
				System.out.print("Digite o mes e ano de fabricação do carro:..................: ");
				mesAnoFab = Main.leia.nextLine();
				if(!consistirMesAnoFab(mesAnoFab)) {
					System.out.println("Data invalida, tente novamente");
				}
			}while(!consistirMesAnoFab(mesAnoFab));

			do {
				System.out.print("\nConfirma a gravacao dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					salvarCarro();
				}
			} while (confirmacao != 'S' && confirmacao != 'N');

		} while (!codCarro.equals("FIM"));
	}
	/// ALTERAR CARRO
	public void alterarCarro() {
		String codCarroChave;
		char confirmacao;
		long posicaoRegistro = 0;
		byte opcao;
		boolean valido;
		int numCodAux;
		String letrasAux;

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
					System.out.print("Carro nao cadastrado no arquivo, digite outro valor: ");
				}
			} while (posicaoRegistro == -1);

			if (codCarroChave.equals("FIM")) {
				break;
			}

			ativo = 'S';

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


				do {

					System.out.println("Digite o numero do campo que deseja alterar (0 para finalizar as alterações): ");
					opcao = Main.leia.nextByte();
				} while (opcao < 0 || opcao > 9);

				Main.leia.nextLine();
				switch (opcao) {
				case 1:
					Main.leia.nextLine();

					do {
						System.out.print("Digite a NOVA MARCA do carro:.........................: ");
						marca = Main.leia.nextLine();
						consistirMarca(marca);
						if(consistirMarca(marca).equals("ERRO")){
							System.out.println("Digite novamente, marca invalida");
						}else {
							origemMarca = consistirMarca(marca);
						}
					}while(consistirMarca(marca).equals("ERRO"));
					break;
				case 2:
					Main.leia.nextLine();
					do {
						System.out.print("Digite o NOVO MODELO do carro:.......: ");
						modelo = Main.leia.nextLine();
						if(modelo.length() < 5) {
							System.out.println("O tamanho deve ser maior que 5 caracters.");
						}
					}while(modelo.length() < 5);
					break;
				case 3:
					do {
						System.out.print("Digite a NOVA FABRICACAO do carro: (N - Nacional ou I - Importado)..................: ");
						fabricacao = Main.leia.next().charAt(0);
						if(fabricacao != 'N' && fabricacao != 'I') {
							System.out.println("E aceito somente N ou I. Digite novamente");
						}
					}while(fabricacao != 'N' && fabricacao != 'I');
					break;
				case 4:
					do {
						System.out.print("Digite NOVA CATEGORIA do carro: (HATCH, SEDÃ, SUV, PICAPE ou ESPORTIVO)...................: ");
						categoria = Main.leia.nextLine();
						if(!categoria.equals("HATCH") && !categoria.equals("SEDÃ")&& !categoria.equals("SUV")
								&& !categoria.equals("PICAPE") && !categoria.equals("ESPORTIVO")){
							System.out.println("Digite novamente, categorias nao permitidas.");
						}
					}while(!categoria.equals("HATCH") && !categoria.equals("SEDÃ")&& !categoria.equals("SUV")
							&& !categoria.equals("PICAPE") && !categoria.equals("ESPORTIVO"));
					break;
				case 5:
					do {
						System.out.print("Digite a NOVA MOTORIZACAO do carro:..................: ");
						motorizacao = Main.leia.nextFloat();
						if(motorizacao < 1.0 || motorizacao > 5.0) {
							System.out.println("O valor deve ser entre 1.0 e 5.0, digite novamente.");
						}
					}while(motorizacao < 1.0 || motorizacao > 5.0);
					break;
				case 6:
					do {
						System.out.print("Digite a NOVA POTENCIA do carro:..................: ");
						potencia = Main.leia.nextInt();
						if(potencia <= 0) {
							System.out.println("O valor deve ser acima de 0.");
						}
					}while(potencia <= 0);
					break;
				case 7:
					do {
						System.out.print("Digite o NOVO PESO do carro:..................: ");
						peso = Main.leia.nextFloat();
						if(peso < 500) {
							System.out.println("O valor minimo e 500KG, digite novamente.");
						}
					}while(peso < 500);
					break;
				case 8:
					do {
						System.out.print("Digite o NOVO PRECO do carro:..................: ");
						preco = Main.leia.nextFloat();
						if(preco <= 10000.00) {
							System.out.println("O preco deve ser acima de 10000.00");
						}
					}while(preco <= 10000.00);
					break;
				case 9:
					Main.leia.nextLine();
					do {
						System.out.print("Digite o NOVO MES E ANO de fabricação do carro:..................: ");
						mesAnoFab = Main.leia.nextLine();
						if(!consistirMesAnoFab(mesAnoFab)) {
							System.out.println("Data invalida, tente novamente");
						}
					}while(!consistirMesAnoFab(mesAnoFab));
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
		boolean valido;
		int numCodAux;
		String letrasAux;

		do {
			do {
				Main.leia.nextLine();
				System.out.println(" ***************  EXCLUSAO DE CARRO  ***************** ");
				do {

					System.out.print("Digite o codigo do carro que deseja excluir ( FIM para encerrar ): ");
					codCarroChave = Main.leia.nextLine();
					try {
						valido = true;
						numCodAux = Integer.parseInt(codCarroChave.substring(3));
					}catch(NumberFormatException e){
						valido = false;
						System.out.println("Formato Invalido, tente novamente.");
					}
					letrasAux = codCarroChave.substring(0,3);
					valido = letrasAux.matches("[a-zA-Z]+");
					if(!valido) {
						System.out.println("Formato Invalido, tente novamente.");
					}

				}while(!valido);
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
				anoFabricacaoAux = Main.leia.next();
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
				"-ATIVO  --------  CodCarro  --------  MARCA  --------  MODELO  -------- FABRICACAO  --------  ORIGEM  --------  CATEGORIA  --------  MOTORIZACAO  --------  POTENCIA  --------  PESO  --------  PRECO  --------  MES/ANO FAB");
	}

	public void imprimirCarro() {
		System.out.println( "-  "+ formatarString(String.valueOf(ativo), 13)+"   "+formatarString(codCarro, 13) +"      "+
				formatarString(marca, 13) +"    "+
				formatarString(modelo, 13) +"        "+
				formatarString( String.valueOf(fabricacao), 13) +"      "+
				formatarString(origemMarca, 13) +"      "+
				formatarString(categoria, 13) +"         "+
				formatarString(String.valueOf(motorizacao), 13) +"          "+
				formatarString(String.valueOf(potencia),13) +"     "+
				formatarString(String.valueOf(peso), 13) +"   "+
				formatarString(String.valueOf(preco), 13) +"      "+
				formatarString(mesAnoFab, 13));

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
	public static String consistirMarca(String marca) {

		String origemPais = " ";
		String erro = "ERRO";
		int salvarPosicao = 0;
		for(int i = 0; i < 10; i++ ) {
			marca.equals(marcas[i]);
			if(marca.equals(marcas[i])) {
				salvarPosicao = i;
			}
		}
		if(marca.equals(marcas[salvarPosicao])) {
			origemPais =  origens[salvarPosicao];
			return origemPais;
		}else {
			return erro;
		}

	}
	public static boolean consistirMesAnoFab(String mesAnoFab) {
		 boolean valido;
	        
	        
	        if (mesAnoFab == null || mesAnoFab.length() != 7) {
	            return false;
	        }
	        
	         
	        if (!mesAnoFab.substring(2, 3).equals("/")) {
	            return false;
	        }

	        
	            int mes = Integer.parseInt(mesAnoFab.substring(0, 2));
	            int ano = Integer.parseInt(mesAnoFab.substring(3));

	            if (mes < 1 || mes > 12) {
	                valido = false;
	            } else if (ano < 1980 || ano > 2024) {
	                valido = false;
	            } else {
	                valido = true;
	            }
	        
	        return valido;
	    }
	}




