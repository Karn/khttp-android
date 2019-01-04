package khttp.extensions

import io.reactivex.Single
import io.reactivex.SingleEmitter
import khttp.responses.Response
import khttp.structures.authorization.Authorization
import khttp.structures.files.FileLike

@JvmOverloads
fun delete(url: String, headers: Map<String, String?> = mapOf(), params: Map<String, String> = mapOf(), data: Any? = null, json: Any? = null, auth: Authorization? = null, cookies: Map<String, String>? = null, timeout: Double = khttp.DEFAULT_TIMEOUT, allowRedirects: Boolean? = null, stream: Boolean = false, files: List<FileLike> = listOf()): Single<Response> {
    return Single.create<Response> { emitter: SingleEmitter<Response> ->
        val response: Response = khttp.delete(url, headers, params, data, json, auth, cookies, timeout, allowRedirects, stream, files)
        emitter.onSuccess(response)
    }
}

@JvmOverloads
fun get(url: String, headers: Map<String, String?> = mapOf(), params: Map<String, String> = mapOf(), data: Any? = null, json: Any? = null, auth: Authorization? = null, cookies: Map<String, String>? = null, timeout: Double = khttp.DEFAULT_TIMEOUT, allowRedirects: Boolean? = null, stream: Boolean = false, files: List<FileLike> = listOf()): Single<Response> {
    return Single.create<Response> { emitter: SingleEmitter<Response> ->
        val response: Response = khttp.get(url, headers, params, data, json, auth, cookies, timeout, allowRedirects, stream, files)
        emitter.onSuccess(response)
    }
}

@JvmOverloads
fun head(url: String, headers: Map<String, String?> = mapOf(), params: Map<String, String> = mapOf(), data: Any? = null, json: Any? = null, auth: Authorization? = null, cookies: Map<String, String>? = null, timeout: Double = khttp.DEFAULT_TIMEOUT, allowRedirects: Boolean? = null, stream: Boolean = false, files: List<FileLike> = listOf()): Single<Response> {
    return Single.create<Response> { emitter: SingleEmitter<Response> ->
        val response: Response = khttp.head(url, headers, params, data, json, auth, cookies, timeout, allowRedirects, stream, files)
        emitter.onSuccess(response)
    }
}

@JvmOverloads
fun options(url: String, headers: Map<String, String?> = mapOf(), params: Map<String, String> = mapOf(), data: Any? = null, json: Any? = null, auth: Authorization? = null, cookies: Map<String, String>? = null, timeout: Double = khttp.DEFAULT_TIMEOUT, allowRedirects: Boolean? = null, stream: Boolean = false, files: List<FileLike> = listOf()): Single<Response> {
    return Single.create<Response> { emitter: SingleEmitter<Response> ->
        val response: Response = khttp.options(url, headers, params, data, json, auth, cookies, timeout, allowRedirects, stream, files)
        emitter.onSuccess(response)
    }
}

@JvmOverloads
fun patch(url: String, headers: Map<String, String?> = mapOf(), params: Map<String, String> = mapOf(), data: Any? = null, json: Any? = null, auth: Authorization? = null, cookies: Map<String, String>? = null, timeout: Double = khttp.DEFAULT_TIMEOUT, allowRedirects: Boolean? = null, stream: Boolean = false, files: List<FileLike> = listOf()): Single<Response> {
    return Single.create<Response> { emitter: SingleEmitter<Response> ->
        val response: Response = khttp.patch(url, headers, params, data, json, auth, cookies, timeout, allowRedirects, stream, files)
        emitter.onSuccess(response)
    }
}

@JvmOverloads
fun post(url: String, headers: Map<String, String?> = mapOf(), params: Map<String, String> = mapOf(), data: Any? = null, json: Any? = null, auth: Authorization? = null, cookies: Map<String, String>? = null, timeout: Double = khttp.DEFAULT_TIMEOUT, allowRedirects: Boolean? = null, stream: Boolean = false, files: List<FileLike> = listOf()): Single<Response> {
    return Single.create<Response> { emitter: SingleEmitter<Response> ->
        val response: Response = khttp.post(url, headers, params, data, json, auth, cookies, timeout, allowRedirects, stream, files)
        emitter.onSuccess(response)
    }
}

@JvmOverloads
fun put(url: String, headers: Map<String, String?> = mapOf(), params: Map<String, String> = mapOf(), data: Any? = null, json: Any? = null, auth: Authorization? = null, cookies: Map<String, String>? = null, timeout: Double = khttp.DEFAULT_TIMEOUT, allowRedirects: Boolean? = null, stream: Boolean = false, files: List<FileLike> = listOf()): Single<Response> {
    return Single.create<Response> { emitter: SingleEmitter<Response> ->
        val response: Response = khttp.put(url, headers, params, data, json, auth, cookies, timeout, allowRedirects, stream, files)
        emitter.onSuccess(response)
    }
}