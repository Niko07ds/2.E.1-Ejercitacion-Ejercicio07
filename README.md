# Ejercicio 7: Cola de Impresión Centralizada

## Descripción

Este ejercicio implementa el patrón de diseño **Singleton** en Java para administrar una cola de impresión compartida por múltiples terminales de una oficina.

La clase `PrintQueue` centraliza el encolado de documentos y su procesamiento en orden estricto de llegada.

## Objetivos

- Aplicar Singleton para coordinar una estructura de datos tipo cola.
- Impedir la creación de instancias adicionales mediante un constructor privado.
- Permitir encolar documentos con metadatos (nombre del archivo y autor).
- Procesar los documentos de forma secuencial y eliminarlos al completarse.
- Evitar condiciones de carrera cuando varias terminales envían trabajos al mismo tiempo.

## Implementación del patrón Singleton

La clase `PrintQueue` utiliza los siguientes elementos:

- **Clase interna `Holder`:** contiene la instancia estática del Singleton.
- **Constructor privado:** impide crear instancias directamente desde otras clases.
- **Método `getInstance()`:** devuelve siempre la misma instancia a través del `Holder`.
- **Método `enqueue()`:** agrega un `PrintJob` al final de la cola.
- **Método `processNext()`:** quita el documento más antiguo y simula su impresión.
- **Métodos `size()` e `isEmpty()`:** permiten consultar el estado de la cola.
- **Métodos sincronizados:** coordinan el acceso concurrente a la cola.

La instancia se crea de forma perezosa mediante el *Initialization-on-demand Holder Idiom*, es decir, únicamente cuando se solicita por primera vez.

## Propósito de la clase PrintQueue

La clase `PrintQueue` se creó para que todos los documentos pasen por una única cola. 
Si cada terminal tuviera su propia cola, no habría un orden global de llegada y se podrían perder o duplicar trabajos.

En este ejercicio la impresión es simulada mediante mensajes en la consola. Los documentos se representan con el record `PrintJob`.

## Concepto clave

En este ejercicio combinamos dos mecanismos de seguridad. El *Holder Idiom* garantiza que la instancia se cree una sola vez sin necesidad de sincronizar `getInstance()`, porque la JVM carga la clase interna de forma segura la primera vez que se usa. Además, `enqueue()` y `processNext()` son `synchronized`, por lo que dos hilos no pueden modificar la cola al mismo tiempo.

## Resultado

![ejercicio07.png](ejercicio07.png)

## Conclusión

Este ejercicio demuestra cómo Singleton permite coordinar una estructura compartida bajo múltiples peticiones concurrentes.

La cola vive exclusivamente dentro de la instancia única, y la sincronización asegura que los documentos se procesen en orden de llegada, sin pérdidas ni duplicados.
