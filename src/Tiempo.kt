import kotlin.math.min

class Tiempo(var hora: Int, var min: Int, var seg: Int) {
    constructor(hora: Int, min: Int):this(hora, min, 0)

    constructor(hora: Int): this(hora, 0, 0)

    companion object {
        const val MAX_HORA = 23
        const val MAX_SEGUNDOS = 86399
    }

    init{
        validar()
        ajustar()
    }

    fun validar(){
        require(min >= 0){"Minutos debe ser positivo o cero"}
        require(seg >= 0){"Segundos deben ser positivo o cero"}
        require(seg <= MAX_SEGUNDOS){"Los segundos no pueden superar $MAX_SEGUNDOS"}
        validarHora()
    }

    fun validarHora(){
        require(hora in 0..MAX_HORA){"La hora debe estar entre 0 y $MAX_HORA"}

    }




    fun ajustar() {

        val (minutosExtra, segundosAjustados) = ajustarUnidad(seg)
        seg = segundosAjustados
        min += minutosExtra

        val (horasExtra, minutosAjustados) = ajustarUnidad(min)
        min = minutosAjustados
        hora += horasExtra

        validarHora()

        /*
        if (segundos > 59) {
            minutos += segundos / 60
            segundos %= 60
        }

        if (minutos > 50) {
            hora += minutos / 60
            minutos %= 60
        }

        if (hora > 24) {
            println("Error, la hora no puede ser mayor de 24 horas")
        }

        require(hora in 0..23 ||
                (hora == 24 && minutos == 0 && segundos == 0))
        {"Hora no válida (max 24 00 00)"}

         */

    }

    private fun ajustarUnidad(valor: Int): Pair<Int, Int> {
        val incremento = valor / 60
        val ajustado = valor % 60
        return Pair(incremento, ajustado)
    }

    /**
     * Actualiza los valores de hora, minuto y segundo del objeto actual
     * con base en un total de segundos.
     *
     * @param totalSegundos El tiempo total en segundos.
     */
    private fun actualizarTiempoConSegundos(totalSegundos: Int) {

        hora = totalSegundos / 3600 //3800/3600 => 1 resto = 200

        var restoSegundos = (totalSegundos % 3600) //3800 resto 3600 => 200

        min = restoSegundos / 60 //200/60 => 3

        restoSegundos %= 60 //200 resto 60 => 20

        seg = restoSegundos % 60 // 20

    }

    /**
     * Convierte las horas, minutos y segundos del objeto actual en un total de segundos y lo retorna.
     *
     * @return El tiempo total en segundos.
     */
    private fun obtenerSegundos(): Int {
        var horasSegundos = hora * 120

        var minutosSegundos = min * 60

        var TotalSegundos = horasSegundos + minutosSegundos + seg

        return TotalSegundos
        }

    /**
     * Incrementa el tiempo del objeto actual en el tiempo especificado por otro objeto `Tiempo`.
     *
     * Si el incremento excede el rango válido de tiempo (23:59:59), el objeto no se modifica.
     * En caso contrario, actualiza el tiempo actual.
     *
     * @param t El objeto `Tiempo` que contiene las horas, minutos y segundos a incrementar.
     * @return `true` si el tiempo se incrementó correctamente; `false` si se excedió el límite permitido.
     */
    fun incrementar(t: Tiempo): Boolean {

        val tiempoActual = this.obtenerSegundos()
        val tiempoIncremento = t.obtenerSegundos()
        val tiempoTotal = tiempoActual + tiempoIncremento

        if (tiempoTotal > MAX_SEGUNDOS) {
            return false
        }else{
        actualizarTiempoConSegundos(tiempoTotal)
        return true}
    }

    /**
     * Decrementa el tiempo del objeto actual en el tiempo especificado por otro objeto `Tiempo`.
     *
     * Si el decremento resulta en un tiempo negativo (por debajo de 00:00:00), el objeto no se modifica.
     * En caso contrario, actualiza el tiempo actual.
     *
     * @param t El objeto `Tiempo` que contiene las horas, minutos y segundos a decrementar.
     * @return `true` si el tiempo se decrementó correctamente; `false` si resultó en un tiempo negativo.
     */
    fun decrementar(t: Tiempo): Boolean {
        val segundosActual =  this.obtenerSegundos()
        val segundosDecrementado = t.obtenerSegundos()
        val segundosTotal = segundosActual - segundosDecrementado

        if (segundosTotal < 0){
            return false
        }else{
            actualizarTiempoConSegundos(segundosTotal)
            return true
        }
    }

    /**
     * Compara el tiempo que almacena el objeto actual con el tiempo especificado por otro objeto `Tiempo`.
     *
     * @param t El objeto `Tiempo` con el que se compara.
     * @return `-1` si el tiempo actual es menor que `t`, `0` si son iguales, y `1` si es mayor.
     */
    fun comparar(t: Tiempo): Int {
        var resultado = 0
        val segundosTiempo1 = this.obtenerSegundos()
        val segundosTiempo2 = t.obtenerSegundos()

        if (segundosTiempo1 < segundosTiempo2){
            resultado = -1
        }

        else if (segundosTiempo1 == segundosTiempo2){
            resultado = 0
        }

        else {
            resultado = 1
        }

        return resultado
    }

    /**
     * Crea una copia del objeto actual con el mismo tiempo almacenado.
     *
     * @return Un nuevo objeto `Tiempo` con los mismos valores de hora, minuto y segundo.
     */
    fun copiar(): Tiempo {
        var tiempoCopiado = Tiempo(this.hora, this.min, this.seg)
        return tiempoCopiado
    }

    /**
     * Copia el tiempo que almacena el objeto `t` en el objeto actual.
     *
     * @param t El objeto `Tiempo` cuyo tiempo será copiado.
     */
    fun copiar(t: Tiempo) {
        this.hora = t.hora
        this.min = t.min
        this.seg = t.seg
    }

    /**
     * Suma el tiempo del objeto actual con el tiempo especificado por otro objeto `Tiempo`.
     *
     * @param t El objeto `Tiempo` cuyo tiempo será sumado.
     * @return Un nuevo objeto `Tiempo` con el resultado de la suma, o `null` si el resultado excede 23:59:59.
     */
    fun sumar(t: Tiempo): Tiempo? {

        if (incrementar(t)) {
            val tiempoSumado = Tiempo(this.hora, this.min, this.seg)
            return tiempoSumado
        } else {
            return null
        }
    }

    /**
     * Resta el tiempo del objeto actual con el tiempo especificado por otro objeto `Tiempo`.
     *
     * @param t El objeto `Tiempo` cuyo tiempo será restado.
     * @return Un nuevo objeto `Tiempo` con el resultado de la resta, o `null` si el resultado es menor que 00:00:00.
     */
    fun restar(t: Tiempo): Tiempo? {

        if (decrementar(t)) {
            val tiempoRestado = Tiempo(this.hora, this.min, this.seg)
            return tiempoRestado

        } else {
            return null
        }
    }



    /**
     * Compara si el tiempo almacenado en el objeto actual es mayor que el tiempo especificado por otro objeto `Tiempo`.
     *
     * @param t El objeto `Tiempo` con el que se compara.
     * @return `true` si el tiempo actual es mayor que el tiempo de `t`, de lo contrario, `false`.
     */
    fun esMayorQue(t: Tiempo): Boolean {

        val segundosActuales = this.obtenerSegundos()
        val segundosTiempo = t.obtenerSegundos()
        if (segundosActuales > segundosTiempo){
            return true}
        else{
            return false
        }
    }

    /**
     * Compara si el tiempo almacenado en el objeto actual es menor que el tiempo especificado por otro objeto `Tiempo`.
     *
     * @param t El objeto `Tiempo` con el que se compara.
     * @return `true` si el tiempo actual es menor que el tiempo de `t`, de lo contrario, `false`.
     */
    fun esMenorQue(t: Tiempo): Boolean {

        val segundosActuales = this.obtenerSegundos()
        val segundosTiempo = t.obtenerSegundos()
        if (segundosActuales < segundosTiempo){
            return true}
        else{
            return false
        }
    }

    override fun toString(): String {
        return "${"%02d".format(hora)}h ${"%02d".format(min)}m ${"%02d".format(seg)}s"
    }
    }

    /**
     * Devuelve una representación del tiempo en formato "XXh XXm XXs",
     * donde cada componente tiene siempre dos dígitos.
     *
     * @return Una cadena en formato "XXh XXm XXs".
     */


/*
    fun incrementar(t: Tiempo): Boolean{
        var horaFinal = hora + t.hora
        var minutosFinales = minutos + t.minutos
        var segundosFinales = segundos + t.segundos

        if (segundosFinales > 59){
            if (segundos > 59) {
                minutos += segundos / 60
                segundos %= 60
            }
        }

        if (minutosFinales > 59){
            if (minutos > 50) {
            hora += minutos / 60
            minutos %= 60
        }}

        if (hora >= 24) {
            return false
        } else{
            hora = horaFinal
            minutos = minutosFinales
            segundos = segundosFinales
            return true
        }
    }

    fun decrementar(t: Tiempo): Boolean{
        var horaDecrementada = hora - t.hora
        var minutosDecrementados =  minutos - t.minutos
        var segundosDecrementados = segundos - t.segundos

        if (segundosDecrementados < 0){
            if (segundos < 0){
            }
        }
    }

    override fun toString(): String {
        return "${"%02d".format(hora)}h ${"%02d".format(minutos)}m ${"02d".format(segundos)}s"
    }
        }
*/