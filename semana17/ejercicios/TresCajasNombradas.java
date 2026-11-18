package semana17.ejercicios;

public class TresCajasNombradas {
    public static void main(String[] args) {
        for (int i = 1; i <= 3; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    System.out.println(Thread.currentThread().getName());
                }
            }, "caja-" + i);
            t.start();
        }
    }
}
