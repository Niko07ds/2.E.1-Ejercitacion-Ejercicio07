package ejercicio07;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        // Obtenemos la instancia única de la cola de impresión
        PrintQueue queue = PrintQueue.getInstance();

        System.out.println("¿Es la misma instancia? "
                + (queue == PrintQueue.getInstance()));

        System.out.println("\n--- Envío concurrente de documentos ---");

        // Simulamos tres terminales enviando documentos al mismo tiempo
        List<Thread> terminals = new ArrayList<>();

        for (String author : new String[]{"Ana", "Bruno", "Carla"}) {
            Thread terminal = new Thread(() -> {
                for (int i = 1; i <= 3; i++) {
                    PrintQueue.getInstance().enqueue(
                            new PrintJob("informe_" + i + ".pdf", author)
                    );
                }
            });

            terminals.add(terminal);
            terminal.start();
        }

        // Esperamos a que todas las terminales terminen de enviar
        for (Thread terminal : terminals) {
            terminal.join();
        }

        System.out.println("\nTrabajos en espera: " + queue.size());

        System.out.println("\n--- Impresión de documentos ---");

        // Procesamos los documentos hasta que la cola quede vacía
        while (queue.processNext()) {
            // Continúa mientras haya documentos pendientes
        }

        System.out.println("\n--- Estado final ---");
        System.out.println("¿Cola vacía? " + queue.isEmpty());
        System.out.println("Trabajos pendientes: " + queue.size());
    }
}