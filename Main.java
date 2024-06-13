import java.util.*;
public class Main {
    static Scanner leia = new Scanner(System.in);
   public static void main(String[] args) {
       
    Carro carro = new Carro();
    byte opcao = -1;


    do{
        do{
            System.out.println("****Relatorio em tela****\n");
            System.out.println("[1] - INCLUSÃO DE CARRO.");
            System.out.println("[2] -  ALTERAÇÃO DE CARRO.");
            System.out.println("[3] - EXCLUSÃO DE CARRO.");
            System.out.println("[4] - RELATÓRIO EM TELA.");
            System.out.println("[0] PARA FINALIZAR.\n");
            System.out.print("Digite a opção desejada: ");
            opcao = leia.nextByte();
            if(opcao < 0 || opcao > 4 ){
                System.out.println("Opção invalida, digite novamente.\n");
            }

        }while(opcao < 0 || opcao > 4);

        switch (opcao) {
            case 1:
              carro.incluir();
                break;
            case 2:
            carro.alterarCarro();
                break;
            case 3:
            carro.excluirCarro();
                break;
            case 4:
             carro.consultar();
                break;
            default:
                    break;
            }

        
    }while(opcao != 0);
    leia.close();
   }
}
