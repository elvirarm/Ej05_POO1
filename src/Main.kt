//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main(){

    fun pedirTiempo(msj: String, aceptaVacio: Boolean = false):Int{
        var num: Int? = null

        do {
            println(msj)
            val entrada = readln().trim()
            if (aceptaVacio && entrada.isEmpty()) {
                num = 0
            } else {
                try {
                    num = entrada.toInt()
                } catch (e: NumberFormatException) {
                    println("ERROR: Número no valido, inténtelo otra vez...")}
            }
        }while (num == null)

        return num
    }

    val hora = pedirTiempo("Introduzca la hora")
    val min = pedirTiempo("Introduzca los minutos:", aceptaVacio = true)
    val seg = pedirTiempo("Introduce los segundos", aceptaVacio = true)

    val tiempo1 = Tiempo(hora, min, seg)

    val tiempo2 = Tiempo(0, 125)

    tiempo1.incrementar(tiempo2)

    println("La máxima hora es ${Tiempo.MAX_HORA}")
}