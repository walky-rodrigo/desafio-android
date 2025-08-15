package common

enum class ErrorType(val title: String) {
    NETWORK("Sem conexão ou Time out"),
    SERVER("Problema no Servidor 5xx"),
    CLIENT("Problema Client 4xx"),
    PARSE("Problema no Parse do objeto"),
    UNKNOWN("Erro desconhecido")
}