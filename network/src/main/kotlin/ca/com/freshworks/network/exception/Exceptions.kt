package ca.com.freshworks.network.exception

import java.lang.Exception

open class GiphyNetworkException(override val message: String?) : Exception(message)
class ClientErrorException : GiphyNetworkException("")
class ServerErrorException : GiphyNetworkException("")